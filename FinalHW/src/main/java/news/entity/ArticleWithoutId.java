package news.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class ArticleWithoutId {
  private final String articleName;
  private final String[] tags;
  private final AtomicLong nextCommentId = new AtomicLong(0);

  @JsonCreator
  public ArticleWithoutId(@JsonProperty("name") String articleName, @JsonProperty("tags") String[] tags) {
    this.articleName = articleName;
    this.tags = tags;
  }

  public String getArticleName() {
    return articleName;
  }


  public String[] getTags() {
    return tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Article article = (Article) o;
    return articleName.equals(article.getArticleName());
  }

  @Override
  public int hashCode() {
    return Objects.hash(articleName);
  }
}
