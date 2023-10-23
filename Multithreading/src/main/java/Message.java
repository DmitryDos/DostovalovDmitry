import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class Message {
  private ConcurrentHashMap <String, String> content;
  private EnrichmentType enrichmentType;

  public Message(ConcurrentHashMap <String, String> content, EnrichmentType enrichmentType){
    this.content = content;
    this.enrichmentType = enrichmentType;
  }
  public EnrichmentType getEnrichmentType(){
    return enrichmentType;
  }

  public ConcurrentHashMap<String, String> getContent(){
    return content;
  }

  public enum EnrichmentType {
    MSISDN;
  }
}
