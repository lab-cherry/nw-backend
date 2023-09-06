package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.repository.QsheetRepository;
import lab.cherry.nw.service.QsheetService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;
/**
 * <pre>
 * ClassName : QsheetServiceImpl
 * Type : class
 * Description : 큐시트와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("QsheetServiceImpl")
@Transactional
@RequiredArgsConstructor
public class QsheetServiceImpl implements QsheetService {

    private final QsheetRepository qsheetRepository;
    /**
     * [QsheetServiceImpl] 전체 큐시트 조회 함수
     *
     * @return DB에서 전체 큐시트 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 큐시트 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 큐시트를 조회하여, 큐시트 목록을 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<QsheetEntity> getQsheets(Pageable pageable) {
    return qsheetRepository.findAll(pageable);
    //        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Roles Not Found");
}


//
    /**
     * [QsheetServiceImpl] 큐시트 생성 함수
     *
     * @param qsheetCreateDto 큐시트 생성에 필요한 큐시트 등록 정보를 담은 개체입니다.
     * @return 생성된 큐시트 정보를 리턴합니다.
     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 큐시트을 등록합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    public void createQsheet(QsheetEntity.CreateDto qsheetCreateDto) {

//        Instant instant = Instant.now();
//        UserEntity userEntity = userRepository.findById(qsheetCreateDto.getUserSeq());
//        OrgEntity orgEntity = orgRepository.findById(qsheetCreateDto.getOrgSeq());
//       JsonNodeFactory factory = JsonNodeFactory.instance;
//       ObjectNode objectNode = factory.objectNode();
//        objectNode.put("data", qsheetCreateDto.getData());
        log.error("qsheetCreateDto :  {} ", qsheetCreateDto.getData());

        QsheetEntity qsheetEntity = QsheetEntity.builder()
            .name(qsheetCreateDto.getName())
            .data(qsheetCreateDto.getData())
//            .opening(QsheetEntity.ContentField.builder()
//                        .orderIndex(qsheetCreateDto.getOpening().getOrderIndex())
//                        .content(qsheetCreateDto.getOpening().getContent())
//                        .build())
//            .light(QsheetEntity.ContentField.builder()
//                        .orderIndex(qsheetCreateDto.getLight().getOrderIndex())
//                        .content(qsheetCreateDto.getLight().getContent())
//                        .build())
            .build();
        qsheetRepository.save(qsheetEntity);
    }
//
//    /**
//     * [QsheetServiceImpl] 큐시트 수정 함수
//     *
//     * @param org 큐시트 수정에 필요한 정보를 담은 개체입니다.
//     * @throws EntityNotFoundException 큐시트 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 특정 큐시트에 대한 정보를 수정합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    public void updateQsheet(QsheetEntity org) {
//        findByName(org.getName());
//        qsheetRepository.save(org);
//    }
//
//    /**
//     * [QsheetServiceImpl] 큐시트 삭제 함수
//     *
//     * @param id 삭제할 큐시트의 식별자입니다.
//     * @throws EntityNotFoundException 해당 ID의 큐시트 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 입력한 id를 가진 큐시트 정보를 삭제합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    public void deleteQsheet(Long id) {
//        qsheetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Qsheet with Id " + id + " Not Found."));
//        qsheetRepository.deleteById(id);
//    }
//
//    /**
//     * [QsheetServiceImpl] 큐시트 이름 중복 체크 함수
//     *
//     * @param name 중복 체크에 필요한 큐시트 이름 객체입니다.
//     * @throws CustomException 큐시트의 이름이 중복된 경우 예외 처리 발생
//     * <pre>
//     * 입력된 큐시트 이름으로 이미 등록된 큐시트이 있는지 확인합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public void checkExistsWithQsheetName(String name) {
//        if (qsheetRepository.findByName(name).isPresent()) {
//            throw new CustomException(ErrorCode.DUPLICATE); // 큐시트 이름이 중복됨
//        }
//    }
//
//    /**
//     * [QsheetServiceImpl] NAME으로 큐시트 조회 함수
//     *
//     * @param name 조회할 큐시트의 이름입니다.
//     * @return 주어진 이름에 해당하는 큐시트 정보를 리턴합니다.
//     * @throws EntityNotFoundException 해당 이름의 큐시트 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 입력한 name에 해당하는 큐시트 정보를 조회합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public QsheetEntity findByName(String name) {
//        return qsheetRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Qsheet with Name " + name + " Not Found."));
//    }
//
//    /**
//     * [QsheetServiceImpl] ID로 큐시트 조회 함수
//     *
//     * @param id 조회할 큐시트의 식별자입니다.
//     * @return 주어진 식별자에 해당하는 큐시트 정보
//     * @throws EntityNotFoundException 해당 ID의 큐시트 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 입력한 id에 해당하는 큐시트 정보를 조회합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public QsheetEntity findById(Long id) {
//        return qsheetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Qsheet with Id " + id + " Not Found."));
//    }

    @Transactional(readOnly = true)
    public QsheetEntity findById(String id) {
        return qsheetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Qsheet with Id " + id + " Not Found."));
    }

    @Transactional(readOnly = true)
    public Page<QsheetEntity> findPageByUserId(String userid, Pageable pageable) {
        return qsheetRepository.findPageByUserid(userid, pageable);
    }

    @Transactional(readOnly = true)
    public Page<QsheetEntity> findPageByOrgId(String orgid, Pageable pageable) {
        return qsheetRepository.findPageByOrgid(orgid, pageable);
    }
    
}
