package baw.mobile.firebase_createreservation;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private Button btnPinjamKomputer;
    private Button btnPinjamPump;
    public static final
    String FirebaseURL ="https://pamd-da691-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi Firebase Database
        FirebaseDatabase database = FirebaseDatabase.getInstance(FirebaseURL);
        db = database.getReference("reservasi");

        // Inisialisasi tombol
        btnPinjamKomputer = findViewById(R.id.btnPinjamKomputer);
        btnPinjamPump = findViewById(R.id.btnPinjamPump);

        // Mengatur listener untuk tombol "Pinjam Komputer"
        btnPinjamKomputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan AddPeminjamanFragment untuk Komputer
                showAddPeminjamanFragment("Komputer");
            }
        });

        // Mengatur listener untuk tombol "Pinjam Pump"
        btnPinjamPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan AddPeminjamanFragment untuk Pump
                showAddPeminjamanFragment("Pump");
            }
        });
    }

    private void showAddPeminjamanFragment(String fasilitas) {
        // Membuat fragment dan memberikan data (fasilitas)
        AddPeminjamanFragment fragment = new AddPeminjamanFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fasilitas", fasilitas); // Mengirim nama fasilitas ke fragment
        fragment.setArguments(bundle);

        // Menampilkan fragment menggunakan FragmentTransaction
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragmentContainer, fragment); // Pastikan ID sesuai dengan frame container di XML
        transaction.addToBackStack(null); // Menambahkan fragment ke back stack agar bisa kembali
        transaction.commit();

        // Sembunyikan elemen-elemen utama di MainActivity
        findViewById(R.id.tvWelcome).setVisibility(View.GONE);
        findViewById(R.id.tvUsername).setVisibility(View.GONE);
        findViewById(R.id.imgProfile).setVisibility(View.GONE);
        findViewById(R.id.etSearch).setVisibility(View.GONE);
        findViewById(R.id.frameKomputer).setVisibility(View.GONE);
        findViewById(R.id.framePump).setVisibility(View.GONE);
        findViewById(R.id.btnLihatDaftar).setVisibility(View.GONE);
        findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);

        // Ubah visibilitas fragmentContainer menjadi terlihat
        findViewById(R.id.fragmentContainer).setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
        if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
            getSupportFragmentManager().popBackStack();

            // Tampilkan kembali elemen-elemen utama jika kembali dari fragment
            if (getSupportFragmentManager().getBackStackEntryCount() == 0) {
                findViewById(R.id.tvWelcome).setVisibility(View.VISIBLE);
                findViewById(R.id.tvUsername).setVisibility(View.VISIBLE);
                findViewById(R.id.imgProfile).setVisibility(View.VISIBLE);
                findViewById(R.id.etSearch).setVisibility(View.VISIBLE);
                findViewById(R.id.frameKomputer).setVisibility(View.VISIBLE);
                findViewById(R.id.framePump).setVisibility(View.VISIBLE);
                findViewById(R.id.btnLihatDaftar).setVisibility(View.VISIBLE);
                findViewById(R.id.bottomNavigationView).setVisibility(View.VISIBLE);
                findViewById(R.id.fragmentContainer).setVisibility(View.GONE);
            }
        } else {
            super.onBackPressed();
        }
    }
}
