package lab.cherry.nw.service;

import lab.cherry.nw.model.OrgEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * ClassName : OrgService
 * Type : interface
 * Description : 조직와 관련된 함수를 정리한 인터페이스입니다.
 * Related : OrgController, OrgServiceImpl
 * </pre>
 */
@Component
public interface OrgService {
    Page<OrgEntity> getOrganizations(Pageable pageable);
    OrgEntity createOrganization(OrgEntity.OrgCreateDto orgCreateDto);
    void updateById(String id, OrgEntity.OrgUpdateDto org);
    OrgEntity findById(String id);
    OrgEntity findByName(String name);
    void deleteById(String id);
    Page<OrgEntity> findPageByName(String name, Pageable pageable);
    void inviteUser(String orgid, String email);
    List<OrgEntity> getOrganizationList();
}
