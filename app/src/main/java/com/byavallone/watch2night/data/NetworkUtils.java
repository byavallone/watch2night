package com.byavallone.watch2night.data;

import android.net.Uri;
import android.util.Log;

import org.json.JSONArray;
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
import java.util.ArrayList;

/**
 * Class used to manage all the network methods
 */
public class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();

    // hold the base URL for top rated
    private static final String URL_V3_TOP_RATED = "https://api.themoviedb.org/3/movie/top_rated";
    // holds the base URL for the most popular
    private static final String URL_v3_MOST_POPULAR = "https://api.themoviedb.org/3/movie/popular";
    //String that holds the parameter for the api key
    final static String API_KEY_PARAM = "api_key";

    final static String API_LANGUAGE_PARAM = "language";

    final static String API_LANGUAGE_ENGLISH = "en-US";

    final static String MOST_POPULAR_INDEX = "1";
    // Holds the base URL that will be used to retrieve each movie poster
    private static final String POSTER_BASE_URL = "https://image.tmdb.org/t/p/w500";

    //API V4 Test URLs
    // hold the base URL for the new version of the API
    private static final String URL_V4_BASE = "https://api.themoviedb.org/4/list/1";
    // String that holds the parameter for the sort type
    final static String API_SORT_BY_PARAM = "sort_by";
    // String that holds the value for the sort by parameter
    final static String API_SORT_BY_AVERAGE = "vote_average.asc";
    // TODO ADD KEY
    private final static String API_KEY = "";

    /**
     * Returns new URL object from the given string URL.
     */
    public static URL createUrl(String sortBy) {

        Uri builtUri;
        if(sortBy.equalsIgnoreCase(MOST_POPULAR_INDEX)) {
            builtUri = Uri.parse(URL_v3_MOST_POPULAR).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .appendQueryParameter(API_LANGUAGE_PARAM, API_LANGUAGE_ENGLISH)
                    .build();
        }else{
            builtUri = Uri.parse(URL_V3_TOP_RATED).buildUpon()
                    .appendQueryParameter(API_KEY_PARAM, API_KEY)
                    .appendQueryParameter(API_LANGUAGE_PARAM, API_LANGUAGE_ENGLISH)
                    .build();

        }

        URL url = null;
        try {
            url = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        Log.v(TAG, "Built URI " + url);
        return url;
    }

    /**
     * Method used to make the http request for the given url
     * @param url
     * @return
     * @throws IOException
     */
    public static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if(url == null){
            return jsonResponse;
        }
        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try{
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setReadTimeout(10000);
            urlConnection.setConnectTimeout(15000);
            urlConnection.connect();
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK){
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            }else{
                Log.e(TAG, "Error code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(TAG, e.getMessage());
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                // function must handle java.io.IOException here
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
     * Method used to parse the list of movies retrieved from the API
     * @param jsonString
     * @return
     */
    public static ArrayList<Movies> parseFromMoviesString(String jsonString){

        ArrayList<Movies> movies = new ArrayList<>();

        try {
            JSONObject response = new JSONObject(jsonString);
            JSONArray results = response.getJSONArray("results");
            for(int i=0; i<results.length(); i++){
                JSONObject jSonMovie = results.getJSONObject(i);

                String title = "";
                if(jSonMovie.has("title")){
                    title = jSonMovie.getString("title");
                }

                String releaseDate ="";
                if(jSonMovie.has("release_date")){
                    releaseDate = jSonMovie.getString("release_date");
                }

                String poster_path ="";
                if(jSonMovie.has("poster_path")){
                    poster_path = POSTER_BASE_URL;
                    poster_path = poster_path.concat(jSonMovie.getString("poster_path"));
                }

                String background_path ="";
                if(jSonMovie.has("backdrop_path")){
                    background_path = POSTER_BASE_URL;
                    background_path = background_path.concat(jSonMovie.getString("backdrop_path"));
                }

                String synopsis ="";
                if(jSonMovie.has("overview")){
                    synopsis = jSonMovie.getString("overview");
                }

                String vote_average ="";
                if(jSonMovie.has("vote_average")){
                    vote_average = jSonMovie.getString("vote_average");
                }

                //Creating one movies with all information retrieved.
                if(!title.isEmpty() && !releaseDate.isEmpty() && !background_path.isEmpty() && !poster_path.isEmpty() && !synopsis.isEmpty()) {
                    Movies movie = new Movies(title, releaseDate, poster_path, background_path, vote_average, synopsis);
                    movies.add(movie);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

}
