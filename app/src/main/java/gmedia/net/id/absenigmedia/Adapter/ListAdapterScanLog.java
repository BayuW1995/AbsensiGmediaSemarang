package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import gmedia.net.id.absenigmedia.R;
import gmedia.net.id.absenigmedia.Model.CustomScanLog;

public class ListAdapterScanLog extends ArrayAdapter {
    private Context context;
    private List<CustomScanLog> scanLog;

    public ListAdapterScanLog(Context context, List<CustomScanLog> scanLog) {
        super(context, R.layout.listview_scan_log, scanLog);
        this.context = context;
        this.scanLog = scanLog;
    }
    private static class ViewHolder {
        private TextView tanggal, jam, type;
    }

    @Override
    public int getCount() {
        return super.getCount();
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {

        int hasil = 0;
        if (position % 2 == 1) {
            hasil = 0;
        } else {
            hasil = 1;
        }
        return hasil;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder = new ViewHolder();
        int tipeViewList = getItemViewType(position);
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /*LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
            convertView = inflater.inflate(R.layout.listview_scan_log, null);
//            holder.NIP = (TextView) convertView.findViewById(R.id.NIKScanLog);
//            holder.nama = (TextView) convertView.findViewById(R.id.namaScanLog);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggalScanLog);
            holder.jam = convertView.findViewById(R.id.jamScanLog);
            holder.type = convertView.findViewById(R.id.typeScanLog);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        CustomScanLog custom = scanLog.get(position);
//        holder.NIP.setText(custom.getNIK());
//        holder.nama.setText(custom.getNama());
        holder.tanggal.setText(custom.getTanggal());
        holder.jam.setText(custom.getJam());
        holder.type.setText(custom.getType());
        return convertView;
    }
}
