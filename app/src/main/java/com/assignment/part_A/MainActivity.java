package com.assignment.part_A;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity
{
    private UserList userList;
    private PostList postList;
    private RecyclerView usersRecyclerView;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        userList = UserList.getInstance();
        postList = PostList.getInstance();


        ProgressBar progressBar = findViewById(R.id.progressBar);
        Button buttonLoad = findViewById(R.id.buttonLoad);
        usersRecyclerView = (RecyclerView) findViewById(R.id.usersRecyclerView);
        Button enableRecyclerView = findViewById(R.id.buttonEnableRecyclerView);

        usersRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
        enableRecyclerView.setVisibility(View.INVISIBLE);


        buttonLoad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               // progressBar.setVisibility(View.VISIBLE);
                //setUserRecyclerView();

                BackGroundTaskHandler backGroundTaskHandler = new
                        BackGroundTaskHandler(MainActivity.this, progressBar,enableRecyclerView);

                executorService.execute(backGroundTaskHandler);
            }
        });


        enableRecyclerView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                enableRecyclerView.setVisibility(View.INVISIBLE);
                setUserRecyclerView();
            }
        });



    }


    private void setUserRecyclerView()
    {
        if(!userList.isEmpty())
        {
            usersRecyclerView.setVisibility(View.VISIBLE);

            usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));

            //Create Adapter for the recyclerview
            MyAdapter adapter = new MyAdapter();

            // Hook it up
            usersRecyclerView.setAdapter(adapter);
        }
    }



    /* Private inner Class for View holder */

    private class MyDataVHolder extends RecyclerView.ViewHolder
    {
        TextView userName;
        Button buttonGetDetails;
        Button buttonLoadPosts;

        public MyDataVHolder(@NonNull View itemView)
        {
            super(itemView);

            userName = itemView.findViewById(R.id.userName);
            buttonGetDetails = itemView.findViewById(R.id.buttonGetDetails);
            buttonLoadPosts = itemView.findViewById(R.id.buttonLoadPosts);
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
            View view = layoutInflater.inflate(R.layout.view_holder_user,parent,false);

            MyDataVHolder myDataVHolder = new MyDataVHolder(view);

            return  myDataVHolder;
        }

        @Override
        public void onBindViewHolder(@NonNull MyDataVHolder holder, int position)
        {
            TextView userName = holder.userName;
            Button buttonGetDetails = holder.buttonGetDetails;
            Button buttonLoadPosts = holder.buttonLoadPosts;

            // Single Data
            User singleUser = userList.get(position);

            // Set the TextView
            userName.setText(singleUser.getName());

            buttonGetDetails.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = GetDetailActivity.getIntent(MainActivity.this,singleUser);
                    startActivity(intent);
                }
            });


            buttonLoadPosts.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent intent = PostRecyclerViewActivity.getIntent(MainActivity.this,singleUser);
                    startActivity(intent);

                }
            });

        }

        @Override
        public int getItemCount()
        {
            int count = userList.size();
            return count;
        }
    }




}