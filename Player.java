public enum Player {
    GiocatoreX("X"),
    GiocatoreO("O");

    private String abbreviation;

    Player(String abbreviation){
        this.abbreviation = abbreviation;
    }

    public String getAbbreviation(){
        return this.abbreviation;
    }
}
