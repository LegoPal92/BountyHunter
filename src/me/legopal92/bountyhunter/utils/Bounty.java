package me.legopal92.bountyhunter.utils;

import com.google.common.collect.Lists;
import me.legopal92.bountyhunter.BountyHunter;

import java.util.List;
import java.util.UUID;

/**
 * Created by legop on 4/21/2016.
 */
public class Bounty {

    private static List<Bounty> bounties = Lists.newArrayList();

    private UUID u;
    private int b;
    private int totalB;
    private List<PrivateBounty> privateBounties;

    public Bounty(UUID player, int bounty){
        this.privateBounties = Lists.newArrayList();
        this.u = player;
        this.b = bounty;
        this.totalB = this.b;
        Bounty.bounties.add(this);
    }

    public UUID getPlayer(){ return u; }

    public int getBounty(){ return b; }

    public int getTotalBounty(){ return totalB; }

    public void setBounty(int b){
        this.b = b;
        this.totalB = b;
    }

    public void setPrivateBounty(PrivateBounty pb){
        privateBounties.add(pb);
        this.totalB += pb.getAmt();
    }

    public void removePrivate(PrivateBounty pb){
        privateBounties.remove(pb);
        this.totalB -= pb.getAmt();
        BountyHunter.getInstance().getDataStore().removePrivateBounty(pb);
    }

    public PrivateBounty getPrivateBounty(UUID u){
        for (PrivateBounty pb : privateBounties){
            if (pb.getSender()==u){
                return pb;
            }
        }
        return null;
    }

    public List<PrivateBounty> getPrivateBounties(){ return privateBounties; }

    public static Bounty getByPlayer(UUID u){
        for (Bounty b : bounties){
            if (b.getPlayer()==u){
                return b;
            }
        }
        return null;
    }

    public static List<Bounty> getBounties(){
        return bounties;
    }

}