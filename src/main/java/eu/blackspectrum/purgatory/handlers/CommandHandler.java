package eu.blackspectrum.purgatory.handlers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;

import eu.blackspectrum.purgatory.Purgatory;
import eu.blackspectrum.purgatory.commands.AbstractCommand;
import eu.blackspectrum.purgatory.commands.FreeCommand;
import eu.blackspectrum.purgatory.commands.SetCommand;

public class CommandHandler implements CommandExecutor
{


	private final Purgatory							plugin;
	private final HashMap<String, AbstractCommand>	commands;




	public CommandHandler(final Purgatory instance) {
		super();
		this.commands = new HashMap<String, AbstractCommand>();
		this.plugin = instance;
		this.plugin.getCommand( "pur" ).setExecutor( this );
		this.registerCommands( new AbstractCommand[] { new SetCommand( this.plugin ), new FreeCommand( this.plugin ) } );
	}




	@Override
	public boolean onCommand( final CommandSender sender, final Command cmd, final String label, final String[] args ) {
		if ( sender instanceof ConsoleCommandSender )
			return false;

		if ( !label.equalsIgnoreCase( "pur" ) || args.length == 0 )
			return false;

		final AbstractCommand abstractCommand = this.commands.get( args[0] );

		if ( abstractCommand.getPermission() != null && !sender.hasPermission( abstractCommand.getPermission() ) )
		{
			sender.sendMessage( "You don't have the permission to use this command!" );
			return true;
		}
		if ( abstractCommand.onCommand( sender, Arrays.asList( args ).subList( 1, args.length ) ) == false
				&& abstractCommand.getUsage() != null )
			sender.sendMessage( abstractCommand.getUsage() );
		return true;
	}




	private void registerCommands( final AbstractCommand[] abstractCommands ) {
		for ( final AbstractCommand abstractCommand : abstractCommands )
		{
			this.commands.put( abstractCommand.getName(), abstractCommand );
			final List<String> aliases = abstractCommand.getAliases();
			if ( abstractCommand.getAliases() != null )
				for ( final String alias : aliases )
					this.commands.put( alias, abstractCommand );
		}
	}
}