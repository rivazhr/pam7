package baw.mobile.firebase_createreservation;

public class Reservasi {
    private String id;
    private String namaPeminjam;
    private String nimPeminjam;
    private String prodiPeminjam;
    private String noHp;
    private int jumlahStik;
    private String sesi;

    // Constructor kosong untuk Firebase
    public Reservasi() {}

    // Constructor lengkap
    public Reservasi(String id, String namaPeminjam, String nimPeminjam, String prodiPeminjam, String noHp, int jumlahStik, String sesi) {
        this.id = id;
        this.namaPeminjam = namaPeminjam;
        this.nimPeminjam = nimPeminjam;
        this.prodiPeminjam = prodiPeminjam;
        this.noHp = noHp;
        this.jumlahStik = jumlahStik;
        this.sesi = sesi;
    }

    // Getter dan Setter
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getNamaPeminjam() { return namaPeminjam; }
    public void setNamaPeminjam(String namaPeminjam) { this.namaPeminjam = namaPeminjam; }

    public String getNimPeminjam() { return nimPeminjam; }
    public void setNimPeminjam(String nimPeminjam) { this.nimPeminjam = nimPeminjam; }

    public String getProdiPeminjam() { return prodiPeminjam; }
    public void setProdiPeminjam(String prodiPeminjam) { this.prodiPeminjam = prodiPeminjam; }

    public String getNoHp() { return noHp; }
    public void setNoHp(String noHp) { this.noHp = noHp; }

    public int getJumlahStik() { return jumlahStik; }
    public void setJumlahStik(int jumlahStik) { this.jumlahStik = jumlahStik; }

    public String getSesi() { return sesi; }
    public void setSesi(String sesi) { this.sesi = sesi; }

    @Override
    public String toString() {
        return "EntitasReservasi{" +
                "id=" + id +
                ", namaPeminjam='" + namaPeminjam + '\'' +
                ", nimPeminjam='" + nimPeminjam + '\'' +
                ", prodiPeminjam='" + prodiPeminjam + '\'' +
                ", noHp='" + noHp + '\'' +
                ", jumlahStik=" + jumlahStik +
                ", sesi='" + sesi + '\'' +
                '}';
    }

}


