package news.controllers;

import com.fasterxml.jackson.databind.*;
import news.controllers.request.ArticleCreateRequest;
import news.controllers.request.CommentCreateRequest;
import news.controllers.response.*;
import news.entity.Article;
import news.entity.Comment;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import news.repository.exception.ArticleIdDuplicatedException;
import news.repository.exception.ArticleNotFoundException;
import news.repository.exception.CommentNotFoundException;
import news.service.ArticleService;
import spark.*;

import java.util.List;

public class ArticleController implements Controller {
  private static final Logger LOG = LoggerFactory.getLogger(ArticleController.class);

  private final Service service;
  private final ArticleService articleService;
  private final ObjectMapper objectMapper;

  public ArticleController(Service service, ArticleService articleService, ObjectMapper objectMapper) {
    this.service = service;
    this.articleService = articleService;
    this.objectMapper = objectMapper;
  }

  @Override
  public void initializeEndpoints() {
    getArticle();
    getArticles();
    createArticle();
    updateArticle();
    deleteArticle();
    getComment();
    deleteComment();
    addComment();
  }

  private void createArticle() {
    service.post(
        "/api/articles",
        (Request request, Response response) -> {
          response.type("application/json");
          String body = request.body();
          ArticleCreateRequest articleCreateRequest = objectMapper.readValue(
              body,
              ArticleCreateRequest.class);
          try {
            ArticleId articleId = articleService.create(articleCreateRequest.getName(), articleCreateRequest.getComments(), articleCreateRequest.getTags());

            LOG.debug("Create article successfully. ArticleId:{}", articleId.getId());
            response.status(201);
            return objectMapper.writeValueAsString(new ArticleCreateResponse(articleId));
          } catch (ArticleIdDuplicatedException e) {

            LOG.warn("Cannot create article", e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }

  private void getArticle() {
    service.get(
        "/api/articles/:articleId",
        (Request request, Response response) -> {
          response.type("application/json");

          ArticleId articleId = new ArticleId(Long.parseLong(request.params("articleId")));

          try {
            Article article = articleService.findById(articleId);

            LOG.debug("Get article by id successfully");
            response.status(200);
            return objectMapper.writeValueAsString(new ArticleGetResponse(article));
          } catch (ArticleNotFoundException e) {

            LOG.warn("Cannot get article with articleId:{}", articleId.getId(), e);
            response.status(404);
            return objectMapper.writeValueAsString(new Error(e.getMessage()));
          }
        }
    );
  }

  private void getArticles() {
    service.get(
        "/api/articles/",
        (Request request, Response response) -> {
          response.type("application/java");

          List <Article> articles = articleService.findAll();

          LOG.debug("Get all articles successfully");
          response.status(200);
          return objectMapper.writeValueAsString(new ArticleGetAllResponse(articles));
        }
    );
  }

  private void updateArticle() {
    service.patch(
        "/api/articles/:articleId",
        (Request request, Response response) -> {
          response.type("application/json");

          ArticleId articleId = new ArticleId(Long.parseLong(request.params("articleId")));

          String body = request.body();
          ArticleCreateRequest articleCreateRequest = objectMapper.readValue(
              body,
              ArticleCreateRequest.class);
          try {
            if (articleCreateRequest.getName() != null) {
              articleService.updateWithName(articleId, articleCreateRequest.getName());
            }
            if (articleCreateRequest.getComments() != null) {
              articleService.updateWithComments(articleId, articleCreateRequest.getComments());
            }
            if (articleCreateRequest.getTags() != null) {
              articleService.updateWithTags(articleId, articleCreateRequest.getTags());
            }

            LOG.debug("Update article by id:{} successfully", articleId);
            response.status(204);
            return objectMapper.writeValueAsString(new ArticleUpdateResponse(articleId));
          } catch (ArticleNotFoundException e) {

            LOG.warn("Cannot update article by id:{} ", articleId, e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }

  private void deleteArticle() {
    service.delete(
        "/api/articles/:articleId",
        (Request request, Response response) -> {
          response.type("application/json");

          ArticleId articleId = new ArticleId(Long.parseLong(request.params("articleId")));

          try {
            articleService.delete(articleId);

            LOG.debug("Delete article by id:{} successfully", articleId);
            response.status(204);
            return 0;
          } catch (ArticleNotFoundException e) {

            LOG.warn("Cannot delete article by id:{}", articleId, e);
            response.status(404);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }

  private void getComment() {
    service.get(
        "/api/articles/:articleId/comments/:commentId",
        (Request request, Response response) -> {
          response.type("application/java");

          ArticleId articleId = new ArticleId(Long.parseLong(request.params("articleId")));

          CommentId commentId = new CommentId(Long.parseLong(request.params("commentId")));

          try {
            Comment comment = articleService.getComment(articleId, commentId);

            LOG.debug("Get comment by id:{} successfully", articleId);
            response.status(200);
            return objectMapper.writeValueAsString(new CommentGetResponse(comment));
          } catch (ArticleNotFoundException | CommentNotFoundException e) {

            LOG.debug("Cannot get comment with id:{} from article:{}", commentId, articleId, e);
            response.status(404);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }

  private void deleteComment() {
    service.delete(
        "/api/articles/:articleId/comments/:commentId",
        (Request request, Response response) -> {
          response.type("application/java");

          ArticleId articleId = new ArticleId(Long.parseLong(request.params("articleId")));

          CommentId commentId = new CommentId(Long.parseLong(request.params("commentId")));

          try {
            articleService.deleteComment(articleId, commentId);

            LOG.debug("Delete comment by id:{} from article:{} successfully", commentId, articleId);
            response.status(204);
            return 0;
          } catch (ArticleNotFoundException | CommentNotFoundException e) {

            LOG.warn("Cannot delete comment with id:{} from article:{}", commentId, articleId, e);
            response.status(404);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }

  private void addComment() {
    service.post(
        "/api/articles/:articleId/comments",
        (Request request, Response response) -> {
          response.type("application/json");

          ArticleId articleId = new ArticleId(Long.parseLong(request.params("articleId")));

          CommentCreateRequest commentCreateRequest = objectMapper.readValue(request.body(), CommentCreateRequest.class);
          try {
            CommentId commentId = articleService.createComment(articleId, commentCreateRequest.getMainBody());

            LOG.debug("Add comment with id:{} to article:{} successfully", commentId, articleId);
            response.status(201);
            return objectMapper.writeValueAsString(new CommentCreateResponse(commentId));
          } catch (ArticleNotFoundException e) {

            LOG.warn("Cannot add comment to article:{}", articleId, e);
            response.status(400);
            return objectMapper.writeValueAsString(new ErrorResponse(e.getMessage()));
          }
        }
    );
  }
}
