
#define DECK_H

#include <stack>
#include <memory>
#include <vector>
#include <string>
#include "card.h"

class Deck {
private:
    std::vector<Card> cards;
public:
    Deck();
    void shuffle();             //constructor, shuffle function, dealing method, size()
    Card dealCard();
    int size() const;
};

