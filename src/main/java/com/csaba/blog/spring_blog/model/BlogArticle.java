package com.csaba.blog.spring_blog.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class BlogArticle  {

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

    @CreatedDate
    private Date cratedAt;

    @CreatedBy
    private BlogUser createdBy;
}
