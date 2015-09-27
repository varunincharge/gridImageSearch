package com.example.vjobanputra.girdimagesearch.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Spinner;

import com.example.vjobanputra.girdimagesearch.Models.Setting;
import com.example.vjobanputra.girdimagesearch.R;
import com.example.vjobanputra.girdimagesearch.adapters.SpinnerCustomAdapter;

/**
 * Created by vjobanputra on 9/26/15.
 */
public class SettingsActivity extends AppCompatActivity {

    Spinner spImageSize;
    Spinner spImageColor;
    Spinner spImageType;
    EditText etImageSite;

    Setting savedSetting;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);
        savedSetting = (Setting) getIntent().getSerializableExtra("savedSetting");
        setupViews();
    }

    private void setupViews()  {
        spImageSize = (Spinner) findViewById(R.id.spImageSize);
        spImageColor = (Spinner) findViewById(R.id.spImageColor);
        spImageType = (Spinner) findViewById(R.id.spImageType);
        etImageSite = (EditText) findViewById(R.id.etImageSite);

        //spImageSize.setAdapter(new ArrayAdapter<String>(this, R.layout.spinner_item, sizeOptions));
        spImageSize.setAdapter(new SpinnerCustomAdapter(this, Setting.sizeOptions));
        spImageColor.setAdapter(new SpinnerCustomAdapter(this, Setting.colorOptions));
        spImageType.setAdapter(new SpinnerCustomAdapter(this, Setting.typeOptions));

        if (savedSetting != null) {
            spImageSize.setSelection(savedSetting.imageSizeIndex);
            spImageColor.setSelection(savedSetting.imageColorIndex);
            spImageType.setSelection(savedSetting.imageTypeIndex);
            etImageSite.setText(savedSetting.imageSite);
        }
    }

    public void onSettingsSave(View view) {

        int size = spImageSize.getSelectedItemPosition();
        int color = spImageColor.getSelectedItemPosition();
        int type = spImageType.getSelectedItemPosition();
        String site = etImageSite.getText().toString();
        Setting setting = new Setting(size, color, type, site);
        Intent intent = new Intent();
        intent.putExtra("setting", setting);
        setResult(RESULT_OK, intent);
        finish();
    }
}
