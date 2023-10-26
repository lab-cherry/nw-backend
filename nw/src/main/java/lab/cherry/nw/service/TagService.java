package lab.cherry.nw.service;

import lab.cherry.nw.model.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

/**
 * <pre>
 * ClassName : TagService
 * Type : interface
 * Description : 태그와 관련된 함수를 정리한 인터페이스입니다.
 * Related : TagController,TagServiceiml
 * </pre>
 */
@Component
public interface TagService {
Page<TagEntity> getTags(Pageable pageable);
TagEntity findByName(String name);
// TagEntity findByUserId(String userid);
// TagEntity findByOrgId(String orgid);
 void createTag(TagEntity.TagCreateDto tagCreateDto);
// Page<TagEntity> findPageByUserId(String userid, Pageable pageable);
// Page<TagEntity> findPageByOrgId(String orgid, Pageable pageable);
 // void updateOrgById(String id, List<String> orgIds);
}