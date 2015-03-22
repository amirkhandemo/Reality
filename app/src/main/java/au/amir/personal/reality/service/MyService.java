package au.amir.personal.reality.service;


import android.util.Log;

import com.google.gson.Gson;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;

import au.amir.personal.reality.model.FactsSheet;


public class MyService  implements Serializable {
    private static final String TAG = "MyService";
	private static final long serialVersionUID = 971297579502546542L;

    private FactsSheet factsSheet;
    private static MyService instance;

    public FactsSheet getFactsSheet() {
        return factsSheet;
    }

    public void setFactsSheet(FactsSheet factsSheet) {
        this.factsSheet = factsSheet;
    }


    public MyService()
	{ 
	}

	public static MyService getInstance() {
		if (instance == null) {
			instance = new MyService();
		}
		return instance;
	}

    public boolean prepareData(String EndPointURL)
    {
        Gson gson = new Gson();

        String Data = fetchFactsData(EndPointURL);

        if (Data==null) return false;

        FactsSheet factsSheet = gson.fromJson(Data, FactsSheet.class);
        MyService.getInstance();

        MyService.getInstance().setFactsSheet(factsSheet);
        return true;
    }

    private String fetchFactsData(String EndPointURL) {

        StringBuilder builder = new StringBuilder();
        HttpClient client = new DefaultHttpClient();
        HttpGet httpGet = new HttpGet(EndPointURL);

        try {
            HttpResponse response = client.execute(httpGet);
            StatusLine statusLine = response.getStatusLine();
            int statusCode = statusLine.getStatusCode();
            if (statusCode == 200) {
                HttpEntity entity = response.getEntity();
                InputStream content = entity.getContent();
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(content));
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                Log.d(TAG, "Successfully Fetched Facts JSON: " + builder.toString());
            } else {
                Log.d(TAG, "Failed to download Facts/JSON -- StatusCode :" + statusCode);
                return null;
            }

            return builder.toString();

        } catch (ClientProtocolException cpe) {
            Log.d(TAG, "an error in HTTP Protocol " + cpe.getMessage());
        } catch (IOException ioe) {
            Log.d(TAG, "an I/O Exception occured " + ioe.getMessage());
        }

        return null;
    }


}
