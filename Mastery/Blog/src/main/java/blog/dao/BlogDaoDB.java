package blog.dao;

import blog.dao.TagDaoDB.TagMapper;
import blog.dto.Blog;
import blog.dto.Tag;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author mohammedchowdhury
 */
@Repository
public class BlogDaoDB implements BlogDao {

    @Autowired
    JdbcTemplate jdbc;

    private List<Tag> getTagsForBlog(int blogID) {

        final String SELECT_TAGS_FOR_BLOG = "SELECT TAGS.* FROM TAGS_BLOG "
                + "JOIN TAGS ON TAGS_BLOG.HASHTAGID = TAGS.HASHTAGID "
                + "WHERE TAGS_BLOG.BLOGID = ?";
        try {
            Set<Tag> tags = new HashSet(jdbc.query(SELECT_TAGS_FOR_BLOG, new TagMapper(), blogID));
            List<Tag> listOfTags = new ArrayList<>(tags);
            return listOfTags;
        } catch (Exception e) {
            List<Tag> listOfTags = new ArrayList<>();
            return listOfTags;
        }
    }

    @Override
    public List<Blog> getAllStaticPageIfApproved() {
        final String SELECT_ALL_USERS = "SELECT * FROM BLOG WHERE STATIC = TRUE AND BLOGVARIFIED = TRUE";
        List<Blog> blogs = jdbc.query(SELECT_ALL_USERS, new BlogMapper());
        for (Blog blog : blogs) {
            blog.setListOfTags(getTagsForBlog(blog.getBlogID()));
        }
        return blogs;
    }

    @Override
    public List<Blog> getAllBlogIfApproved() {
        final String SELECT_ALL_USERS = "SELECT * FROM BLOG WHERE STATIC = FALSE AND BLOGVARIFIED = TRUE";
        List<Blog> blogs = jdbc.query(SELECT_ALL_USERS, new BlogMapper());
        for (Blog blog : blogs) {
            blog.setListOfTags(getTagsForBlog(blog.getBlogID()));
        }
        return blogs;
    }

    @Override
    public Blog getBlogById(int id) {
        try {
            final String SELECT_BLOG_BY_ID = "SELECT * FROM BLOG WHERE BLOGID = ?";
            Blog blog = jdbc.queryForObject(SELECT_BLOG_BY_ID, new BlogMapper(), id);
            blog.setListOfTags(getTagsForBlog(id));
            return blog;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Blog> getAllBlogs(){
        final String SELECT_ALL_USERS = "SELECT * FROM BLOG";
        List<Blog> blogs = jdbc.query(SELECT_ALL_USERS, new BlogMapper());
        for (Blog blog : blogs) {
            blog.setListOfTags(getTagsForBlog(blog.getBlogID()));
        }
        return blogs;
    }

    @Override
    public void deleteBlog(int id) {
        final String DELETE_TAGS_BLOG = "DELETE FROM TAGS_BLOG WHERE BLOGID = ?";
        final String DELETE_BLOG = "DELETE FROM BLOG WHERE BLOGID = ?";
        jdbc.update(DELETE_TAGS_BLOG, id);
        jdbc.update(DELETE_BLOG, id);
    }

    @Override
    public void updateBlog(Blog blog) {
        final String UPDATE_BLOG = "UPDATE BLOG SET TITLE = ? , BLOGTEXT = ? , DATEOFSHOW = ? , EXPIRATION = ? , IMAGE = ? , BLOGVARIFIED = ? , STATIC = ? , USERID = ? "
                + "WHERE BLOGID = ?";
        jdbc.update(UPDATE_BLOG, blog.getTitle(), blog.getBlogText(), blog.getDateOfShow(), blog.getExpirationDate(), blog.getImageLink(), blog.isVarified(), blog.isStaticPage(), blog.getUserID(), blog.getBlogID());

        final String DELETE_TAGS_BLOG = "DELETE FROM TAGS_BLOG WHERE BLOGID = ?";
        jdbc.update(DELETE_TAGS_BLOG, blog.getBlogID());

        if (blog.getListOfTags() != null) {
            for (Tag tag : blog.getListOfTags()) {
                final String INSERT_TAGS_BLOG = "INSERT INTO TAGS_BLOG(HASHTAGID, BLOGID) VALUES(?,?)";
                jdbc.update(INSERT_TAGS_BLOG, tag.getTagID(), blog.getBlogID());
            }
        }
    }

    @Override
    @Transactional
    public Blog createBlog(Blog blog) {
        final String INSERT_BLOG = "INSERT INTO BLOG(TITLE,BLOGTEXT,DATEOFSHOW,EXPIRATION,IMAGE,BLOGVARIFIED,STATIC,USERID) VALUES(?,?,?,?,?,?,?,?) ";
        jdbc.update(INSERT_BLOG, blog.getTitle(), blog.getBlogText(), blog.getDateOfShow(), blog.getExpirationDate(), blog.getImageLink(), blog.isVarified(), blog.isStaticPage(), blog.getUserID());
        int newId = jdbc.queryForObject("select LAST_INSERT_ID()", Integer.class);
        blog.setBlogID(newId);

        if (blog.getListOfTags() != null) {
            for (Tag tag : blog.getListOfTags()) {
                final String INSERT_TAGS_BLOG = "INSERT INTO TAGS_BLOG(HASHTAGID, BLOGID) VALUES(?,?)";
                jdbc.update(INSERT_TAGS_BLOG, tag.getTagID(), blog.getBlogID());
            }
        } else {
            blog.setListOfTags(new ArrayList<Tag>());
        }
        return blog;
    }

    public static final class BlogMapper implements RowMapper<Blog> {

        @Override
        public Blog mapRow(ResultSet rs, int i) throws SQLException {
            Blog blog = new Blog();
            blog.setBlogID(rs.getInt("BLOGID"));
            blog.setTitle(rs.getString("TITLE"));
            blog.setBlogText(rs.getString("BLOGTEXT"));
            try{
                 blog.setDateOfShow(rs.getDate("DATEOFSHOW").toLocalDate());
            }catch(Exception ex){
                blog.setDateOfShow(null);
            }
             try{
                blog.setExpirationDate(rs.getDate("EXPIRATION").toLocalDate());
            }catch(Exception ex){
                blog.setExpirationDate(null);
            }
           
            
            blog.setImageLink(rs.getString("IMAGE"));
            blog.setVarified(rs.getBoolean("BLOGVARIFIED"));
            blog.setStaticPage(rs.getBoolean("STATIC"));
            blog.setUserID(rs.getInt("USERID"));
            return blog;
        }
    }
}
