package srk.mgstyles.gameofcards.Model;

import java.io.Serializable;
import java.util.ArrayList;


public class Table implements Serializable {
    public ArrayList<Cards> TableCards;

    public Table() {
        this.TableCards = new ArrayList();
    }

    public int getTableCardsCount() {
        return this.TableCards.size();
    }

    public void removeCardsFromTable(int count) {
        for (int i = 0; i < count; i++)
            this.TableCards.remove(TableCards.size() - 1);
    }

    public void putCardsOnTable(ArrayList<Cards> cards) {
        for (int i = 0; i < cards.size(); i++)
            this.TableCards.add(cards.get(i));
    }
}