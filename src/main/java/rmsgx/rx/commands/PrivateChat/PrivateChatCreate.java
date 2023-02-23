package rmsgx.rx.commands.PrivateChat;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import rmsgx.rx.PrivateChat;
import rmsgx.rx.PrivateChat.Data;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import rmsgx.rx.PrivateChat.*;

public class PrivateChatCreate implements CommandExecutor, TabCompleter{

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player pl = (Player) sender;
        String fileName = "DataChat.json";
        boolean owner_b = false;
        List<String> allOwners;
        List<String> AllUsers = new ArrayList<>();

        if(sender instanceof Player ){
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            File file = new File(fileName);
            List<String> playerList = new ArrayList<>();


            if(file.exists()){
                allOwners = PrivateChat.GetAllOwners(fileName);
                for (String owner : allOwners) {
                    if (pl.getDisplayName().equals(owner)) {
                        owner_b = true;
                    }
                }



                for (String owner: allOwners){
                    AllUsers.add(new PrivateChatGetAllMembers().GetAllMembers(fileName, owner).toString());
                    AllUsers.add(owner);
                }

            }


            if(args.length == 0){
                if(!file.exists()) {
                    pl.sendMessage(ChatColor.GREEN + "Чат создан");

                    try {
                        file.createNewFile();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    PrivateChat.ChatDataObject DataObject = new PrivateChat.ChatDataObject(pl.getDisplayName(), playerList);
                    List<PrivateChat.ChatDataObject> dataList = new ArrayList<>();
                    dataList.add(DataObject);
                    Data data = new Data(dataList);

                    try (FileWriter wr = new FileWriter(fileName)) {
                        gson.toJson(data, wr);
                    } catch (IOException e) {
                    }
                }

                allOwners = PrivateChat.GetAllOwners(fileName);
                for (String owner : allOwners) {
                    if (pl.getDisplayName().equals(owner)) {
                        owner_b = true;
                    }
                }

                if(!owner_b){
                        String json;
                        try {
                            json = new String(Files.readAllBytes(Paths.get(fileName)));

                        } catch (IOException e) {

                            throw new RuntimeException(e);
                        }

                        JsonObject jsObj = new Gson().fromJson(json, JsonObject.class);
                        JsonArray jsArr = jsObj.getAsJsonArray("Chat");

                        JsonObject chat = new JsonObject();
                        chat.addProperty("Owner", pl.getDisplayName());
                        chat.add("Players", new JsonArray());

                        jsArr.add(chat);

                        try {
                            Files.write(Paths.get(fileName), jsObj.toString().getBytes());
                            pl.sendMessage(ChatColor.GREEN+"Чат создан с владельцем: "+pl.getDisplayName());

                        } catch (IOException e) {
                            System.out.println("Ошибка записи файла: " + e.getMessage());
                        }
                    }else{
                    pl.sendMessage(ChatColor.RED + "У тебя уже есть созданый чат!");
                }
            }else if(args[0].equals("delete")){
                if(owner_b){
                    new PrivateChatDeleteChat().DeleteChat(sender,command,label,args);
                }else {
                    pl.sendMessage(ChatColor.RED + "У тебя нет приватного чата!");
                }
            } else if (args[0].equals("add")) {
                if(owner_b){
                    if(Bukkit.getPlayerExact(args[1]).isOnline()){
                        if(!AllUsers.contains(args[1])){
                            new PrivateChatAddMember().AddMember(sender,command,label,args);
                        }else {
                        pl.sendMessage( ChatColor.RED + "Игрок " + ChatColor.YELLOW + args[1] + ChatColor.RED + " уже состоит в приватном чате!" );
                        }


                    }else {
                        pl.sendMessage(ChatColor.RED + "Игрок не на сервере!");
                    }
                }else {
                    pl.sendMessage(ChatColor.RED + "У тебя нет приватного чата!");
                }
            } else if (args[0].equals("members")) {
                if(owner_b){
                    List<String> members = new PrivateChatGetAllMembers().GetAllMembers(fileName, pl.getDisplayName());
                    StringBuilder res = new StringBuilder();

                    for(String name: members){
                        res.append(", ").append(ChatColor.YELLOW).append(name);
                    }
                    pl.sendMessage(ChatColor.GREEN + "Участники чата " + res);
                }else {
                    pl.sendMessage(ChatColor.RED + "У тебя нет приватного чата!");
                }

            } else if (args[0].equals("member_delete")) {
                if(owner_b){
                    new PrivateChatDeleteMember().DeleteMember(sender,command,label,args);
                    Bukkit.getPlayerExact(args[1]).sendMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + pl.getDisplayName() + ChatColor.GREEN + " удалил вас из приватного чата!");
                }else {
                    pl.sendMessage(ChatColor.RED + "У тебя нет приватного чата!");
                }


            }
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return Collections.emptyList();
        }
        List<String> listCom = new ArrayList<String>();
        listCom.add("delete");
        listCom.add("add");
        listCom.add("members");
        listCom.add("member_delete");

        List<String> playerNames = new ArrayList<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            playerNames.add(player.getName());
        }

        if (args.length == 1) {
            return listCom;
        }else if(args.length > 1){
            return playerNames;
        }else {
            return Collections.emptyList();
        }
    }
}