package news;

import com.fasterxml.jackson.databind.ObjectMapper;
import news.controllers.ArticleController;
import news.controllers.ArticleFreemarkerController;
import news.repository.InMemoryArticleRepository;
import news.service.ArticleService;
import spark.Service;
import news.template.TemplateFactory;

import java.util.List;

public class Main {
  //private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static void main(String[] args) {
    Service service = Service.ignite();
    ObjectMapper objectMapper = new ObjectMapper();
    final var articleService = new ArticleService(
        new InMemoryArticleRepository()
    );
    Application application = new Application(
        List.of(
            new ArticleController(
                service,
                articleService,
                objectMapper
            ),
            new ArticleFreemarkerController(
                service,
                articleService,
                TemplateFactory.freeMarkerEngine()
            )
        )
    );
    application.start();
  }
}

