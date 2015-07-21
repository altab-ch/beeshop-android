package com.example.white.myfirstapp;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.white.myfirstapp.Adapter.HomeShopListAdapter;
import com.example.white.myfirstapp.DataContainer.Company;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by White on 13.01.2015.
 */
public class HomeShopActivity extends ActionBarActivity {

    ListView listView;
    private List<Company> mItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_shop_activity);
        listView = (ListView)findViewById(R.id.listView);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> a, View v,int position, long id)
            {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_home_shop, menu);
        return true;
    }

    public void search (View view) throws UnsupportedEncodingException{
        RequestParams params = new RequestParams();
        params.put("latitude", 46.534187);
        params.put("longitude", 6.562802);
        EditText editText = (EditText) findViewById(R.id.edit_search);
        String keyword = editText.getText().toString();
        params.put("keyword", keyword);
        params.put("range", 25);
        params.put("is_open", 2);
        params.put("type", "restaurant");

        //StringEntity entity = new StringEntity(jsonParams.toString());
        BeeshopRESTClient.post("search", params, new JsonHttpResponseHandler(){
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
        mItems = new ArrayList<Company>();

        Log.i("data", data.toString());
        try {
            JSONArray datas = data.getJSONArray("companies");
            for (int i=0; i < datas.length(); i++){
                JSONObject json = datas.getJSONObject(i);
                Company cp = new Company(json.getInt("id"), json.getString("name"), json.getString("type"), json.getString("description"), json.getInt("latitude"), json.getInt("longitude"), json.getJSONObject("meta"));
                mItems.add(cp);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        listView.setAdapter(new HomeShopListAdapter(this, mItems));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items
        switch (item.getItemId()) {
            case R.id.action_switch_display:
                switchDisplay();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void switchDisplay(){


    }
}
