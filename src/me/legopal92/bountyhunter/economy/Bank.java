package me.legopal92.bountyhunter.economy;

import me.legopal92.bountyhunter.BountyHunter;

import java.util.UUID;

/**
 * Created by legop on 4/8/2016.
 */
public class Bank {

    /**
     * Craetes a bank with Vault.
     */
    public Bank(){
        BountyHunter.getInstance().getEconomy().createBank("BH", "BH");
    }

    /**
     * Join the bank with a starting amount of money. Default 100.
     * @param u - The player's UUID.
     */
    public static void join(UUID u){
        join(u, 100);
    }

    /**
     * Join the bank with a starting amount of money.
     * @param u - The player's UUID.
     * @param startingBalance - The starting balance.
     */
    public static void join(UUID u, double startingBalance){
        BountyHunter.getInstance().getEconomy().depositPlayer(BountyHunter.getInstance().getServer().getOfflinePlayer(u), startingBalance);
    }

    /**
     * Checks to see if the player has enough money to do the thing.
     * @param u - The player's UUID.
     * @param h - The amount in question.
     * @return - true if the person has enough money.
     */
    public static boolean has(UUID u, double h){
        return BountyHunter.getInstance().getEconomy().has(BountyHunter.getInstance().getServer().getOfflinePlayer(u), h);

    }

    public static boolean isInBank(UUID u){
        return BountyHunter.getInstance().getEconomy().hasAccount(BountyHunter.getInstance().getServer().getOfflinePlayer(u));
    }

    /**
     * Deposits money into the player's account.
     * @param u - The player's UUId.
     * @param d - The amount to deposit.
     */
    public static void deposit(UUID u, double d){
        BountyHunter.getInstance().getEconomy().depositPlayer(BountyHunter.getInstance().getServer().getOfflinePlayer(u), d);
    }

    /**
     * Withdraws money from the player's account.
     * @param u - The player's UUID.
     * @param d - The amount to withdraw.
     */
    public static void withdraw(UUID u, double d){
        BountyHunter.getInstance().getEconomy().withdrawPlayer(BountyHunter.getInstance().getServer().getOfflinePlayer(u), d);
    }
}
