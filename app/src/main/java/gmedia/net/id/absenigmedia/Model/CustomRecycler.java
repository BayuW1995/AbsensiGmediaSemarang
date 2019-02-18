package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 14/12/2017.
 */

public class CustomRecycler {
    private String id,mulai, sampai, keterangan;

    public CustomRecycler(String id, String mulai, String sampai, String keterangan) {
        this.id=id;
        this.mulai=mulai;
        this.sampai=sampai;
        this.keterangan=keterangan;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSampai() {
        return sampai;
    }

    public void setSampai(String sampai) {
        this.sampai = sampai;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
