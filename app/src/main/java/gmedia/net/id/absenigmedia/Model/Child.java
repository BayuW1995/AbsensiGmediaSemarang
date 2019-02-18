package gmedia.net.id.absenigmedia.Model;

/**
 * Created by Bayu on 30/11/2017.
 */

public class Child {
    private String tanggalchild, jumlahexpand;


    public Child(String tanggalchild, String jumlahexpand) {
        this.tanggalchild = tanggalchild;
        this.jumlahexpand = jumlahexpand;
    }

    public String getTanggalchild() {
        return tanggalchild;
    }

    public void setTanggalchild(String tanggalchild) {
        this.tanggalchild = tanggalchild;
    }

    public String getJumlahexpand() {
        return jumlahexpand;
    }

    public void setJumlahexpand(String jumlahexpand) {
        this.jumlahexpand = jumlahexpand;
    }
}
