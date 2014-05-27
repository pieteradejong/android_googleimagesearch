package com.codepath.gridimagesearch;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

public class SearchActivity extends ActionBarActivity {
	EditText etQuery;
	GridView gvResults;
	Button btnSearch;
	ArrayList<ImageResult> imageResults = new ArrayList<ImageResult>();
    ImageResultArrayAdapter imageAdapter;
    private final int REQUEST_CODE = 20;
    
    private String filter_color = "";
    private String filter_size = "";
    private String filter_type = "";
    private String filter_site = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search);
		setupViews();
		imageAdapter = new ImageResultArrayAdapter(this, imageResults);
		gvResults.setAdapter(imageAdapter);
		gvResults.setOnItemClickListener(new OnItemClickListener(){
			@Override
			public void onItemClick(AdapterView<?> adapter, View parent, int position, long l) {
				Intent i = new Intent(getApplicationContext(), ImageDisplayActivity.class);
				ImageResult imageResult = imageResults.get(position);
				i.putExtra("result", imageResult);
				startActivity(i);
			}
		});
		
		gvResults.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
                // Triggered only when new data needs to be appended to the list
                // Add whatever code is needed to append new items to your AdapterView
                customLoadMoreDataFromApi(totalItemsCount);
                // or customLoadMoreDataFromApi(totalItemsCount); 
        }
        });
	}
	
	// Append more data into the adapter
  public void customLoadMoreDataFromApi(int offset) {
  // This method probably sends out a network request and appends new data items to your adapter. 
  // Use the offset value and add it as a parameter to your API request to retrieve paginated data.
  // Deserialize API response and then construct new objects to append to the adapter
	String query = etQuery.getText().toString();
	AsyncHttpClient client = new AsyncHttpClient();
	client.get("https://ajax.googleapis.com/ajax/services/search/images?" + 
     "rsz=8" +
     "&start=" + offset + 
     "&v=1.0" + 
     "&as_sitesearch=" + filter_site + 
     "&imgcolor=" + filter_color + 
     "&imgsz=" + filter_size + 
     "&imgtype=" + filter_type +
     "&q=" + Uri.encode(query), 
     new JsonHttpResponseHandler() {
				@Override         
				public void onSuccess(JSONObject response) {
					JSONArray imageJsonResults = null;
					try {
						imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
					    imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
					} catch (JSONException e) {
						e.printStackTrace();
					}
			  }
   });
}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.search, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			onFilterAction();
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void setupViews() {
		etQuery = (EditText) findViewById(R.id.etQuery);
		gvResults = (GridView) findViewById(R.id.gvResults);
		btnSearch = (Button) findViewById(R.id.btnSearch);
	}
	
	public void onImageSearch(View v) {
		String query = etQuery.getText().toString();
		Toast.makeText(this, "Searching for " + query, Toast.LENGTH_SHORT).show();
		AsyncHttpClient client = new AsyncHttpClient();
		client.get("https://ajax.googleapis.com/ajax/services/search/images?" + 
	     "rsz=8" +
	     "&start=" + 0 + 
	     "&v=1.0" + 
	     "&as_sitesearch=" + filter_site + 
	     "&imgcolor=" + filter_color + 
	     "&imgsz=" + filter_size + 
	     "&imgtype=" + filter_type +
	     "&q=" + Uri.encode(query), 
	     new JsonHttpResponseHandler() {
					@Override         
					public void onSuccess(JSONObject response) {
						JSONArray imageJsonResults = null;
						try {
							imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
							imageResults.clear();
					    imageAdapter.addAll(ImageResult.fromJSONArray(imageJsonResults));
						} catch (JSONException e) {
							e.printStackTrace();		
						}
				  }
	   });
	}
	
	public void onFilterAction() {
		
		Intent i = new Intent(getApplicationContext(), FilterActivity.class);
		i.putExtra("filter_color", filter_color);
		i.putExtra("filter_size", filter_size);
		i.putExtra("filter_type", filter_type);
		i.putExtra("filter_site", filter_site);
		startActivityForResult(i, REQUEST_CODE);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
	  if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {

		  filter_color = data.getExtras().getString("filter_color");
		  filter_size = data.getExtras().getString("filter_size");
		  filter_type = data.getExtras().getString("filter_type");
		  filter_site = data.getExtras().getString("filter_site");
		  
		 onImageSearch(btnSearch);
	     Toast.makeText(this, filter_color + " " + filter_size + " " + filter_type + " " + filter_site, Toast.LENGTH_SHORT).show();
	  }
	} 
}
