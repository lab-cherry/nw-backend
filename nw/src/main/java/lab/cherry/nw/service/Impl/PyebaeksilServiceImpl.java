package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.PyebaeksilEntity;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.repository.PyebaeksilRepository;
import lab.cherry.nw.service.PyebaeksilService;
import lab.cherry.nw.service.FileService;
import lab.cherry.nw.service.OrgService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <pre>
 * ClassName : PyebaeksilServiceImpl
 * Type : class
 * Description : 연회장과 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
@Slf4j
@Service("pyebaeksilServiceImpl")
@Transactional
@RequiredArgsConstructor
public class PyebaeksilServiceImpl implements PyebaeksilService {
	
	private final PyebaeksilRepository pyebaeksilRepository;
	private final FileService fileService;
	private final OrgService orgService;
	
	/**
     * [PyebaeksilServiceImpl] 전체 폐백실 조회 함수
     *
     * @return DB에서 전체 폐백실 정보 목록을 리턴합니다.
     * <pre>
     * 전체 폐백실을 조회하여, 폐백실 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<PyebaeksilEntity> getPyebaeksils(Pageable pageable) {
		return pyebaeksilRepository.findAll(pageable);
		//        return EntityNotFoundException.requireNotEmpty(banquetRepository.findAll(pageable), "Banquets Not Found");
	}
	

	/**
     * [PyebaeksilServiceImpl] 폐백실 생성 함수
     *
     * @param pyebaeksilCreateDto 폐백실 생성에 필요한 역할 등록 정보를 담은 개체입니다.
     * @return 생성된 폐백실 정보를 리턴합니다.
     * <pre>
     * 폐백실을 등록합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public PyebaeksilEntity createPyebaeksil(PyebaeksilEntity.PyebaeksilCreateDto pyebaeksilCreateDto, List<MultipartFile> images) {
		
		log.error("[#0] in createPyebaeksil");
		
		checkExistsWithPyebaeksilName(pyebaeksilCreateDto.getPyebaeksilName());	// 중복 체크

		String orgId = pyebaeksilCreateDto.getOrgId();
		OrgEntity orgEntity = orgService.findById(orgId);
    ObjectId objectId = new ObjectId();
		
		// 파일 업로드 시, 구분하기 위한 정보 입력
		// {org_objectId}/웨딩홀/{weddinghallName}/profile.jpg
		// {org_objectId}/고객/{userName}/문서.xlsx
		Map<String, String> info = new HashMap<>();
    info.put("org", orgEntity.getName());
    info.put("type", "폐백실");
    // info.put("username", "");
    info.put("kind", pyebaeksilCreateDto.getPyebaeksilName());
    info.put("seq", objectId.toString());

		// 업로드한 파일의 ObjectId 를 List로 반환
    List<String> imageUrls = fileService.uploadFiles(info, images);
		
		PyebaeksilEntity pyebaeksilEntity = PyebaeksilEntity.builder()
        .id(objectId.toString())
        .name(pyebaeksilCreateDto.getPyebaeksilName())
        .org(orgEntity)
        .images(imageUrls)
        .created_at(Instant.now())
        .build();

		return pyebaeksilRepository.save(pyebaeksilEntity);
	}

	@Transactional(readOnly = true)
    public Page<PyebaeksilEntity> findPageByName(String name, Pageable pageable) {
		return pyebaeksilRepository.findPageByName(name, pageable);
	}

	/**
     * [PyebaeksilServiceImpl] 폐백실 이름 중복 체크 함수
     *
     * @param name 중복 체크에 필요한 폐백실 이름 객체입니다.
     * @throws CustomException 폐백실의 이름이 중복된 경우 예외 처리 발생
     * <pre>
     * 입력된 폐백실 이름으로 이미 등록된 폐백실이 있는지 확인합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public void checkExistsWithPyebaeksilName(String name) {
		if (pyebaeksilRepository.findByName(name).isPresent()) {
			throw new CustomException(ErrorCode.DUPLICATE); // 조직 이름이 중복됨
		}
	}

	/**
     * [PyebaeksilServiceImpl] ID로 폐백실 조회 함수
     *
     * @param id 조회할 폐백실의 식별자입니다.
     * @return 주어진 식별자에 해당하는 폐백실 정보
     * @throws EntityNotFoundException 해당 ID의 폐백실 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 폐백실 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public PyebaeksilEntity findById(String id) {
		return pyebaeksilRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pyebaeksil with Id " + id + " Not Found."));
	}
	

	/**
     * [PyebaeksilServiceImpl] 폐백실 삭제 함수
     *
     * @param id 삭제할 폐백실의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 폐백실 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 폐백실 정보를 삭제합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void deleteById(String id) {

    PyebaeksilEntity pyebaeksilEntity = findById(id);
    fileService.deleteFiles(pyebaeksilEntity.getName(), pyebaeksilEntity.getImages());

		pyebaeksilRepository.delete(pyebaeksilRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Pyebaeksil with Id " + id + " Not Found.")));
	}

}