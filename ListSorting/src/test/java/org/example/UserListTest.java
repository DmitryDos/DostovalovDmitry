package org.example;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import static org.junit.jupiter.api.Assertions.*;

class UserListTest {

    UserList userList = new UserList(Arrays.asList(7, 3, 6, 1, 23, 8, 2, 6, -5, 23, 1));

    @Test
    public void getUserList() {
        int item2 = userList.getItem(2);
        assertEquals(6, item2);

        userList.setItem(5, 0);
        assertEquals(Arrays.asList(7, 3, 6, 1, 23, 0, 2, 6, -5, 23, 1), userList.list);
    }
}