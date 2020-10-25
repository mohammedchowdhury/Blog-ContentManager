package blog.controller;

import blog.dao.BlogDao;
import blog.dto.Blog;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author mohammedchowdhury
 */
@Controller
public class StaticPageController {

    @Autowired
    BlogDao blogDao;

    @GetMapping({"/displayStaticPage"})
    public String displayStaticPage(HttpServletRequest request, Model model) {
        
        List<Blog> listOfStaticPage = blogDao.getAllStaticPageIfApproved();
        model.addAttribute("staticPage", listOfStaticPage);
        int id = Integer.parseInt(request.getParameter("id"));
        Blog blog = blogDao.getBlogById(id);
        String a = "a";
         model.addAttribute("blog", blog);
        return "staticpage";
    }
}