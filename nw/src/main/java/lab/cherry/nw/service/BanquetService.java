package lab.cherry.nw.service;

import lab.cherry.nw.model.BanquetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


/**
 * <pre>
 * ClassName : BanquetService
 * Type : interface
 * Description : 연회장와 관련된 함수를 정리한 인터페이스입니다.
 * Related : BanquetController, BanquetServiceImpl
 * </pre>
 */
@Component
public interface BanquetService {
	Page<BanquetEntity> getBanquets(Pageable pageable);
	BanquetEntity createBanquet(BanquetEntity.BanquetCreateDto banquetCreateDto, List<MultipartFile> files);
	//    void updateById(String id, BanquetEntity.BanquetUpdateDto banquet);
    BanquetEntity findById(String id);
    //  BanquetEntity findByName(String name);
    void deleteById(String id);
	Page<BanquetEntity> findPageByName(String name, Pageable pageable);
}
