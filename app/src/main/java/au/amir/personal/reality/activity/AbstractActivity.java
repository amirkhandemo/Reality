package au.amir.personal.reality.activity;


import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

import au.amir.personal.reality.service.DetachableResultReceiver;


public abstract class AbstractActivity extends Activity implements DetachableResultReceiver.Receiver {

    private ProgressDialog progressDialog;
    protected DetachableResultReceiver mReceiver;
    private static final String TAG = AbstractActivity.class.getName();
    private boolean destroyed = false;

    @Override
    protected void onStart() {
        super.onStart();

     }

    @Override
    public void onStop() {
        super.onStop();

     }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mReceiver.setReceiver(null);
        destroyed = true;
        if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mReceiver = new DetachableResultReceiver(new Handler());
        mReceiver.setReceiver(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
    }

    public void onPause() {
        super.onPause();

        mReceiver.setReceiver(null); // clear receiver so no leaks.
    }

    public void onResume() {
        super.onResume();

        mReceiver.setReceiver(this);
    }

    public void showProgressDialog() {
        showProgressDialog("");
    }
    public void showProgressDialog(String message) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
        }
        progressDialog.setMessage(message);

        // check to make sure we aren't about to finish or have finished
        if (!destroyed && !isFinishing()) {
            progressDialog.show();
        }
    }
    public void dismissProgressDialog() {
        if (progressDialog != null && !destroyed) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }


    public boolean onReceiveResult(int resultCode, Bundle resultData) {
        return false;
    }
}