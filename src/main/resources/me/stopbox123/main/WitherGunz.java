package me.stopbox123.main;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.WitherSkull;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;


@SuppressWarnings("unused")
public class WitherGunz extends JavaPlugin
    implements Listener
{

       ArrayList<Player> Wither = new ArrayList<Player>(); {
       
       short r = 59;
       short d = 59;
       boolean noshoot2 = false;
       }

    public void onEnable()
    {
        getConfig().options().copyDefaults(true);
        saveConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @SuppressWarnings("deprecation")
	public void EntityDamage(EntityDamageByEntityEvent e)
    {
        if(e.getDamager() instanceof WitherSkull)
        {
            WitherSkull w = (WitherSkull)e.getDamager();
            if(w.getShooter() instanceof Player)
            {
                Player shooter = (Player)w.getShooter();
                if(shooter.getItemInHand().getType() == Material.GOLD_HOE)
                    e.setDamage(10000);
                else
                    return;
            }
        }
    }

    @EventHandler
	public void onRightClick(final PlayerInteractEvent event)
    {
    	final ArrayList<Player> noshoot = new ArrayList<Player>();
    	short r = 59;
        short d = 59;
        if (noshoot.contains(event.getPlayer())) {
        	return;
        } else {
        	
        if(event.getPlayer().getItemInHand().getType() == Material.WOOD_HOE && event.getAction() == Action.LEFT_CLICK_AIR)
        {
            event.getPlayer().launchProjectile(org.bukkit.entity.WitherSkull.class);
            event.getPlayer().getItemInHand().setDurability((short)(event.getPlayer().getItemInHand().getDurability() + 1));
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.WITHER_SHOOT, 1.0F, 1.0F);
            if(event.getPlayer().getItemInHand().getDurability() == d)
                getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
                	short r = 59;
                    public void run()
                    {
                        if(r != 0)
                        {
                            event.getPlayer().getItemInHand().setDurability((short)(event.getPlayer().getItemInHand().getDurability() + -1));
                            noshoot.add(event.getPlayer());
                            r--;
                        } else
                        {
                            r = 59;
                            Bukkit.getServer().getScheduler().cancelAllTasks();
                        }
                    }

                
            }
                
, 20L, 1L);
        }
    }
    }
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e)
    {
        if(Wither.contains(e.getEntity().getPlayer().getKiller().getName()))
        {
            e.setDeathMessage((new StringBuilder()).append(ChatColor.GREEN).append(e.getEntity().getName()).append(ChatColor.GRAY).append(" Was Shot By ").append(ChatColor.RED).append(e.getEntity().getKiller().getDisplayName()).toString());
            Wither.remove(e.getEntity().getPlayer().getKiller().getName());
        }
    }
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e)
    {
        e.getPlayer().setAllowFlight(true);
    }

    public void onplayermove(PlayerMoveEvent e)
    {
        e.getPlayer().setFallDistance(0.0F);
    }


}
