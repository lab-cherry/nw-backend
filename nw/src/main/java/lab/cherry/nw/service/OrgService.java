package lab.cherry.nw.service;

import lab.cherry.nw.model.OrgEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    Page<OrgEntity> getOrganizations(Pageable pageable);
    OrgEntity createOrganization(OrgEntity.CreateDto orgCreateDto);
    void updateById(Long tsid, OrgEntity.UpdateDto org);
    OrgEntity findById(Long tsid);
    OrgEntity findByName(String name);
    void deleteById(Long tsid);
    Page<OrgEntity> findPageByName(String name, Pageable pageable);
}
