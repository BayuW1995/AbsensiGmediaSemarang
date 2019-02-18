package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 21/02/2018.
 */

public class CustomHistoryIjinPulangAwal {
    private String tanggal, jam, alasan, status;

    public CustomHistoryIjinPulangAwal(String tanggal, String jam, String alasan, String status) {
        this.tanggal = tanggal;
        this.jam = jam;
        this.alasan = alasan;
        this.status = status;
    }


    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
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
