package lab.cherry.nw.service;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.model.QsheetHistoryEntity;

/**
 * <pre>
 * ClassName : QsheetHistoryService
 * Type : interface
 * Description : 큐시트 히스토리와 관련된 함수를 정리한 인터페이스입니다.
 * Related : QsheetController, QsheetServiceiml
 * </pre>
 */
@Component
public interface QsheetHistoryService {
  Page<QsheetHistoryEntity> getQsheetHistorys(Pageable pageable);
  QsheetHistoryEntity findById(String id);
  // QsheetHistoryEntity findByUserId(ObjectId userId);
  Page<QsheetHistoryEntity> findPageByUserId(String userid, Pageable pageable);
  Page<QsheetHistoryEntity> findPageByQsheetId(String qsheetid, Pageable pageable);
  void createQsheetHistory(QsheetEntity qsheetEntity, QsheetEntity.QsheetUpdateDto qsheetUpdateDto);
  List<QsheetHistoryEntity> findByQsheetId(ObjectId qsheetId);
//  Page<QsheetEntity> findPageByUserId(String userid, Pageable pageable);
//  Page<QsheetEntity> findPageByOrgId(String orgid, Pageable pageable);
// void updateOrgById(String id, List<String> orgIds);
// byte[] download(List<String> users);
}