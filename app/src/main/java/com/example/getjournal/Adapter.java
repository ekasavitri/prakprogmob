package com.example.getjournal;

import android.app.Activity;
import android.content.Context;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class Adapter extends BaseAdapter {
    private Activity activity  ;
    private LayoutInflater inflater;
    private List<Data> items;

    public Adapter(Activity activity, List<Data> items) {
        this.activity = activity;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int location) {
        return items.get(location);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (inflater == null)
            inflater = (LayoutInflater) activity
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if (convertView == null)
            convertView = inflater.inflate(R.layout.list_row, null);

        TextView id = (TextView) convertView.findViewById(R.id.id_list);
        TextView nama = (TextView) convertView.findViewById(R.id.nama_jurnal);
        TextView kategori = (TextView) convertView.findViewById(R.id.kategori);
        TextView tahun = (TextView) convertView.findViewById(R.id.tahun);

        Data data = items.get(position);

        id.setText(data.getId());
        nama.setText(data.getNama_jurnal());
        kategori.setText(data.getKategori());
        tahun.setText(data.getTahun());

        return convertView;
    }


}
