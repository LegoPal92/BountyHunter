package me.legopal92.bountyhunter.utils;

import java.util.UUID;

/**
 * Created by legop on 4/21/2016.
 */
public class PrivateBounty {


    private UUID sender, target;
    private int amt;

    public PrivateBounty(UUID sender, UUID target, int amt){
        this.sender = sender;
        this.target = target;
        this.amt = amt;
    }

    public UUID getSender(){ return sender; }

    public UUID getTarget(){ return target; }

    public int getAmt(){ return amt; }

    public void setAmt(int i){ this.amt = i; }

}
