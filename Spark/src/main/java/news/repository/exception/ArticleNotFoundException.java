package news.repository.exception;

public class ArticleNotFoundException extends Throwable {
  public ArticleNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
