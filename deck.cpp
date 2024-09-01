
#include <algorithm>
#include <random>
#include <chrono>
#include <iostream>
#include "deck.h"

Deck::Deck() {
    for (int suit = 0; suit < 4; ++suit) {                  //Full deck, 52 cards
        for (int rank = 1; rank <= 13; ++rank) {
            cards.push_back(Card(rank, suit));
        }
    }
}

void Deck::shuffle() {
    auto rng = std::default_random_engine{};
    rng.seed(std::chrono::system_clock::now().time_since_epoch().count());  //shuffling seed takes current time to make it truly random
    std::shuffle(cards.begin(), cards.end(), rng);

}

Card Deck::dealCard() {
    Card topCard = cards.back();         //Deals the cards
    cards.pop_back();
    return topCard;
}

int Deck::size() const {
    return cards.size();
}