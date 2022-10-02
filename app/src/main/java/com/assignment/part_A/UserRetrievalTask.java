package com.assignment.part_A;

import android.app.Activity;
import android.graphics.Bitmap;

import java.util.concurrent.Callable;

public class UserRetrievalTask implements Callable<User>
{
    private Activity uiActivity;
    private RemoteUtilities remoteUtilities;

    public UserRetrievalTask(Activity uiActivity)
    {
        remoteUtilities = RemoteUtilities.getInstance(uiActivity);
        this.uiActivity=uiActivity;
    }

    @Override
    public User call() throws Exception
    {
        User user = null;
        //String endpoint = getEndpoint(this.data);




        return user;
    }
}
