package lab.cherry.nw.service;

import lab.cherry.nw.model.PyebaeksilEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <pre>
 * ClassName : PyebaeksilService
 * Type : interface
 * Description : 폐백실와 관련된 함수를 정리한 인터페이스입니다.
 * Related : PyebaeksilController, PyebaeksilServiceImpl
 * </pre>
 */
@Component
public interface PyebaeksilService {
	Page<PyebaeksilEntity> getPyebaeksils(Pageable pageable);
	PyebaeksilEntity createPyebaeksil(PyebaeksilEntity.PyebaeksilCreateDto pyebaeksilCreateDto, List<MultipartFile> files);
	//    void updateById(String id, PyebaeksilEntity.PyebaeksilUpdateDto pyebaeksil);
	PyebaeksilEntity findById(String id);
	//  PyebaeksilEntity findByName(String name);
	void deleteById(String id);
	Page<PyebaeksilEntity> findPageByName(String name, Pageable pageable);
}
