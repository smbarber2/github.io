#include "war.h"
#include <iostream>

int playerOneWins = 0;
int playerTwoWins = 0;

War::War() {
    deck.shuffle();
    for (int i = 0; i < 26; ++i) {
        player1.push_back(deck.dealCard());     //Deals the cards to the players. Shuffle is weird
        player2.push_back(deck.dealCard());
    }
}

bool War::playRound() {
    Card card1 = player1.back();
    player1.pop_back();
    Card card2 = player2.back();
    player2.pop_back();

    std::cout << "Player 1 plays: " << card1.getRank() << std::endl;            //Shows what the players play in the round
    std::cout << "Player 2 plays: " << card2.getRank() << std::endl;

    if (card1 > card2) {
        std::cout << "Player 1 wins the round!\n" << std::endl;
        playerOneWins++;
        player1.insert(player1.begin(), card1);
        player1.insert(player1.begin(), card2);

        std::cout << "Continue? (y/n)\n";           //yes or no prompt for starting the game
        char answer;
        std::cin >> answer;

        if (answer == 'y') {
            return true;

        }
        else if (answer == 'n') {
            std::cout << "Game over\n";
            if (playerOneWins > playerTwoWins) {
                std::cout << "Player 1 wins with " << player1.size() << " cards!\n";        ///Player 1 wins
                std::cout << "Player 2 had " << player2.size() << " cards.\n";
                exit(0);
            }
            else if (playerTwoWins > playerOneWins) {
                std::cout << "Player 2 wins with " << player2.size() << " cards!\n";        //Player 2 wins
                std::cout << "Player 1 had " << player1.size() << " cards.\n";
                exit(0);
            }
            else {
                std::cout << "Draw!";
                exit(0);

            }

        }

    }
    else if (card2 > card1) {
        std::cout << "Player 2 wins the round!\n" << std::endl;         //Who wins the round
        playerTwoWins++;
        player2.insert(player2.begin(), card2);
        player2.insert(player2.begin(), card1);

        std::cout << "Continue? (y/n)\n";           //yes or no prompt for starting the game
        char answer;
        std::cin >> answer;

        if (answer == 'y') {
            return true;

        }
        else if (answer == 'n') {
            std::cout << "Game over\n";
            if (playerOneWins > playerTwoWins) {
                std::cout << "Player 1 wins with " << player1.size() << " cards!\n";        ///Player 1 wins
                std::cout << "Player 2 had " << player2.size() << " cards.\n";
                exit(0);
            }
            else if (playerTwoWins > playerOneWins) {
                std::cout << "Player 2 wins with " << player2.size() << " cards!\n";        //Player 2 wins
                std::cout << "Player 1 had " << player1.size() << " cards.\n";
                exit(0);
            }
            else {
                std::cout << "Draw!";
                exit(0);

            }

        }
    }
    else {
        std::cout << "Draw. War!\n" << std::endl;                   //Draw

        std::cout << "Continue? (y/n)\n";           //yes or no prompt for starting the game
        char answer;
        std::cin >> answer;

        if (answer == 'y') {
            return true;
            warRound();

        }
        else if (answer == 'n') {
            std::cout << "Game over.\n";

            if (playerOneWins > playerTwoWins) {
                std::cout << "Player 1 wins with " << player1.size() << " cards!\n";        ///Player 1 wins
                std::cout << "Player 2 had " << player2.size() << " cards.\n";
                exit(0);
            }
            else if (playerTwoWins > playerOneWins) {
                std::cout << "Player 2 wins with " << player2.size() << " cards!\n";        //Player 2 wins
                std::cout << "Player 1 had " << player1.size() << " cards.\n";
                exit(0);
            }
            else {
                std::cout << "Draw!";
                exit(0);
            
            }

        }
    }
}

void War::warRound() {                                                      //See if there are enough cards left for war
    if (player1.size() < 4 || player2.size() < 4) {
        std::cout << "Not enough cards for war. Game over.\n" << std::endl;
        if (playerOneWins > playerTwoWins) {
            std::cout << "Player 1 wins with " << player1.size() << " cards!\n";        ///Player 1 wins
            std::cout << "Player 2 had " << player2.size() << " cards.\n";
                exit(0);
        }
        else if (playerTwoWins > playerOneWins) {
            std::cout << "Player 2 wins with " << player2.size() << " cards!\n";        //Player 2 wins
            std::cout << "Player 1 had " << player1.size() << " cards.\n";
            exit(0);
        }
        exit(0);
    }

    std::cout << "Dealing additional cards for war..." << std::endl;        //Deals additional cards for the game to continue. Why doesn't it continue though
    for (int i = 0; i < 3; ++i) {
        player1.pop_back();
        player2.pop_back();
    }

    playRound();        //Play again
}

void War::giveCardsToWinner(bool player1Wins) {                         //Gives the cards to the winner
    if (player1Wins) {
        player1.insert(player1.begin(), player2.begin(), player2.end());
        player2.clear();
    }
    else {
        player2.insert(player2.begin(), player1.begin(), player1.end());
        player1.clear();
    }
}

void War::playGame() {          //Game will always continue until it ends? At least it works now. This is... theorhetically obsolete but still needed.
    bool continueGame = true;
    while (continueGame) {
        continueGame = playRound();
        if (player1.empty()) {
            std::cout << "Player 1 wins with " << player1.size() << " cards!\n";        ///Player 1 wins
            std::cout << "Player 2 had " << player2.size() << " cards.\n";
            continueGame = false;
        }
        else if (player2.empty()) {
            std::cout << "Player 2 wins with " << player2.size() << " cards!\n";       //Player 2 wins
            std::cout << "Player 1 had " << player1.size() << " cards.\n";
            continueGame = false;
        }
    }
}