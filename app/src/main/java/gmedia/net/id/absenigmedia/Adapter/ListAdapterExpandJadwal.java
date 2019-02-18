package gmedia.net.id.absenigmedia.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import gmedia.net.id.absenigmedia.R;
import gmedia.net.id.absenigmedia.Model.CustomJadwal;
import gmedia.net.id.absenigmedia.Model.ChildJadwal;

import java.util.HashMap;
import java.util.List;

/**
 * Created by Bayu on 04/04/2018.
 */

public class ListAdapterExpandJadwal extends BaseExpandableListAdapter {
    private Context context;
    private List<CustomJadwal> jadwalParent;
    private HashMap<String, List<ChildJadwal>> childList;

    public ListAdapterExpandJadwal(Context context, List<CustomJadwal> jadwalParent, HashMap<String, List<ChildJadwal>> childList) {
        this.context = context;
        this.jadwalParent = jadwalParent;
        this.childList = childList;
    }

    private static class ViewHolder {
        private TextView minggu, tanggal, jumlah;
    }

    @Override
    public int getGroupCount() {
        return this.jadwalParent.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return (this.childList.get(this.jadwalParent.get(groupPosition).getTanggal()) != null) ? this.childList.get(this.jadwalParent.get(groupPosition).getTanggal()).size() : 0;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.jadwalParent.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childList.get(this.jadwalParent.get(groupPosition).getTanggal())
                .get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public int getGroupTypeCount() {
        return 2;
    }

    @Override
    public int getGroupType(int groupPosition) {
        int hasil = 0;
        if(groupPosition % 2 == 1){
            hasil = 0;
        }else{
            hasil = 1;
        }
        return hasil;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        convertView=null;
        CustomJadwal parent1 = (CustomJadwal) getGroup(groupPosition);
        ViewHolder holder = new ViewHolder();
        int tipeViewList = getGroupType(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.parent_listview_jadwal, null);
        }
        TextView tanggal = (TextView) convertView.findViewById(R.id.tanggaljadwal);
        TextView shift = (TextView) convertView.findViewById(R.id.shiftjadwal);
        TextView jamMasuk = (TextView)convertView.findViewById(R.id.jammasukjadwal);
        TextView jamKeluar = (TextView)convertView.findViewById(R.id.jamkeluarjadwal);
        tanggal.setText(parent1.getTanggal());
        shift.setText(parent1.getShift());
        jamMasuk.setText(parent1.getJammasuk());
        jamKeluar.setText(parent1.getJamkeluar());
//        shift.setTypeface(null, Typeface.BOLD);
        if (tipeViewList==0) {
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
        TextView texta = convertView.findViewById(R.id.shiftjadwal);
        String textb = texta.getText().toString();
        if (textb.contains("L")) {
            hariLibur.setTextColor(Color.parseColor("#FF0000"));
        } else {
            hariLibur.setTextColor(context.getResources().getColor(R.color.textbiasa));
        }
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ChildJadwal child = (ChildJadwal) getChild(groupPosition, childPosition);
//         ViewHolderChild holder = new ViewHolderChild();
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.child_listview_jadwal, null);
        }
        TextView keterangan = (TextView) convertView.findViewById(R.id.keteranganChild);
        keterangan.setTextColor(Color.parseColor("#FFFFFF"));
        keterangan.setText(child.getKeteranganJadwal());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }
}
