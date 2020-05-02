package com.chakam.aplikasifirebaserealtime;

import java.io.Serializable;

public class Barang implements Serializable  {
    private String kode;
    private String satuan;
    private String harga_beli;
    private String harga_jual;
    private String stok;
    private String stok_min;
    private String nama;
    private String key;



    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getSatuan() {
        return satuan;
    }

    public void setSatuan(String satuan) {
        this.satuan = satuan;
    }

    public String getHarga_beli() {
        return harga_beli;
    }

    public void setHarga_beli(String harga_beli) {
        this.harga_beli = harga_beli;
    }

    public String getHarga_jual() {
        return harga_jual;
    }

    public void setHarga_jual(String harga_jual) {
        this.harga_jual = harga_jual;
    }

    public String getStok() {
        return stok;
    }

    public void setStok(String stok) {
        this.stok = stok;
    }

    public String getStok_min() {
        return stok_min;
    }

    public void setStok_min(String stok_min) {
        this.stok_min = stok_min;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return " "+kode+"\n" +
                " "+nama+"\n" +
                " "+satuan+"\n" +
                " "+harga_beli+"\n" +
                " "+harga_jual+"\n" +
                " "+stok+"\n" +
                " "+stok_min;
    }

    public Barang () {}
    public Barang(String kd, String nm, String sat, String hrg_beli, String hrg_jual, String stk, String stk_min){
        kode = kd;
        nama = nm;
        satuan = sat;
        harga_beli =  hrg_beli;
        harga_jual = hrg_jual;
        stok = stk;
        stok_min = stk_min;
    }


}
