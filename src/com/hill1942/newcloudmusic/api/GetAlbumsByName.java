package com.hill1942.newcloudmusic.api;

import android.os.AsyncTask;
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


public class GetAlbumsByName {

    String name;
    APICallBack callBack;

    public GetAlbumsByName(String name, APICallBack callBack) {
        this.name = name;
        this.callBack = callBack;
        new GetAblumsByNameTask().execute(APIConstant.NCM_SEARCH_GET);
    }

    private class GetAblumsByNameTask extends AsyncTask<String, Void, String> {
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
                querys.add(new BasicNameValuePair("type", "10"));
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
                int albumCount = jsonObject.getJSONObject("result").getInt("albumCount");
                if (albumCount > 0) {
                    JSONArray albums = jsonObject.getJSONObject("result").getJSONArray("albums");
                    callBack.run(albums);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
