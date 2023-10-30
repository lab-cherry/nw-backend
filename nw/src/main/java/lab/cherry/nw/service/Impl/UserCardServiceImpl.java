package lab.cherry.nw.service.Impl;

import java.time.Instant;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.UserCardEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.model.WeddinghallEntity;
import lab.cherry.nw.repository.UserCardRepository;
import lab.cherry.nw.repository.UserRepository;
import lab.cherry.nw.service.EventService;
import lab.cherry.nw.service.UserCardService;
import lab.cherry.nw.service.UserService;
import lab.cherry.nw.service.WeddinghallService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * <pre>
 * ClassName : userCardServiceImpl
 * Type : class
 * Description : 고객카드와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("userCardServiceImpl")
@Transactional
@RequiredArgsConstructor
public class UserCardServiceImpl implements UserCardService {

    private final UserCardRepository userCardRepository;
    private final UserRepository userRepository;
    private final UserService userService;
    private final WeddinghallService weddinghallService;
    private final EventService eventService;

    /**
     * [userCardServiceImpl] 전체 고객카드 조회 함수
     *
     * @return DB에서 전체 고객카드 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 고객카드 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 고객카드을 조회하여, 고객카드 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<UserCardEntity> getUsercards(Pageable pageable) {
        return userCardRepository.findAll(pageable);

    }

    /**
     * [userCardServiceImpl] 고객카드 생성 함수
     * @return 생성된 고객카드 정보를 리턴합니다.
     * <pre>
     * 고객카드 등록합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public UserCardEntity createUserCard(UserCardEntity.UserCardCreateDto userCardCreateDto) {

        UserEntity userEntity = userService.findById(userCardCreateDto.getUserSeq());

        checkExistsWithUserId(userEntity.getId());

        System.err.println("user id "+userEntity.getId());

        WeddinghallEntity weddinghallEntity = weddinghallService.findByName(userCardCreateDto.getWeddinghallName());
        ObjectId objectId = new ObjectId();

        UserCardEntity userCardEntity = UserCardEntity.builder()
            .id(objectId.toString())
            .groom(userCardCreateDto.getGroom())
            .bride(userCardCreateDto.getBride())
            .note(userCardCreateDto.getNote())
            .resDate(userCardCreateDto.getResDate())
            .weddingDate(userCardCreateDto.getWeddingDate())
            .status(userCardCreateDto.getStatus())
            .userinfo(userEntity)
            .weddinghall(weddinghallEntity)
            .created_at(Instant.now())
            .build();

        userCardRepository.save(userCardEntity);

        eventService.createEvent(objectId.toString());

        return userCardEntity;
    }

    /**
     * [userCardServiceImpl] 고객카드 수정 함수
     *

     * @throws EntityNotFoundException 고객카드 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 고객카드에 대한 정보를 수정합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public void updateById(String id, UserCardEntity.UserCardUpdateDto usercard) {

        UserEntity userEntity = userService.findById(usercard.getUserSeq());
        WeddinghallEntity weddinghallEntity = weddinghallService.findByName(usercard.getWeddinghallName());;

        UserCardEntity userCardEntity = findById(id);
        Instant instant = Instant.now();

        if (usercard.getGroom() != null || usercard.getBride() != null || usercard.getNote() != null|| usercard.getStatus() != null|| usercard.getResDate() != null) {

            userCardEntity = UserCardEntity.builder()
                .id(userCardEntity.getId())
                .groom((usercard.getGroom() !=null) ? usercard.getGroom() : userCardEntity.getGroom())
                .bride((usercard.getBride() != null) ? usercard.getBride() : userCardEntity.getBride())
                .note((usercard.getNote() != null) ? usercard.getNote() : userCardEntity.getNote())
                .status((usercard.getStatus() != null) ? usercard.getStatus() : userCardEntity.getStatus())
                .resDate((usercard.getResDate() != null) ? usercard.getResDate() : userCardEntity.getResDate())
                .weddingDate((usercard.getWeddingDate() != null) ? usercard.getWeddingDate() : userCardEntity.getWeddingDate())
                .update_at(instant)
                .userinfo(userEntity)
                .weddinghall(weddinghallEntity)
                .build();

            userCardRepository.save(userCardEntity);

            eventService.createEvent(userCardEntity.getId());

        } else {
            log.error("[userCardServiceImpl - groom, bride, note, status,weddingDate,resDate,만 수정 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }


      /**
     * [userCardServiceImpl] 고객카드 웨딩홀 이름 수정 함수
     *

     * @throws EntityNotFoundException 고객카드 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 고객카드 웨딩홀 이름을 수정합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */

      public void updateWeddinghallByName(String id, String weddinghall) {

        Instant instant = Instant.now();

        UserCardEntity userCardEntity = findById(id);
        
        if (id != null) {
            WeddinghallEntity weddinghallEntity = weddinghallService.findByName(weddinghall);
            
            userCardEntity = UserCardEntity.builder()
            .id(userCardEntity.getId())
            .groom(userCardEntity.getGroom())
            .bride(userCardEntity.getBride())
            .note(userCardEntity.getNote())
            .resDate(userCardEntity.getResDate())
            .weddingDate(userCardEntity.getWeddingDate())
            .status(userCardEntity.getStatus())
            .userinfo(userCardEntity.getUserinfo())
            .weddinghall(weddinghallEntity)
            .created_at(instant)
            .build();

            userCardRepository.save(userCardEntity);

            eventService.createEvent(userCardEntity.getId());

        } else {
			log.error("[UserCardServiceImpl - updateWeddinghallById] weddinghallId 만 입력 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
		}
    }


    /**
     * [userCardServiceImpl] 고객카드 삭제 함수
     *
     * @param id 삭제할 고객카드의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 고객카드 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 고객카드 정보를 삭제합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public void deleteById(String id) {
        
        eventService.deleteById(id);
        userCardRepository.delete(userCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usercard with Id " + id + " Not Found.")));
    }


    /**
     * [userCardServiceImpl] ID로 고객카드 조회 함수
     *
     * @param id 조회할 고객카드의 식별자입니다.
     * @return 주어진 식별자에 해당하는 고객카드 정보
     * @throws EntityNotFoundException 해당 ID의 고객카드 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 고객카드 정보를 조회합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @Transactional(readOnly = true)
    public UserCardEntity findById(String id) {
        return userCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Usercard with Id " + id + " Not Found."));
    }


    @Transactional(readOnly = true)
    public Page<UserCardEntity> findPageById(String name, Pageable pageable) {
        return userCardRepository.findPageById(name, pageable);
    }

    @Transactional(readOnly = true)
    public void checkExistsWithUserId(String userid) {
         System.err.println("중복체크 :"+userCardRepository.findByUserSeq(userid));
        if (userCardRepository.findByUserSeq(userid).isPresent()) {
            throw new CustomException(ErrorCode.USERCARD_DUPLICATE); // usercard에 사용자 ID 중복 체크
        }
    }

}
