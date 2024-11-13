package baw.mobile.gamecorner_tambah;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

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
                // Membuat intent untuk berpindah ke FormPeminjamanActivity
                Intent intent = new Intent(MainActivity.this, FormPeminjamanActivity.class);
                // Jika perlu, data ditambahkan ke intent
                intent.putExtra("fasilitas", "Komputer");
                startActivity(intent);
            }
        });

        // Mengatur listener untuk tombol "Pinjam Pump"
        btnPinjamPump.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Membuat intent untuk berpindah ke FormPeminjamanActivity
                Intent intent = new Intent(MainActivity.this, FormPeminjamanActivity.class);
                // Jika perlu, data ditambahkan ke intent
                intent.putExtra("fasilitas", "Pump");
                startActivity(intent);
            }
        });
    }
}
