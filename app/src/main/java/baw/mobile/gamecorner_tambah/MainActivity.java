package baw.mobile.gamecorner_tambah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
<<<<<<< HEAD
import androidx.fragment.app.FragmentTransaction;
=======
>>>>>>> 815c308d01620dd13c356003cd44ca7ae8471aa2

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
<<<<<<< HEAD
                // Menampilkan AddPeminjamanFragment
                showAddPeminjamanFragment("Komputer");
=======
                // Membuat intent untuk berpindah ke FormPeminjamanActivity
                Intent intent = new Intent(MainActivity.this, FormPeminjamanActivity.class);
                // Jika perlu, data ditambahkan ke intent
                intent.putExtra("fasilitas", "Komputer");
                startActivity(intent);
>>>>>>> 815c308d01620dd13c356003cd44ca7ae8471aa2
            }
        });

        // Mengatur listener untuk tombol "Pinjam Pump"
        btnPinjamPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
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
=======
                // Membuat intent untuk berpindah ke FormPeminjamanActivity
                Intent intent = new Intent(MainActivity.this, FormPeminjamanActivity.class);
                // Jika perlu, data ditambahkan ke intent
                intent.putExtra("fasilitas", "Pump");
                startActivity(intent);
            }
        });
    }
>>>>>>> 815c308d01620dd13c356003cd44ca7ae8471aa2
}
