package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.repository.RoleRepository;
import lab.cherry.nw.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * <pre>
 * ClassName : RoleServiceImpl
 * Type : class
 * Description : 역할과 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("roleServiceImpl")
@Transactional
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    /**
     * [RoleServiceImpl] 전체 역할 조회 함수
     *
     * @return DB에서 전체 역할 정보 목록을 리턴합니다.
     * <pre>
     * 전체 역할을 조회하여, 사용자 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<RoleEntity> getRoles(Pageable pageable) {
        return roleRepository.findAll(pageable);
        //        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Roles Not Found");
    }

    /**
     * [RoleServiceImpl] 역할 생성 함수
     *
     * @param roleCreateDto 역할 생성에 필요한 역할 등록 정보를 담은 개체입니다.
     * @return 생성된 역할 정보를 리턴합니다.
     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 역할을 등록합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public RoleEntity createRole(RoleEntity.CreateDto roleCreateDto) {

        Instant instant = Instant.now();
        checkExistsWithRoleName(roleCreateDto.getName()); // 동일한 이름 중복체크

        RoleEntity roleEntity = RoleEntity.builder()
            .name("ROLE_" + roleCreateDto.getName().toUpperCase())
            .created_at(instant)
            .build();

        return roleRepository.save(roleEntity);
    }

    /**
     * [RoleServiceImpl] 역할 삭제 함수
     *
     * @param id 삭제할 역할의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 역할 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 역할 정보를 삭제합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void deleteById(String id) {
        roleRepository.delete(roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role with Id " + id + " Not Found.")));
    }
    
    /**
     * [RoleServiceImpl] 역할 이름 중복 체크 함수
     *
     * @param name 중복 체크에 필요한 역할 이름 객체입니다.
     * @throws CustomException 역할의 이름이 중복된 경우 예외 처리 발생
     * <pre>
     * 입력된 역할 이름으로 이미 등록된 역할이 있는지 확인합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public void checkExistsWithRoleName(String name) {
        if (roleRepository.findByName(name).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE); // 조직 이름이 중복됨
        }
    }

    /**
     * [RoleServiceImpl] NAME으로 역할 조회 함수
     *
     * @param name 조회할 역할의 이름입니다.
     * @return 주어진 이름에 해당하는 역할 정보를 리턴합니다.
     * @throws EntityNotFoundException 해당 이름의 역할 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 name에 해당하는 역할 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public RoleEntity findByName(String name) {
        return roleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Role with Name " + name + " Not Found."));
    }

    /**
     * [RoleServiceImpl] ID로 역할 조회 함수
     *
     * @param id 조회할 역할의 식별자입니다.
     * @return 주어진 식별자에 해당하는 역할 정보
     * @throws EntityNotFoundException 해당 ID의 역할 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 역할역할 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public RoleEntity findById(String id) {
        return roleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Role with Id " + id + " Not Found."));
    }

    @Transactional(readOnly = true)
    public Page<RoleEntity> findPageByName(String name, Pageable pageable) {
        return roleRepository.findPageByName(name, pageable);
    }
}
