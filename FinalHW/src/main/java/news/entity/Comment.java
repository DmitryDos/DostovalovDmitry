package news.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;

import java.util.Objects;

public class Comment {
  private final CommentId id;
  private final String mainBody;
  private final ArticleId articleId;

  @JsonCreator
  public Comment(@JsonProperty("id") CommentId id, @JsonProperty("mainBody") String mainBody, @JsonProperty("articleId") ArticleId articleId) {
    this.id = id;
    this.mainBody = mainBody;
    this.articleId = articleId;
  }

  public CommentId getId() {
    return id;
  }

  public String getMainBody() {
    return mainBody;
  }

  public ArticleId getArticleId() {
    return articleId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Comment comment = (Comment) o;
    return id == comment.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
