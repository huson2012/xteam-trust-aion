/*
 * This file is part of aion-unique <aionu-unique.com>.
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
package mysql5;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.aionemu.commons.database.DB;
import com.aionemu.commons.database.DatabaseFactory;
import com.aionemu.gameserver.dao.InventoryDAO;
import com.aionemu.gameserver.model.gameobjects.Item;
import com.aionemu.gameserver.model.gameobjects.PersistentState;
import com.aionemu.gameserver.model.gameobjects.player.Equipment;
import com.aionemu.gameserver.model.gameobjects.player.Player;
import com.aionemu.gameserver.model.gameobjects.player.Storage;
import com.aionemu.gameserver.model.gameobjects.player.StorageType;

/**
 * @author ATracer
 * 
 */
public class MySQL5InventoryDAO extends InventoryDAO
{
	private static final Logger log = Logger.getLogger(MySQL5InventoryDAO.class);

	public static final String SELECT_QUERY = "SELECT `itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `isEquiped`, `isSoulBound`, `slot`, `enchant`, `itemSkin` FROM `inventory` WHERE `itemOwner`=? AND `itemLocation`=? AND `isEquiped`=?";
	public static final String INSERT_QUERY = "INSERT INTO `inventory` (`itemUniqueId`, `itemId`, `itemCount`, `itemColor`, `itemOwner`, `isEquiped`, isSoulBound, `slot`, `itemLocation`, `enchant`, `itemSkin`) VALUES(?,?,?,?,?,?,?,?,?,?, ?)";
	public static final String UPDATE_QUERY = "UPDATE inventory SET  itemCount=?, itemColor=?, itemOwner=?, isEquiped=?, isSoulBound=?, slot=?, itemLocation=?, enchant=?, itemSkin=? WHERE itemUniqueId=?";
	public static final String DELETE_QUERY = "DELETE FROM inventory WHERE itemUniqueId=?";
	public static final String DELETE_CLEAN_QUERY = "DELETE FROM inventory WHERE itemOwner=? AND (itemLocation=0 OR itemLocation=1)";
	public static final String SELECT_ACCOUNT_QUERY = "SELECT `account_id` FROM `players` WHERE `id`=?";

	@Override
	public Storage loadStorage(Player player, StorageType storageType)
	{
		final Storage inventory = new Storage(player, storageType);
		int playerId = player.getObjectId();
		final int storage = storageType.getId();
		final int equipped = 0;

		if(storageType == StorageType.ACCOUNT_WAREHOUSE)
		{
			playerId = getPlayerAccountId(playerId);
		}

		final int owner = playerId;
		Connection con = null;
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(SELECT_QUERY);
			stmt.setInt(1, owner);
			stmt.setInt(2, storage);
			stmt.setInt(3, equipped);
			ResultSet rset = stmt.executeQuery();
			while(rset.next())
			{
				int itemUniqueId = rset.getInt("itemUniqueId");
				int itemId = rset.getInt("itemId");
				long itemCount = rset.getLong("itemCount");
				int itemColor = rset.getInt("itemColor");
				int isEquiped = rset.getInt("isEquiped");
				int isSoulBound = rset.getInt("isSoulBound");
				int slot = rset.getInt("slot");
				int enchant = rset.getInt("enchant");
				int itemSkin = rset.getInt("itemSkin");
				Item item = new Item(itemUniqueId, itemId, itemCount, itemColor, isEquiped == 1, isSoulBound == 1,slot, storage, enchant, itemSkin);
				item.setPersistentState(PersistentState.UPDATED);
				inventory.onLoadHandler(item);
			}
			rset.close();
			stmt.close();
		}
		catch (Exception e)
		{
			log.fatal("Could not restore storage data for player: " + playerId + " from DB: "+e.getMessage(), e);
		}
		finally
		{
			DatabaseFactory.close(con);
		}
		return inventory;
	}

	@Override
	public Equipment loadEquipment(Player player)
	{
		final Equipment equipment = new Equipment(player);
		
		int playerId = player.getObjectId();
		final int storage = 0;
		final int equipped = 1;
		final int owner = playerId;

		Connection con = null;
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(SELECT_QUERY);
			stmt.setInt(1, owner);
			stmt.setInt(2, storage);
			stmt.setInt(3, equipped);
			ResultSet rset = stmt.executeQuery();
			while(rset.next())
			{
				int itemUniqueId = rset.getInt("itemUniqueId");
				int itemId = rset.getInt("itemId");
				long itemCount = rset.getLong("itemCount");
				int itemColor = rset.getInt("itemColor");
				int isSoulBound = rset.getInt("isSoulBound");
				int slot = rset.getInt("slot");
				int enchant = rset.getInt("enchant");
				int itemSkin = rset.getInt("itemSkin");
				Item item = new Item(itemUniqueId, itemId, itemCount, itemColor, true, isSoulBound == 1, slot, storage, enchant, itemSkin);
				item.setPersistentState(PersistentState.UPDATED);
				equipment.onLoadHandler(item);
			}
			rset.close();
			stmt.close();
		}
		catch (Exception e)
		{
			log.fatal("Could not restore Equipment data for player: " + playerId + " from DB: "+e.getMessage(), e);
		}
		finally
		{
			DatabaseFactory.close(con);
		}
		return equipment;
	}

	@Override
	public List<Item> loadEquipment(int playerId)
	{
		final List<Item> items = new ArrayList<Item>();
		final int storage = 0;
		final int equipped = 1;
		final int owner = playerId;

		Connection con = null;
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(SELECT_QUERY);
			stmt.setInt(1, owner);
			stmt.setInt(2, storage);
			stmt.setInt(3, equipped);
			ResultSet rset = stmt.executeQuery();
			while(rset.next())
			{
				int itemUniqueId = rset.getInt("itemUniqueId");
				int itemId = rset.getInt("itemId");
				long itemCount = rset.getLong("itemCount");
				int itemColor = rset.getInt("itemColor");
				int isSoulBound = rset.getInt("isSoulBound");
				int slot = rset.getInt("slot");
				int enchant = rset.getInt("enchant");
				int itemSkin = rset.getInt("itemSkin");
				Item item = new Item(itemUniqueId, itemId, itemCount, itemColor, true, isSoulBound == 1, slot, storage, enchant, itemSkin);
				items.add(item);
			}
			rset.close();
			stmt.close();
		}
		catch (Exception e)
		{
			log.fatal("Could not restore Equipment data for player: " + playerId + " from DB: "+e.getMessage(), e);
		}
		finally
		{
			DatabaseFactory.close(con);
		}
		return items;
	}

	public int getPlayerAccountId(final int playerId)
	{
		Connection con = null;
		int accountId = 0;
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(SELECT_ACCOUNT_QUERY);
			stmt.setInt(1, playerId);
			ResultSet rset = stmt.executeQuery();
			if(rset.next())
			{
				accountId = rset.getInt("account_id");
			}
			rset.close();
			stmt.close();
		}
		catch (Exception e)
		{
			log.fatal("Could not restore accountId data for player: " + playerId + " from DB: "+e.getMessage(), e);
		}
		finally
		{
			DatabaseFactory.close(con);
		}
		return accountId;
	}

	@Override
	public boolean store(Player player)
	{
		int playerId = player.getObjectId();
		
		List<Item> allPlayerItems = player.getDirtyItemsToUpdate();

		boolean resultSuccess = true;
		for(Item item : allPlayerItems)
		{
			if(item != null)
				resultSuccess = store(item, playerId);
		} 
		return resultSuccess;
	}

	/**
	 * @param item The item that needs to be stored
	 * @param ownerId The playerObjectId of the owner of the item
	 * @return true if storing succeeded
	 */
	@Override
	public boolean store(final Item item, int ownerId)
	{
		boolean result = false;

		if(item.getItemLocation() == StorageType.ACCOUNT_WAREHOUSE.getId())
		{
			ownerId = getPlayerAccountId(ownerId);
		}

		switch(item.getPersistentState())
		{
			case NEW:
				result = insertItem(item, ownerId);
				break;
			case UPDATE_REQUIRED:
				result = updateItem(item, ownerId);
				break;
			case DELETED:
				result = deleteItem(item);
				break;
		}
		item.setPersistentState(PersistentState.UPDATED);
		return result;
	}

	/**
	 * @param item
	 * @param playerId
	 * @return
	 */
	private boolean insertItem(final Item item, final int ownerId)
	{
		Connection con = null;
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(INSERT_QUERY);
			stmt.setInt(1, item.getObjectId());
			stmt.setInt(2, item.getItemTemplate().getTemplateId());
			stmt.setLong(3, item.getItemCount());
			stmt.setInt(4, item.getItemColor());
			stmt.setInt(5, ownerId);
			stmt.setBoolean(6, item.isEquipped());
			stmt.setInt(7, item.isSoulBound() ? 1 : 0);
			stmt.setInt(8, item.getEquipmentSlot());
			stmt.setInt(9, item.getItemLocation());
			stmt.setInt(10, item.getEchantLevel());
			stmt.setInt(11, item.getItemSkinTemplate().getTemplateId());
			stmt.execute();
			stmt.close();
		}
		catch (Exception e)
		{
			log.error("Error insert item ItemObjId: "+item.getObjectId(), e);
			return false;
		}
		finally
		{
			DatabaseFactory.close(con);
		}
		return true;
	}

	/**
	 * @param item
	 * @return
	 */
	private boolean updateItem(final Item item,  final int ownerId)
	{
		Connection con = null;
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(UPDATE_QUERY);
			stmt.setLong(1, item.getItemCount());
			stmt.setInt(2, item.getItemColor());
			stmt.setInt(3, ownerId);
			stmt.setBoolean(4, item.isEquipped());
			stmt.setInt(5, item.isSoulBound() ? 1 : 0);
			stmt.setInt(6, item.getEquipmentSlot());
			stmt.setInt(7, item.getItemLocation());
			stmt.setInt(8, item.getEchantLevel());
			stmt.setInt(9, item.getItemSkinTemplate().getTemplateId());
			stmt.setInt(10, item.getObjectId());
			stmt.execute();
			stmt.close();
		}
		catch (Exception e)
		{
			log.error("Error update item ItemObjId: "+item.getObjectId(), e);
			return false;
		}
		finally
		{
			DatabaseFactory.close(con);
		}
		return true;
	}

	/**
	 * 
	 * @param item
	 */
	private boolean deleteItem(final Item item) 
	{
		Connection con = null;
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(DELETE_QUERY);
			stmt.setInt(1, item.getObjectId());
			stmt.execute();
			stmt.close();
		}
		catch (Exception e)
		{
			log.error("Error delete item. ItemObjId: "+item.getObjectId(), e);
			return false;
		}
		finally
		{
			DatabaseFactory.close(con);
		}
		return true;
	}

	/**
	 *  Since inventory is not using FK - need to clean items
	 */
	@Override
	public boolean deletePlayerItems(final int playerId)
	{
		Connection con = null;
		try
		{
			con = DatabaseFactory.getConnection();
			PreparedStatement stmt = con.prepareStatement(DELETE_CLEAN_QUERY);
			stmt.setInt(1, playerId);
			stmt.execute();
			stmt.close();
		}
		catch (Exception e)
		{
			log.error("Error Player all items. PlayerObjId: "+playerId, e);
			return false;
		}
		finally
		{
			DatabaseFactory.close(con);
		}
		return true;
	}

	@Override
	public int[] getUsedIDs() 
	{
		PreparedStatement statement = DB.prepareStatement("SELECT itemUniqueId FROM inventory", ResultSet.TYPE_SCROLL_INSENSITIVE,
			ResultSet.CONCUR_READ_ONLY);

		try
		{
			ResultSet rs = statement.executeQuery();
			rs.last();
			int count = rs.getRow();
			rs.beforeFirst();
			int[] ids = new int[count];
			for(int i = 0; i < count; i++)
			{
				rs.next();
				ids[i] = rs.getInt("itemUniqueId");
			}
			return ids;
		}
		catch(SQLException e)
		{
			log.error("Can't get list of id's from inventory table", e);
		}
		finally
		{
			DB.close(statement);
		}

		return new int[0];
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public boolean supports(String s, int i, int i1)
	{
		return MySQL5DAOUtils.supports(s, i, i1);
	}

}
