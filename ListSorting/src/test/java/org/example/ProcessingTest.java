package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class ProcessingTest {
    Processing process = new Processing();
    UserList userList1 = new UserList(Arrays.asList(7, 3, 6, 1, 23, 0, 2, 6, -5, 23, 1));
    UserList userList2 = new UserList(Arrays.asList(-75, 23, 71, 1, 45, 3, -124, 12, 6, 333, 12));

    @Test
    public void processing() {
        UserList sortedList1 = process.processSort(userList1, 0);
        assertEquals(Arrays.asList(-5, 0, 1, 1, 2, 3, 6, 6, 7, 23, 23), sortedList1.list);

        UserList sortedList2 = process.processSort(userList2, 1);
        assertEquals(Arrays.asList(-124, -75, 1, 3, 6, 12, 12, 23, 45, 71, 333), sortedList2.list);
    }
}