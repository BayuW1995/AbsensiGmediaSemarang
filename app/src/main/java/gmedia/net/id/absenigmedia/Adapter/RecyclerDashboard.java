package gmedia.net.id.absenigmedia.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import gmedia.net.id.absenigmedia.Model.CustomDashboard;
import gmedia.net.id.absenigmedia.R;

import java.util.ArrayList;

/**
 * Created by Bayu on 03/01/2018.
 */

public class RecyclerDashboard extends RecyclerView.Adapter<RecyclerDashboard.ViewHolder> {
    private ArrayList<CustomDashboard> rvData;
    private Context context;

    public RecyclerDashboard(Context context, ArrayList<CustomDashboard> rvData) {
        this.context = context;
        this.rvData = rvData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView judul, hari;

        public ViewHolder(View itemView) {
            super(itemView);
            judul = itemView.findViewById(R.id.textsisacuti);
            hari = itemView.findViewById(R.id.harirecyclerdashboard);
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_dashboard, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onViewDetachedFromWindow(ViewHolder holder) {
        super.onViewDetachedFromWindow(holder);
        holder.itemView.clearAnimation();
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.judul.setText(rvData.get(position).getJudul());
        holder.hari.setText(rvData.get(position).getHari());
        setAnimation(holder.itemView, position);

    }
    private void setAnimation(View viewToAnimate, int position) {
        /*Animation animation = AnimationUtils.loadAnimation(context, (position > lastPosition) ? R.anim.right_from_left
                : R.anim.left_form_right);*/
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animasi);
        viewToAnimate.startAnimation(animation);
//        lastPosition = position;
    }

    @Override
    public int getItemCount() {
        return rvData.size();
    }


}
