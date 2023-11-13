package news.repository;

import news.entity.Article;
import news.entity.Comment;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;
import news.repository.exception.ArticleIdDuplicatedException;
import news.repository.exception.ArticleNotFoundException;
import news.repository.exception.CommentNotFoundException;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.*;

public class InMemoryArticleRepository implements ArticleRepository {

  private final AtomicLong nextId = new AtomicLong(0);
  private final Map<ArticleId, Article> articlesMap = new ConcurrentHashMap<>();

  private List<Comment> filterCommentByType(List<Comment> list, CommentId commentId) {
    List<Comment> r = new ArrayList<>();
    for (Comment comment : list) {
      if (comment.getId() == commentId) {
        r.add(comment);
      }
    }
    return r;
  }
  @Override
  public ArticleId generateId() {
    return new ArticleId(nextId.incrementAndGet());
  }

  @Override
  public List<Article> findAll() {
    return new ArrayList<>(articlesMap.values());
  }

  @Override
  public Article findById(ArticleId articleId) throws ArticleNotFoundException {
    Article article = articlesMap.get(articleId);
    if (article == null) {
      throw new ArticleNotFoundException("Cannot find article by id=" + articleId.getId());
    }
    return article;
  }

  @Override
  public synchronized void create(Article article) throws ArticleIdDuplicatedException {
    if (articlesMap.get(article.getId()) != null) {
      throw new ArticleIdDuplicatedException("news.entity.Article with the given id already exists: " + article.getId());
    }
    articlesMap.put(article.getId(), article);
  }

  @Override
  public synchronized void update(Article article) throws ArticleNotFoundException {
    if (articlesMap.get(article.getId()) == null) {
      throw new ArticleNotFoundException("news.entity.Article with the given id already exists: " + article.getId());
    }
    articlesMap.put(article.getId(), article);
  }

  @Override
  public void delete(ArticleId articleId) throws ArticleNotFoundException {
    if (articlesMap.remove(articleId) == null) {
      throw new ArticleNotFoundException("Cannot find article by id=" + articleId);
    }
    articlesMap.remove(articleId);
  }

  public synchronized Comment getComment(ArticleId articleId, CommentId commentId) throws ArticleNotFoundException, CommentNotFoundException {
    Article article = findById(articleId);
    List<Comment> comments = article.getComments();

    List<Comment> commentsReturned = comments.stream().filter(comment -> comment.getId().equals(commentId)).toList();

    if (commentsReturned.isEmpty()) {
      throw new CommentNotFoundException("Cannot find article by id=" + articleId);
    }
    return commentsReturned.get(0);
  }

  public synchronized Comment addComment(ArticleId articleId, Comment comment) throws ArticleNotFoundException {
    Article article = findById(articleId);

    Article updatedArticle = article.addComment(comment);

    update(updatedArticle);
    return comment;
  }

  public synchronized CommentId createComment(ArticleId articleId, String mainBody) throws ArticleNotFoundException {
    Article article = findById(articleId);
    CommentId commentId = article.nextId();
    update(article.addComment(article.createComment(commentId, mainBody)));

    return commentId;
  }

  public synchronized void deleteComment(ArticleId articleId, CommentId commentId) throws ArticleNotFoundException, CommentNotFoundException {
    Article article = findById(articleId);
    Comment comment = getComment(articleId, commentId);
    List<Comment> comments = article.getComments();
    if (!comments.contains(comment)) {
      throw new CommentNotFoundException("Cannot find comment by id=" + commentId);
    }
    comments.remove(comment);

    Article updatedArticle = new Article(articleId, article.getArticleName(), comments, article.getTags());
    update(updatedArticle);
  }
}