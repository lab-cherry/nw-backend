package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.BookmarkEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.BookmarkRepository;
import lab.cherry.nw.service.BookmarkService;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * <pre>
 * ClassName : BookmarkServiceImpl
 * Type : class0.20
 * Description : 북마크와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("BookmarkServiceImpl")
@Transactional
@RequiredArgsConstructor
public class BookmarkServiceImpl implements BookmarkService {

    private final BookmarkRepository bookmarkRepository;
    private final UserService userService;
    /**
     * [BookmarkServiceImpl] 전체 북마크 조회 함수
     *
     * @return DB에서 전체 북마크 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 북마크 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 북마크를 조회하여, 북마크 목록을 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<BookmarkEntity> getBookmarks(Pageable pageable) {
    return bookmarkRepository.findAll(pageable);
    //        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Roles Not Found");
}


//
    /**
     * [BookmarkServiceImpl] 북마크 생성 함수
     *
     * @param bookmarkCreateDto 북마크 생성에 필요한 북마크 등록 정보를 담은 개체입니다.
     * @return 생성된 북마크 정보를 리턴합니다.
     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 북마크를 등록합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    public void createBookmark(BookmarkEntity.BookmarkCreateDto bookmarkCreateDto) {
        Instant instant = Instant.now();
		checkExistsWithBookmarkName(bookmarkCreateDto.getUserSeq());
        UserEntity userEntity = userService.findById(bookmarkCreateDto.getUserSeq());
        BookmarkEntity bookmarkEntity = BookmarkEntity.builder()
			.id(bookmarkCreateDto.getUserSeq())
            .userid(userEntity)
            .data(bookmarkCreateDto.getData())
            .created_at(instant)
            .build();
        bookmarkRepository.save(bookmarkEntity);
    }

    /**
     * [BookmarkServiceImpl] 북마크 수정 함수
     *
     * @param id 조회할 북마크의 고유번호입니다.
     * @param bookmarkUpdateDto 북마크 수정에 필요한 사용자 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 북마크 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 북마크 대해 북마크 정보를 수정합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    public void updateById(String id, BookmarkEntity.BookmarkUpdateDto bookmarkUpdateDto) {
        BookmarkEntity bookmarkEntity = findById(id);

        if (bookmarkEntity.getData() != null ) {
            log.error("bookmarkEntity : {} ", bookmarkEntity);
            log.error("bookmarkUpdateDto.getData() : {} ", bookmarkUpdateDto.getData());
            bookmarkEntity.updateFromDto(bookmarkUpdateDto);
            bookmarkRepository.save(bookmarkEntity);


        } else {
            log.error("[BookmarkServiceImpl - udpateBookmark] data 만 수정 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }
//	/**
//     * [BookmarkServiceImpl] 북마크 수정 함수
//     *
//     * @param userid 조회할 유저의 고유번호입니다.
//     * @param bookmarkUpdateDto 북마크 수정에 필요한 사용자 정보를 담은 개체입니다.
//     * @throws EntityNotFoundException 북마크 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 특정 북마크에 대해 북마크 정보를 수정합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    public void updateByUserId(String userid, BookmarkEntity.UpdateDto bookmarkUpdateDto) {
//		ObjectId objectId = new ObjectId(userid);
//		BookmarkEntity bookmarkEntity = findByUserId(objectId);
//
//		if (bookmarkEntity.getData() != null ) {
//			log.error("bookmarkEntity : {} ", bookmarkEntity);
//			log.error("bookmarkUpdateDto.getData() : {} ", bookmarkUpdateDto.getData());
//			bookmarkEntity.updateFromDto(bookmarkUpdateDto);
//			bookmarkRepository.save(bookmarkEntity);
//
//
//		} else {
//			log.error("[BookmarkServiceImpl - udpateBookmark] data 만 수정 가능합니다.");
//			throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
//		}
//	}
	//
//
    /**
     * [BookmarkServiceImpl] 북마크 삭제 함수
     *
     * @param id 삭제할 북마크의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 북마크 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 북마크 정보를 삭제합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
     public void deleteById(String id) {
        bookmarkRepository.delete(bookmarkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with Id " + id + " Not Found.")));
    }



//    /**
//     * [BookmarkServiceImpl] 북마크 이름 중복 체크 함수
//     *
//     * @param name 중복 체크에 필요한 북마크 이름 객체입니다.
//     * @throws CustomException 북마크의 이름이 중복된 경우 예외 처리 발생
//     * <pre>
//     * 입력된 북마크 이름으로 이미 등록된 북마크이 있는지 확인합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
    @Transactional(readOnly = true)
    public void checkExistsWithBookmarkName(String id) {
		ObjectId objectId = new ObjectId(id);
//		log.error("error1 : {} " ,bookmarkRepository.findByUserid(objectId));
//		log.error("error2 : {} " ,bookmarkRepository.findByUserid(objectId).isPresent());
	if (bookmarkRepository.findByUserid(objectId).isPresent()) {
            throw new CustomException(ErrorCode.DUPLICATE); // 북마크생성 유저가 중복됨
        }
    }
//
//    /**
//     * [BookmarkServiceImpl] NAME으로 북마크 조회 함수
//     *
//     * @param name 조회할 북마크의 이름입니다.
//     * @return 주어진 이름에 해당하는 북마크 정보를 리턴합니다.
//     * @throws EntityNotFoundException 해당 이름의 북마크 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 입력한 name에 해당하는 북마크 정보를 조회합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public BookmarkEntity findByName(String name) {
//        return bookmarkRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Bookmark with Name " + name + " Not Found."));
//    }
//
    /**
     * [BookmarkServiceImpl] ID로 북마크 조회 함수
     *
     * @param id 조회할 북마크의 식별자입니다.
     * @return 주어진 식별자에 해당하는 북마크 정보
     * @throws EntityNotFoundException 해당 ID의 북마크 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 북마크 정보를 조회합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    public BookmarkEntity findById(String id) {
        return bookmarkRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Bookmark with Id " + id + " Not Found."));
    }

    @Transactional(readOnly = true)
    public BookmarkEntity findByUserId(ObjectId userId) {
        return bookmarkRepository.findByUserid(userId).orElseThrow(() -> new EntityNotFoundException("Bookmark with User id " + userId.toString() + " Not Found."));
    }
    
    @Transactional(readOnly = true)
    public Page<BookmarkEntity> findPageByUserId(String userid, Pageable pageable) {
        return bookmarkRepository.findPageByUserid(userid, pageable);
    }
    
}
