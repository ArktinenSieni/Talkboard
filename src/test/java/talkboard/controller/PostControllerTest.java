package talkboard.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import talkboard.Application;
import talkboard.domain.Post;
import talkboard.library.Link;
import talkboard.repository.PostRepository;

import static junit.framework.TestCase.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by mcsieni on 18.1.2016.
 */
@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PostControllerTest {

    @Autowired
    PostRepository postRepository;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;
    private String URI = Link.POSTS.toString();

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void userCanCreatePost() throws Exception{
        int postCount = (int) postRepository.count();

        String testName = "Test 1";
        String testText = "Contains testing text";

        mockMvc.perform(post(URI)
                    .param("name", testName).param("text", testText))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertTrue(postCount < postRepository.count() && postRepository.findAll().stream().filter(p -> p.getName().equals(testName)).findFirst().isPresent());
    }

    @Test
    public void postAttributesAreChecked() throws Exception{
        int postCount = (int) postRepository.count();

        String testName = "Huuhaata";
        String testText = "";

        mockMvc.perform(post(URI).param("name", testName).param("text", testText))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertTrue(postCount == (int) postRepository.count());
    }

    @Test
    public void userCanModifyPosts() throws Exception{
        Post testPost = new Post();
        String testName = "Test 2";
        testPost.setName(testName);
        testPost.setText("Testing...");
        testPost.setCreated();
        testPost = postRepository.save(testPost);
        int postCount = (int) postRepository.count();
        String modifiedText = "Testing is fun!";

        mockMvc.perform(post("" + Link.POSTS + "/" +  testPost.getId()).param("name", testName).param("text", modifiedText))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertTrue(postRepository.findOne(testPost.getId()).getText().equals(modifiedText) && (int) postRepository.count() == postCount);
    }

    @Test
    public void userSeesWhenPostIsModified() throws Exception {
        Post testPost = new Post();
        String testName = "Test 3";
        String testText = "Testing...";
        testPost.setName(testName);
        testPost.setText(testText);
        testPost.setCreated();
        testPost = postRepository.save(testPost);

        testText = "Mixing up";

        mockMvc.perform(post("" + Link.POSTS + "/" + testPost.getId()).param("name", testName).param("text", testText))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertTrue(postRepository.findOne(testPost.getId()).getModified() != null);
    }

    @Test
    public void userCanDeletePost() throws Exception {
        Post testPost = new Post();
        testPost.setName("Test 3");
        testPost.setText("Let's delete this");
        testPost.setCreated();
        testPost = postRepository.save(testPost);

        int postCount = (int) postRepository.count();
        mockMvc.perform(post("" + Link.POSTS + "/" + testPost.getId() + "/delete"))
                .andExpect(status().is3xxRedirection())
                .andReturn();

        assertTrue(postRepository.count() < postCount);
    }
}

