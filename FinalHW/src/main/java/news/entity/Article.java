package news.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import news.entity.id.ArticleId;
import news.entity.id.CommentId;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class Article {
  private final ArticleId id;
  private final String articleName;
  private final String[] tags;
  private final AtomicLong nextCommentId = new AtomicLong(0);

  @JsonCreator
  public Article(@JsonProperty("id") ArticleId id, @JsonProperty("name") String articleName, @JsonProperty("tags") String[] tags) {
    this.id = id;
    this.articleName = articleName;
    this.tags = tags;
  }
  public ArticleId getId() {
    return id;
  }

  public String getArticleName() {
    return articleName;
  }

  public String[] getTags() {
    return tags;
  }

  public synchronized CommentId nextId(){
    return new CommentId(nextCommentId.incrementAndGet());
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
    return id == article.id;
  }

  @Override
  public int hashCode() {
    return Objects.hash(id);
  }
}
