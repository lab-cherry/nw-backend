package lab.cherry.nw.service;

import lab.cherry.nw.model.QsheetEntity;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

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
 QsheetEntity findById(String id);
// QsheetEntity findByUserId(String userid);
// QsheetEntity findByOrgId(String orgid);
 void createQsheet(QsheetEntity.QsheetCreateDto qsheetCreateDto, List<MultipartFile> files);
 void updateById(String id, QsheetEntity.QsheetUpdateDto updateDto, List<MultipartFile> files);
 void deleteById(String id);
 Page<QsheetEntity> findPageByUserId(String userid, Pageable pageable);
 Page<QsheetEntity> findPageByOrgId(String orgid, Pageable pageable);
// void updateOrgById(String id, List<String> orgIds);
byte[] download(List<String> users);
}