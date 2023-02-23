package rmsgx.rx;

import org.bukkit.plugin.java.JavaPlugin;
import rmsgx.rx.commands.PrivateChat.PrivateChatCreate;
import rmsgx.rx.commands.PrivateChat.PrivateChatMessage;
import rmsgx.rx.commands.PrivateMsg;
public final class rx extends JavaPlugin {

    @Override
    public void onEnable() {
        System.out.println("\n\nHI GITLER\n");

        getCommand("msg").setExecutor(new PrivateMsg());
        getCommand("chat").setExecutor(new PrivateChatCreate());
        getCommand("pm").setExecutor(new PrivateChatMessage());

    }

}
