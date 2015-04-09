package com.hill1942.newcloudmusic.api;

import android.os.AsyncTask;
import android.util.Log;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
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
public class GetSongsByName {
    String name;
    APICallBack callBack;

    public GetSongsByName(String name, APICallBack callBack) {
        this.name = name;
        this.callBack = callBack;
        new GetSongsByNameTask().execute(APIConstant.NCM_SEARCH_GET);
    }

    private class GetSongsByNameTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                String url  = params[0];
                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(url);
                httpPost.addHeader("Cookie", "appver=2.5.4");
                httpPost.addHeader("Referer", "http://music.163.com");

                List<NameValuePair> querys = new ArrayList<NameValuePair>();
                querys.add(new BasicNameValuePair("s", name));
                querys.add(new BasicNameValuePair("type", "1"));  // type 1 means searching songs
                querys.add(new BasicNameValuePair("offset", "0"));
                querys.add(new BasicNameValuePair("sub", "false"));
                querys.add(new BasicNameValuePair("limit", "20"));
                httpPost.setEntity(new UrlEncodedFormEntity(querys, HTTP.UTF_8));

                HttpResponse httpResponse = httpClient.execute(httpPost);
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
                int songCount = jsonObject.getJSONObject("result").getInt("songCount");
                if (songCount > 0) {
                    JSONArray songs = jsonObject.getJSONObject("result").getJSONArray("songs");
                    callBack.run(songs);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
