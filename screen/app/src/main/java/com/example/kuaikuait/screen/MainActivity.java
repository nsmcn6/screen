package com.example.kuaikuait.screen;import android.app.Activity;import android.content.Intent;import android.database.Cursor;import android.database.sqlite.SQLiteDatabase;import android.graphics.PixelFormat;import android.os.Bundle;import android.util.Log;import android.view.Gravity;import android.view.View;import android.view.WindowManager;import android.widget.AdapterView;import android.widget.Button;import android.widget.ListView;import android.widget.TextView;public class MainActivity extends Activity implements View.OnClickListener{    private Button btnToAdd,btnStarFloat,btnStopFloat;    private ListView lv;    private Intent i,iFloat;    private MyAdapter adapter;    private NotesDB notesDB;    private SQLiteDatabase dbReader;    private Cursor cursor;    @Override    protected void onCreate(Bundle savedInstanceState) {        System.out.println("主界面的onCreat方法");        super.onCreate(savedInstanceState);        setContentView(R.layout.activity_main);        initView();    }    public void initView() {        notesDB = new NotesDB(this);        dbReader = notesDB.getReadableDatabase();        btnToAdd= (Button) findViewById(R.id.btn_toadd);        btnStarFloat= (Button) findViewById(R.id.btn_beginfloat);        btnStopFloat= (Button) findViewById(R.id.btn_stopfloat);        lv= (ListView) findViewById(R.id.list);        btnToAdd.setOnClickListener(this);        btnStarFloat.setOnClickListener(this);        btnStopFloat.setOnClickListener(this);        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {//每一个项目的点击事件            @Override            public void onItemClick(AdapterView<?> parent, View view,                                    int position, long id) {                cursor.moveToPosition(position);                Intent i = new Intent(MainActivity.this, SelectAct.class);                i.putExtra(NotesDB.ID,                        cursor.getInt(cursor.getColumnIndex(NotesDB.ID)));                i.putExtra(NotesDB.CONTENT, cursor.getString(cursor                        .getColumnIndex(NotesDB.CONTENT)));                i.putExtra(NotesDB.TIME,                        cursor.getString(cursor.getColumnIndex(NotesDB.TIME)));                i.putExtra(NotesDB.PATH,                        cursor.getString(cursor.getColumnIndex(NotesDB.PATH)));                i.putExtra(NotesDB.VIDEO,                        cursor.getString(cursor.getColumnIndex(NotesDB.VIDEO)));                startActivity(i);            }        });    }    @Override    public void onClick(View v) {        i=new Intent(this,AddContent.class);        iFloat=new Intent(this,FloatViewService.class);        switch (v.getId()){            case R.id.btn_toadd:                i.putExtra("flag","1");                startActivity(i);                break;             case R.id.btn_beginfloat:                 System.out.println("点击了开始按钮");                 startService(iFloat);                break;             case R.id.btn_stopfloat:                 stopService(iFloat);                break;        }    }    public void selectDB() {        cursor = dbReader.query(NotesDB.TABLE_NAME, null, null, null, null,                null, null);        adapter = new MyAdapter(this, cursor);        lv.setAdapter(adapter);    }    protected void onResume() {        super.onResume();        selectDB();    }}