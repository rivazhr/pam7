package rfz.mobile.gamecorner;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Reservasi extends AppCompatActivity implements View.OnClickListener {

    public static final String FirebaseURL = "https://game-corner7-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private FirebaseDatabase db;
    private DatabaseReference appDb;
    private String id;
    private TextView tvStatus;
    private TextView tvOpsi;
    private TextView tvSesi;
    private TextView tvNama;
    private TextView tvNim;
    private TextView tvProdi;
    private TextView tvNotel;
    private TextView tvNav;
    private Button btBatalkan;
    private Riwayat riwayat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_reservasi);

        Intent intent = getIntent();
        this.id = intent.getStringExtra("id");

        this.db = FirebaseDatabase.getInstance(FirebaseURL);
        this.appDb = this.db.getReference("game_corner7");

        this.tvStatus = findViewById(R.id.tvStatus);
        this.tvOpsi = findViewById(R.id.tvOpsi);
        this.tvSesi = findViewById(R.id.tvSesi);
        this.tvNama = findViewById(R.id.tvNama);
        this.tvNim = findViewById(R.id.tvNim);
        this.tvProdi = findViewById(R.id.tvProdi);
        this.tvNotel = findViewById(R.id.tvNotel);
        this.tvNav = findViewById(R.id.tvNav);
        this.btBatalkan = findViewById(R.id.btBatalkan);

        this.tvNav.setOnClickListener(this);
        this.btBatalkan.setOnClickListener(this);

        appDb.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    riwayat = dataSnapshot.getValue(Riwayat.class);

                    if (riwayat != null) {
                        Log.d("Reservasi", "Data berhasil diambil: " + riwayat.toString()); // Log data
                        tvStatus.setText(riwayat.getStatus());
                        tvOpsi.setText(riwayat.getOpsi());
                        tvSesi.setText(riwayat.getSesi());
                        tvNama.setText(riwayat.getNama());
                        tvNim.setText(riwayat.getNim());
                        tvProdi.setText(riwayat.getProdi());
                        tvNotel.setText(riwayat.getNoTel());
                    }
                } else {
                    Log.d("Reservasi", "Data tidak ditemukan.");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.tvNav) onBackPressed();
        else if (view.getId() == R.id.btBatalkan) {
            showBatalkanDialog();
        }
    }

    private void showBatalkanDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        LayoutInflater inflater = LayoutInflater.from(this);
        View customView = inflater.inflate(R.layout.dialog_batalkan, null);
        builder.setView(customView);

        AlertDialog dialog = builder.create();
        dialog.setCancelable(false);
        dialog.show();

        Button btnYes = customView.findViewById(R.id.btnYes);
        Button btnNo = customView.findViewById(R.id.btnNo);

        btnYes.setOnClickListener(v -> {
            appDb.child(riwayat.getId()).removeValue()
                    .addOnSuccessListener(aVoid -> {
                        onBackPressed();
                        Toast.makeText(this, "Reservasi berhasil dibatalkan.", Toast.LENGTH_SHORT).show();
                    })
                    .addOnFailureListener(e -> {
                        Toast.makeText(this, "Gagal membatalkan reservasi. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                    });
            dialog.dismiss();
        });

        btnNo.setOnClickListener(v -> dialog.dismiss());
    }
}
