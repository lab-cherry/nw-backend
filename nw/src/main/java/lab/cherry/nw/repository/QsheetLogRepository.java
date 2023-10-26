package lab.cherry.nw.repository;

import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import lab.cherry.nw.model.QsheetLogEntity;


public interface QsheetLogRepository extends MongoRepository<QsheetLogEntity, String> {
    @Query("{'qsheet.$_id' : ?0}")
    List<QsheetLogEntity> findByQsheetId(ObjectId qsheetId);
}