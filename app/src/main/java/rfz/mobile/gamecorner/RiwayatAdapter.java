package rfz.mobile.gamecorner;

import android.content.Context;
import android.content.res.ColorStateList;
import android.text.TextUtils;
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

import java.util.List;

public class RiwayatAdapter extends RecyclerView.Adapter {

    private final Context ctx;
    private List<Riwayat> data;

    public RiwayatAdapter(Context ctx, List<Riwayat> data) {
        this.ctx = ctx;
        this.data = data;
    }

    public class RiwayatVH extends RecyclerView.ViewHolder {

        private final TextView tvNama;
        private final TextView tvSesi;
        private final TextView tvOpsi;
        private final TextView tvTanggal;
        private final TextView tvAlat;
        private final TextView tvJumlahAlat;
        private final ImageView ivIcon;
        private View cvRiwayat;
        public Button btBatalkan;
        public View vGaris;

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
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View rowView = LayoutInflater.from(this.ctx)
                .inflate(R.layout.rowview_daftar_riwayat, parent, false);
        RecyclerView.ViewHolder vh = new RiwayatVH(rowView);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Riwayat r = this.data.get(position);
        RiwayatVH vh = (RiwayatVH) holder;
        vh.tvNama.setText(r.nama);
        vh.tvSesi.setText(r.sesi);
        vh.tvOpsi.setText(r.opsi);
        vh.tvAlat.setText(r.alat);
        vh.tvTanggal.setText(r.tanggal);
        vh.tvJumlahAlat.setText(r.jumlahAlat);

        if (r.status.equals("Selesai")) {
            vh.cvRiwayat.setBackgroundResource(R.drawable.card_riwayat_past);
            vh.btBatalkan.setVisibility(View.GONE);
            vh.ivIcon.setBackgroundTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.base_white)));
            vh.ivIcon.setImageTintList(ColorStateList.valueOf(ContextCompat.getColor(ctx, R.color.neutrals400)));
        } else {
            vh.cvRiwayat.setBackgroundResource(R.drawable.card_riwayat);
        }

        if (r.opsi.equals("Pump It Up")) vh.ivIcon.setImageResource(R.drawable.ic_pump);
        else vh.ivIcon.setImageResource(R.drawable.ic_game);

        vh.cvRiwayat.setOnClickListener(v -> {
                Toast.makeText(ctx, "Reservasi atas nama " + r.status, Toast.LENGTH_SHORT).show();
        });

        if (TextUtils.isEmpty(r.alat)) {
            vh.tvAlat.setVisibility(View.GONE);
            vh.tvJumlahAlat.setVisibility(View.GONE);
            vh.vGaris.setVisibility(View.GONE);
        } else {
            vh.tvAlat.setVisibility(View.VISIBLE);
            vh.tvJumlahAlat.setVisibility(View.VISIBLE);
            vh.vGaris.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public int getItemCount() {
        return this.data.size();
    }

    public void updateData(List<Riwayat> newData) {
        this.data = newData;
        notifyDataSetChanged();
    }
}
