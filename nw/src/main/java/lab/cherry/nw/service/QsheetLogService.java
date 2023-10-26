package lab.cherry.nw.service;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.model.QsheetLogEntity;

/**
 * <pre>
 * ClassName : QsheetLogService
 * Type : interface
 * Description : 큐시트와 관련된 함수를 정리한 인터페이스입니다.
 * Related : QsheetLogController, QsheetLogServiceiml
 * </pre>
 */
@Component
public interface QsheetLogService {
 void createQsheetLog(String method, QsheetEntity qsheetEntity);
 List<QsheetLogEntity> findByQsheetId(ObjectId qsheetId);
}