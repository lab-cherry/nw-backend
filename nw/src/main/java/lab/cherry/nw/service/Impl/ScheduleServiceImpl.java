package lab.cherry.nw.service.Impl;

import lab.cherry.nw.error.enums.ErrorCode;
import lab.cherry.nw.error.exception.CustomException;
import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.FinalTemplEntity;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.ScheduleEntity;
import lab.cherry.nw.model.UserEntity;
import lab.cherry.nw.repository.FinalTemplRepository;
import lab.cherry.nw.repository.ScheduleRepository;
import lab.cherry.nw.service.FinalTemplService;
import lab.cherry.nw.service.OrgService;
import lab.cherry.nw.service.ScheduleService;
import lab.cherry.nw.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

import java.util.HashMap;
import java.util.Map;

/**
 * <pre>
 * ClassName : ScheduleServiceImpl
 * Type : class
 * Description : 스케줄표와 관련된 서비스 구현과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : spring-boot-starter-data-jpa
 * </pre>
 */
@Slf4j
@Service("scheduleServiceImpl")
@Transactional
@RequiredArgsConstructor
public class ScheduleServiceImpl implements ScheduleService {

    private final FinalTemplRepository finalTemplRepository;
	private final ScheduleRepository scheduleRepository;
	private final FinalTemplService finalTemplService;
    private final UserService userService;
    private final OrgService orgService;

    /**
     * [FinalTemplServiceImpl] 스케줄표 조회 함수
     *
     * @return DB에서 스케줄표 정보 목록을 리턴합니다.
     * @throws EntityNotFoundException 스케줄표 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 전체 스케줄표 조회하여, 최종 스케줄표목록을 반환합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    @Transactional(readOnly = true)
    @Override
    public Page<FinalTemplEntity> getFinalTemplate(Pageable pageable) {

        return finalTemplRepository.findAll(pageable);


        //        return EntityNotFoundException.requireNotEmpty(finaldocsRepository.findAll(), "Fianldocs Not Found");
    }

    /**
     * [FinalTemplServiceImpl] 스케줄표 생성 함수
     *
     * @param finalTemplCreateDto 스케줄표 생성에 필요한 조직 등록 정보를 담은 개체입니다.
     * @return 생성된 스케줄표 정보를 리턴합니다.
     * <pre>
     * 스케줄표을 등록합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public ScheduleEntity transColumn(ScheduleEntity.transDto scheduleTransDto) {

        UserEntity userEntity = userService.findById(scheduleTransDto.getUserid());
        OrgEntity orgEntity = orgService.findById(scheduleTransDto.getOrgid());
		FinalTemplEntity finalTemplEntity = finalTemplService.findById(scheduleTransDto.getFinalTemplid());



		Map<Object,Object> content = finalTemplEntity.getContent();

		// Null 값을 가진 엔트리만 가져오기
		Map<Object, Object> nullEntries = new HashMap<>();

		for (Map.Entry<Object, Object> entry : content.entrySet()) {
			if (!entry.getValue().equals("")) {
				nullEntries.put(entry.getKey(), entry.getValue());
			}
		}

		ScheduleEntity scheduleEntity = ScheduleEntity.builder()
            .name(scheduleTransDto.getName())
            .column(nullEntries)
            .userid(userEntity)
            .orgid(orgEntity)
            .build();

        return scheduleRepository.save(scheduleEntity);

    }

    /**
     * [FinalTemplServiceImpl] 스케줄표 수정 함수
     *
     * @param fianlTempl 스케줄표 수정에 필요한 정보를 담은 개체입니다.
     * @throws EntityNotFoundException 스케줄표 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 특정 스케줄표에 대한 정보를 수정합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public void updateById(String id, FinalTemplEntity.UpdateDto finalTempl) {

        FinalTemplEntity finalTemplEntity = findById(id);
        UserEntity userEntity = userService.findById(finalTempl.getUserid());
        OrgEntity orgEntity = orgService.findById(finalTempl.getOrgid());
        Instant instant = Instant.now();

        if (finalTempl.getContent() != null) {

            finalTemplEntity = FinalTemplEntity.builder()
                .id(finalTemplEntity.getId())
                .content((finalTempl.getContent() != null) ? finalTempl.getContent() : finalTemplEntity.getContent())
                .userid(userEntity)
                .orgid(orgEntity)
                .updated_at(instant)
                .build();

            finalTemplRepository.save(finalTemplEntity);

        } else {
            log.error("[FinaldocsServiceImpl - udpateFinalTempl] Content만 수정 가능합니다.");
            throw new CustomException(ErrorCode.INVALID_INPUT_VALUE);
        }
    }

    /**
     * [FinalTemplServiceImpl] 스케줄표  삭제 함수
     *
     * @param id 삭제할 스케줄표의 식별자입니다.
     * @throws EntityNotFoundException 해당 ID의 스케줄표 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id를 가진 조직 스케줄표을 삭제합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public void deleteById(String id) {
        finalTemplRepository.delete(finalTemplRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Fianldocs with Id " + id + " Not Found.")));
    }



    /**
     * [FinalTemplServiceImpl] ID로 스케줄표  조회 함수
     *
     * @param id 조회할 스케줄표의 식별자입니다.
     * @return 주어진 식별자에 해당하는 조직 정보
     * @throws EntityNotFoundException 해당 ID의 스케줄표 정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 id에 해당하는 스케줄표 정보를 조회합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
//    @Transactional(readOnly = true)
//    public FinaldocsEntity findByName(String name) {
//        return finaldocsRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Fianldocs with Id " + name + " Not Found."));
//    }


    @Transactional(readOnly = true)
    public FinalTemplEntity findById(String id) {
        return finalTemplRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("finaldocs with Id " + id + " Not Found."));
    }

    /**
     * [FinalTemplServiceImpl] NAME으로 스케줄표 조회 함수
     *
     * @param name 조회할 스케줄표의 이름입니다.
     * @return 주어진 이름에 해당하는 스케줄표  정보를 리턴합니다.
     * @throws EntityNotFoundException 해당 이름의 스케줄표  정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 name에 해당하는 스케줄표  정보를 조회합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @Transactional(readOnly = true)
    public FinalTemplEntity findByName(String name) {
        return finalTemplRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Org with Name " + name + " Not Found."));
    }

    @Transactional(readOnly = true)
    public Page<FinalTemplEntity> findPageByName(String name, Pageable pageable) {
        return finalTemplRepository.findPageByName(name, pageable);
    }


    @Transactional(readOnly = true)
    public Page<FinalTemplEntity> findPageById(String id, Pageable pageable) {
        return finalTemplRepository.findPageById(id, pageable);
    }
}
