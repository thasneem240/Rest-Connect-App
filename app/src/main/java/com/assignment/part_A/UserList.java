package com.assignment.part_A;

import android.app.Activity;

import java.util.ArrayList;

public class UserList
{
    private static UserList userList = null;
    private ArrayList<User> listOfUser = null;

    private UserList()
    {
        listOfUser = new ArrayList<>();
    }

    /* Singleton */
    public static UserList getInstance()
    {
        if(userList == null)
        {
            userList = new UserList();
        }

        return userList;
    }

    public void addUser(User user)
    {
        listOfUser.add(user);
    }

    public boolean isEmpty()
    {
        return  listOfUser.isEmpty();
    }

    public int size()
    {
        return  listOfUser.size();
    }

    public User get(int position)
    {
        return listOfUser.get(position);
    }
}
