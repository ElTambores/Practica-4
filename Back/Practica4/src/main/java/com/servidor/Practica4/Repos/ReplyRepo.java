package com.servidor.Practica4.Repos;

import com.servidor.Practica4.Models.Reply;
import com.servidor.Practica4.Models.Topic;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
    List<Reply> findByTopicEquals(Topic topic);

    @Modifying
    @Transactional
    @Query("UPDATE Reply r SET r.content=:newContent WHERE r._id=:replyId")
    void update(long replyId, String newContent);
}
