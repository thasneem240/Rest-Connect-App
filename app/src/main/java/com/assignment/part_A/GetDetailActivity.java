package com.assignment.part_A;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class GetDetailActivity extends AppCompatActivity
{
    private static final String CLICKED_USER = "Clicked User";
    private User clickedUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_detail);

        Intent intent = getIntent();

        clickedUser = (User)intent.getSerializableExtra(CLICKED_USER);

        TextView detailUserId = findViewById(R.id.detailUserId);
        TextView detailName = findViewById(R.id.detailName);
        TextView detailUserName = findViewById(R.id.detailUserName);
        TextView detailAddress = findViewById(R.id.detailAddress);
        TextView detailCompany = findViewById(R.id.detailCompany);
        TextView detailWebsite = findViewById(R.id.detailWebsite);
        TextView detailEmail = findViewById(R.id.detailEmail);
        TextView detailPhone = findViewById(R.id.detailPhone);


        if(clickedUser != null)
        {
            detailUserId.setText(String.valueOf(clickedUser.getUser_Id()));
            detailName.setText(clickedUser.getName());
            detailUserName.setText(clickedUser.getUserName());
            detailAddress.setText(clickedUser.getAddress());
            detailCompany.setText(clickedUser.getCompanyDetails());
            detailWebsite.setText(clickedUser.getWebsite());
            detailEmail.setText(clickedUser.getEmail());
            detailPhone.setText(clickedUser.getPhone());
        }



    }


    public static Intent getIntent(Context context, User user)
    {
        Intent intent = new Intent(context, GetDetailActivity.class);
        intent.putExtra(CLICKED_USER,user);

        return intent;
    }
}