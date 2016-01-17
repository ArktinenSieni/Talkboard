package talkboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import talkboard.domain.Post;
import talkboard.library.FileLocation;
import talkboard.library.Link;
import talkboard.repository.PostRepository;

import javax.validation.Valid;

/**
 * Created by mcsieni on 15.1.2016.
 */
@Controller
@RequestMapping(value = "/posts")
public class PostController {

    @Autowired
    PostRepository postRepository;

    @RequestMapping(method = RequestMethod.GET)
    public String listPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return FileLocation.POSTS.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "/createPost")
    public String creationForm() {
        return FileLocation.POSTFORM.toString();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createPost(@Valid @ModelAttribute Post newPost, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return FileLocation.POSTFORM.toString();
        }

        newPost.setCreated();
        postRepository.save(newPost);

        return Link.REDIRECT_POSTS.toString();
    }
}
