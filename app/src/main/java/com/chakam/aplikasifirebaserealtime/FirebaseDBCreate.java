package com.chakam.aplikasifirebaserealtime;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static android.text.TextUtils.isEmpty;

public class FirebaseDBCreate extends AppCompatActivity {

    // variable yang merefers ke Firebase Realtime Database
    private DatabaseReference database;

    // variable fields EditText dan Button
    private Button btSubmit;
    private EditText etKode, etNama, etSatuan, etHargaBeli, etHargaJual, etStok, etStokMin;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_firebase_dbcreate);


        // inisialisasi fields EditText dan Button
        etKode = (EditText) findViewById(R.id.et_kodebarang);
        etNama = (EditText) findViewById(R.id.et_namabarang);
        etSatuan = (EditText) findViewById(R.id.et_satuan);
        etHargaBeli = (EditText) findViewById(R.id.et_hargabeli);
        etHargaJual = (EditText) findViewById(R.id.et_hargajual);
        etStok = (EditText) findViewById(R.id.et_stok);
        etStokMin = (EditText) findViewById(R.id.et_stokmin);

        btSubmit = (Button) findViewById(R.id.bt_submit);

        // mengambil referensi ke Firebase Database
        database = FirebaseDatabase.getInstance().getReference();

        // kode yang dipanggil ketika tombol Submit diklik
        btSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isEmpty(etKode.getText().toString()) &&
                        !isEmpty(etNama.getText().toString()) &&
                        !isEmpty(etSatuan.getText().toString()) &&
                        !isEmpty(etHargaBeli.getText().toString()) &&
                        !isEmpty(etHargaJual.getText().toString()) &&
                        !isEmpty(etStok.getText().toString()) &&
                        !isEmpty(etStokMin.getText().toString()))

                    submitBarang(new Barang(
                            etKode.getText().toString(),
                            etNama.getText().toString(),
                            etSatuan.getText().toString(),
                            etHargaBeli.getText().toString(),
                            etHargaJual.getText().toString(),
                            etStok.getText().toString(),
                            etStokMin.getText().toString()));
                else
                    Snackbar.make(findViewById(R.id.bt_submit),
                            "Data barang tidak boleh kosong", Snackbar.LENGTH_LONG).show();

                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(
                        etKode.getWindowToken(), 0);
            }
        });
    }

    private boolean isEmpty(String s) {
        // Cek apakah ada fields yang kosong, sebelum disubmit
        return TextUtils.isEmpty(s);
    }
    private void updateBarang(Barang barang) {
        // kodingan untuk next tutorial ya :p
    }

    private void submitBarang(Barang barang) {
        /**
         * Ini adalah kode yang digunakan untuk mengirimkan data ke Firebase Realtime Database
         * dan juga kita set onSuccessListener yang berisi kode yang akan dijalankan
         * ketika data berhasil ditambahkan
         */

        database.child("barang").push().setValue(barang).addOnSuccessListener(
                this, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                etKode.setText("");
                etNama.setText("");
                etSatuan.setText("");
                etHargaBeli.setText("");
                etHargaJual.setText("");
                etStok.setText("");
                etStokMin.setText("");
                Snackbar.make(findViewById(R.id.bt_submit),
                        "Data berhasil ditambahkan", Snackbar.LENGTH_LONG).show();
            }
        });
    }
    public static Intent getActIntent(Activity activity) {
        // kode untuk pengambilan Intent
        return new Intent(activity, FirebaseDBCreate.class);
    }
}
