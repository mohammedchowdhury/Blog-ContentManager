package blog.dao;

import blog.dto.Blog;
import java.util.List;

/**
 *
 * @author mohammedchowdhury
 */
public interface BlogDao {

    List<Blog> getAllStaticPageIfApproved();

    List<Blog> getAllBlogIfApproved();

    Blog getBlogById(int id);

    List<Blog> getAllBlogs();

    void deleteBlog(int id);

    void updateBlog(Blog blog);

    Blog createBlog(Blog blog);

}
