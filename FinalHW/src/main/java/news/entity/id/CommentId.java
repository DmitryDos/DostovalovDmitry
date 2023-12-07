package news.entity.id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public final class CommentId {
  private final long id;

  @JsonCreator
  public CommentId(@JsonProperty("id") long id) {
    this.id = id;
  }

  public long getId() {
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
    CommentId commentId = (CommentId) o;
    return id == commentId.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
