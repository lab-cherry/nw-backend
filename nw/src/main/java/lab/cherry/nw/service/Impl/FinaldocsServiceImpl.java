package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.FinaldocsEntity;
import lab.cherry.nw.model.FinalTemplEntity;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.FinaldocsRepository;
import lab.cherry.nw.service.FinalTemplService;
import lab.cherry.nw.service.FinaldocsService;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * <pre>
 * ClassName : FinaldocsServiceImpl
 * Type : class
 * Description : 최종확인서와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("finaldocsServiceImpl")
@Transactional
@RequiredArgsConstructor
public class FinaldocsServiceImpl implements FinaldocsService {

    private final FinaldocsRepository finaldocsRepository;
    private final UserService userService;
    private final OrgService orgService;
    private final FinalTemplService finalTemplService;

    /**
     * [FinaldocsServiceImpl] 최종 확인서 조회 함수
     *
     * @return DB에서 최종 확인서 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 최종 확인서 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 최종 확인서 조회하여, 최종 확인정보 목록을 반환합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<FinaldocsEntity> getFinaldocs(Pageable pageable) {

        return finaldocsRepository.findAll(pageable);
    }

    /**
     * [FinaldocsServiceImpl] 최종확인서 생성 함수
     *
	 * @param finaldocsCreateDto 최종확인서 생성에 필요한 최종확인서 등록 정보를 담은 개체입니다.
     * @return 생성된 최종확인서 정보를 리턴합니다.
     * <pre>
     * 최종확인서를 등록합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public FinaldocsEntity createFinaldocs(FinaldocsEntity.FinaldocsCreateDto finaldocsCreateDto) {

        Instant instant = Instant.now();

        UserEntity userEntity = userService.findById(finaldocsCreateDto.getUserid());
        OrgEntity orgEntity = orgService.findById(finaldocsCreateDto.getOrgid());
        FinalTemplEntity finamTemplEntity = finalTemplService.findById(finaldocsCreateDto.getFinaltemplid());

        FinaldocsEntity finaldocsEntity = FinaldocsEntity.builder()
            .name(finaldocsCreateDto.getName())
            .content(finaldocsCreateDto.getContent())
            .userid(userEntity)
            .orgid(orgEntity)
            .finaltemplid(finamTemplEntity)
            .createdAt(instant)
            .build();

        return finaldocsRepository.save(finaldocsEntity);
    }

    /**
     * [FinaldocsServiceImpl] 최종확인서 수정 함수
     *
     * @param finaldocs 최종확인서 수정에 필요한 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 최종확인서 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 최종확인서에 대한 정보를 수정합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public void updateById(String id, FinaldocsEntity.FinaldocsUpdateDto finaldocs) {

        FinaldocsEntity finaldocsEntity = findById(id);
        UserEntity userEntity = userService.findById(finaldocs.getUserid());
        OrgEntity orgEntity = orgService.findById(finaldocs.getOrgid());
        FinalTemplEntity finamTemplEntity = finalTemplService.findById(finaldocs.getFinaltemplid());

        Instant instant = Instant.now();

        if (finaldocs.getContent() != null) {

            finaldocsEntity = FinaldocsEntity.builder()
                    .id(finaldocsEntity.getId())
                    .userid(userEntity)
                    .orgid(orgEntity)
                    .finaltemplid(finamTemplEntity)
                    .content((finaldocs.getContent() != null) ? finaldocs.getContent() : finaldocsEntity.getContent())
                    .updated_at(instant)
                    .build();

            finaldocsRepository.save(finaldocsEntity);

        } else {
            log.error("[FinaldocsServiceImpl - udpateFinaldocs] Content만 수정 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    /**
     * [FinaldocsServiceImpl] 최종확인서 삭제 함수
     *
     * @param id 삭제할 최종확인서의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 최종확인서 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 최종확인서 정보를 삭제합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public void deleteById(String id) {
        finaldocsRepository.delete(finaldocsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Fianldocs with Id " + id + " Not Found.")));
    }



    /**
     * [FinaldocsServiceImpl] ID로 최종확인서 조회 함수
     *
     * @param id 조회할 최종확인서의 식별자입니다.
     * @return 주어진 식별자에 해당하는 최종확인서 정보
     * @throws EntityNotFoundException 해당 ID의 최종확인서 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 최종확인서 정보를 조회합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
//    @Transactional(readOnly = true)
//    public FinaldocsEntity findByName(String name) {
//        return finaldocsRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Fianldocs with Id " + name + " Not Found."));
//    }


    @Transactional(readOnly = true)
    public FinaldocsEntity findById(String id) {
        return finaldocsRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("finaldocs with Id " + id + " Not Found."));
    }

    /**
     * [FinaldocsServiceImpl] NAME으로 최종확인서 조회 함수
     *
     * @param name 조회할 최종확인서의 이름입니다.
     * @return 주어진 이름에 해당하는 최종확인서 정보를 리턴합니다.
     * @throws EntityNotFoundException 해당 이름의 최종확인서 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 name에 해당하는 최종확인서 정보를 조회합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @Transactional(readOnly = true)
    public FinaldocsEntity findByName(String name) {
        return finaldocsRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Org with Name " + name + " Not Found."));
    }

    @Transactional(readOnly = true)
    public Page<FinaldocsEntity> findPageByName(String name, Pageable pageable) {
        return finaldocsRepository.findPageByName(name, pageable);
    }


    @Transactional(readOnly = true)
    public Page<FinaldocsEntity> findPageById(String id, Pageable pageable) {
        return finaldocsRepository.findPageById(id, pageable);
    }
}
