package com.example.jdbcmssql.jdbcmssqlartifact;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class JdbcArticleRepo implements ArticleRepo {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Override
    public int save(Article article) {
        return jdbcTemplate.update("INSERT INTO articles (title, description, published) VALUES(?,?,?)",
                new Object[] { article.getTitle(), article.getDescription(), article.isPublished() });
    }
    @Override
    public int update(Article article) {
        return jdbcTemplate.update("UPDATE articles SET title=?, description=?, published=? WHERE id=?",
                new Object[] { article.getTitle(), article.getDescription(), article.isPublished(), article.getId() });
    }
    @Override
    public Article findById(Long id) {
        try {
            Article article = jdbcTemplate.queryForObject("SELECT * FROM articles WHERE id=?",
                    BeanPropertyRowMapper.newInstance(Article.class), id);
            return article;
        } catch (IncorrectResultSizeDataAccessException e) {
            return null;
        }
    }
    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update("DELETE FROM articles WHERE id=?", id);
    }
    @Override
    public List<Article> findAll() {
        return jdbcTemplate.query("SELECT * from articles",BeanPropertyRowMapper.newInstance(Article.class));
    }
    @Override
    public List<Article> findByPublished(boolean published) {
        return jdbcTemplate.query("SELECT * from articles WHERE published=?",
                BeanPropertyRowMapper.newInstance(Article.class), published);
    }
    @Override
    public List<Article> findByTitleContaining(String title) {
        String q = "SELECT * from articles where title LIKE '%"+title+"%'";
        return jdbcTemplate.query(q, BeanPropertyRowMapper.newInstance(Article.class));
    }
    @Override
    public int deleteAll() {
        return jdbcTemplate.update("DELETE from articles");
    }
}
