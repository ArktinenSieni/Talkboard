package talkboard.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import talkboard.Repository.PostRepository;

/**
 * Created by mcsieni on 15.1.2016.
 */
@Controller
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    public String listPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return "post/posts";
    }

    public String creationForm() {
        return "post/postForm";
    }
}
