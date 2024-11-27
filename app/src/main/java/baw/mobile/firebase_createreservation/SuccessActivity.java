package baw.mobile.firebase_createreservation;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class SuccessActivity extends AppCompatActivity {

    private DatabaseReference db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        // Inisialisasi Firebase Database
        db = FirebaseDatabase.getInstance().getReference("reservasi");

        Button btnBack = findViewById(R.id.btnBack);
        Button btnCheckData = findViewById(R.id.btnCheckData);

        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        });

        btnCheckData.setOnClickListener(v -> checkData());
    }

    private void checkData() {
        db.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot data : snapshot.getChildren()) {
                    Reservasi reservasi = data.getValue(Reservasi.class);
                    Log.d("DatabaseCheck", "Data dari Firebase: " + reservasi.toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.e("DatabaseCheck", "Gagal membaca data: " + error.getMessage());
            }
        });
    }
}

