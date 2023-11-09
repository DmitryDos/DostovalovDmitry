package news.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import news.entity.Comment;
import news.entity.id.ArticleId;

import java.util.List;
import java.util.Objects;
import java.util.Set;

public class ArticleUpdateRequest {
  private final ArticleId articleId;
  private final String name;
  private final List<Comment> comments;
  private final Set<String> tags;

  @JsonCreator
  public ArticleUpdateRequest(
      @JsonProperty("id") ArticleId articleId,
      @JsonProperty("name") String name,
      @JsonProperty("comments") List<Comment> comments,
      @JsonProperty("tags") Set<String> tags
  ) {
    this.articleId = articleId;
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

  public ArticleId getArticleId() {
    return articleId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    ArticleUpdateRequest that = (ArticleUpdateRequest) o;
    return Objects.equals(articleId, that.getArticleId()) && Objects.equals(name, that.getName()) && Objects.equals(comments, that.getComments()) && Objects.equals(tags, that.getTags());
  }

  @Override
  public int hashCode() {
    return Objects.hash(articleId, name, comments, tags);
  }
}
