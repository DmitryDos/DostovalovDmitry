package news;
import com.fasterxml.jackson.databind.ObjectMapper;
import news.controllers.ArticleController;
import news.controllers.response.ArticleGetResponse;
import news.repository.SQLArticleRepository;
import news.repository.SQLCommentRepository;
import news.main.Application;
import news.service.ArtAndComService;
import org.flywaydb.core.Flyway;
import org.jdbi.v3.core.Jdbi;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import spark.Service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

@Testcontainers
class E2ETest {
  @Container
  public static final PostgreSQLContainer<?> POSTGRES = new PostgreSQLContainer<>("postgres:11");
  private static Jdbi jdbi;

  @BeforeAll
  static void beforeAll() {
    Flyway flyway =
        Flyway.configure()
            .outOfOrder(true)
            .locations("classpath:db/migrations")
            .dataSource(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword())
            .load();
    flyway.migrate();

    jdbi = Jdbi.create(POSTGRES.getJdbcUrl(), POSTGRES.getUsername(), POSTGRES.getPassword());
  }

  @BeforeEach
  void beforeEach() {
    service = Service.ignite();
    jdbi.useTransaction(handle -> handle.createUpdate("TRUNCATE TABLE article CASCADE ").execute());
    jdbi.useTransaction(handle -> handle.createUpdate("TRUNCATE TABLE  comment CASCADE ").execute());
  }

  private Service service;
  @BeforeEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  public void test() throws IOException, InterruptedException {
    SQLArticleRepository articleRepository = new SQLArticleRepository(jdbi);
    SQLCommentRepository commentsRepository = new SQLCommentRepository(jdbi);
    ArtAndComService articleService = new ArtAndComService(articleRepository, commentsRepository);

    HttpClient http = HttpClient.newHttpClient();
    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
        List.of(new ArticleController(service, articleService, objectMapper)));

    application.start();
    service.awaitInitialization();

    HttpResponse<String> response = http.send(
        HttpRequest.newBuilder()
            .POST(
                HttpRequest.BodyPublishers.ofString(
                    """
                        { "name": "Test", "tags": ["Tag1", "Tag2"] }"""
                )
            )
            .uri(URI.create("http://localhost:%d/api/articles".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(200, response.statusCode());

    response = http.send(
        HttpRequest.newBuilder()
            .POST(
                HttpRequest.BodyPublishers.ofString(
                    """
                        { "mainBody": "Test"}"""
                )
            )
            .uri(URI.create("http://localhost:%d/api/articles/1/comments".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(200, response.statusCode());

    response = http.send(
        HttpRequest.newBuilder()
            .POST(
                HttpRequest.BodyPublishers.ofString(
                    """
                        { "name": "Test2", "tags": ["Tag3", "Tag4"] }"""
                )
            )
            .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(204, response.statusCode());

    response = http.send(
        HttpRequest.newBuilder()
            .DELETE()
            .uri(URI.create("http://localhost:%d/api/articles/1/comments/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(204, response.statusCode());

    response = http.send(
        HttpRequest.newBuilder()
            .GET()
            .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
            .build(),
        HttpResponse.BodyHandlers.ofString(UTF_8)
    );

    assertEquals(200, response.statusCode());

    var articleResponse = objectMapper.readValue(response.body(), ArticleGetResponse.class);

    assertEquals("Test2", articleResponse.article().getArticleName());
    assertEquals("Tag3", articleResponse.article().getTags()[0]);
    assertEquals("Tag4", articleResponse.article().getTags()[1]);
  }
}