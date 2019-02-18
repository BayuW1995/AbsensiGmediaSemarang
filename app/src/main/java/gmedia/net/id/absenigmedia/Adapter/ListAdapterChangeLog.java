package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import gmedia.net.id.absenigmedia.R;
import gmedia.net.id.absenigmedia.Model.CustomChangeLog;

public class ListAdapterChangeLog extends ArrayAdapter {
    private Context context;
    private List<CustomChangeLog> changeLog;

    public ListAdapterChangeLog(Context context, List<CustomChangeLog> changeLog) {
        super(context, R.layout.lv_change_log, changeLog);
        this.context = context;
        this.changeLog = changeLog;
    }

    private static class ViewHolder {
        private TextView version, tanggal, isi;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        convertView = null;
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.lv_change_log, null);
            holder.version = convertView.findViewById(R.id.tv_Version);
            holder.tanggal = convertView.findViewById(R.id.tv_Tanggal);
            holder.isi = convertView.findViewById(R.id.tv_Isi);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LinearLayout background = convertView.findViewById(R.id.backgroundChangeLog);
        CustomChangeLog custom = changeLog.get(position);
        holder.version.setText(custom.getVersion());
        holder.tanggal.setText(custom.getTanggal());
        holder.isi.setText(custom.getIsi());
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.animasi);
        background.startAnimation(animation);
        return convertView;
    }
}
