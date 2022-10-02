package com.assignment.part_A;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
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
    private Activity uiActivity;
    private ProgressBar progressBar;
    private Button enableRecyclerView;

    public BackGroundTaskHandler(Activity uiActivity, ProgressBar progressBar, Button enableRecyclerView)
    {
        this.uiActivity = uiActivity;
        this.progressBar = progressBar;
        this.enableRecyclerView = enableRecyclerView;

    }

    @Override
    public void run()
    {
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        UserRetrievalTask userRetrievalTask = new UserRetrievalTask(uiActivity);

        Future<String> userObjectPlaceHolder =  executorService.submit(userRetrievalTask);

        String successMessage1 = waitingForUserLoad(userObjectPlaceHolder);

        if(successMessage1 != null)
        {
            showToast("Load_User List Successfully Completed ");

            PostRetrievalTask postRetrievalTask = new PostRetrievalTask(uiActivity);

            Future<String> postObjectPlaceHolder = executorService.submit(postRetrievalTask);

            String successMessage2 = waitingForPostLoad(postObjectPlaceHolder);


            if(successMessage2 != null)
            {
                showToast("Load_Post List Successfully Completed ");

                uiActivity.runOnUiThread(new Runnable()
                {
                    @Override
                    public void run()
                    {
                        enableRecyclerView.setVisibility(View.VISIBLE);
                    }
                });
            }
        }

    }

    private String waitingForPostLoad(Future<String> postObjectPlaceHolder)
    {
        String loadPostsResponseData =null;

        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.VISIBLE);
            }
        });

        showToast("Load Post List Starts");

        try
        {
            loadPostsResponseData = postObjectPlaceHolder.get(10000, TimeUnit.MILLISECONDS);
        }
        catch (ExecutionException e)
        {
            e.printStackTrace();
            showError(1, "Load_Posts");
        }
        catch (InterruptedException e)
        {
            e.printStackTrace();
            showError(2, "Load_Posts");
        }
        catch (TimeoutException e)
        {
            e.printStackTrace();
            showError(3, "Load_Posts");
        }


        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.INVISIBLE);
            }
        });


        return  loadPostsResponseData;


    }

    private String waitingForUserLoad(Future<String> userObjectPlaceHolder)
    {
        String loadUsersResponseData =null;

        uiActivity.runOnUiThread(new Runnable()
        {
            @Override
            public void run()
            {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
        showToast("Load User List Starts");


        try
        {
            loadUsersResponseData = userObjectPlaceHolder.get(10000, TimeUnit.MILLISECONDS);
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
