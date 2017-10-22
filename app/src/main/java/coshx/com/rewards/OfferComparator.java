package coshx.com.rewards;

import java.util.Comparator;

import coshx.com.rewards.model.Offer;

/**
 * Created by MSMD on 10/22/17.
 */

public class OfferComparator implements Comparator<Offer> {
    @Override
    public int compare(Offer o1, Offer o2) {
        return o1.getIndex().compareTo(o2.getIndex());
    }
}
