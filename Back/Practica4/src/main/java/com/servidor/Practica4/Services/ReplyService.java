package com.servidor.Practica4.Services;

import com.servidor.Practica4.Builders.ReplyBuilder;
import com.servidor.Practica4.Builders.UserBuilder;
import com.servidor.Practica4.Exceptions.ErrorUpdatingException;
import com.servidor.Practica4.Forms.ReplyForm;
import com.servidor.Practica4.Models.Reply;
import com.servidor.Practica4.Models.Topic;
import com.servidor.Practica4.Models.User;
import com.servidor.Practica4.Repos.ReplyRepo;
import com.servidor.Practica4.Repos.TopicRepo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
public class ReplyService {
    ReplyRepo replyRepo;
    TopicRepo topicRepo;

    public ReplyService(ReplyRepo replyRepo, TopicRepo topicRepo) {
        this.replyRepo = replyRepo;
        this.topicRepo = topicRepo;
    }

    UserBuilder userBuilder = new UserBuilder();
    ReplyBuilder replyBuilder = new ReplyBuilder();

    public Map<String, Object> postReply(ReplyForm replyForm, long topicId, Object userInfo) {
        Topic topic = getTopicById(topicId);
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        Reply reply = replyRepo.save(replyBuilder.fromForm(replyForm, topic, user));

        return replyBuilder.getJson(reply);
    }

    private Topic getTopicById(long topicId) {
        Optional<Topic> optional = topicRepo.findById(topicId);
        return optional.orElse(null);
    }

    public Map<String, Object> updateReply(ReplyForm replyForm, long replyId, Object userInfo) {
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        Reply reply = replyRepo.findById(replyId).orElseGet(null);

        if (user.getRole().equals("admin") || user.getId().equals(reply.getUser().getId())) {
            try {
                Date updateDate = new Date(System.currentTimeMillis());
                replyRepo.update(replyId, replyForm.getContent(), updateDate);
                Reply newReply = replyRepo.findById(replyId).orElseGet(null);
                if (newReply == null) throw new ErrorUpdatingException();
                return replyBuilder.getJson(newReply);
            } catch (Exception e) {
                throw new ErrorUpdatingException();
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    public Boolean deleteReply(long replyId, Object userInfo) {
        User user = userBuilder.fromUserInfo((Map<String, Object>) userInfo);
        Reply reply = replyRepo.findById(replyId).orElseGet(null);
        if (user.getRole().equals("admin") || user.getId().equals(reply.getUser().getId())) {
            try {
                replyRepo.deleteById(replyId);
                return true;
            } catch (Exception e) {
                return false;
            }
        }
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }
}