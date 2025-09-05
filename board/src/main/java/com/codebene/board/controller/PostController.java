package com.codebene.board.controller;

import com.codebene.board.model.Post;
import com.codebene.board.model.PostPatchRequestBody;
import com.codebene.board.model.PostPostRequestBody;
import com.codebene.board.service.PostService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/posts")
public class PostController {

    private final PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    // 게시글 여러건 조회
    @GetMapping
    public ResponseEntity<List<Post>> getPosts() {
        var posts = postService.getPosts();

        return ResponseEntity.ok(posts);
    }

    // 단건 게시물 조회
    @GetMapping("/{postId}")
    public ResponseEntity<Post> getPostByPostId(@PathVariable Long postId) {

        Optional<Post> matchingPost = postService.getPostByPostId(postId);

        // 존재하면 응답
        return matchingPost
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // 게시물 생성
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody PostPostRequestBody postPostRequestBody) {
        var post = postService.createPost(postPostRequestBody);
        return ResponseEntity.ok(post);
    }

    // 게시물 수정
    @PatchMapping("/{postId}")
    public ResponseEntity<Post> updatePost(@PathVariable Long postId, @RequestBody PostPatchRequestBody postPatchRequestBody) {

        var post = postService.updatePost(postId, postPatchRequestBody);
        return ResponseEntity.ok(post);
    }
}

