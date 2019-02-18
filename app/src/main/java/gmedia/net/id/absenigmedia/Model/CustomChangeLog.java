package gmedia.net.id.absenigmedia.Model;

public class CustomChangeLog {
    private String version, tanggal, isi;

    public CustomChangeLog(String version, String tanggal, String text) {
        this.version = version;
        this.tanggal = tanggal;
        this.isi = text;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getIsi() {
        return isi;
    }

    public void setIsi(String isi) {
        this.isi = isi;
    }
}
