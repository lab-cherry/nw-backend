package lab.cherry.nw.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import lab.cherry.nw.model.QsheetEntity;
import java.util.List;


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
 Page<QsheetEntity> getUsers(Pageable pageable);
// QsheetEntity findById(String id);
// QsheetEntity findByUserId(String userid);
// void deleteById(String id);
// Page<QsheetEntity> findPageByUserId(String userid, Pageable pageable);
// Page<QsheetEntity> findPageByOrgId(String orgid, Pageable pageable);

// void updateOrgById(String id, List<String> orgIds);
}
