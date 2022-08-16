package com.example.app_with_sql;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText etName;
    EditText etMail;
    Button save, load, clear, textClear;
    DBHelper dbHelper;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Window w = getWindow();
        w.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        etMail = findViewById(R.id.string_mail);
        etName = findViewById(R.id.string_name);

        save = (Button)findViewById(R.id.btnAdd);
        load = (Button)findViewById(R.id.btnRead);
        clear = (Button) findViewById(R.id.btnClear);
        textClear = (Button)findViewById(R.id.btnTextClear);
        save.setOnClickListener(this);
        load.setOnClickListener(this);
        clear.setOnClickListener(this);
        textClear.setOnClickListener(this);
        dbHelper = new DBHelper(this);

    }

    @Override
    public void onClick(View v){
        String name = etName.getText().toString();
        String email = etMail.getText().toString();
        Toast toast;

                SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        switch(v.getId()) {
            case R.id.btnAdd:

                if (!(etName.getText().toString().isEmpty()) || !(etMail.getText().toString().isEmpty())){
                    toast = Toast.makeText(getApplicationContext(),
                            "Добавлено в базу!", Toast.LENGTH_SHORT);
                    toast.show();

                    contentValues.put(DBHelper.KEY_NAME, name);
                contentValues.put(DBHelper.KEY_MAIL, email);

                database.insert(DBHelper.TABLE_CONTACTS, null, contentValues); }
                else {

                    toast = Toast.makeText(getApplicationContext(),
                            "Поле пустое!", Toast.LENGTH_SHORT);
                    toast.show();

                }
                break;

            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_CONTACTS, null, null, null,
                        null, null, null);

                toast = Toast.makeText(getApplicationContext(),
                        "Посмотри данные в Logcat!", Toast.LENGTH_SHORT);
                toast.show();

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int nameIndex = cursor.getColumnIndex(DBHelper.KEY_NAME);
                    int emailIndex = cursor.getColumnIndex(DBHelper.KEY_MAIL);
                    do {
                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", name = " + cursor.getString(nameIndex) +
                                ", email = " + cursor.getString(emailIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog", " 0 rows");
                    cursor.close();
                    break;

            case R.id.btnClear:
                toast = Toast.makeText(getApplicationContext(),
                        "База данных очищена", Toast.LENGTH_SHORT);
                toast.show();
                database.delete(DBHelper.TABLE_CONTACTS, null, null);
                break;

            case R.id.btnTextClear:
                etName.setText(" ");
                etMail.setText(" ");
                break;
            
        }
        dbHelper.close();
    }



}