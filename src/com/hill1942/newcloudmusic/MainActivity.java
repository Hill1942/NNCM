package com.hill1942.newcloudmusic;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import com.hill1942.newcloudmusic.api.APICallBack;
import com.hill1942.newcloudmusic.api.GetAlbumsByName;
import com.hill1942.newcloudmusic.api.GetSongsByName;
import com.hill1942.newcloudmusic.api.GetSpecSongById;
import com.hill1942.newcloudmusic.model.Song;
import org.json.JSONArray;

public class MainActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        Song song = new Song("战之祭");
        int song_id = song.GetId();
       // Log.e("song_id", Integer.toString(song_id));
        new GetSpecSongById(31311125, new APICallBack() {
            @Override
            public void run(JSONArray array) {

            }
        });

    }
}









