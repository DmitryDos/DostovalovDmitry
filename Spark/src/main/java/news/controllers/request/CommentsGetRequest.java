package news.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import news.entity.Comment;

import java.util.List;
import java.util.Objects;

public class CommentsGetRequest {
  private final List <Comment> comments;
  @JsonCreator
  public CommentsGetRequest(
      @JsonProperty("comments") List <Comment> comments
  ) {
    this.comments = comments;
  }

  public List<Comment> getComments() {
    return comments;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    CommentsGetRequest that = (CommentsGetRequest) o;
    return Objects.equals(comments, that.comments);
  }

  @Override
  public int hashCode() {
    return Objects.hash(comments);
  }
}
