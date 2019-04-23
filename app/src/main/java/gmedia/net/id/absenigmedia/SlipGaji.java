package gmedia.net.id.absenigmedia;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterPotonganSlipGaji;
import gmedia.net.id.absenigmedia.Adapter.ListAdapterTunjanganSlipGaji;
import gmedia.net.id.absenigmedia.Model.ModelPotonganSlipGaji;
import gmedia.net.id.absenigmedia.Model.ModelTunjanganSlipGaji;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

public class SlipGaji extends Fragment {
	private Context context;
	private View view;
	private TextView gajiPokok;
	private ListView lvPotongan, lvTunjangan;
	private List<ModelPotonganSlipGaji> listPotonganSlipGaji;
	private List<ModelTunjanganSlipGaji> listTunjanganSlipGaji;
	private SessionManager session;
	private ListAdapterPotonganSlipGaji adapterPotonganSlipGaji;
	private ListAdapterTunjanganSlipGaji adapterTunjanganSlipGaji;
	public static double gajiUtama = 0, totalPotongan = 0, totalTunjangan = 0, totalSemuaPotongan = 0;
	private String totalanPotonganSlipGaji = "";
	private TextView txtTotalPotonganSlipGaji;
	private EditText inputPIN;
	private Boolean klikToVisiblePIN = true;

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
		view = inflater.inflate(R.layout.slip_gaji, viewGroup, false);
		context = getContext();
		session = new SessionManager(context);
		initUI();
		initAction();
		return view;
	}

	private void initUI() {
		gajiPokok = (TextView) view.findViewById(R.id.nomGajiPokokSlipGaji);
		lvPotongan = (ListView) view.findViewById(R.id.lv_potongan_slip_gaji);
		lvTunjangan = (ListView) view.findViewById(R.id.lv_tunjangan_slip_gaji);
		txtTotalPotonganSlipGaji = (TextView) view.findViewById(R.id.totalPotonganSlipGaji);
	}

	private void initAction() {
		preparePopupReInputPIN();
	}

	private void preparePopupReInputPIN() {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(R.layout.popup_reinput_pin);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCanceledOnTouchOutside(false);

		inputPIN = (EditText) dialog.findViewById(R.id.reinputPIN);
		inputPIN.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				final Handler handler = new Handler();
				final Runnable runnable = new Runnable() {
					@Override
					public void run() {
						if (inputPIN.getText().toString().length() < 6) {
							inputPIN.setError("PIN harus 6 karakter");
						}
					}
				};
				handler.postDelayed(runnable, 2000);
			}
		});

		final ImageView visibleInputPIN = (ImageView) dialog.findViewById(R.id.visibleReInputPin);
		visibleInputPIN.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (klikToVisiblePIN) {
					visibleInputPIN.setImageDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.visible));
					inputPIN.setInputType(InputType.TYPE_CLASS_NUMBER);
					klikToVisiblePIN = false;
				} else {
					visibleInputPIN.setImageDrawable(getResources().getDrawable(gmedia.net.id.absenigmedia.R.drawable.invisible));
					inputPIN.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_VARIATION_PASSWORD);
					inputPIN.setTransformationMethod(PasswordTransformationMethod.getInstance());
					klikToVisiblePIN = true;
				}
			}
		});

		RelativeLayout btnOK = (RelativeLayout) dialog.findViewById(R.id.tombolProsesReInputPIN);
		btnOK.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (inputPIN.getText().toString().isEmpty()) {
					inputPIN.setError("PIN harap diisi");
					inputPIN.requestFocus();
					return;
				} else if (inputPIN.getText().toString().length() != 6) {
					inputPIN.setError("PIN harus 6 karakter");
					inputPIN.requestFocus();
					return;
				} else {
					inputPIN.setError(null);
				}
				prepareDataPotonganSlipGaji();
				dialog.dismiss();
			}
		});

		RelativeLayout btnSkip = (RelativeLayout) dialog.findViewById(R.id.tombolCancelReInputPIN);
		btnSkip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				MainActivity.posisi = true;
				Intent intent = new Intent(context, MainActivity.class);
				startActivity(intent);
				((Activity) context).finish();
			}
		});

		dialog.show();
	}

	private void prepareDataPotonganSlipGaji() {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		final JSONObject jBody = new JSONObject();
		try {
			jBody.put("pin", inputPIN.getText().toString());
			Log.d("pin anda", inputPIN.getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlSlipGaji, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				listPotonganSlipGaji = new ArrayList<>();
				listTunjanganSlipGaji = new ArrayList<>();
				try {
					JSONObject object = new JSONObject(result);
					String status = object.getJSONObject("metadata").getString("status");
					String message = object.getJSONObject("metadata").getString("message");
					if (status.equals("1")) {
						JSONObject response = object.getJSONObject("response");
						gajiUtama = parseNullDouble(response.getString("value"));
						gajiPokok.setText(ChangeToRupiahFormat(response.getString("value")));
						JSONObject bpjs = response.getJSONObject("bpjs");
						JSONArray potongan = bpjs.getJSONArray("potongan");
						for (int i = 0; i < potongan.length(); i++) {
							JSONObject isiPotongan = potongan.getJSONObject(i);
							listPotonganSlipGaji.add(new ModelPotonganSlipGaji(
									isiPotongan.getString("item"),
									isiPotongan.getString("value")
							));
							if (i == listPotonganSlipGaji.size() - 1) {
								ModelPotonganSlipGaji modelPotonganSlipGaji = listPotonganSlipGaji.get(i);
								totalPotongan = parseNullDouble(modelPotonganSlipGaji.getNominal());
							}
						}
						lvPotongan.setAdapter(null);
						adapterPotonganSlipGaji = new ListAdapterPotonganSlipGaji(context, listPotonganSlipGaji);
						lvPotongan.setAdapter(adapterPotonganSlipGaji);
						JSONArray tunjangan = bpjs.getJSONArray("tunjangan");
						for (int i = 0; i < tunjangan.length(); i++) {
							JSONObject isiTunjangan = tunjangan.getJSONObject(i);
							listTunjanganSlipGaji.add(new ModelTunjanganSlipGaji(
									isiTunjangan.getString("item"),
									isiTunjangan.getString("value")
							));
							if (i == listTunjanganSlipGaji.size() - 1) {
								ModelTunjanganSlipGaji modelTunjanganSlipGaji = listTunjanganSlipGaji.get(i);
								totalTunjangan = parseNullDouble(modelTunjanganSlipGaji.getNominal());
							}
						}
						lvTunjangan.setAdapter(null);
						adapterTunjanganSlipGaji = new ListAdapterTunjanganSlipGaji(context, listTunjanganSlipGaji);
						lvTunjangan.setAdapter(adapterTunjanganSlipGaji);
						totalSemuaPotongan = gajiUtama - (totalPotongan + totalTunjangan);
						totalanPotonganSlipGaji = doubleToStringFull(totalSemuaPotongan);
						txtTotalPotonganSlipGaji.setText(ChangeToRupiahFormat(String.valueOf(totalanPotonganSlipGaji)));
//						WrapContentListView.setListViewHeightBasedOnChildren(lvPotongan);
						dialog.dismiss();
//						prepareDataTunjanganSlipGaji();
					} else {
						Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
						dialog.dismiss();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String result) {
				dialog.dismiss();
				Toast.makeText(context, "terjadi kesalahan di potongan asuransi", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private void prepareDataTunjanganSlipGaji() {
		final Dialog dialog = new Dialog(context);
		dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
		dialog.setCanceledOnTouchOutside(false);
		dialog.show();
		final JSONObject jBody = new JSONObject();
		try {
			jBody.put("pin", inputPIN.getText().toString());
			Log.d("Pin", inputPIN.getText().toString());
		} catch (JSONException e) {
			e.printStackTrace();
		}
		ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlSlipGaji, "", "", 0, new ApiVolley.VolleyCallback() {
			@Override
			public void onSuccess(String result) {
				dialog.dismiss();
				listTunjanganSlipGaji = new ArrayList<>();
				try {
					JSONObject object = new JSONObject(result);
					String status = object.getJSONObject("metadata").getString("status");
					String message = object.getJSONObject("metadata").getString("message");
					if (status.equals("1")) {
						JSONObject response = object.getJSONObject("response");
						JSONObject bpjs = response.getJSONObject("bpjs");
						JSONArray tunjangan = bpjs.getJSONArray("tunjangan");
						for (int i = 0; i < tunjangan.length(); i++) {
							JSONObject isiTunjangan = tunjangan.getJSONObject(i);
							listTunjanganSlipGaji.add(new ModelTunjanganSlipGaji(
									isiTunjangan.getString("item"),
									isiTunjangan.getString("value")
							));
							if (i == listTunjanganSlipGaji.size() - 1) {
								ModelTunjanganSlipGaji modelTunjanganSlipGaji = listTunjanganSlipGaji.get(i);
								totalTunjangan = parseNullDouble(modelTunjanganSlipGaji.getNominal());
							}
						}
						lvTunjangan.setAdapter(null);
						adapterTunjanganSlipGaji = new ListAdapterTunjanganSlipGaji(context, listTunjanganSlipGaji);
						lvTunjangan.setAdapter(adapterTunjanganSlipGaji);
						totalSemuaPotongan = gajiUtama - (totalPotongan + totalTunjangan);
						totalanPotonganSlipGaji = doubleToStringFull(totalSemuaPotongan);
						txtTotalPotonganSlipGaji.setText(ChangeToRupiahFormat(String.valueOf(totalanPotonganSlipGaji)));
//						WrapContentListView.setListViewHeightBasedOnChildren(lvPotongan);
					} else {
						Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}

			@Override
			public void onError(String result) {
				dialog.dismiss();
				Toast.makeText(context, "terjadi kesalahan di potongan asuransi", Toast.LENGTH_SHORT).show();
			}
		});
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
