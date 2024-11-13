package rfz.mobile.gamecorner;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    protected BottomNavigationView bottomNavigationView;
    private FragmentManager fm;
    private Fragment currentFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        fm = getSupportFragmentManager();

        currentFragment = new HomepageFragment();
        loadFragment(new HomepageFragment());

        bottomNavigationView.setOnNavigationItemSelectedListener(this);
    }

    protected void loadFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.fragment_layout, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;
        if (item.getItemId() == R.id.menuBeranda)
            selectedFragment = new HomepageFragment();
        else if (item.getItemId() == R.id.menuRiwayat)
            selectedFragment = new DaftarRiwayatFragment();
        else if (item.getItemId() == R.id.menuProfil)
            selectedFragment = new ProfileFragment();

        if (selectedFragment != null && currentFragment != selectedFragment)
            loadFragment(selectedFragment);

        return true;
    }
}
