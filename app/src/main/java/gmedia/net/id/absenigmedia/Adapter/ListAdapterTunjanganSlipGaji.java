package gmedia.net.id.absenigmedia.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.List;

import gmedia.net.id.absenigmedia.Model.ModelPotonganSlipGaji;
import gmedia.net.id.absenigmedia.Model.ModelTunjanganSlipGaji;
import gmedia.net.id.absenigmedia.R;

public class ListAdapterTunjanganSlipGaji extends ArrayAdapter {
	private Context context;
	private List<ModelTunjanganSlipGaji> list;

	public ListAdapterTunjanganSlipGaji(Context context, List<ModelTunjanganSlipGaji> list) {
		super(context, R.layout.view_lv_tunjangan_slip_gaji, list);
		this.context = context;
		this.list = list;
	}

	private static class ViewHolder {
		private TextView nama, nominal;
		private LinearLayout garisTotal;
	}

	@Override
	public int getCount() {
		return super.getCount();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = new ViewHolder();
		if (convertView == null) {
			LayoutInflater inflater = ((Activity) context).getLayoutInflater();
//            LayoutInflater inflater =(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            /*LayoutInflater inflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);*/
			convertView = inflater.inflate(R.layout.view_lv_tunjangan_slip_gaji, null);
			holder.nama = (TextView) convertView.findViewById(R.id.txtNamaTunjanganSlipGaji);
			holder.nominal = (TextView) convertView.findViewById(R.id.txtNominalTunjanganSlipGaji);
			holder.garisTotal = (LinearLayout) convertView.findViewById(R.id.garisTotalTunjangan);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		ModelTunjanganSlipGaji model = list.get(position);
		holder.nominal.setText(ChangeToRupiahFormat(model.getNominal()));
		int garisPosition = list.size() - 2;
		int lastPosition = list.size() - 1;
		if (position == garisPosition) {
			holder.garisTotal.setVisibility(View.VISIBLE);
		} else {
			holder.garisTotal.setVisibility(View.GONE);
		}
		if (position == lastPosition) {
			holder.nama.setText("");
		} else {
			holder.nama.setText(model.getNama());
		}
		return convertView;
	}

	public Double parseNullDouble(String s) {
		double result = 0;
		if (s != null && s.length() > 0) {
			try {
				result = Double.parseDouble(s);
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
		return result;
	}

	public String doubleToStringFull(Double number) {
		return String.format("%s", number).replace(",", ".");
	}

	public String ChangeToRupiahFormat(String number) {

		double value = parseNullDouble(number);

		NumberFormat format = NumberFormat.getCurrencyInstance();
		DecimalFormatSymbols symbols = ((DecimalFormat) format).getDecimalFormatSymbols();

		symbols.setCurrencySymbol("Rp ");
		((DecimalFormat) format).setDecimalFormatSymbols(symbols);
		format.setMaximumFractionDigits(0);

		String hasil = String.valueOf(format.format(value));

		return hasil;
	}
}
