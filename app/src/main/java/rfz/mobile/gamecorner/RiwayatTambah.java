package rfz.mobile.gamecorner;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RiwayatTambah extends AppCompatActivity implements View.OnClickListener {

    public static final String FirebaseURL = "https://game-corner7-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private Button btSimpan;
    private EditText etNamaPeminjam;
    private EditText etNimPeminjam;
    private EditText etProdi;
    private EditText etNotel;
    private RadioGroup rgPilihSesi;
    private RadioGroup rgPilihJumlahStik;
    private FirebaseDatabase db;
    private DatabaseReference appDb;
    private TextView tvNav;
    private String opsi;
    private TextView tvTambahAlat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_riwayat_tambah);

        Intent intent = getIntent();
        this.opsi = intent.getStringExtra("opsi");

        this.tvNav = findViewById(R.id.tvNav);
        tvNav.setText("Buat Reservasi - " + this.opsi);
        this.tvNav.setOnClickListener(v -> onBackPressed());

        this.btSimpan = findViewById(R.id.btSimpan);
        this.etNamaPeminjam = findViewById(R.id.etNamaPeminjam);
        this.etNimPeminjam = findViewById(R.id.etNimPeminjam);
        this.etProdi = findViewById(R.id.etProdi);
        this.etNotel = findViewById(R.id.etNotel);
        this.rgPilihSesi = findViewById(R.id.rgPilihSesi);
        this.rgPilihJumlahStik = findViewById(R.id.rgPilihJumlahStik);
        this.tvTambahAlat = findViewById(R.id.tvTambahAlat);

        if(this.opsi.equals("Pump It Up")){
            this.rgPilihJumlahStik.setVisibility(View.GONE);
            this.tvTambahAlat.setVisibility(View.GONE);
        } else {
            this.rgPilihJumlahStik.setVisibility(View.VISIBLE);
            this.tvTambahAlat.setVisibility(View.VISIBLE);
        }

        this.db = FirebaseDatabase.getInstance(FirebaseURL);
        this.appDb = this.db.getReference("game_corner7");

        this.btSimpan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.btSimpan){
            if (TextUtils.isEmpty(etNamaPeminjam.getText()) || TextUtils.isEmpty(etNimPeminjam.getText()) || TextUtils.isEmpty(etProdi.getText()) || TextUtils.isEmpty(etNotel.getText())) {
                if (TextUtils.isEmpty(etNamaPeminjam.getText())) etNamaPeminjam.setError("Field ini harus diisi");
                if (TextUtils.isEmpty(etNimPeminjam.getText())) etNimPeminjam.setError("Field ini harus diisi");
                if (TextUtils.isEmpty(etProdi.getText())) etProdi.setError("Field ini harus diisi");
                if (TextUtils.isEmpty(etNotel.getText())) etNotel.setError("Field ini harus diisi");
                return;
            }

            String id = this.appDb.push().getKey();
            int selectedSesiId = rgPilihSesi.getCheckedRadioButtonId();
            RadioButton selectedSesi = findViewById(selectedSesiId);
            long tanggalToday = System.currentTimeMillis();

            Riwayat riwayat = new Riwayat();
            riwayat.setId(id);
            riwayat.setNama(this.etNamaPeminjam.getText().toString());
            riwayat.setNim(this.etNimPeminjam.getText().toString());
            riwayat.setProdi(this.etProdi.getText().toString());
            riwayat.setNoTel(this.etNotel.getText().toString());
            riwayat.setOpsi(this.opsi);
            riwayat.setStatus("Sedang Berlangsung");
            riwayat.setSesi(selectedSesi.getText().toString());
            riwayat.setTanggal(tanggalToday);
            if (this.rgPilihJumlahStik.getVisibility() == View.VISIBLE) {
                int selectedJumlahStikId = rgPilihJumlahStik.getCheckedRadioButtonId();
                RadioButton selectedJumlahStik = findViewById(selectedJumlahStikId);
                riwayat.setJumlahAlat(Integer.parseInt(selectedJumlahStik.getText().toString()));
            }

            this.appDb.child(id).setValue(riwayat);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}