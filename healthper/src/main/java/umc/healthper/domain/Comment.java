package umc.healthper.domain;

import lombok.Getter;
import umc.healthper.global.BaseTimeEntity;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.FetchType.*;
import static umc.healthper.domain.CommentStatus.*;

@Entity
@Getter
public class Comment extends BaseTimeEntity {

    @Id
    @GeneratedValue
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    private String Content;

    @Enumerated(EnumType.STRING)
    private CommentStatus status;    // NORMAL, REMOVED, BLOCKED

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent")
    private List<Comment> children = new ArrayList<>();

    //== 생성 메서드 ==//
    public static Comment createComment(Member member, Post post, String content) {
        Comment comment = new Comment();
        comment.setMember(member);
        comment.setPost(post);
        comment.setContent(content);
        comment.setStatus(NORMAL);
        return comment;
    }

    public static Comment createNestedComment(Member member, Post post, Comment parent, String content) {
        Comment comment = createComment(member, post, content);
        parent.addChildComment(comment);
        return comment;
    }

    //== 연관관계 편의 Method ==//
    public void setPost(Post post) {
        this.post = post;
        post.getComments().add(this);
    }

    public void addChildComment(Comment child) {
        this.getChildren().add(child);
        child.setParent(this);
    }

    //== Setter ==//
    private void setId(Long id) {
        this.id = id;
    }

    private void setMember(Member member) {
        this.member = member;
    }

    private void setContent(String content) {
        Content = content;
    }

    private void setStatus(CommentStatus status) {
        this.status = status;
    }

    private void setParent(Comment parent) {
        this.parent = parent;
    }
}
