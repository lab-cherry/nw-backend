package lab.cherry.nw.service;

import lab.cherry.nw.model.QsheetEntity;
import org.springframework.stereotype.Component;
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
    List<QsheetEntity> getQsheets();
    QsheetEntity createQsheet(QsheetEntity.CreateDto qsheetCreateDto);
    void updateQsheet(QsheetEntity org);
    void deleteQsheet(Long id);
    QsheetEntity findById(Long id);
    QsheetEntity findByName(String name);
}
