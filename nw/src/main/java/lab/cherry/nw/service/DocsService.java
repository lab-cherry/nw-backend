package lab.cherry.nw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import lab.cherry.nw.model.DocsEntity;

/**
 * <pre>
 * ClassName : DocsService
 * Type : interface
 * Description : 문서 관리와 관련된 함수를 정리한 인터페이스입니다.
 * Related : DocsController, DocsServiceiml
 * </pre>
 */
@Component
public interface DocsService {
 Page<DocsEntity> getDocs(Pageable pageable);
 DocsEntity findById(String id);
 DocsEntity createDocs(DocsEntity.DocsCreateDto docsCreateDto);
 void updateById(String id, DocsEntity.DocsUpdateDto docsUpdateDto);
 void deleteById(String id);
}