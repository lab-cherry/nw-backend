package lab.cherry.nw.service;

import lab.cherry.nw.model.WeddinghallEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
public interface WeddinghallService {
    Page<WeddinghallEntity> getWeddinghalls(Pageable pageable);
	WeddinghallEntity createWeddinghall(WeddinghallEntity.CreateDto weddinghallCreateDto, List<MultipartFile> files);
//    void updateById(String id, WeddinghallEntity.UpdateDto weddinghall);
//    WeddinghallEntity findById(String id);
//    WeddinghallEntity findByName(String name);
    void deleteById(String id);
    Page<WeddinghallEntity> findPageByName(String name, Pageable pageable);
}
