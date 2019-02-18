package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 08/12/2017.
 */

public class Custom {
    private String tanggal, jammasuk, jamkeluar, telat, flag_libur;

    public Custom(String tanggal, String jammasuk, String jamkeluar, String telat, String flag_libur) {
        this.tanggal=tanggal;
        this.jammasuk=jammasuk;
        this.jamkeluar=jamkeluar;
        this.telat=telat;
        this.flag_libur=flag_libur;
    }

   /* Custom(String tanggal, String jammasuk, String jamkeluar) {
        this.tanggal = tanggal;
        this.jammasuk = jammasuk;
        this.jamkeluar = jamkeluar;
    }*/


    public String getJammasuk() {
        return jammasuk;
    }

    public void setJammasuk(String jammasuk) {
        this.jammasuk = jammasuk;
    }

    public String getJamkeluar() {
        return jamkeluar;
    }

    public void setJamkeluar(String jamkeluar) {
        this.jamkeluar = jamkeluar;
    }

    public String getTelat() {
        return telat;
    }

    public void setTelat(String telat) {
        this.telat = telat;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getFlag_libur() {
        return flag_libur;
    }

    public void setFlag_libur(String flag_libur) {
        this.flag_libur = flag_libur;
    }
}
