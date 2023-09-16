package lab.cherry.nw.service.Impl;

import io.minio.errors.MinioException;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.WeddinghallEntity;
import lab.cherry.nw.repository.WeddinghallRepository;
import lab.cherry.nw.service.FileService;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.WeddinghallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
     * 역할을 등록합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public WeddinghallEntity createWeddinghall(WeddinghallEntity.CreateDto weddinghallCreateDto, List<MultipartFile> imageFiles) {
		
		log.error("[#0] in createWeddinghall");
		
		String orgName = weddinghallCreateDto.getOrg();
		OrgEntity orgEntity = orgService.findByName(orgName);
		
		// 파일 업로드 시, 구분하기 위한 정보 입력
		// {org_objectId}/웨딩홀/{weddinghallName}/profile.jpg
		// {org_objectId}/고객/{userName}/문서.xlsx
		Map<String, String> info = new HashMap<>();
		info.put("name", weddinghallCreateDto.getName());
		info.put("org", weddinghallCreateDto.getOrg());
        List<String> fileObjectIds = null;
        try {
            fileObjectIds = fileService.uploadFiles(info, imageFiles);
        } catch (IOException | MinioException | InvalidKeyException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }

        log.error("[#1] imageFileIds = {}", fileObjectIds);
		
		log.error("[#2] fileService.getFileByName = {}", fileService.getFileByName(weddinghallCreateDto.getName()));
//		
//		// 이미지의 DBRef를 WeddinghallEntity에 추가
//		List<GridFSFile> images = new ArrayList<>(); // 이미지 목록을 초기화
//		for (String fileObjectId : fileObjectIds) {
//			log.error("[#3] {}", fileService.getFileById(fileObjectId));
//			Optional<GridFSFile> image = fileService.getFileById(fileObjectId);
//		    if (image != null) {
//		        images.add(image.get());
//			}
//		}
//
//		WeddinghallEntity weddinghallEntity = WeddinghallEntity.builder()
//			.id(objectId.toString())
//            .name(weddinghallCreateDto.getName())
//			.max_person(weddinghallCreateDto.getMaxPerson())
//			.org(orgRepository.findByName(weddinghallCreateDto.getOrg()).get())
//			.interval(weddinghallCreateDto.getInterval())
//			.images(images)
//            .created_at(Instant.now())
//            .build();
//		
//		return weddinghallRepository.save(weddinghallEntity);
		return null;
	}

	@Transactional(readOnly = true)
    public Page<WeddinghallEntity> findPageByName(String name, Pageable pageable) {
		return weddinghallRepository.findPageByName(name, pageable);
	}
	
}