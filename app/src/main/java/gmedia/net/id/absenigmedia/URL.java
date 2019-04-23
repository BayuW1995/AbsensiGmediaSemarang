package gmedia.net.id.absenigmedia;

/**
 * Created by Bayu on 15/01/2018.
 */

public class URL {
    public static String BaseUrl = "https://erpsmg.gmedia.id/hrd/rest/";
//        public static String BaseUrl = "http://192.168.12.100/gmedia.hr/rest/";
    public static String urlLogin = BaseUrl + "authentication";
    public static String urlUangMakan = BaseUrl + "uang";
    public static String urlUangLembur = BaseUrl + "uang";
    public static String urlCheckIn = BaseUrl + "scan";
    public static String urlRekapitulasiAbsensi = BaseUrl + "rekap_absen";
    public static String urlCuti = BaseUrl + "cuti";
    public static String urlHistory = BaseUrl + "view_cuti";
    public static String urlDashboard = BaseUrl + "dashboard";
    public static String urlSisaCuti = BaseUrl + "sisa_cuti";
    public static String urlBiodata = BaseUrl + "biodata";
    public static String urlNews = BaseUrl + "news";
    public static String urlBatalCuti = BaseUrl + "batal_cuti";
    public static String urlIpPublic = "https://erpsmg.gmedia.id/check_ip.php";
    public static String urlDownloadPDFRekapAbsensi = "https://erpsmg.gmedia.id/hrd/download_excel/rekap_absen?";
    public static String urlDownloadPDFRekapitulasiAbsensi = "https://erpsmg.gmedia.id/hrd/download_file/download_rekap_absen?";
    public static String urlDownloadPDFJadwalTS = "https://erpsmg.gmedia.id/hrd/download_file/view_jadwal_ts?";
    public static String urlJadwal = BaseUrl + "penjadwalan";
    public static String urlGantiPassword = BaseUrl + "reset_password";
    public static String urlProfile = BaseUrl + "biodata";
    public static String urlTotalTerlambat = BaseUrl + "detail_terlambat";
    public static String urlRekapAbsen = BaseUrl + "absensi";
    public static String urlIjinPulangAwal = BaseUrl + "create_pulang_awal";
    public static String urlIjinKeluarKantor = BaseUrl + "create_keluar_kantor";
    public static String urlViewPulangAwal = BaseUrl + "view_pulang_awal";
    public static String urlViewKeluarKantor = BaseUrl + "view_keluar_kantor";
    public static String urlUpVersion = BaseUrl + "app_version";
    public static String urlScanLog = BaseUrl + "scanlog";
    public static String urlMasterApproval = BaseUrl + "master_approval";
    public static String urlApproval = BaseUrl + "view_approval";
    public static String urlKonfirmasiApproval = BaseUrl + "approve_action";
    public static String urlChangeLog = BaseUrl + "changelog";
    public static String urlAsuransi = BaseUrl + "asuransi";
    public static String urlCicilHutang = BaseUrl + "hutang";
	public static String urlEditPIN = BaseUrl + "update_pin";
	public static String urlCreatePIN = BaseUrl + "create_pin";
	public static String urlSlipGaji = BaseUrl + "payroll";
}
