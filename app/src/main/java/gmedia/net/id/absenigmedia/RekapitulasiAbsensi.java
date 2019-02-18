package gmedia.net.id.absenigmedia;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Adapter.ListAdapterAbsensi;
import gmedia.net.id.absenigmedia.Model.Custom;
import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Bayu on 05/12/2017.
 */

public class RekapitulasiAbsensi extends Fragment {
    private Context context;
    public static TextView tglperiode, tglsampai;
    public static String awal, akhir;
    private List<Custom> absensi;
    private ListView listView;
    private RelativeLayout dataabsensi, downloadPDF;
    private ListAdapterAbsensi adapter;
    private ProgressDialog progressDialog;
    private SessionManager session;
    public final String tanggal[] =
            {
                    "Rab, 21/01/2017",
                    "Kam, 22/01/2017",
                    "Rab, 23/01/2017",
                    "Kam, 24/01/2017",
                    "Jum, 25/01/2017",
                    "Sab, 26/01/2017",
                    "Ming, 27/01/2017",
                    "Sen, 28/01/2017",
                    "sel, 29/01/2017",
                    "Rab, 30/01/2017",
                    "Kam, 31/01/2017",
                    "Jum, 01/01/2017",
                    "Sab, 02/01/2017"
            };
    public final String email[] =
            {
                    "example@gmail.com",
            };
    public final String jamkeluar[] =
            {
                    "17.40",
                    "-",
                    "18.20",
                    "17.45",
                    "14.00",
                    "-",
                    "-",
                    "14.00",
                    "14.00",
                    "14.00",
                    "-",
                    "-",
                    "-"
            };

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        final View view = inflater.inflate(gmedia.net.id.absenigmedia.R.layout.rekapitulasi_absensi, viewGroup, false);
        context = getContext();
        RelativeLayout tombolperiode = (RelativeLayout) view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutperiode);
        RelativeLayout tombolsampai = (RelativeLayout) view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutsampai);
        RelativeLayout tombolproses = (RelativeLayout) view.findViewById(gmedia.net.id.absenigmedia.R.id.layoutproses);
        session = new SessionManager(context);
        downloadPDF = view.findViewById(gmedia.net.id.absenigmedia.R.id.downloadPDF);
        dataabsensi = view.findViewById(gmedia.net.id.absenigmedia.R.id.dataabsensi);
        tglperiode = view.findViewById(gmedia.net.id.absenigmedia.R.id.tglperiode);
        tglsampai = view.findViewById(gmedia.net.id.absenigmedia.R.id.tglsampai);
        tombolperiode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final java.util.Calendar customDate = java.util.Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        customDate.set(java.util.Calendar.YEAR, year);
                        customDate.set(java.util.Calendar.MONTH, month);
                        customDate.set(java.util.Calendar.DATE, dayOfMonth);
                        SimpleDateFormat sdFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        tglperiode.setText(sdFormat.format(customDate.getTime()));
                        awal = "" + tglperiode.getText();
                        tglperiode.setAlpha(1);

                    }
                };
                new DatePickerDialog(context, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
                /*Intent intent = new Intent(context, Calendar.class);
                intent.putExtra("tombol", "dari");
                intent.putExtra("flag", "absen");
                startActivity(intent);*/
            }
        });
        tombolsampai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final java.util.Calendar customDate = java.util.Calendar.getInstance();
                DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        customDate.set(java.util.Calendar.YEAR, year);
                        customDate.set(java.util.Calendar.MONTH, month);
                        customDate.set(java.util.Calendar.DATE, dayOfMonth);
                        SimpleDateFormat sdFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
                        tglsampai.setText(sdFormat.format(customDate.getTime()));
                        akhir = "" + tglsampai.getText();
                        tglsampai.setAlpha(1);
                    }
                };
                new DatePickerDialog(context, date, customDate.get(java.util.Calendar.YEAR), customDate.get(java.util.Calendar.MONTH), customDate.get(java.util.Calendar.DATE)).show();
                /*Intent intent = new Intent(context, Calendar.class);
                intent.putExtra("tombol", "sampai");
                intent.putExtra("flag", "absen");
                startActivity(intent);*/
            }
        });
        tombolproses.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tglperiode.getText().toString().equals("Tanggal Mulai")) {
                    Toast.makeText(context, "Silahkan Pilih Tanggal Mulai", Toast.LENGTH_LONG).show();
                    return;
                } else if (tglsampai.getText().toString().equals("Tanggal Selesai")) {
                    Toast.makeText(context, "Silahkan Pilih Tanggal Akhir", Toast.LENGTH_LONG).show();
                    return;
                } else {
                    prepareData();
                }

            }
        });
        listView = (ListView) view.findViewById(gmedia.net.id.absenigmedia.R.id.listAbsen);
            /*ArrayList<Custom> absensi = isiList();
            adapter = new ListAdapterAbsensi(getActivity(), absensi);
            listView.setAdapter(adapter);*/
        final RelativeLayout downloadPDF = view.findViewById(gmedia.net.id.absenigmedia.R.id.downloadPDF);
        downloadPDF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String link = URL.urlDownloadPDFRekapitulasiAbsensi + "nip=" + session.getNip() + "&tgl_awal=" + awal + "&tgl_akhir=" + akhir;
                new DownloadFileFromURL().execute(link, "DetailRekapitulasiAbsensi.pdf");
//                preparedownloadPDF();
            }
        });
        return view;
    }

    public static void setDate(String date) {
        tglperiode.setText(date);
        tglperiode.setAlpha(1);
        tglperiode.setTextSize(20);
    }

    public static void setDate2(String date2) {
        tglsampai.setText(date2);
        tglsampai.setAlpha(1);
        tglsampai.setTextSize(20);
    }

    private void prepareData() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(gmedia.net.id.absenigmedia.R.layout.loading);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        final JSONObject jBody = new JSONObject();
        try {
            jBody.put("startdate", tglperiode.getText());
            jBody.put("enddate", tglsampai.getText());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        ApiVolley request = new ApiVolley(context, jBody, "POST", URL.urlRekapitulasiAbsensi, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                absensi = new ArrayList<Custom>();
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")) {
                        JSONArray array = object.getJSONArray("response");
                        for (int i = 0; i < array.length(); i++) {
                            JSONObject isi = array.getJSONObject(i);
                            absensi.add(new Custom(
                                    isi.getString("hari"),
                                    isi.getString("jam_masuk"),
                                    isi.getString("jam_pulang"),
                                    isi.getString("telat"),
                                    isi.getString("flag_libur")
                            ));
                        }
                        listView.setAdapter(null);
                        adapter = new ListAdapterAbsensi(context, absensi);
                        listView.setAdapter(adapter);
                        dataabsensi.setVisibility(View.VISIBLE);
                        downloadPDF.setVisibility(View.VISIBLE);
                        dialog.dismiss();

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                dialog.dismiss();
                Toast.makeText(getContext(), "Terjadi Kesalahan", Toast.LENGTH_LONG).show();
            }

        });
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
                        (Environment.DIRECTORY_DOCUMENTS) + File.separator + "Detail Rekapitulasi Absensi");
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
                                        BuildConfig.APPLICATION_ID + ".provider", targetFile);

                                Intent intent;
                                intent = new Intent(Intent.ACTION_VIEW);
                                intent.setDataAndType(targetUri, "application/pdf");
                                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                                try {
                                    startActivity(intent);
                                } catch (Exception e) {
//                                    final Intent intent2 = new Intent(context, PDFReader.class);
//                                    intent2.putExtra(PDFViewerActivities.EXTRA_PDFFILENAME, path);
                                    final Intent intent2 = new Intent(context, PDFReader.class);
                                    intent2.putExtra(PDFViewerActivities.EXTRA_PDFFILENAME, path);
                                    try {
                                        startActivity(intent2);
                                    } catch (Exception e2) {
                                        e2.printStackTrace();
                                    }
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

    /*private ArrayList<Custom> isiList() {
        ArrayList<Custom> rvData = new ArrayList<>();
        for (int i = 0; i < tanggal.length; i++) {
            Custom custom = new Custom();
            custom.setTanggal(tanggal[i]);
            custom.setJammasuk(jammasuk[i]);
            custom.setJamkeluar(jamkeluar[i]);
            rvData.add(custom);
        }
        return rvData;
    }*/


}
