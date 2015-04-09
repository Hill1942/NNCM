package com.hill1942.newcloudmusic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.hill1942.newcloudmusic.api.APICallBack;
import com.hill1942.newcloudmusic.api.GetAlbumsByName;
import org.json.JSONArray;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        new GetAlbumsByName("AMIT2", new APICallBack(){
            @Override
            public void run(JSONArray array) {
                try {
                    for (int i = 0; i < array.length(); i++) {
                        Log.e("sss", Integer.toString(array.getJSONObject(i).getInt("id")));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}









