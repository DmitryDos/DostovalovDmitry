package org.example;

public class Processing {
    public UserList processSort(UserList userList, int operator){
        switch (operator){
            case 0:
                return bSort(userList);
            case 1:
                return qSort(userList);
            default:
                System.out.println("Неверный тип сортировки");
                return userList;
        }
    }

    private UserList bSort(UserList list){
        return new BubbleSort(list).sort();
    }

    private UserList qSort(UserList list){
        return new QuickSort(list).sort();
    }
}
