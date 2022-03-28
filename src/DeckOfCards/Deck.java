package DeckOfCards;

public class Deck{

    private final Cards[] Deck52 = new Cards[52];
    private final String[] Suits = {"Hearts", "Diamonds", "Spades", "Clubs"};
    private final String[] Values = {"Ace", "2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King"};
    private final String[] Colors = {"Red", "Black"};
    private final String[] Visibility = {"[A]", "[J]", "[Q]", "[K]"};

    public Deck(){

    }
    public void deckBuilder(){ // invoke this method after creating a Deck object in MainDriver
       int cardCounting = 0;

        for (int builder = 0; builder < 52; builder++){ //Stores Card Objects within Deck52 elements
            Deck52[builder] = new Cards();
        }

        for (int suitCount = 0; suitCount < 4; suitCount++) {
            for (int valueCount = 0; valueCount < 13; valueCount++) { //TODO: this needs to be changed.(10,J,Q,K = 10)
                generateCardProperties(cardCounting, suitCount, valueCount);
                cardCounting++;
            }
        }
    }
    public void generateCardProperties(int pCount, int pSuit, int pValue){
        Deck52[pCount].setCardSuit(pSuit);
        Deck52[pCount].setCardValue(pValue);
        if(pSuit > 1){
            Deck52[pCount].setCardColor(1);
        }else Deck52[pCount].setCardColor(0);
        Deck52[pCount].setCardID();
        Deck52[pCount].setCardTitle(generateCardTitle(Deck52[pCount].getCardID(), pValue));
        changeSilhouette(pCount, pValue);

    }
    public String generateCardTitle(String pID, int pValue){
        String cardTitle = "";
        //Color
        cardTitle = Colors[Character.getNumericValue(pID.charAt(4))] + " ";
        //Value
        cardTitle += Values[pValue] + " of ";
        //Suit
        cardTitle += Suits[Character.getNumericValue(pID.charAt(1))];

        return cardTitle;
    } // pValue may be redundant, just parse ID(#0XX0)
    public void viewCard(int pChoice){
        Deck52[pChoice].getCardProperties();
    }
    public Cards getDeck52(int pParticular) {
        return Deck52[pParticular];
    }
    public void changeAvailability(int pChoice){
        if(Deck52[pChoice].getCardAvailability()){
            Deck52[pChoice].setCardAvailability(false);
        }
        else Deck52[pChoice].setCardAvailability(true);
    }
    public void changeSilhouette(int pCount, int pValue){
        if(pValue == 0){
            Deck52[pCount].setCardSilhouette(Visibility[0]);
        }
        else if(pValue == 10){
            Deck52[pCount].setCardSilhouette(Visibility[1]);
        }
        else if(pValue == 11){
            Deck52[pCount].setCardSilhouette(Visibility[2]);
        }
        else if(pValue == 12){
            Deck52[pCount].setCardSilhouette(Visibility[3]);
        }
        else Deck52[pCount].setCardSilhouette("[" + (pValue+1) + "]");
    }
}
