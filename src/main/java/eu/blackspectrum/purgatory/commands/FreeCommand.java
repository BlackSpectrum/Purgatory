package eu.blackspectrum.purgatory.commands;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.blackspectrum.purgatory.Purgatory;

public class FreeCommand extends AbstractCommand
{


	public FreeCommand(final Purgatory instance) {
		super( instance, "free" );
	}




	@Override
	public String getUsage() {
		return "Usage: /pur free [player]";
	}




	@Override
	public boolean onCommand( final CommandSender sender, final List<String> args ) {

		Player target = null;
		if ( args.size() == 0 && sender instanceof Player )
			target = (Player) sender;
		else if ( args.size() == 1 )
		{
			boolean foundOne = false;
			for ( final Player p : Bukkit.getOnlinePlayers() )
				if ( p.getName().matches( "(?i:.*" + args.get( 0 ) + ".*)" ) )

				{
					if ( foundOne )
					{
						sender.sendMessage( ChatColor.RED + "PURGATORY: Multiple players found for that name!" );
						return true;
					}
					target = p;
					foundOne = true;
				}
		}

		if ( target != null )
		{
			Purgatory.removePlayer( target );
			return true;
		}

		return false;
	}

}
