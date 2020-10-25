package blog.controller;

import blog.dao.BlogDao;
import blog.dto.Blog;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @Autowired
    BlogDao blogDao;

    @GetMapping({"/", "/home"})
    public String displayHomePage(Model model) {
        List<Blog> listOfBlogs = blogDao.getAllBlogIfApproved();
        List<Blog> listOfStaticPage = blogDao.getAllStaticPageIfApproved();
        model.addAttribute("staticPage", listOfStaticPage);
        model.addAttribute("blogs", listOfBlogs);
        return "home";
    }


}
