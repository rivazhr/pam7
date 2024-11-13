package rfz.mobile.gamecorner;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class DaftarRiwayatFragment extends Fragment {

    private RecyclerView rvRiwayat;
    private RiwayatAdapter riwayatAdapter;
    private List<Riwayat> data;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_daftar_riwayat, container, false);

        rvRiwayat = v.findViewById(R.id.rvRiwayat);
        data = new ArrayList<>();
        riwayatAdapter = new RiwayatAdapter(getContext(), data);
        rvRiwayat.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRiwayat.setAdapter(riwayatAdapter);

        fetchData();

        return v;
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

                getActivity().runOnUiThread(() -> riwayatAdapter.updateData(riwayatList));
            } catch (Exception e) {
                Log.e("RequestError", "Error during request", e);
            }
        }).start();
    }

}