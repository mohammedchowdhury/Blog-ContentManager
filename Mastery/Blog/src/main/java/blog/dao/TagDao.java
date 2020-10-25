package blog.dao;

import blog.dto.Tag;
import java.util.List;

/**
 *
 * @author mohammedchowdhury
 */
public interface TagDao {

    Tag getTagById(int id);

    Tag getTagbyHashTag(String hashTah);

    List<Tag> getAllTags();

    void deleteTag(int id);

    void updateTag(Tag tag);

    Tag createTag(Tag tag);
}
