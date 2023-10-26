package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.BanquetEntity;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.repository.BanquetRepository;
import lab.cherry.nw.service.BanquetService;
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
 * ClassName : BanquetServiceImpl
 * Type : class
 * Description : 연회장과 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
@Slf4j
@Service("banquetServiceImpl")
@Transactional
@RequiredArgsConstructor
public class BanquetServiceImpl implements BanquetService {
	
	private final BanquetRepository banquetRepository;
	private final FileService fileService;
	private final OrgService orgService;
	
	/**
     * [BanquetServiceImpl] 전체 연회장 조회 함수
     *
     * @return DB에서 전체 연회장 정보 목록을 리턴합니다.
     * <pre>
     * 전체 연회장을 조회하여, 연회장 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<BanquetEntity> getBanquets(Pageable pageable) {
		return banquetRepository.findAll(pageable);
		//        return EntityNotFoundException.requireNotEmpty(banquetRepository.findAll(pageable), "Banquets Not Found");
	}
	

	/**
     * [BanquetServiceImpl] 연회장 생성 함수
     *
     * @param banquetCreateDto 연회장 생성에 필요한 역할 등록 정보를 담은 개체입니다.
     * @return 생성된 연회장 정보를 리턴합니다.
     * <pre>
     * 연회장을 등록합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public BanquetEntity createBanquet(BanquetEntity.BanquetCreateDto banquetCreateDto, List<MultipartFile> images) {
		
		log.error("[#0] in createBanquet");
		
		checkExistsWithBanquetName(banquetCreateDto.getBanquetName());	// 중복 체크

		String orgId = banquetCreateDto.getOrgId();
		OrgEntity orgEntity = orgService.findById(orgId);
    ObjectId objectId = new ObjectId();
		
    Map<String, String> info = new HashMap<>();
      info.put("org", orgEntity.getName());
      info.put("type", "연회장");
      // info.put("username", "");
      info.put("kind", banquetCreateDto.getBanquetName());
      info.put("seq", objectId.toString());
    
    List<String> imageUrls = fileService.uploadFiles(info, images);
		
		BanquetEntity banquetEntity = BanquetEntity.builder()
        .id(objectId.toString())
        .name(banquetCreateDto.getBanquetName())
        .max_person(banquetCreateDto.getMaxPerson())
        .org(orgEntity)
        .interval(banquetCreateDto.getInterval())
        .images(imageUrls)
        .created_at(Instant.now())
        .build();

		return banquetRepository.save(banquetEntity);
	}

	@Transactional(readOnly = true)
    public Page<BanquetEntity> findPageByName(String name, Pageable pageable) {
		return banquetRepository.findPageByName(name, pageable);
	}

	/**
     * [BanquetServiceImpl] 연회장 이름 중복 체크 함수
     *
     * @param name 중복 체크에 필요한 연회장 이름 객체입니다.
     * @throws CustomException 연회장의 이름이 중복된 경우 예외 처리 발생
     * <pre>
     * 입력된 연회장 이름으로 이미 등록된 연회장이 있는지 확인합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public void checkExistsWithBanquetName(String name) {
		if (banquetRepository.findByName(name).isPresent()) {
			throw new CustomException(ErrorCode.DUPLICATE); // 조직 이름이 중복됨
		}
	}

	/**
     * [BanquetServiceImpl] ID로 연회장 조회 함수
     *
     * @param id 조회할 연회장의 식별자입니다.
     * @return 주어진 식별자에 해당하는 연회장 정보
     * @throws EntityNotFoundException 해당 ID의 연회장 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 연회장 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public BanquetEntity findById(String id) {
		return banquetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Banquet with Id " + id + " Not Found."));
	}
	

	/**
     * [BanquetServiceImpl] 연회장 삭제 함수
     *
     * @param id 삭제할 연회장의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 연회장 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 연회장 정보를 삭제합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void deleteById(String id) {

      BanquetEntity banquetEntity = findById(id);
      fileService.deleteFiles(banquetEntity.getName(), banquetEntity.getImages());

      banquetRepository.delete(banquetRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Banquet with Id " + id + " Not Found.")));
	}

}