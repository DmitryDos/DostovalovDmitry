package org.example;

public class BubbleSort implements InterfaceSort{
    private final UserList userList;

    public BubbleSort(UserList userList) {
        this.userList = userList;
    }

    @Override
    public UserList sort(){
        return includedSort(userList.length);
    }

    private UserList includedSort(int length){
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
