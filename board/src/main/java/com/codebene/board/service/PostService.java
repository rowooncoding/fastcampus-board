package com.codebene.board.service;

import com.codebene.board.exception.post.PostNotFoundException;
import com.codebene.board.model.Post;
import com.codebene.board.model.PostPatchRequestBody;
import com.codebene.board.model.PostPostRequestBody;
import com.codebene.board.model.entity.PostEntity;
import com.codebene.board.repository.PostEntityRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private final PostEntityRepository postEntityRepository;

    public PostService(PostEntityRepository postEntityRepository) {
        this.postEntityRepository = postEntityRepository;
    }

    // 게시물 여러건 조회
    public List<Post> getPosts() {
        List<PostEntity> postEntities = postEntityRepository.findAll();
        return postEntities.stream().map(Post::from).toList();
    }

    // post 식별자로 게시글 단건 조회
    public Post getPostByPostId(Long postId) {
        PostEntity postEntity = postEntityRepository
                .findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException(postId)
                );

        return Post.from(postEntity);
    }

    // 게시물 생성
    public Post createPost(PostPostRequestBody postPostRequestBody) {
        PostEntity postEntity = new PostEntity();
        postEntity.setBody(postPostRequestBody.body());

        PostEntity savedPostEntity = postEntityRepository.save(postEntity);
        return Post.from(savedPostEntity);
    }

    // 게시물 수정
    public Post updatePost(Long postId, PostPatchRequestBody postPatchRequestBody) {
        PostEntity postEntity = postEntityRepository
                .findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException(postId));

        postEntity.setBody(postPatchRequestBody.body());
        PostEntity updatedPostEntity = postEntityRepository.save(postEntity);
        return Post.from(updatedPostEntity);
    }

    public void deletePost(Long postId) {
        PostEntity postEntity = postEntityRepository
                .findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException(postId));

        postEntityRepository.delete(postEntity);
    }
}
