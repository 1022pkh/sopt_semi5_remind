package com.sopt_semi5_remind.main;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.sopt_semi5_remind.R;


/**
 * Created by kh on 2016. 10. 24..
 */
public class ViewHolder extends RecyclerView.ViewHolder {
    ImageView imageView;
    TextView nameView;
    TextView phoneNumView;

    public ViewHolder(View itemView) {
        super(itemView);

        imageView = (ImageView)itemView.findViewById(R.id.imageView1);
        nameView = (TextView)itemView.findViewById(R.id.titleTextView);
        phoneNumView =(TextView)itemView.findViewById(R.id.contentTextView);

    }
}
