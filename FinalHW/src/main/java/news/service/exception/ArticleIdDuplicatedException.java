package news.service.exception;

public class ArticleIdDuplicatedException extends Throwable {
  public ArticleIdDuplicatedException(String errorMessage) {
    super(errorMessage);
  }
}
