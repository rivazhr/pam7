package rfz.mobile.gamecorner;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class DaftarRiwayat extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private RecyclerView rvRiwayat;
    private RiwayatAdapter riwayatAdapter;
    private Button btBatalkan;
    private List<Riwayat> data;

    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_daftar_riwayat);

        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.menuRiwayat);
        bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.menuBeranda) {
                startActivity(new Intent(this, Homepage.class));
                return true;
            } else if (item.getItemId() == R.id.menuRiwayat) {
                return true;
            }

            return false;
        });

        rvRiwayat = findViewById(R.id.rvRiwayat);
        data = new ArrayList<>();
        riwayatAdapter = new RiwayatAdapter(this, data);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(this));
        rvRiwayat.setAdapter(riwayatAdapter);

        fetchData();
    }

    private void fetchData() {
        new Thread(() -> {
            try {
                URL url = new URL("http://10.0.2.2/gamecorner_api/get_reservasi.php");
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setConnectTimeout(5000);
                urlConnection.setReadTimeout(5000);
                urlConnection.setDoOutput(true);

                JSONObject jsonData = new JSONObject();
                jsonData.put("data", data);
                urlConnection.getOutputStream().write(jsonData.toString().getBytes("utf-8"));

                BufferedReader reader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null)
                    response.append(line);

                Gson gson = new Gson();
                List<Riwayat> riwayatList = gson.fromJson(response.toString(), new TypeToken<List<Riwayat>>(){}.getType());

                runOnUiThread(() -> riwayatAdapter.updateData(riwayatList));
            } catch (Exception e) {
                Log.e("RequestError", "Error during request", e);
            }
        }).start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateIcons(R.id.menuRiwayat);
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
                // Set default icon
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

}