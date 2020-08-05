package tdc.edu.vn.myapplication;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import static tdc.edu.vn.myapplication.MainActivity.arrayList1;
import static tdc.edu.vn.myapplication.MainActivity.imageView1;
import static tdc.edu.vn.myapplication.MainActivity.imgbtnPlay;
import static tdc.edu.vn.myapplication.MainActivity.mediaPlayer1;
import static tdc.edu.vn.myapplication.MainActivity.position1;
import static tdc.edu.vn.myapplication.MainActivity.seekBar;
import static tdc.edu.vn.myapplication.MainActivity.tenbaihat;
import static tdc.edu.vn.myapplication.MainActivity.tgcuoi;
import static tdc.edu.vn.myapplication.MainActivity.tgdau;

public class CustomAdapterBaiHat<position> extends ArrayAdapter {
    static MediaPlayer mediaPlayer;

    Context context;
    int resource;
    ArrayList<Song> data;
    int position1;

//    ImageView imageView1;
//    TextView tenbaihat;



    public CustomAdapterBaiHat(@NonNull Context context, int resource, @NonNull ArrayList<Song> data) {
        super(context, resource);
        this.context = context;
        this.resource = resource;
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    private void setTime(){
        SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
        tgcuoi.setText(dinhDangGio.format(mediaPlayer.getDuration()) + "");

        //lay tong thoi gian 1 bai hat
        seekBar.setMax(mediaPlayer.getDuration());
    }
    private void updateTime(){
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat dinhDangGio = new SimpleDateFormat("mm:ss");
                try{
                    tgdau.setText(dinhDangGio.format(mediaPlayer.getCurrentPosition()) + "");
                    seekBar.setProgress(mediaPlayer.getCurrentPosition());
                }catch (Exception ex){

                }





                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position1++;
                        if (position1 > data.size() - 1){
                            position1 = 0;
                        }
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        if (mediaPlayer1.isPlaying()){
                            mediaPlayer1.stop();
                        }

                        //khoi tao lai bai hat
                        mediaPlayer = MediaPlayer.create(context, data.get(position1).getFile());
                        tenbaihat.setText(data.get(position1).getTenbaihat());
                        mediaPlayer.start();
                        imgbtnPlay.setImageResource(R.drawable.ic_pause);
                        setTime();
                        updateTime();
                    }
                });
                handler.postDelayed(this,500);
            }
        }, 1000);
    }
    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(resource, null);
        ImageView imgHinh = view.findViewById(R.id.imageViewBH);
        TextView tvTenBH = view.findViewById(R.id.tvTenBH);



        final Song song = data.get(position);
        imgHinh.setImageResource(song.getHinh());
        tvTenBH.setText(song.getTenbaihat());

        tvTenBH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer1.isPlaying()){
                    mediaPlayer1.stop();
                }

                try {
                    if (mediaPlayer.isPlaying()) {
                        mediaPlayer.stop();
                    }

                    Log.i("TEST", "tat cai cu ");
                }
                catch (Exception e)
                {

                    Log.i("TEST", "AAAAAAAAAAA loi");

                }

                mediaPlayer = MediaPlayer.create(context, data.get(position).getFile());
                mediaPlayer.start();
                tenbaihat.setText(data.get(position).getTenbaihat());
                imageView1.setImageResource(data.get(position).getHinh());

//                seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
//                    @Override
//                    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
//
//                    }
//
//                    @Override
//                    public void onStartTrackingTouch(SeekBar seekBar) {
//
//                    }
//
//                    @Override
//                    public void onStopTrackingTouch(SeekBar seekBar) {
//                        mediaPlayer.seekTo(seekBar.getProgress());
//                    }
//                });
                position1 = position;
                setTime();
                updateTime();



            }
        });
        return view;
    }
}
