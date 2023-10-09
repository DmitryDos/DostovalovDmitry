package org.example;

public class QuickSort implements InterfaceSort{
    private final UserList userList;

    public QuickSort(UserList userList) {
        this.userList = userList;
    }

    @Override
    public UserList sort() {
        includedSort(userList, 0, userList.length - 1);

        return userList;
    }

    private void includedSort(UserList userList, int begin, int end){
        if (begin < end) {
            int partitionIndex = partition(userList, begin, end);

            includedSort(userList, begin, partitionIndex - 1);
            includedSort(userList, partitionIndex + 1, end);
        }
    }

    private int partition(UserList userList, int begin, int end) {
        int pivot = userList.getItem(end);
        int i = (begin - 1);

        for (int j = begin; j < end; j++) {
            if (userList.getItem(j) <= pivot) {
                i++;

                int swapTemp = userList.getItem(i);
                userList.setItem(i, userList.getItem(j));
                userList.setItem(j, swapTemp);
            }
        }

        int swapTemp = userList.getItem(i + 1);
        userList.setItem(i + 1, userList.getItem(end));
        userList.setItem(end, swapTemp);

        return i + 1;
    }
}

