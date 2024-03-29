package lab.cherry.nw.service.Impl;

import java.time.Instant;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.WeddinghallEntity;
import lab.cherry.nw.repository.WeddinghallRepository;
import lab.cherry.nw.service.FileService;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.WeddinghallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : WeddinghallServiceImpl
 * Type : class
 * Description : 웨딩홀(예식장)과 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-mongo
 * </pre>
 */
@Slf4j
@Service("weddinghallServiceImpl")
@Transactional
@RequiredArgsConstructor
public class WeddinghallServiceImpl implements WeddinghallService {
	
	private final WeddinghallRepository weddinghallRepository;
	private final FileService fileService;
	private final OrgService orgService;
	
	/**
     * [WeddinghallServiceImpl] 전체 웨딩홀(예식장) 조회 함수
     *
     * @return DB에서 전체 웨딩홀(예식장) 정보 목록을 리턴합니다.
     * <pre>
     * 전체 웨딩홀(예식장)를 조회하여, 웨딩홀(예식장) 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<WeddinghallEntity> getWeddinghalls(Pageable pageable) {
		return weddinghallRepository.findAll(pageable);
		//        return EntityNotFoundException.requireNotEmpty(weddinghallRepository.findAll(pageable), "Weddinghalls Not Found");
	}
	

	/**
     * [WeddinghallServiceImpl] 웨딩홀(예식장) 생성 함수
     *
     * @param weddinghallCreateDto 웨딩홀(예식장) 생성에 필요한 역할 등록 정보를 담은 개체입니다.
     * @return 생성된 웨딩홀(예식장) 정보를 리턴합니다.
     * <pre>
     * 웨딩홀(예식장)을 등록합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public WeddinghallEntity createWeddinghall(WeddinghallEntity.WeddinghallCreateDto weddinghallCreateDto, List<MultipartFile> images) {
		
		checkExistsWithWeddingHallName(weddinghallCreateDto.getWeddinghallName());	// 중복 체크

		String orgId = weddinghallCreateDto.getOrgId();
		OrgEntity orgEntity = orgService.findById(orgId);
    ObjectId objectId = new ObjectId();
    
    List<String> imageUrls = fileService.uploadFiles(objectId.toString(), images);
    
    WeddinghallEntity weddinghallEntity = WeddinghallEntity.builder()
        .id(objectId.toString())
        .name(weddinghallCreateDto.getWeddinghallName())
        .max_person(weddinghallCreateDto.getMaxPerson())
        .org(orgEntity)
        .interval(weddinghallCreateDto.getInterval())
        .images(imageUrls)
        .created_at(Instant.now())
        .build();

		return weddinghallRepository.save(weddinghallEntity);
	}

//
//	/**
//     * [WeddinghallServiceImpl] 웨딩홀(예식장) 이미지 수정 함수
//     *
//     * @param id 조회할 웨딩홀(예식장)의 고유번호입니다.
//     * @param imageFiles 큐시트 수정에 필요한 사용자 정보를 담은 개체입니다.
//     * @throws EntityNotFoundException 사용자 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 특정 웨딩홀(예식장)에 대해 웨딩홀(예식장) 정보를 수정합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    public void updateById(String id, List<MultipartFile> imageFiles) {
//		//        Instant instant = Instant.now();
//		WeddinghallEntity weddinghallEntity = findById(id);
//
//		if (imageFiles != null) {
//			weddinghallEntity = WeddinghallEntity.builder()
//					
//					.build();
//			weddinghallRepository.save(weddinghallEntity);
//		} else {
//			log.error("[QsheetServiceImpl - udpateQsheet] data 만 수정 가능합니다.");
//			throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
//		}
//	}


	@Transactional(readOnly = true)
    public Page<WeddinghallEntity> findPageByName(String name, Pageable pageable) {
		  return weddinghallRepository.findPageByName(name, pageable);
	}

	/**
     * [WeddinghallServiceImpl] 웨딩홀 이름 중복 체크 함수
     *
     * @param name 중복 체크에 필요한 웨딩홀 이름 객체입니다.
     * @throws CustomException 웨딩홀의 이름이 중복된 경우 예외 처리 발생
     * <pre>
     * 입력된 웨딩홀 이름으로 이미 등록된 웨딩홀이 있는지 확인합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public void checkExistsWithWeddingHallName(String name) {
		if (weddinghallRepository.findByName(name).isPresent()) {
			throw new CustomException(ErrorCode.DUPLICATE); // 웨딩홀 이름이 중복됨
		}
	}

	/**
     * [WeddinghallServiceImpl] ID로 웨딩홀(예식장) 조회 함수
     *
     * @param id 조회할 웨딩홀(예식장)의 식별자입니다.
     * @return 주어진 식별자에 해당하는 웨딩홀(예식장) 정보
     * @throws EntityNotFoundException 해당 ID의 웨딩홀(예식장) 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 웨딩홀(예식장) 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public WeddinghallEntity findById(String id) {
		  return weddinghallRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Weddinghall with Id " + id + " Not Found."));
	}

      /**
     * [OrgServiceImpl] NAME으로 웨딩홀 조회 함수
     *
     * @param name 조회할 웨딩홀의 이름입니다.
     * @return 주어진 이름에 해당하는 웨딩홀 정보를 리턴합니다.
     * @throws EntityNotFoundException 해당 이름의 웨딩홀 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 name에 해당하는 웨딩홀 정보를 조회합니다.
     * </pre>
     *
     * Author : haeri(yhoo0020@gamil.com)
     */
    @Transactional(readOnly = true)
    public WeddinghallEntity findByName(String name) {
        return weddinghallRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Weddinghall with Name " + name + " Not Found."));
    }
	

	/**
     * [WeddinghallServiceImpl] 웨딩홀(예식장) 삭제 함수
     *
     * @param id 삭제할 웨딩홀(예식장)의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 웨딩홀(예식장) 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 웨딩홀(예식장) 정보를 삭제합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void deleteById(String id) {

      WeddinghallEntity weddinghallEntity = findById(id);
		  weddinghallRepository.delete(weddinghallRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Weddinghall with Id " + id + " Not Found.")));

	}

}