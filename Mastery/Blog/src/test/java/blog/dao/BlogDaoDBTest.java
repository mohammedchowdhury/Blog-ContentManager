package blog.dao;

import blog.TestApplicationConfiguration;
import blog.dto.Blog;
import blog.dto.Role;
import blog.dto.Tag;
import blog.dto.User;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import static org.junit.Assert.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author mohammedchowdhury
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TestApplicationConfiguration.class)
public class BlogDaoDBTest {

    @Autowired
    BlogDao blogDao;

    @Autowired
    UserDao userDao;

    @Autowired
    RoleDao roleDao;

    @Autowired
    TagDao tagDao;

    @BeforeEach
    @AfterEach
    public void setUp() {
        //remove all tags 
        List<Tag> listOfTags = tagDao.getAllTags();
        for (Tag tag : listOfTags) {
            tagDao.deleteTag(tag.getTagID());
        }
        // remove all blogs 
        List<Blog> listOfBlogs = blogDao.getAllBlogs();
        for (Blog blog : listOfBlogs) {
            blogDao.deleteBlog(blog.getBlogID());
        }
        //remove all User
        List<User> listOfUsers = userDao.getAllUsers();
        for (User user : listOfUsers) {
            userDao.deleteUser(user.getId());
        }
        //remove all Roll
        List<Role> listOfRolls = roleDao.getAllRoles();
        for (Role role : listOfRolls) {
            roleDao.deleteRole(role.getId());
        }
    }

    @Test
    public void testcreateBlog() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        user = userDao.createUser(user);
        //create tag
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        tag = tagDao.createTag(tag);
        //create blog
        Blog blog = new Blog();
        blog.setTitle("Title");
        blog.setBlogText("Terrible blog");
        blog.setDateOfShow(LocalDate.parse("2020-10-18"));
        blog.setExpirationDate(LocalDate.parse("2020-10-20"));
        blog.setImageLink("/Food.jpg");
        blog.setVarified(true);
        blog.setStaticPage(true);
        blog.setUserID(user.getId());
        blog.setListOfTags(tagDao.getAllTags());
        //act 
        blog = blogDao.createBlog(blog);
        Blog newBlog = blogDao.getBlogById(blog.getBlogID());
        //assert
        assertEquals(blog, newBlog);
    }

    @Test
    public void testGetBlogById() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        user = userDao.createUser(user);
        //create tag
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        tag = tagDao.createTag(tag);
        //create blog
        Blog blog = new Blog();
        blog.setTitle("Testing getByID");
        blog.setBlogText("Blog Blog Blog");
        blog.setDateOfShow(LocalDate.parse("2020-10-26"));
        blog.setExpirationDate(LocalDate.parse("2020-10-30"));
        blog.setImageLink("/Food.jpg");
        blog.setVarified(true);
        blog.setStaticPage(true);
        blog.setUserID(user.getId());
        blog.setListOfTags(tagDao.getAllTags());
        //act 
        blog = blogDao.createBlog(blog);
        Blog newBlog = blogDao.getBlogById(blog.getBlogID());
        //assert
        assertEquals(blog, newBlog);
    }

    @Test
    public void testGetAllBlogs() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        user = userDao.createUser(user);
        //create tag
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        tag = tagDao.createTag(tag);
        //create blog
        Blog blog = new Blog();
        blog.setTitle("Testing getAll");
        blog.setBlogText("Blog Blog Blog");
        blog.setDateOfShow(LocalDate.parse("2020-10-26"));
        blog.setExpirationDate(LocalDate.parse("2020-10-30"));
        blog.setImageLink("/Food.jpg");
        blog.setVarified(true);
        blog.setStaticPage(true);
        blog.setUserID(user.getId());
        blog.setListOfTags(tagDao.getAllTags());

        Blog blog2 = new Blog();
        blog2.setTitle("Testing getAll");
        blog2.setBlogText("Blog Blog Blog");
        blog2.setDateOfShow(LocalDate.parse("2020-10-26"));
        blog2.setExpirationDate(LocalDate.parse("2020-10-30"));
        blog2.setImageLink("/Food.jpg");
        blog2.setVarified(true);
        blog2.setStaticPage(true);
        blog2.setUserID(user.getId());
        blog2.setListOfTags(tagDao.getAllTags());
        //act 
        blog = blogDao.createBlog(blog);
        blog2 = blogDao.createBlog(blog2);

        List<Blog> listOfBlogs = blogDao.getAllBlogs();
        int size = 2;
        int actualSize = listOfBlogs.size();
        //assert
        assertEquals(size, actualSize);
        assertTrue(listOfBlogs.contains(blog));
        assertTrue(listOfBlogs.contains(blog2));
    }

    @Test
    public void testdeleteBlog() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        user = userDao.createUser(user);
        //create tag
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        tag = tagDao.createTag(tag);
        //create blog
        Blog blog = new Blog();
        blog.setTitle("Testing getByID");
        blog.setBlogText("Blog Blog Blog");
        blog.setDateOfShow(LocalDate.parse("2020-10-26"));
        blog.setExpirationDate(LocalDate.parse("2020-10-30"));
        blog.setImageLink("/Food.jpg");
        blog.setVarified(true);
        blog.setStaticPage(true);
        blog.setUserID(user.getId());
        blog.setListOfTags(tagDao.getAllTags());
        //act 
        blog = blogDao.createBlog(blog);
        blogDao.deleteBlog(blog.getBlogID());
        Blog newBlog = blogDao.getBlogById(blog.getBlogID());
        //assert
        assertNull(newBlog);
    }

    @Test
    public void testUpdateBlog() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        user = userDao.createUser(user);
        //create tag
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        tag = tagDao.createTag(tag);
        //create blog
        Blog blog = new Blog();
        blog.setTitle("Title");
        blog.setBlogText("Terrible blog");
        blog.setDateOfShow(LocalDate.parse("2020-10-18"));
        blog.setExpirationDate(LocalDate.parse("2020-10-20"));
        blog.setImageLink("/Food.jpg");
        blog.setVarified(true);
        blog.setStaticPage(true);
        blog.setUserID(user.getId());
        blog.setListOfTags(tagDao.getAllTags());
        blog = blogDao.createBlog(blog);
        //updating blog
        blog.setBlogText("BlogBlog");
        blog.setImageLink("/Chips.jpg");
        //act 
        blogDao.updateBlog(blog);
        Blog newBlog = blogDao.getBlogById(blog.getBlogID());
        //assert
        assertEquals(blog, newBlog);
    }

    @Test
    public void testGetAllStaticPage() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        user = userDao.createUser(user);
        //create tag
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        tag = tagDao.createTag(tag);
        //create blog
        Blog blog = new Blog();
        blog.setTitle("Testing getAll");
        blog.setBlogText("Blog Blog Blog");
        blog.setDateOfShow(LocalDate.parse("2020-10-26"));
        blog.setExpirationDate(LocalDate.parse("2020-10-30"));
        blog.setImageLink("/Food.jpg");
        blog.setVarified(false);
        blog.setStaticPage(true);
        blog.setUserID(user.getId());
        blog.setListOfTags(tagDao.getAllTags());

        Blog blog2 = new Blog();
        blog2.setTitle("Testing getAll");
        blog2.setBlogText("Blog Blog Blog");
        blog2.setDateOfShow(LocalDate.parse("2020-10-26"));
        blog2.setExpirationDate(LocalDate.parse("2020-10-30"));
        blog2.setImageLink("/Food.jpg");
        blog2.setVarified(true);
        blog2.setStaticPage(true);
        blog2.setUserID(user.getId());
        blog2.setListOfTags(tagDao.getAllTags());
        //act 
        blog = blogDao.createBlog(blog);
        blog2 = blogDao.createBlog(blog2);

        List<Blog> listOfBlogs = blogDao.getAllStaticPageIfApproved();
        int size = 1;
        int actualSize = listOfBlogs.size();
        //assert
        assertEquals(size, actualSize);
        assertTrue(listOfBlogs.contains(blog2));
    }

    @Test
    public void testGetAllBlogIfApproved() {
        //arrange
        //create roll 
        Role role = new Role();
        role.setRole("ROLE_ADMIN");
        role = roleDao.createRole(role);
        //create user         
        User user = new User();
        user.setPassword("password");
        user.setUsername("admin");
        Set<Role> roles = new HashSet<>(roleDao.getAllRoles());
        user.setRoles(roles);
        user = userDao.createUser(user);
        //create tag
        Tag tag = new Tag();
        tag.setHashTag("#Family");
        tag = tagDao.createTag(tag);
        //create blog
        Blog blog = new Blog();
        blog.setTitle("Testing getAll");
        blog.setBlogText("Blog Blog Blog");
        blog.setDateOfShow(LocalDate.parse("2020-10-26"));
        blog.setExpirationDate(LocalDate.parse("2020-10-30"));
        blog.setImageLink("/Food.jpg");
        blog.setVarified(true);
        blog.setStaticPage(true);
        blog.setUserID(user.getId());
        blog.setListOfTags(tagDao.getAllTags());

        Blog blog2 = new Blog();
        blog2.setTitle("Testing getAll");
        blog2.setBlogText("Blog Blog Blog");
        blog2.setDateOfShow(LocalDate.parse("2020-10-26"));
        blog2.setExpirationDate(LocalDate.parse("2020-10-30"));
        blog2.setImageLink("/Food.jpg");
        blog2.setVarified(true);
        blog2.setStaticPage(true);
        blog2.setUserID(user.getId());
        blog2.setListOfTags(tagDao.getAllTags());
        //act 
        blog = blogDao.createBlog(blog);
        blog2 = blogDao.createBlog(blog2);

        List<Blog> listOfBlogs = blogDao.getAllStaticPageIfApproved();
        int size = 2;
        int actualSize = listOfBlogs.size();
        //assert
        assertEquals(size, actualSize);
        assertTrue(listOfBlogs.contains(blog2));
    }
    
    
}
