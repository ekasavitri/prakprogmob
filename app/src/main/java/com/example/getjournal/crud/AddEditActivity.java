package com.example.getjournal.crud;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.getjournal.HomeActivity;
import com.example.getjournal.R;
import com.example.getjournal.database.DataHelper;

public class AddEditActivity extends AppCompatActivity {

    EditText txt_id, txt_judul, txt_kategori, txt_tahun;
    Button bt_submit, bt_cancel;
    DataHelper SQLite = new DataHelper(this);
    String id, judul, kategori, tahun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addedit);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        txt_id = (EditText) findViewById(R.id.txt_id);
        txt_judul = (EditText) findViewById(R.id.txt_name);
        txt_kategori = (EditText) findViewById(R.id.txt_kategori);
        txt_kategori = (EditText) findViewById(R.id.txt_tahun);
        bt_submit = (Button) findViewById(R.id.btn_submit);
        bt_cancel = (Button) findViewById(R.id.btn_cancel);

        id = getIntent().getStringExtra(HomeActivity.TAG_ID);
        judul = getIntent().getStringExtra(HomeActivity.TAG_JUDUL);
        kategori = getIntent().getStringExtra(HomeActivity.TAG_KATEGORI);
        tahun = getIntent().getStringExtra(HomeActivity.TAG_TAHUN);

        if (id == null || id == "") {
            setTitle("Add Journal");
        } else {
            setTitle("Edit Journal");
            txt_id.setText(id);
            txt_judul.setText(judul);
            txt_kategori.setText(kategori);
            txt_tahun.setText(tahun);
        }

        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (txt_id.getText().toString().equals("")) {
                        save();
                    } else {
                        edit();
                    }
                } catch (Exception e) {
                    Log.e("Submit", e.toString());
                }
            }
        });

        bt_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                blank();
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                blank();
                this.finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void blank() {
        txt_judul.requestFocus();
        txt_kategori.requestFocus();
        txt_tahun.requestFocus();
        txt_id.setText(null);
        txt_judul.setText(null);
        txt_kategori.setText(null);
        txt_tahun.setText(null);
    }

    //menyimpan data ke sqlite database
    private void save() {
        if (String.valueOf(txt_judul.getText()).equals(null) || String.valueOf(txt_judul.getText()).equals("") ||
                String.valueOf(txt_kategori.getText()).equals(null) || String.valueOf(txt_kategori.getText()).equals("") ||
                String.valueOf(txt_tahun.getText()).equals(null) || String.valueOf(txt_tahun.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Masukkan Data ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.insert(txt_judul.getText().toString().trim(), txt_kategori.getText().toString().trim(), txt_tahun.getText().toString().trim());
            blank();
            finish();
        }
    }

    // update data di sqlite
    private void edit() {
        if (String.valueOf(txt_judul.getText()).equals(null) || String.valueOf(txt_judul.getText()).equals("") ||
                String.valueOf(txt_kategori.getText()).equals(null) || String.valueOf(txt_kategori.getText()).equals("") ||
                String.valueOf(txt_tahun.getText()).equals(null) || String.valueOf(txt_tahun.getText()).equals("")) {
            Toast.makeText(getApplicationContext(),
                    "Masukkan Data ...", Toast.LENGTH_SHORT).show();
        } else {
            SQLite.update(Integer.parseInt(txt_id.getText().toString().trim()), txt_judul.getText().toString().trim(),
                    txt_kategori.getText().toString().trim(), txt_tahun.getText().toString().trim());
            blank();
            finish();
        }
    }

}