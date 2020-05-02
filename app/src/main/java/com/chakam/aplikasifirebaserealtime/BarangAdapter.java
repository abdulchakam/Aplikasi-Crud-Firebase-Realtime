package com.chakam.aplikasifirebaserealtime;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class BarangAdapter extends RecyclerView.Adapter<BarangAdapter.ViewHolder> {

    //Deklarasi Variable
    private ArrayList<Barang> dataBarang;
    private Context context;


    //Membuat Interfece
    public interface dataListener{
        void onDeleteData(Barang data, int position);
    }
    //Deklarasi objek dari Interfece
    dataListener listener;

    //Membuat Konstruktor, untuk menerima input dari Database
    public BarangAdapter(ArrayList<Barang> dataList, Context ctx) {
        dataBarang = dataList;
        context = ctx;

        //tambahkan kode ini
        listener = (FirebaseDBRead)ctx;
    }

    //ViewHolder Digunakan Untuk Menyimpan Referensi Dari View-View
    class ViewHolder extends RecyclerView.ViewHolder{

        private TextView namaBarang, hargaBarang, stokBarang, satuanBarang;
        private CardView cardItem;

        ViewHolder(View itemView) {
            super(itemView);
            //Menginisialisasi View-View yang terpasang pada layout RecyclerView kita
            namaBarang = itemView.findViewById(R.id.tv_namabarang);
            hargaBarang = itemView.findViewById(R.id.tv_hargabarang);
            stokBarang = itemView.findViewById(R.id.tv_stokbarang);
            satuanBarang = itemView.findViewById(R.id.tv_satuan);
            cardItem = itemView.findViewById(R.id.card_item);
        }
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //Mengambil Nilai/Value yenag terdapat pada RecyclerView berdasarkan Posisi Tertentu
        final String namaBarang = dataBarang.get(position).getNama();
        final String hargaBarang = dataBarang.get(position).getHarga_jual();
        final String stokBarang = dataBarang.get(position).getStok();
        final String satuan = dataBarang.get(position).getSatuan();

        //Memasukan Nilai/Value kedalam View (TextView: NIM, Nama, Jurusan)
        holder.namaBarang.setText(namaBarang);
        holder.hargaBarang.setText(hargaBarang);
        holder.stokBarang.setText(stokBarang);
        holder.satuanBarang.setText(satuan);

        holder.cardItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(final View view) {

                //Tambahkan kode berikut

                final String[] action = {"Update", "Delete"};
                AlertDialog.Builder alert = new AlertDialog.Builder(view.getContext());
                alert.setItems(action,  new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int i) {
                        switch (i){
                            case 0:
                                /*
                                  Berpindah Activity pada halaman layout updateData
                                  dan mengambil data pada listMahasiswa, berdasarkan posisinya
                                  untuk dikirim pada activity selanjutnya
                                */
                                Bundle bundle = new Bundle();
                                bundle.putString("dataKode", dataBarang.get(position).getKode());
                                bundle.putString("dataNama", dataBarang.get(position).getNama());
                                bundle.putString("dataSatuan", dataBarang.get(position).getSatuan());
                                bundle.putString("dataHargaBeli", dataBarang.get(position).getHarga_beli());
                                bundle.putString("dataHargaJual", dataBarang.get(position).getHarga_jual());
                                bundle.putString("dataStok", dataBarang.get(position).getStok());
                                bundle.putString("dataStokMinimal", dataBarang.get(position).getStok_min());
                                bundle.putString("getPrimaryKey", dataBarang.get(position).getKey());
                                Intent intent = new Intent(view.getContext(), FirebaseDBUpdate.class);
                                intent.putExtras(bundle);
                                context.startActivity(intent);
                                break;
                            case 1:
                                //Memanggil fungsi Delete
                                listener.onDeleteData(dataBarang.get(position), position);
                                break;
                        }
                    }
                });
                alert.create();
                alert.show();
                return true;
            }
        });
    }

    @Override
    public int getItemCount() {
        //Menghitung Ukuran/Jumlah Data Yang Akan Ditampilkan Pada RecyclerView
         return dataBarang.size();
    }

}
