package me.legopal92.bountyhunter.utils;

import java.util.Comparator;

/**
 * Created by legop on 5/3/2016.
 */
public class BountyComparator implements Comparator<Bounty> {

    @Override
    public int compare(Bounty b1, Bounty b2){
        return b1.getTotalBounty()-b2.getTotalBounty();
    }
}
