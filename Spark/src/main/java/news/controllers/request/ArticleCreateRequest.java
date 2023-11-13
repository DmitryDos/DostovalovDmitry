package news.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import news.entity.Comment;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ArticleCreateRequest {
  private final String name;
  private final List<Comment> comments;
  private final Set<String> tags;

  @JsonCreator
  public ArticleCreateRequest(
      @JsonProperty("name") String name,
      @JsonProperty("comments") List<Comment> comments,
      @JsonProperty("tags") Set<String> tags
  ) {
    this.name = name;
    this.comments = comments;
    this.tags = tags;
  }

  public String getName() {
    return name;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public Set<String> getTags() {
    return tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleCreateRequest that = (ArticleCreateRequest) o;
    return Objects.equals(name, that.name) && Objects.equals(comments, that.comments) && Objects.equals(tags, that.tags);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, comments, tags);
  }
}
