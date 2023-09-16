package lab.cherry.nw.service;

import lab.cherry.nw.model.RoleEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;


/**
 * <pre>
 * ClassName : RoleService
 * Type : interface
 * Description : 역할와 관련된 함수를 정리한 인터페이스입니다.
 * Related : OrgController, OrgServiceImpl
 * </pre>
 */
@Component
public interface RoleService {
    Page<RoleEntity> getRoles(Pageable pageable);
    RoleEntity createRole(RoleEntity.CreateDto orgCreateDto);
    RoleEntity findById(String id);
    RoleEntity findByName(String name);
    void deleteById(String id);
    Page<RoleEntity> findPageByName(String name, Pageable pageable);
}
