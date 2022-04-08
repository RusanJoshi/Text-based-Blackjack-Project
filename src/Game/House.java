package Game;
import DeckOfCards.Cards;
import DeckOfCards.Deck;

import javax.swing.text.Element;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class House {
   Deck dealerDeck = new Deck();
   Random rand = new Random();
   Scanner typeWriter = new Scanner(System.in);
   private ArrayList<Cards> houseHand = new ArrayList<Cards>();
   private ArrayList<Cards> housePotentialPlays = new ArrayList<Cards>();
   private int[] houseDisplayedPlays  = new int[6];

   private int hitCounter = -2;
   private int largestValue = 0;
   boolean secondRoundCheck = false;

   public void pickUpDeck(){
      dealerDeck.deckBuilder();
   }
   public Cards getSingleCard(){
      Cards testCard;
      int randomChoice;
      do{
         randomChoice = rand.nextInt(51);
         testCard = dealerDeck.getDeck52(randomChoice);
      }while(!testCard.getCardAvailability());
      dealerDeck.changeAvailability(randomChoice);

      return testCard;
   }
   public Cards getSpecificCard(){
      Cards testCard;
      int choice;

      do{
         System.out.print("Value of card: ");
         choice = typeWriter.nextInt();
         testCard = dealerDeck.getDeck52(choice);
         if(!testCard.getCardAvailability()) System.out.println("[*Card taken] " + dealerDeck.getDeck52(choice).getCardSilhouette() + " " + dealerDeck.getDeck52(choice).getCardTitle());
      }while(!testCard.getCardAvailability());
      dealerDeck.changeAvailability(choice);

      return testCard;
   }

   private void dealHouse(){
      Cards tempCard = new Cards();
      tempCard = getSingleCard();
      //tempCard = getSpecificCard();
      houseHand.add(tempCard);

      housePlayLogistics(tempCard.getCardValue() + 1);
   }//Current Hits: RANDOM
   public void hitHouse(){
      dealHouse();
      hitCounter++;
   }

   public void houseDisplayCardsOnHand(int pDisplay){
      System.out.println("[HOUSE]");

      switch(pDisplay){
         case 0:
            for(int cardCount = 0; cardCount < 1; cardCount++){
               System.out.print(houseHand.get(cardCount).getCardSilhouette() + " " + houseHand.get(cardCount).getCardTitle() + "\n");
            }
            break;
         case 1:
            for(int cardCount = 0; cardCount < houseHand.size(); cardCount++){
               System.out.print(houseHand.get(cardCount).getCardSilhouette() + " " + houseHand.get(cardCount).getCardTitle() + "\n");
            }
            break;
      }
   }
   public void houseDisplayPlays(int pDisplay){
      houseDisplayedPlays = new int[6]; //resets the content of the array every time the method is executed
      subHouseDisplayPlays();
      switch(pDisplay) {
         case 0:
            //Searches for largestValue
            for (int realPlays = 0; realPlays < houseDisplayedPlays.length; realPlays++) {
               if (houseDisplayedPlays[realPlays] > largestValue && houseDisplayedPlays[realPlays] <= 21) {
                  largestValue = houseDisplayedPlays[realPlays];
               }
            }
            break;
         case 1:
            //Displaying and Searches for largestValue
            for (int realPlays = 0; realPlays < houseDisplayedPlays.length; realPlays++) {
               if (houseDisplayedPlays[realPlays] > 0) System.out.print(houseDisplayedPlays[realPlays] + ", ");
               if (houseDisplayedPlays[realPlays] > largestValue && houseDisplayedPlays[realPlays] <= 21) {
                  largestValue = houseDisplayedPlays[realPlays];
               }
            }
      }
      largestValueCheck();
   }
   private void subHouseDisplayPlays(){
      int holdValue = 0;
      for(int holdCount = 0; holdCount < housePotentialPlays.size(); holdCount++){ //run through potentialPlays<AL>
         boolean duplicate = false;
         holdValue = housePotentialPlays.get(holdCount).getCardValue();
         for(int playCount = 0; playCount < houseDisplayedPlays.length; playCount++){ //collate holdValue with displayPlays[A]
            if(houseDisplayedPlays[playCount] == holdValue){
               //System.out.println("(DEBUG) Duplicate spotted! (" + holdValue + ")");
               duplicate = true;
               break;
            }
         }
         if(!duplicate){
            for(int stamp = 0; stamp < houseDisplayedPlays.length; stamp++){
               if(houseDisplayedPlays[stamp] == 0){
                  houseDisplayedPlays[stamp] = holdValue;
                  break;
               }
            }
         }
      }
   }
   public void houseDisplayAllPlays(){
      for(int displayCount = 0; displayCount < housePotentialPlays.size(); displayCount++){
         System.out.print(housePotentialPlays.get(displayCount).getCardValue() + ", ");
      }
      System.out.println();
   }

   private void housePlayLogistics(int pCardVal){
      int holdTempValue;
      int newHalf;
      int currentPPSize;

      if(pCardVal == 1){
         createHousePPCards(housePotentialPlays.size());
         currentPPSize = housePotentialPlays.size();
         newHalf = (currentPPSize/2);

         for(int prevHalf = 0; prevHalf < (currentPPSize/2); prevHalf++, newHalf++){
            holdTempValue = housePotentialPlays.get(prevHalf).getCardValue();
            housePotentialPlays.get(prevHalf).setCardValue(holdTempValue + 1);
            housePotentialPlays.get(newHalf).setCardValue(holdTempValue + 11);
         }
      }
      else{
         for(int elementCount = 0; elementCount < housePotentialPlays.size(); elementCount++){
            holdTempValue = housePotentialPlays.get(elementCount).getCardValue();
            housePotentialPlays.get(elementCount).setCardValue(holdTempValue + pCardVal);
         }
      }
   }
   private void createHousePPCards(int pNewAdditions){
      for(int createCount = 0; createCount < pNewAdditions; createCount++){
         housePotentialPlays.add(new Cards());
      }
      //System.out.println("(DEBUG, HOUSE) Created " + pNewAdditions + " Card(s).");//REMOVE THIS
   }
   public void createOneTimeCard(){
      housePotentialPlays.add(new Cards());
   }
   private void largestValueCheck() {
      for (int count = 0; count < houseDisplayedPlays.length; count++) {
         if (largestValue == houseDisplayedPlays[count]) {
            secondRoundCheck = true;
            break;
         }
         else secondRoundCheck = false;
      }

      if(!secondRoundCheck){
         largestValue = 0;
      }
   }
   public int getLargestValue(){
      return largestValue;
   };

   public void houseFrontalLobe(int pPlayerLargestValue){
      if(largestValue <= 16 && pPlayerLargestValue > 0){
         System.out.println("House is <= 16. Drawing another card.");
         hitHouse();
      }
      houseDisplayCardsOnHand(1);
      houseDisplayPlays(0);
   }
   public void availabilityCheck(){
      int heart = 0;
      int diamond = 13;
      int spade = 26;
      int clover = 39;

      System.out.println();
      for(int count = 0; count < 13; count++, heart++, diamond++, spade++, clover++){
         //TODO: Remove from table is true
         System.out.println(
                 dealerDeck.getDeck52(heart).getCardSilhouette()+dealerDeck.getDeck52(heart).getCardTitle() + dealerDeck.getDeck52(heart).getCardAvailability() + " " +
                 dealerDeck.getDeck52(diamond).getCardSilhouette()+dealerDeck.getDeck52(diamond).getCardTitle() + dealerDeck.getDeck52(diamond).getCardAvailability() + " " +
                 dealerDeck.getDeck52(spade).getCardSilhouette()+dealerDeck.getDeck52(spade).getCardTitle() + dealerDeck.getDeck52(spade).getCardAvailability() + " " +
                 dealerDeck.getDeck52(clover).getCardSilhouette()+dealerDeck.getDeck52(clover).getCardTitle() + dealerDeck.getDeck52(clover).getCardAvailability());
      }
   }
}
