package umc.healthper.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import umc.healthper.domain.Member;
import umc.healthper.domain.Post;

import static org.assertj.core.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class PostLikeServiceTest {

    @Autowired MemberService memberService;
    @Autowired PostService postService;
    @Autowired PostLikeService postLikeService;

    @Test
    public void 좋아요_추가() throws Exception {
        // given
        Member member1 = Member.createMember(100L, "James");
        memberService.join(member1);
        Member member2 = Member.createMember(101L, "Anne");
        memberService.join(member2);
        Member member3 = Member.createMember(102L, "Max");
        memberService.join(member3);

        Post post1 = Post.createPost(member1, "제목" + 1, "테스트를 위한 " + 1 + "번 게시글");
        postService.savePost(post1);
        Post post2 = Post.createPost(member1, "제목" + 2, "테스트를 위한 " + 2 + "번 게시글");
        postService.savePost(post2);
        Post post3 = Post.createPost(member1, "제목" + 3, "테스트를 위한 " + 3 + "번 게시글");
        postService.savePost(post3);

        // when
        postLikeService.addLike(member1.getId(), post1.getId());
        postLikeService.addLike(member1.getId(), post2.getId());
        postLikeService.addLike(member2.getId(), post1.getId());
        postLikeService.addLike(member2.getId(), post3.getId());
        postLikeService.addLike(member3.getId(), post1.getId());

        System.out.println("post1.getLikes() = " + post1.getLikes().toString());
        System.out.println("member1.getPostLikes() = " + member1.getPostLikes().toString());

        // then
        assertThat(member1.getPostLikes().size()).isEqualTo(2);
        assertThat(member2.getPostLikes().size()).isEqualTo(2);
        assertThat(member3.getPostLikes().size()).isEqualTo(1);
        assertThat(post1.getLikes().size()).isEqualTo(3);
        assertThat(post2.getLikes().size()).isEqualTo(1);
        assertThat(post3.getLikes().size()).isEqualTo(1);
    }

    @Test(expected = IllegalStateException.class)
    public void 중복_좋아요() throws Exception {
        // given
        Member member1 = Member.createMember(100L, "James");
        memberService.join(member1);

        Post post1 = Post.createPost(member1, "제목" + 1, "테스트를 위한 " + 1 + "번 게시글");
        postService.savePost(post1);

        // when
        postLikeService.addLike(member1.getId(), post1.getId());
        postLikeService.addLike(member1.getId(), post1.getId());

        // then
        fail("중복으로 좋아요 했기 때문에 exception이 발생해야 한다.");
    }
}