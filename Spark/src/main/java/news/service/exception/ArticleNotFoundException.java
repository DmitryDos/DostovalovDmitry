package news.service.exception;

public class ArticleNotFoundException extends Throwable {
  public ArticleNotFoundException(String errorMessage) {
    super(errorMessage);
  }
}
