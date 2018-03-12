package com.example.treyban;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.treyban.myapplication.DB_like;
import com.example.treyban.myapplication.DatabaseHelper;
import com.example.treyban.myapplication.MainActivity;
import com.example.treyban.myapplication.MyAdapter;
import com.example.treyban.myapplication.PlayerService;
import com.example.treyban.myapplication.R;
import com.example.treyban.myapplication.RecyclerClickListener;

import java.io.IOException;
import java.util.Objects;

import static android.view.View.VISIBLE;
import static com.example.treyban.myapplication.MainActivity.APP_PREFERENCES;
import static com.example.treyban.myapplication.MainActivity.ad;
import static com.example.treyban.myapplication.MainActivity.mediaController;
import static com.example.treyban.myapplication.MainActivity.name_link;
import static com.example.treyban.myapplication.MainActivity.name_nome;
import static com.example.treyban.myapplication.MainActivity.name_radio;
import static com.example.treyban.myapplication.MainActivity.name_stream;
import static com.example.treyban.myapplication.MainActivity.searchAct;
import static com.example.treyban.myapplication.PlayerService.name;
import static java.lang.System.arraycopy;

public class searchActivity extends AppCompatActivity implements  View.OnClickListener,  AdapterView.OnItemSelectedListener{
    public Spinner spinner1,spinner2;
    public static int [] ganr_int={0,13,18,35,47,58,70,80,96,101,106,111,115,120,125,176,191,194,198};
    public static int [][] zanat_int={{},{2,27,18,26,25,22,20,28,31,29,24,23},
            {27,20,26,2,24,43},
            {28,41,24,38,2,46,27,20,26},
            {20,2,85,82,27,38,43,80,41},
            {0,22,73,64,90,57,30,82,46,71,27,96},
            {119,118,117,116,115},
            {59,65,61,58,3},
            {60,62,59,61,3},
            {68,61,3,102,104,105,75,63,60,103,66,4,59,64,58},
            {102,68,103,59,195,196},
            {32,18,122,121,50,120,124,30,29,123}};

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @SuppressLint("StaticFieldLeak")
    public static TextView textView;
    public static String link2;
    public static String DATA_STREAM = "http://ic7.101.ru:8000/a219?userid=0&setst=ve7npodn8gkcncmdjk54ls7ht4&city=347";
    //Переменная для работы с БД
    public static String name_kanal;

    public Switch aSwitch;
    public static String [] list_radioo;
    public static String [] list_potoks;
    public static String [] list_link;
    public static String [] list_radioo2;
    public static int i2=0;
    public static int i3=1;
    public SearchView search;
    public TextView textSwitch;
    public static ArrayAdapter<String> adapter;
    @SuppressLint("StaticFieldLeak")
    public static RelativeLayout linearLayout;
    @SuppressLint("StaticFieldLeak")
    public static LinearLayout linearLayout2;
    @SuppressLint("StaticFieldLeak")
    public static ImageButton ima, ima1,  ima2;
    public SharedPreferences maPrefs;
    public android.support.v7.widget.Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Resources res = getResources();

        maPrefs = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE);
        boolean APP_THEME = maPrefs.getBoolean("APP_THEME",false);

        if(APP_THEME){
            setTheme(R.style.SearchTheme);
        } else {
            if (android.os.Build.VERSION.SDK_INT <= 22) {
                setTheme(R.style.SearchTheme_light_l);
            } else {
                setTheme(R.style.SearchTheme_light);
            }
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search1);

        linearLayout2= findViewById(R.id.lin_l);
        toolbar = findViewById(R.id.toolbar);

        if(APP_THEME){
            linearLayout2.setBackgroundColor(getResources().getColor(R.color.dark_color));
            toolbar.setBackgroundColor(getResources().getColor(R.color.dark_color));
        } else {
            linearLayout2.setBackgroundColor(getResources().getColor(R.color.white_dark));
            toolbar.setBackgroundColor(getResources().getColor(R.color.white_dark));
        }

        String [] garn= res.getStringArray(R.array.Genre);
        String[] zanaytia= res.getStringArray(R.array.For_activity);
        (findViewById(R.id.textView)).setSelected(true);

        ima= findViewById(R.id.imageButton4);
        ima.setOnClickListener(this);
        ima1= findViewById(R.id.imageButton5);
        ima1.setOnClickListener(this);
        ima2= findViewById(R.id.imageButton6);
        ima2.setOnClickListener(this);
        textView= findViewById(R.id.textView);
        linearLayout= findViewById(R.id.lin);

        linearLayout2.removeView(linearLayout);
        spinner1= findViewById(R.id.spinner);
        textSwitch = findViewById(R.id.textSwitch);
        spinner1.setOnItemSelectedListener(this);
        spinner2= findViewById(R.id.spinner2);
        spinner2.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,garn);
        spinner1.setAdapter(adapter1);
        final ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item,zanaytia);
        spinner2.setAdapter(adapter2);
        mRecyclerView= findViewById(R.id.list_kanal);
        mRecyclerView.setHasFixedSize(true);

        aSwitch= findViewById(R.id.switch1);
        search=findViewById(R.id.searchView);
        searchAct=true;
        if(name_radio!=null)
            link2=name_link;

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mRecyclerView.addOnItemTouchListener(new RecyclerClickListener(this) {
            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

            }

            @Override
            public void onItemClick(RecyclerView recyclerView, View itemView,
                                    int position) {

                TextView tv = ( TextView ) ((LinearLayout)itemView).getChildAt(0);
                name_kanal= (String)tv.getText();
                for(int i=0;i<198;i++){
                    if(Objects.equals(name_kanal, list_radioo[i])){
                        DATA_STREAM=list_potoks[i];
                        link2=list_link[i];
                        break;
                    }
                }
                MainActivity.mediaController.getTransportControls().stop();
                String masiv_link[] = link2.split("/");
                String nome=masiv_link[masiv_link.length-1];
                name_nome=nome;
                name_stream=DATA_STREAM;
                name_nome=nome;
                name_radio= name;
                textView.setText(name_kanal);
                state_music();
                new MainActivity().Parse_data(nome,3);
            }
        });


        DatabaseHelper mDBHelper = new DatabaseHelper(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        SQLiteDatabase mDb = mDBHelper.getWritableDatabase();

        Cursor cursor = mDb.rawQuery("SELECT * FROM clients", null);
        cursor.moveToFirst();

        list_radioo = new String[198];
        list_potoks = new String[198];
        list_link = new String[198];
        int nom=0;
        while (!cursor.isAfterLast()) {
            list_radioo[nom]= " " +cursor.getString(1);
            list_potoks[nom]=cursor.getString(2);
            list_link[nom]=cursor.getString(3);
            nom++;
            cursor.moveToNext();
        }
        cursor.close();
        list_radioo2 = new String[nom];
        System.arraycopy(list_radioo, 0, list_radioo2, 0, nom);

        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {

            @Override
            public boolean onQueryTextSubmit(String s) {

                return false;
            }

            @SuppressLint("LongLogTag")
            @Override
            public boolean onQueryTextChange(String s) {
                spinner1.setSelection(0);
                spinner2.setSelection(0);
                aSwitch.setChecked(false);
                onClick(aSwitch);

                int col=0;
                for (int i=0; i<198; i++){
                    if((list_radioo[i].toLowerCase().replace(" ","")).contains(s.toLowerCase().replace(" ",""))){
                        Log.d("^^^^^^^^^^^^^^^^^^^^^^^^",list_radioo[i]);
                        col++;
                    }
                }
                String filter [] = new String [col];
                col=0;
                for (int i = 0; i < 198; i++) {
                    if ((list_radioo[i].toLowerCase().replace(" ", "")).contains(s.toLowerCase().replace(" ", ""))) {
                        Log.d(String.valueOf(filter.length)+"  "+String.valueOf(col)+"  &&&&&&&&&&&&&&&&&&&",list_radioo[i]);
                        filter[col] = list_radioo[i];
                        col++;
                    }
                }

                mAdapter = new MyAdapter(filter);
                mRecyclerView.setAdapter(mAdapter);

                return false;
            }
        });

        search.setOnSearchClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                aSwitch.setVisibility(View.INVISIBLE);
                textSwitch.setVisibility(View.INVISIBLE);
            }
        });

        search.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                aSwitch.setVisibility(VISIBLE);
                textSwitch.setVisibility(View.VISIBLE);
                return false;
            }
        });

        mAdapter = new MyAdapter(list_radioo);
        mRecyclerView.setAdapter(mAdapter);

        windowAnimations();
    }

    private void windowAnimations() {
        Fade slide = new Fade();
        getWindow().setEnterTransition(slide);
    }

    public void list(String s[]){
        mAdapter = new MyAdapter(s);
        mRecyclerView.setAdapter(mAdapter);
    }

    public void state_music(){
        //textView= findViewById(R.id.textView);
        Log.d("","+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");

        textView.setText(name);
        linearLayout2.removeView(linearLayout);
        if(name!=null) {
            Log.d("","+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
            linearLayout2.addView(linearLayout);
        }
        if(name_radio==null){

            ima2.setImageResource(R.drawable.play_button);
        }else {
            ima2.setImageResource(R.drawable.pause_button);
        }
    }

    @SuppressLint("MissingSuperCall")
    public void onResume() {
        super.onResume();
        state_music();
    }

    public void onClick(View view){
        if(view.getId()==ima.getId()){
            new PlayerService().setData_Media(null,null,null,null,null);
            mediaController.getTransportControls().stop();
            state_music();
            name_radio=null;
            name_nome=null;
            linearLayout2.removeView(linearLayout);

        }else {
            if (view.getId() == ima1.getId()) {
                boolean pass=true;
                DB_like db_like0=new DB_like(this);
                SQLiteDatabase database0 = db_like0.getWritableDatabase();
                Cursor cursor = database0.query(DB_like.TABLE_CONTACTS, null, null, null, null, null, null);
                int idIndex = cursor.getColumnIndex("name");
                if (cursor.moveToFirst()) {
                    do {
                        if(name_kanal.equals(cursor.getString(idIndex))){
                            pass=false;
                        }
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
                database0.close();

                if(pass) {
                    Toast.makeText(this, R.string.Added, Toast.LENGTH_LONG).show();
                    DB_like db_like = new DB_like(this);
                    SQLiteDatabase database = db_like.getWritableDatabase();
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("name", name_kanal);
                    contentValues.put("potok", DATA_STREAM);
                    contentValues.put("link", link2);
                    database.insert(DB_like.TABLE_CONTACTS, null, contentValues);
                    database.close();
                    ad(name_kanal, DATA_STREAM, link2);
                }else {
                    Toast.makeText(this, R.string.Already_added, Toast.LENGTH_LONG).show();
                }

            } else if (view.getId() == ima2.getId()) {
                if (name_radio!=null) {
                    mediaController.getTransportControls().pause();
                    name_radio=null;
                } else{
                    mediaController.getTransportControls().play();
                    mediaController.getTransportControls().pause();
                    mediaController.getTransportControls().play();
                    name_radio=name;
                }
                state_music();
            } else {
                if (aSwitch.isChecked()) {
                    i3=1;
                    i2=1;
                    spinner1.setSelection(0);
                    spinner2.setSelection(0);
                    String[] mood = new String[ganr_int[15] - ganr_int[14]];
                    arraycopy(list_radioo, ganr_int[14], mood, 0, ganr_int[15] - ganr_int[14]);
                    list(mood);
                } else {

                    mAdapter = new MyAdapter(list_radioo);
                    mRecyclerView.setAdapter(mAdapter);
                }
            }
        }
        state_music();

    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        if(i3!=1|i2!=1)aSwitch.setChecked(false);
        if(adapterView.getId()==spinner1.getId()){

            if(spinner1.getSelectedItemPosition()!=0 & spinner2.getSelectedItemPosition()!=0)
                spinner2.setSelection(0);
            if(i2==0 )
                spinner2.setSelection(0);

            if(i==0){
                if(i2==0)list(list_radioo);
            }else {
                int i1=i>=15 ? i+1 : i;
                int i2=(i>=15 ? i+1 : i) - 1;
                String[] ll = new String[ganr_int[i1] - ganr_int[i2]];
                arraycopy(list_radioo, ganr_int[i2], ll, 0, ganr_int[i1] - ganr_int[i2]);
                list(ll);
            }
            i2=0;
            i3=1;
        } else if (adapterView.getId()==spinner2.getId()) {

            if(spinner1.getSelectedItemPosition()!=0 & spinner2.getSelectedItemPosition()!=0)
                spinner1.setSelection(0);
            if(i3==0)
                spinner1.setSelection(0);

            if(i==0) {
                if(i3==0)list(list_radioo);
            }else{

                String[] ll2 = new String[zanat_int[i].length];
                for (int k = 0; k <zanat_int[i].length; k++) {
                    ll2[k] = list_radioo[zanat_int[i][k]];
                }

                list(ll2);
                i2=1;
                i3=0;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

}
