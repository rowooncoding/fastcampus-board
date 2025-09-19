package com.codebene.board.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.ZonedDateTime;

@Entity
@Table(
        name = "\"like\"",
        indexes = {@Index(name = "like_userid_postid_idx", columnList = "user_id, post_id", unique = true)}
        )
@Getter @Setter
public class LikeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column
    private ZonedDateTime createdDateTime;

    @ManyToOne
    @JoinColumn(name = "userId")
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "postId")
    private PostEntity post;

    public static LikeEntity of(UserEntity user, PostEntity post) {
        LikeEntity like = new LikeEntity();
        like.setUser(user);
        like.setPost(post);
        return like;
    }

    // 저장 전처리
    @PrePersist
    private void prePersist() {
        this.createdDateTime = ZonedDateTime.now();
    }
}
