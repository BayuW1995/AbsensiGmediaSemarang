package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 19/02/2018.
 */

public class CustomTotalTerlambat {
    private String tanggal, jammasuk, scanmasuk, telat;

    public CustomTotalTerlambat(String tanggal, String jammasuk, String scanmasuk, String telat) {
        this.tanggal=tanggal;
        this.jammasuk=jammasuk;
        this.scanmasuk=scanmasuk;
        this.telat=telat;
    }

  /*  public CustomTotalTerlambat(String tanggal, String jammasuk, String scanmasuk, String telat) {
        this.tanggal = tanggal;
        this.jammasuk = jammasuk;
        this.scanmasuk = scanmasuk;
        this.telat = telat;
    }*/

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJammasuk() {
        return jammasuk;
    }

    public void setJammasuk(String jammasuk) {
        this.jammasuk = jammasuk;
    }

    public String getTelat() {
        return telat;
    }

    public void setTelat(String telat) {
        this.telat = telat;
    }

    public String getScanmasuk() {
        return scanmasuk;
    }

    public void setScanmasuk(String scanmasuk) {
        this.scanmasuk = scanmasuk;
    }
}
