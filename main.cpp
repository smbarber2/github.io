
#include <iostream>
#include "war.h"

int main() {
    std::cout << "Hello. Want to go to war? (y/n)\n";           //yes or no prompt for starting the game
    char answer;
    std::cin >> answer;

    if (answer == 'y') {

        War warGame;
        warGame.playGame();         //Starts the game
        return 0;

    }
    else if (answer == 'n') {
        std::cout << "You value life and see war for the humanitarian issue it is. Press any key to continue.\n";
        std::cin.ignore(1);
    }
}