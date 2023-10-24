import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

  @Test
  void shouldReturnEnrichedMessage() throws InterruptedException, UserNotFoundException {
    ConcurrentHashMap<String, String> correctPD = new ConcurrentHashMap<>() {{
      put("firstname", "Ivan");
      put("lastname", "Petrov");
      put("dateOfBirth", "03.11.1997");
      put("msisdn", "89227777777");
    }};
    User userCorrect = new User(correctPD);
    UserRepository.createUser(userCorrect);

    ConcurrentHashMap<String, String> incorrectPD = new ConcurrentHashMap<>() {{
      put("firstname", "Mark");
      put("lastname", "Volkov");
    }};
    User userIncorrect = new User(incorrectPD);
    UserRepository.createUser(userIncorrect);

    ConcurrentHashMap<String, String> simpleMessage = new ConcurrentHashMap<>() {{
      put("msisdn", "89227777777");
      put("favouriteGame", "DiabloIV");
    }};

    Message messageExtra = UserRepository.findByMsisdn("89227777777").getMessage(Message.EnrichmentType.MSISDN);
    Message messageIncorrect = new Message(incorrectPD, Message.EnrichmentType.MSISDN);
    Message messageCorrect = new Message(correctPD, Message.EnrichmentType.MSISDN);
    Message messageSimple = new Message(simpleMessage, Message.EnrichmentType.MSISDN);

    ConcurrentLinkedQueue<Message> messagesPool = new ConcurrentLinkedQueue<>(){{
      add(messageIncorrect);
      add(messageCorrect);
      add(messageExtra);
      add(messageSimple);
    }};

    EnrichmentService enrichmentService = new EnrichmentService();

    ExecutorService executorService = Executors.newCachedThreadPool();

    ConcurrentHashMap<Message, Message> enrichMessagesPool = new ConcurrentHashMap<>();
    HashMap<Message, Message> correctMessagesPool = new HashMap<>();

    AtomicInteger counter = new AtomicInteger(0);
    Message[] msg = new Message[4];
    msg[0] = messageIncorrect;
    msg[1] = messageCorrect;
    msg[2] = messageCorrect;
    msg[3] = new Message(new ConcurrentHashMap<>(){{
      put("firstname", "Ivan");
      put("lastname", "Petrov");
      put("favouriteGame", "DiabloIV");
      put("msisdn", "89227777777");
    }}, Message.EnrichmentType.MSISDN);

    messagesPool.forEach((message -> {
      correctMessagesPool.put(message, msg[counter.get()]);
      counter.getAndIncrement();

      executorService.submit(() -> {
        try {
          enrichMessagesPool.put(message, enrichmentService.enrich(message));
        } catch (EnrichmentTypeNotFoundException e) {
          throw new RuntimeException(e);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
      });
    }));
    executorService.shutdown();
    executorService.awaitTermination(3000, TimeUnit.SECONDS);

    for (int i = 0; i < counter.get(); i++) {
      Message msgKey = messagesPool.poll();
      Message msgGet = enrichMessagesPool.get(msgKey);
      Message msgTest = correctMessagesPool.get(msgKey);

      ConcurrentHashMap <String, String> contentGet = msgGet.getContent();
      ConcurrentHashMap <String, String> contentTest = msgTest.getContent();

      assertEquals(contentTest.size(), contentGet.size());

      contentTest.forEach((key, value) ->{
        assertTrue(contentGet.containsKey(key));
        assertEquals(value, contentGet.get(key));
      });
    }
  }
}