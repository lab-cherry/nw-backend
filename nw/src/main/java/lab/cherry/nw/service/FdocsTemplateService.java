package lab.cherry.nw.service;

import lab.cherry.nw.model.FdocsTemplateEntity;
import lab.cherry.nw.model.FinaldocsEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * ClassName : FinaldocsService
 * Type : interface
 * Description : 최종확인서와 관련된 함수를 정리한 인터페이스입니다.
 * Related : FinaldocsController, FinaldocsServiceImpl
 * </pre>
 */
@Component
public interface FdocsTemplateService {
    Page<FdocsTemplateEntity> getFdocsTemplate(Pageable pageable);
    FdocsTemplateEntity createFdocsTemplate(FdocsTemplateEntity.CreateDto fdocsTemplateCreateDto);
//    void updateById(String id, FinaldocsEntity.UpdateDto org);
    FdocsTemplateEntity findById(String id);
    FdocsTemplateEntity findByName(String name);
    void deleteById(String id);
    Page<FdocsTemplateEntity> findPageByName(String name, Pageable pageable);
    Page<FdocsTemplateEntity> findPageById(String id, Pageable pageable);
}
