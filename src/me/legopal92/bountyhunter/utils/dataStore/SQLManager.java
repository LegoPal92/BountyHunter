package me.legopal92.bountyhunter.utils.dataStore;

import com.google.common.collect.Lists;
import me.legopal92.bountyhunter.BountyHunter;
import me.legopal92.bountyhunter.utils.Bounty;
import me.legopal92.bountyhunter.utils.DataStore;
import me.legopal92.bountyhunter.utils.PrivateBounty;
import me.legopal92.bountyhunter.utils.dataStore.mysql.MySQL;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

/**
 * Created by legop on 4/23/2016.
 */
public class SQLManager implements DataStore {

    private String host, user, pass, database;
    private int port;
    private MySQL mysql;

    public SQLManager(String host, int port, String user, String pass, String database){
        this.host = host;
        this.port = port;
        this.user = user;
        this.pass = pass;
        this.database = database;
        mysql = new MySQL(BountyHunter.getInstance(), host, port + "", database, user, pass);
        try {
            mysql.updateSQL("CREATE TABLE IF NOT EXISTS Public_Bounties (`name` varchar(36), `Bounty` int, PRIMARY KEY `name`);");
            mysql.updateSQL("CREATE TABLE IF NOT EXISTS Private_Bounties (`to` varchar(36), `from` varchar(36), `Bounty` int)");
        } catch (SQLException e){
            e.printStackTrace();
        }

    }

    @Override
    public List<Bounty> loadAllBounties() {
        List<Bounty> bounties = Lists.newArrayList();
        try {
            ResultSet res = mysql.querySQL("SELECT * FROM Public_Bounties;");
            while(res.next()){
                UUID u = UUID.fromString(res.getString("name"));
                int b = res.getInt("Bounty");
                bounties.add(new Bounty(u, b));
            }
            res.close();
        } catch (Exception e){
            e.printStackTrace();
        }
        return bounties;
    }

    @Override
    public void saveAllBounties(List<Bounty> bounties) {
        for (Bounty b : bounties){
            try {
                mysql.updateSQL("TRUNCATE TABLE Public_Bounties;");
                mysql.updateSQL("INSERT INTO Public_Bounties VALUES ('" + b.getPlayer().toString() + "', " + b.getBounty() + ");");
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public void saveAllPrivateBounties(List<PrivateBounty> bounties) {
        for (PrivateBounty b : bounties){
            try {
                mysql.updateSQL("TRUNCATE TABLE Private_Bounties;");
                mysql.updateSQL("INSERT INTO Private_Bounties VALUES ('" + b.getTarget().toString() + "', '" + b.getSender().toString() + "', " + b.getAmt() + ");");
            } catch(SQLException e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public List<PrivateBounty> loadAllPrivateBounties() {
        List<PrivateBounty> bounties = Lists.newArrayList();
        try {
            ResultSet res = mysql.querySQL("SELECT * FROM Private_Bounties;");
            while(res.next()){
                UUID u = UUID.fromString(res.getString("to"));
                UUID from = UUID.fromString(res.getString("from"));
                int b = res.getInt("Bounty");
                bounties.add(new PrivateBounty(from, u, b));
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return bounties;
    }

    @Override
    public void saveBounty(Bounty b) {
        try {
            mysql.updateSQL("INSERT INTO Public_Bounties VALUES ('" + b.getPlayer().toString() + "', " + b.getBounty() + ");");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void savePrivateBounty(PrivateBounty b) {
        try {
            mysql.updateSQL("INSERT INTO Private_Bounties VALUES ('" + b.getTarget().toString() + "', '" + b.getSender().toString() + "', " + b.getAmt() + ");");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public void removePrivateBounty(PrivateBounty b){
        try {
            mysql.updateSQL("DELETE FROM Private_Bounties WHERE to='" + b.getTarget().toString() + "' AND from= '" + b.getSender().toString() + "';");
        } catch(SQLException e){
            e.printStackTrace();
        }
    }
}
