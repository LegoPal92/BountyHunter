package me.legopal92.bountyhunter.utils;

import com.google.common.collect.Lists;
import me.legopal92.bountyhunter.BountyHunter;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * Created by legop on 5/3/2016.
 */
public class ScoreBoardHelper {
    private List<UUID> activeBoards;

    private Scoreboard sb;
    private Objective o;

    public ScoreBoardHelper(){
        activeBoards = Lists.newArrayList();
        setupScoreboard(getFiveHighest());
        BountyHunter.getInstance().getServer().getScheduler().runTaskTimer(BountyHunter.getInstance(), new Runnable(){
            public void run(){
                setupScoreboard(getFiveHighest());
            }
        }, 1200L, 1200L);
    }

    public void setupScoreboard(List<Bounty> bounties){
        if (sb ==  null){
            sb = BountyHunter.getInstance().getServer().getScoreboardManager().getNewScoreboard();
            o = sb.registerNewObjective("dummy", "title");

            o.setDisplayName(ChatColor.GOLD + "Bounty Leaderboard");
        }

        for (Bounty b : bounties){
            o.getScore(ChatColor.RED + BountyHunter.getInstance().getServer().getPlayer(b.getPlayer()).getDisplayName()).setScore(b.getBounty());
        }

        o.setDisplaySlot(DisplaySlot.SIDEBAR);


        setBoards();
    }

    public List<Bounty> getFiveHighest(){
        List<Bounty> bounties = Lists.newArrayList();

        Bounty b = Collections.max(Bounty.getBounties(), new BountyComparator());
        List<Bounty> bounties1 = Bounty.getBounties();
        bounties1.remove(b);
        bounties.add(b);
        b = Collections.max(bounties1, new BountyComparator());
        bounties1.remove(b);
        bounties.add(b);
        b = Collections.max(bounties1, new BountyComparator());
        bounties1.remove(b);
        bounties.add(b);
        b = Collections.max(bounties1, new BountyComparator());
        bounties1.remove(b);
        bounties.add(b);
        b = Collections.max(bounties1, new BountyComparator());
        bounties1.remove(b);
        bounties.add(b);

        return bounties;
    }

    public void setBoards(){
        for (UUID u : activeBoards){
            Player player = BountyHunter.getInstance().getServer().getPlayer(u);
            player.setScoreboard(sb);
        }
    }

    public void showBoard(UUID u){
        activeBoards.add(u);
    }

    public void removeBoardView(UUID u){
        activeBoards.remove(u);
    }

    public List<UUID> getViewers(){
        return activeBoards;
    }
}
