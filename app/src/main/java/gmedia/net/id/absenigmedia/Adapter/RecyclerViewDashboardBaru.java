package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gmedia.net.id.absenigmedia.DetailNews;
import gmedia.net.id.absenigmedia.R;
import gmedia.net.id.absenigmedia.Model.CustomRecyclerViewDashboardBaru;

import java.util.ArrayList;

/**
 * Created by Bayu on 24/01/2018.
 */

public class RecyclerViewDashboardBaru extends RecyclerView.Adapter<RecyclerViewDashboardBaru.ViewHolder> {
    private ArrayList<CustomRecyclerViewDashboardBaru> rvData;
    private Context context;

    public RecyclerViewDashboardBaru(Context context, ArrayList<CustomRecyclerViewDashboardBaru> rvData) {
        this.context = context;
        this.rvData = rvData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tanggal, judul, berita;
        private RelativeLayout background;

        public ViewHolder(View itemView) {
            super(itemView);
            tanggal = itemView.findViewById(R.id.tanggalDashboardBaru);
            judul = itemView.findViewById(R.id.teks1DashboardBaru);
            berita = itemView.findViewById(R.id.teks2DashboardBaru);
            background = itemView.findViewById(R.id.onClickNews);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_dashboard_baru, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.tanggal.setText(rvData.get(position).getTanggal());
        holder.judul.setText(rvData.get(position).getJudul());
        holder.berita.setText(rvData.get(position).getBerita());
        CustomRecyclerViewDashboardBaru custom = rvData.get(position);
        final String id = custom.getId();
        final String gambar = custom.getGambar();
        holder.background.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DetailNews.class);
                intent.putExtra("id", id);
                intent.putExtra("gambar", gambar);
                ((Activity) context).startActivity(intent);
            }
        });
//        setAnimation(holder.itemView, position);
    }

    private void setAnimation(View viewToAnimate, int position) {
        /*Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.right_from_left
                    : R.anim.left_form_right);*/
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animasi);
        viewToAnimate.startAnimation(animation);
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }


}
