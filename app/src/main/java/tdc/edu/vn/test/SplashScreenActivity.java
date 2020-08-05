package tdc.edu.vn.test;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class SplashScreenActivity extends AppCompatActivity {
    boolean run = true;
    ImageView img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setControl();
        setEvent();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashScreenActivity.this, DoHoaHoaCanh.class);
                startActivity(intent);
            }
        }, 3000);

    }

    private void setControl() {
         img = findViewById(R.id.imageView);
    }

    private void setEvent() {

        final AnimationDrawable runingcat = (AnimationDrawable) img.getDrawable();
        runingcat.start();

    }
}
