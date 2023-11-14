package lab.cherry.nw.service.Impl;

import java.time.Instant;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.DocsEntity;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.DocsRepository;
import lab.cherry.nw.service.DocsService;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : DocsServiceImpl
 * Type : class
 * Description : 문서 관리와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("docsServiceImpl")
@Transactional
@RequiredArgsConstructor
public class DocsServiceImpl implements DocsService {

    private final UserService userService;
    private final OrgService orgService;
    private final DocsRepository docsRepository;


    /**
     * [DocsServiceImpl] 전체 문서 조회 함수
     *
     * @return DB에서 전체 문서 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 문서 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 문서를 조회하여, 문서 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<DocsEntity> getDocs(Pageable pageable) {
    return docsRepository.findAll(pageable);
}


//
    /**
     * [DocsServiceImpl] 문서 생성 함수
     *
     * @param docsCreateDto 문서 생성에 필요한 등록 정보를 담은 개체입니다.
     * @return 생성된 문서 정보를 리턴합니다.
     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 문서을 등록합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public DocsEntity createDocs(DocsEntity.DocsCreateDto docsCreateDto) {

        // user&org Check
        UserEntity userEntity = userService.findById(docsCreateDto.getUserSeq());
        OrgEntity orgEntity = orgService.findById(docsCreateDto.getOrgSeq());

        // Init(Object)
        Instant instant = Instant.now();
        ObjectId objectid = new ObjectId();
        
        DocsEntity docsEntity = DocsEntity.builder()
            .id(objectid.toString())
            .user(userEntity)
            .org(orgEntity)
            .data(DocsEntity.DocsData.builder()
                .title(docsCreateDto.getData().getTitle())
                .author(docsCreateDto.getData().getAuthor())
                .content(docsCreateDto.getData().getContent())
            .build())
            .created_at(instant)
            .updated_at(instant)
            .build();

        docsRepository.save(docsEntity);

        return docsEntity;
    }

    /**
     * [DocsServiceImpl] 문서 수정 함수
     *
     * @param id 조회할 문서의 고유번호입니다.
     * @param docsUpdateDto 문서 수정에 필요한 사용자 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 문서 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 문서에 대해 사용자 정보를 수정합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void updateById(String id, DocsEntity.DocsUpdateDto docsUpdateDto) {
        
        DocsEntity docsEntity = findById(id);

        // Init(Object)
        Instant instant = Instant.now();

        if(docsUpdateDto != null) {

            docsEntity = DocsEntity.builder()
                .id(docsEntity.getId())
                .user(docsEntity.getUser())
                .org(docsEntity.getOrg())
                .data(DocsEntity.DocsData.builder()
                    .title((docsUpdateDto.getData().getTitle() != null) ? docsUpdateDto.getData().getTitle() : docsEntity.getData().getTitle())
                    .author((docsUpdateDto.getData().getAuthor() != null) ? docsUpdateDto.getData().getAuthor() : docsEntity.getData().getAuthor())
                    .content((docsUpdateDto.getData().getContent() != null) ? docsUpdateDto.getData().getContent() : docsEntity.getData().getContent())
                .build())
                .created_at(docsEntity.getCreated_at())
                .updated_at(instant)
                .build();
                
            docsRepository.save(docsEntity);

        } else {
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
//
    /**
     * [DocsServiceImpl] 문서 삭제 함수
     *
     * @param id 삭제할 문서의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 문서 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 문서 정보를 삭제합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
     public void deleteById(String id) {
        docsRepository.delete(docsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Docs with Id " + id + " Not Found.")));
    }

    /**
     * [DocsServiceImpl] ID로 문서 조회 함수
     *
     * @param id 조회할 문서의 식별자입니다.
     * @return 주어진 식별자에 해당하는 문서 정보
     * @throws EntityNotFoundException 해당 ID의 문서 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 문서 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public DocsEntity findById(String id) {
        return docsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Docs with Id " + id + " Not Found."));
    }
}