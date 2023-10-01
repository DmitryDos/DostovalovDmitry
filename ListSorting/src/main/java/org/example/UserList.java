package org.example;
import java.util.List;

public class UserList{
    public final List <Integer> list;
    public int length;

    public UserList(List <Integer> list){
        this.list = list;
        this.length = list.size();
    }

    public int getItem(int index){
        return list.get(index);
    }

    public void setItem(int index, Integer value){
        list.set(index, value);
    }

    public void print(){
        for (int i = 0; i < length; i++) {
            System.out.print(list.get(i) + " ");
        }
    }
}
