package blog.controller;

import blog.dao.BlogDao;
import blog.dao.TagDao;
import blog.dao.UserDao;
import blog.dto.Blog;
import blog.dto.Tag;
import blog.dto.User;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
//import java.util.HashSet;
import java.util.List;
import java.util.Set;
//import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class ContentController {

    @Autowired
    BlogDao blogDao;

    @Autowired
    TagDao tagDao;

    @Autowired
    UserDao userDao;

    Set<ConstraintViolation<Tag>> violationsTag = new HashSet<>();

    Set<ConstraintViolation<Blog>> violationsBlog = new HashSet<>();

    @GetMapping("/content")
    public String displayContentPage(Model model) {

        List<Blog> listOfStaticPage = blogDao.getAllStaticPageIfApproved();
        List<Tag> listOfTags = tagDao.getAllTags();
        List<User> listOfUsers = userDao.getAllUsers();

        model.addAttribute("staticPage", listOfStaticPage);

        //added for errors
        if (violationsBlog.size() > 0) {
            violationsTag.clear();
        }
        if (violationsTag.size() > 0) {
            violationsBlog.clear();
        }
        model.addAttribute("errors", violationsTag);
        model.addAttribute("errorsBlog", violationsBlog);

        model.addAttribute("tags", listOfTags);
        model.addAttribute("users", listOfUsers);

        return "content";
    }

    @PostMapping("addBlog")
    public String addBlog(HttpServletRequest request) {
        String title = request.getParameter("title");
        String blogText = request.getParameter("blogInput");
        String expirationDate = request.getParameter("dateOfExpiration");
        String dateOfShow = request.getParameter("dateOfPublishing");
        String staticPage = request.getParameter("static");
        String[] tagIDS = null;
        try {
            tagIDS = request.getParameterValues("tagsID");
        } catch (Exception e) {

        }

        String userID = request.getParameter("userID");

        Blog blog = new Blog();
        blog.setTitle(title);
        blog.setBlogText(blogText);
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
        if (tagIDS != null) {
            for (int i = 0; i < tagIDS.length; i++) {
                listOfTags.add(tagDao.getTagById(Integer.parseInt(tagIDS[i])));
            }
        }
        blog.setListOfTags(listOfTags);

        try {
            blog.setUserID(Integer.parseInt(userID));
        } catch (Exception e) {
            blog.setUserID(-9);
        }

        /// verify the blog herer
        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsBlog = validate.validate(blog);
        if (violationsBlog.isEmpty()) {
            blogDao.createBlog(blog);
        }
        
        // blogDao.createBlog(blog);

        return "redirect:/content";
    }

    @PostMapping("addTag")
    public String addTag(HttpServletRequest request) {
        String hashTag = request.getParameter("tagInput");
        Tag tag = new Tag();
        tag.setHashTag(hashTag);
        ///validate tags here 

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();
        violationsTag = validate.validate(tag);

        if (violationsTag.isEmpty()) {
            tagDao.createTag(tag);
        }

        //tagDao.createTag(tag);
        return "redirect:/content";
    }

}
