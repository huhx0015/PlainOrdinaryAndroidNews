package com.huhx0015.poa.network;

import android.util.Log;
import com.huhx0015.poa.constants.AppConstants;
import com.huhx0015.poa.interfaces.NewsResponseListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.net.ssl.HttpsURLConnection;

/**
 * Created by Michael Yoon Huh on 4/4/2017.
 */

public class HttpClient {

    /** CLASS VARIABLES ________________________________________________________________________ **/

    // HTTP VARIABLES:
    private String mUrl;

    // LOGGING VARIABLES:
    private static final String LOG_TAG = HttpClient.class.getSimpleName();

    /** CONSTRUCTOR METHODS ____________________________________________________________________ **/

    // HttpClient(): Constructor method.
    public HttpClient(String url) {
        this.mUrl = url;
    }

    /** HTTP METHODS ___________________________________________________________________________ **/

    // sendGet(): Performs an HTTP GET request and sends the response back to the passed in listener.
    public void sendGet(NewsResponseListener listener, String type) throws Exception {

        URL obj = new URL(mUrl);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();
        connection.setRequestMethod(AppConstants.HTTP_GET); // Sets the request method.
        connection.setRequestProperty(AppConstants.HTTP_USER_AGENT, AppConstants.HTTP_USER_AGENT_ID);

        Log.d(LOG_TAG, "Sending GET request to the following URL: " + mUrl);

        int responseCode = connection.getResponseCode();

        Log.d(LOG_TAG, "Response Code: " + responseCode);

        if (responseCode != AppConstants.HTTP_RESPONSE_200_OK) {
            Log.e(LOG_TAG, "ERROR: An invalid response from the server was received: " + responseCode);
            return;
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        // Reads each line from the input and appends it to the response.
        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }
        input.close();

        Log.d(LOG_TAG, "Response: " + response.toString());

        listener.onNewsGetResponse(response.toString(), type); // Sends response back to listener.
    }

    // sendPost(): Performs an HTTP POST request and sends the response back to the passed in listener.
    public void sendPost(NewsResponseListener listener, String type) throws Exception {

        URL obj = new URL(mUrl);
        HttpsURLConnection connection = (HttpsURLConnection) obj.openConnection();

        //add reuqest header
        connection.setRequestMethod("POST");
        connection.setRequestProperty(AppConstants.HTTP_USER_AGENT, AppConstants.HTTP_USER_AGENT_ID);
        connection.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

        String urlParameters = "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345";

        // Send post request
        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(urlParameters);
        wr.flush();
        wr.close();

        Log.d(LOG_TAG, "Sending POST request to the following URL: " + mUrl);

        int responseCode = connection.getResponseCode();

        Log.d(LOG_TAG, "Response Code: " + responseCode);

        if (responseCode != AppConstants.HTTP_RESPONSE_200_OK) {
            Log.e(LOG_TAG, "ERROR: An invalid response from the server was received: " + responseCode);
            return;
        }

        BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;
        StringBuilder response = new StringBuilder();

        while ((inputLine = input.readLine()) != null) {
            response.append(inputLine);
        }
        input.close();

        //print result
        System.out.println(response.toString());
    }
}
