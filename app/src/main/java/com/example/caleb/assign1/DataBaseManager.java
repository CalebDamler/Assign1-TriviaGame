package com.example.caleb.assign1;

/**
 * Created by Caleb on 2/1/2018.
 */

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class DataBaseManager extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "questionDB";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_QUESTION = "questions", ID = "id", CATEGORY = "category", QUESTION = "question", CORRECT_ANS = "correct_answer", INCCORRECT_ANS ="inccorect_answers";

    public DataBaseManager(Context context) {
        super(context,DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlCreate = "create table " + TABLE_QUESTION + "( " + ID + " integer primary key autoincrement, " + CATEGORY + " text, " + QUESTION + " text, " + CORRECT_ANS  + " text, " + INCCORRECT_ANS + " text )";
        db.execSQL(sqlCreate);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists " + TABLE_QUESTION);
        onCreate(db);
    }

    public void insert(Question question){
        SQLiteDatabase database = getWritableDatabase();

        String sqlInsert = "insert into " + TABLE_QUESTION + " values( null, '"  + question.getCategory() + "', '"+ question.getQuestion() + "', '" + question.getCorrectAns() + "', '" + question.getIncorrectAns() + "' )";
        database.execSQL(sqlInsert);
        database.close();

    }



    public Question selectByID(int id){
        String sqlSelect = "select * from " + TABLE_QUESTION + " where " + ID + " = " + id;
        SQLiteDatabase database = getWritableDatabase();
        Cursor cursor = database.rawQuery(sqlSelect, null);
        Question question1 = null;
        if (cursor.moveToFirst()){
            question1 = new Question(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));
        }
        database.close();
        return question1;
    }


}