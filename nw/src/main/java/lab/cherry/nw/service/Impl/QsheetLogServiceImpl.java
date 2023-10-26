package lab.cherry.nw.service.Impl;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.model.QsheetHistoryEntity;
import lab.cherry.nw.model.QsheetLogEntity;
import lab.cherry.nw.repository.QsheetLogRepository;
import lab.cherry.nw.service.QsheetLogService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : QsheetServiceImpl
 * Type : class0.20
 * Description : 큐시트와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("qsheetLogServiceImpl")
@Transactional
@RequiredArgsConstructor
public class QsheetLogServiceImpl implements QsheetLogService {

    private final QsheetLogRepository qsheetLogRepository;
    public void createQsheetLog(String method, QsheetEntity qsheetEntity) {
        QsheetLogEntity qsheetLogEntity = QsheetLogEntity.builder()
            .method(method)
            .qsheet(QsheetEntity.builder()
                .id(qsheetEntity.getId())
                .name(qsheetEntity.getName())
                .orgid(qsheetEntity.getOrgid())
                .userid(qsheetEntity.getUserid())
                .created_at(qsheetEntity.getCreated_at())
                .data(qsheetEntity.getData())
                .org_approver(qsheetEntity.getOrg_approver())
                .org_confirm(qsheetEntity.isOrg_confirm())
                .client_confirm(qsheetEntity.isClient_confirm())
                .memo(qsheetEntity.getMemo())
                .created_at(qsheetEntity.getCreated_at())
                .updated_at(qsheetEntity.getUpdated_at())
            .build())
            .build();
        qsheetLogRepository.save(qsheetLogEntity);
    }

    @Transactional(readOnly = true)
    @Override
    public List<QsheetLogEntity> findByQsheetId(ObjectId qsheeObjectId) {
        return EntityNotFoundException.requireNotEmpty(qsheetLogRepository.findByQsheetId(qsheeObjectId), "QsheetId Not Found");
    }
}
