package com.servidor.Practica4.Repos;

import com.servidor.Practica4.Models.Reply;
import com.servidor.Practica4.Models.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
    List<Reply> findByTopicFullEquals(Topic topic);
}
