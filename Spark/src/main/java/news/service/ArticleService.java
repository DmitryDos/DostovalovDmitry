package news.service;

import java.util.*;

import news.entity.Article;
import news.entity.Comment;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;
import news.repository.InMemoryArticleRepository;
import news.repository.exception.ArticleIdDuplicatedException;
import news.repository.exception.ArticleNotFoundException;
import news.repository.exception.CommentNotFoundException;

public class ArticleService {
  private final InMemoryArticleRepository articleRepository;

  public ArticleService(InMemoryArticleRepository articleRepository) {
    this.articleRepository = articleRepository;
  }

  public List<Article> findAll() {
    return articleRepository.findAll();
  }

  public Article findById(ArticleId articleId) throws ArticleNotFoundException {
    return articleRepository.findById(articleId);
  }

  public ArticleId create(String name, List<Comment> comments, Set<String> tags) throws ArticleIdDuplicatedException {
    ArticleId articleId = articleRepository.generateId();
    Article article = new Article(articleId, name, comments, tags);

    articleRepository.create(article);
    return articleId;
  }

  public void updateWithName(ArticleId articleId, String name) throws ArticleNotFoundException {
    Article article = articleRepository.findById(articleId);
    articleRepository.update(
        new Article(articleId, name, article.getComments(), article.getTags())
    );
  }

  public void updateWithComments(ArticleId articleId, List<Comment> comments) throws ArticleNotFoundException {
    Article article = articleRepository.findById(articleId);
    articleRepository.update(
        new Article(articleId, article.getArticleName(), comments, article.getTags())
    );
  }

  public void updateWithTags(ArticleId articleId, Set<String> tags) throws ArticleNotFoundException {
    Article article = articleRepository.findById(articleId);
    articleRepository.update(
        new Article(articleId, article.getArticleName(), article.getComments(), tags)
    );
  }

  public void delete(ArticleId articleId) throws ArticleNotFoundException {
    articleRepository.delete(articleId);
  }

  public List<Comment> getComments(ArticleId articleId) throws ArticleNotFoundException {
    List<Comment> comments;
    comments = articleRepository.findById(articleId).getComments();
    return comments;
  }

  public synchronized Comment getComment(ArticleId articleId, CommentId commentId) throws ArticleNotFoundException, CommentNotFoundException {
    return articleRepository.getComment(articleId, commentId);
  }

  public synchronized Comment addComment(ArticleId articleId, Comment comment) throws ArticleNotFoundException {
    return articleRepository.addComment(articleId, comment);
  }

  public synchronized CommentId createComment(ArticleId articleId, String mainBody) throws ArticleNotFoundException {
    return articleRepository.createComment(articleId, mainBody);
  }

  public synchronized void deleteComment(ArticleId articleId, CommentId commentId) throws ArticleNotFoundException, CommentNotFoundException {
    articleRepository.deleteComment(articleId, commentId);
  }
}
