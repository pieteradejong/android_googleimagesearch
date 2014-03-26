package com.codepath.gridimagesearch;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

public class FilterActivity extends ActionBarActivity  {

	private Spinner spinner_color;
	private Spinner spinner_size;
	private Spinner spinner_type;
	private EditText string_site;
	
	private String filter_color;
	private String filter_size;
	private String filter_type;
	private String filter_site;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_filter);
	
		spinner_color = (Spinner)findViewById(R.id.spinner_color);
		spinner_size = (Spinner)findViewById(R.id.spinner_size);
		spinner_type = (Spinner)findViewById(R.id.spinner_type);
		string_site = (EditText)findViewById(R.id.etFilterSite);
		
		filter_color = getIntent().getStringExtra("filter_color");
		filter_size = getIntent().getStringExtra("filter_size");
		filter_type = getIntent().getStringExtra("filter_type");
		filter_site = getIntent().getStringExtra("filter_site");
		
//		Toast.makeText(this, filter_color + filter_size + filter_type + filter_site, Toast.LENGTH_LONG).show();
	
		string_site.setText(filter_site);
		
		// for each spinner:
		ArrayAdapter adapter_color = (ArrayAdapter) spinner_color.getAdapter();
		int spinnerPosition_color = adapter_color.getPosition(filter_color);
		spinner_color.setSelection(spinnerPosition_color);
//		
		ArrayAdapter adapter_size = (ArrayAdapter) spinner_size.getAdapter();
		int spinnerPosition_size = adapter_size.getPosition(filter_size);
		spinner_size.setSelection(spinnerPosition_size);
//		
		ArrayAdapter adapter_type = (ArrayAdapter) spinner_type.getAdapter();
		int spinnerPosition_type = adapter_type.getPosition(filter_type);
		spinner_type.setSelection(spinnerPosition_type);
		
//		ArrayAdapter<CharSequence> adapter_color = ArrayAdapter.createFromResource(this,
//		        R.array.colors_array, android.R.layout.simple_spinner_item);
//		adapter_color.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner_color.setAdapter(adapter_color);
//		
//		ArrayAdapter<CharSequence> adapter_size = ArrayAdapter.createFromResource(this,
//		        R.array.sizes_array, android.R.layout.simple_spinner_item);
//		adapter_size.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner_size.setAdapter(adapter_size);
//		
//		ArrayAdapter<CharSequence> adapter_type = ArrayAdapter.createFromResource(this,
//		        R.array.types_array, android.R.layout.simple_spinner_item);
//		adapter_type.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//		spinner_type.setAdapter(adapter_type);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.filter, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void saveFilter(View v) {
		Intent data = new Intent();
		
//		spinner_color = (Spinner)findViewById(R.id.spinner_color);
//		spinner_size = (Spinner)findViewById(R.id.spinner_size);
//		spinner_type = (Spinner)findViewById(R.id.spinner_type);
//		string_site = (EditText)findViewById(R.id.etFilterSite);
		
		
		filter_color = spinner_color.getSelectedItem().toString();
		filter_size = spinner_size.getSelectedItem().toString();
		filter_type = spinner_type.getSelectedItem().toString();
		filter_site = string_site.getText().toString();
		data.putExtra("filter_color", filter_color);
		data.putExtra("filter_size", filter_size);
		data.putExtra("filter_type", filter_type);
		data.putExtra("filter_site", filter_site);
		
		setResult(RESULT_OK, data);
		finish();
	}

//	@Override
//	public void onItemSelected(AdapterView<?> parent, View view, int pos,
//			long id) {
//
//		Spinner spinner = (Spinner) parent;
//		if(spinner.getId() == R.id.spinner_color) {
////			spinner_color = spinner.getItemAtPosition(pos);
//			spinner_color = spinner.getSelectedItem().toString();
//		} else if (spinner.getId() == R.id.spinner_size) {
//			
//		} else if (spinner.getId() == R.id.spinner_type) {
//		
//		}
//		parent.getItemAtPosition(pos);
//   return;		
//	}
//
//	@Override
//	public void onNothingSelected(AdapterView<?> arg0) {
//		// TODO Auto-generated method stub
//		return;
//		
//	}

}
