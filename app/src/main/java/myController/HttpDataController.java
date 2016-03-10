package myController;

import android.os.AsyncTask;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import myInterface.HttpGetDataListener;

/**
 * Created by cooper on 2016.3.7.
 */
public class HttpDataController extends AsyncTask<String, Void, String> {
    private HttpURLConnection urlConnection;
    private InputStream inputStream;

    private URL url;
    private HttpGetDataListener listener;

    public HttpDataController(URL url, HttpGetDataListener listener) {
        this.url = url;
        this.listener = listener;
    }

    @Override
    protected String doInBackground(String... params) {
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            inputStream = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader br = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            urlConnection.disconnect();
            try {
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @Override
    protected void onPostExecute(String s) {
        listener.getDataUrl(s);
        super.onPostExecute(s);
    }


}
