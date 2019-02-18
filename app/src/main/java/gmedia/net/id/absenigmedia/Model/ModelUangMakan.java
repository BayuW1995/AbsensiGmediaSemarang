package gmedia.net.id.absenigmedia.Model;

public class ModelUangMakan {
    private String minggu, tanggal, jumlah;

    public ModelUangMakan(String minggu, String range, String nominal) {
        this.minggu = minggu;
        this.tanggal = range;
        this.jumlah = nominal;
    }

    public String getMinggu() {
        return minggu;
    }

    public void setMinggu(String minggu) {
        this.minggu = minggu;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
