package com.sopt_semi5_remind.database;

import android.provider.BaseColumns;

/**
 * Created by parkkyounghyun
 */
public final class DataBases {

    public static final class CreateDB implements BaseColumns {
        public static final String NAME = "name";
        public static final String PHONENUM = "phoneNum";
        public static final String ICON = "icon";
        public static final String _TABLENAME = "memberinfo";
        // id name number time image
        public static final String _CREATE =
                "create table "+_TABLENAME+"("
                        +_ID+" integer primary key autoincrement, "
                        +NAME+" varchar(25) not null , "
                        +PHONENUM+" varchar(25) not null , "
                        +ICON+" int not null )";

    }

}
