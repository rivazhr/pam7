package rfz.mobile.gamecorner;

import android.os.Bundle;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
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

        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;

            if (item.getItemId() == R.id.menuBeranda)
                selectedFragment = new HomepageFragment();
            else if (item.getItemId() == R.id.menuRiwayat)
                selectedFragment = new DaftarRiwayatFragment();
            else if (item.getItemId() == R.id.menuProfil)
                selectedFragment = new ProfileFragment();

            if (selectedFragment != null && currentFragment != selectedFragment) {
                loadFragment(selectedFragment);
            }

            return true;
        });
    }

    protected void loadFragment(Fragment fragment) {
        FragmentTransaction ft = fm.beginTransaction();

        if (currentFragment != null) {
            ft.hide(currentFragment);
        }

        if (!fragment.isAdded()) {
            ft.add(R.id.fragment_layout, fragment);
        } else {
            ft.show(fragment);
        }

        currentFragment = fragment;
        ft.commit();
    }
}
