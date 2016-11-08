package com.sopt_semi5_remind.main;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.sopt_semi5_remind.R;
import com.sopt_semi5_remind.application.ApplicationController;
import com.sopt_semi5_remind.database.DbOpenHelper;
import com.sopt_semi5_remind.register.RegisterActivity;

import java.util.ArrayList;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.filterInput)
    EditText filterInput;
    @BindView(R.id.addBtn)
    Button addBtn;


    DbOpenHelper mDbOpenHelper;
    LinearLayoutManager mLayoutManager;
    Adapter adapter;
    ArrayList<ItemData> itemDatas = null;

    //Back 키 두번 클릭 여부 확인
    private final long FINSH_INTERVAL_TIME = 2000;
    private long backPressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        mDbOpenHelper = ApplicationController.getInstance().mDbOpenHelper;

        itemDatas = new ArrayList<ItemData>();
        // itemDatas 들어갈 자료를 추가

        itemDatas =  mDbOpenHelper.DbMainSelect();

        recyclerView.setHasFixedSize(true);

        // layoutManager 설정
        mLayoutManager = new LinearLayoutManager(this);
        mLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(mLayoutManager);

        adapter = new Adapter(itemDatas,clickEvent,longClickEvent);
        recyclerView.setAdapter(adapter);

        filterInput.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
                String text = filterInput.getText().toString().toLowerCase(Locale.getDefault());
                adapter.filter(text);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onTextChanged(CharSequence arg0, int arg1, int arg2,
                                      int arg3) {
                // TODO Auto-generated method stub
            }
        });

    }

    public View.OnClickListener clickEvent = new View.OnClickListener() {
        public void onClick(View v) {
            int itemPosition = recyclerView.getChildPosition(v);
            String phoneNum = itemDatas.get(itemPosition).phoneNum;
            Intent page = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+phoneNum));
            startActivity(page);
        }
    };


    public View.OnLongClickListener longClickEvent = new View.OnLongClickListener() {
        @Override
        public boolean onLongClick(View v) {
            final int itemPosition = recyclerView.getChildPosition(v);

            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);     // 여기서 this는 Activity의 this

            // 여기서 부터는 알림창의 속성 설정
            builder.setMessage("삭제하시겠습니까?")        // 메세지 설정
                    .setCancelable(true)        // 뒤로 버튼 클릭시 취소 가능 설정
                    .setPositiveButton("확인", new DialogInterface.OnClickListener(){
                        // 확인 버튼 클릭시 설정
                        public void onClick(DialogInterface dialog, int whichButton){

                            mDbOpenHelper.DbDelete(String.valueOf(itemDatas.get(itemPosition).id));

                            itemDatas.clear();
                            itemDatas =  mDbOpenHelper.DbMainSelect();
                            adapter.setAdaper(itemDatas);

                            Toast.makeText(getApplicationContext(),"삭제 완료",Toast.LENGTH_SHORT).show();
                        }
                    })
                    .setNegativeButton("취소", new DialogInterface.OnClickListener(){
                        // 취소 버튼 클릭시 설정
                        public void onClick(DialogInterface dialog, int whichButton){
                            dialog.cancel();
                        }
                    });

            AlertDialog dialog = builder.create();    // 알림창 객체 생성
            dialog.show();    // 알림창 띄우기

            //true로 바꾸어주어야 함! false로하면 click 이벤트와 같이 실행됨
            return true;
        }
    };


    @OnClick(R.id.addBtn)
    public void moveRegisterPage(){
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
        startActivity(intent);
    }


    @Override
    protected void onRestart() {
        super.onRestart();

        itemDatas.clear();
        itemDatas =  mDbOpenHelper.DbMainSelect();
        adapter.setAdaper(itemDatas);
    }


    @Override
    public void onBackPressed() {
        long tempTime        = System.currentTimeMillis();
        long intervalTime    = tempTime - backPressedTime;

        /**
         * Back키 두번 연속 클릭 시 앱 종료
         */

        if ( 0 <= intervalTime && FINSH_INTERVAL_TIME >= intervalTime ) {
            super.onBackPressed();
        }
        else {
            backPressedTime = tempTime;
            Toast.makeText(getApplicationContext(),"뒤로 가기 키을 한번 더 누르시면 종료됩니다.",Toast.LENGTH_SHORT).show();
        }
    }
}
