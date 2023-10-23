import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

public class EnrichmentService {
  public Message enrich(Message message) throws EnrichmentTypeNotFoundException, InterruptedException {

    Message.EnrichmentType type = message.getEnrichmentType();
    switch (type){
      case MSISDN :
        try {
          return enrichMSISDN(message);
        } catch (Exception e) {
          System.out.println(e.getMessage());
          throw e;
        }
    }
    throw new EnrichmentTypeNotFoundException("Type not found");
  }
  public Message enrichMSISDN(Message message) throws InterruptedException {
    if(!message.getContent().containsKey("msisdn")){
      return message;
    }
    ConcurrentHashMap <String, String> content = message.getContent();
    String msisdn = content.get("msisdn");
    User user;
    try {
      user = UserRepository.findByMsisdn(msisdn);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    ConcurrentHashMap <String, String> enrichContent = new ConcurrentHashMap<>();

    CompletableFuture<Void> completableFuture;
    completableFuture = CompletableFuture.runAsync(() ->
        enrichContent.putAll(content));
        System.out.println(Thread.currentThread().getName());
    while(!completableFuture.isDone()){
      Thread.currentThread().sleep(300);
    }

    try{
      enrichContent.put("firstname", user.getData().get("firstname"));
      enrichContent.put("lastname", user.getData().get("lastname"));
    } catch (Exception e) {
      return message;
    }

    return new Message(enrichContent, Message.EnrichmentType.MSISDN);
  }
}
