package Model;

public class ChiTietKho {

    private int id;
    private int ma_hang;
    private int so_luong;

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

    public int getSL() {
        return so_luong;
    }

    public void setSL(int sl) {
        this.so_luong = sl;
    }

    public ChiTietKho(int id, int ma_kho, int ma_hang, int so_luong) {
        this.id = id;
        this.ma_hang = ma_hang;
        this.so_luong = so_luong;
    }
    public ChiTietKho(){
    }
}
