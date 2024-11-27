package baw.mobile.firebase_createreservation;

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
import androidx.lifecycle.ViewModelProvider;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddPeminjamanFragment extends Fragment {
    private ImageButton btnBack, btnMinus, btnPlus, btnUploadKtm;
    private TextView tvTitle, tvJumlahStik;
    private EditText etNamaPeminjam, etNimPeminjam, etProdiPeminjam, etNoHp;
    private Spinner spinnerSesi;
    private Button btnKirim;

    private int jumlahStik = 0; // Awal jumlah stik
    private static final int PICK_IMAGE_REQUEST = 1; // Kode untuk membuka galeri


    private DatabaseReference db;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_peminjaman, container, false);

        // Inisialisasi Firebase Database
        db = FirebaseDatabase.getInstance().getReference("reservasi");

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

        //Listener untuk button back
        btnBack.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), MainActivity.class);
            startActivity(intent);
            getActivity().finish(); // Menyelesaikan Activity saat ini
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
            Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        });

        // Listener untuk tombol kirim
        btnKirim.setOnClickListener(v -> saveData());

        return view;
    }
    //Menangani hasil pemilihan gambar (untuk KTM)
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Toast.makeText(getContext(), "Gambar KTM berhasil dipilih!", Toast.LENGTH_SHORT).show();
        }
    }

    private void saveData() {
        String nama = etNamaPeminjam.getText().toString().trim();
        String nim = etNimPeminjam.getText().toString().trim();
        String prodi = etProdiPeminjam.getText().toString().trim();
        String noHp = etNoHp.getText().toString().trim();
        int jumlahStik = Integer.parseInt(tvJumlahStik.getText().toString().trim());
        String sesi = (spinnerSesi.getSelectedItem() != null) ? spinnerSesi.getSelectedItem().toString() : "";

        if (nama.isEmpty() || nim.isEmpty() || prodi.isEmpty() || noHp.isEmpty()) {
            Toast.makeText(getContext(), "Semua field harus diisi!", Toast.LENGTH_SHORT).show();
            return;
        }

        String id = db.push().getKey(); // Generate ID unik
        Reservasi reservasi = new Reservasi(id, nama, nim, prodi, noHp, jumlahStik, sesi);

        db.child(id).setValue(reservasi)
                .addOnSuccessListener(aVoid -> {
                    Intent intent = new Intent(getActivity(), SuccessActivity.class);
                    startActivity(intent);
                    Toast.makeText(getContext(), "Data berhasil disimpan!", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    Toast.makeText(getContext(), "Gagal menyimpan data: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

}
