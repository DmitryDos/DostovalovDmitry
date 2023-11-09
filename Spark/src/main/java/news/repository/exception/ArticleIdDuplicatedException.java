package news.repository.exception;

public class ArticleIdDuplicatedException extends Throwable {
  public ArticleIdDuplicatedException(String errorMessage) {
    super(errorMessage);
  }
}
