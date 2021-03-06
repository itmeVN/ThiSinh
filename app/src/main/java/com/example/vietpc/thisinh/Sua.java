package com.example.vietpc.thisinh;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Sua extends AppCompatActivity {

    EditText edt_sbd, edt_ten,edt_toan,edt_ly,edt_hoa;
    Button btn_add,btn_cancel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_thisinh);
        AnhXa();
        final Bundle bundle = getIntent().getBundleExtra("edit");
        final thiSinh ts = (thiSinh) bundle.getSerializable("thisinh_edit");
        final int vitri = bundle.getInt("vitri");
        Edit(ts);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send(ts,vitri,bundle);
                finish();
            }
        });
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void AnhXa(){
        edt_sbd = (EditText) findViewById(R.id.editText_sbd);
        edt_ten = (EditText) findViewById(R.id.editText_hoten);
        edt_toan = (EditText) findViewById(R.id.editText_toan);
        edt_hoa = (EditText) findViewById(R.id.editText_hoa);
        edt_ly = (EditText) findViewById(R.id.editText_ly);
        btn_add = (Button) findViewById(R.id.button_edit);
        btn_cancel = (Button) findViewById(R.id.button_cancel);
    }
    public void Edit(thiSinh ts){
        edt_sbd.setText(ts.getSbd() + "");
        edt_ten.setText(ts.getTen());
        edt_toan.setText(ts.getToan()+ "");
        edt_ly.setText(ts.getLy()+ "");
        edt_hoa.setText(ts.getHoa()+ "");
    }
    public void send(thiSinh ts,int vt,Bundle bundle) {
        ts.setSbd(Integer.parseInt(edt_sbd.getText().toString()));
        ts.setTen(edt_ten.getText().toString());
        ts.setToan(Float.parseFloat(edt_toan.getText().toString()));
        ts.setLy(Float.parseFloat(edt_ly.getText().toString()));
        ts.setHoa(Float.parseFloat(edt_hoa.getText().toString()));
        Intent intent = new Intent(Sua.this,MainActivity.class);
        bundle.putSerializable("thisinh",ts);
        bundle.putInt("vitri",vt);
        intent.putExtra("trave",bundle);
        setResult(200,intent);
    }
}

