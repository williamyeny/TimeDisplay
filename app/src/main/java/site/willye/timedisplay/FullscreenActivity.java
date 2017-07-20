package site.willye.timedisplay;

import android.annotation.SuppressLint;
import android.graphics.PixelFormat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class FullscreenActivity extends AppCompatActivity {
    Handler loopHandler = new Handler();
    int count = 0;
    Runnable loopRunnable = new Runnable() {
        @Override
        public void run() {
            updateData();
            loopHandler.postDelayed(this, 1000);
        }
    };

    private void updateData() {
//        dummyText.setText(String.valueOf(count));
        count++;
    }

    int delay = 1000; //milliseconds
    TextView dummyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
//        dummyText = (TextView)findViewById(R.id.fullscreen_content);

    }
    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window window = getWindow();
        // Eliminates color banding
        window.setFormat(PixelFormat.RGBA_8888);
    }
    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
        decorView.setSystemUiVisibility(uiOptions);

        loopHandler.postDelayed(loopRunnable, 0);

    }

    public void onPause() {
        super.onPause();

        loopHandler.removeCallbacks(loopRunnable);
    }

    public static JSONObject getRoutes() throws IOException, JSONException  { //CCX Bus Route: 4009768
        return getJSONObjectFromURL("https://transloc-api-1-2.p.mashape.com/routes.json?agencies=176");
    }

    public static JSONObject getArrivalEstimates(String routeId) throws IOException, JSONException  {
        return getJSONObjectFromURL("https://transloc-api-1-2.p.mashape.com/arrival-estimates.json?agencies=176&routes=" + routeId);
    }

    public static JSONObject getStops() throws IOException, JSONException  {
        return getJSONObjectFromURL("https://transloc-api-1-2.p.mashape.com/stops.json?agencies=176");
    }

    public static JSONObject getJSONObjectFromURL(String urlString) throws IOException, JSONException {
        HttpURLConnection urlConnection = null;
        URL url = new URL(urlString);
        urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("GET");
        urlConnection.setReadTimeout(10000 /* milliseconds */ );
        urlConnection.setConnectTimeout(15000 /* milliseconds */ );
        urlConnection.setDoOutput(true);
        urlConnection.setRequestProperty("X-Mashape-Key", "JyC0PK4lVvmshqjyYK2mig6c6HbEp123D5SjsnAoavtkd6md1v");
        urlConnection.connect();

        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
        StringBuilder sb = new StringBuilder();

        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();

        String jsonString = sb.toString();
        Log.d("JSON" , jsonString);

        return new JSONObject(jsonString);
    }



}
