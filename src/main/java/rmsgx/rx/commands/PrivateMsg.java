package rmsgx.rx.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrivateMsg implements CommandExecutor, TabCompleter {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player){
            Player p = (Player) sender;
            if(args.length == 0){
                p.sendMessage(ChatColor.RED+"Укажите имя пользователя");
            }else {
                Player OnPlayer = Bukkit.getPlayerExact(args[0]);
                if(OnPlayer != null||args[1]!=args[0]){
                    if(OnPlayer != p){
                        if(OnPlayer.isOnline()){
                            String mesg = "";
                            for(int i = 1; i < args.length; i++){
                                mesg = mesg+" "+args[i];
                            }
                            p.sendMessage(ChatColor.DARK_PURPLE +"["+ ChatColor.GRAY+ p.getDisplayName() + ChatColor.LIGHT_PURPLE + ">>" + ChatColor.GRAY+ OnPlayer.getDisplayName()+ChatColor.DARK_PURPLE+"]-->"+ChatColor.WHITE+mesg);
                            OnPlayer.sendMessage(ChatColor.DARK_PURPLE +"["+ ChatColor.GRAY+ OnPlayer.getDisplayName() + ChatColor.LIGHT_PURPLE + ">>" + ChatColor.GRAY+ p.getDisplayName()+ChatColor.DARK_PURPLE+"]-->"+ChatColor.WHITE+mesg);
                        }else {
                            p.sendMessage(ChatColor.RED + "НЕЛЬЯЗ ОТПРАВИТЬ СООБЩЕНИЕ ИРОКУ НЕ НА СЕРВЕРЕ");
                        }
                    }else{
                        p.sendMessage(ChatColor.RED + "НЕЛЬЯЗ ОТПРАВИТЬ СООБЩЕНИЕ СМАМУ СЕБЕ");
                    }

                }else{
                    p.sendMessage(ChatColor.RED + "НЕЛЬЯЗ ОТПРАВИТЬ СООБЩЕНИЕ ИРОКУ НЕ НА СЕРВЕРЕ");
                }
            }
        }
        return true;
    }
    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String alias, String[] args) {
        // Проверка, что команда была вызвана игроком
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }

        // Получение списка игроков на сервере
        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerNames.add(player.getName());
        }

        // Определение списка аргументов, которые должны быть предложены для автодополнения
        if (args.length == 1) {
            // Предложить список игроков на сервере
            return playerNames;
        } else {
            // Не предлагать автодополнение для других аргументов
            return Collections.emptyList();
        }
    }

}
