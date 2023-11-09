package lab.cherry.nw.service.Impl;


import lab.cherry.nw.error.exception.EntityNotFoundException;
import lab.cherry.nw.model.*;
import lab.cherry.nw.repository.FinaldocsRepository;
import lab.cherry.nw.repository.ScheduleRepository;
import lab.cherry.nw.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
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

	private final ScheduleRepository scheduleRepository;
	private final FinalTemplService finalTemplService;
    private final OrgService orgService;


    /**
     * [scheduleServiceImpl] 스케줄표 조회 함수
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
    public Page<ScheduleEntity> getSchedule(Pageable pageable) {
        return scheduleRepository.findAll(pageable);
    }


      /**
     * [scheduleServiceImpl] 스케줄표 생성 함수
     *
     * @param scheduleCreateDto 스케줄표 생성에 필요한 조직 등록 정보를 담은 개체입니다.
     * @return 스케줄표정보를 리턴합니다.
     * <pre>
     * 스케줄표를 등록합니다.
     * </pre>
     *
     * Author : hhhaeri(yhoo0020@gmail.com)
     */
    public ScheduleEntity createSchedule(ScheduleEntity.ScheduleCreateDto scheduleCreateDto) {

        Instant instant = Instant.now();

        OrgEntity orgEntity = orgService.findById(scheduleCreateDto.getOrgId());

        ScheduleEntity scheduleEntity = ScheduleEntity.builder()
            .content(scheduleCreateDto.getContent())
            .org(orgEntity)
            .created_at(instant)
            .build();

        return scheduleRepository.save(scheduleEntity);
    }

    /**
     * [scheduleServiceImpl] 스케줄표 삭제 함수
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
		scheduleRepository.delete(scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Schedule with Id " + id + " Not Found.")));
    }


    /**
     * [scheduleServiceImpl] ID로 스케줄표  조회 함수
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

    @Transactional(readOnly = true)
    public ScheduleEntity findById(String id) {
        return scheduleRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("schedule with Id " + id + " Not Found."));
    }

    /**
     * [scheduleServiceImpl] NAME으로 스케줄표 조회 함수
     *
     * @param name 조회할 스케줄표의 이름입니다.
     * @return 주어진 이름에 해당하는 스케줄표  정보를 리턴합니다.
     * @throws EntityNotFoundException 해당 이름의 스케줄표  정보가 없을 경우 예외 처리 발생
     * <pre>
     * 입력한 name에 해당하는 스케줄표  정보를 조회합니다.
     * </pre>
     *
	 * Author : hhhaeri(yhoo0020@gmail.com)
     */
    // @Transactional(readOnly = true)
    // public ScheduleEntity findByName(String name) {
    //     return scheduleRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("Schedule with Name " + name + " Not Found."));
    // }

    // @Transactional(readOnly = true)
    // public Page<ScheduleEntity> findPageByName(String name, Pageable pageable) {
    //     return scheduleRepository.findPageByName(name, pageable);
    // }

    @Transactional(readOnly = true)
    public Page<ScheduleEntity> findPageById(String id, Pageable pageable) {
        return scheduleRepository.findPageById(id, pageable);
    }
}
