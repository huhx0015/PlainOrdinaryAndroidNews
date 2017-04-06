package com.huhx0015.poa.network;

import android.util.Log;
import com.huhx0015.poa.constants.AppConstants;
import com.huhx0015.poa.interfaces.NewsResponseListener;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

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

    private HttpURLConnection initConnection(String requestMethod, String parameters) throws Exception {
        URL obj = new URL(mUrl);
        HttpURLConnection connection = (HttpURLConnection) obj.openConnection();

        connection.setRequestMethod(requestMethod); // Sets the request method.
        connection.setRequestProperty(AppConstants.HTTP_USER_AGENT, AppConstants.HTTP_USER_AGENT_ID);

        if (parameters != null) {
            connection.setDoOutput(true);
            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            outputStream.writeBytes(parameters);
            outputStream.flush();
            outputStream.close();
        }
        return connection;
    }

    private void readResponseFromServer(HttpURLConnection connection, NewsResponseListener listener, String type) throws IOException {
        int responseCode = connection.getResponseCode();

        Log.d(LOG_TAG, "Response Code: " + responseCode);

        if (responseCode != AppConstants.HTTP_RESPONSE_200_OK) {
            Log.e(LOG_TAG, "ERROR: An invalid response from the server was received: " + responseCode);
            listener.onNewsGetResponse(null, AppConstants.HTTP_RESPONSE_ERROR);
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

    // sendGet(): Performs an HTTP GET request and sends the response back to the passed in listener.
    public void sendGet(NewsResponseListener listener, String type) throws Exception {
        HttpURLConnection connection = initConnection(AppConstants.HTTP_GET, null);

        Log.d(LOG_TAG, "Sending GET request to the following URL: " + mUrl);

        readResponseFromServer(connection, listener, type);
    }

    // sendPost(): Performs an HTTP POST request and sends the response back to the passed in listener.
    public void sendPost(NewsResponseListener listener, String type) throws Exception {
        HttpURLConnection connection = initConnection(AppConstants.HTTP_POST, "sn=C02G8416DRJM&cn=&locale=&caller=&num=12345");

        Log.d(LOG_TAG, "Sending POST request to the following URL: " + mUrl);

        readResponseFromServer(connection, listener, type);
    }
}
