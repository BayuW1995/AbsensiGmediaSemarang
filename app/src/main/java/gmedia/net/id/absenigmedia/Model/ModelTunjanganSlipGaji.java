package gmedia.net.id.absenigmedia.Model;

public class ModelTunjanganSlipGaji {
	private String nama, nominal;

	public ModelTunjanganSlipGaji(String item, String value) {
		this.nama = item;
		this.nominal = value;
	}

	public String getNama() {
		return nama;
	}

	public void setNama(String nama) {
		this.nama = nama;
	}

	public String getNominal() {
		return nominal;
	}

	public void setNominal(String nominal) {
		this.nominal = nominal;
	}
}
