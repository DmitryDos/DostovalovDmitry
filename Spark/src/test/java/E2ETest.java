import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpRequest.BodyPublishers;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Set;

import news.Application;
import news.controllers.ArticleController;
import news.controllers.response.ArticleCreateResponse;
import news.controllers.response.ArticleGetResponse;
import news.controllers.response.CommentCreateResponse;
import news.entity.Article;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import news.repository.InMemoryArticleRepository;
import news.service.ArticleService;
import spark.Service;

class E2ETest {

  private Service service;

  @BeforeEach
  void befofeEach() {
    service = Service.ignite();
  }

  @AfterEach
  void afterEach() {
    service.stop();
    service.awaitStop();
  }

  @Test
  void createEditDeleteArticle() throws Exception {
    InMemoryArticleRepository articleRepository = new InMemoryArticleRepository();
    ArticleService articleService = new ArticleService(articleRepository);
    ObjectMapper objectMapper = new ObjectMapper();
    Application application = new Application(
        List.of(
            new ArticleController(
                service,
                articleService,
                objectMapper
            )
        )
    );

    application.start();
    service.awaitInitialization();

    HttpRequest createRequest = HttpRequest.newBuilder()
        .POST(
            BodyPublishers.ofString(
                """
                    {"name":"article1","comments":[],"tags":["tagA","tabB"]}
                    """
            )
        )
        .uri(URI.create("http://localhost:%d/api/articles".formatted(service.port())))
        .build();

    HttpResponse<String> responseCreate = HttpClient.newHttpClient()
        .send(
            createRequest,
            BodyHandlers.ofString(UTF_8)
        );


    assertEquals(201, responseCreate.statusCode());

    ArticleCreateResponse articleCreateResponse = objectMapper.readValue(responseCreate.body(), ArticleCreateResponse.class);

    assertTrue(new ArticleId(1).equals(articleCreateResponse.id()));

    HttpRequest addComment = HttpRequest.newBuilder()
        .POST(
            HttpRequest.BodyPublishers.ofString(
                """
                        { "mainBody": "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce quis dignissim augue, non viverra turpis." }
                      """
            )
        )
        .uri(URI.create("http://localhost:%d/api/articles/1/comments".formatted(service.port())))
        .build();

    HttpResponse<String> responseAdd = HttpClient.newHttpClient()
        .send(
            addComment,
            BodyHandlers.ofString(UTF_8)
        );

    assertEquals(201, responseAdd.statusCode());
    CommentCreateResponse commentCreateResponse = objectMapper.readValue(responseAdd.body(), CommentCreateResponse.class);

    assertEquals(new CommentId(1), commentCreateResponse.commentId());

    HttpRequest updateArticle = HttpRequest.newBuilder()
        .method("PATCH",
            HttpRequest.BodyPublishers.ofString(
                """
                          { "name":  "newArticleName", "tags": ["tag1", "tag2", "tag3"] }
                        """))
        .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
        .build();

    HttpResponse<String> responseUpdate = HttpClient.newHttpClient()
        .send(
            updateArticle,
            BodyHandlers.ofString(UTF_8)
        );

    assertEquals(204, responseUpdate.statusCode());

    HttpRequest deleteComment = HttpRequest.newBuilder()
        .DELETE()
        .uri(URI.create("http://localhost:%d/api/articles/1/comments/1".formatted(service.port())))
        .build();

    HttpResponse<String> responseDelete = HttpClient.newHttpClient()
        .send(
            deleteComment,
            BodyHandlers.ofString(UTF_8)
        );
    assertEquals(204, responseDelete.statusCode());

    HttpRequest getArticle = HttpRequest.newBuilder()
        .GET()
        .uri(URI.create("http://localhost:%d/api/articles/1".formatted(service.port())))
        .build();

    HttpResponse<String> responseGet = HttpClient.newHttpClient()
        .send(
            getArticle,
            BodyHandlers.ofString(UTF_8)
        );

    assertEquals(200, responseGet.statusCode());

    ArticleGetResponse articleGetResponse = objectMapper.readValue(responseGet.body(), ArticleGetResponse.class);
    Article article = articleGetResponse.article();

    assertEquals("newArticleName", article.getArticleName());
    assertEquals(Set.of("tag1", "tag2", "tag3"), article.getTags());
    assertTrue(article.getComments().isEmpty());
  }
}