package talkboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import talkboard.domain.Post;
import talkboard.library.FileLocation;
import talkboard.library.Link;
import talkboard.repository.PostRepository;
import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

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
    public String createPost(@Valid @ModelAttribute Post newPost, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", collectErrors(bindingResult));
            redirectAttributes.addFlashAttribute("newPost", newPost);
            return Link.REDIRECT_POSTFORM.toString();
        }

        newPost.setCreated();
        postRepository.save(newPost);

        return Link.REDIRECT_POSTS.toString();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String editionForm(@PathVariable Long id, Model model) {
        model.addAttribute("newPost", postRepository.findOne(id));
        return FileLocation.POSTEDITFORM.toString();
    }

    @Transactional
    @RequestMapping(value = "/{id}", method = RequestMethod.POST)
    public String editPost(@PathVariable Long id, @Valid @ModelAttribute Post editedPost, RedirectAttributes redirectAttributes, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("id", id);
            redirectAttributes.addFlashAttribute("errors", collectErrors(bindingResult));
            redirectAttributes.addFlashAttribute("newPost", editedPost);
            return Link.REDIRECT_POSTEDIT.toString();
        }

        Post toBeEdited = postRepository.findOne(id);
        toBeEdited.setName(editedPost.getName());
        toBeEdited.setText(editedPost.getText());
        toBeEdited.setModified();

        return Link.REDIRECT_POSTS.toString();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deletePost(@PathVariable Long id) {
        postRepository.delete(id);
        return Link.REDIRECT_POSTS.toString();
    }

    private List<String> collectErrors (BindingResult bindingResult) {
        List<String> errors = bindingResult.getAllErrors().stream()
                .map(e -> e.getDefaultMessage())
                .collect(Collectors.toList());
        return errors;
    }
}
