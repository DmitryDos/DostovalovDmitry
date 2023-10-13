package org.example;

public class BubbleSortS extends BubbleSort {
  @Override
  public UserList sort(UserList userList) throws Exception {
    if(userList.length > 5){
      throw new Exception("Invalid parameters");
    }
    return includedSort(userList.length, userList);
  }
}
