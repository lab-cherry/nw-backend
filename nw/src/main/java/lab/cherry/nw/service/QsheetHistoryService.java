package lab.cherry.nw.service;

import lab.cherry.nw.model.BookmarkEntity;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.model.QsheetHistoryEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * ClassName : QsheetService
 * Type : interface
 * Description : 큐시트와 관련된 함수를 정리한 인터페이스입니다.
 * Related : QsheetController, QsheetServiceiml
 * </pre>
 */
@Component
public interface QsheetHistoryService {
  Page<QsheetHistoryEntity> getQsheetHistorys(Pageable pageable);
  QsheetHistoryEntity findById(String id);
  // QsheetHistoryEntity findByUserId(ObjectId userId);
  Page<QsheetHistoryEntity> findPageByUserId(String userid, Pageable pageable);
  void createQsheetHistory(QsheetEntity qsheetEntity, QsheetEntity.QsheetUpdateDto qsheetUpdateDto);
//  Page<QsheetEntity> findPageByUserId(String userid, Pageable pageable);
//  Page<QsheetEntity> findPageByOrgId(String orgid, Pageable pageable);
// void updateOrgById(String id, List<String> orgIds);
// byte[] download(List<String> users);
}