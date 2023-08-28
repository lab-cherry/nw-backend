package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.repository.OrgRepository;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.util.TsidGenerator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
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

    /**
     * [OrgServiceImpl] 전체 조직 조회 함수
     *
     * @return DB에서 전체 조직 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 조직를 조회하여, 사용자 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public List<OrgEntity> getOrgs() {
        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Orgs Not Found");
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
    public OrgEntity createOrg(OrgEntity.CreateDto orgCreateDto) {

        Instant instant = Instant.now();
        checkExistsWithOrgName(orgCreateDto.getName()); // 동일한 이름 중복체크

        OrgEntity orgEntity = OrgEntity.builder()
            .id(TsidGenerator.next())
            .name(orgCreateDto.getName())
            .biznum(orgCreateDto.getBiznum())
            .contact(orgCreateDto.getContact())
            .enabled(true)
            .created_at(Timestamp.from(instant))
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
    public void updateOrg(OrgEntity org) {
        findByName(org.getName());
        orgRepository.save(org);
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
    public void deleteOrg(Long id) {
        orgRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Org with Id " + id + " Not Found."));
        orgRepository.deleteById(id);
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
    public OrgEntity findById(Long id) {
        return orgRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Org with Id " + id + " Not Found."));
    }
}
