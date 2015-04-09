package com.hill1942.newcloudmusic;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import com.hill1942.newcloudmusic.api.APICallBack;
import com.hill1942.newcloudmusic.api.GetDetailSongById;
import com.hill1942.newcloudmusic.api.GetSongsByName;
import org.json.JSONArray;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        final TextView textView = (TextView) findViewById(R.id.song_name);


        final EditText editText = (EditText)findViewById(R.id.enter_name);
       // final String name = editText.getText().toString();

        //Song song = new Song("战之祭");

        Button button = (Button)findViewById(R.id.download);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editText.getText().toString();
                new GetSongsByName(name, new APICallBack() {
                    @Override
                    public void run(JSONArray array) {
                        try {
                            int id = array.getJSONObject(0).getInt("id");
                            textView.setText("Song id: " + Integer.toString(id));
                            new GetDetailSongById(id, new APICallBack() {
                                @Override
                                public void run(JSONArray array){
                                    try {
                                        String url = array.getJSONObject(0).getString("mp3Url");
                                        textView.setText("Song url: " + url);
                                        new DownloadSongTask().execute(url);

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });

            }
        });






       // Log.e("song_id", Integer.toString(song_id));


    }

    private class DownloadSongTask extends AsyncTask<String, Void, String> {
        protected String doInBackground(String... urlStr) {
            try {
                URL url = new URL(urlStr[0]);
                InputStream inputStream = url.openStream();
                BufferedOutputStream bos = new BufferedOutputStream(
                        new FileOutputStream("/sdcard/Download/test.mp3"));
                byte[] buff = new byte[1024];
                int hasRead = 0;
                while ((hasRead = inputStream.read(buff)) > 0) {
                    bos.write(buff, 0, hasRead);
                }
                inputStream.close();

                inputStream.close();
                bos.close();

                return "done";
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String msg) {
            Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
        }

    }
}









