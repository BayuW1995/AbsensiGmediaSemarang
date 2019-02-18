package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gmedia.net.id.absenigmedia.R;
import gmedia.net.id.absenigmedia.Model.CustomTotalTerlambat;

import java.util.List;

/**
 * Created by Bayu on 19/02/2018.
 */

public class ListAdapterTotalTerlambat extends ArrayAdapter{
    private Context context;
    private List<CustomTotalTerlambat> totalTerlambat;
    public ListAdapterTotalTerlambat(Context context, List<CustomTotalTerlambat>totalTerlambat) {
        super(context, R.layout.listview_totalterlambat, totalTerlambat);
        this.context=context;
        this.totalTerlambat=totalTerlambat;
    }
    private static class ViewHolder {
        private TextView tanggal, jammasuk, scanmasuk, telat;
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
        if(position % 2 == 1){
            hasil = 0;
        }else{
            hasil = 1;
        }
        return hasil;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ListAdapterTotalTerlambat.ViewHolder holder = new ListAdapterTotalTerlambat.ViewHolder();
        int tipeViewList = getItemViewType(position);
        if (convertView == null) {
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /*LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
            convertView = inflater.inflate(R.layout.listview_totalterlambat, null);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggaltotalterlambat);
            holder.jammasuk = (TextView) convertView.findViewById(R.id.jammasuktotalterlambat);
            holder.scanmasuk = (TextView) convertView.findViewById(R.id.scanmasuktotalterlambat);
            holder.telat = convertView.findViewById(R.id.telattotalterlambat);
            convertView.setTag(holder);
        }
        else {
            holder = (ListAdapterTotalTerlambat.ViewHolder) convertView.getTag();
        }
        final CustomTotalTerlambat absen = totalTerlambat.get(position);
        holder.tanggal.setText(absen.getTanggal());
        holder.jammasuk.setText(absen.getJammasuk());
        holder.scanmasuk.setText(absen.getScanmasuk());
        holder.telat.setText(absen.getTelat());
        if (tipeViewList==0) {
            RelativeLayout a = convertView.findViewById(R.id.layouttanggaltotalterlambat);
            RelativeLayout b = convertView.findViewById(R.id.layoutjammasuktotalterlambat);
            RelativeLayout c = convertView.findViewById(R.id.layoutscanmasuktotalterlambat);
            RelativeLayout d = convertView.findViewById(R.id.layoutjamtelattotalterlambat);
            a.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            b.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            c.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            d.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
        }
        TextView texta = convertView.findViewById(R.id.tanggaltotalterlambat);
        String textb = texta.getText().toString();
        if (textb.contains("Sab")) {
            texta.setTextColor(Color.parseColor("#FF0000"));
        } else if (textb.contains("Min")) {
            texta.setTextColor(Color.parseColor("#FF0000"));
        }else {
            texta.setTextColor(context.getResources().getColor(R.color.textbiasa));
        }
        return convertView;
    }
}
