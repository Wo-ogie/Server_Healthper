package umc.healthper.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import umc.healthper.domain.Post;
import umc.healthper.domain.PostStatus;
import umc.healthper.dto.post.UpdatePostRequestDto;
import umc.healthper.repository.post.PostRepository;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;

    /**
     * Post 등록(저장)
     */
    @Transactional
    public Post savePost(Post post) {
        return postRepository.save(post);
    }

    /**
     * Post 목록 조회 - Paging
     */
    public Page<Post> findPosts(Pageable pageable) {
        return postRepository.findAllByStatusNot(pageable, PostStatus.REMOVED);
    }

    /**
     * Post 조회 - id
     */
    public Post findPost(Long postId) {
        validatePost(postId);
        Post post = postRepository.findById(postId).get();
        post.addViewCount();
        return post;
    }

    /**
     * Post 수정
     */
    @Transactional
    public void updatePost(Long postId, UpdatePostRequestDto postDto) {
        validatePost(postId);
        Post findPost = postRepository.findById(postId).get();
        findPost.update(postDto.getTitle(), postDto.getContent());
    }

    /**
     * Post 삭제
     */
    @Transactional
    public void removePost(Long postId) {
        validatePost(postId);
        postRepository.removePost(postId);
    }

    // 존재하지 않는 게시글인지, 이미 삭제된 게시글인지. 게시글 유효성 검증
    private void validatePost(Long postId) {
        Optional<Post> findPost = postRepository.findById(postId);
        if (findPost.isEmpty()) {
            throw new IllegalStateException("존재하지 않는 게시글입니다.");
        } else if (findPost.get().getStatus() == PostStatus.REMOVED) {
            throw new IllegalStateException("이미 삭제된 게시글입니다.");
        }
    }
}
