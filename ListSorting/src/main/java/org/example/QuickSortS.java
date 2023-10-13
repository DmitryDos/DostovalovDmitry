package org.example;

public class QuickSortS extends QuickSort{
  @Override
  public UserList sort(UserList userList) throws Exception{
    if(userList.length > 1e9){
      throw new Exception("Invalid parameters");
    }
    includedSort(userList, 0, userList.length - 1);
    return userList;
  }
}
