package lab.cherry.nw.service.Impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.BookmarkEntity;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.model.QsheetEntity.ItemData;
import lab.cherry.nw.model.QsheetHistoryEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.QsheetHistoryRepository;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.QsheetHistoryService;
import lab.cherry.nw.service.QsheetService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
/**
 * <pre>
 * ClassName : QsheetHistoryServiceImpl
 * Type : class0.20
 * Description : 큐시트 히스토리와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("qsheetHistoryServiceImpl")
@Transactional
@RequiredArgsConstructor
public class QsheetHistoryServiceImpl implements QsheetHistoryService {

    private final QsheetHistoryRepository qsheetHistoryRepository;
    private final UserService userService;
    private final OrgService orgService;
    /**
     * [QsheetHistoryServiceImpl] 전체 큐시트 히스토리 조회 함수
     *
     * @return DB에서 전체 큐시트 히스토리 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 큐시트 히스토리 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 큐시트 히스토리를 조회하여, 큐시트 히스토리 목록을 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<QsheetHistoryEntity> getQsheetHistorys(Pageable pageable) {
    return qsheetHistoryRepository.findAll(pageable);
    }


//
    /**
     * [QsheetHistoryServiceImpl] 큐시트 히스토리 생성 함수
     *
     * @param qsheetHistoryCreateDto 큐시트 히스토리 생성에 필요한 큐시트 히스토리 등록 정보를 담은 개체입니다.
     * @return 생성된 큐시트 히스토리 정보를 리턴합니다.
     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 큐시트 히스토리를 등록합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    public void createQsheetHistory(QsheetEntity originQsheetEntity, QsheetEntity.QsheetUpdateDto qsheetUpdateDto) {
        // QsheetEntity qsheetEntity = qsheetService.findById(originQsheetEntity.getId());
        UserEntity userEntity = userService.findById(qsheetUpdateDto.getUpdateUser());
        List<Map<String,String>> contentList = new  ArrayList<>();
        if(qsheetUpdateDto.getOrgSeq()!=null){
            OrgEntity orgEntity = orgService.findById(qsheetUpdateDto.getOrgSeq());
            Map<String, String> data = new HashMap<>();
            data.put("action", "수정");
            data.put("info", "업체");
            data.put("content",orgEntity.getName());
            contentList.add(data);
        }
        if(qsheetUpdateDto.getMemo()!=null){
            Map<String, String> data = new HashMap<>();
            data.put("action", "수정");
            data.put("info", "시크릿 메모");
            data.put("content", qsheetUpdateDto.getMemo());
            contentList.add(data);
        }
        if(qsheetUpdateDto.getOrg_approverSeq()!=null && qsheetUpdateDto.isClient_confirm()){
            UserEntity Org_approverEntity = userService.findById(qsheetUpdateDto.getOrg_approverSeq());
            Map<String, String> data = new HashMap<>();
            data.put("action", "최종 확인");
            data.put("info", "업체 최종 확인");
            data.put("content","최종 확인자 : " + Org_approverEntity.getUsername());
            contentList.add(data);
        }
        if(qsheetUpdateDto.isClient_confirm()){
            Map<String, String> data = new HashMap<>();
            data.put("action", "최종 확인");
            data.put("info", "신랑&신부 최종 확인");
            data.put("content","신랑&신부 최종 확인 완료");
            contentList.add(data);
        }
        if(qsheetUpdateDto.getData()!=null){
            List<Map<String, ItemData>> result = compareLists(originQsheetEntity.getData(), qsheetUpdateDto.getData());
            for (Map<String, ItemData> change : result) {
                for (Map.Entry<String, ItemData> entry : change.entrySet()) {
                    String action = entry.getKey();
                    ItemData item = entry.getValue();
                    System.out.println(action + " : " + item.getProcess());
                    Map<String, String> data = new HashMap<>();
                    data.put("action", entry.getKey());
                    data.put("info", item.getProcess());
                    data.put("content",item.getProcess() + " " + entry.getKey());
                    contentList.add(data);
                }
            }
        }
        Instant instant = Instant.now();
        // UserEntity userEntity = userService.findById(qsheetHistoryCreateDto.getUserSeq());
        QsheetHistoryEntity qsheetHistoryEntity = QsheetHistoryEntity.builder()
			.qsheetid(originQsheetEntity)
            .userid(userEntity)
            .content(contentList)
            .updated_at(instant)
            .build();
        qsheetHistoryRepository.save(qsheetHistoryEntity);
    }
    public List<Map<String, ItemData>> compareLists(List<ItemData> list1, List<ItemData> list2) {
        List<Map<String, ItemData>> resultList = new ArrayList<>();
    
        // 두 리스트를 순회하면서 비교
        for (ItemData item1 : list1) {
            ItemData matchingItem = list2.stream()
                    .filter(item2 -> item2.getOrderIndex().equals(item1.getOrderIndex()))
                    .findFirst()
                    .orElse(null);
    
            if (matchingItem == null) {
                // list2에 없는 경우, 삭제로 처리
                Map<String, ItemData> change = new HashMap<>();
                change.put("삭제", item1);
                resultList.add(change);
            } else if (!item1.equals(matchingItem)) {
                // 내용이 다른 경우, 수정으로 처리
                Map<String, ItemData> change = new HashMap<>();
                change.put("수정", matchingItem);
                resultList.add(change);
            }
        }
    
        // list2에만 있는 항목을 추가로 처리
        for (ItemData item2 : list2) {
            ItemData matchingItem = list1.stream()
                    .filter(item1 -> item1.getOrderIndex().equals(item2.getOrderIndex()))
                    .findFirst()
                    .orElse(null);
    
            if (matchingItem == null) {
                Map<String, ItemData> change = new HashMap<>();
                change.put("추가", item2);
                resultList.add(change);
            }
        }
    
        return resultList;
    }

    
    /**
     * [QsheetHistoryServiceImpl] ID로 큐시트 히스토리 조회 함수
     *
     * @param id 조회할 큐시트 히스토리의 식별자입니다.
     * @return 주어진 식별자에 해당하는 큐시트 히스토리 정보
     * @throws EntityNotFoundException 해당 ID의 큐시트 히스토리 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 큐시트 히스토리 정보를 조회합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    public QsheetHistoryEntity findById(String id) {
        return qsheetHistoryRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("QsheetHistory with Id " + id + " Not Found."));
    }

    // @Transactional(readOnly = true)
    // public QsheetHistoryEntity findByUserId(ObjectId userId) {
    //     return qsheetHistoryRepository.findByUserid(userId).orElseThrow(() -> new EntityNotFoundException("QsheetHistory with User id " + userId.toString() + " Not Found."));
    // }
    
    @Transactional(readOnly = true)
    public Page<QsheetHistoryEntity> findPageByUserId(String userid, Pageable pageable) {
        return qsheetHistoryRepository.findPageByUserid(userid, pageable);
    }

    @Transactional(readOnly = true)
    public Page<QsheetHistoryEntity> findPageByQsheetId(String qsheetid, Pageable pageable) {
        return qsheetHistoryRepository.findPageByQsheetid(qsheetid, pageable);
    }

    @Transactional(readOnly = true)
    @Override
    public List<QsheetHistoryEntity> findByQsheetId(ObjectId qsheeObjectId) {
        return EntityNotFoundException.requireNotEmpty(qsheetHistoryRepository.findByQsheetId(qsheeObjectId), "QsheetId Not Found");
    }
    
}
