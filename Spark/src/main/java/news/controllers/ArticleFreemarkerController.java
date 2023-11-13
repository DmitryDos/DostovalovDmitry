package news.controllers;

import java.util.*;
import java.util.List;
import java.util.Map;

import news.entity.Article;
import news.service.ArticleService;
import spark.*;
import spark.template.freemarker.FreeMarkerEngine;

public class ArticleFreemarkerController implements Controller {

  private final Service service;
  private final ArticleService articleService;
  private final FreeMarkerEngine freeMarkerEngine;

  public ArticleFreemarkerController(Service service, ArticleService articleService, FreeMarkerEngine freeMarkerEngine) {
    this.service = service;
    this.articleService = articleService;
    this.freeMarkerEngine = freeMarkerEngine;
  }

  @Override
  public void initializeEndpoints() {
    getAllBooks();
  }

  private void getAllBooks() {
    service.get(
        "/",
        (Request request, Response response) -> {
          response.type("text/html; charset=utf-8");
          List<Article> articles = articleService.findAll();
          List<Map<String, String>> articleMapList =
              articles.stream()
                  .map(article -> Map.of(
                      "id", article.getId().getId().toString(),
                      "name", article.getArticleName(),
                      "numberOfComments", article.commentsNumber().toString()))
                  .toList();

          Map<String, Object> model = new HashMap<>();
          model.put("articles", articleMapList);
          return freeMarkerEngine.render(new ModelAndView(model, "index.ftl"));
        }
    );
  }
}