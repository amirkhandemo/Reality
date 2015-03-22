package au.amir.personal.reality.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import au.amir.personal.reality.R;


public class SplashScreenActivity extends AbstractFragmentActivity {

    private static final int SPLASH_DISPLAY_TIME = 3000;  /* 3 seconds */
    private static final String TAG = SplashScreenActivity.class.getName();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.splash_screen);


            new Handler().postDelayed(new Runnable() {

                public void run() {
					
					/* Create an intent that will start the main activity. */
                    Intent mainIntent = new Intent(SplashScreenActivity.this, MainActivity.class);

                    startActivity(mainIntent);
					/* Finish splash activity so user cant go back to it. */
                    finish();
					

                }
            }, SPLASH_DISPLAY_TIME);

    }

    @Override
    public void onStart() {
        super.onStart();

    }


    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    public void onStop() {
        super.onStop();

    }


}
