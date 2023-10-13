package org.example;

public class BubbleSortL extends BubbleSort{
  @Override
  public UserList sort(UserList userList) throws Exception {
    if(userList.length > 1e9){
      throw new Exception("Invalid parameters");
    }
    return includedSort(userList.length, userList);
  }
}
