package Model;

import java.util.Date;

public class DonHang {

    private int id;
    private int ma_thukho;
    private Date ngay_nhap;
    private int loai_phieu;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getMaThuKho() {
        return ma_thukho;
    }

    public void setMaThuKho(int matk) {
        this.ma_thukho = matk;
    }

    public Date getNgayNhap() {
        return ngay_nhap;
    }

    public void setNgayNhap(Date ngay) {
        this.ngay_nhap = ngay;
    }

    public int getLoaiPhieu() {
        return loai_phieu;
    }

    public void setLoaiPhieu(int value) {
        this.loai_phieu = value;
    }
}
