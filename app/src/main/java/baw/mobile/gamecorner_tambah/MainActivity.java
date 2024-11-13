package baw.mobile.gamecorner_tambah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class MainActivity extends AppCompatActivity {

    private Button btnPinjamKomputer;
    private Button btnPinjamPump;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inisialisasi tombol
        btnPinjamKomputer = findViewById(R.id.btnPinjamKomputer);
        btnPinjamPump = findViewById(R.id.btnPinjamPump);

        // Mengatur listener untuk tombol "Pinjam Komputer"
        btnPinjamKomputer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan AddPeminjamanFragment
                showAddPeminjamanFragment("Komputer");
            }
        });

        // Mengatur listener untuk tombol "Pinjam Pump"
        btnPinjamPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Menampilkan AddPeminjamanFragment
                showAddPeminjamanFragment("Pump");
            }
        });
    }

    private void showAddPeminjamanFragment(String fasilitas) {
        AddPeminjamanFragment fragment = new AddPeminjamanFragment();
        Bundle bundle = new Bundle();
        bundle.putString("fasilitas", fasilitas);
        fragment.setArguments(bundle);

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();

        // Sembunyikan komponen lain dan tampilkan fragment_container
        findViewById(R.id.tvWelcome).setVisibility(View.GONE);
        findViewById(R.id.tvUsername).setVisibility(View.GONE);
        findViewById(R.id.imgProfile).setVisibility(View.GONE);
        findViewById(R.id.etSearch).setVisibility(View.GONE);
        findViewById(R.id.frameKomputer).setVisibility(View.GONE);
        findViewById(R.id.framePump).setVisibility(View.GONE);
        findViewById(R.id.btnLihatDaftar).setVisibility(View.GONE);
        findViewById(R.id.bottomNavigationView).setVisibility(View.GONE);
        findViewById(R.id.fragment_container).setVisibility(View.VISIBLE);
    }
}
