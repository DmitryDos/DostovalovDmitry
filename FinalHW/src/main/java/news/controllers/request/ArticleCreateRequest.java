package news.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import news.entity.Comment;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ArticleCreateRequest {
  private final String name;
  private final String[] tags;

  @JsonCreator
  public ArticleCreateRequest(
      @JsonProperty("name") String name,
      @JsonProperty("tags") String[] tags
  ) {
    this.name = name;
    this.tags = tags;
  }

  public String getName() {
    return name;
  }

  public String[] getTags() {
    return tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleCreateRequest that = (ArticleCreateRequest) o;
    return Objects.equals(name, that.name) && Objects.equals(tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, tags);
  }
}
