import java.util.concurrent.ConcurrentHashMap;

public class UserRepository {
  static ConcurrentHashMap<String, User> users = new ConcurrentHashMap<>();

  public UserRepository(){};
  public static User findByMsisdn(String msisdn) throws UserNotFoundException {
    if(!users.containsKey(msisdn)){
      throw new UserNotFoundException("User not found");
    }
    return users.get(msisdn);
  }

  public static void updateUserByMsisdn(String msisdn, User user){

  }

  public static void createUser(User user){
    if(user.getData().containsKey("msisdn")){
      users.put(user.getData().get("msisdn"), user);
    }
  }
}
