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
  private final List<Comment> comments;
  private final Set<String> tags;
  private final AtomicLong nextCommentId = new AtomicLong(0);

  @JsonCreator
  public Article(@JsonProperty("id") ArticleId id, @JsonProperty("name") String articleName, @JsonProperty("comments") List<Comment> comments, @JsonProperty("tags") Set<String> tags) {
    this.id = id;
    this.articleName = articleName;
    this.comments = comments;
    this.tags = tags;
  }

  public ArticleId getId() {
    return id;
  }

  public String getArticleName() {
    return articleName;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public Set<String> getTags() {
    return tags;
  }

  public synchronized Article addComment(Comment comment) {
    List<Comment> newComments = new ArrayList<>(List.copyOf(comments));
    newComments.add(comment);

    return new Article(getId(), getArticleName(), newComments, getTags());
  }

  public synchronized Comment createComment(CommentId commentId, String mainBody) {
    return new Comment(commentId, mainBody, id);
  }

  public Long commentsNumber () {
    return (long) comments.size();
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
