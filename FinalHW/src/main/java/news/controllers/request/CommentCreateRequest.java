package news.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

public class CommentCreateRequest {
  private final String mainBody;

  @JsonCreator
  public CommentCreateRequest(@JsonProperty("mainBody") String mainBody) {
    this.mainBody = mainBody;
  }

  public String getMainBody() {
    return mainBody;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CommentCreateRequest that = (CommentCreateRequest) o;
    return Objects.equals(mainBody, that.mainBody);
  }

  @Override
  public int hashCode() {
    return Objects.hash(mainBody);
  }
}
