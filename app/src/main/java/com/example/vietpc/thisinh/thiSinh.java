package com.example.vietpc.thisinh;

import android.text.Editable;
import android.widget.Filter;
import android.widget.Filterable;

import java.io.Serializable;

public class thiSinh implements Serializable {
    private int sbd;
    private String ten;
    private float toan,ly,hoa;

    public thiSinh(int sbd, String ten, float toan, float ly, float hoa) {
        this.sbd = sbd;
        this.ten = ten;
        this.toan = toan;
        this.ly = ly;
        this.hoa = hoa;
    }

    public thiSinh() {}

    public int getSbd() {
        return sbd;
    }

    public void setSbd(int sbd) {
        this.sbd = sbd;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public float getToan() {
        return toan;
    }

    public void setToan(float toan) {
        this.toan = toan;
    }

    public float getLy() {
        return ly;
    }

    public void setLy(float ly) {
        this.ly = ly;
    }

    public float getHoa() {
        return hoa;
    }

    public void setHoa(float hoa) {
        this.hoa = hoa;
    }

    public float getTongDiem(){
        return this.toan+this.hoa+this.ly;
    }

    public String getOnlyTen(){
        String []abc = this.ten.split(" ");
        return abc[abc.length-1];
    }


}
