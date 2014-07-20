package eu.blackspectrum.purgatory;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import eu.blackspectrum.purgatory.handlers.CommandHandler;
import eu.blackspectrum.purgatory.listeners.PlayerListener;
import eu.blackspectrum.spawnbed.entities.BedHead;
import eu.blackspectrum.spawnbed.util.Util;

public class Purgatory extends JavaPlugin
{


	private static final HashMap<UUID, Long>	playersInPurgatory	= new HashMap<UUID, Long>();
	public static FileConfiguration				config;
	@SuppressWarnings("unused")
	private CommandHandler						commandHandler;

	public static Location						purgatoryLocation;
	public static Location						spawnLocation;




	public static void addPlayer( final Player p ) {
		final long now = System.currentTimeMillis();

		playersInPurgatory.put( p.getUniqueId(), now + config.getLong( "timeInPurgatory", 900L ) * 1000 );

	}




	/**
	 *
	 * @param now
	 * @param player
	 * @return true if the player can leave the purgatory
	 */
	public static boolean checkPlayer( final Player player ) {
		final long now = System.currentTimeMillis();
		final UUID uuid = player.getUniqueId();
		if ( playersInPurgatory.containsKey( uuid ) )
		{
			if ( playersInPurgatory.get( uuid ) <= now )
			{
				playersInPurgatory.remove( uuid );

				return true;
			}
			return false;
		}

		if ( player.getLocation().getWorld().getName().equalsIgnoreCase( config.getString( "purgatory.world" ) ) )
			return true;
		else
			return false;
	}




	public static Location getTpLocation( final Player player ) {
		final BedHead bedHead = Util.getBed( player, false );

		if ( bedHead != null && bedHead.isSpawnable( player, true ) )
			return bedHead.getSpawnLocation();

		return spawnLocation;
	}




	public static void removePlayer( final Player player ) {
		if ( playersInPurgatory.containsKey( player.getUniqueId() ) )
		{
			playersInPurgatory.remove( player.getUniqueId() );

			if ( !player.isDead() )
				tpPlayer( player );
		}

	}




	public static void tpPlayer( final Player player ) {
		player.sendMessage( "You are freed from the Purgatory" );
		player.teleport( getTpLocation( player ) );
	}




	@Override
	public void onDisable() {
		playersInPurgatory.clear();
	}




	@Override
	public void onEnable() {
		this.initConfig();

		playersInPurgatory.clear();

		this.commandHandler = new CommandHandler( this );

		final PluginManager pm = this.getServer().getPluginManager();

		pm.registerEvents( new PlayerListener(), this );

		this.getServer().getScheduler().scheduleSyncRepeatingTask( this, new Runnable() {


			@Override
			public void run() {
				System.currentTimeMillis();
				Purgatory.this.checkPurgatory();
			}
		}, config.getInt( "checkperiodInTicks", 1200 ), config.getInt( "checkperiodInTicks", 1200 ) );
	}




	public void setPurgatoryLocation( final Location location ) {
		config.set( "purgatory.x", config.getInt( "purgatory.x", location.getBlockX() ) );
		config.set( "purgatory.y", config.getInt( "purgatory.y", location.getBlockY() ) );
		config.set( "purgatory.z", config.getInt( "purgatory.z", location.getBlockZ() ) );
		config.set( "purgatory.yaw", config.getDouble( "purgatory.yaw", location.getYaw() ) );
		config.set( "purgatory.pitch", config.getDouble( "purgatory.ptich", location.getPitch() ) );

		this.saveConfig();

		purgatoryLocation = location;
	}




	public void setSpawnLocation( final Location location ) {
		config.set( "spawn.x", config.getInt( "spawn.x", location.getBlockX() ) );
		config.set( "spawn.y", config.getInt( "spawn.y", location.getBlockY() ) );
		config.set( "spawn.z", config.getInt( "spawn.z", location.getBlockZ() ) );
		config.set( "spawn.yaw", config.getDouble( "spawn.yaw", location.getYaw() ) );
		config.set( "spawn.pitch", config.getDouble( "spawn.ptich", location.getPitch() ) );

		this.saveConfig();

		spawnLocation = location;
	}




	private void checkPurgatory() {
		for ( final UUID uuid : playersInPurgatory.keySet() )
		{
			final Player player = this.getServer().getPlayer( uuid );
			if ( player != null && checkPlayer( player ) )
				tpPlayer( player );
		}

	}




	private void initConfig() {
		config = this.getConfig();

		config.set( "timeInPurgatory", Long.valueOf( config.getLong( "timeInPurgatory", 900L ) ) );
		config.set( "checkperiodInTicks", Integer.valueOf( config.getInt( "checkperiodInTicks", 1200 ) ) );

		config.set( "purgatory.world", config.getString( "purgatory.world", "world" ) );

		World world = this.getServer().getWorld( config.getString( "purgatory.world" ) );
		Location spawnLocation = world.getSpawnLocation();

		this.setPurgatoryLocation( spawnLocation );

		config.set( "spawn.world", config.getString( "spawn.world", "world" ) );
		world = this.getServer().getWorld( config.getString( "spawn.world" ) );
		spawnLocation = world.getSpawnLocation();

		this.setSpawnLocation( spawnLocation );

		this.saveConfig();
	}
}
