package rmsgx.rx.listener;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class lst implements Listener {

    public Player OnPlayerGive(String name){
        Player OnPlayer = Bukkit.getPlayerExact(name);
        return OnPlayer;
    }

    public OfflinePlayer OfPlayerGive(String name){
        OfflinePlayer OfPlayer = Bukkit.getOfflinePlayer(name);
        return OfPlayer;
    }

}
