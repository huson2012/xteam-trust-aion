package admincommands;

import com.light.gameserver.model.Race;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.services.teleport.TeleportService;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.chathandlers.ChatCommand;
import com.light.gameserver.world.World;
import com.light.gameserver.world.WorldMapType;

/**
 * @author Centisgood(Barahime)
 */
public class HomeAll extends ChatCommand {

    public HomeAll() {
        super("homeall");
    }

    @Override
    public void execute(Player admin, String... params) {
        if (params == null || params.length > 0){
            dd(admin);
            return;
        }
        if (params[0].equals("all")) {
            for (Player pp : World.getInstance().getAllPlayers()) {
                if (pp.getCommonData().getRace() == Race.ELYOS) {
                    TeleportService.teleportTo(pp, WorldMapType.SANCTUM.getId(), 1322, 1511, 568, 0);
                    PacketSendUtility.sendYellowMessageOnCenter(pp, "Вы переноситесь в столицу Элийцев.");
                } else if (pp.getCommonData().getRace() == Race.ASMODIANS) {
                    TeleportService.teleportTo(pp, WorldMapType.PANDAEMONIUM.getId(), 1679, 1400, 195, 0);
                    PacketSendUtility.sendYellowMessageOnCenter(pp, "Вы переноситесь в столицу Асмодиан.");
                }
            }
        } else if (params[0].equals("loc")) {
            for (Player pp : World.getInstance().getAllPlayers()) {
                if (pp.getWorldId() == admin.getWorldId()) {
                    if (pp.getCommonData().getRace() == Race.ELYOS) {
                        TeleportService.teleportTo(pp, WorldMapType.SANCTUM.getId(), 1322, 1511, 568, 0);
                        PacketSendUtility.sendYellowMessageOnCenter(pp, "Вы переноситесь в столицу Элийцев.");
                    } else if (pp.getCommonData().getRace() == Race.ASMODIANS) {
                        TeleportService.teleportTo(pp, WorldMapType.PANDAEMONIUM.getId(), 1679, 1400, 195, 0);
                        PacketSendUtility.sendYellowMessageOnCenter(pp, "Вы переноситесь в столицу Асмодиан.");
                    }
                }
            }
        }
    }

    public void dd(Player player){
        PacketSendUtility.sendMessage(player, "syntax: //homeall <all | loc> ");
        
    }
    
    @Override
    public void onFail(Player player, String message) {
         PacketSendUtility.sendMessage(player, "syntax: //homeall <all | loc> ");
         return;
    }
}