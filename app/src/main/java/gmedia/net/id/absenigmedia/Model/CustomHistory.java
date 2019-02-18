package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 15/12/2017.
 */

public class CustomHistory {
    private String no, mulai, selesai, keteranganHistory;

    public CustomHistory(String no, String mulai, String selesai, String alasan) {
        this.no=no;
        this.mulai=mulai;
        this.selesai=selesai;
        this.keteranganHistory=alasan;
    }

    public String getNo() {
        return no;
    }

    public void setNo(String no) {
        this.no = no;
    }

    public String getMulai() {
        return mulai;
    }

    public void setMulai(String mulai) {
        this.mulai = mulai;
    }

    public String getSelesai() {
        return selesai;
    }

    public void setSelesai(String selesai) {
        this.selesai = selesai;
    }

    public String getKeteranganHistory() {
        return keteranganHistory;
    }

    public void setKeteranganHistory(String keteranganHistory) {
        this.keteranganHistory = keteranganHistory;
    }
}
