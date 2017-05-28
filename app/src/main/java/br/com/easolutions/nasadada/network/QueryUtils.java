package br.com.easolutions.nasadada.network;

/**
 * Created by elvisaron on 16/05/17.
 */

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

import br.com.easolutions.nasadada.AstronomyPictureDay;

public final class QueryUtils {

    private static final String LOG_TAG = QueryUtils.class.getName();

    /**
     * Crie um construtor privado porque ninguém deve criar um objeto {@link QueryUtils}.
     * Esta classe destina-se apenas a manter variáveis estáticas e métodos, que podem ser acessados diretamente a partir do
     * nome da classe QueryUtils (e uma instância de objeto de QueryUtils não é necessária).
     */
    private QueryUtils() {
    }

    public static AstronomyPictureDay fetchAstronomyPictureDay(String requestUrl){

//        try {
//            Thread.sleep(2000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        // Create URL object
        URL url = createUrl(requestUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Extract relevant fields from the JSON response and create an {@link Event} object
        AstronomyPictureDay astronomyPictureDay =  extractAstronomyPictureDay(jsonResponse);

        // Return the {@link Event}
        return astronomyPictureDay;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }


    /**
     * Return a list of {@link AstronomyPictureDay} objects that has been built up from
     * parsing a JSON response.
     */
    public static AstronomyPictureDay extractAstronomyPictureDay(String astronomyPicDayJSON) {

        AstronomyPictureDay astronomyPictureDay = null;

        try {

            JSONObject root = new JSONObject(astronomyPicDayJSON);
            String copyright = root.getString("copyright");
            String date = root.getString("date");
            String explanation = root.getString("explanation");
            String hdurl = root.getString("hdurl");
            String title = root.getString("title");
            String url = root.getString("url");

            astronomyPictureDay = new AstronomyPictureDay(copyright, date, explanation, hdurl, title, url);

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e);
        }

        return  astronomyPictureDay;
    }

}
