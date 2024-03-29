package lab.cherry.nw.service;

import java.util.List;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import lab.cherry.nw.model.QsheetEntity;

/**
 * <pre>
 * ClassName : QsheetService
 * Type : interface
 * Description : 큐시트와 관련된 함수를 정리한 인터페이스입니다.
 * Related : QsheetController, QsheetServiceiml
 * </pre>
 */
@Component
public interface QsheetService {
 Page<QsheetEntity> getQsheets(Pageable pageable);
//  @PreAuthorize("hasAnyRole('ADMIN','ORG')")
 QsheetEntity findById(String id);
 QsheetEntity createQsheet(QsheetEntity.QsheetCreateDto qsheetCreateDto, List<MultipartFile> files);
 void updateById(String id, QsheetEntity.QsheetUpdateDto updateDto, List<MultipartFile> files);
 void deleteById(String id);
 Page<QsheetEntity> findPageByUserId(String userSeq, Pageable pageable);
 Page<QsheetEntity> findPageByOrgId(String orgSeq, String type, Pageable pageable);
// void updateOrgById(String id, List<String> orgIds);
Map<String, Object> download(String[] qsheetSeq);
List<QsheetEntity.ItemData> findByIdWithData(String id);
}