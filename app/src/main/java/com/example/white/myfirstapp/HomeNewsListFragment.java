package com.example.white.myfirstapp;

import android.annotation.TargetApi;
import android.app.ListFragment;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import com.example.white.myfirstapp.Adapter.HomeNewsListAdapter;
import com.example.white.myfirstapp.DataContainer.News;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by White on 09.01.2015.
 */
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class HomeNewsListFragment extends ListFragment{
    private List<News> mItems;        // ListView items list

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // initialize the items list
        mItems = new ArrayList<News>();
        setListAdapter(new HomeNewsListAdapter(getActivity(), mItems));
        updateNewsNear();
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // remove the dividers from the ListView of the ListFragment
        getListView().setDivider(null);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        // retrieve theListView item
        News item = mItems.get(position);

        // do something
        //Toast.makeText(getActivity(), item.title, Toast.LENGTH_SHORT).show();
    }

    public void updateNewsNear (){
        RequestParams params = new RequestParams();
        params.put("latitude", 46.534187);
        params.put("longitude", 6.562802);

        //StringEntity entity = new StringEntity(jsonParams.toString());
        BeeshopRESTClient.post("newsnear", params, new JsonHttpResponseHandler(){
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
                Log.i("test14", responseString);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("test14", errorResponse.toString());
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.i("test14", errorResponse.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                //Log.i("test15", response.toString());
                updateList(response);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                Log.i("test16", response.toString());
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                super.onSuccess(statusCode, headers, responseString);
                Log.i("test17", responseString);
            }
        });
    }

    public void updateList (JSONObject data){
        // initialize the items list
        mItems = new ArrayList<News>();

        Log.i("data", data.toString());
        try {
            JSONArray datas = data.getJSONArray("news");
            for (int i=0; i < datas.length(); i++){
                JSONObject jsonNews = datas.getJSONObject(i);
                //Log.i("date format", jsonNews.getString("start_time"));
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String start_time = jsonNews.getString("start_time");
                String end_time = jsonNews.getString("end_time");
                //JSONObject meta = jsonNews.getJSONObject("meta");

                try {
                    Date start = format.parse(start_time);
                    Date end = format.parse(start_time);
                    JSONObject meta = jsonNews.getJSONObject("meta");
                    News news = new News(jsonNews.getInt("id"), jsonNews.getString("news_type"), meta.getString("description"), start, end);
                    mItems.add(news);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        setListAdapter(new HomeNewsListAdapter(getActivity(), mItems));

    }
}
