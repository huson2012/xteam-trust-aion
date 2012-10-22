package com.light.gameserver.network.loginserver.serverpackets;

import com.light.gameserver.configs.network.NetworkConfig;
import com.light.gameserver.network.loginserver.LoginServerConnection;
import com.light.gameserver.network.loginserver.LsServerPacket;

/**
 * 
 * @author KID
 *
 */
public class SM_LS_PONG extends LsServerPacket {
	private int pid;
	
	public SM_LS_PONG(int pid) {
		super(12);
		this.pid = pid;
	}

	@Override
	protected void writeImpl(LoginServerConnection con) {
		writeC(NetworkConfig.GAMESERVER_ID);
		writeD(pid);
	}
}
