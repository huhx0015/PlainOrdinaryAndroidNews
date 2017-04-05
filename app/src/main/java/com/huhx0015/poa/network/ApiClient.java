package com.huhx0015.poa.network;

import android.util.Log;
import com.huhx0015.poa.constants.AppConstants;
import com.huhx0015.poa.interfaces.NewsResponseListener;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class ApiClient {

    private static final String LOG_TAG = ApiClient.class.getSimpleName();

    private String mUrl;

    public ApiClient(String url) {
        this.mUrl = url;
    }

    // HTTP GET request
    public void sendGet(NewsResponseListener listener, String type) throws Exception {

        URL obj = new URL(mUrl);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        connection.setRequestMethod(AppConstants.HTTP_GET);

        //add request header
        connection.setRequestProperty(AppConstants.HTTP_USER_AGENT, AppConstants.HTTP_USER_AGENT_ID);

        int responseCode = connection.getResponseCode();
        System.out.println("\nSending 'GET' request to URL : " + mUrl);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        Log.d(LOG_TAG, response.toString());
        System.out.println(response.toString());

        JSONObject jsonObject = new JSONObject(response.toString());
        Log.d(LOG_TAG, "Conversion: " + jsonObject.getString("status"));

        listener.onNewsGetResponse(response.toString(), type);
    }

    // HTTP POST request
    public void sendPost() throws Exception {

        String url = "https://selfsolve.apple.com/wcResults.do";
        URL obj = new URL(url);
        HttpsURLConnection con = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        con.setRequestMethod("POST");
        con.setRequestProperty(AppConstants.HTTP_USER_AGENT, AppConstants.HTTP_USER_AGENT_ID);
        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        int responseCode = con.getResponseCode();
        System.out.println("\nSending 'POST' request to URL : " + url);
        System.out.println("Post parameters : " + urlParameters);
        System.out.println("Response Code : " + responseCode);

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        //print result
        System.out.println(response.toString());

    }
}
