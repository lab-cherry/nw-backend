package lab.cherry.nw.service;

import lab.cherry.nw.model.FinaldocsEntity;
import lab.cherry.nw.model.OrgEntity;
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
public interface FinaldocsService {
    Page<FinaldocsEntity> getFinaldocs(Pageable pageable);
    FinaldocsEntity createFinaldocs(FinaldocsEntity.FinaldocsCreateDto finaldocsCreateDto);
    void updateById(String id, FinaldocsEntity.FinaldocsUpdateDto finaldocs);
    FinaldocsEntity findById(String id);
    FinaldocsEntity findByName(String name);
    void deleteById(String id);
    Page<FinaldocsEntity> findPageByName(String name, Pageable pageable);
    Page<FinaldocsEntity> findPageById(String id, Pageable pageable);
}
