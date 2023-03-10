package com.servidor.Practica4.Controllers;

import com.servidor.Practica4.Forms.ReplyForm;
import com.servidor.Practica4.Services.ReplyService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
public class ReplyController {
    ReplyService replyService;

    public ReplyController(ReplyService replyService) {
        this.replyService = replyService;
    }

    @PostMapping("/topics/{topicId}/replies")
    public Map<String, Object> postReply(@RequestBody ReplyForm replyForm, @PathVariable long topicId, HttpServletRequest request) {
        return replyService.postReply(replyForm, topicId, request.getAttribute("user"));
    }

    @PutMapping("/topics/{topicId}/replies/{replyId}")
    public Map<String, Object> updateReply(@RequestBody ReplyForm replyForm, @PathVariable long replyId, HttpServletRequest request) {
        return replyService.updateReply(replyForm, replyId, request.getAttribute("user"));
    }

    @DeleteMapping("/topics/{topicId}/replies/{replyId}")
    public Object deleteReply(@PathVariable long replyId, HttpServletRequest request) {
        return replyService.deleteReply(replyId, request.getAttribute("user"));
    }
}
