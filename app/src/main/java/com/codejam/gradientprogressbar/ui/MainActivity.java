package com.codejam.gradientprogressbar.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.codejam.gradientprogressbar.R;
import com.codejam.gradientprogressbar.custom.GradientProgressBar;

import java.util.Random;

/**
 * Project GradientProgressBar.
 * <p>
 * Created by Rhony Abdullah Siagian on 8/18/17.
 */
public class MainActivity extends AppCompatActivity {

    private Random random = new Random(System.currentTimeMillis());
    private GradientProgressBar progressView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        progressView = (GradientProgressBar) findViewById(R.id.spring_progress_view);
        findViewById(R.id.click).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int randomValue = random.nextInt(100);
                progressView.setCurrentCount(randomValue);
                Toast.makeText(MainActivity.this, "Progress value = "+randomValue, Toast.LENGTH_LONG).show();
            }
        });
    }
}
