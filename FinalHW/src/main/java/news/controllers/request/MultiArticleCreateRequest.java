package news.controllers.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MultiArticleCreateRequest {
  private final Map<String, String[]> data;

  @JsonCreator
  public MultiArticleCreateRequest(
      @JsonProperty("data") Map<String, String[]> data
  ) {
    this.data = data;
  }

  public Map<String, String[]> getData() {
    return data;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    MultiArticleCreateRequest that = (MultiArticleCreateRequest) o;
    return Objects.equals(data, that.data);
  }

  @Override
  public int hashCode() {
    return Objects.hash(data);
  }
}
