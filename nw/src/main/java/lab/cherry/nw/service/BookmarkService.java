package lab.cherry.nw.service;

import lab.cherry.nw.model.BookmarkEntity;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


/**
 * <pre>
 * ClassName : BookmarkService
 * Type : interface
 * Description : 북마크와 관련된 함수를 정리한 인터페이스입니다.
 * Related : BookmarkController, BookmarkServiceiml
 * </pre>
 */
@Component
public interface BookmarkService {
 Page<BookmarkEntity> getBookmarks(Pageable pageable);

 BookmarkEntity findById(String id);
// BookmarkEntity findByUserId(String userid);
 BookmarkEntity findByUserId(ObjectId userId);
 void createBookmark(BookmarkEntity.BookmarkCreateDto bookmarkCreateDto);
 void updateById(String id, BookmarkEntity.BookmarkUpdateDto bookmarkUpdateDto);
// void updateByUserId(String userid, BookmarkEntity.UpdateDto updateDto);
 void deleteById(String id);

    Page<BookmarkEntity> findPageByUserId(String userid, Pageable pageable);

// void updateOrgById(String id, List<String> orgIds);
}
