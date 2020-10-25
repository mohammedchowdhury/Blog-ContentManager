package blog.dao;

import blog.dto.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author mohammedchowdhury
 */
@Repository
public class TagDaoDB implements TagDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Tag getTagById(int id) {
        try {
            final String SELECT_Tag_BY_ID = "SELECT * FROM TAGS WHERE HASHTAGID = ? ;";
            return jdbc.queryForObject(SELECT_Tag_BY_ID, new TagMapper(), id);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public Tag getTagbyHashTag(String hashTag) {
        try {
            final String SELECT_Tag_BY_HashTag = "SELECT * FROM TAGS WHERE HASHTAG = ?";
            return jdbc.queryForObject(SELECT_Tag_BY_HashTag, new TagMapper(), hashTag);
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Tag> getAllTags() {
        final String SELECT_ALL_ROLES = "SELECT * FROM TAGS ;";
        return jdbc.query(SELECT_ALL_ROLES, new TagMapper());
    }

    @Override
    public void deleteTag(int id) {
        final String DELETE_TAGS_BLOG = "DELETE FROM TAGS_BLOG WHERE HASHTAGID = ?";
        final String DELETE_TAGS = "DELETE FROM TAGS WHERE HASHTAGID = ?";
        try {
            jdbc.update(DELETE_TAGS_BLOG, id);
        } catch (Exception ex) {
        }
        jdbc.update(DELETE_TAGS, id);
    }

    @Override
    public void updateTag(Tag tag) {
        Tag temp = getTagbyHashTag(tag.getHashTag());
        if (temp == null) {
            final String UPDATE_TAG = "UPDATE TAGS SET HASHTAG = ? WHERE HASHTAGID = ? ;";
            jdbc.update(UPDATE_TAG, tag.getHashTag(), tag.getTagID());
        }
    }

    @Override
    public Tag createTag(Tag tag) {
        Tag temp = getTagbyHashTag(tag.getHashTag());
        if (temp == null) {
            final String INSERT_TAG = "INSERT INTO TAGS(HASHTAG) VALUES(?)";
            jdbc.update(INSERT_TAG, tag.getHashTag());
            int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
            tag.setTagID(newId);
            return tag;
        } else {
            return temp;
        }
    }

    public static final class TagMapper implements RowMapper<Tag> {

        @Override
        public Tag mapRow(ResultSet rs, int i) throws SQLException {
            Tag tag = new Tag();
            tag.setTagID(rs.getInt("HASHTAGID"));
            tag.setHashTag(rs.getString("HASHTAG"));
            return tag;
        }
    }
}
