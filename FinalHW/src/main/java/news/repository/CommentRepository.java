package news.repository;

import news.entity.Article;
import news.entity.Comment;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;
import news.repository.exception.CommentNotFoundException;

import java.util.*;

public interface CommentRepository {

  CommentId generateId(Article article);

  List<Comment> findAll(ArticleId articleId);

  Comment findById(ArticleId articleId, CommentId commentId) throws CommentNotFoundException;

  CommentId create(Comment comment);

  void update(Comment comment) throws CommentNotFoundException;

  void delete(ArticleId articleId, CommentId commentId) throws CommentNotFoundException;

}