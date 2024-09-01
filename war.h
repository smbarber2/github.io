
#define WAR_H

#include "deck.h"

class War {
private:
    Deck deck;
    std::vector<Card> player1;
    std::vector<Card> player2;                  //Vectors, methods for the deck
    bool playRound();
    void warRound();
    void giveCardsToWinner(bool player1Wins);
public:
    War();
    void playGame();
};
