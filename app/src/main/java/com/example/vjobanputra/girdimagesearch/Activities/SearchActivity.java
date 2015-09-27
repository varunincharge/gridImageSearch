package com.example.vjobanputra.girdimagesearch.Activities;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.Toast;

import com.example.vjobanputra.girdimagesearch.Models.Image;
import com.example.vjobanputra.girdimagesearch.Models.Setting;
import com.example.vjobanputra.girdimagesearch.R;
import com.example.vjobanputra.girdimagesearch.adapters.ImageArrayAdapter;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchActivity extends AppCompatActivity {

    EditText etQuery;
    GridView gvResults;
    Button btnSearch;
    ArrayList<Image> images;
    ImageArrayAdapter imageAdapter; 
    Setting savedSetting = null;

    private int REQUEST_CODE_SETTINGS = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_activity);
        setupViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    private void setupViews () {
        images = new ArrayList<>();
        imageAdapter = new ImageArrayAdapter(this, images);
        etQuery = (EditText) findViewById(R.id.etQuery);
        gvResults = (GridView) findViewById(R.id.gvResults);
        btnSearch = (Button) findViewById(R.id.btnSearch);
        gvResults.setAdapter(imageAdapter);

        gvResults.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(getApplicationContext(), ImageResultsActivity.class);
                Image image = images.get(position);
                i.putExtra("image", image);
                startActivityForResult(i, REQUEST_CODE_SETTINGS);
            }
        });

        gvResults.setOnScrollListener(new EndlessScrollListener() {
            @Override
            public boolean onLoadMore(int page, int totalItemsCount) {
                search(totalItemsCount);
                return true;
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.miSettings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            intent.putExtra("savedSetting", savedSetting);
            startActivityForResult(intent, REQUEST_CODE_SETTINGS);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_SETTINGS) {
            if (resultCode == RESULT_OK) {
                savedSetting = (Setting) data.getSerializableExtra("setting");
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onImageSearch(View view) {
        if (!isNetworkAvailable()) {
            Toast.makeText(getApplicationContext(), "Network unavailable", Toast.LENGTH_SHORT).show();
            return;
        }
        images.clear();
        search(0);
    }

    private void search(int offset) {
        String query = etQuery.getText().toString();
        AsyncHttpClient client = new AsyncHttpClient();
        String apiUrl = "https://ajax.googleapis.com/ajax/services/search/images";
        RequestParams params = new RequestParams();
        params.put("v", "1.0");
        params.put("rsz", "8");
        params.put("q", query);
        params.put("start", offset);
        if (savedSetting != null) {
            String imageColor = savedSetting.getImageColor();
            if (!imageColor.equals("any")) {
                params.put("imgcolor", imageColor);
            }
            String imageSize = savedSetting.getImagesize();
            if (!imageColor.equals("any")) {
                params.put("imgsz", imageSize);
            }
            String imageType = savedSetting.getImageType();
            if (!imageColor.equals("any")) {
                params.put("imgtype", imageType);
            }
            String imageSite = savedSetting.getImageSite();
            if (!imageColor.equals("")) {
                params.put("as_sitesearch", imageSite);
            }
        }
        client.get(apiUrl, params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                try {
                    JSONArray imageJsonResults = response.getJSONObject("responseData").getJSONArray("results");
                    updateImageAdapter(imageJsonResults);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    private void updateImageAdapter(JSONArray in) {
        images.addAll(Image.fromJsonArray(in));
        imageAdapter.notifyDataSetChanged();
    }

    private Boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null &&
                activeNetworkInfo.isAvailable() &&
                activeNetworkInfo.isConnected();
    }
}
