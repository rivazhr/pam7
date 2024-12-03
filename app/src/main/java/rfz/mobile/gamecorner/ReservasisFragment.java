package rfz.mobile.gamecorner;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReservasisFragment extends Fragment {

    public static final String FirebaseURL = "https://game-corner7-default-rtdb.asia-southeast1.firebasedatabase.app/";
    private RecyclerView rvRiwayat;
    private RiwayatAdapter riwayatAdapter;
    private List<Riwayat> dataset;
    private FirebaseDatabase db;
    private DatabaseReference appDb;
    private LinearLayout vEmpty;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_reservasis, container, false);

        vEmpty = v.findViewById(R.id.vEmpty);
        rvRiwayat = v.findViewById(R.id.rvRiwayat);
        dataset = new ArrayList<Riwayat>();
        riwayatAdapter = new RiwayatAdapter(getContext(), this.dataset);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRiwayat.setAdapter(this.riwayatAdapter);

        this.db = FirebaseDatabase.getInstance(FirebaseURL);
        this.appDb = this.db.getReference("game_corner7");
        this.riwayatAdapter.setAppDb(this.appDb);

        this.appDb.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        dataset.clear();
                        for (DataSnapshot s: snapshot.getChildren()){
                            Riwayat riwayat = s.getValue(Riwayat.class);
                            dataset.add(riwayat);
                        }
                        riwayatAdapter.notifyDataSetChanged();

                        if (dataset.isEmpty()) {
                            rvRiwayat.setVisibility(View.GONE);
                            vEmpty.setVisibility(View.VISIBLE);
                        } else {
                            rvRiwayat.setVisibility(View.VISIBLE);
                            vEmpty.setVisibility(View.GONE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Log.e("FirebaseError", "Database Error: " + error.getMessage());
                    }
                }
        );

        return v;
    }
}
