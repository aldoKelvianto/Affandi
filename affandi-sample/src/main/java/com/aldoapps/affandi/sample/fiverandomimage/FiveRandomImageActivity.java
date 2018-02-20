package com.aldoapps.affandi.sample.fiverandomimage;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.aldoapps.affandi.Affandi;
import com.aldoapps.affandi.sample.R;
import com.aldoapps.affandi.sample.databinding.ActivityFiveImageBinding;
import com.aldoapps.affandi.sample.repository.ImagesRepository;

import java.util.Random;

/**
 * Created by aldo on 02/11/17.
 */

public class FiveRandomImageActivity extends AppCompatActivity {

    private ActivityFiveImageBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_five_image);
        loadMultipleImages();
    }

    private void loadMultipleImages() {
        Affandi.with(this).paint(ImagesRepository.imageThumbUrls[getRandomInt()]).into(binding.imageView1);
        Affandi.with(this).paint(ImagesRepository.imageThumbUrls[getRandomInt()]).into(binding.imageView2);
        Affandi.with(this).paint(ImagesRepository.imageThumbUrls[getRandomInt()]).into(binding.imageView3);
        Affandi.with(this).paint(ImagesRepository.imageThumbUrls[getRandomInt()]).into(binding.imageView4);
        Affandi.with(this).paint(ImagesRepository.imageThumbUrls[getRandomInt()]).into(binding.imageView5);
    }

    private int getRandomInt() {
        Random random = new Random(SystemClock.elapsedRealtimeNanos());
        return random.nextInt(ImagesRepository.imageThumbUrls.length - 1);
    }
}
