package news.repository;

import news.entity.Article;
import news.entity.ArticleWithoutId;
import news.entity.id.ArticleId;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.Jdbi;

import java.util.*;

public class SQLArticleRepository implements ArticleRepository {
  Random random = new Random();
  private final Jdbi jdbi;
  public SQLArticleRepository(Jdbi jdbi) {
    this.jdbi = jdbi;
  }

  @Override
  public ArticleId generateId() {
    return new ArticleId(random.nextLong());
  }

  @Override
  public List<Article> findAll() {
    return jdbi.inTransaction((Handle handle) -> {
      var articles =
          handle.createQuery(
                  "SELECT * FROM article FOR UPDATE"
              )
              .mapToMap();
      ArrayList<Article> lstArticles = new ArrayList<>();
      for (Map<String, Object> artMap : articles) {
        lstArticles.add(parsMapToArticle(artMap));
      }
      return lstArticles;
    });
  }

  @Override
  public Article findById(ArticleId articleId) {
    return jdbi.inTransaction((Handle handle) -> {
      Long article_id = articleId.getId();
      Map<String, Object> artMap =
          handle.createQuery(
                  "SELECT id, name, tags, trending FROM article WHERE id = :id FOR UPDATE"
              )
              .bind("id", article_id)
              .mapToMap()
              .first();
      return parsMapToArticle(artMap);
    });
  }

  @Override
  public ArticleId create(ArticleWithoutId article) {
    return jdbi.inTransaction((Handle handle) -> {
      boolean flag = article.getTags().length >= 3;

      Map<String, Object> mapRes = handle.createUpdate(
              "INSERT INTO article (name, tags, trending) VALUES (:name, :tags, :trending)")
          .bind("name", article.getArticleName())
          .bind("tags", article.getTags())
          .bind("trending", flag)
          .executeAndReturnGeneratedKeys("id")
          .mapToMap()
          .first();

      return new ArticleId((Long) mapRes.get("id"));
    });
  }

  @Override
  public List<ArticleId> multiCreate(List<ArticleWithoutId> articles) {
    return jdbi.inTransaction((Handle handle) -> {
      List<ArticleId> articlesId = new ArrayList<>();
      for (ArticleWithoutId article : articles) {
        boolean flag = article.getTags().length >= 3;
        Map<String, Object> mapResult = handle.createUpdate(
                "INSERT INTO article (name, tags, trending) VALUES (:name, :tags, :trending) FOR UPDATE")
            .bind("name", article.getArticleName())
            .bind("tags", article.getTags())
            .bind("trending", flag)
            .executeAndReturnGeneratedKeys("id")
            .mapToMap()
            .first();
        articlesId.add(new ArticleId((Long) mapResult.get("id")));
      }
      return articlesId;
    });
  }

  @Override
  public void update(Article article) {
    ArticleId articleId = article.getId();
    jdbi.inTransaction((Handle handle) -> handle.createUpdate(
            "UPDATE article SET name = :name, tags = :tags WHERE id = :id")
        .bind("name", article.getArticleName())
        .bind("tags", article.getTags())
        .bind("id", articleId.getId())
        .execute());
  }

  @Override
  public void delete(ArticleId articleId) {
    jdbi.inTransaction((Handle handle) -> handle.createUpdate(
            "DELETE FROM article WHERE id = :id")
        .bind("id", articleId.getId())
        .execute());
  }

  public Article parsMapToArticle(Map<String, Object> artMap) {
    var tags = artMap.get("tags").toString().substring(1, artMap.get("tags").toString().length() - 1).split(",");
    return new Article(new ArticleId((Long) artMap.get("id")), artMap.get("name").toString(), tags);
  }
}
