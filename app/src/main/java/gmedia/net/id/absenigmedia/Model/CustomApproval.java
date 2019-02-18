package gmedia.net.id.absenigmedia.Model;

public class CustomApproval {
    private String id, nama, tanggal, keterangan, alasan, jam;

    public CustomApproval(String id, String nama, String tanggal, String keterangan, String alasan, String jam) {
        this.id = id;
        this.nama = nama;
        this.tanggal = tanggal;
        this.keterangan = keterangan;
        this.alasan = alasan;
        this.jam = jam;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
