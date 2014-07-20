package eu.blackspectrum.purgatory.listeners;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import eu.blackspectrum.purgatory.Purgatory;

public class PlayerListener implements Listener
{


	@EventHandler(priority = EventPriority.NORMAL)
	public void onPlayerDie( final PlayerDeathEvent event ) {
		final Player player = event.getEntity();

		Purgatory.addPlayer( player );
	}




	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin( final PlayerJoinEvent event ) {
		final Player player = event.getPlayer();

		// If he can leave purgatory TP him
		if ( Purgatory.checkPlayer( player ) && !player.isDead() )
			Purgatory.tpPlayer( player );
	}




	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerRespawn( final PlayerRespawnEvent event ) {
		final Player player = event.getPlayer();

		// If he cant leave purgatory, spawn him inside
		if ( !Purgatory.checkPlayer( player ) )
		{
			event.setRespawnLocation( Purgatory.purgatoryLocation );
			player.sendMessage( "Your are stuck in the .." + ChatColor.RED + "FOREVER" );
		}
		else
			event.setRespawnLocation( Purgatory.getTpLocation( player ) );

	}
}
