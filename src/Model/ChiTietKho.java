package Model;

public class ChiTietKho {

    private int id;
    private int ma_kho;
    private int ma_hang;
    private int so_luong;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaKho() {
        return ma_kho;
    }

    public void setMakho(int makho) {
        this.ma_kho = makho;
    }

    public int getMaHang() {
        return ma_hang;
    }

    public void setMaHang(int mahang) {
        this.ma_hang = mahang;
    }

    public int getSL() {
        return so_luong;
    }

    public void setSL(int sl) {
        this.so_luong = sl;
    }
}
