package Model;

public class ChiTietDonHang {

    private int id;
    private int ma_hang;
    private int soluong_nhap;
    private int ma_don;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaHang() {
        return ma_hang;
    }

    public void setMaHang(int mahang) {
        this.ma_hang = mahang;
    }

    public int getSLNhap() {
        return soluong_nhap;
    }

    public void setSLNhap(int slnhap) {
        this.soluong_nhap = slnhap;
    }

    public int getMaDon() {
        return ma_don;
    }

    public void setMaDon(int madon) {
        this.ma_don = madon;
    }
}
