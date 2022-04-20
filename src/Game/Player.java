package Game;
import DeckOfCards.Cards;
import java.util.ArrayList;
import java.util.Scanner;

public class Player {
    House dealer = new House();
    Scanner keyboard = new Scanner(System.in);

    private ArrayList<Cards> Hand = new ArrayList<Cards>();
    private ArrayList<Cards> potentialPlays = new ArrayList<Cards>();
    private int[] displayedPlays = new int[6];

    private int hitCounter = -2;
    private String yesOrNo = "yes";
    private int largestValue;
    private int losingValue;
    private boolean secondRoundCheck = false;
    private boolean winnerParameter = false;
    private boolean bustParameter = false;

    public void playGame(){
        potentialPlays.add(new Cards());
        dealer.createOneTimeCard();
        receiveFirstDeal();

        do{
            askPlayerToHit();
            if(yesOrNo.equalsIgnoreCase("no")){
                break;
            }
        }while(true);
        whoWins();
    }

    private void receiveFirstDeal(){
        dealer.pickUpDeck();
        hitPlayer();
        hitPlayer();
        dealer.hitHouse();
        dealer.hitHouse();
        //player
        displayCardsOnHand();
        displayPlays();
        //house
        dealer.houseDisplayCardsOnHand(0);
        dealer.houseDisplayPlays(0);
        System.out.println("----------------------------");
        value21Victory();
        endGameWhenHigherThan21(); //May be redundant
    }
    private void askPlayerToHit(){
        if(!winnerParameter && !bustParameter){
            System.out.println("Hit?");
            do
            {
                System.out.println("Please enter \"yes\" or \"no\".");
                yesOrNo = keyboard.next();
            } while (!yesOrNo.equalsIgnoreCase("yes") && !yesOrNo.equalsIgnoreCase("no"));

            if(yesOrNo.equalsIgnoreCase("yes")){
                hitPlayer();
            }
        }
        else {
            yesOrNo = "no";
        }
    }
    private void dealPlayer(){
        Cards tempCard = new Cards();
        //tempCard = dealer.getSpecificCard();
        tempCard = dealer.getSingleCard();
        Hand.add(tempCard);

        playLogistics(tempCard.getCardValue() + 1);
    }//Current Hits: RANDOM
    private void hitPlayer(){
        if(hitCounter < 0){
            dealPlayer();
            hitCounter++;
        }
        else {
            dealPlayer();
            displayCardsOnHand();
            displayPlays();
            dealer.houseDisplayCardsOnHand(0);
            System.out.println("----------------------------");
            value21Victory();
            endGameWhenHigherThan21();
            hitCounter++;
        }
    }

    private void displayCardsOnHand(){
        System.out.println("[PLAYER]");
        for(int cardCount = 0; cardCount < Hand.size(); cardCount++){
            System.out.print(Hand.get(cardCount).getCardSilhouette() + " " + Hand.get(cardCount).getCardTitle() + "\n");
        }
    }
    private void displayPlays(){
        displayedPlays = new int[6];
        subDisplayPlays();
        //Displaying and Searching for largestValue
        for(int realPlays = 0; realPlays < displayedPlays.length; realPlays++){
            if(displayedPlays[realPlays] > 0) System.out.print(displayedPlays[realPlays] + ", ");
            if(displayedPlays[realPlays] > largestValue && displayedPlays[realPlays] <= 21){
                largestValue = displayedPlays[realPlays];
            }
        }
        largestValueCheck(displayedPlays);
        System.out.println();
    }
    private void subDisplayPlays(){
        int holdValue = 0;

        for(int holdCount = 0; holdCount < potentialPlays.size(); holdCount++){ //run through potentialPlays<AL>
            boolean duplicate = false;
            holdValue = potentialPlays.get(holdCount).getCardValue();
            for(int playCount = 0; playCount < displayedPlays.length; playCount++){ //collate holdValue with displayPlays[A]
                if(displayedPlays[playCount] == holdValue){
                    //System.out.println("(DEBUG) Duplicate spotted! (" + holdValue + ")");
                    duplicate = true;
                    break;
                }
            }
            if(!duplicate){
                for(int stamp = 0; stamp < displayedPlays.length; stamp++){
                    if(displayedPlays[stamp] == 0){
                        displayedPlays[stamp] = holdValue;
                        break;
                    }
                }
            }
        }
    }
    private void displayAllPlays(){
        System.out.println();
        for(int displayCount = 0; displayCount < potentialPlays.size(); displayCount++){
            System.out.print(potentialPlays.get(displayCount).getCardValue() + ", ");
        }
        System.out.println();

    }

    // REMEMBER TO PEEL THE ONION. ONE PROBLEM AT A TIME.
    private void playLogistics(int pCardVal) {
        int holdTempValue;
        int newHalf;
        int currentPPSize;

        if(pCardVal == 1){
            createPPCards(potentialPlays.size());
            currentPPSize = potentialPlays.size();
            newHalf = (currentPPSize/2);

            for(int prevHalf = 0; prevHalf < (currentPPSize/2); prevHalf++, newHalf++){
                holdTempValue = potentialPlays.get(prevHalf).getCardValue();
                potentialPlays.get(prevHalf).setCardValue(holdTempValue + 1);
                potentialPlays.get(newHalf).setCardValue(holdTempValue + 11);
            }
        }
        else{
            for(int elementCount = 0; elementCount < potentialPlays.size(); elementCount++){
                holdTempValue = potentialPlays.get(elementCount).getCardValue();
                potentialPlays.get(elementCount).setCardValue(holdTempValue + pCardVal);
            }
        }
    }
    private void createPPCards(int pNewAdditions){
        for(int createCount = 0; createCount < pNewAdditions; createCount++){
            potentialPlays.add(new Cards());
        }
    }

    private void largestValueCheck(int [] pArray) {
        for (int count = 0; count < pArray.length; count++) {
            if (largestValue == pArray[count]) {
                secondRoundCheck = true;
                break;
            }
            else secondRoundCheck = false;
        }
        if(!secondRoundCheck){
            //losingValue = largestValue;
            largestValue = 0;
        }
    }
    private void whoWins(){
        if(largestValue == 0){
            System.out.println("Player: BUST" ); //Attempted to display value that led to BUST, but could not do so.
        } else System.out.println("Player: " + largestValue);
        if(dealer.getLargestValue() == 0){
            System.out.println("House: BUST" + "\n");
        } System.out.println("House: " + dealer.getLargestValue() +"\n");

        displayCardsOnHand();
        System.out.println();
        dealer.houseFrontalLobe(largestValue);

        if(largestValue == 0){
            System.out.println("\nPlayer: BUST");
        } else System.out.println("\nPlayer: " + largestValue);

        if(dealer.getLargestValue() == 0){
            System.out.println("House: BUST");
        } else System.out.println("House: " + dealer.getLargestValue());

        //House beats Player
        if(largestValue < dealer.getLargestValue()){
            System.out.println("[House Wins!]");
        }
        //Tie
        else if(largestValue == dealer.getLargestValue()){
            if(largestValue == 21){
                System.out.println("[Player Wins!]");
            }
            else System.out.println("[Tie!]");
        }
        //Player beats House
        else System.out.println("[Player Wins!]");
    }
    private void value21Victory(){
        if(largestValue == 21){
            System.out.println("PLAYER BLACKJACK!");
            winnerParameter = true;
        }
        else if(dealer.getLargestValue() == 21){
            System.out.println("HOUSE BLACKJACK!");
            winnerParameter = true;
        }
    }
    private void endGameWhenHigherThan21(){
        if(largestValue == 0){
            System.out.println("PLAYER BUST!");
            bustParameter = true;
        }
        else if(dealer.getLargestValue() == 0){
            System.out.println("HOUSE BUST!");
            bustParameter = true;
        }
    }
}