package application;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

//klasa Treni me objekte e saj dhe getter dhe setters (read,write) 
//-->qe do te perdoren nga TableView tblTreni i klases kontrollAdmin dhe tblTreniUser i klasa KontrollUser
public class Treni {

    private int nrTreni;
    private int kapaciteti;
    private String emriTreni;
    private String nisja;
    private String mberritja;
    private String data;
    private String ora;
    private Double cmimi;

    public Treni(int nrTreni, int kapaciteti, String emriTreni, String nisja, String mberritja, String data, String ora, Double cmimi) {
        this.nrTreni = nrTreni;
        this.kapaciteti = kapaciteti;
        this.emriTreni = emriTreni;
        this.nisja = nisja;
        this.mberritja = mberritja;
        this.data = data;
        this.ora = ora;
        this.cmimi = cmimi;
    }

    public int getNrTreni() {
        return nrTreni;
    }

    public void setNrTreni(int nrTreni) {
        this.nrTreni = nrTreni;
    }

  
    public int getKapaciteti() {
		return kapaciteti;
	}

	public void setKapaciteti(int kataciteti) {
		this.kapaciteti = kataciteti;
	}

	public String getEmriTreni() {
        return emriTreni;
    }

    public void setEmriTreni(String emriTreni) {
        this.emriTreni = emriTreni;
    }

    public String getNisja() {
        return nisja;
    }

    public void setNisja(String nisja) {
        this.nisja = nisja;
    }

    public String getMberritja() {
        return mberritja;
    }

    public void setMberritja(String mberritja) {
        this.mberritja = mberritja;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public Double getCmimi() {
        return cmimi;
    }

    public void setCmimi(Double cmimi) {
        this.cmimi = cmimi;
    }

    //listat statike per ComboBox-et , per te bere popullimin e tyre 
    public static ObservableList<String> getTrenaEmra() {
        return FXCollections.observableArrayList("AC11X", "ZX22Y", "KP09M", "TR55T", "LV99Q");
    }

    public static ObservableList<String> getQytete() {
        return FXCollections.observableArrayList("SW", "AL", "NY", "TK", "PR", "LA", "DE", "BE", "IT", "GR");
    }
}
