package news.repository;

import news.entity.Article;
import news.entity.ArticleWithoutId;
import news.entity.id.ArticleId;
import news.repository.exception.ArticleIdDuplicatedException;
import news.repository.exception.ArticleNotFoundException;

import java.util.*;

public interface ArticleRepository {
  ArticleId generateId();

  List<Article> findAll();

  Article findById(ArticleId articleId) throws ArticleNotFoundException;

  ArticleId create(ArticleWithoutId article) throws ArticleIdDuplicatedException;
  List<ArticleId> multiCreate(List<ArticleWithoutId> lst) throws ArticleIdDuplicatedException;

  void update(Article article) throws ArticleIdDuplicatedException, ArticleNotFoundException;

  void delete(ArticleId articleId) throws ArticleNotFoundException;

}