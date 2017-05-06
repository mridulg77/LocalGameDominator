package srk.mgstyles.gameofcards;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by MGStyles on 20-04-2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="Game.db";
    public static final String TABLE_NAME="game_record";
    public static final String  COL_1="ID";
    public static final String  COL_2="GAME_NAME";
    public static final String  COL_3="NUMBER_PLAYERS";
    public static final String  COL_4="YOUR_POSITION";
    public static final String  COL_5="WINNER";
    public static final String  COL_6="DATE";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
     db.execSQL("create table "+ TABLE_NAME +"(ID INTEGER PRIMARY KEY AUTOINCREMENT,GAME_NAME TEXT,NUMBER_PLAYERS INTEGER,YOUR_POSITION INTEGER,WINNER TEXT,DATE TEXT )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
     db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String game_name,String nplayers,String pos,String win,String date )
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues cv=new ContentValues();
        cv.put(COL_2,game_name);
        cv.put(COL_3,nplayers);
        cv.put(COL_4,pos);
        cv.put(COL_5,win);
        cv.put(COL_6,date);
      long result=  db.insert(TABLE_NAME,null,cv);
        if(result==-1)
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public Cursor getAllData()
    { SQLiteDatabase db= this.getWritableDatabase();
        Cursor cs=db.rawQuery("select * from "+TABLE_NAME,null);
        return cs;
    }
}
