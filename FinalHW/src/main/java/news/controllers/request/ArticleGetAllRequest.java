package news.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import news.entity.Article;

import java.util.List;
import java.util.Objects;

public class ArticleGetAllRequest {
  private final List<Article> articles;
  @JsonCreator
  public ArticleGetAllRequest(
      @JsonProperty("articles") List<Article> articles
  ) {
    this.articles = articles;
  }

  public List<Article> getArticles() {
    return articles;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleGetRequest that = (ArticleGetRequest) o;
    return Objects.equals(articles, that);
  }

  @Override
  public int hashCode() {
    return Objects.hash(articles);
  }
}
