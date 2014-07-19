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




	public abstract boolean onCommand( final CommandSender sender, final List<String> args );




	public String getName() {
		return this.name;
	}




	public String getDescription() {
		try
		{
			return this.plugin.getCommand( this.name ).getDescription();
		}
		catch ( NullPointerException e )
		{
			return null;
		}
	}




	public String getUsage() {
		try
		{
			return this.plugin.getCommand( this.name ).getUsage();
		}
		catch ( NullPointerException e )
		{
			return null;
		}
	}




	public String getPermission() {
		try
		{
			return this.plugin.getCommand( this.name ).getPermission();
		}
		catch ( NullPointerException e )
		{
			return null;
		}
	}




	public List<String> getAliases() {
		try
		{
			return (List<String>) this.plugin.getCommand( this.name ).getAliases();
		}
		catch ( NullPointerException e )
		{
			return null;
		}
	}
}