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
public class BlogArticle extends AuditableEntity<String> {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotEmpty(message = "Blog article title cannot be empty")
    private String title;

    @NotEmpty(message = "Blog article text cannot be empty")
    private String text;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            joinColumns = @JoinColumn(name = "blog_article_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "category_name", referencedColumnName = "name")
    )
    Set<Category> categories;

    @ManyToOne
    private BlogUser author;
}
