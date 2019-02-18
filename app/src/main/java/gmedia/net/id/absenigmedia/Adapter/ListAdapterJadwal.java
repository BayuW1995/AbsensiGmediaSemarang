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
import gmedia.net.id.absenigmedia.Model.CustomJadwal;

import java.util.List;

/**
 * Created by Bayu on 28/02/2018.
 */

public class ListAdapterJadwal extends ArrayAdapter {
    private Context context;
    private List<CustomJadwal> jadwal;

    public ListAdapterJadwal(Context context, List<CustomJadwal> jadwal) {
        super(context, R.layout.parent_listview_jadwal, jadwal);
        this.context = context;
        this.jadwal = jadwal;
    }

    private static class ViewHolder {
        private TextView tanggal, shift, jammasuk, jamkeluar;
        private String flag_libur;
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
            convertView = inflater.inflate(R.layout.parent_listview_jadwal, null);
            holder.tanggal = (TextView) convertView.findViewById(R.id.tanggaljadwal);
            holder.shift = (TextView) convertView.findViewById(R.id.shiftjadwal);
            holder.jammasuk = (TextView) convertView.findViewById(R.id.jammasukjadwal);
            holder.jamkeluar = convertView.findViewById(R.id.jamkeluarjadwal);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        final CustomJadwal absen = jadwal.get(position);
        holder.tanggal.setText(absen.getTanggal());
        holder.shift.setText(absen.getShift());
        holder.jammasuk.setText(absen.getJammasuk());
        holder.jamkeluar.setText(absen.getJamkeluar());
        holder.flag_libur = absen.getFlag_libur();
        if (tipeViewList == 0) {
            RelativeLayout a = convertView.findViewById(R.id.layouttanggaljadwal);
            RelativeLayout b = convertView.findViewById(R.id.layoutshiftjadwal);
            RelativeLayout c = convertView.findViewById(R.id.layoutjammasukjadwal);
            RelativeLayout d = convertView.findViewById(R.id.layoutjamkeluarjadwal);
            a.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            b.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            c.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
            d.setBackgroundColor(Color.parseColor("#FFE5E6E8"));
        }
        TextView hariLibur = convertView.findViewById(R.id.tanggaljadwal);
        TextView texta = convertView.findViewById(R.id.jammasukjadwal);
        TextView textb = convertView.findViewById(R.id.jamkeluarjadwal);
        if (holder.flag_libur.equals("1")) {
            hariLibur.setTextColor(Color.parseColor("#FF0000"));
        } else {
            hariLibur.setTextColor(context.getResources().getColor(R.color.textbiasa));
        }
        /*if (texta.getText().toString().equals("00:00:00") && textb.getText().toString().equals("00:00:00")) {
            hariLibur.setTextColor(Color.parseColor("#FF0000"));
        } else {
            hariLibur.setTextColor(context.getResources().getColor(R.color.textbiasa));
        }*/
        return convertView;
    }
}
