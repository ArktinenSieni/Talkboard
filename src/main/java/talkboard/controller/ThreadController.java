package talkboard.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import talkboard.library.FileLocation;
import talkboard.library.Link;
import talkboard.repository.PostRepository;
import talkboard.repository.ThreadRepository;
import talkboard.domain.PostThread;
import talkboard.service.ToolService;

import javax.validation.Valid;
import java.util.ArrayList;

/**
 * Created by mcsieni on 19.1.2016.
 */
@Controller
@RequestMapping(value = "thread")
public class ThreadController {

    @Autowired
    ThreadRepository threadRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    ToolService toolService;

    @RequestMapping(method = RequestMethod.GET)
    public String listThreads(Model model) {
        model.addAttribute("threads", threadRepository.findAllByOrderByModifiedDesc());
        return FileLocation.THREADS.toString();
    }

    @RequestMapping(value = "/{id}",method = RequestMethod.GET)
    public String viewThread(Model model, @PathVariable Long id) {
        model.addAttribute("thread", threadRepository.findOne(id));
        return FileLocation.THREAD.toString();
    }

    @RequestMapping(value = "/new", method = RequestMethod.GET)
    public String createThreadForm() {
        return FileLocation.THREAD_FORM.toString();
    }

    @RequestMapping(method = RequestMethod.POST)
    public String createThread(@Valid @ModelAttribute PostThread newThread, BindingResult bindingResult, RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", toolService.collectErrors(bindingResult));
            redirectAttributes.addFlashAttribute("thread", newThread);
            return Link.REDIRECT_NEWTHREAD.toString();
        }
        newThread.setPosts(new ArrayList<>());
        newThread.setModified();
        threadRepository.save(newThread);
        return Link.REDIRECT_THREADS.toString();
    }

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String editThreadForm(Model model, @PathVariable Long id) {
        model.addAttribute("thread", threadRepository.findOne(id));
        return FileLocation.THREAD_EDIT.toString();
    }

    @Transactional
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.POST)
    public String editThread(@Valid @ModelAttribute PostThread editedThread, BindingResult bindingResult, RedirectAttributes redirectAttributes, @PathVariable Long id) {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("errors", toolService.collectErrors(bindingResult));
            redirectAttributes.addFlashAttribute("thread", editedThread);
            redirectAttributes.addFlashAttribute("id", id);

            return Link.REDIRECT_EDITTHREAD.toString();
        }

        PostThread threadToEdit = threadRepository.findOne(id);
        threadToEdit.setName(editedThread.getName());

        return Link.REDIRECT_THREADS.toString();
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.POST)
    public String deleteThread(@PathVariable Long id) {
        threadRepository.delete(id);
        return Link.REDIRECT_THREADS.toString();
    }
}
