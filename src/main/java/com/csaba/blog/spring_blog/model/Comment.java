package com.csaba.blog.spring_blog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Comment extends AuditableEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private BlogArticle blogArticle;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private BlogUser blogUser;

    @NotEmpty(message = "Comment text should not be empty")
    private String text;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "comment_user_likes",
            joinColumns = @JoinColumn(name = "comment_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "blog_user_id", referencedColumnName = "id"))
    private Set<BlogUser> likedBy;
}
