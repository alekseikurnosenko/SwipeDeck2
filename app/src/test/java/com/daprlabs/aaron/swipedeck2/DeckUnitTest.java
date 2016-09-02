package com.daprlabs.aaron.swipedeck2;
import android.support.v7.widget.CardView;

import com.daprlabs.aaron.swipedeck.Utility.Deck;

import org.junit.Test;

import static org.junit.Assert.*;
/**
 * Created by aaron on 31/08/2016.
 */
public class DeckUnitTest {

    private Object callbackObj;
    private int callbackInt;

    Deck deck = new Deck(new Deck.ListEventListener() {
        @Override
        public void itemAdded(Object object, int position) {
            callbackObj = object;
            callbackInt = position;
        }

        @Override
        public void itemRemoved(Object object) {
            callbackObj = object;
            callbackInt = -1;
        }

        @Override
        public void itemPositionChanged(Object object, int position) {
            callbackObj = object;
            callbackInt = position;
        }
    });

    @Test
    public void card_isAdded(){
        deck.clear();
        String mockCard = new String("Card1");
        deck.add(mockCard);
        assertTrue(deck.contains(mockCard));
    }

    @Test
    public void card_addTriggersCallback(){
        deck.clear();
        String mockCard = new String("Card1");
        deck.add(mockCard);
        assertTrue(callbackObj == mockCard && callbackInt == 0);
    }

}
