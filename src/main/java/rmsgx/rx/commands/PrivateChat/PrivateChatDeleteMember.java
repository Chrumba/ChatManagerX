package rmsgx.rx.commands.PrivateChat;

import com.google.gson.*;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PrivateChatDeleteMember {

    public boolean DeleteMember(CommandSender sender, Command command, String label, String[] args) {
        String fileName = "DataChat.json";
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Player pl = (Player) sender;

        try (FileReader wr = new FileReader(fileName)){
            JsonParser parser = new JsonParser();

            JsonElement jsonElement = parser.parseReader(wr);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray cArr = jsonObject.getAsJsonArray("Chat");

            for(JsonElement cEl: cArr){
                JsonObject jsObj = cEl.getAsJsonObject();
                if(jsObj.get("Owner").getAsString().equals(pl.getDisplayName())){
                    JsonArray jsonArr = jsObj.get("Players").getAsJsonArray();

                    if(!pl.getDisplayName().equals(args[1])){
                        for(JsonElement name: jsonArr){
                            if(args[1].equals(name.getAsString())){
                                jsonArr.remove(name);

                                pl.sendMessage(ChatColor.GREEN + "Игрок " + ChatColor.YELLOW + name.getAsString() + ChatColor.GREEN + " удалён!");
                                break;
                            }
                        }

                        break;
                    }else{
                        pl.sendMessage(ChatColor.RED + "Нельзя удалить самого себя!");
                    }
                }
            }
            try(FileWriter ww = new FileWriter(fileName)) {
                gson.toJson(jsonElement, ww);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return false;
    }
}
