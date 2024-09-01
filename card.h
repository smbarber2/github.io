
#define CARD_H

class Card {
private:
    int rank;
    int suit;
public:
    Card();
    Card(int r, int s);                         //Card rank, suit methods
    int getRank() const;
    int getSuit() const;
    bool operator>(const Card& other) const;
};

