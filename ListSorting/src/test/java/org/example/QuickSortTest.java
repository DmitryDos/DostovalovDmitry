package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class QuickSortTest {
    UserList userList = new UserList(Arrays.asList(7, 3, 6, 1, 23, 0, 2, 6, -5, 23, 1));

    @Test
    public void quickSortList() {
        try{
            UserList sortedList = new QuickSort().sort(userList);
            assertEquals(Arrays.asList(-5, 0, 1, 1, 2, 3, 6, 6, 7, 23, 23), sortedList.list);
        }catch (Exception e){
            System.out.println("Type undefined");
        }
    }
}
