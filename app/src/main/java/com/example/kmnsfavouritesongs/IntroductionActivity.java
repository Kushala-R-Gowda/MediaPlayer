package com.example.kmnsfavouritesongs;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class IntroductionActivity extends AppCompatActivity {

    private ImageView siri, nidhi, meghi, kushi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introduction);


        siri = findViewById(R.id.siri);
        nidhi = findViewById(R.id.nidhi);
        meghi = findViewById(R.id.meghi);
        kushi = findViewById(R.id.kushala);

        siri.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroductionActivity.this, SiriActivity.class);
                startActivity(intent);
            }
        });

        nidhi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroductionActivity.this, NidhiActivity.class);
                startActivity(intent);
            }
        });

        meghi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroductionActivity.this, MeghiActivity.class);
                startActivity(intent);
            }
        });

        kushi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(IntroductionActivity.this, KushiActivity.class);
                startActivity(intent);
            }
        });
    }
}
