package com.assignment.part_A;

import android.app.Activity;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class BackGroundTaskHandler implements Runnable
{
    Activity uiActivity;
    ProgressBar progressBar;

    public BackGroundTaskHandler(Activity uiActivity, ProgressBar progressBar)
    {
        this.uiActivity = uiActivity;
        this.progressBar = progressBar;
    }

    @Override
    public void run()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        UserRetrievalTask userRetrievalTask = new UserRetrievalTask(uiActivity);

        Future<String> userObjectPlaceHolder =  executorService.submit(userRetrievalTask);

        String loadedUserObject = waitingForUserObject(userObjectPlaceHolder);

    }

    private String waitingForUserObject(Future<String> userObjectPlaceHolder)
    {
        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        showToast("Load User List Starts");

       String loadUsersResponseData =null;

        try
        {
            loadUsersResponseData = userObjectPlaceHolder.get(20000, TimeUnit.MILLISECONDS);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            showError(1, "Load_Users");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            showError(2, "Load_Users");
        }
        catch (TimeoutException e)
        {
            e.printStackTrace();
            showError(3, "Load_Users");
        }


        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        showToast("Load_User List Successfully Completed ");

        return  loadUsersResponseData;
    }

    private void showToast(String message)
    {
        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                Toast.makeText(uiActivity,message,Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showError(int code, String taskName)
    {
        if(code ==1)
        {
            showToast(taskName+ " Task Execution Exception");
        }
        else if(code ==2)
        {
            showToast(taskName+" Task Interrupted Exception");
        }
        else if(code ==3)
        {
            showToast(taskName+" Task Timeout Exception");
        }
        else
        {
            showToast(taskName+" Task could not be performed. Restart!!");
        }
    }
}
