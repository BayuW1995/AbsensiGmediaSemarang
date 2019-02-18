package gmedia.net.id.absenigmedia;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import gmedia.net.id.absenigmedia.Volley.ApiVolley;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bayu on 27/02/2018.
 */

public class Profile extends Fragment {
    private Context context;
    private TextView nik, nama, noKTP, tempatLahir, tgl_lahir, agama, jenisKelamin, golonganDarah, telephone,
            email, alamat, status_menikah, bank, noRek, pendidikanTerakhir, namaSekolah, jurusan,
            thnLulus, noAbsen, cabang, divisi, bagian, jabatan, statusKaryawan, departmen, tgl_masuk;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup viewGroup, Bundle savedInstanceState) {
        View view = inflater.inflate(gmedia.net.id.absenigmedia.R.layout.profile, viewGroup, false);
        context = getContext();
        nik = view.findViewById(gmedia.net.id.absenigmedia.R.id.noNik);
        nama = view.findViewById(gmedia.net.id.absenigmedia.R.id.namaProfile);
        noKTP = view.findViewById(gmedia.net.id.absenigmedia.R.id.noKTP);
        tempatLahir = view.findViewById(gmedia.net.id.absenigmedia.R.id.tempatLahir);
        tgl_lahir = view.findViewById(gmedia.net.id.absenigmedia.R.id.tanggalLahir);
        agama = view.findViewById(gmedia.net.id.absenigmedia.R.id.agama);
        jenisKelamin = view.findViewById(gmedia.net.id.absenigmedia.R.id.jenisKelamin);
        golonganDarah = view.findViewById(gmedia.net.id.absenigmedia.R.id.golDarah);
        telephone = view.findViewById(gmedia.net.id.absenigmedia.R.id.noTelepon);
        email = view.findViewById(gmedia.net.id.absenigmedia.R.id.email);
        alamat = view.findViewById(gmedia.net.id.absenigmedia.R.id.alamatProfile);
        status_menikah = view.findViewById(gmedia.net.id.absenigmedia.R.id.statusMenikah);
        bank = view.findViewById(gmedia.net.id.absenigmedia.R.id.namaBank);
        noRek = view.findViewById(gmedia.net.id.absenigmedia.R.id.noRekening);
        pendidikanTerakhir = view.findViewById(gmedia.net.id.absenigmedia.R.id.pendTerakhir);
        namaSekolah = view.findViewById(gmedia.net.id.absenigmedia.R.id.namaSekolah);
        jurusan = view.findViewById(gmedia.net.id.absenigmedia.R.id.jurusan);
        thnLulus = view.findViewById(gmedia.net.id.absenigmedia.R.id.thnLulus);
        noAbsen = view.findViewById(gmedia.net.id.absenigmedia.R.id.noAbsen);
        cabang = view.findViewById(gmedia.net.id.absenigmedia.R.id.cabang);
        divisi = view.findViewById(gmedia.net.id.absenigmedia.R.id.divisi);
        bagian = view.findViewById(gmedia.net.id.absenigmedia.R.id.bagian);
        jabatan = view.findViewById(gmedia.net.id.absenigmedia.R.id.jabatan);
        statusKaryawan = view.findViewById(gmedia.net.id.absenigmedia.R.id.statusKaryawan);
        departmen = view.findViewById(gmedia.net.id.absenigmedia.R.id.departmen);
        tgl_masuk = view.findViewById(gmedia.net.id.absenigmedia.R.id.tglMasuk);
        prepareDataProfile();
        return view;
    }

    private void prepareDataProfile() {
        ApiVolley request = new ApiVolley(context, new JSONObject(), "POST", URL.urlProfile, "", "", 0, new ApiVolley.VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject object = new JSONObject(result);
                    String status = object.getJSONObject("metadata").getString("status");
                    if (status.equals("1")){
                        JSONObject profile = object.getJSONObject("response");
                        nik.setText(profile.getString("nip"));
                        nama.setText(profile.getString("nama"));
                        noKTP.setText(profile.getString("no_ktp"));
                        tempatLahir.setText(profile.getString("tempat_lahir"));
                        tgl_lahir.setText(profile.getString("tgl_lahir"));
                        agama.setText(profile.getString("agama"));
                        jenisKelamin.setText(profile.getString("jenis_kelamin"));
                        golonganDarah.setText(profile.getString("golongan_darah"));
                        telephone.setText(profile.getString("telp"));
                        email.setText(profile.getString("email"));
                        alamat.setText(profile.getString("alamat"));
                        status_menikah.setText(profile.getString("status_menikah"));
                        bank.setText(profile.getString("nama_bank"));
                        noRek.setText(profile.getString("no_rekening"));
                        pendidikanTerakhir.setText(profile.getString("pendidikan"));
                        namaSekolah.setText(profile.getString("nama_sekolah"));
                        jurusan.setText(profile.getString("jurusan"));
                        thnLulus.setText(profile.getString("tahun_lulus"));
                        noAbsen.setText(profile.getString("pin"));
                        cabang.setText(profile.getString("cabang"));
                        divisi.setText(profile.getString("divisi"));
                        bagian.setText(profile.getString("bagian"));
                        jabatan.setText(profile.getString("jabatan"));
                        statusKaryawan.setText(profile.getString("status_karyawan"));
                        departmen.setText(profile.getString("departemen"));
                        tgl_masuk.setText(profile.getString("tgl_masuk"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onError(String result) {
                Toast.makeText(context, "Error Brooh", Toast.LENGTH_LONG).show();
            }
        });
    }
}
