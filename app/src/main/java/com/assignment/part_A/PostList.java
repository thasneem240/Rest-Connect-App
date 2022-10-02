package com.assignment.part_A;

import java.util.ArrayList;

public class PostList
{
    private static PostList postList = null;
    private ArrayList<Post> listOfPost = null;

    private PostList()
    {
        listOfPost = new ArrayList<>();
    }

    /* Singleton */
    public static PostList getInstance()
    {
        if(postList == null)
        {
            postList = new PostList();
        }

        return postList;
    }

    public void addPost(Post post)
    {
        listOfPost.add(post);
    }

    public boolean isEmpty()
    {
        return  listOfPost.isEmpty();
    }

    public int size()
    {
        return  listOfPost.size();
    }


    public ArrayList<Post> getSpecificUserPosts(int userId)
    {
        ArrayList<Post> listOfUserPost = new ArrayList<>();

        for (Post post: listOfPost)
        {
            if(post.getUserId() == userId)
            {
                listOfUserPost.add(post);
            }
        }

        return  listOfUserPost;
    }

}
