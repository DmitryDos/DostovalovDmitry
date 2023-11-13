package news.entity.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class ArticleId {
  private final long id;

  @JsonCreator
  public ArticleId(@JsonProperty("id") long id) {
    this.id = id;
  }

  public Long getId() {
    return id;
  }
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ArticleId articleId = (ArticleId) o;
    return id == articleId.id;
  }
  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
