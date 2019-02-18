package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 24/01/2018.
 */

public class CustomRecyclerViewDashboardBaru {
    private String id, judul, berita, tanggal, gambar;

    public CustomRecyclerViewDashboardBaru(String id, String judul, String berita, String tanggal, String gambar) {
        this.id = id;
        this.judul = judul;
        this.berita = berita;
        this.tanggal = tanggal;
        this.gambar = gambar;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getBerita() {
        return berita;
    }

    public void setBerita(String berita) {
        this.berita = berita;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }


    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }
}
