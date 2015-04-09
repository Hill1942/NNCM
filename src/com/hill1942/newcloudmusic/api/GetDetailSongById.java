package com.hill1942.newcloudmusic.api;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Kaidi on 2015/4/9.
 */
public class GetDetailSongById {
    int id;
    APICallBack callBack;

    public GetDetailSongById(int id, APICallBack callBack) {
        this.id = id;
        this.callBack = callBack;
        new GetDetailSongByIdTask().execute(APIConstant.NCM_SONG_DETAIL);
    }

    private class GetDetailSongByIdTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String url  = params[0];
                HttpClient httpClient = new DefaultHttpClient();

                List<NameValuePair> querys = new ArrayList<NameValuePair>();
                querys.add(new BasicNameValuePair("ids", "[" + Integer.toString(id) + "]"));
                HttpGet httpGet = new HttpGet(url + "?" + URLEncodedUtils.format(querys, "utf-8"));
                httpGet.addHeader("Cookie", "appver=2.5.4");
                httpGet.addHeader("Referer", "http://music.163.com");

                HttpResponse httpResponse = httpClient.execute(httpGet);
                if (httpResponse.getStatusLine().getStatusCode() == 200) {
                    return EntityUtils.toString(httpResponse.getEntity());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(String msg) {
            try {
                JSONObject jsonObject = new JSONObject(msg);
                JSONArray songs = jsonObject.getJSONArray("songs");
                callBack.run(songs);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
