package com.sopt_semi5_remind.register;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.sopt_semi5_remind.R;
import com.sopt_semi5_remind.application.ApplicationController;
import com.sopt_semi5_remind.database.DbOpenHelper;
import com.sopt_semi5_remind.main.ItemData;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.imageView)
    ImageView image;
    @BindView(R.id.leftBtn)
    ImageView leftBtn;
    @BindView(R.id.rightBtn)
    ImageView rightBtn;
    @BindView(R.id.inputName)
    EditText inputName;
    @BindView(R.id.inputPhoneNum)
    EditText inputPhoneNum;

    DbOpenHelper mDbOpenHelper;
    int imgCount = 900;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_acticity);

        ButterKnife.bind(this);

        mDbOpenHelper = ApplicationController.getInstance().mDbOpenHelper;

    }


    @OnClick(R.id.leftBtn)
    public void preChangeImg(){
        imgCount--;

        if(imgCount%3 ==0)
            image.setImageResource(R.drawable.man);
        else if(imgCount%3 == 1)
            image.setImageResource(R.drawable.teamwork);
        else if(imgCount%3 == 2)
            image.setImageResource(R.drawable.handshake);


    }

    @OnClick(R.id.rightBtn)
    public void nextChangeImg(){
        imgCount++;

        if(imgCount%3 ==0)
            image.setImageResource(R.drawable.man);
        else if(imgCount%3 == 1)
            image.setImageResource(R.drawable.teamwork);
        else if(imgCount%3 == 2)
            image.setImageResource(R.drawable.handshake);

    }

    @OnClick(R.id.completeBtn)
    public void regiserMember(){
        String name = inputName.getText().toString();
        String phoneNum = inputPhoneNum.getText().toString();

        ItemData data = new ItemData();
        data.name = name;
        data.phoneNum = phoneNum;

        if(imgCount%3 ==0)
            data.icon = R.drawable.man;
        else if(imgCount%3 == 1)
            data.icon = R.drawable.teamwork;
        else if(imgCount%3 == 2)
            data.icon = R.drawable.handshake;

        mDbOpenHelper.DbInsert(data);


        Toast.makeText(getApplicationContext(),"등록 완료",Toast.LENGTH_SHORT).show();
        finish();
    }

}
