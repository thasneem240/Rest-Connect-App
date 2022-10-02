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

public class MainActivity extends AppCompatActivity
{
    private UserList userList;
    private PostList postList;
    private RecyclerView usersRecyclerView;


    public MainActivity()
    {
        userList = UserList.getInstance();

        User user1 = new User();

        user1.setUser_Id(1);
        user1.setName("Thasneem1");
        user1.setUserName("Th1");
        user1.setAddress("dsfgdg");
        user1.setCompanyDetails("Company dssdfgf");
        user1.setWebsite("Website dfghfthtfh");
        user1.setEmail("jdc@gmail.com");
        user1.setPhone("545645");


        User user2 = new User();

        user2.setUser_Id(2);
        user2.setName("Thasneem2");
        user2.setUserName("Th2");
        user2.setAddress("dsfgdg");
        user2.setCompanyDetails("Company dssdfgf");
        user2.setWebsite("Website dfghfthtfh");
        user2.setEmail("jdc@gmail.com");
        user2.setPhone("545645");

        User user3 = new User();

        user3.setUser_Id(3);
        user3.setName("Thasneem3");
        user3.setUserName("Th3");
        user3.setAddress("dsfgdg");
        user3.setCompanyDetails("Company dssdfgf");
        user3.setWebsite("Website dfghfthtfh");
        user3.setEmail("jdc@gmail.com");
        user3.setPhone("545645");


        User user4 = new User();

        user4.setUser_Id(4);
        user4.setName("Thasneem4");
        user4.setUserName("Th4");
        user4.setAddress("dsfgdg");
        user4.setCompanyDetails("Company dssdfgf");
        user4.setWebsite("Website dfghfthtfh");
        user4.setEmail("jdc@gmail.com");
        user4.setPhone("545645");


        User user5 = new User();

        user5.setUser_Id(5);
        user5.setName("Thasneem5");
        user5.setUserName("Th5");
        user5.setAddress("dsfgdg");
        user5.setCompanyDetails("Company dssdfgf");
        user5.setWebsite("Website dfghfthtfh");
        user5.setEmail("jdc@gmail.com");
        user5.setPhone("545645");


        userList.addUser(user1);
        userList.addUser(user2);
        userList.addUser(user3);
        userList.addUser(user4);
        userList.addUser(user5);

        postList = PostList.getInstance();

        Post post1 = new Post(1,"dfgdfgdf","gdfgdfgdfg");
        Post post2 = new Post(1,"dfgdfgdf","gdfgdfgdfg");
        Post post3 = new Post(1,"dfgdfgdf","gdfgdfgdfg");
        Post post4 = new Post(1,"dfgdfgdf","gdfgdfgdfg");
        Post post5 = new Post(1,"dfgdfgdf","gdfgdfgdfg");
        Post post6 = new Post(2,"dfgdfgdf","gdfgdfgdfg");
        Post post7 = new Post(2,"dfgdfgdf","gdfgdfgdfg");
        Post post8 = new Post(3,"dfgdfgdf","gdfgdfgdfg");
        Post post9 = new Post(3,"dfgdfgdf","gdfgdfgdfg");
        Post post10 = new Post(4,"dfgdfgdf","gdfgdfgdfg");

        postList.addPost(post1);
        postList.addPost(post2);
        postList.addPost(post3);
        postList.addPost(post4);
        postList.addPost(post5);
        postList.addPost(post6);
        postList.addPost(post7);
        postList.addPost(post8);
        postList.addPost(post9);
        postList.addPost(post10);


    }



    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        ProgressBar progressBar = findViewById(R.id.progressBar);
        Button buttonLoad = findViewById(R.id.buttonLoad);
        usersRecyclerView = (RecyclerView) findViewById(R.id.usersRecyclerView);

        usersRecyclerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.INVISIBLE);


        buttonLoad.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
               // progressBar.setVisibility(View.VISIBLE);
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