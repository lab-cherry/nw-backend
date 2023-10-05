package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.TagEntity;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.TagRepository;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.TagService;
import lab.cherry.nw.service.QsheetService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * <pre>
 * ClassName : TagServiceImpl
 * Type : class0.20
 * Description : 게시판 태그와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("TagServiceImpl")
@Transactional
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {

	private final TagRepository tagRepository;

	/**
     * [TagServiceImpl] 전체 게시판 태그 조회 함수
     *
     * @return DB에서 전체 게시판 태그 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 게시판 태그 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 게시판 태그를 조회하여, 게시판 태그 목록을 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    public Page<TagEntity> getTags(Pageable pageable) {
		return tagRepository.findAll(pageable);
    //        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Roles Not Found");
}


//
    /**
     * [TagServiceImpl] 게시판 태그 생성 함수
     *
     * @param tagCreateDto 게시판 태그 생성에 필요한 게시판 태그 등록 정보를 담은 개체입니다.
     * @return 생성된 게시판 태그 정보를 리턴합니다.
//     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 게시판 태그을 등록합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    public void createTag(TagEntity.TagCreateDto tagCreateDto) {
		checkExistsWithTagName(tagCreateDto.getName());
        TagEntity tagEntity = TagEntity.builder()
			.name(tagCreateDto.getName())
            .build();
		tagRepository.save(tagEntity);
    }

    /**
     * [TagServiceImpl] 게시판 태그 수정 함수
     *
     * @param id 조회할 게시판 태그의 고유번호입니다.
     * @param TagUpdateDto 게시판 태그 수정에 필요한 사용자 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 사용자 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 사용자에 대해 사용자 정보를 수정합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */

//    /**
//     * [TagServiceImpl] 게시판 태그 이름 중복 체크 함수
//     *
//     * @param name 중복 체크에 필요한 게시판 태그 이름 객체입니다.
//     * @throws CustomException 게시판 태그의 이름이 중복된 경우 예외 처리 발생
//     * <pre>
//     * 입력된 게시판 태그 이름으로 이미 등록된 게시판 태그이 있는지 확인합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public void checkExistsWithTagName(String name) {
//        if (TagRepository.findByName(name).isPresent()) {
//            throw new CustomException(ErrorCode.DUPLICATE); // 게시판 태그 이름이 중복됨
//        }
//    }
//
//    /**
//     * [TagServiceImpl] NAME으로 게시판 태그 조회 함수
//     *
//     * @param name 조회할 게시판 태그의 이름입니다.
//     * @return 주어진 이름에 해당하는 게시판 태그 정보를 리턴합니다.
//     * @throws EntityNotFoundException 해당 이름의 게시판 태그 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 입력한 name에 해당하는 게시판 태그 정보를 조회합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public TagEntity findByName(String name) {
//        return TagRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Tag with Name " + name + " Not Found."));
//    }
//
    /**
     * [TagServiceImpl] ID로 게시판 태그 조회 함수
     *
     * @param name 조회할 게시판 태그의 식별자입니다.
     * @return 주어진 식별자에 해당하는 게시판 태그 정보
     * @throws EntityNotFoundException 해당 ID의 게시판 태그 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 게시판 태그 정보를 조회합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    public TagEntity findByName(String name) {
		return tagRepository.findById(name).orElseThrow(() -> new EntityNotFoundException("Tag with Name " + name + " Not Found."));
    }
//
//    @Transactional(readOnly = true)
//    public Page<TagEntity> findPageByUserId(String userid, Pageable pageable) {
//        return TagRepository.findPageByUserid(userid, pageable);
//    }

	public void checkExistsWithTagName(String name) {

		if (tagRepository.findByName(name).isPresent()) {
			throw new CustomException(ErrorCode.DUPLICATE); // 북마크생성 유저가 중복됨
		}
	}
}
