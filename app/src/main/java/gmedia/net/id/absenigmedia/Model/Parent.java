package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 30/11/2017.
 */

public class Parent {
    private String minggu, tanggaldari, tanggalsampai, jumlah;


    public Parent(String minggu, String tanggaldari, String tanggalsampai, String jumlah) {
        this.minggu = minggu;
        this.tanggaldari = tanggaldari;
        this.tanggalsampai = tanggalsampai;
        this.jumlah = jumlah;
    }

    public String getMinggu() {
        return minggu;
    }

    public void setMinggu(String minggu) {
        this.minggu = minggu;
    }

    public String getTanggaldari() {
        return tanggaldari;
    }

    public void setTanggaldari(String tanggaldari) {
        this.tanggaldari = tanggaldari;
    }

    public String getTanggalsampai() {
        return tanggalsampai;
    }

    public void setTanggalsampai(String tanggalsampai) {
        this.tanggalsampai = tanggalsampai;
    }

    public String getJumlah() {
        return jumlah;
    }

    public void setJumlah(String jumlah) {
        this.jumlah = jumlah;
    }
}
