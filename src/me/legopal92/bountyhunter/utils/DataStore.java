package me.legopal92.bountyhunter.utils;

import java.util.List;

/**
 * Created by legop on 4/23/2016.
 */
public interface DataStore {

    public List<?> loadAllBounties();

    public void saveAllBounties(List<Bounty> bounties);

    public void saveAllPrivateBounties(List<PrivateBounty> bounties);

    public List<?> loadAllPrivateBounties();

    public void saveBounty(Bounty b);

    public void savePrivateBounty(PrivateBounty b);

    public void removePrivateBounty(PrivateBounty b);

}
