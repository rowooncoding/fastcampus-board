package com.codebene.board.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;

@Entity
@Table(
        name = "reply",
        indexes = {@Index(name = "post_userid_idx", columnList = "user_id")}
        )
@Getter @Setter
@SQLDelete(sql = "UPDATE reply SET deleted_date_time = CURRENT_TIMESTAMP WHERE reply_id = ?")
@SQLRestriction("deleted_date_time IS NULL")
public class ReplyEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long replyId;

    @Column(columnDefinition = "TEXT")
    private String body;

    @Column
    private ZonedDateTime createdDateTime;

    @Column
    private ZonedDateTime updatedDateTime;

    @Column
    private ZonedDateTime deletedDateTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "postId")
    private PostEntity post;

    public static ReplyEntity of(String body, UserEntity user, PostEntity post) {
        ReplyEntity reply = new ReplyEntity();
        reply.setBody(body);
        reply.setUser(user);
        reply.setPost(post);
        return reply;
    }

    // 저장 전처리
    @PrePersist
    private void prePersist() {
        this.createdDateTime = ZonedDateTime.now();
        this.updatedDateTime = this.createdDateTime;
    }

    // 수정 전처리
    @PreUpdate
    private void preUpdate() {
        this.updatedDateTime = ZonedDateTime.now();
    }
}
