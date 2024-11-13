package rfz.mobile.gamecorner;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private RiwayatAdapter riwayatAdapter;
    private Button btBatalkan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_homepage);

        this.btBatalkan = this.findViewById(R.id.btBatalkan);
        this.btBatalkan.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
//        if(view.getId() == R.id.btBatalkan){
//            int pos = this.data.indexOf(view);
//            this.data.remove(pos);
//            this.kontakAdapter.notifyDataSetChanged();
//        }
    }
}