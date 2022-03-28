package DeckOfCards;

public class CardTester {
    public static void main(String [] args){
        Deck totalDeck = new Deck();
        totalDeck.deckBuilder();
        totalDeck.viewCard(11);
        totalDeck.viewCard(37);

    }
}
