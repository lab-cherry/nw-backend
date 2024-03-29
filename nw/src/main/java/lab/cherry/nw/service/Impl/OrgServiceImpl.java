package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.repository.OrgRepository;
import lab.cherry.nw.service.EmailAuthService;
import lab.cherry.nw.service.OrgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.List;

/**
 * <pre>
 * ClassName : OrgServiceImpl
 * Type : class
 * Description : 조직와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("orgServiceImpl")
@Transactional
@RequiredArgsConstructor
public class OrgServiceImpl implements OrgService {

	private final OrgRepository orgRepository;
    private final EmailAuthService emailAuthService;

    /**
     * [OrgServiceImpl] 전체 조직 조회 함수
     *
     * @return DB에서 전체 조직 정보 목록을 리턴합니다.
     * <pre>
     * 전체 조직를 조회하여, 사용자 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<OrgEntity> getOrganizations(Pageable pageable) {
        return orgRepository.findAll(pageable);
//        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Orgs Not Found");
    }

    /**
     * [OrgServiceImpl] 전체 조직 조회 함수
     *
     * @return DB에서 전체 조직 정보 목록을 리턴합니다.
     * <pre>
     * 전체 조직를 조회하여, 사용자 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrgEntity> getOrganizationList() {
        return orgRepository.findAll();
//        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Orgs Not Found");
    }

    /**
     * [OrgServiceImpl] 조직 생성 함수
     *
     * @param orgCreateDto 조직 생성에 필요한 조직 등록 정보를 담은 개체입니다.
     * @return 생성된 조직 정보를 리턴합니다.
     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 조직을 등록합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public OrgEntity createOrganization(OrgEntity.OrgCreateDto orgCreateDto) {
		ObjectId orgId = new ObjectId();

		// try {
		// 	// ObjectId orgId로 Bucket 생성
		// 	minioService.createBucketIfNotExists(orgId.toString());

		// 	// ObjectId orgId에 해당하는 GlobalPolicy 생성
		// 	minioService.createGlobalPolicy(orgId.toString());

		// 	// 생성된 Bucket에 Policy 적용
		// 	minioService.setBucketPolicy(orgId.toString());

		// } catch (Exception e) {
		// 	log.error(e.getMessage());
		// }

        Instant instant = Instant.now();
        checkExistsWithOrgName(orgCreateDto.getOrgName()); // 동일한 이름 중복체크

        OrgEntity orgEntity = OrgEntity.builder()
			.id(orgId.toString())
            .name(orgCreateDto.getOrgName())
            .biznum(orgCreateDto.getOrgBiznum())
            .contact(orgCreateDto.getOrgContact())
			.address(orgCreateDto.getOrgAddress())
            .enabled(true)
            .created_at(instant)
            .build();

        return orgRepository.save(orgEntity);
    }
    
    /**
     * [OrgServiceImpl] 조직 수정 함수
     *
     * @param org 조직 수정에 필요한 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 조직에 대한 정보를 수정합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void updateById(String id, OrgEntity.OrgUpdateDto org) {

        OrgEntity orgEntity = findById(id);

        if (org.getOrgName() != null || org.getOrgBiznum() != null || org.getOrgContact() != null || org.getOrgAddress() != null || org.getOrgEnabled() != null) {
            orgEntity = OrgEntity.builder()
                .id(orgEntity.getId())
                .name((org.getOrgName() != null) ? org.getOrgName() : orgEntity.getName())
                .biznum((org.getOrgBiznum() != null) ? org.getOrgBiznum() : orgEntity.getBiznum())
                .contact((org.getOrgContact() != null) ? org.getOrgContact() : orgEntity.getContact())
                .address((org.getOrgAddress() != null) ? org.getOrgAddress() : orgEntity.getAddress())
                .enabled((org.getOrgEnabled() != null) ? org.getOrgEnabled() : orgEntity.getEnabled())
                .build();

            orgRepository.save(orgEntity);

        } else {
            log.error("[OrgServiceImpl - udpateOrganization] orgName, bizNum, contact 수정 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    /**
     * [OrgServiceImpl] 조직 삭제 함수
     *
     * @param id 삭제할 조직의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 조직 정보를 삭제합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void deleteById(String id) {
        orgRepository.delete(orgRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Org with Id " + id + " Not Found.")));
    }
    
    /**
     * [OrgServiceImpl] 조직 이름 중복 체크 함수
     *
     * @param name 중복 체크에 필요한 조직 이름 객체입니다.
     * @throws CustomException 조직의 이름이 중복된 경우 예외 처리 발생
     * <pre>
     * 입력된 조직 이름으로 이미 등록된 조직이 있는지 확인합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public void checkExistsWithOrgName(String name) {
        if (orgRepository.findByName(name).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE); // 조직 이름이 중복됨
        }
    }

    /**
     * [OrgServiceImpl] ID로 조직 조회 함수
     *
     * @param id 조회할 조직의 식별자입니다.
     * @return 주어진 식별자에 해당하는 조직 정보
     * @throws EntityNotFoundException 해당 ID의 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 조직 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public OrgEntity findById(String id) {
        return orgRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Org with Id " + id + " Not Found."));
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
    public OrgEntity findByName(String name) {
        return orgRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Org with Name " + name + " Not Found."));
    }

    @Transactional(readOnly = true)
    public Page<OrgEntity> findPageByName(String name, Pageable pageable) {
        return orgRepository.findPageByName(name, pageable);
    }

    public void inviteOrgSend(String orgid, String email) {

        log.error("orgid is {}", orgid);

        OrgEntity orgEntity = findById(orgid);
        emailAuthService.InviteOrgSend(orgEntity.getId(), orgEntity.getName(), email);

    }
}
