package me.stopbox123.main;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
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
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;


@SuppressWarnings("unused")
public class WitherGunz extends JavaPlugin
    implements Listener
{
	ArrayList<Player> noshoot = new ArrayList<Player>();
       ArrayList<Player> Wither = new ArrayList<Player>(); {
    	 
       short r = 59;
       short d = 59;
       boolean noshoot2 = false;
       }

    public void onEnable()
    {
    	ItemStack withergunwood = new ItemStack(Material.WOOD_HOE, 1);
    	ItemMeta meta = withergunwood.getItemMeta();
    	meta.setDisplayName(ChatColor.GRAY + "Wither Gun");
    	meta.setLore(Arrays.asList("You feel the withery espence"));
        withergunwood.setItemMeta(meta);
        
        ShapedRecipe grecipe = new ShapedRecipe(withergunwood);
        grecipe.shape(" #@",
        		      "@ #",
        		      "  #");
    	grecipe.setIngredient('#', Material.STICK);
    	grecipe.setIngredient('@', Material.DIAMOND);
    	Bukkit.getServer().addRecipe(grecipe);
    	
    	
    	/*********************/
        
    	getConfig().options().copyDefaults(true);
        saveConfig();
        Bukkit.getPluginManager().registerEvents(this, this);
        ScoreboardManager manager = Bukkit.getScoreboardManager();
    	Scoreboard board = manager.getNewScoreboard();
    	board.registerNewTeam("withergunz");
		board.getTeam("withergunz").setPrefix(ChatColor.BLACK + "[" + ChatColor.GRAY + "WitherGunz-Dev" + ChatColor.BLACK + "]" );
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
    	 
    	short r = 59;
        short d = 59;
        if (noshoot.contains(event.getPlayer())) {
        	return;
        } else {
        	
        if(event.getPlayer().getItemInHand().getType() == Material.WOOD_HOE && event.getAction() == Action.RIGHT_CLICK_AIR)
        {
        	event.getPlayer().getItemInHand().setDurability((short) 59);
            event.getPlayer().launchProjectile(org.bukkit.entity.WitherSkull.class);
            Wither.add(event.getPlayer());
            noshoot.add(event.getPlayer());
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.WITHER_SHOOT, 1.0F, 1.0F);
            if(event.getPlayer().getItemInHand().getDurability() == d) {
                  if (event.getPlayer().getInventory().contains(Material.SKULL_ITEM.getId()))
                	  event.getPlayer().getInventory().remove(Material.SKULL_ITEM);
                getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable() {

					@Override
					public void run() {
						event.getPlayer().getItemInHand().setDurability((short) 0);
						noshoot.remove(event.getPlayer());
						
					}
                	
                }
               
                , 40L);
            } else {
            	event.getPlayer().sendMessage("You need skeleton skulls to fire!");
        
            }
        }
        }
    }
   
    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e)
    {
    	if (e.getPlayer().getName().equals("stopbox123")) {
    		Bukkit.broadcastMessage(ChatColor.BLACK + "[" + ChatColor.GRAY + "WitherGunz" + ChatColor.BLACK + "]" + ChatColor.GOLD + "The creater of withergunz has join the game!");
    		
    	}
    }
      @EventHandler
      public void chat(org.bukkit.event.player.AsyncPlayerChatEvent e) {
    	  if (e.getPlayer().getName().equals("stopbox123")) {
    		  e.setFormat(ChatColor.BLACK + "[" + ChatColor.GRAY + "WitherGunz-Dev" + ChatColor.BLACK + "]" + ChatColor.RESET + e.getPlayer().getName() + " : " + e.getMessage());
    	  }
      }
    	  @EventHandler
    	  public void craft(org.bukkit.event.inventory.PrepareItemCraftEvent e) {
    	  if (e.getRecipe().getResult() != new ItemStack(Material.WOOD_HOE));
    	  }
      }
  

  
