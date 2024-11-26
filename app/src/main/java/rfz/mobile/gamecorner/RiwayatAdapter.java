package rfz.mobile.gamecorner;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DatabaseReference;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class RiwayatAdapter extends RecyclerView.Adapter<RiwayatAdapter.RiwayatVH> {

    private final Context ctx;
    private List<Riwayat> dataset;
    private DatabaseReference appDb;

    public RiwayatAdapter(Context ctx, List<Riwayat> dataset) {
        this.ctx = ctx;
        this.dataset = dataset;
    }

    public void setAppDb(DatabaseReference appDb){
        this.appDb = appDb;
    }

    public class RiwayatVH extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView tvNama, tvSesi, tvOpsi, tvTanggal, tvAlat, tvJumlahAlat;
        private ImageView ivIcon;
        private Button btBatalkan;
        private View cvRiwayat, vGaris;
        private Riwayat riwayat;

        public RiwayatVH(@NonNull View itemView) {
            super(itemView);
            this.tvNama = itemView.findViewById(R.id.tvNama);
            this.tvSesi = itemView.findViewById(R.id.tvSesi);
            this.tvOpsi = itemView.findViewById(R.id.tvOpsi);
            this.tvTanggal = itemView.findViewById(R.id.tvTanggal);
            this.tvAlat = itemView.findViewById(R.id.tvAlat);
            this.tvJumlahAlat = itemView.findViewById(R.id.tvJumlahAlat);
            this.btBatalkan = itemView.findViewById(R.id.btBatalkan);
            this.vGaris = itemView.findViewById(R.id.vGaris);
            this.cvRiwayat = itemView.findViewById(R.id.cvRiwayat);
            this.ivIcon = itemView.findViewById(R.id.ivIcon);

            this.btBatalkan.setOnClickListener(this);
        }

        public void bind(Riwayat riwayat) {
            this.riwayat = riwayat;
            this.tvNama.setText(riwayat.getNama());
            this.tvOpsi.setText(riwayat.getOpsi());
            this.tvJumlahAlat.setText(riwayat.getJumlahAlat() + "x");
            this.tvSesi.setText(riwayat.getSesi());

            SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
            String formattedDate = sdf.format(riwayat.getTanggal());
            this.tvTanggal.setText(formattedDate);

            if (!"Sedang Berlangsung".equals(riwayat.getStatus())) {
                this.cvRiwayat.setBackgroundResource(R.drawable.card_riwayat_past);
                this.btBatalkan.setVisibility(View.GONE);
                this.ivIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.base_white)));
                this.ivIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.neutrals400)));
            } else {
                this.btBatalkan.setVisibility(View.VISIBLE);
                this.cvRiwayat.setBackgroundResource(R.drawable.card_riwayat);
                this.ivIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.primary)));
                this.ivIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.primary200)));
            }

            if ("Pump It Up".equals(riwayat.getOpsi())) {
                this.ivIcon.setImageResource(R.drawable.ic_pump);
            } else {
                this.ivIcon.setImageResource(R.drawable.ic_game);
            }

            this.cvRiwayat.setOnClickListener(this);
            if (riwayat.getJumlahAlat() == 0 || "Pump It Up".equals(riwayat.getOpsi())) {
                this.tvAlat.setVisibility(View.GONE);
                this.tvJumlahAlat.setVisibility(View.GONE);
                this.vGaris.setVisibility(View.GONE);
            } else {
                this.tvAlat.setVisibility(View.VISIBLE);
                this.tvJumlahAlat.setVisibility(View.VISIBLE);
                this.vGaris.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onClick(View view) {
            if (view.getId() == R.id.btBatalkan) {
                showBatalkanDialog();
            } else if (view.getId() == R.id.cvRiwayat) {
                Intent intent = new Intent(ctx, Reservasi.class);
                intent.putExtra("id", this.riwayat.getId());
                ctx.startActivity(intent);
            }
        }

        private void showBatalkanDialog() {
            AlertDialog.Builder builder = new AlertDialog.Builder(ctx);

            LayoutInflater inflater = LayoutInflater.from(ctx);
            View customView = inflater.inflate(R.layout.dialog_batalkan, null);
            builder.setView(customView);

            AlertDialog dialog = builder.create();
            dialog.setCancelable(false);
            dialog.show();

            Button btnYes = customView.findViewById(R.id.btnYes);
            Button btnNo = customView.findViewById(R.id.btnNo);

            btnYes.setOnClickListener(v -> {
                appDb.child(riwayat.getId()).removeValue()
                        .addOnSuccessListener(aVoid -> {
                            Toast.makeText(ctx, "Reservasi berhasil dibatalkan.", Toast.LENGTH_SHORT).show();
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(ctx, "Gagal membatalkan reservasi. Coba lagi nanti.", Toast.LENGTH_SHORT).show();
                        });
                dialog.dismiss();
            });

            btnNo.setOnClickListener(v -> dialog.dismiss());
        }

    }

    @NonNull
    @Override
    public RiwayatVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx).inflate(R.layout.item_daftar_riwayat, parent, false);
        return new RiwayatVH(rowView);
    }

    @Override
    public void onBindViewHolder(@NonNull RiwayatVH holder, int position) {
        Riwayat riwayat = this.dataset.get(position);
        holder.bind(riwayat);
    }

    @Override
    public int getItemCount() {
        return this.dataset.size();
    }
}
