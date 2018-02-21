package com.aldoapps.affandi.sample.singleimage;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.aldoapps.affandi.Affandi;
import com.aldoapps.affandi.sample.R;
import com.aldoapps.affandi.sample.SampleApplication;

public class SingleImageActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void loadImageWithAffandi(View view) {
        Toast.makeText(this, "Loading image..", Toast.LENGTH_SHORT).show();
        String imageUrl =
                "https://lh5.googleusercontent.com/-n7mdm7I7FGs/URqueT_BT-I/AAAAAAAAAbs/9MYmXlmpSAo/s1024/Bonzai%252520Rock%252520Sunset.jpg";
        final ImageView imageView = findViewById(R.id.imageView);
        Affandi.with(this).paint(imageUrl).into(imageView);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SampleApplication.watchReference(this);
    }
}
