package eu.blackspectrum.purgatory.commands;

import java.util.List;

import org.bukkit.command.CommandSender;

import eu.blackspectrum.purgatory.Purgatory;

public abstract class AbstractCommand
{


	protected final Purgatory	plugin;
	protected final String		name;




	public AbstractCommand(final Purgatory instance, final String commandName) {
		super();
		this.plugin = instance;
		this.name = commandName;
	}




	public List<String> getAliases() {
		try
		{
			return this.plugin.getCommand( this.name ).getAliases();
		}
		catch ( final NullPointerException e )
		{
			return null;
		}
	}




	public String getDescription() {
		try
		{
			return this.plugin.getCommand( this.name ).getDescription();
		}
		catch ( final NullPointerException e )
		{
			return null;
		}
	}




	public String getName() {
		return this.name;
	}




	public String getPermission() {
		try
		{
			return this.plugin.getCommand( this.name ).getPermission();
		}
		catch ( final NullPointerException e )
		{
			return null;
		}
	}




	public String getUsage() {
		try
		{
			return this.plugin.getCommand( this.name ).getUsage();
		}
		catch ( final NullPointerException e )
		{
			return null;
		}
	}




	public abstract boolean onCommand( final CommandSender sender, final List<String> args );
}