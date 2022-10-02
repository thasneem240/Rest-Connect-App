package com.assignment.part_A;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class PostRecyclerViewActivity extends AppCompatActivity
{
    private static final String SELECTED_USER ="Selected User" ;
    private User selectedUser = null;
    private ArrayList<Post> userPostList = null;
    private RecyclerView postRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_recycler_view);

        Intent intent = getIntent();
        selectedUser = (User)intent.getSerializableExtra(SELECTED_USER);

        TextView textTitle = findViewById(R.id.textTitle);
        postRecyclerView = findViewById(R.id.postRecyclerView);


        textTitle.setText("Posts Made by the User: " + selectedUser.getName());


        PostList postList = PostList.getInstance();

        userPostList = postList.getSpecificUserPosts(selectedUser.getUser_Id());

        setPostRecyclerView();
    }


    public static Intent getIntent(Context context, User user)
    {
        Intent intent = new Intent(context, PostRecyclerViewActivity.class);
        intent.putExtra(SELECTED_USER,user);

        return intent;
    }

    private void setPostRecyclerView()
    {
        if(!userPostList.isEmpty())
        {

            postRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            //Create Adapter for the recyclerview
            PostRecyclerViewActivity.MyAdapter adapter = new PostRecyclerViewActivity.MyAdapter();

            // Hook it up
            postRecyclerView.setAdapter(adapter);


        }
    }


    /* Private inner Class for View holder */

    private class MyDataVHolder extends RecyclerView.ViewHolder
    {
        TextView postTitle;
        TextView postBody;

        public MyDataVHolder(@NonNull View itemView)
        {
            super(itemView);

            postTitle = itemView.findViewById(R.id.postTitle);
            postBody = itemView.findViewById(R.id.postBody);
        }
    }


    /* Private inner Class for Adapter */

    private class MyAdapter extends RecyclerView.Adapter<MyDataVHolder>
    {

        @NonNull
        @Override
        public MyDataVHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
        {
            LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
            View view = layoutInflater.inflate(R.layout.view_holder_post,parent,false);

            PostRecyclerViewActivity.MyDataVHolder myDataVHolder = new PostRecyclerViewActivity.MyDataVHolder(view);

            return  myDataVHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyDataVHolder holder, int position)
        {
            TextView postTitle = holder.postTitle;
            TextView postBody = holder.postBody;

            // Single Data
            Post singlePost = userPostList.get(position);

            postTitle.setText(singlePost.getTitle());
            postBody.setText(singlePost.getBody());

        }

        @Override
        public int getItemCount()
        {
            int count = userPostList.size();
            return count;
        }
    }



}