package com.example.jdbcmssql.jdbcmssqlartifact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:8080")
@RestController
@RequestMapping("/api")
public class ArticleController {
    @Autowired
    ArticleRepo articleRepo;
    @GetMapping("/articles")
    public ResponseEntity<List<Article>> getAllArticles(@RequestParam(required = false) String title) {
        try {
            List<Article> articles = new ArrayList<Article>();
            if (title == null)
                articleRepo.findAll().forEach(articles::add);
            else {
                articleRepo.findByTitleContaining(title).forEach(articles::add);
            }
            if (articles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(articles, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/articles/{id}")
    public ResponseEntity<Article> getArticleById(@PathVariable("id") long id) {
        Article article = articleRepo.findById(id);
        if (article != null) {
            return new ResponseEntity<>(article, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @PostMapping("/articles")
    public ResponseEntity<String> createArticle(@RequestBody Article article) {
        try {
            articleRepo.save(new Article(article.getTitle(), article.getDescription(), false));
            return new ResponseEntity<>("Article was created successfully.", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PutMapping("/articles/{id}")
    public ResponseEntity<String> updateArticle(@PathVariable("id") long id, @RequestBody Article article) {
        Article _article = articleRepo.findById(id);
        if (_article != null) {
            _article.setId(id);
            _article.setTitle(article.getTitle());
            _article.setDescription(article.getDescription());
            _article.setPublished(article.isPublished());
            articleRepo.update(_article);
            return new ResponseEntity<>("Article was updated successfully.", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Cannot find Article with id="+id, HttpStatus.NOT_FOUND);
        }
    }
    @DeleteMapping("/articles/{id}")
    public ResponseEntity<String> deleteArticle(@PathVariable("id") long id) {
        try {
            int result = articleRepo.deleteById(id);
            if (result == 0) {
                return new ResponseEntity<>("Cannot find Article with id="+id, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Article was deleted successfully", HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete article.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @DeleteMapping("/articles")
    public ResponseEntity<String> deleteAllArticles() {
        try {
            int numRows = articleRepo.deleteAll();
            return new ResponseEntity<>("Deleted "+numRows+" Article(s) successfully.", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Cannot delete articles.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/articles/published")
    public ResponseEntity<List<Article>> findByPublished() {
        try {
            List<Article> articles = articleRepo.findByPublished(true);
            if (articles.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(articles, HttpStatus.OK);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
