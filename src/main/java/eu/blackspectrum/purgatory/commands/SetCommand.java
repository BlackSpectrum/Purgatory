package eu.blackspectrum.purgatory.commands;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import eu.blackspectrum.purgatory.Purgatory;

public class SetCommand extends AbstractCommand
{


	public SetCommand(final Purgatory instance) {
		super( instance, "set" );
	}




	@Override
	public String getUsage() {
		return "Usage: /pur set <args>, <args> can be \"purgatory\", \"spawn\", \"check\" + [value] or \"time\" + [value]";
	}




	@Override
	public boolean onCommand( final CommandSender sender, final List<String> args ) {

		if ( args.size() == 1 )
		{

			final String arg = args.get( 0 );
			Location location = null;
			if ( sender instanceof Player )
				location = ( (Player) sender ).getLocation();
			else
				return false;

			if ( arg.equalsIgnoreCase( "purgatory" ) )
			{
				this.plugin.setSpawnLocation( location );
				sender.sendMessage( "New Purgatory position set." );
				return true;
			}

			if ( arg.equalsIgnoreCase( "spawn" ) )
			{
				this.plugin.setSpawnLocation( location );
				sender.sendMessage( "New Spawn position set." );
				return true;
			}

			if ( arg.equalsIgnoreCase( "check" ) )
			{
				sender.sendMessage( "Check interval: " + Purgatory.config.getLong( "checkPeriodInTicks" ) );
				return true;
			}
			if ( arg.equalsIgnoreCase( "time" ) )
			{
				sender.sendMessage( "Time in purgatory: " + Purgatory.config.getLong( "timeInPurgatory" ) );
				return true;
			}
		}

		if ( args.size() == 2 )
		{
			final String arg = args.get( 0 );
			long value;
			try
			{
				value = Long.parseLong( args.get( 1 ) );
			}
			catch ( final Exception e )
			{
				return false;
			}

			if ( arg.equalsIgnoreCase( "check" ) )
			{
				Purgatory.config.set( "checkPeriodInTicks", value );
				this.plugin.saveConfig();
				sender.sendMessage( "New check interval set." );
				return true;
			}
			if ( arg.equalsIgnoreCase( "time" ) )
			{
				Purgatory.config.set( "timeInPurgatory", value );
				this.plugin.saveConfig();
				sender.sendMessage( "New time in Purgatory set." );
				return true;
			}

		}
		return false;
	}

}
