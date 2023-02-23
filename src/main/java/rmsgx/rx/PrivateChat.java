package rmsgx.rx;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

public class PrivateChat {
    private void CreateChat(Player owner, List<Player> PlayersLsit, JsonArray json){

    }


    public static class ChatDataObject {
        private final String Owner;
        private final List<String> Players;

        public  ChatDataObject(String Owner, List<String> Players) {
            this.Owner = Owner;
            this.Players = Players;
        }


        public Object getOwner() {
            return Owner;
        }

    }
    public static class Data {
        private final List<ChatDataObject> Chat;

        public Data(List<ChatDataObject> Chat) {
            this.Chat = Chat;
        }
    }


    public static List<String> GetAllOwners(String fileName){
        JsonParser parser = new JsonParser();
        JsonElement jElement = null;
        try {
            jElement = parser.parse(new FileReader(fileName));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        JsonArray cArr = jElement.getAsJsonObject().get("Chat").getAsJsonArray();
        List<String> ownerList = new ArrayList<>();
        for (JsonElement cEl: cArr){
            JsonObject chatObj= cEl.getAsJsonObject();
            ownerList.add(chatObj.get("Owner").getAsString());
        }

        return  ownerList;
    }


}
