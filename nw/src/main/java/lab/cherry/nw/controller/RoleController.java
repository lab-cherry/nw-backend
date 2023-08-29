package lab.cherry.nw.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lab.cherry.nw.error.ErrorResponse;
import lab.cherry.nw.error.enums.SuccessCode;
import lab.cherry.nw.model.OrgEntity;
import lab.cherry.nw.model.RoleEntity;
import lab.cherry.nw.service.RoleService;
import lab.cherry.nw.error.ResultResponse;
import lab.cherry.nw.util.Common;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * <pre>
 * ClassName : RoleController
 * Type : class
 * Description : 역할 목록 조회, 역할 상세 조회, 역할 업데이트, 역할 삭제, 역할 찾기 등 역활과 관련된 함수를 포함하고 있는 클래스입니다.
 * Related : RoleRepository, RoleService, RoleServiceImpl
 * </pre>
 */

// MEMO : 재사용성을 위해 ServiceImpl에서만 비즈니스 로직을 사용하기로 함
//        Dto를 통해 알맞는 파라미터로 데이터 가공 후 사용하기로 함
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/role")
@Tag(name = "Role", description = "Role API Document")
public class RoleController {

    private final RoleService roleService;

    /**
     * [RoleController] 전체 역할 목록 함수
     *
     * @return 전체 역할 목록을 반환합니다.
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("")
    @Operation(summary = "역할 목록", description = "역할 목록을 조회합니다.")
    public ResponseEntity<?> findAllRoles(
            @RequestParam(required = false) String name,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "5") Integer size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        log.info("retrieve all roles controller...!");

        Pageable pageable = PageRequest.of(page, size, Sort.by(Common.getOrder(sort)));

        Page<RoleEntity> roleEntity;
        if(name == null) {
            roleEntity = roleService.getRoles(pageable);
        } else {
            roleEntity = roleService.findPageByName(name, pageable);
        }

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.getUsers());
        return new ResponseEntity<>(roleEntity, new HttpHeaders(), HttpStatus.OK);
    }


    /**
     * [RoleController] 역할 생성 함수
     *
     * @param roleCreateDto 생성에 필요한 역할 정보를 담고 있는 객체입니다.
     * @return
     * <pre>
     * true  : 성공(200)을 반환합니다.
     * false : 에러(400)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @PostMapping("")
    @Operation(summary = "역할 생성", description = "역할을 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "역할 생성이 완료되었습니다.", content = @Content(schema = @Schema(implementation = ResponseEntity.class))),
            @ApiResponse(responseCode = "400", description = "입력 값이 잘못되었습니다.", content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    public ResponseEntity<?> createRole(@Valid @RequestBody(required = false) RoleEntity.CreateDto roleCreateDto) {

        log.info("[RoleController] createRole...!");

        RoleEntity roleEntity =  roleService.createRole(roleCreateDto);

        //         Header 에 등록
        //        HttpHeaders httpHeaders = new HttpHeaders();
        //        httpHeaders.add("Authorization", "Bearer " + accessToken.getToken());

        return new ResponseEntity<>(roleEntity, new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [RoleController] 특정 역할 조회 함수
     *
     * @param id 역할 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 역할 정보를 반환합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @GetMapping("{id}")
    @Operation(summary = "ID로 역할 찾기", description = "역할을 조회합니다.")
    public ResponseEntity<?> findByRoleId(@PathVariable("id") String id) {

        log.info("[RoleController] findByRoleId...!");

//        final ResultResponse response = ResultResponse.of(SuccessCode.OK, userService.findById(id));
        return new ResponseEntity<>(roleService.findById(id), new HttpHeaders(), HttpStatus.OK);
    }

    /**
     * [RoleController] 특정 역할 삭제 함수
     *
     * @param id 역할 고유번호를 입력합니다.
     * @return
     * <pre>
     * true  : 특정 역할 삭제처리합니다.
     * false : 에러(400, 404)를 반환합니다.
     * </pre>
     *
     * Author : taking(taking@duck.com)
     */
    @DeleteMapping("{id}")
    @Operation(summary = "역할 삭제", description = "역할을 삭제합니다.")
    public ResponseEntity<?> deleteRole(@PathVariable("id") String id) {

        log.info("[RoleController] deleteRole...!");

        roleService.deleteById(id);

        final ResultResponse response = ResultResponse.of(SuccessCode.OK);
        return new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
    }
}
