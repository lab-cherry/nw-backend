package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.UserCardEntity;
import lab.cherry.nw.repository.UserCardRepository;
import lab.cherry.nw.service.UserCardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

/**
 * <pre>
 * ClassName : OrgServiceImpl
 * Type : class
 * Description : 조직와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("userCardServiceImpl")
@Transactional
@RequiredArgsConstructor
public class UserCardServiceImpl implements UserCardService {

    private final UserCardRepository userCardRepository;

    /**
     * [OrgServiceImpl] 전체 조직 조회 함수
     *
     * @return DB에서 전체 조직 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 조직를 조회하여, 사용자 정보 목록을 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<UserCardEntity> getUsercards(Pageable pageable) {
        return userCardRepository.findAll(pageable);
//        return EntityNotFoundException.requireNotEmpty(orgRepository.findAll(), "Orgs Not Found");
    }

    /**
     * [OrgServiceImpl] 조직 생성 함수
     * @return 생성된 조직 정보를 리턴합니다.
     * @throws CustomException 중복된 이름에 대한 예외 처리 발생
     * <pre>
     * 조직을 등록합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public UserCardEntity createUserCard(UserCardEntity.CreateDto userCardCreateDto) {

        Instant instant = Instant.now();

        UserCardEntity userCardEntity = UserCardEntity.builder()
            .groom(userCardCreateDto.getGroom())
            .created_at(instant)
            .build();

        return userCardRepository.save(userCardEntity);
    }
    
    /**
     * [OrgServiceImpl] 조직 수정 함수
     *

     * @throws EntityNotFoundException 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 조직에 대한 정보를 수정합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void updateById(String id, UserCardEntity.UpdateDto usercard) {

        UserCardEntity userCardEntity = findById(id);

        if (usercard.getGroom() != null || usercard.getBride() != null || usercard.getNote() != null|| usercard.getStatus() != null|| usercard.getResDate() != null) {

            userCardEntity = UserCardEntity.builder()
                .id(userCardEntity.getId())
                .groom((usercard.getGroom() !=null) ? usercard.getGroom() : userCardEntity.getGroom())
                .bride((usercard.getBride() != null) ? usercard.getBride() : userCardEntity.getBride())
                .note((usercard.getNote() != null) ? usercard.getNote() : userCardEntity.getNote())
                .status((usercard.getStatus() != null) ? usercard.getStatus() : userCardEntity.getStatus())
                .resDate((usercard.getResDate() != null) ? usercard.getResDate() : userCardEntity.getResDate())
                .build();

            userCardRepository.save(userCardEntity);

        } else {
            log.error("[OrgServiceImpl - udpateOrganization] orgName, bizNum, contact 수정 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    /**
     * [OrgServiceImpl] 조직 삭제 함수
     *
     * @param id 삭제할 조직의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 조직 정보를 삭제합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    public void deleteById(String id) {
        userCardRepository.delete(userCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Org with Id " + id + " Not Found.")));
    }
    

    /**
     * [OrgServiceImpl] ID로 조직 조회 함수
     *
     * @param id 조회할 조직의 식별자입니다.
     * @return 주어진 식별자에 해당하는 조직 정보
     * @throws EntityNotFoundException 해당 ID의 조직 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 조직 정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public UserCardEntity findById(String id) {
        return userCardRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Org with Id " + id + " Not Found."));
    }

    @Transactional(readOnly = true)
    public Page<UserCardEntity> findPageById(String name, Pageable pageable) {
        return userCardRepository.findPageById(name, pageable);
    }

}
