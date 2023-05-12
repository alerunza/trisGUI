public enum Giocatore {
    gX("X"),
    gO("O");

    private String abbreviation;

    Giocatore(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return this.abbreviation;
    }
}
