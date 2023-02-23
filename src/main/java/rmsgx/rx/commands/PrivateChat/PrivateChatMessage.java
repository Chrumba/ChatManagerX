package rmsgx.rx.commands.PrivateChat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import rmsgx.rx.PrivateChat;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrivateChatMessage implements CommandExecutor, TabCompleter {


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player pl = (Player) sender;
        String fileName = "DataChat.json";

        List<String> allOwners = PrivateChat.GetAllOwners(fileName);
        List<String> AllUsers = new ArrayList<>();

        for (String owner: allOwners){
            AllUsers.add(new PrivateChatGetAllMembers().GetAllMembers(fileName, owner).toString());
            AllUsers.add(owner);
        }


        if(sender instanceof Player){
            if(!(args.length == 0)){
                if(AllUsers.indexOf(pl.getDisplayName())!=-1){
                    List<String> members = new PrivateChatGetAllMembers().GetAllMembers(fileName, pl.getDisplayName());
                    String msg = "";
                    for(String piece: args){
                        msg = msg + " " +piece;
                    }
                    pl.sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.YELLOW + "ПЧ: " + pl.getDisplayName() + ChatColor.LIGHT_PURPLE + " >> " + ChatColor.DARK_PURPLE + "] " + ChatColor.WHITE + msg);
                    for(String member: members){
                        Player player = Bukkit.getPlayerExact(member);
                        player.sendMessage(ChatColor.DARK_PURPLE + "[" + ChatColor.YELLOW + "ПЧ: " + ChatColor.YELLOW + pl.getDisplayName() + ChatColor.LIGHT_PURPLE + " >> " + ChatColor.DARK_PURPLE + "] " + ChatColor.WHITE + msg);
                    }

                }else {
                    pl.sendMessage(ChatColor.RED + "Ты не сосотоишь в приватном чате!");
                }

            }else {
                pl.sendMessage(ChatColor.RED + "Укажи сообщение!");
            }
        }


        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        return Collections.emptyList();
    }
}
