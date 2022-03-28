package DeckOfCards;

public class Cards {
    private String cardTitle;
    private int cardSuit;
    private int cardValue;
    private int cardColor;
    private String cardSilhouette;
    private String cardID;
    private boolean cardAvailability;

    public Cards(){
        cardTitle = null;
        cardSuit = 0;
        cardValue = 0;
        cardColor = 0;
        cardSilhouette = null;
        cardID = null;
        cardAvailability = true;
    }

    public String getCardTitle(){
        return cardTitle;
    }
    public int getCardSuit(){
        return cardSuit;
    }
    public int getCardValue(){
        return cardValue;
    }
    public int getCardColor(){
        return cardColor;
    }
    public String getCardSilhouette(){
        return cardSilhouette;
    }
    public String getCardID(){
        return cardID;
    }
    public boolean getCardAvailability(){
        return cardAvailability;}

    public void setCardTitle(String pTitle){
        cardTitle = pTitle;
    }
    public void setCardSuit(int pSuit){
        cardSuit = pSuit;
    }
    public void setCardValue(int pValue){
        cardValue = pValue;
    }
    public void setCardColor(int pColor){
        cardColor = pColor;
    }
    public void setCardSilhouette(String pSilhouette){
        cardSilhouette = pSilhouette;
    }
    public void setCardID(){
        String ceroChecker;
        int cSuit = this.getCardSuit();
        int cVal = this.getCardValue();
        int cCol = this.getCardColor();
        if(cVal < 10){
            ceroChecker = "0" + String.valueOf(cVal);
        }
        else ceroChecker = String.valueOf(cVal);
        cardID = "#" + String.valueOf(cSuit) + ceroChecker + String.valueOf(cCol);
    }
    public void setCardAvailability(boolean pAvailability){
        cardAvailability = pAvailability;
    }

    public void getCardProperties(){
        System.out.println(
                "Title: " + getCardTitle() +
                "\nSuit: " + getCardSuit() +
                "\nValue: " + getCardValue() +
                "\nColor: " + getCardColor() +
                "\nSilhouette: " + getCardSilhouette() +
                "\nCardID: " + getCardID());
    }
}
