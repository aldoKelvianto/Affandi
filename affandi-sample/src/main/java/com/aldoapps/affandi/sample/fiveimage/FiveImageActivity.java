package com.aldoapps.affandi.sample.fiveimage;

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

public class FiveImageActivity extends AppCompatActivity {

    private ActivityFiveImageBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = DataBindingUtil.setContentView(this, R.layout.activity_five_image);
        loadMultipleImages();
    }

    private void loadMultipleImages() {
        Affandi.with(this).paint(ImagesRepository.numberedImage+"1").into(binding.imageView1);
        Affandi.with(this).paint(ImagesRepository.numberedImage+"2").into(binding.imageView2);
        Affandi.with(this).paint(ImagesRepository.numberedImage+"3").into(binding.imageView3);
        Affandi.with(this).paint(ImagesRepository.numberedImage+"4").into(binding.imageView4);
        Affandi.with(this).paint(ImagesRepository.numberedImage+"5").into(binding.imageView5);
    }

}
