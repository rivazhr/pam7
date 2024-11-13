package rfz.mobile.gamecorner;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SearchView;

public class HomepageFragment extends Fragment implements View.OnClickListener {

    private SearchView searchView;
    private Button cardDaftarBt;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_homepage, container, false);


        cardDaftarBt = v.findViewById(R.id.cardDaftarBt);
        cardDaftarBt.setOnClickListener(this);

        searchView = v.findViewById(R.id.searchView);
        searchView.clearFocus();

        return v;
    }

    @Override
    public void onClick(View view) {
        MainActivity ma = (MainActivity) getActivity();
        if (ma != null)
            ma.loadFragment(new DaftarRiwayatFragment());
    }
}