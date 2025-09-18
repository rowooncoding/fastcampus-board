package com.codebene.board.service;

import com.codebene.board.exception.post.PostNotFoundException;
import com.codebene.board.exception.user.UserNotAllowedException;
import com.codebene.board.exception.user.UserNotFoundException;
import com.codebene.board.model.entity.UserEntity;
import com.codebene.board.model.post.Post;
import com.codebene.board.model.post.PostPatchRequestBody;
import com.codebene.board.model.post.PostPostRequestBody;
import com.codebene.board.model.entity.PostEntity;
import com.codebene.board.model.user.User;
import com.codebene.board.repository.PostEntityRepository;
import com.codebene.board.repository.UserEntityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService {

    private final PostEntityRepository postEntityRepository;
    private final UserEntityRepository userEntityRepository;

    public PostService(PostEntityRepository postEntityRepository, UserEntityRepository userEntityRepository) {
        this.postEntityRepository = postEntityRepository;
        this.userEntityRepository = userEntityRepository;
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
    public Post createPost(PostPostRequestBody postPostRequestBody, UserEntity currentUser) {
        PostEntity savedPostEntity = postEntityRepository.save(PostEntity.of(postPostRequestBody.body(), currentUser));
        return Post.from(savedPostEntity);
    }

    // 게시물 수정
    public Post updatePost(Long postId, PostPatchRequestBody postPatchRequestBody, UserEntity currentUser) {
        PostEntity postEntity = postEntityRepository
                .findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException(postId));

        // 작성자와 수정자 동일인 체크
        if (!postEntity.getUser().equals(currentUser)) {
            throw new UserNotAllowedException();
        }

        postEntity.setBody(postPatchRequestBody.body());
        PostEntity updatedPostEntity = postEntityRepository.save(postEntity);
        return Post.from(updatedPostEntity);
    }

    public void deletePost(Long postId, UserEntity currentUser) {
        PostEntity postEntity = postEntityRepository
                .findById(postId)
                .orElseThrow(
                        () -> new PostNotFoundException(postId));

        // 작성자와 수정자 동일인 체크
        if (!postEntity.getUser().equals(currentUser)) {
            throw new UserNotAllowedException();
        }

        postEntityRepository.delete(postEntity);
    }

    public List<Post> getPostsByUsername(String username) {
        UserEntity userEntity = userEntityRepository
                .findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));

        List<PostEntity> postEntities = postEntityRepository.findByUser(userEntity);
        return postEntities.stream().map(Post::from).toList();
    }
}
