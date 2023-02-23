package rmsgx.rx.commands.PrivateChat;

import com.google.gson.*;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import rmsgx.rx.PrivateChat;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PrivateChatDeleteChat{
    public boolean DeleteChat(CommandSender sender, Command command, String label, String[] args) {

    Player pl  = (Player) sender;
    pl.sendMessage(  ChatColor.GREEN + "Чат удалён");
    String fileName = "DataChat.json";

    if(sender instanceof Player){
        if((args.length == 1)){
            DeleteChat(pl.getDisplayName().toString(), fileName);
        }else {
            pl.sendMessage(ChatColor.RED + "Команда указа не правильно!");
        }
    }
    return false;
    }

    public JsonArray GetAllChats(String fileName){
        JsonParser parser = new JsonParser();
        JsonElement jElement = null;
        try {
            jElement = parser.parse(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JsonArray cArr = jElement.getAsJsonObject().get("Chat").getAsJsonArray();

        return cArr;

    };


    public void DeleteChat(String Owner, String fileName){
        try(Reader reader = new FileReader(fileName)){
            JsonParser parser = new JsonParser();

            JsonElement jsonElement = parser.parseReader(reader);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray cArr = jsonObject.getAsJsonArray("Chat");

            for(JsonElement cEl: cArr){
                JsonObject jsObj = cEl.getAsJsonObject();
                if(jsObj.get("Owner").getAsString().equals(Owner)){
                    cArr.remove(cEl);
                    break;
                }
            }

            try(FileWriter wr = new FileWriter(fileName)) {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                gson.toJson(jsonElement, wr);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
