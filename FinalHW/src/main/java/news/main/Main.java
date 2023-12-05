package news.main;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import news.controllers.ArticleController;
import news.controllers.ArticleFreemarkerController;
import news.controllers.Controller;
import news.repository.SQLArticleRepository;
import news.repository.SQLCommentRepository;
import news.service.ArtAndComService;
import org.jdbi.v3.core.Jdbi;
import org.flywaydb.core.Flyway;
import spark.Service;
import spark.template.freemarker.FreeMarkerEngine;

import java.util.ArrayList;

public class Main {
  public static void main(String[] args) {
    Config config = ConfigFactory.load();

    Flyway flyway =
        Flyway.configure()
            .outOfOrder(true)
            .locations("classpath:db/migrations")
            .dataSource(
                config.getString("app.database.url"),
                config.getString("app.database.user"),
                config.getString("app.database.password"))
            .load();
    flyway.migrate();

    Jdbi jdbi = Jdbi.create(
        config.getString("app.database.url"),
        config.getString("app.database.user"),
        config.getString("app.database.password"));

    SQLArticleRepository articleRepository = new SQLArticleRepository(jdbi);
    SQLCommentRepository commentsRepository = new SQLCommentRepository(jdbi);
    ArtAndComService articleService = new ArtAndComService(articleRepository, commentsRepository);

    var freeMakerConfig = new Configuration(Configuration.VERSION_2_3_0);
    freeMakerConfig.setTemplateLoader(new ClassTemplateLoader(Main.class, "/"));
    var freeMakerEngine = new FreeMarkerEngine(freeMakerConfig);

    var objMapper = new ObjectMapper();
    var sparkService = Service.ignite();
    var controllers = new ArrayList<Controller>();

    controllers.add(new ArticleController(sparkService, articleService, objMapper));
    controllers.add(new ArticleFreemarkerController(sparkService, articleService, freeMakerEngine));

    var app = new Application(controllers);
    app.start();
  }
}

