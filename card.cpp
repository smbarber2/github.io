#include "card.h"

Card::Card() {}

Card::Card(int r, int s) : rank(r), suit(s) {}

int Card::getRank() const {                         //Gets the card rank
    return rank;
}

int Card::getSuit() const {                         //Gets the card suit
    return suit;
}

bool Card::operator>(const Card& other) const {
    return rank > other.rank;
}