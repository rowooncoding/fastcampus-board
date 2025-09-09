package com.codebene.board.controller;

import com.codebene.board.model.Post;
import com.codebene.board.model.PostPatchRequestBody;
import com.codebene.board.model.PostPostRequestBody;
import com.codebene.board.service.PostService;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/posts")
@Slf4j
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 여러건 조회
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        log.info("GET /api/v1/posts");

        var posts = postService.getPosts();

        return ResponseEntity.ok(posts);
    }

    // 단건 게시물 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable Long postId) {
        log.info("GET /api/v1/posts/{}", postId);

        Post post = postService.getPostByPostId(postId);

        // 존재하면 응답
        return ResponseEntity.ok(post);
    }

    // 게시물 생성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostPostRequestBody postPostRequestBody) {
        log.info("POST /api/v1/posts");

        var post = postService.createPost(postPostRequestBody);
        return ResponseEntity.ok(post);
    }

    // 게시물 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostPatchRequestBody postPatchRequestBody) {
        log.info("PATCH /api/v1/posts/{}", postId);

        var post = postService.updatePost(postId, postPatchRequestBody);
        return ResponseEntity.ok(post);
    }

    // 게시물 삭제
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> deletePost(@PathVariable Long postId) {
        log.info("DELETE /api/v1/posts/{}", postId);

        postService.deletePost(postId);
        return ResponseEntity.noContent().build();
    }
}

