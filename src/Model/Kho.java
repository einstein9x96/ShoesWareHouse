package Model;

public class Kho {

    private int ma_hang;
    private int so_luong;
    private String ten_hang;
    private String dvt;

    public int getMaHang() {
        return ma_hang;
    }

    public void setMaHang(int mahang) {
        this.ma_hang = mahang;
    }

    public int getSoLuong() {
        return so_luong;
    }

    public void setSoLuong(int soluong) {
        this.so_luong = soluong;
    }

    public String getTenHang() {
        return ten_hang;
    }

    public void setTenHang(String ten) {
        this.ten_hang = ten;
    }

    public String getDVT() {
        return dvt;
    }

    public void setDVT(String dvt) {
        this.dvt = dvt;
    }
    public Kho (){
        
    }
    public Kho (int mahang, int soluong, String tenhang, String dvt){
        this.ma_hang = mahang;
        this.so_luong = soluong;
        this.ten_hang = tenhang;
        this.dvt = dvt;
    }
}
