package lab.cherry.nw.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.Map;

/**
 * <pre>
 * ClassName : UserEntity
 * Type : class
 * Description : User와 관련된 Entity를 구성하고 있는 클래스입니다.
 * Related : UserRepository, UserServiceImpl
 * </pre>
 */
@Getter
@Builder
@NoArgsConstructor @AllArgsConstructor
@Document(collection = "finaldocs")
@JsonPropertyOrder({ "id", "finaldocsName","groom", "bride", "hallname", "weddingDatename", "guaranteePerson", "hallFee", "weddingPicture", "dresshelper", "mc" , "bus", "bouqet"  , "photo" , "officiant", "pyebaek","etc" })
public class FdocsTemplateEntity implements Serializable {

    @Id
    @JsonProperty("fdocstmlSeq")
    @Schema(title = "최종확인서 템플릿 고유번호", example = "64ed89aa9e813b5ab16da6de")
    private String id;


    @JsonProperty("fdocstmlName")
    @Schema(title = "최종확인서 이름", example = "문서1")
    @Size(min = 4, max = 255, message = "Minimum name length: 4 characters")
    private String name;

    @JsonProperty("groom")
    @Schema(title = "신랑측 정보", example = "")
    private Map groom;
//    private Map<String, Object> groom;

    @JsonProperty("bride")
    @Schema(title = "신부측 정보", example = "")
    private Map bride;

    @JsonProperty("weddingDatename")
    @JsonFormat(pattern="yyyy-MM-dd hh:mm:ss", locale = "ko_KR", timezone = "Asia/Seoul")
    @Schema(title = "예식 날짜", example = "2023-07-04 12:00:00")
    private String weddingDatename;

    @JsonProperty("guaranteePerson")
    @Schema(title = "보증인원", example = "")
    private Map guaranteePerson;

    @JsonProperty("hallFee")
    @Schema(title = "대관료", example = "5000")
    private int hallFee;

    @JsonProperty("weddingPicture")
    @Schema(title = "스튜디오", example = "가을")
    private String weddingPicture;

    @JsonProperty("dresshelper")
    @Schema(title = "드레스 헬퍼 비용", example = "15")
    private int dresshelper;


    @JsonProperty("mc")
    @Schema(title = "사회자", example = "전문 사회")
    private String mc;


    @JsonProperty("bouqet")
    @Schema(title = "부케", example = "Y")
    private boolean bouqet;
    

    @JsonProperty("pyebaek")
    @Schema(title = "폐백여부", example = "Y")
    private boolean pyebaek;
    

    @JsonProperty("photo")
    @Schema(title = "포토 개수", example = "Y")
    private int photo;
    
   
    @JsonProperty("bus")
    @Schema(title = "버스 대절", example = "Y")
    private boolean bus;
  
    @JsonProperty("officiant")
    @Schema(title = "주례", example = "N")
    private boolean officiant;


    @JsonProperty("etc")
    @Schema(title = "주례", example = "N")
    private Map etc;
//////////////////////////////////////////////////////////////////////////

    @Getter
    @Builder
    @NoArgsConstructor @AllArgsConstructor
    public static class CreateDto {

    @Schema(title = "최종확인서 이름", example = "더모멘트")
    @Size(min = 4, max = 20, message = "Minimum name length: 4 characters")
    private String name;

    @Schema(title = "신랑측 정보", example = "김철수")
    private Map groom;

    @Schema(title = "신부측 정보", example = "박영희")
    private Map bride;

    @Schema(title = "예식 날짜", example = "2023-07-04 12:00:00")
    private String weddingDatename;
    
    @Schema(title = "보증인원", example = "200")
    private Map guaranteePerson;
    
    @Schema(title = "대관료", example = "5000")
    private int hallFee;
    
    @Schema(title = "스튜디오", example = "가을")
    private String weddingPicture;
 
    @Schema(title = "드레스 헬퍼 비용", example = "15")
    private int dresshelper;
    
    @Schema(title = "사회자", example = "전문 사회")
    private String mc;

    @Schema(title = "부케", example = "Y")
    private boolean bouqet;

    @Schema(title = "폐백여부", example = "Y")
    private boolean pyebaek;

    @JsonProperty("photo")
    @Schema(title = "포토 개수", example = "Y")
    private int photo;

    @JsonProperty("bus")
    @Schema(title = "버스 대절", example = "Y")
    private boolean bus;

    @JsonProperty("officiant")
    @Schema(title = "주례", example = "N")
    private boolean officiant;


    @JsonProperty("etc")
    private Map etc;
    }

}
