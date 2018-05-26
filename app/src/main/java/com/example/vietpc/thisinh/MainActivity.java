package com.example.vietpc.thisinh;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.ContextMenu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lv_thiSinh;
    EditText edt_filter;
    FloatingActionButton fab_Add;
    List<thiSinh> thiSinhList;
    SQLlite dbthiSinh;
    adapter_thisSinh adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AnhXa();
        addThiSinh();
        textchanged();
        sortThiSinh();
        registerForContextMenu(lv_thiSinh);
    }
    public void AnhXa(){
        lv_thiSinh = (ListView) findViewById(R.id.list_View_thiSinh);
        edt_filter = (EditText) findViewById(R.id.editText_filter);
        fab_Add = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        dbthiSinh = new SQLlite(this,"SQLthiSinh",null,1);
        thiSinhList = new ArrayList<>();
    }

    public void addThiSinh(){
        dbthiSinh.addThiSinh(new thiSinh(1,"Bui Tuan Viet",7,8,9));
        dbthiSinh.addThiSinh(new thiSinh(2,"Nguyen Van A",2,4,5));
        dbthiSinh.addThiSinh(new thiSinh(3,"Tran Van B",7.4f,8.2f,9.1f));
        dbthiSinh.addThiSinh(new thiSinh(4,"Lai Thi C",7.2f,8,9));
        dbthiSinh.addThiSinh(new thiSinh(5,"Hoang Van D",4,8.1f,1.5f));
        dbthiSinh.addThiSinh(new thiSinh(6,"Bui Thi E",1,2,3));
        dbthiSinh.addThiSinh(new thiSinh(7,"Nguyen Thi Z",7,5.9f,3.6f));

        thiSinhList = dbthiSinh.getAllThisinh();
        adapter = new adapter_thisSinh(this,thiSinhList);
        lv_thiSinh.setAdapter(adapter);
    }

    public void textchanged(){
        edt_filter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                    adapter.getFilter().filter(s);
                    adapter.notifyDataSetChanged();
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    public void sortThiSinh(){
        Collections.sort(thiSinhList, new Comparator<thiSinh>() {
            @Override
            public int compare(thiSinh o1, thiSinh o2) {
                return (o1.getOnlyTen().compareTo(o2.getOnlyTen()));
            }
        });
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater inflater  = new MenuInflater(this);
        inflater.inflate(R.menu.sua_xoa,menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int pos = ((AdapterView.AdapterContextMenuInfo)item.getMenuInfo()).position;
        switch (item.getItemId()){
            case R.id.item_Sua:
                EditthiSinh(pos);
                break;
            case R.id.item_xoa:
                ShowAlertDialog(pos);
                break;
        }


        return super.onContextItemSelected(item);
    }


    public void ShowAlertDialog(final int pos){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Alert");
        int Dem = 0;
        for(int i =0;i<thiSinhList.size();i++){
            if(thiSinhList.get(i).getTongDiem() < thiSinhList.get(pos).getTongDiem())
                Dem++;
        }
        builder.setMessage("Ban co muon xoa " + Dem + " thi sinh khong?");
        builder.setCancelable(false);
        builder.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setNegativeButton("Xoa", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dbthiSinh.deleteTS(thiSinhList.get(pos));
                thiSinhList = dbthiSinh.getAllThisinh();
                adapter = new adapter_thisSinh(MainActivity.this,thiSinhList);
                lv_thiSinh.setAdapter(adapter);
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public void EditthiSinh(final int pos){
        Intent intent = new Intent(MainActivity.this, Sua.class );
        Bundle bundle = new Bundle();
        thiSinh ts = thiSinhList.get(pos);
        bundle.putSerializable("thisinh_edit",ts);
        bundle.putInt("vitri",pos);
        intent.putExtra("edit",bundle);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data == null) return;
        Bundle bundle = data.getBundleExtra("trave");
        thiSinh edited_thisinh = (thiSinh) bundle.getSerializable("thisinh");
        int vt = bundle.getInt("vitri");
        dbthiSinh.updateThiSinh(edited_thisinh);
        thiSinhList.set(vt,edited_thisinh);
        adapter.notifyDataSetChanged();
    }
}
