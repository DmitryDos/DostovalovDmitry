package news.repository;

import news.entity.Article;
import news.entity.id.ArticleId;
import news.repository.exception.ArticleIdDuplicatedException;
import news.repository.exception.ArticleNotFoundException;

import java.util.*;

public interface ArticleRepository {
  ArticleId generateId();

  List<Article> findAll();

  Article findById(ArticleId articleId) throws ArticleNotFoundException;

  void create(Article article) throws ArticleIdDuplicatedException;

  void update(Article article) throws ArticleIdDuplicatedException, ArticleNotFoundException;

  void delete(ArticleId articleId) throws ArticleNotFoundException;

}