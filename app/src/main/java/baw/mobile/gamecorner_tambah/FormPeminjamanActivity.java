package baw.mobile.gamecorner_tambah;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class FormPeminjamanActivity extends AppCompatActivity {

    private ImageButton btnBack, btnMinus, btnPlus, btnUploadKtm;
    private TextView tvTitle, tvJumlahStik;
<<<<<<< HEAD
    private EditText etNamaPeminjam, etNimPeminjam, etProdiPeminjam, etNoHp;
=======
    private EditText etNamaPeminjam, etNimPeminjam, etProdiPeminjam, etNoHp, etKtm;
>>>>>>> 815c308d01620dd13c356003cd44ca7ae8471aa2
    private Spinner spinnerSesi;
    private Button btnKirim;

    private int jumlahStik = 0; // Awal jumlah stik
    private static final int PICK_IMAGE_REQUEST = 1; // Kode untuk membuka galeri

    // URL API
    private static final String URL_API = "http://192.168.56.1/restApiAsp/Reservation.php";

    private Gson gson = new Gson(); // GSON instance for parsing

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_peminjaman);

        // Inisialisasi UI
        btnBack = findViewById(R.id.btnBack);
        tvTitle = findViewById(R.id.tvTitle);
        etNamaPeminjam = findViewById(R.id.etNamaPeminjam);
        etNimPeminjam = findViewById(R.id.etNimPeminjam);
        etProdiPeminjam = findViewById(R.id.etProdiPeminjam);
        etNoHp = findViewById(R.id.etNoHp);
<<<<<<< HEAD
=======
        etKtm = findViewById(R.id.etKtm);
>>>>>>> 815c308d01620dd13c356003cd44ca7ae8471aa2
        btnMinus = findViewById(R.id.btnMinus);
        btnPlus = findViewById(R.id.btnPlus);
        tvJumlahStik = findViewById(R.id.tvJumlahStik);
        btnUploadKtm = findViewById(R.id.btnUploadKtm);
        spinnerSesi = findViewById(R.id.spinnerSesi);
        btnKirim = findViewById(R.id.btnKirim);

        // Mendapatkan data dari intent
        Intent intent = getIntent();
        String fasilitas = intent.getStringExtra("fasilitas");

        // Menampilkan nama fasilitas di judul
        tvTitle.setText("Buat Reservasi - " + fasilitas);

        // Mengatur Spinner Sesi
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.sesi_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSesi.setAdapter(adapter);

        // Mengatur listener untuk tombol kembali
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Listener untuk mengurangi jumlah stik
        btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (jumlahStik > 0) {
                    jumlahStik--;
                    tvJumlahStik.setText(String.valueOf(jumlahStik));
                }
            }
        });

        // Listener untuk menambah jumlah stik
        btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                jumlahStik++;
                tvJumlahStik.setText(String.valueOf(jumlahStik));
            }
        });

        // Listener untuk unggah KTM
        btnUploadKtm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Buka galeri untuk memilih gambar
                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, PICK_IMAGE_REQUEST);
            }
        });

        // Mengatur listener untuk tombol kirim
        btnKirim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kirimDataPeminjaman();
            }
        });
    }

    // Bagian dalam fungsi `kirimDataPeminjaman`
    private void kirimDataPeminjaman() {
        String namaPeminjam = etNamaPeminjam.getText().toString();
        String nimPeminjam = etNimPeminjam.getText().toString();
        String prodiPeminjam = etProdiPeminjam.getText().toString();
        String noHp = etNoHp.getText().toString();
        String sesi = spinnerSesi.getSelectedItem().toString();
        String jumlahStikStr = String.valueOf(jumlahStik);
<<<<<<< HEAD

=======
        String ktm = etKtm.getText().toString();
>>>>>>> 815c308d01620dd13c356003cd44ca7ae8471aa2

        // Membuat request dengan Volley
        // Create request with Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_API,
                response -> {
                    // Parse the JSON response using GSON
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    String message = jsonObject.get("message").getAsString();

                    // Display server message on the main UI thread
                    runOnUiThread(() -> {
                        Toast.makeText(FormPeminjamanActivity.this, message, Toast.LENGTH_SHORT).show();
                        if (message.equals("Data disimpan")) {
                            startActivity(new Intent(FormPeminjamanActivity.this, SuccessActivity.class));
                        }
                    });
                },
                error -> runOnUiThread(() -> {
                    Toast.makeText(FormPeminjamanActivity.this, "Failed to send data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                })) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                params.put("nama_peminjam", namaPeminjam);
                params.put("nim_peminjam", nimPeminjam);
                params.put("prodi_peminjam", prodiPeminjam);
                params.put("no_hp", noHp);
                params.put("sesi", sesi);
                params.put("jumlah_stik", jumlahStikStr);
                return params;
            }
        };

        // Add request to Volley Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    // Handle the result of the image selection
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
<<<<<<< HEAD
            Toast.makeText(this, "Gambar KTM berhasil dipilih!", Toast.LENGTH_SHORT).show();
=======
            // Display selected image path in EditText `etKtm`
            etKtm.setText(data.getData().toString());
            Toast.makeText(this, "KTM image selected successfully!", Toast.LENGTH_SHORT).show();
>>>>>>> 815c308d01620dd13c356003cd44ca7ae8471aa2
        }
    }
}
