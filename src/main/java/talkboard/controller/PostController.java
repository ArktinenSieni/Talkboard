package talkboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import talkboard.domain.Post;
import talkboard.domain.PostThread;
import talkboard.library.FileLocation;
import talkboard.library.Link;
import talkboard.repository.PostRepository;
import talkboard.repository.ThreadRepository;
import talkboard.service.ToolService;

import javax.validation.Valid;

/**
 * Created by mcsieni on 15.1.2016.
 */
@Controller
public class PostController {

    @Autowired
    PostRepository postRepository;

    @Autowired
    ToolService toolService;

    @Autowired
    ThreadRepository threadRepository;

    @RequestMapping(value = "/posts", method = RequestMethod.GET)
    public String listPosts(Model model) {
        model.addAttribute("posts", postRepository.findAll());
        return FileLocation.POSTS.toString();
    }

    @RequestMapping(method = RequestMethod.GET, value = "thread/{threadId}/createPost")
    public String creationForm(Model model, @PathVariable Long threadId) {
        model.addAttribute("thread", threadRepository.findOne(threadId));
        return FileLocation.POSTFORM.toString();
    }

    @Transactional
    @RequestMapping(method = RequestMethod.POST, value = "/thread/{threadId}")
    public String createPost(@Valid @ModelAttribute Post newPost,
                             BindingResult bindingResult,
                             RedirectAttributes redirectAttributes,
                             @PathVariable Long threadId) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", toolService.collectErrors(bindingResult));
            redirectAttributes.addFlashAttribute("newPost", newPost);
            redirectAttributes.addFlashAttribute("threadId", threadId);
            return Link.REDIRECT_POSTFORM.toString();
        }

        PostThread thread = threadRepository.findOne(threadId);
        newPost.setCreated();
        newPost = postRepository.save(newPost);
        thread.getPosts().add(newPost);
        thread.setModified();

        redirectAttributes.addFlashAttribute("threadId", threadId);

        return Link.REDIRECT_THREAD.toString();
    }

    @RequestMapping(value = "/thread/{threadId}/{postId}", method = RequestMethod.GET)
    public String editionForm(@PathVariable Long postId, @PathVariable Long threadId, Model model) {
        model.addAttribute("newPost", postRepository.findOne(postId));
        model.addAttribute("thread", threadRepository.findOne(threadId));
        return FileLocation.POSTEDITFORM.toString();
    }

    @Transactional
    @RequestMapping(value = "/thread/{threadId}/{postId}", method = RequestMethod.POST)
    public String editPost(@PathVariable Long postId,
                           @Valid @ModelAttribute Post editedPost,
                           RedirectAttributes redirectAttributes,
                           BindingResult bindingResult,
                           @PathVariable Long threadId) {
        redirectAttributes.addFlashAttribute("threadId", threadId);

        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("postId", postId);
            redirectAttributes.addFlashAttribute("errors", toolService.collectErrors(bindingResult));
            redirectAttributes.addFlashAttribute("newPost", editedPost);
            return Link.REDIRECT_POSTEDIT.toString();
        }

        Post toBeEdited = postRepository.findOne(postId);
        toBeEdited.setName(editedPost.getName());
        toBeEdited.setText(editedPost.getText());
        toBeEdited.setModified();

        return Link.REDIRECT_THREAD.toString();
    }

    @Transactional
    @RequestMapping(value = {"/{threadId}/{postId}/delete"} , method = RequestMethod.POST)
    public String deletePost(@PathVariable Long postId, RedirectAttributes redirectAttributes, @PathVariable Long threadId) {
        Post post = postRepository.findOne(postId);
        PostThread thread = threadRepository.findOne(threadId);
        thread.getPosts().remove(post);
        postRepository.delete(post);

        redirectAttributes.addFlashAttribute("threadId", threadId);
        return Link.REDIRECT_THREAD.toString();
    }
}
