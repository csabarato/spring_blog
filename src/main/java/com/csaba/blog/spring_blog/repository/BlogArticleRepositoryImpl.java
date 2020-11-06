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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public class BlogArticleRepositoryImpl implements BlogArticleRepositoryCustom {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public List<BlogArticle> searchByParams(Optional<String> titleOpt, Optional<String> authorOpt, Optional<String> textOpt,
                                            Optional<Date> dateFromOpt, Optional<Date> dateToOpt) {

        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BlogArticle> cq = cb.createQuery(BlogArticle.class);

        Root<BlogArticle> blogArticleRoot = cq.from(BlogArticle.class);

        List<Predicate> predicates = new ArrayList<>();

        titleOpt.ifPresent(title -> predicates.add(cb.equal(blogArticleRoot.get("title"), title)));
        authorOpt.ifPresent(author -> predicates.add(cb.equal(blogArticleRoot.get("author").get("username"), author)));

        textOpt.ifPresent(text -> predicates.add(cb.like(blogArticleRoot.get("text") , '%'+text+'%' )) );

        dateFromOpt.ifPresent(date -> predicates.add(cb.greaterThanOrEqualTo(blogArticleRoot.<Date>get("createdAt"), date)));
        dateToOpt.ifPresent(date -> predicates.add(cb.lessThanOrEqualTo(blogArticleRoot.<Date>get("createdAt"), date)));

        cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));
        cq.orderBy(cb.desc(blogArticleRoot.get("createdAt")));
        return entityManager.createQuery(cq).getResultList();
    }
}
