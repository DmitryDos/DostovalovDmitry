package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class MainTest {

  @Test
  public void getUserList() {
    Processing process = new Processing();

    UserList userList1 = new UserList(Arrays.asList(7, 3, 6, 1, 23, 8, 2, 6, -5, 23, 1));

    UserList sortedList1 = process.processSort(userList1, 0);
    assertEquals(Arrays.asList(-5, 1, 1, 2, 3, 6, 6, 7, 8, 23, 23), sortedList1.list);
    assertEquals(Arrays.asList(7, 3, 6, 1, 23, 8, 2, 6, -5, 23, 1), userList1.list);
  }
}