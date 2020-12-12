package com.example.getjournal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.getjournal.crud.AddEditActivity;
import com.example.getjournal.database.DataHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    ListView listView;
    AlertDialog.Builder dialog;
    List<Data> itemList = new ArrayList<Data>();
    Adapter adapter;
    DataHelper SQLite = new DataHelper(this);

    public static final String TAG_ID ="id";
    public static final String TAG_JUDUL ="judul";
    public static final String TAG_KATEGORI ="kategori";
    public static final String TAG_TAHUN ="tahun";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        SQLite = new DataHelper(getApplicationContext());
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        listView = (ListView) findViewById(R.id.list_view);

        BottomNavigationView bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setSelectedItemId(R.id.nav_library);

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.nav_library:
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_jurnal:
                        startActivity(new Intent(getApplicationContext(),JurnalActivity.class));
                        overridePendingTransition(0,0);
                        break;
                    case R.id.nav_user:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        break;
                }
                return false;
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, AddEditActivity.class);
                startActivity(intent);
            }
        });

        adapter= new Adapter(HomeActivity.this, itemList);
        listView.setAdapter(adapter);

        //klik listview agak lama buat nampilin edit sama hapus
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position,
                                           long id) {
                // TODO Auto-generated method stub
                final String id1 = itemList.get(position).getId();
                final String nama = itemList.get(position).getNama_jurnal();
                final String kategori = itemList.get(position).getKategori();
                final String tahun = itemList.get(position).getTahun();

                final CharSequence[] dialogitem = {"Edit", "Delete"};
                dialog = new AlertDialog.Builder(HomeActivity.this);
                dialog.setCancelable(true);
                dialog.setItems(dialogitem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                Intent intent = new Intent(HomeActivity.this, AddEditActivity.class);
                                intent.putExtra(TAG_ID, id1);
                                intent.putExtra(TAG_JUDUL, nama);
                                intent.putExtra(TAG_KATEGORI, kategori);
                                intent.putExtra(TAG_TAHUN, tahun);
                                startActivity(intent);
                                break;
                            case 1:
                                SQLite.delete(Integer.parseInt(id1));
                                itemList.clear();
                                getAllData();
                                break;
                        }
                    }
                }).show();
                return false;
            }
        });

        getAllData();
    }

    private void getAllData(){
        ArrayList<HashMap<String, String>> row = SQLite.getAllData();

        for (int i = 0; i < row.size(); i++) {
            String id = row.get(i).get(TAG_ID);
            String judul1 = row.get(i).get(TAG_JUDUL);
            String kategori1 = row.get(i).get(TAG_KATEGORI);
            String tahun1 = row.get(i).get(TAG_TAHUN);

            Data data = new Data();

            data.setId(id);
            data.setNama_jurnal(judul1);
            data.setKategori(kategori1);
            data.setTahun(tahun1);

            itemList.add(data);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
        super.onResume();
        itemList.clear();
        getAllData();
    }
}