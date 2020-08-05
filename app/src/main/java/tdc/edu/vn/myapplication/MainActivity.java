package tdc.edu.vn.myapplication;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Handler;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static tdc.edu.vn.myapplication.CustomAdapterBaiHat.mediaPlayer;

public class MainActivity extends AppCompatActivity {
    //khai bao bien
    ListView lvdanhsachnhac;
    static ImageView imageView1;
    static ImageButton imgbtnPlay, imgbtnStop, imgbtnPrev, imgNext;
    static TextView tenbaihat, tgdau, tgcuoi;
    static SeekBar seekBar;
    static  ArrayList<Song> arrayList1;
    static MediaPlayer mediaPlayer1;
    static int position1 = 0;
    SeekBar seekBarVolumn;
    ArrayAdapter adapterNhac;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setControl();
        khoitao();
        setEvent();
    }
    private void setControl() {
        imageView1 = (ImageView) findViewById(R.id.img_baihat);
        imgbtnPlay = (ImageButton) findViewById(R.id.imgbtn_play);
        imgbtnStop = (ImageButton) findViewById(R.id.imgbtn_stop);
        imgbtnPrev = (ImageButton) findViewById(R.id.imgbtn_prev);
        imgNext = (ImageButton) findViewById(R.id.imgbtn_next);

        tenbaihat = (TextView) findViewById(R.id.tv_tenbaihat);
        tgdau = (TextView) findViewById(R.id.tv_dau);
        tgcuoi = (TextView) findViewById(R.id.tv_cuoi);

        seekBar = (SeekBar) findViewById(R.id.seekBar);
        seekBarVolumn = (SeekBar) findViewById(R.id.seekBar_volumn);
        lvdanhsachnhac = findViewById(R.id.danhsachnhac);
    }

    private void khoitao() {
        arrayList1 = new ArrayList<>();
        arrayList1.add(new Song("Anh chẳng sao mà", R.drawable.khangviet, R.raw.b1));
        arrayList1.add(new Song("Anh nhớ nhé", R.drawable.anhnhonhe, R.raw.b2));
        arrayList1.add(new Song("Đời là thế thôi", R.drawable.phule, R.raw.b3));
        arrayList1.add(new Song("Anh có tài mà", R.drawable.anhcotaima, R.raw.b4));
        arrayList1.add(new Song("Ta đi tìm em", R.drawable.taditimem, R.raw.b5));
    }

    private void play() {
        mediaPlayer1 = MediaPlayer.create(MainActivity.this, arrayList1.get(position1).getFile());
        tenbaihat.setText(arrayList1.get(position1).getTenbaihat());
        imageView1.setImageResource(arrayList1.get(position1).getHinh());

    }

    private void setEvent() {
        //khoi tao bai hat
        play();
//        danh sach
        adapterNhac = new CustomAdapterBaiHat(this,R.layout.listview_item_baihat,arrayList1);
        lvdanhsachnhac.setAdapter(adapterNhac);
        imgbtnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.pause();
                        imgbtnPlay.setImageResource(R.drawable.ic_play);
                    }
                }catch (Exception ex){
                    Log.i("err", "err");
                }


                if (mediaPlayer1.isPlaying()) {
                    mediaPlayer1.pause();
                    imgbtnPlay.setImageResource(R.drawable.ic_play);
                } else {
                    mediaPlayer1.start();
                    imgbtnPlay.setImageResource(R.drawable.ic_pause);
                }
                setTime();
                updateTime();
            }
        });

        imgbtnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    mediaPlayer1.stop();
                    mediaPlayer1.release();
                }catch (Exception ex){
                    Log.i("thua", "onClick: thua");
                }

//                play2
                try {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                }catch (Exception ex){
                    Log.i("test", "Test 1");
                }


                imgbtnPlay.setImageResource(R.drawable.ic_play);
                //khoi tao lai bai hat
                play();
                setTime();
                updateTime();
            }
        });

        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position1++;
                if (position1 > arrayList1.size() -1){
                    position1 = 0;
                }
                if (mediaPlayer1.isPlaying()){
                    mediaPlayer1.stop();
                }
                //khoi tao lai bai hat
                play();
                mediaPlayer1.start();
                imgbtnPlay.setImageResource(R.drawable.ic_pause);
                setTime();
                updateTime();
            }
        });

        imgbtnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position1--;
                if (position1 < 0){
                    position1 = arrayList1.size() -1;
                }
                if (mediaPlayer1.isPlaying()){
                    mediaPlayer1.stop();
                }
                //khoi tao lai bai hat
                play();
                mediaPlayer1.start();
                imgbtnPlay.setImageResource(R.drawable.ic_pause);
                setTime();
                updateTime();
            }
        });

        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer1.seekTo(seekBar.getProgress());
            }
        });

        Animation animation = AnimationUtils.loadAnimation(this,R.anim.quay);
        imageView1.startAnimation(animation);

        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolumn = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBarVolumn.setMax(maxVolumn);

        seekBarVolumn.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBarVolumn.getProgress(), 0);
            }
        });


    }

    private void setTime(){
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        tgcuoi.setText(dinhDangGio.format(mediaPlayer1.getDuration()) + "");

        //lay tong thoi gian 1 bai hat
        seekBar.setMax(mediaPlayer1.getDuration());
    }

    private void updateTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                tgdau.setText(dinhDangGio.format(mediaPlayer1.getCurrentPosition()) + "");


                seekBar.setProgress(mediaPlayer1.getCurrentPosition());

                mediaPlayer1.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position1++;
                        if (position1 > arrayList1.size() - 1){
                            position1 = 0;
                        }
                        if (mediaPlayer1.isPlaying()){
                            mediaPlayer1.stop();
                        }

                        //khoi tao lai bai hat
                        mediaPlayer1 = MediaPlayer.create(MainActivity.this, arrayList1.get(position1).getFile());
                        tenbaihat.setText(arrayList1.get(position1).getTenbaihat());
                        mediaPlayer1.start();
                        imgbtnPlay.setImageResource(R.drawable.ic_pause);
                        setTime();
                        updateTime();
                    }
                });
                handler.postDelayed(this,500);
            }
        }, 100);
    }

    private void volumn(){
        final AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int maxVolumn = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
        seekBarVolumn.setMax(maxVolumn);

        seekBarVolumn.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, seekBarVolumn.getProgress(), 0);
            }
        });


    }

}
