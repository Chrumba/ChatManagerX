package rmsgx.rx.commands.PrivateChat;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PrivateChatGetAllMembers {

    public List<String> GetAllMembers(String fileName, String owner){
        List<String> AllMembers = new ArrayList<>();
        try(FileReader wr = new FileReader(fileName)){
            JsonParser parser = new JsonParser();

            JsonElement jsonElement = parser.parseReader(wr);
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonArray cArr = jsonObject.getAsJsonArray("Chat");
            for(JsonElement cEl: cArr){
                JsonObject jsObj = cEl.getAsJsonObject();
                if(jsObj.get("Owner").getAsString().equals(owner)){
                    JsonArray jsonArr = jsObj.get("Players").getAsJsonArray();
                    for(JsonElement jsEll: jsonArr){
                        AllMembers.add(jsEll.getAsString());
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return AllMembers;

    }
}
