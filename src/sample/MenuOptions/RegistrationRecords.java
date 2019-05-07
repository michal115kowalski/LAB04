package sample.MenuOptions;

public class RegistrationRecords {
    private int id_zapisu,id_wydarzenia,id_uzytkownika;
    private String typ_uczestnictwa,wyzywienie;

    public RegistrationRecords(int id_zapisu,int id_uzytkownika,int id_wydarzenia,String typ_uczestnictwa,String wyzywienie){
        this.id_zapisu=id_zapisu;
        this.id_wydarzenia=id_wydarzenia;
        this.id_uzytkownika=id_uzytkownika;
        this.typ_uczestnictwa=typ_uczestnictwa;
        this.wyzywienie=wyzywienie;
    }

    public int getId_zapisu() {
        return id_zapisu;
    }

    public void setId_zapisu(int id_zapisu) {
        this.id_zapisu = id_zapisu;
    }

    public int getId_wydarzenia() {
        return id_wydarzenia;
    }

    public void setId_wydarzenia(int id_wydarzenia) {
        this.id_wydarzenia = id_wydarzenia;
    }

    public int getId_uzytkownika() {
        return id_uzytkownika;
    }

    public void setId_uzytkownika(int id_uzytkownika) {
        this.id_uzytkownika = id_uzytkownika;
    }

    public String getTyp_uczestnictwa() {
        return typ_uczestnictwa;
    }

    public void setTyp_uczestnictwa(String typ_uczestnictwa) {
        this.typ_uczestnictwa = typ_uczestnictwa;
    }

    public String getWyzywienie() {
        return wyzywienie;
    }

    public void setWyzywienie(String wyzywienie) {
        this.wyzywienie = wyzywienie;
    }
}
