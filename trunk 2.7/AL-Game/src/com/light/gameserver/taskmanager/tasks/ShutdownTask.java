/*
 * This file is part of aion-unique <aion-unique.org>.
 *
 *  aion-unique is free software: you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation, either version 3 of the License, or
 *  (at your option) any later version.
 *
 *  aion-unique is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with aion-unique.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.light.gameserver.taskmanager.tasks;

import org.slf4j.Logger; import org.slf4j.LoggerFactory;

import com.light.gameserver.ShutdownHook;
import com.light.gameserver.ShutdownHook.ShutdownMode;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.model.templates.tasks.TaskFromDBHandler;
import com.light.gameserver.utils.PacketSendUtility;
import com.light.gameserver.utils.ThreadPoolManager;
import com.light.gameserver.world.World;
import com.light.gameserver.world.knownlist.Visitor;

/**
 * @author Divinity
 */
public class ShutdownTask extends TaskFromDBHandler {

	private static final Logger log = LoggerFactory.getLogger(ShutdownTask.class);

	private int countDown;
	private int announceInterval;
	private int warnCountDown;

	@Override
	public String getTaskName() {
		return "shutdown";
	}

	@Override
	public boolean isValid() {
		return params.length == 3;

	}

	@Override
	public void run() {
		log.info("Task[" + id + "] launched : shuting down the server !");
		setLastActivation();

		countDown = Integer.parseInt(params[0]);
		announceInterval = Integer.parseInt(params[1]);
		warnCountDown = Integer.parseInt(params[2]);

		World.getInstance().doOnAllPlayers(new Visitor<Player>() {

			@Override
			public void visit(Player player) {
				PacketSendUtility.sendBrightYellowMessageOnCenter(player, "Авто отключение: Сервер будет отключен через " + warnCountDown
					+ " секунд ! Пожалуйста завершите игру.");
			}
		});

		ThreadPoolManager.getInstance().schedule(new Runnable() {

			@Override
			public void run() {
				ShutdownHook.getInstance().doShutdown(countDown, announceInterval, ShutdownMode.SHUTDOWN);
			}
		}, warnCountDown * 1000);
	}
}
