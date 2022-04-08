package Game;

import DeckOfCards.Deck;

/*
@author Rusan Joshi
"Red Five standing by."
Blackjack v1.1
04/07/22
 */
public class MainDriver {
    public static void main(String [] args){
        Player player = new Player();

        player.playGame();
    }
}

/*
 Additions to make:
 [PLAYER]-
 [X] If Player busts and House is <=16, do not draw card. P:3/23-F:3/24
 [ ] Hide realPlays above 21 P:3/24
 [HOUSE]-
 [X] First card face up, second card face down. P:3/23-F:3/23
 [X] Once player is finished, flip second card face up. F:3/23
 [X] If <= 16, then hit house. If >= 17, then house stays. F:3/23
 [ ] If Player InstantWins, then do not draw another card if houseValue is < 16 P:3/24
 [X] If BLACKJACK Tie, then Player wins P:3/24
 [MISCELLANEOUS]-
 [X] Change Face card values to 10 F:4/7
 [X] Instant winning parameter (Value = 21) P:3/23-F:3/24
 [X] End game if Player or House is > 21 P:3/23-F:3/27
 [ ] If Player/House bust, display bust instead of value 0 P:3/24
 [ ] Implement exception handling P:3/24 (May not be necessary)
 [ ] Ask player if they would like to play again. P:3/27

 For me:
 DECK52 Values
Ace: 0, 13, 26, 39
  2: 1, 14, 27, 40

  Version History
  v1.0 : 03/27/22
  v1.1 : 04/07/22
 */
