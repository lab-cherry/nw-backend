package lab.cherry.nw.service.Impl;

import java.time.Instant;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.BoardEntity;
import lab.cherry.nw.model.QsheetEntity;
import lab.cherry.nw.model.TagEntity;
import lab.cherry.nw.model.TagEntity.TagCreateDto;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.BoardRepository;
import lab.cherry.nw.repository.TagRepository;
import lab.cherry.nw.service.BoardService;
import lab.cherry.nw.service.QsheetService;
import lab.cherry.nw.service.TagService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : BoardServiceImpl
 * Type : class0.20
 * Description : 게시물와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("boardServiceImpl")
@Transactional
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;
    private final UserService userService;
	private final QsheetService qsheetService;
    private final TagService tagService;

	/**
     * [BoardServiceImpl] 전체 게시물 조회 함수
     *
     * @return DB에서 전체 게시물 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 게시물 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 게시물을 조회하여, 게시물 목록을 반환합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<BoardEntity> getBoards(Pageable pageable) {
    return boardRepository.findAll(pageable);
    //        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Roles Not Found");
}


//
    /**
     * [BoardServiceImpl] 게시물 생성 함수
     *
     * @param boardCreateDto 게시물 생성에 필요한 게시물 등록 정보를 담은 개체입니다.
     * @return 생성된 게시물 정보를 리턴합니다.
//     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 게시물을 등록합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    public void createBoard(BoardEntity.BoardCreateDto boardCreateDto) {
        Instant instant = Instant.now();
        UserEntity userEntity = userService.findById(boardCreateDto.getUserSeq());
		QsheetEntity qsheetEntity= null;
        if(boardCreateDto.getQsheetSeq()!=null){
            qsheetService.findById(boardCreateDto.getQsheetSeq());
        }
        List<TagEntity> tagList =  new LinkedList<>();
        if(boardCreateDto.getTagList() !=null){
            for(String tag : boardCreateDto.getTagList()){
                TagEntity tagEntity = tagService.findByName(tag);
                if(tagEntity !=null){
                    tagList.add(tagEntity);
                }else{
                    TagEntity.TagCreateDto tagCreateDto=TagCreateDto.builder()
                    .name(tag)
                    .build();
                    tagService.createTag(tagCreateDto);
                    TagEntity newTagEntity = tagService.findByName(tag);
                    tagList.add(newTagEntity);
                }
            }
        }
        BoardEntity boardEntity = BoardEntity.builder()
            .user(userEntity)
			.content(boardCreateDto.getContent())
			.qsheet(qsheetEntity)
            .tag(tagList)
//            .name(boardCreateDto.getName())
//            .data(boardCreateDto.getData())
            .created_at(instant)
            .build();
        boardRepository.save(boardEntity);
    }

    /**
     * [BoardServiceImpl] 게시물 수정 함수
     *
     * @param id 조회할 게시물의 고유번호입니다.
     * @param boardUpdateDto 게시물 수정에 필요한 사용자 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 사용자 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 게시물에 대해 정보를 수정합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    public void updateById(String id, BoardEntity.BoardUpdateDto boardUpdateDto) {
        Instant instant = Instant.now();
        BoardEntity boardEntity = findById(id);
        
		

        List<TagEntity> tagList =  new LinkedList<>();
        if(boardUpdateDto.getTagList() !=null){
            for(String tag : boardUpdateDto.getTagList()){
                TagEntity tagEntity = tagService.findByName(tag);
                if(tagEntity !=null){
                    tagList.add(tagEntity);
                }else{
                    TagEntity.TagCreateDto tagCreateDto=TagCreateDto.builder()
                    .name(tag)
                    .build();
                    tagService.createTag(tagCreateDto);
                    TagEntity newTagEntity = tagService.findByName(tag);
                    tagList.add(newTagEntity);
                }
            }
        }else{
            tagList=boardEntity.getTag();
        }

		if (boardEntity.getContent() != null || boardEntity.getQsheet()!=null|| boardEntity.getTag()!= null) {
			boardEntity = BoardEntity.builder()
			.id(boardEntity.getId())
			.user(boardEntity.getUser())
			.content(boardUpdateDto.getContent() != null ? boardUpdateDto.getContent():boardEntity.getContent() )
			.qsheet(boardUpdateDto.getQsheetSeq()!=null? qsheetService.findById(boardUpdateDto.getQsheetSeq()) : boardEntity.getQsheet())
			.tag(tagList)
            .created_at(boardEntity.getCreated_at())
			.updated_at(instant)
			.build();
            boardRepository.save(boardEntity);


        } else {
            log.error("[BoardServiceImpl - udpateBoard] data 만 수정 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    /**
     * [BoardServiceImpl] 게시물 삭제 함수
     *
     * @param id 삭제할 게시물의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 게시물 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 게시물 정보를 삭제합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
     public void deleteById(String id) {
        boardRepository.delete(boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("User with Id " + id + " Not Found.")));
    }



//    /**
//     * [BoardServiceImpl] 게시물 이름 중복 체크 함수
//     *
//     * @param name 중복 체크에 필요한 게시물 이름 객체입니다.
//     * @throws CustomException 게시물의 이름이 중복된 경우 예외 처리 발생
//     * <pre>
//     * 입력된 게시물 이름으로 이미 등록된 게시물이 있는지 확인합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public void checkExistsWithBoardName(String name) {
//        if (boardRepository.findByName(name).isPresent()) {
//            throw new CustomException(ErrorCode.DUPLICATE); // 게시물 이름이 중복됨
//        }
//    }
//
//    /**
//     * [BoardServiceImpl] NAME으로 게시물 조회 함수
//     *
//     * @param name 조회할 게시물의 이름입니다.
//     * @return 주어진 이름에 해당하는 게시물 정보를 리턴합니다.
//     * @throws EntityNotFoundException 해당 이름의 게시물 정보가 없을 경우 예외 처리 발생
//     * <pre>
//     * 입력한 name에 해당하는 게시물 정보를 조회합니다.
//     * </pre>
//     *
//     * Author : yby654(yby654@github.com)
//     */
//    @Transactional(readOnly = true)
//    public BoardEntity findByName(String name) {
//        return boardRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Board with Name " + name + " Not Found."));
//    }
//
    /**
     * [BoardServiceImpl] ID로 게시물 조회 함수
     *
     * @param id 조회할 게시물의 식별자입니다.
     * @return 주어진 식별자에 해당하는 게시물 정보
     * @throws EntityNotFoundException 해당 ID의 게시물 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 게시물 정보를 조회합니다.
     * </pre>
     *
     * Author : yby654(yby654@github.com)
     */
    @Transactional(readOnly = true)
    public BoardEntity findById(String id) {
        return boardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Board with Id " + id + " Not Found."));
    }

    @Transactional(readOnly = true)
    public Page<BoardEntity> findPageByUserId(String userid, Pageable pageable) {
        return boardRepository.findPageByUserid(userid, pageable);
    }

    
}