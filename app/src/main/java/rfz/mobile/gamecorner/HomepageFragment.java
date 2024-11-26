package rfz.mobile.gamecorner;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

public class HomepageFragment extends Fragment implements View.OnClickListener {

    private SearchView searchView;
    private Button cardDaftarBt;
    private CardView fasilitas1;
    private CardView fasilitas2;
    private Button cardFasilitasBt;
    private Button cardFasilitas2Bt;
    private CardView daftarReservasi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homepage, container, false);

        fasilitas1 = v.findViewById(R.id.fasilitas1);
        fasilitas1.setOnClickListener(this);

        cardFasilitasBt = v.findViewById(R.id.cardFasilitasBt);
        cardFasilitasBt.setOnClickListener(this);

        fasilitas2 = v.findViewById(R.id.fasilitas2);
        fasilitas2.setOnClickListener(this);

        cardFasilitas2Bt = v.findViewById(R.id.cardFasilitas2Bt);
        cardFasilitas2Bt.setOnClickListener(this);

        daftarReservasi = v.findViewById(R.id.daftarReservasi);
        daftarReservasi.setOnClickListener(this);

        cardDaftarBt = v.findViewById(R.id.cardDaftarBt);
        cardDaftarBt.setOnClickListener(this);

        searchView = v.findViewById(R.id.searchView);
        searchView.clearFocus();

        return v;
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.cardDaftarBt || view.getId() == R.id.daftarReservasi) {
            MainActivity ma = (MainActivity) getActivity();
            if (ma != null) {
                ma.loadFragment(new ReservasisFragment());
                ma.bottomNavigationView.setSelectedItemId(R.id.menuRiwayat);
            }
        } else if (view.getId() == R.id.fasilitas1 || view.getId() == R.id.cardFasilitasBt) {
            Intent intent = new Intent(getContext(), RiwayatTambah.class);
            intent.putExtra("opsi", "TV PS");
            startActivity(intent);
        } else if (view.getId() == R.id.fasilitas2 || view.getId() == R.id.cardFasilitas2Bt) {
            Intent intent = new Intent(getContext(), RiwayatTambah.class);
            intent.putExtra("opsi", "Pump It Up");
            startActivity(intent);
        }
    }
}
