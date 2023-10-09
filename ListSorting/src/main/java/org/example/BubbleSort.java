package org.example;

public class BubbleSort implements InterfaceSort{

    @Override
    public UserList sort(UserList userList) throws Exception {
        return includedSort(userList.length, userList);
    }

    private UserList includedSort(int length, UserList userList){
        for (int i = 0; i < length; i++) {
            for (int j = i + 1; j < length; j++) {
                if (userList.getItem(i) > userList.getItem(j)) {
                    int temp = userList.getItem(i);
                    userList.setItem(i, userList.getItem(j));
                    userList.setItem(j, temp);
                }
            }
        }
        return userList;
    }
}

