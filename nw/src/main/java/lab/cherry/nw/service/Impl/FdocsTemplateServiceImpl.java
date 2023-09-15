package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.FdocsTemplateEntity;
import lab.cherry.nw.model.FinaldocsEntity;
import lab.cherry.nw.repository.FdocsTemplateRepository;
import lab.cherry.nw.repository.FinaldocsRepository;
import lab.cherry.nw.service.FdocsTemplateService;
import lab.cherry.nw.service.FinaldocsService;
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
@Service("fdocsTemplateServiceImpl")
@Transactional
@RequiredArgsConstructor
public class FdocsTemplateServiceImpl implements FdocsTemplateService {

    private final FdocsTemplateRepository fdocsTemplateRepository;

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
    public Page<FdocsTemplateEntity> getFdocsTemplate(Pageable pageable) {

        return fdocsTemplateRepository.findAll(pageable);


        //        return EntityNotFoundException.requireNotEmpty(finaldocsRepository.findAll(), "Fianldocs Not Found");
    }

    /**
     * [FinaldocsServiceImpl] 조직 생성 함수
     *
     * @param finaldocsCreateDto 최종확인서 생성에 필요한 조직 등록 정보를 담은 개체입니다.
     * @return 생성된 최종확인서 정보를 리턴합니다.
     * <pre>
     * 최종확인서를 등록합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public FdocsTemplateEntity createFdocsTemplate(FdocsTemplateEntity.CreateDto fdocsTelpateCreateDto) {

        Instant instant = Instant.now();


        FdocsTemplateEntity finaldocsEntity = FdocsTemplateEntity.builder()
            .name(fdocsTelpateCreateDto.getName())
            .groom(fdocsTelpateCreateDto.getGroom())
            .bride(fdocsTelpateCreateDto.getBride())
            .weddingDatename(fdocsTelpateCreateDto.getWeddingDatename())
            .guaranteePerson(fdocsTelpateCreateDto.getGuaranteePerson())
            .hallFee(fdocsTelpateCreateDto.getHallFee())
            .weddingPicture(fdocsTelpateCreateDto.getWeddingPicture())
            .dresshelper(fdocsTelpateCreateDto.getDresshelper())
            .mc(fdocsTelpateCreateDto.getMc())
            .guaranteePerson(fdocsTelpateCreateDto.getGuaranteePerson())
            .photo(fdocsTelpateCreateDto.getPhoto())
            .bus(fdocsTelpateCreateDto.isBus())
            .bouqet(fdocsTelpateCreateDto.isBouqet())
            .officiant(fdocsTelpateCreateDto.isOfficiant())
            .pyebaek(fdocsTelpateCreateDto.isPyebaek())
            .etc(fdocsTelpateCreateDto.getEtc())
            .build();

        return fdocsTemplateRepository.save(finaldocsEntity);
    }
    
    /**
     * [FinaldocsServiceImpl] 조직 수정 함수
     *
     * @param org 조직 수정에 필요한 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 조직에 대한 정보를 수정합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
//    public void updateById(String id, FinaldocsEntity.UpdateDto org) {
//
//        FinaldocsEntity finaldocsEntity = findById(id);
//
//        if (org.getName() != null || org.getBiznum() != null || org.getContact() != null) {
//
//            finaldocsEntity = OrgEntity.builder()
//                .id(orgEntity.getId())
//                .name((org.getName() != null) ? org.getName() : orgEntity.getName())
//                .biznum((org.getBiznum() != null) ? org.getBiznum() : orgEntity.getBiznum())
//                .contact((org.getBiznum() != null) ? org.getBiznum() : orgEntity.getBiznum())
//                .build();
//
//            orgRepository.save(finaldocsEntity);
//
//        } else {
//            log.error("[OrgServiceImpl - udpateOrganization] orgName, bizNum, contact 수정 가능합니다.");
//            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
//        }
//    }

    /**
     * [FinaldocsServiceImpl] 조직 삭제 함수
     *
     * @param id 삭제할 조직의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 조직 정보를 삭제합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public void deleteById(String id) {
        fdocsTemplateRepository.delete(fdocsTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Fianldocs with Id " + id + " Not Found.")));
    }



    /**
     * [FinaldocsServiceImpl] ID로 조직 조회 함수
     *
     * @param id 조회할 조직의 식별자입니다.
     * @return 주어진 식별자에 해당하는 조직 정보
     * @throws EntityNotFoundException 해당 ID의 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 조직 정보를 조회합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
//    @Transactional(readOnly = true)
//    public FinaldocsEntity findByName(String name) {
//        return finaldocsRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Fianldocs with Id " + name + " Not Found."));
//    }


    @Transactional(readOnly = true)
    public FdocsTemplateEntity findById(String id) {
        return fdocsTemplateRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("finaldocs with Id " + id + " Not Found."));
    }

    /**
     * [OrgServiceImpl] NAME으로 조직 조회 함수
     *
     * @param name 조회할 조직의 이름입니다.
     * @return 주어진 이름에 해당하는 조직 정보를 리턴합니다.
     * @throws EntityNotFoundException 해당 이름의 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 name에 해당하는 조직 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public FdocsTemplateEntity findByName(String name) {
        return fdocsTemplateRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Org with Name " + name + " Not Found."));
    }

    @Transactional(readOnly = true)
    public Page<FdocsTemplateEntity> findPageByName(String name, Pageable pageable) {
        return fdocsTemplateRepository.findPageByName(name, pageable);
    }


    @Transactional(readOnly = true)
    public Page<FdocsTemplateEntity> findPageById(String id, Pageable pageable) {
        return fdocsTemplateRepository.findPageById(id, pageable);
    }
}
