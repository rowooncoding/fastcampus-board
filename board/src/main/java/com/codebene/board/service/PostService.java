package com.codebene.board.service;

import com.codebene.board.model.Post;
import com.codebene.board.model.PostPostRequestBody;
import org.springframework.stereotype.Service;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {


    private static final List<Post> posts = new ArrayList<>();

    static {
        posts.add(new Post(1L, "Post 1",ZonedDateTime.now()));
        posts.add(new Post(2L, "Post 2", ZonedDateTime.now()));
        posts.add(new Post(3L, "Post 3", ZonedDateTime.now()));
    }

    // 게시물 여러건 조회
    public List<Post> getPosts() {
        return posts;
    }

    // post 식별자로 게시글 단건 조회
    public Optional<Post> getPostByPostId(Long postId) {
        return posts.stream()
                .filter(post -> postId.equals(post.postId()))
                .findFirst();
    }

    public Post createPost(PostPostRequestBody postPostRequestBody) {
        var newPostId = posts.stream().mapToLong(Post::postId).max().orElse(0L) + 1;

        var newPost = new Post(newPostId, postPostRequestBody.body(), ZonedDateTime.now());
        posts.add(newPost);

        return newPost;
    }
}
