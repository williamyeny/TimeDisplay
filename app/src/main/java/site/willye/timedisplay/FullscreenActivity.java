package site.willye.timedisplay;

import android.annotation.SuppressLint;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

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
            dummyText.setText(String.valueOf(count));
            count++;
            loopHandler.postDelayed(this, 1000);
        }
    };
    int delay = 1000; //milliseconds
    TextView dummyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_fullscreen);
        dummyText = (TextView)findViewById(R.id.fullscreen_content);

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

}
