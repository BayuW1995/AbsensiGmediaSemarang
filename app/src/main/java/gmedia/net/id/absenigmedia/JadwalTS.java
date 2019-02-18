package gmedia.net.id.absenigmedia;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Calendar;
import java.util.Date;

public class JadwalTS extends Fragment {
	private Spinner menuBulan, menuTahun;
	//    private EditText menuTahun;
	private RelativeLayout download;
	private String posisiMenuBulan, posisiMenuTahun;
	private Context context;
	private ProgressDialog progressDialog;
	private SessionManager session;

	public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
		final View view = inflater.inflate(R.layout.jadwal_ts, viewGroup, false);
		context = getContext();
		session = new SessionManager(context);
		download = (RelativeLayout) view.findViewById(R.id.downloadPDFJadwalTS);
		String[] bulan = new String[]
				{
						"Pilih Bulan : ",
						"Januari",
						"Februari",
						"Maret",
						"April",
						"Mei",
						"Juni",
						"Juli",
						"Agustus",
						"September",
						"Oktober",
						"November",
						"Desember"
				};
		String[] tahun = new String[]
				{
						"" + String.valueOf(Calendar.getInstance().get(Calendar.YEAR) - 1),
						"" + String.valueOf(Calendar.getInstance().get(Calendar.YEAR)),
				};
//        android.R.layout.simple_spinner_item
		menuBulan = (Spinner) view.findViewById(R.id.menuBulan);
		menuTahun = (Spinner) view.findViewById(R.id.menuTahun);
		ArrayAdapter<String> spinnerAdapterBulan = new ArrayAdapter<String>(context, R.layout.spinner_items, bulan) {
			@Override
			public boolean isEnabled(int position) {
				if (position == 0) {
					// Disable the first item from Spinner
					// First item will be use for hint
					return false;
				} else {
					return true;
				}
			}

			@Override
			public View getDropDownView(int position, View convertView,
										ViewGroup parent) {
				View view = super.getDropDownView(position, convertView, parent);
				TextView tv = (TextView) view;
				if (position == 0) {
					// Set the hint text color gray
					tv.setTextColor(Color.BLACK);
				} else {
					tv.setTextColor(Color.BLACK);
				}
				return view;
			}
		};
		spinnerAdapterBulan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		menuBulan.setAdapter(spinnerAdapterBulan);
		menuBulan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				posisiMenuBulan = String.valueOf(menuBulan.getSelectedItemPosition());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
		ArrayAdapter<String> spinnerAdapterTahun = new ArrayAdapter<String>(context, R.layout.spinner_items, tahun) {
			@Override
			public boolean isEnabled(int position) {
				if (position == 0) {
					// Disable the first item from Spinner
					// First item will be use for hint
					return true;
				} else {
					return true;
				}
			}

			@Override
			public View getDropDownView(int position, View convertView,
										ViewGroup parent) {
				View view = super.getDropDownView(position, convertView, parent);
				TextView tv = (TextView) view;
				if (position == 0) {
					// Set the hint text color gray
					tv.setTextColor(Color.BLACK);
				} else {
					tv.setTextColor(Color.BLACK);
				}
				return view;
			}
		};
		spinnerAdapterTahun.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		menuTahun.setAdapter(spinnerAdapterTahun);
		menuTahun.setSelection(1);
		menuTahun.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				posisiMenuTahun = String.valueOf(menuTahun.getSelectedItem().toString());
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {

			}
		});
//        menuTahun.setText(String.valueOf(Calendar.getInstance().get(Calendar.YEAR)-1));
		download.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (posisiMenuBulan.equals("0")) {
					Toast.makeText(context, "Silahkan Pilih Bulan Terlebih Dahulu", Toast.LENGTH_LONG).show();
					return;
				}/* else if (posisiMenuTahun.equals("0")) {
                    Toast.makeText(context, "Silahkan Pilih Tahun Terlebih Dahulu", Toast.LENGTH_LONG).show();
                    return;
                }*/
				String link = URL.urlDownloadPDFJadwalTS + "bulan=" + posisiMenuBulan + "&tahun=" + posisiMenuTahun + "&nip=" + session.getNip();
				new DownloadFileFromURL().execute(link, "JadwalTS.pdf");
			}
		});
		return view;
	}

	private class DownloadFileFromURL extends AsyncTask<String, String, String> {
		String extension;
		boolean success;
		String fileName;
		String donwloadedFilePath;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			showDialog();
		}

		@RequiresApi(api = Build.VERSION_CODES.KITKAT)
		@Override
		protected String doInBackground(String... f_url) {
			int count;
			try {
				java.net.URL url = new java.net.URL(f_url[0]);
				URLConnection conection = url.openConnection();
				conection.connect();

				// this will be useful so that you can show a tipical 0-100% progress bar
				int lenghtOfFile = conection.getContentLength();

				// download the file
				extension = f_url[0].substring(f_url[0].lastIndexOf("."));
				File vidDir = new File(Environment.getExternalStoragePublicDirectory
						(Environment.DIRECTORY_DOCUMENTS) + File.separator + "Jadwal TS");
				vidDir.mkdirs();

				// create unique identifier
				Date date = new Date();
				// create file name
				//fileName = "mutasi_" + date.getYear()+date.getMonth()+date.getDate()+date.getHours()+date.getMinutes()+date.getSeconds()+extension;
				fileName = f_url[1];

				File fileDownload = new File(vidDir.getAbsolutePath(), fileName);
				donwloadedFilePath = fileDownload.getAbsolutePath();
				success = false;
				InputStream input = new BufferedInputStream(url.openStream());
				// Output stream
				OutputStream output = new FileOutputStream(fileDownload);
				byte data[] = new byte[1024];
				long total = 0;
				while ((count = input.read(data)) != -1) {
					total += count;
					// publishing the progress....
					// After this onProgressUpdate will be called
					publishProgress("" + (int) ((total * 100) / lenghtOfFile));
					// writing data to file
					output.write(data, 0, count);
					success = true;
				}

				// flushing output
				output.flush();

				// closing streams
				output.close();
				input.close();

			} catch (Exception e) {
				Log.e("Error: ", e.getMessage());
			}
			return null;
		}

		protected void onProgressUpdate(String... progress) {
			// setting progress percentage
			progressDialog.setProgress(Integer.parseInt(progress[0]));
		}

		/**
		 * After completing background task
		 * Dismiss the progress dialog
		 **/
		@Override
		protected void onPostExecute(String file_url) {

			dismissDialog();

			//String downloadedPath = "file://" + Environment.getExternalStorageDirectory().toString() + "/downloadedfile."+extension;

			showFinishDialog(success);
		}

		private void showDialog() {
			progressDialog = new ProgressDialog(context);
			progressDialog.setMessage("Downloading file. Please wait...");
			progressDialog.setIndeterminate(false);
			progressDialog.setMax(100);
			progressDialog.setCancelable(false);
			progressDialog.setCanceledOnTouchOutside(false);
			progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			progressDialog.show();
		}

		private void dismissDialog() {
			progressDialog.dismiss();
		}

		private void showFinishDialog(final boolean downloadSuccess) {
			String title, message;
			if (downloadSuccess) {

				title = "Download selesai";
				message = "File telah terdownload " + donwloadedFilePath;

				AlertDialog.Builder builder = new AlertDialog.Builder(context)
						.setTitle(title)
						.setIcon(gmedia.net.id.absenigmedia.R.mipmap.ic_launcher)
						.setMessage(message)
						.setPositiveButton("Buka", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

								String path = donwloadedFilePath;
								File targetFile = new File(path);
								Uri targetUri = FileProvider.getUriForFile(context,
										context.getApplicationContext().getPackageName() + ".provider", targetFile);

								Intent intent = new Intent(Intent.ACTION_VIEW);
								intent.setDataAndType(targetUri, "application/pdf");
								intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
								try {
									startActivity(intent);
								} catch (Exception e) {
//                                    Toast.makeText(context,"tidak ada aplikasi",Toast.LENGTH_LONG).show();
									final Intent intent2 = new Intent(context, PDFReader.class);
									intent2.putExtra(PDFViewerActivities.EXTRA_PDFFILENAME, path);
									try {
										startActivity(intent2);
									} catch (Exception e2) {
										e2.printStackTrace();
									}
                                    /*try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.microsoft.office.excel")));
                                    } catch (Exception el) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.microsoft.office.excel")));
                                    }*/
								}
							}
						})
						.setNegativeButton("Tutup", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						});

				builder.show();
			} else {

				title = "Download terganggu";
				message = "Tidak dapat mengunduh file, silahkan coba kembali";

				final AlertDialog.Builder builder = new AlertDialog.Builder(context)
						.setTitle(title)
						.setIcon(gmedia.net.id.absenigmedia.R.mipmap.ic_launcher)
						.setMessage(message)
						.setNeutralButton("OK", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {

							}
						});
				builder.show();
			}

			//delete file from server
			JSONObject jBody = new JSONObject();

			try {
				jBody.put("file_name", fileName);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
	}
}
