package com.hill1942.newcloudmusic.model;

import com.hill1942.newcloudmusic.api.APICallBack;
import com.hill1942.newcloudmusic.api.GetSongsByName;
import org.json.JSONArray;

/**
 * Created by Kaidi on 2015/4/9.
 */
public class Song {
    private int id;
    private String name;

    public Song(String name) {
        this.id = -1;
        this.name = name;

    }

    public int GetIdByName(String name) {
        new GetSongsByName(name, new APICallBack() {
            @Override
            public void run(JSONArray array) {
                try {
                    id = array.getJSONObject(0).getInt("id");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        return 0;
    }

    public int GetId() {
        if (id == -1) {
            GetIdByName(name);
        }
        return this.id;
    }
}
