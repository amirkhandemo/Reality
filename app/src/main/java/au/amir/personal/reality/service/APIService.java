package au.amir.personal.reality.service;

import android.app.IntentService;
import android.content.Intent;
import android.os.Bundle;
import android.os.ResultReceiver;
import android.util.Log;

import au.amir.personal.reality.R;
import au.amir.personal.reality.model.FactsSheet;
import au.amir.personal.reality.service.enums.IntentEnums;
import au.amir.personal.reality.service.enums.WebCommandEnums;


public class APIService extends IntentService {

	private static final String TAG = "APIService";
	public static final int STATUS_RUNNING = 0x1;
	public static final int STATUS_ERROR = 0x2;
	public static final int STATUS_FINISHED = 0x3;

	public APIService()
	{
		super(TAG);
	}
	
	public APIService(String name) {
		super(name);
		// TODO Auto-generated constructor stub
	}

	protected void onHandleIntent(Intent intent) {
		
		Log.d(TAG, "onHandledIntent Called....");
		
		final ResultReceiver receiver = intent.getParcelableExtra(IntentEnums.RECEIVER.name());
		Bundle bundle = new Bundle();
		WebCommandEnums command = (WebCommandEnums) intent.getSerializableExtra(IntentEnums.COMMAND.name());
		bundle.putSerializable(IntentEnums.COMMAND.name(), command);
		
		/*
		if (!checkConnectivity()) {
			if (receiver != null) {
				bundle.putString(Intent.EXTRA_TITLE, MessageUtility.getMessage(MessageEnum.REQUIRES_INTERNET_TITLE));
				bundle.putString(Intent.EXTRA_TEXT, MessageUtility.getMessage(MessageEnum.REQUIRES_INTERNET));
				receiver.send(STATUS_ERROR, bundle);
			}

			this.stopSelf();
			return;
		}
		*/

		Log.d(TAG, "Recieved intent for - " + command);

		receiver.send(STATUS_RUNNING, bundle);
		try {

			switch (command) {
			case GET_DATA:

				Log.d(TAG, "Processing GET_DATA");
				// this will throw an exception if problem occur while fetching data
				boolean Success = MyService.getInstance().prepareData(intent.getStringExtra(IntentEnums.WSURL.name()));
                if (!Success)
                    throw new Exception(getResources().getString(R.string.FailureMessage));

				receiver.send(STATUS_FINISHED, bundle);  // update the relevant activity/fragment
                Log.d(TAG, "Processing GET_DATA successfull");
                FactsSheet facts  = MyService.getInstance().getFactsSheet();
                Log.d(TAG, "Title is " + facts.getTitle());
				break;

			}
		} catch (Exception e) {
			Log.d(TAG, e.getMessage(), e);
			bundle.putString(Intent.EXTRA_TEXT, e.toString());
			receiver.send(STATUS_ERROR, bundle);
		}

		this.stopSelf();  // service ends
	}

}
