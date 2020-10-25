package blog.controller;

import blog.dao.BlogDao;
import blog.dao.RoleDao;
import blog.dao.TagDao;
import blog.dao.UserDao;
import blog.dto.Blog;
import blog.dto.Role;
import blog.dto.Tag;
import blog.dto.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {

    @Autowired
    RoleDao roles;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    UserDao users;

    @Autowired
    BlogDao blogDao;

    @Autowired
    TagDao tagDao;

    @GetMapping("/admin")
    public String displayAdminPage(Model model) {
        model.addAttribute("users", users.getAllUsers());

        List<Blog> listOfStaticPage = blogDao.getAllStaticPageIfApproved();
        model.addAttribute("staticPage", listOfStaticPage);

        List<Tag> getAllTags = tagDao.getAllTags();
        model.addAttribute("allTags", getAllTags);

        List<Blog> getAllBlogs = blogDao.getAllBlogs();
        model.addAttribute("blogs", getAllBlogs);

        return "admin";
    }

    @PostMapping("/addUser")
    public String addUser(String username, String password) {
        User user = new User();
        user.setUsername(username);
        user.setPassword(encoder.encode(password));
        user.setEnabled(true);

        Set<Role> userRoles = new HashSet<>();
        userRoles.add(roles.getRoleByRole("ROLE_USER"));
        user.setRoles(userRoles);

        users.createUser(user);

        return "redirect:/admin";
    }

    @PostMapping("/deleteUser")
    public String deleteUser(Integer id) {
        users.deleteUser(id);
        return "redirect:/admin";
    }

    @GetMapping("/editUser")
    public String editUserDisplay(Model model, Integer id, Integer error) {
        User user = users.getUserById(id);
        List roleList = roles.getAllRoles();

        model.addAttribute("user", user);
        model.addAttribute("roles", roleList);

        if (error != null) {
            if (error == 1) {
                model.addAttribute("error", "Passwords did not match, password was not updated.");
            }
        }

        return "editUser";
    }

    @PostMapping(value = "/editUser")
    public String editUserAction(String[] roleIdList, Boolean enabled, Integer id) {
        User user = users.getUserById(id);
        if (enabled != null) {
            user.setEnabled(enabled);
        } else {
            user.setEnabled(false);
        }

        Set<Role> roleList = new HashSet<>();
        for (String roleId : roleIdList) {
            Role role = roles.getRoleById(Integer.parseInt(roleId));
            roleList.add(role);
        }
        user.setRoles(roleList);
        users.updateUser(user);

        return "redirect:/admin";
    }

    @PostMapping("editPassword")
    public String editPassword(Integer id, String password, String confirmPassword) {
        User user = users.getUserById(id);

        if (password.equals(confirmPassword)) {
            user.setPassword(encoder.encode(password));
            users.updateUser(user);
            return "redirect:/admin";
        } else {
            return "redirect:/editUser?id=" + id + "&error=1";
        }
    }

    @GetMapping("approveBlog")
    public String approveBlog(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Blog blog = blogDao.getBlogById(id);
        blog.setVarified(true);
        blogDao.updateBlog(blog);
        return "redirect:/admin";
    }

    @GetMapping("deleteBlog")
    public String deleteTeacher(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        Blog blog = blogDao.getBlogById(id);
        blogDao.deleteBlog(blog.getBlogID());
        return "redirect:/admin";
    }

    @GetMapping("editblog")
    public String editBlog(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Blog blog = blogDao.getBlogById(id);
        model.addAttribute("blogtoedit", blog);
        List<Tag> listOfTags = tagDao.getAllTags();
        model.addAttribute("tags", listOfTags);
        List<User> listOfUsers = users.getAllUsers();
        model.addAttribute("users", listOfUsers);
        return "editBlog";
    }

    @PostMapping("editBlog")
    public String performEditBlog(HttpServletRequest request) {
        String title = request.getParameter("title");
        String blogText = request.getParameter("blogInput");
        String expirationDate = request.getParameter("dateOfExpiration");
        String dateOfShow = request.getParameter("dateOfPublishing");
        String staticPage = request.getParameter("static");
        String[] tagIDS = request.getParameterValues("tagsID");
        String userID = request.getParameter("userID");
        String blogID = request.getParameter("blogtoeditID");

        Blog blog = null;

        try {

            blog = blogDao.getBlogById(Integer.parseInt(blogID));
            if (title != null) {
                blog.setTitle(title);
            }
            if (blogText != null) {
                blog.setBlogText(blogText);
            }

            LocalDate Exdate;
            try {
                Exdate = LocalDate.parse(expirationDate);
            } catch (Exception e) {
                Exdate = null;
            }
            blog.setExpirationDate(Exdate);

            LocalDate Showdate;
            try {
                Showdate = LocalDate.parse(dateOfShow);
            } catch (Exception e) {
                Showdate = null;
            }
            blog.setDateOfShow(Showdate);

            if (staticPage == null) {
                blog.setStaticPage(false);
            } else {
                blog.setStaticPage(true);
            }
            List<Tag> listOfTags = new ArrayList<Tag>();
            for (int i = 0; i < tagIDS.length; i++) {
                listOfTags.add(tagDao.getTagById(Integer.parseInt(tagIDS[i])));
            }
            blog.setListOfTags(listOfTags);

            try {
                blog.setUserID(Integer.parseInt(userID));
            } catch (Exception e) {
                blog.setUserID(-9);
            }
        } catch (Exception e) {

        }

        /// verify the blog herer
//        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
//        violations = validate.validate(blog);
//        if (violations.isEmpty()) {
//            blogDao.createBlog(blog);
//        }
        blogDao.updateBlog(blog);

        return "redirect:/admin";
    }

    @PostMapping("/deleteTag")
    public String deleteTag(Integer id) {
        tagDao.deleteTag(id);
        return "redirect:/admin";
    }

    @GetMapping("/editTag")
    public String editTag(HttpServletRequest request, Model model) {
        String tagID = request.getParameter("id");
        Tag tag = tagDao.getTagById(Integer.parseInt(tagID));

        model.addAttribute("tagToEdit", tag);
        return "editTag";
    }

    @PostMapping("updateTag")
    public String updateTag(HttpServletRequest request) {
        String hashTag = request.getParameter("editHashTagName");
        String hashTagID = request.getParameter("HashTagid");
        
        Tag tag = tagDao.getTagById(Integer.parseInt(hashTagID));
        tag.setHashTag(hashTag);

        tagDao.updateTag(tag);
        return "redirect:/admin";
    }
}
