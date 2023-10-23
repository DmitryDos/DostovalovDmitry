import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
public class User {
  private ConcurrentHashMap<String, String> personalData = new ConcurrentHashMap<>();

  public User (ConcurrentHashMap<String, String> data){
    personalData.putAll(data);
  }

  public void updateData(){

  }
  public Message sendMessage(Message.EnrichmentType enrichmentType){
    return new Message(personalData, enrichmentType);
  }
  public ConcurrentHashMap<String, String> getData(){
    return personalData;
  }
}
