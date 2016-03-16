package vaadin.models;

public class Contestant {
	
	Integer bib;
	String name;
    String surname;
    String nation;
    
    public Contestant(Integer bib,String name, String surname, String nation ) {
    	this.bib = bib;
        this.name = name;
        this.surname = surname;
        this.nation = nation;
    }
    
    public Integer getContestantBib() {
        return bib;
    }

    public void setContestantBib(Integer bib) {
        this.bib = bib;
    }
    
    public String getContestantName() {
        return name;
    }

    public void setContestantName(String name) {
        this.name = name;
    }
    
    public String getContestantSurname() {
        return surname;
    }

    public void setContestantSurname(String surname) {
        this.surname = surname;
    }
    
    public String getContestantNation() {
        return nation;
    }

    public void setContestantNation(String nation) {
        this.nation = nation;
    }
}
