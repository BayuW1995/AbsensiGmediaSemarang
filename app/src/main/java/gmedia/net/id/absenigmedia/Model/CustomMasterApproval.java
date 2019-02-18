package gmedia.net.id.absenigmedia.Model;

public class CustomMasterApproval {
    private String kode, nama;

    public CustomMasterApproval(String kode, String nama) {
        this.kode = kode;
        this.nama = nama;
    }

    public String toString() {
        return nama;
    }

    public String getKode() {
        return kode;
    }

    public void setKode(String kode) {
        this.kode = kode;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
