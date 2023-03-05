package com.servidor.Practica4.Controllers;

import com.servidor.Practica4.Forms.TopicForm;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Services.TopicService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class TopicController {
    TopicService topicService;

    public TopicController(TopicService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("categories/{slug}/topics")
    public List<Map<String, Object>> getTopics(@PathVariable String slug) {
        return topicService.getAllTopics(slug);
    }

    @PostMapping("/topics")
    public Topic createTopic(@RequestBody TopicForm topicForm, HttpServletRequest request) {
        return topicService.createTopic(topicForm, request.getAttribute("user"));
    }

    @GetMapping("/topics/{topicId}")
    public Map<String, Object> getTopic(@PathVariable long topicId) {
        return topicService.getTopic(topicId);
    }

    @PutMapping("/topics/{topicId}")
    public Map<String, Object> updateTopic(@RequestBody TopicForm topicForm, @PathVariable long topicId, HttpServletRequest request) {
        return topicService.updateTopic(topicForm, topicId, request.getAttribute("user"));
    }

    @DeleteMapping("/topics/{topicId}")
    public Object deleteReply(@PathVariable long topicId, HttpServletRequest request) {
        return topicService.deleteTopic(topicId, request.getAttribute("user"));
    }
}
