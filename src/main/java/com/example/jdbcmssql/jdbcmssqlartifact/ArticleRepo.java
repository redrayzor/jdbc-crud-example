package com.example.jdbcmssql.jdbcmssqlartifact;
import java.util.List;

public interface ArticleRepo {
    int save(Article book);
    int update(Article book);
    Article findById(Long id);
    int deleteById(Long id);
    List<Article> findAll();
    List<Article> findByPublished(boolean published);
    List<Article> findByTitleContaining(String title);
    int deleteAll();
}
