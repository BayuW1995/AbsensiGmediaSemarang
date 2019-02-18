package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 21/02/2018.
 */

public class CustomHistoryIjinKeluarKantor {
    private String tanggal, jamDari, jamSampai, alasan, status;

    public CustomHistoryIjinKeluarKantor(String tanggal, String jamDari, String jamSampai, String alasan, String status) {
        this.tanggal = tanggal;
        this.jamDari = jamDari;
        this.jamSampai = jamSampai;
        this.alasan = alasan;
        this.status = status;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJamDari() {
        return jamDari;
    }

    public void setJamDari(String jamDari) {
        this.jamDari = jamDari;
    }

    public String getJamSampai() {
        return jamSampai;
    }

    public void setJamSampai(String jamSampai) {
        this.jamSampai = jamSampai;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
