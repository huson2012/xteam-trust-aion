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
package com.light.gameserver.network.aion.serverpackets;


import com.light.gameserver.model.gameobjects.Creature;
import com.light.gameserver.network.aion.AionConnection;
import com.light.gameserver.network.aion.AionServerPacket;
import com.light.gameserver.model.gameobjects.player.Player;
import com.light.gameserver.utils.PacketSendUtility;

/**
 * @author Sweetkr, xTz
 */
public class SM_TRANSFORM extends AionServerPacket {

	private Creature creature;
	private int state;
	private int modelId;
	private boolean applyEffect;
	private int panelId;

	public SM_TRANSFORM(Creature creature, boolean applyEffect) {
		this.creature = creature;
		this.state = creature.getState();
		modelId = creature.getTransformedModelId();
		this.applyEffect = applyEffect;
	}

	public SM_TRANSFORM(Creature creature, int panelId, boolean applyEffect) {
		this.creature = creature;
		this.state = creature.getState();
		modelId = creature.getTransformedModelId();
		this.panelId = panelId;
		this.applyEffect = applyEffect;
	}

	@Override
	protected void writeImpl(AionConnection con) {
		writeD(creature.getObjectId());
		writeD(creature.getTransformedModelId());
		writeH(state);
		writeF(0.55f);
		writeF(1.5f);
		writeC(0);
		writeD(1);
        writeD(0);
        writeH(0);
        writeC(0);
		
		Player player;
		try
		{
			player = (Player) creature;
		}
		catch(ClassCastException e)
		{
			return;
		}

		int raceId = player.getCommonData().getRace().getRaceId();

		switch(creature.getTransformedModelId())
		{
			case 0:
				switch(player.getAbyssRank().getRank().getId())
				{
					case 14:
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11895, false));
						break;
					case 15:
						if(raceId == 0)
						{
							PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11899, false));
						}
						else
						{
							PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11901, false));
						}
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11896, false));
						break;
					case 16:
						if(raceId == 0)
						{
							PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11899, false));
						}
						else
						{
							PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11901, false));
						}
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11897, false));
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11903, false));
						break;
					case 17:
						if(raceId == 0)
						{
							PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11900, false));
						}
						else
						{
							PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11902, false));
						}
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11898, false));
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11903, false));
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11904, false));
						break;
					case 18:
						if(raceId == 0)
						{
							PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11900, false));
						}
						else
						{
							PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11902, false));
						}
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11898, false));
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11903, false));
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11904, false));
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11905, false));
						PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11906, false));
						break;
				}
				break;
			case 202502: // trans I Elyos
			case 202507: // trans I Asmo
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11895, true));
				break;
			case 202503: // trans II Elyos
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11896, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11899, true));
				break;
			case 202508: // trans II Asmo
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11896, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11901, true));
				break;
			case 202504: // trans III Elyos
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11899, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11897, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11903, true));
				break;
			case 202509: // trans III Asmo
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11901, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11897, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11903, true));
				break;
			case 202505: // trans IV Elyos
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11900, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11898, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11903, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11904, true));
				break;
			case 202510: // trans IV Asmo
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11902, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11898, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11903, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11904, true));
				break;
			case 202506: // trans V Elyos
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11900, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11898, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11903, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11904, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11905, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11906, true));
				break;
			case 202511: // trans V Asmo
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11902, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11898, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11903, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11904, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11905, true));
				PacketSendUtility.sendPacket(player, new SM_SKILL_ACTIVATION(11906, true));
				break;
		}
	}
}