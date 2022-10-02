package com.assignment.part_A;

import android.app.Activity;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;


public class RemoteUtilities
{

    public static RemoteUtilities remoteUtilities = null;

    private Activity uiActivity;

    private RemoteUtilities(Activity uiActivity)
    {
        this.uiActivity = uiActivity;
    }

    public static RemoteUtilities getInstance(Activity uiActivity)
    {
        if (remoteUtilities == null) {
            remoteUtilities = new RemoteUtilities(uiActivity);
        }

        remoteUtilities.setUiActivity(uiActivity);

        return remoteUtilities;
    }

    public void setUiActivity(Activity uiActivity)
    {
        this.uiActivity = uiActivity;
    }


    public HttpURLConnection openConnection(String urlString)
    {
        HttpURLConnection connection = null;

        try
        {
            URL url = new URL(urlString);
            connection = (HttpURLConnection) url.openConnection();
        }
        catch (MalformedURLException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        if (connection == null)
        {
            uiActivity.runOnUiThread(new Runnable()
            {
                @Override
                public void run()
                {
                    Toast.makeText(uiActivity, "Check Internet", Toast.LENGTH_LONG).show();
                }
            });

        }

        return connection;
    }

    public boolean isConnectionOkay(HttpURLConnection conn)
    {
        try
        {
            if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
            {
                return true;
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public String getResponseString(HttpURLConnection conn)
    {
        String data = null;
        try
        {
            InputStream inputStream = conn.getInputStream();
            byte[] byteData = IOUtils.toByteArray(inputStream);
            data = new String(byteData, StandardCharsets.UTF_8);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return data;
    }

}
