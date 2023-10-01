package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class BubbleSortTest {
    UserList userList = new UserList(Arrays.asList(7, 3, 6, 1, 23, 0, 2, 6, -5, 23, 1));

    @Test
    public void bubbleSortList() {
        UserList sortedList = new BubbleSort(userList).sort();
        assertEquals(Arrays.asList(-5, 0, 1, 1, 2, 3, 6, 6, 7, 23, 23), sortedList.list);
    }
}