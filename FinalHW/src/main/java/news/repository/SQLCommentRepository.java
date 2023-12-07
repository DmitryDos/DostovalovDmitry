package news.repository;

import news.entity.Article;
import news.entity.Comment;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.*;

public class SQLCommentRepository implements CommentRepository {
  private final Jdbi jdbi;
  public SQLCommentRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }
  @Override
  public CommentId generateId(Article article) {
    return article.nextId();
  }

  @Override
  public List<Comment> findAll(ArticleId articleId) {
    return jdbi.inTransaction((Handle handle) -> {
      var comments =
          handle.createQuery(
                  "SELECT * FROM comment WHERE articleId = :articleId FOR UPDATE"
              )
              .bind("articleId", articleId.getId())
              .mapToMap();
      ArrayList<Comment> lstComments = new ArrayList<>();
      for (Map<String, Object> comMap : comments) {
        lstComments.add(parsMapToComment(comMap));
      }

      return lstComments;
    });
  }

  @Override
  public Comment findById(ArticleId articleId, CommentId commentId) {
    return jdbi.inTransaction((Handle handle) -> {
      Map<String, Object> comMap =
          handle.createQuery(
                  "SELECT * FROM comment WHERE articleId = :articleId AND id = :id FOR UPDATE"
              )
              .bind("articleId", articleId.getId())
              .bind("id", commentId.getId())
              .mapToMap()
              .first();

      return parsMapToComment(comMap);
    });
  }

  @Override
  public CommentId create(Comment comment) {
    jdbi.useTransaction((Handle handle) -> {
      ArticleId articleId = comment.getArticleId();

      handle.createUpdate(
          "INSERT INTO comment (id, name, articleid) VALUES (:id, :name, :articleId) FOR UPDATE")
          .bind("id", comment.getId())
          .bind("name", comment.getMainBody())
          .bind("articleId", articleId.getId());

      int numb =
          handle.createQuery(
                  "SELECT COUNT(id) FROM comment WHERE articleId = :articleId"
              )
              .bind("articleId", articleId.getId())
              .mapTo(Integer.class)
              .first();

      boolean flag = numb >= 3;

      handle.createUpdate(
              "UPDATE article SET trending = :trending WHERE id = :id")
          .bind("trending", flag)
          .bind("id", articleId.getId());
    });
    return comment.getId();
  }

  @Override
  public void update(Comment comment) {
    jdbi.useTransaction((Handle handle) -> {
      ArticleId articleId = comment.getArticleId();

      handle.createUpdate(
              "UPDATE comment SET name = :name WHERE articleid = :articleId AND id = :id")
          .bind("articleId", articleId.getId())
          .bind("name", comment.getMainBody())
          .bind("id", comment.getId());
      });
  }

  @Override
  public void delete(ArticleId articleId, CommentId commentId) {
    jdbi.useTransaction((Handle handle) -> {
      handle.createUpdate(
              "DELETE FROM comment WHERE articleid = :articleId AND id = :id")
          .bind("articleId", articleId.getId())
          .bind("id", commentId.getId());

      int numb =
          handle.createQuery(
                  "SELECT COUNT(id) FROM comment WHERE articleId = :articleId"
              )
              .bind("articleId", articleId.getId())
              .mapTo(Integer.class)
              .first();

      boolean flag = numb >= 3;

      handle.createUpdate(
              "UPDATE article SET trending = :trending WHERE id = :id")
          .bind("trending", flag)
          .bind("id", articleId.getId());
    });
  }

  public Comment parsMapToComment(Map<String, Object> comMap) {
    return new Comment(new CommentId((long) comMap.get("id")), comMap.get("name").toString(), new ArticleId((Long) comMap.get("articleId")));
  }
}
