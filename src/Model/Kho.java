package Model;

public class Kho {

    private int ma_kho;
    private String ten_kho;
    private String dia_diem;

    public int getMaKho() {
        return ma_kho;
    }

    public void setMaKho(int makho) {
        this.ma_kho = makho;
    }

    public String getTenKho() {
        return ten_kho;
    }

    public void setTenKho(String tenkho) {
        this.ten_kho = tenkho;
    }

    public String getDiaDiem() {
        return dia_diem;
    }

    public void setDiaDiem(String diadiem) {
        this.dia_diem = diadiem;
    }
    public Kho(int makho, String tenkho, String điaiem) {
        this.ma_kho = makho;
        this.ten_kho = tenkho;
        this.dia_diem = điaiem;
    }
    public Kho(){
        
    }
    
}
