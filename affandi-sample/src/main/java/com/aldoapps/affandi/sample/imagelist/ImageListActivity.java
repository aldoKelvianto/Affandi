package com.aldoapps.affandi.sample.imagelist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.aldoapps.affandi.sample.R;
import com.aldoapps.affandi.sample.SampleApplication;
import com.aldoapps.affandi.sample.repository.ImagesRepository;

import java.util.Arrays;

/**
 * Created by aldo on 02/11/17.
 */

public class ImageListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_image_list);

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        int gridCount = 3;
        recyclerView.setLayoutManager(new GridLayoutManager(this, gridCount));
        ImageListAdapter adapter = new ImageListAdapter(Arrays.asList(ImagesRepository.imageUrls));
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SampleApplication.watchReference(this);
    }
}
