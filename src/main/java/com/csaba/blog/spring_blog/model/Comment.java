package com.csaba.blog.spring_blog.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Comment extends AuditableEntity<String> {

    public Comment(@NotNull BlogArticle blogArticle, @NotNull BlogUser blogUser, @NotEmpty(message = "Comment text should not be empty") String text) {
        this.blogArticle = blogArticle;
        this.blogUser = blogUser;
        this.text = text;
    }

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
}
