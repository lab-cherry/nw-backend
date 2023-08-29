package lab.cherry.nw.service;

import lab.cherry.nw.model.RoleEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;


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
    List<RoleEntity> getRoles();
    RoleEntity createRole(RoleEntity.CreateDto roleCreateDto);
    void updateRole(RoleEntity user);
    void deleteRole(String id);
    RoleEntity findById(String id);
    RoleEntity findByName(String name);
}
