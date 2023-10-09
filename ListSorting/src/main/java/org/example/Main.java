package org.example;
import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        Processing process = new Processing();

        UserList userList1 = new UserList(Arrays.asList(7, 3, 6, 1, 23, 8, 2, 6, -5, 23, 1));
        UserList userList2 = new UserList(Arrays.asList(-75, 23, 71, 1, 45, 3, -124, 12, 6, 333, 12));

        UserList sortedList1 = process.processSort(userList1, 0);
        sortedList1.print();

        UserList sortedList2 = process.processSort(userList2, 1);
        sortedList2.print();
    }
}

