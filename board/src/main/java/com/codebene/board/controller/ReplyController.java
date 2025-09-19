package com.codebene.board.controller;

import com.codebene.board.model.entity.UserEntity;
import com.codebene.board.model.post.Post;
import com.codebene.board.model.post.PostPatchRequestBody;
import com.codebene.board.model.post.PostPostRequestBody;
import com.codebene.board.model.post.ReplyPostRequestBody;
import com.codebene.board.model.reply.Reply;
import com.codebene.board.model.reply.ReplyPatchRequestBody;
import com.codebene.board.service.PostService;
import com.codebene.board.service.ReplyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts/{postId}/replies")
public class ReplyController {

    private final PostService postService;
    private final ReplyService replyService;

    public ReplyController(PostService postService, ReplyService replyService) {
        this.postService = postService;
        this.replyService = replyService;
    }

    // 게시글 여러건 조회
    @GetMapping
    public ResponseEntity<List<Reply>> getRepliesByPostId(@PathVariable Long postId) {
        var replies = replyService.getRepliesByPostId(postId);
        return ResponseEntity.ok(replies);
    }

    // 게시물 생성
    @PostMapping
    public ResponseEntity<Reply> createReply(@PathVariable Long postId, @RequestBody ReplyPostRequestBody replyPostRequestBody, Authentication authentication) {
        var reply = replyService.createReply(postId, replyPostRequestBody, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(reply);
    }

    // 게시물 수정
    @PatchMapping("/{replyId}")
    public ResponseEntity<Reply> updatePost(@PathVariable Long postId, @PathVariable Long replyId, @RequestBody ReplyPatchRequestBody replyPatchRequestBody, Authentication authentication) {
        var reply = replyService.updateReply(postId, replyId, replyPatchRequestBody, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.ok(reply);
    }

    // 게시물 삭제
    @DeleteMapping("/{replyId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId, @PathVariable Long replyId, Authentication authentication) {
        replyService.deleteReply(postId, replyId, (UserEntity) authentication.getPrincipal());
        return ResponseEntity.noContent().build();
    }
}

