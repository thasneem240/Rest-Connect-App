package com.assignment.part_A;

import android.app.Activity;
import android.graphics.Bitmap;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.util.concurrent.Callable;

public class UserRetrievalTask implements Callable<String>
{
    private Activity uiActivity;
    private RemoteUtilities remoteUtilities;
    private String baseUrl;
    private UserList userList = UserList.getInstance();

    public UserRetrievalTask(Activity uiActivity)
    {
        baseUrl = "https://jsonplaceholder.typicode.com/users";
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
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    int id = jsonObject.getInt("id");
                    String name = jsonObject.getString("name");
                    String username = jsonObject.getString("username");
                    String email = jsonObject.getString("email");

                    JSONObject addressObj = jsonObject.getJSONObject("address");
                    String street = addressObj.getString("street");
                    String suite = addressObj.getString("suite");
                    String city = addressObj.getString("city");
                    String zipcode = addressObj.getString("zipcode");
                    JSONObject geoObj = addressObj.getJSONObject("geo");
                    String latitude = geoObj.getString("lat");
                    String longitude = geoObj.getString("lng");

                    String fullAddress = String.format("%s, %s, %s, %s , latitude: %s, longitude: %s",
                            street,suite,city,zipcode,latitude,longitude);


                    String phone = jsonObject.getString("phone");
                    String website = jsonObject.getString("website");

                    JSONObject companyObj = jsonObject.getJSONObject("company");
                    String companyName = companyObj.getString("name");
                    String catchPhrase = companyObj.getString("catchPhrase");
                    String bs = companyObj.getString("bs");

                    String companyDetails = String.format("%s, %s, %s",companyName,catchPhrase,bs);

                    User newUser = new User();

                    newUser.setUser_Id(id);
                    newUser.setName(name);
                    newUser.setUserName(username);
                    newUser.setEmail(email);
                    newUser.setAddress(fullAddress);
                    newUser.setPhone(phone);
                    newUser.setWebsite(website);
                    newUser.setCompanyDetails(companyDetails);

                    userList.addUser(newUser);

                }

                try
                {
                    Thread.sleep(3000);
                }
                catch(Exception e)
                {

                }


                successMessage = "SUCCESSFULLY LOADED THE USER DATA LIST";

            }


        }

        return successMessage;
    }


}
