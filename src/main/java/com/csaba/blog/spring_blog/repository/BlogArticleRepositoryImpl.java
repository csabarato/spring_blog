package com.csaba.blog.spring_blog.repository;

import com.csaba.blog.spring_blog.model.BlogArticle;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class BlogArticleRepositoryImpl implements BlogArticleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<BlogArticle> searchByParams(Optional<String> titleParam, Optional<String> authorParam) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BlogArticle> cq = cb.createQuery(BlogArticle.class);

        Root<BlogArticle> blogArticleRoot = cq.from(BlogArticle.class);

        List<Predicate> predicates = new ArrayList<>();

        titleParam.ifPresent(title -> predicates.add(cb.equal(blogArticleRoot.get("title"), title)));
        authorParam.ifPresent(author -> predicates.add(cb.equal(blogArticleRoot.get("author"), author)));

        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        cq.orderBy(cb.desc(blogArticleRoot.get("createdAt")));
        return entityManager.createQuery(cq).getResultList();
    }
}
