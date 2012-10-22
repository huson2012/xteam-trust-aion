package com.light.gameserver.network.loginserver.serverpackets;

import com.light.gameserver.configs.network.NetworkConfig;
import com.light.gameserver.model.ingameshop.IGRequest;
import com.light.gameserver.network.loginserver.LoginServerConnection;
import com.light.gameserver.network.loginserver.LsServerPacket;

/**
 * @author KID
 */
public class SM_PREMIUM_CONTROL extends LsServerPacket {
	private IGRequest request;
	private int cost;
	public SM_PREMIUM_CONTROL(IGRequest request, int cost) {
		super(11);
		this.request = request;
		this.cost = cost;
	}

	@Override
	protected void writeImpl(LoginServerConnection con) {
		writeD(request.accountId);
		writeD(request.requestId);
		writeD(cost);
		writeC(NetworkConfig.GAMESERVER_ID);
	}
}
