package lab.cherry.nw.service;

import lab.cherry.nw.model.FinalTemplEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * ClassName : FinaldocsService
 * Type : interface
 * Description : 최종확인서템플릿과 관련된 함수를 정리한 인터페이스입니다.
 * Related : FinalTemplController, FinalTemplServiceImpl
 * </pre>
 */
@Component
public interface FinalTemplService {
    Page<FinalTemplEntity> getFinalTemplate(Pageable pageable);
    FinalTemplEntity createFinalTemplate(FinalTemplEntity.FinalTemplCreateDto fdocsTemplateCreateDto);
    void updateById(String id, FinalTemplEntity.FinalTemplUpdateDto org);
    FinalTemplEntity findById(String id);
    FinalTemplEntity findByName(String name);
    void deleteById(String id);
    Page<FinalTemplEntity> findPageByName(String name, Pageable pageable);
    Page<FinalTemplEntity> findPageById(String id, Pageable pageable);
}
