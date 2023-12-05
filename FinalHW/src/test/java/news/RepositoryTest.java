package news;

import news.entity.Article;
import news.entity.ArticleWithoutId;
import news.entity.id.ArticleId;
import news.repository.SQLArticleRepository;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Testcontainers
class MySqlArticleRepositoryTest {
  @Container
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:11");
  private static Jdbi jdbi;
  @BeforeAll
  static void beforeAll() {
    Flyway flyway =
        Flyway.configure()
            .outOfOrder(true)
            .locations("classpath:db/migrations")
            .dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(),
                POSTGRES.getPassword())
            .load();
    flyway.migrate();

    jdbi = Jdbi.create(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
  }

  @BeforeEach
  void beforeEach() {
    jdbi.useTransaction(handle -> handle.createUpdate("TRUNCATE TABLE article CASCADE").execute());
    jdbi.useTransaction(handle -> handle.createUpdate("TRUNCATE TABLE comment CASCADE").execute());
  }

  @Test
  void findZeroArticles() {
    SQLArticleRepository articleRepository = new SQLArticleRepository(jdbi);
    assertEquals(0, articleRepository.findAll().size());
  }

  @Test
  void findOneArticle() {
    SQLArticleRepository articleRepository = new SQLArticleRepository(jdbi);
    String[] emptyList = {};

    articleRepository.create(new ArticleWithoutId("Test", emptyList));

    assertEquals(1, articleRepository.findAll().size());
  }

  @Test
  void getOneArticle() {
    SQLArticleRepository articleRepository = new SQLArticleRepository(jdbi);
    String[] emptyList = {};

    ArticleId articleId = articleRepository.create(new ArticleWithoutId("Test", emptyList));
    Article article = articleRepository.findById(articleId);

    assertEquals("Test", article.getArticleName());
  }

  @Test
  void getZeroArticle() {
    SQLArticleRepository articleRepository = new SQLArticleRepository(jdbi);

    assertThrows(IllegalStateException.class, () -> articleRepository.findById(new ArticleId(0L)));
  }

  @Test
  void updateArticle() {
    SQLArticleRepository articleRepository = new SQLArticleRepository(jdbi);
    String[] emptyList = {};

    ArticleId articleId = articleRepository.create(new ArticleWithoutId("Test", emptyList));
    String[] listOne = {"tag1"};
    articleRepository.update(new Article(articleId, "Test1", listOne));

    Article article = articleRepository.findById(articleId);

    assertEquals("Test1", article.getArticleName());
  }

  @Test
  void deleteArticle() {
    SQLArticleRepository articleRepository = new SQLArticleRepository(jdbi);
    String[] emptyList = {};

    ArticleId articleId = articleRepository.create(new ArticleWithoutId("Test", emptyList));

    articleRepository.delete(articleId);

    assertThrows(IllegalStateException.class, () -> articleRepository.findById(articleId));
  }
}