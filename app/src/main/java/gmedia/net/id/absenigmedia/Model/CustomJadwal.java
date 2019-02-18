package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 28/02/2018.
 */

public class CustomJadwal {
    private String tanggal, shift, jammasuk, jamkeluar, flag_libur;

    public CustomJadwal(String tanggal, String shift, String jam_masuk, String jam_pulang, String flag_libur) {
        this.tanggal = tanggal;
        this.shift = shift;
        this.jammasuk = jam_masuk;
        this.jamkeluar = jam_pulang;
        this.flag_libur = flag_libur;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

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

    public String getFlag_libur() {
        return flag_libur;
    }

    public void setFlag_libur(String flag_libur) {
        this.flag_libur = flag_libur;
    }
}
