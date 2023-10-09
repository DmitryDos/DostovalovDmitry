package org.example;
import java.util.List;

public class Processing {
    private List<InterfaceSort> actions;
    public UserList processSort(UserList userList, int operator){
        switch (operator) {
            case 0 -> actions = List.of(new BubbleSortS(), new BubbleSortL());
            case 1 -> actions = List.of(new QuickSortS());
            default -> System.out.println("Type didn't define");
        }
        return sort(userList);
    }

    private UserList sort(UserList list){
        for(InterfaceSort action : actions){
            try{
                action.sort(list);
                break;
            } catch (Exception e){
                System.out.println("Couldn't execute action " + action.getClass() + ". Trying next one.");
            }
        }
        return list;
    }
}

