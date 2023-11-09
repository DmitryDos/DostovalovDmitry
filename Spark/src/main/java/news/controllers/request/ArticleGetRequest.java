package news.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import news.entity.id.ArticleId;

import java.util.Objects;

public class ArticleGetRequest {
  private final ArticleId articleId;
  @JsonCreator
  public ArticleGetRequest(
      @JsonProperty("id") ArticleId articleId
  ) {
    this.articleId = articleId;
  }

  public ArticleId getId() {
    return articleId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleGetRequest that = (ArticleGetRequest) o;
    return Objects.equals(articleId.getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return Objects.hash(articleId.getId());
  }

  @Override
  public String toString() {
    return "articleId=" + articleId;
  }
}
