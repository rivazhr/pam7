package rfz.mobile.gamecorner;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SearchView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.res.ResourcesCompat;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Homepage extends AppCompatActivity implements View.OnClickListener {

    private BottomNavigationView bottomNavigationView;
    private SearchView searchView;
    private Button cardDaftarBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menuBeranda);
        bottomNavigationView.setOnItemSelectedListener(item -> {

            if (item.getItemId() == R.id.menuBeranda) {
                return true;
            } else if (item.getItemId() == R.id.menuRiwayat) {
                startActivity(new Intent(this, DaftarRiwayat.class));
                return true;
            }

            return false;
        });

        cardDaftarBt = findViewById(R.id.cardDaftarBt);
        cardDaftarBt.setOnClickListener(this);

        searchView = findViewById(R.id.searchView);
        searchView.clearFocus();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateIcons(R.id.menuBeranda);
    }

    private void updateIcons(int selectedItemId) {
        for (int i = 0; i < bottomNavigationView.getMenu().size(); i++) {
            MenuItem item = bottomNavigationView.getMenu().getItem(i);
            if (item.getItemId() == selectedItemId) {
                if (item.getItemId() == R.id.menuBeranda) {
                    item.setIcon(R.drawable.ic_home_selected);
                } else if (item.getItemId() == R.id.menuRiwayat) {
                    item.setIcon(R.drawable.ic_history_selected);
                } else if (item.getItemId() == R.id.menuProfil) {
                    item.setIcon(R.drawable.ic_profile_selected);
                }
            } else {
                if (item.getItemId() == R.id.menuBeranda) {
                    item.setIcon(R.drawable.ic_home);
                } else if (item.getItemId() == R.id.menuRiwayat) {
                    item.setIcon(R.drawable.ic_history);
                } else if (item.getItemId() == R.id.menuProfil) {
                    item.setIcon(R.drawable.ic_profile);
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        startActivity(new Intent(this, DaftarRiwayat.class));

    }
}
