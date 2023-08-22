package lab.cherry.nw.service;

import lab.cherry.nw.model.OrgEntity;
import org.springframework.stereotype.Component;
import lab.cherry.nw.model.UserEntity;
import java.util.List;


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
    List<OrgEntity> getOrgs();
    OrgEntity createOrg(OrgEntity.CreateDto orgCreateDto);
    void updateOrg(OrgEntity org);
    void deleteOrg(Long id);
    OrgEntity findById(Long id);
    OrgEntity findByName(String name);
}
