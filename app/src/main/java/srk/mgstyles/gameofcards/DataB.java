package srk.mgstyles.gameofcards;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * Created by MGStyles on 21-04-2017.
 */

public class DataB extends AppCompatActivity {
    DatabaseHelper db;
    EditText ed1,ed2,ed3,ed4,ed5;
    Button add,show;


    @Override

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_b2);
        db=new DatabaseHelper(this);
         ed1=(EditText)findViewById(R.id.editText8);
        ed2=(EditText)findViewById(R.id.editText10);
        ed3=(EditText)findViewById(R.id.editText11);
        ed4=(EditText)findViewById(R.id.editText12);
        ed5=(EditText)findViewById(R.id.editText13);
            add=(Button)findViewById(R.id.button4);

        show=(Button)findViewById(R.id.button5);
      addData();
        viewALL();
    }
    public void addData()
    {
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean inserted=  db.insertData(ed1.getText().toString(),
                          ed2.getText().toString(),
                          ed3.getText().toString(),
                          ed4.getText().toString(),
                          ed5.getText().toString());
                if(inserted==true)
                    Toast.makeText(DataB.this,"Data Inserted",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(DataB.this,"Data not Inserted",Toast.LENGTH_LONG).show();
            }
        });
    }
    public void viewALL()
    {
        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              Cursor res = db.getAllData();
                if(res.getCount()==0)
                {
                    showMessage("Error","No data Found");
                    return ;

                }
                StringBuffer bf=new StringBuffer();
                while(res.moveToNext())
                {
                    bf.append("Match NO:"+res.getString(0)+"\n");
                    bf.append("Game Name:"+res.getString(1)+"\n");
                    bf.append("Number Of Players:"+res.getString(2)+"\n");
                    bf.append("Your Position:"+res.getString(3)+"\n");
                    bf.append("Winner:"+res.getString(4)+"\n");
                    bf.append("Date:"+res.getString(5)+"\n\n");
                }
                showMessage("Games Info",bf.toString());
            }
        });
    }
    public void  showMessage(String t,String m)
    {
        AlertDialog.Builder b=new AlertDialog.Builder(this);
        b.setCancelable(true);
        b.setTitle(t);
        b.setMessage(m);
        b.show();

    }
}
