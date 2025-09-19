package com.codebene.board.service;

import com.codebene.board.exception.post.PostNotFoundException;
import com.codebene.board.exception.reply.ReplyNotFoundException;
import com.codebene.board.exception.user.UserNotAllowedException;
import com.codebene.board.exception.user.UserNotFoundException;
import com.codebene.board.model.entity.PostEntity;
import com.codebene.board.model.entity.ReplyEntity;
import com.codebene.board.model.entity.UserEntity;
import com.codebene.board.model.post.Post;
import com.codebene.board.model.post.PostPatchRequestBody;
import com.codebene.board.model.post.PostPostRequestBody;
import com.codebene.board.model.post.ReplyPostRequestBody;
import com.codebene.board.model.reply.Reply;
import com.codebene.board.model.reply.ReplyPatchRequestBody;
import com.codebene.board.repository.PostEntityRepository;
import com.codebene.board.repository.ReplyEntityRepository;
import com.codebene.board.repository.UserEntityRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ReplyService {


    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;
    private final ReplyEntityRepository replyEntityRepository;

    public ReplyService(PostEntityRepository postEntityRepository, UserEntityRepository userEntityRepository, ReplyEntityRepository replyEntityRepository) {
        this.postEntityRepository = postEntityRepository;
        this.userEntityRepository = userEntityRepository;
        this.replyEntityRepository = replyEntityRepository;
    }

    // 게시글 당 댓글 조회
    public List<Reply> getRepliesByPostId(Long postId) {
        PostEntity postEntity = postEntityRepository
                .findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException(postId)
                );

        List<ReplyEntity> replyEntities = replyEntityRepository. findByPost(postEntity);

        return replyEntities.stream().map(Reply::from).toList();
    }

    @Transactional
    public Reply createReply(Long postId, ReplyPostRequestBody replyPostRequestBody, UserEntity currentUser) {
        PostEntity postEntity = postEntityRepository
                .findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException(postId)
                );

        ReplyEntity replyEntity = replyEntityRepository.save(
                ReplyEntity.of(replyPostRequestBody.body(), currentUser, postEntity)
        );

        postEntity.setRepliesCount(postEntity.getRepliesCount() + 1);

        return Reply.from(replyEntity);
    }

    public Reply updateReply(Long postId, Long replyId, ReplyPatchRequestBody replyPatchRequestBody, UserEntity currentUser) {
        ReplyEntity replyEntity = replyEntityRepository
                .findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException(replyId));

        if (!replyEntity.getUser().equals(currentUser)) {
            throw new UserNotAllowedException();
        }

        replyEntity.setBody(replyPatchRequestBody.body());
        return Reply.from(replyEntityRepository.save(replyEntity));
    }

    @Transactional
    public void deleteReply(Long postId, Long replyId, UserEntity currentUser) {
        PostEntity postEntity = postEntityRepository
                .findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException(postId)
                );

        ReplyEntity replyEntity = replyEntityRepository
                .findById(replyId)
                .orElseThrow(() -> new ReplyNotFoundException(replyId));

        if (!replyEntity.getUser().equals(currentUser)) {
            throw new UserNotAllowedException();
        }

        replyEntityRepository.delete(replyEntity);

        postEntity.setRepliesCount(Math.max(0, postEntity.getRepliesCount() - 1));
        postEntityRepository.save(postEntity);
    }
}
