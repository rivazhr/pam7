package baw.mobile.gamecorner_tambah;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.HashMap;
import java.util.Map;

public class AddPeminjamanFragment extends Fragment {

    private ImageButton btnBack, btnMinus, btnPlus, btnUploadKtm;
    private TextView tvTitle, tvJumlahStik;
    private EditText etNamaPeminjam, etNimPeminjam, etProdiPeminjam, etNoHp;
    private Spinner spinnerSesi;
    private Button btnKirim;

    private int jumlahStik = 0; // Awal jumlah stik
    private static final int PICK_IMAGE_REQUEST = 1; // Kode untuk membuka galeri

    // URL API
    private static final String URL_API = "http://192.168.56.1/restApiAsp/Reservation.php";

    private Gson gson = new Gson(); // GSON instance for parsing

    public AddPeminjamanFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_peminjaman, container, false);

        // Inisialisasi UI
        btnMinus = view.findViewById(R.id.btnMinus);
        btnPlus = view.findViewById(R.id.btnPlus);
        tvJumlahStik = view.findViewById(R.id.tvJumlahStik);
        btnUploadKtm = view.findViewById(R.id.btnUploadKtm);
        etNamaPeminjam = view.findViewById(R.id.etNamaPeminjam);
        etNimPeminjam = view.findViewById(R.id.etNimPeminjam);
        etProdiPeminjam = view.findViewById(R.id.etProdiPeminjam);
        etNoHp = view.findViewById(R.id.etNoHp);
        spinnerSesi = view.findViewById(R.id.spinnerSesi);
        btnKirim = view.findViewById(R.id.btnKirim);
        tvTitle = view.findViewById(R.id.tvTitle);
        btnBack = view.findViewById(R.id.btnBack);

        btnBack.setOnClickListener(v -> {
            // Intent untuk kembali ke MainActivity
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish();  // Menyelesaikan Activity saat ini
        });

        // Mendapatkan data dari bundle (misalnya nama fasilitas)
        if (getArguments() != null) {
            String fasilitas = getArguments().getString("fasilitas");
            tvTitle.setText("Buat Reservasi - " + fasilitas);
        }

        // Mengatur Spinner Sesi
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(),
                R.array.sesi_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerSesi.setAdapter(adapter);

        // Listener untuk mengurangi jumlah stik
        btnMinus.setOnClickListener(v -> {
            if (jumlahStik > 0) {
                jumlahStik--;
                tvJumlahStik.setText(String.valueOf(jumlahStik));
            }
        });

        // Listener untuk menambah jumlah stik
        btnPlus.setOnClickListener(v -> {
            jumlahStik++;
            tvJumlahStik.setText(String.valueOf(jumlahStik));
        });

        // Listener untuk unggah KTM
        btnUploadKtm.setOnClickListener(v -> {
            // Buka galeri untuk memilih gambar
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Mengatur listener untuk tombol kirim
        btnKirim.setOnClickListener(v -> kirimDataPeminjaman());

        return view;
    }

    // Bagian dalam fungsi `kirimDataPeminjaman`
    private void kirimDataPeminjaman() {
        String namaPeminjam = etNamaPeminjam.getText().toString();
        String nimPeminjam = etNimPeminjam.getText().toString();
        String prodiPeminjam = etProdiPeminjam.getText().toString();
        String noHp = etNoHp.getText().toString();
        String sesi = spinnerSesi.getSelectedItem().toString();
        String jumlahStikStr = String.valueOf(jumlahStik);

        // Membuat request dengan Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_API,
                response -> {
                    JsonObject jsonObject = gson.fromJson(response, JsonObject.class);
                    String message = jsonObject.get("message").getAsString();

                    // Menampilkan pesan server di UI utama
                    getActivity().runOnUiThread(() -> {
                        Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        if (message.equals("Data disimpan")) {
                            //Navigasi ke halaman Success jika data berhasil disimpan
                            getActivity().startActivity(new Intent(getActivity(), SuccessActivity.class));
                        }
                    });
                },
                error -> getActivity().runOnUiThread(() -> {
                    Toast.makeText(getContext(), "Failed to send data: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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

        // Menambahkan request ke queue Volley
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    // Menangani hasil pemilihan gambar (untuk KTM)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Toast.makeText(getContext(), "Gambar KTM berhasil dipilih!", Toast.LENGTH_SHORT).show();
        }
    }
}