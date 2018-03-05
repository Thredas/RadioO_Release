package com.example.treyban.myapplication;


import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.constraint.ConstraintLayout;
import android.support.v4.media.session.MediaControllerCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.transition.ChangeBounds;
import android.transition.Fade;
import android.transition.Transition;
import android.util.Log;
import android.util.Pair;
import android.view.GestureDetector;
import android.view.HapticFeedbackConstants;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.ads.MobileAds;
import com.squareup.picasso.Picasso;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Objects;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

import static android.view.View.GONE;
import static android.view.View.INVISIBLE;
import static android.view.View.VISIBLE;
import static com.example.treyban.searchActivity.name_kanal;



public class MainActivity extends AppCompatActivity implements View.OnClickListener, View.OnLongClickListener {

    @SuppressLint("StaticFieldLeak")
    public static Context context;
    public static CardView Search, cardview;
    public TextView textview10;
    @SuppressLint("StaticFieldLeak")
    public static ProgressBar progressBar;
    public static HttpURLConnection urlConnection = null;
    public static BufferedReader reader = null;
    public static String resultJson = "";
    @SuppressLint("StaticFieldLeak")
    public static TextView textView_parse1, textView_parse2;
    public static String name_trake=null, test_trake=null;
    public static String name_ispoln=null;
    public static String name_radio=null, test_radio=null;
    public static String name_stream=null;
    public static String name_link=null;
    public static String name_nome=null;
    public static String name_timeLeft="0";
    public static String image="https://i.imgur.com/Og7pwiX.jpg", nome;
   // public static boolean pim_parse=true;
    public static String theme = "";
    @SuppressLint("StaticFieldLeak")
    public static ImageButton imgbtn, play_button;
    public static TextView [] id;
    public static String [] name , potok , link ;
    public static int  pim=0, pim2=0, iLeft=0;
    public static int chose = 0;
    @SuppressLint("StaticFieldLeak")
    public static TextView textView0,radio_now_play;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout linearLayout;
    @SuppressLint("StaticFieldLeak")
    public static ConstraintLayout scrollView;
    public int cx, cy;
    public static Thread thread,thread2;
    public SharedPreferences sPref;
    public static final String APP_PREFERENCES = "Settings";

    public Button button;
    public static String pars_uri_1="http://101.ru/api/channel/getTrackOnAir/",
            pars_uri_2="/channel/?dataFormat=json";
    public static MediaControllerCompat mediaController;
    PlayerService.PlayerServiceBinder playerServiceBinder;
    public static boolean searchAct=false;
    Bitmap bit=null;

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void Update_fon(){

        if(PlayerService.name!=null) {
            Parse_data(name_nome,1);
        }else{
            image="https://i.imgur.com/Og7pwiX.jpg";
            name_trake="НАЗВАНИЕ ТРЕКА";
            name_ispoln="Исполнитель";
            name_radio="Ничего не играет";
        }
        stat();
    }
    public static void ad(String namee, String potokk, String linkk) {

        name[pim] = namee;
        potok[pim] = potokk;
        link[pim] = linkk;
        id[pim].setText(namee);
        linearLayout.addView(id[pim]);
        pim++;

    }





    public void Data() {
        pim=0;
        pim2=0;
        try {

            DB_like db_like = new DB_like(this);
            SQLiteDatabase database = db_like.getWritableDatabase();
            Cursor cursor = database.query(DB_like.TABLE_CONTACTS, null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    pim++;
                } while (cursor.moveToNext());
            } else
                Log.d("mLog", "0 rows");
            name = new String[50];
            potok = new String[50];
            link = new String[50];


            cursor.close();
            Cursor cursor2 = database.query(DB_like.TABLE_CONTACTS, null, null, null, null, null, null);
            if (cursor2.moveToFirst()) {
                int idIndex = cursor2.getColumnIndex("name");
                int nameIndex = cursor2.getColumnIndex("potok");
                int emailIndex = cursor2.getColumnIndex("link");
                do {
                    name[pim2] = cursor2.getString(idIndex);
                    potok[pim2] = cursor2.getString(nameIndex);
                    link[pim2] = cursor2.getString(emailIndex);
                    pim2++;
                } while (cursor2.moveToNext());
            } else
                cursor2.close();
            database.close();

        } catch (Exception ignored) {

        }

    }

    public static void Update() {
        linearLayout.removeAllViews();
        id = null;
        id = new TextView[50];

        for (int i = 0; i < 50; i++) {
            id[i] = new TextView(textView0.getContext());
            id[i].setWidth(textView0.getWidth());
            id[i].setHeight(textView0.getHeight());
            id[i].setLayoutParams(textView0.getLayoutParams());
            id[i].setTextColor(textView0.getTextColors());
            id[i].setGravity(textView0.getGravity());
            id[i].setTextSize(20);
            id[i].setBackground(textView0.getBackground());
            id[i].setOnClickListener((View.OnClickListener) textView0.getContext());
            id[i].setOnLongClickListener((View.OnLongClickListener) textView0.getContext());
            id[i].setId(i);


        }
        for (int i = 0; i < pim; i++) {
            id[i].setText(name[i]);
            linearLayout.addView(id[i]);
        }
    }



    public Bitmap loadBitmap(String url)
    {
        Bitmap bm = null;
        InputStream is = null;
        BufferedInputStream bis = null;
        try
        {
            URLConnection conn = new URL(url).openConnection();
            conn.connect();
            is = conn.getInputStream();
            bis = new BufferedInputStream(is, 1000);
            bm = BitmapFactory.decodeStream(bis);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        finally {
            if (bis != null)
            {
                try
                {
                    bis.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
            if (is != null)
            {
                try
                {
                    is.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace();
                }
            }
        }
        return bm;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("LongLogTag")
    public void Parse_data(String nomee, final int whence){

        nome=nomee;
        progressBar.setVisibility(VISIBLE);
        thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String sum_uri=pars_uri_1+nome+pars_uri_2;
                    URL url = new URL(sum_uri);

                    urlConnection = (HttpURLConnection) url.openConnection();
                    urlConnection.setRequestMethod("GET");
                    urlConnection.connect();

                    InputStream inputStream = urlConnection.getInputStream();
                    StringBuilder buffer = new StringBuilder();

                    reader = new BufferedReader(new InputStreamReader(inputStream));

                    String line;
                    while ((line = reader.readLine()) != null) {
                        buffer.append(line);
                    }
                    resultJson = buffer.toString();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Log.d(">>>>>>>>>>>>>>>",resultJson);
                JSONParser parser = new JSONParser();
                Object obj = null;
                try {
                    obj = parser.parse(resultJson);
                } catch (android.net.ParseException | ParseException e) {
                    e.printStackTrace();
                }
                JSONObject jsonObj = (JSONObject) obj;
                try {
                    assert jsonObj != null;
                    JSONObject jsonObj2 = (JSONObject) jsonObj.get("result");
                    JSONObject jsonObj3 = (JSONObject) jsonObj2.get("short");
                    name_trake=( jsonObj3.get("titleTrack")).toString();
                    name_ispoln=( jsonObj3.get("titleExecutor")).toString();
                    Log.d("Имя трека", name_trake);
                    Log.d("Исполнитель", name_ispoln);
                    image="https://i.imgur.com/Og7pwiX.jpg";
                    if(!Objects.equals((jsonObj3.get("cover")).toString(), "false")){
                        JSONObject jsonObj4 = (JSONObject) jsonObj3.get("cover");
                        image=jsonObj4.get("cover400").toString();
                        Log.d("Картинки>>>>>>", image);
                    }

                    JSONObject jsonObjTime = (JSONObject) jsonObj2.get("stat");
                    //JSONObject jsonObjTime2 = (JSONObject) jsonObjTime.get("lastTime");
                    name_timeLeft=( jsonObjTime.get("lastTime")).toString();

                }catch (Exception x){
                    image="https://i.imgur.com/Og7pwiX.jpg";
                    name_trake="Музыка";
                    name_ispoln="Отсутствует";
                    name_timeLeft="0";
                }


                //  pim_parse=false;
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        bit=loadBitmap(image);
                    }
                });
                thread.start();
                while (thread.isAlive());

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        progressBar.setVisibility(INVISIBLE);

                        if(whence==1){
                            new PlayerService().setData_Media(name_kanal, name_trake, name_ispoln, name_stream,bit);
                            mediaController.getTransportControls().prepare();
                            stat();
                        }else {

                            iLeft=0;

                            if (PlayerService.name != null) {
                                mediaController.getTransportControls().pause();
                                new PlayerService().setData_Media(name_kanal, name_trake, name_ispoln, name_stream, bit);
                                mediaController.getTransportControls().play();
                            } else {
                                new PlayerService().setData_Media(name_kanal, name_trake, name_ispoln, name_stream, bit);
                                mediaController.getTransportControls().play();
                            }

                            if(whence!=3)stat();
                        }
                    }
                });




            }
        });

        thread.start();



    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void stat(){
        if(!Objects.equals(name_trake,test_trake) | !Objects.equals(name_radio,test_radio)) {
            test_trake=name_trake;
            test_radio=name_radio;
            cardview.post(new Runnable() {
                @Override
                public void run() {
                    int tx = (cardview.getLeft() + cardview.getRight()) / 3;
                    double ty = (cardview.getTop() + cardview.getBottom()) / 2.5;

                    float finalRadius = (float) Math.hypot(cardview.getWidth(), cardview.getHeight());

                    Animator anim = ViewAnimationUtils.createCircularReveal(cardview, tx, (int) ty, 0, finalRadius);
                    anim.setDuration(650);
                    cardview.setVisibility(VISIBLE);
                    anim.start();
                }
            });

            textView_parse1.setText(name_trake);
            textView_parse2.setText(name_ispoln);
            radio_now_play.setText(PlayerService.name == null ? "Ничего не играет" : PlayerService.name);
            Uri uri = Uri.parse(image);
            Picasso.with(context) //передаем контекст приложения
                    .load(uri)
                    .resize(750, 750)
                    .into(imgbtn);
            //progressBar.setScrollbarFadingEnabled(false);
        }
    }


    public void start() {
        bindService(new Intent(this, PlayerService.class), new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                playerServiceBinder = (PlayerService.PlayerServiceBinder) service;
                try {
                    mediaController = new MediaControllerCompat(MainActivity.this, playerServiceBinder.getMediaSessionToken());
                    mediaController.registerCallback(new MediaControllerCompat.Callback() {
                        @Override
                        public void onPlaybackStateChanged(PlaybackStateCompat state) {
                            if (state == null){

                            }

                        }
                    });
                } catch (RemoteException e) {
                    mediaController = null;
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {
                playerServiceBinder = null;
                mediaController = null;
            }
        }, BIND_AUTO_CREATE);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(PlayerService.stateN){
            mediaController.getTransportControls().fastForward();
        }
    }

    @SuppressLint("ClickableViewAccessibility")
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        sPref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean APP_THEME = sPref.getBoolean("APP_THEME",false);

        if(APP_THEME){
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme);
        }

        setContentView(R.layout.activity_main);

        Search = findViewById(R.id.Search);
        scrollView = findViewById(R.id.scroll);
        cardview = findViewById(R.id.cardView);

        if(APP_THEME){
            Search.setCardBackgroundColor(getResources().getColor(R.color.dark_color));
            scrollView.setBackground(getResources().getDrawable(R.drawable.myrect));
            cardview.setCardBackgroundColor(getResources().getColor(R.color.Background));
        } else {
            Search.setCardBackgroundColor(getResources().getColor(R.color.white_dark));
            scrollView.setBackground(getResources().getDrawable(R.drawable.myrect_light));
            cardview.setCardBackgroundColor(getResources().getColor(R.color.white));
        }

        (findViewById(R.id.track)).setSelected(true);
        (findViewById(R.id.singer)).setSelected(true);
        (findViewById(R.id.radio_now_play)).setSelected(true);

        start();
        button = new Button(this);
        pim = 0;
        pim2 = 0;

        Data();
        radio_now_play = findViewById(R.id.radio_now_play);
        textView0 = findViewById(R.id.fari_text);
        linearLayout = findViewById(R.id.favorite_list);
        scrollView = findViewById(R.id.scroll);
        scrollView.setVisibility(INVISIBLE);
        imgbtn = findViewById(R.id.play);
        play_button = findViewById(R.id.play_button);
        play_button.setOnClickListener(this);
        textView_parse1 = findViewById(R.id.track);
        textView_parse2 = findViewById(R.id.singer);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setVisibility(INVISIBLE);
        // progressBar.setProgress(1);

        context=getApplicationContext();

        Update();

        MobileAds.initialize(this, "ca-app-pub-8421124895754019~9641352193");

        final GestureDetector gesture = new GestureDetector(MainActivity.this,
                new GestureDetector.SimpleOnGestureListener() {

                    @Override
                    public boolean onDoubleTap(MotionEvent e) {
                        Search = findViewById(R.id.Search);
                        Intent intent = new Intent("com.example.treyban.searchActivity");
                        View sharedView = Search;
                        String transitionName = getString(R.string.Search);
                        (findViewById(R.id.track)).setSelected(false);
                        (findViewById(R.id.singer)).setSelected(false);
                        (findViewById(R.id.radio_now_play)).setSelected(false);
                        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, sharedView, transitionName);
                        startActivity(intent, transitionActivityOptions.toBundle());
                        Search.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        return false;
                    }

                    @Override
                    public void onLongPress(MotionEvent e) {
                        Search = findViewById(R.id.Search);
                        textview10 = findViewById(R.id.radio_now_play);
                        Search.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
                        Intent intent = new Intent("com.example.treyban.settingActivity");
                        View sharedView = Search;
                        View sharedView2 = textview10;
                        String transitionName = getString(R.string.Search);
                        String transitionName2 = getString(R.string.Text);
                        (findViewById(R.id.track)).setSelected(false);
                        (findViewById(R.id.singer)).setSelected(false);
                        (findViewById(R.id.radio_now_play)).setSelected(false);
                        ActivityOptions transitionActivityOptions = ActivityOptions.makeSceneTransitionAnimation(MainActivity.this, Pair.create(sharedView, transitionName), Pair.create(sharedView2, transitionName2));
                        startActivity(intent, transitionActivityOptions.toBundle());
                    }

                    @Override
                    public boolean onSingleTapConfirmed (MotionEvent e){
                        cx = (int) e.getRawX();
                        cy = (int) (e.getRawY() / 1.4);
                        revealFromCoordinates();
                        return false;
                    }
                });

        //Обучение
        boolean hasVisited = sPref.getBoolean("Tutorial", false);

        if (!hasVisited) {
            new MaterialTapTargetPrompt.Builder(MainActivity.this)
                    .setTarget(Search)
                    .setPrimaryText(R.string.Tutorial)
                    .setSecondaryText(R.string.Tutorial2)
                    .setBackgroundColour(getResources().getColor(R.color.AccentTransperent))
                    .setFocalRadius(R.dimen.dp40)
                    .setFocalColour(getResources().getColor(R.color.AccentTransperent))
                    .setPromptStateChangeListener(new MaterialTapTargetPrompt.PromptStateChangeListener()
                    {
                        @Override
                        public void onPromptStateChanged(@NonNull MaterialTapTargetPrompt prompt, int state)
                        {
                            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                                SharedPreferences.Editor e = sPref.edit();
                                e.putBoolean("Tutorial", true);
                                e.apply();
                            } else if (state == MaterialTapTargetPrompt.STATE_NON_FOCAL_PRESSED) {
                                SharedPreferences.Editor e = sPref.edit();
                                e.putBoolean("Tutorial", true);
                                e.apply();
                            }
                        }
                    })
                    .show();
        }
        //

        Search.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                gesture.onTouchEvent(event);
                return true;
            }
        });
        thread2 = new Thread(new Runnable() {
            @SuppressLint("LongLogTag")
            @Override
            public void run() {
                while(true) {

                    try {
                        Log.d("llllllllllllllllllllllllllllllllllllllllllllllll",name_timeLeft);
                        iLeft=0;
                        while (iLeft < (Objects.equals(name_timeLeft, "0") ? 5 : Integer.parseInt(name_timeLeft))) {
                            iLeft++;
                            Log.d("2222222222222222222222222222222222222222222222222222222222222",name_timeLeft);
                            Thread.sleep(1000);
                        }
                    } catch (InterruptedException ignored) { }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("()()()()()()()()()()()()()()()()()()()()()()()()()",name_timeLeft);
                            Update_fon();
                        }
                    });

                }
            }
        });
        thread2.start();
        getWindow().setSharedElementEnterTransition(enterTransition());
        getWindow().setSharedElementReturnTransition(returnTransition());

    }

   /* @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onRestart() {
        super.onRestart();
        Update_fon();
    }*/

    @RequiresApi(api = Build.VERSION_CODES.N)
    @SuppressLint("MissingSuperCall")
    public void onResume() {
        super.onResume();
        (findViewById(R.id.track)).setSelected(true);
        (findViewById(R.id.singer)).setSelected(true);
        (findViewById(R.id.radio_now_play)).setSelected(true);
        Update_fon();
        test_trake="";
        test_radio="";
    }
    private Transition enterTransition() {
        ChangeBounds bounds = new ChangeBounds();
        bounds.setDuration(1500);
        return bounds;
    }

    private Transition returnTransition() {
        Fade f = new Fade();
        f.setDuration(1500);
        return f;
    }

    public void revealFromCoordinates() {
        float finalRadius = (float) Math.hypot(scrollView.getWidth(), scrollView.getHeight());

        Search.performHapticFeedback(HapticFeedbackConstants.VIRTUAL_KEY);
        Animator anim = ViewAnimationUtils.createCircularReveal(scrollView, cx, cy, 0, finalRadius);
        anim.setDuration(350);
        scrollView.setVisibility(VISIBLE);
        anim.start();
        (findViewById(R.id.track)).setSelected(false);
        (findViewById(R.id.singer)).setSelected(false);
        (findViewById(R.id.radio_now_play)).setSelected(false);
    }

    public void closeFromCoordinates(){
        float finalRadius = (float) Math.hypot(scrollView.getWidth(), scrollView.getHeight());

        Animator anim = ViewAnimationUtils.createCircularReveal(scrollView, cx, cy, finalRadius, 0);
        anim.setDuration(350);
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                scrollView.setVisibility(GONE);
                (findViewById(R.id.track)).setSelected(true);
                (findViewById(R.id.singer)).setSelected(true);
                (findViewById(R.id.radio_now_play)).setSelected(true);
            }
        });
        anim.start();
    }

    @Override
    public void onBackPressed() {
        if (scrollView.getVisibility() == VISIBLE) {
            closeFromCoordinates();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onLongClick(View view) {
        AlertDialog.Builder ad;
        ad = new AlertDialog.Builder(this);
        ad.setTitle(R.string.Dialog);  // заголовок
        // ad.setMessage(message); // сообщение

        for(int i=0;i<pim;i++){
            if(view==id[i]){
                chose=i;
            }
        }
        final String chose_s = "'"+name[chose]+"'";
        ad.setPositiveButton(R.string.Yes, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
                Toast.makeText(MainActivity.this, R.string.Deleted ,Toast.LENGTH_SHORT).show();
                DB_like db_like=new DB_like(MainActivity.this);
                SQLiteDatabase database = db_like.getWritableDatabase();
                database.delete(DB_like.TABLE_CONTACTS,"name = "+chose_s,null);
                database.close();
                db_like.close();
                Data();
                Update();
            }
        });
        ad.setNegativeButton(R.string.No, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int arg1) {
            }
        });
        ad.setCancelable(true);

        for(int i=0;i<pim;i++)
            if(view==id[i])
                ad.show();



        return true;
    }


    public void button_animation(){
        Animation animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.fade);
        animation.setStartOffset(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                play_button.setImageResource(R.color.transparent);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        play_button.startAnimation(animation);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.play_button){
            if(PlayerService.name!=null){
                if(name_radio!=null){
                    play_button.setImageResource(R.drawable.play_button);
                    mediaController.getTransportControls().pause();
                    name_radio=null;

                }else {
                    play_button.setImageResource(R.drawable.pause_button);
                    mediaController.getTransportControls().play();
                    name_radio=PlayerService.name;

                }
                button_animation();
            }

            //progressBar.setVisibility(INVISIBLE);
        }

        for (int i = 0; i < pim; i++) {
            if (view == id[i]) {

                closeFromCoordinates();
                //mediaController.getTransportControls().stop();
                name_radio=name[i];
                name_kanal=name[i];
                name_stream=potok[i];
                name_link=link[i];
                String masiv_link[]=link[i].split("/");
                final String nome=masiv_link[masiv_link.length-1];
                name_nome=nome;
                Parse_data(nome,2);
            }
        }

    }

}


