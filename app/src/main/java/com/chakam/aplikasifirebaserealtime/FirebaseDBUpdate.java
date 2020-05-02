package com.chakam.aplikasifirebaserealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class FirebaseDBUpdate extends AppCompatActivity {
    //Deklarasi Variable
    private EditText kodeUpdate, namaUpdate, satuanUpdate,
                        hargaBeliUpdate, hargaJualUpdate, stokUpdate, stokMinUpdate;
    private Button update;
    private DatabaseReference database;
    private FirebaseAuth auth;
    private String cekKode, cekNama, cekSatuan, cekHargaBeli,
                    cekHargaJual, cekStok, cekStokMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_dbupdate);

        kodeUpdate = findViewById(R.id.et_kodebarangupdate);
        namaUpdate = findViewById(R.id.et_namabarangupdate);
        satuanUpdate = findViewById(R.id.et_satuanupdate);
        hargaBeliUpdate = findViewById(R.id.et_hargabeliupdate);
        hargaJualUpdate = findViewById(R.id.et_hargajualupdate);
        stokUpdate = findViewById(R.id.et_stokupdate);
        stokMinUpdate = findViewById(R.id.et_stokminupdate);

        update = findViewById(R.id.bt_update);

        //Mendapatkan Instance autentikasi dan Referensi dari Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference();
        getData();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Mendapatkan Data Barang yang akan dicek
                cekKode = kodeUpdate.getText().toString();
                cekNama = namaUpdate.getText().toString();
                cekSatuan = satuanUpdate.getText().toString();
                cekHargaBeli = hargaBeliUpdate.getText().toString();
                cekHargaJual = hargaJualUpdate.getText().toString();
                cekStok = stokUpdate.getText().toString();
                cekStokMin = stokMinUpdate.getText().toString();

                //Mengecek agar tidak ada data yang kosong, saat proses update
                if(isEmpty(cekKode) || isEmpty(cekNama) || isEmpty(cekSatuan) || isEmpty(cekHargaBeli) ||
                        isEmpty(cekHargaJual) || isEmpty(cekStok) || isEmpty(cekStokMin)){
                    Toast.makeText(FirebaseDBUpdate.this, "Data tidak boleh ada yang kosong", Toast.LENGTH_SHORT).show();
                }else {
                    /*
                      Menjalankan proses update data.
                      Method Setter digunakan untuk mendapakan data baru yang diinputkan User.
                    */
                    Barang setBarang = new Barang();
                    setBarang.setKode(kodeUpdate.getText().toString());
                    setBarang.setNama(namaUpdate.getText().toString());
                    setBarang.setSatuan(satuanUpdate.getText().toString());
                    setBarang.setHarga_beli(hargaBeliUpdate.getText().toString());
                    setBarang.setHarga_jual(hargaJualUpdate.getText().toString());
                    setBarang.setStok(stokUpdate.getText().toString());
                    setBarang.setStok_min(stokMinUpdate.getText().toString());
                    updateBarang(setBarang);
                }

            }
        });
    }

    // Mengecek apakah ada data yang kosong, sebelum diupdate
    private boolean isEmpty(String s){
        return TextUtils.isEmpty(s);
    }

    //Menampilkan data yang akan di update
    private void getData(){
        //Menampilkan data dari item yang dipilih sebelumnya
        final String getKode = getIntent().getExtras().getString("dataKode");
        final String getNama = getIntent().getExtras().getString("dataNama");
        final String getSatuan = getIntent().getExtras().getString("dataSatuan");
        final String getHargaBeli = getIntent().getExtras().getString("dataHargaBeli");
        final String getHargaJual = getIntent().getExtras().getString("dataHargaJual");
        final String getStok = getIntent().getExtras().getString("dataStok");
        final String getStokMin = getIntent().getExtras().getString("dataStokMinimal");
        kodeUpdate.setText(getKode);
        namaUpdate.setText(getNama);
        satuanUpdate.setText(getSatuan);
        hargaBeliUpdate.setText(getHargaBeli);
        hargaJualUpdate.setText(getHargaJual);
        stokUpdate.setText(getStok);
        stokMinUpdate.setText(getStokMin);
    }

    //Proses Update data yang sudah ditentukan
    private void updateBarang(Barang barang){
        String userID = auth.getUid();
        String getKey = getIntent().getExtras().getString("getPrimaryKey");
        database.child("barang")
                .child(getKey)
                .setValue(barang)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        kodeUpdate.setText("");
                        namaUpdate.setText("");
                        satuanUpdate.setText("");
                        hargaBeliUpdate.setText("");
                        hargaJualUpdate.setText("");
                        stokUpdate.setText("");
                        stokMinUpdate.setText("");
                        Toast.makeText(FirebaseDBUpdate.this, "Data Berhasil diubah", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                });
    }
}
