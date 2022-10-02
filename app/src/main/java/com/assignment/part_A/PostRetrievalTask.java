package com.assignment.part_A;

import android.app.Activity;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class PostRetrievalTask implements Callable<String>
{
    private Activity uiActivity;
    private RemoteUtilities remoteUtilities;
    private String baseUrl;
    private PostList postList = PostList.getInstance();

    public PostRetrievalTask(Activity uiActivity)
    {
        baseUrl = "https://jsonplaceholder.typicode.com/posts";
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.uiActivity=uiActivity;
    }

    @Override
    public String call() throws Exception
    {
       String successMessage = null;

        String response = null;
        String endpoint = baseUrl;
        HttpURLConnection connection = remoteUtilities.openConnection(endpoint);

        if(connection!=null)
        {

            if(remoteUtilities.isConnectionOkay(connection)==true)
            {
                response = remoteUtilities.getResponseString(connection);
                connection.disconnect();

                JSONArray jsonArray = new JSONArray(response);

                for( int i = 0; i < jsonArray.length(); i++)
                {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);

                    int userId = jsonObject.getInt("userId");

                    String title = jsonObject.getString("title");
                    String body = jsonObject.getString("body");

                    Post newPost = new Post(userId,title,body);


                    postList.addPost(newPost);

                }

                try
                {
                    Thread.sleep(3000);
                }
                catch(Exception e)
                {

                }


                successMessage = "SUCCESSFULLY LOADED ALL THE POST";

            }


        }

        return successMessage;
    }


}
