package com.servidor.Practica4.Controllers;

import com.servidor.Practica4.Forms.ReplyForm;
import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Services.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class TopicController {
    TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @CrossOrigin
    @GetMapping("categories/{slug}/topics")
    public List<Map<String,Object>> getTopics(@PathVariable String slug, HttpServletResponse response) {
        return topicService.getAllTopics(slug);
    }

    @CrossOrigin
    @PostMapping("/topics")
    public Topic createTopic(@RequestBody TopicForm topicForm, HttpServletRequest request) {
        Topic topic = topicService.createTopic(topicForm, request.getAttribute("user"));
        return topic;
    }

    @CrossOrigin
    @GetMapping("/topics/{topicId}")
    public Map<String, Object> getTopic(@PathVariable long topicId) {
        return topicService.getTopic(topicId);
    }

}
