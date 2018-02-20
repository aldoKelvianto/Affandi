package com.aldoapps.affandi.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.aldoapps.affandi.sample.fiveimage.FiveImageActivity;
import com.aldoapps.affandi.sample.fiverandomimage.FiveRandomImageActivity;
import com.aldoapps.affandi.sample.imagelist.ImageListActivity;
import com.aldoapps.affandi.sample.singleimage.SingleImageActivity;

/**
 * Created by aldo on 21/01/18.
 */

public class MenuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_menu);
    }

    public void onBtnFiveRandomImageClick(View view) {
        startActivity(FiveRandomImageActivity.class);
    }

    public void onBtnFiveImageClick(View view) {
        startActivity(FiveImageActivity.class);
    }

    public void onBtnImageListClick(View view) {
        startActivity(ImageListActivity.class);
    }

    public void onBtnSingleImageClick(View view) {
        startActivity(SingleImageActivity.class);
    }

    private void startActivity(Class clazz) {
        Intent intent = new Intent(this, clazz);
        startActivity(intent);
    }
}
