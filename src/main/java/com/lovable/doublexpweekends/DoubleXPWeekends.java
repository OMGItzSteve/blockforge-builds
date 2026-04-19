package com.lovable.doublexpweekends;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;

public class DoubleXPWeekends extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        getLogger().info("DoubleXPWeekends enabled! Checking for weekend status...");
        
        if (isWeekend()) {
            getLogger().info("It is currently the weekend! Double XP is ACTIVE.");
        } else {
            getLogger().info("It is currently a weekday. Double XP is INACTIVE.");
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onEntityDeath(EntityDeathEvent event) {
        // Only apply if it's the weekend
        if (!isWeekend()) {
            return;
        }

        // Only apply if the killer is a player (optional, but standard for XP plugins)
        if (event.getEntity().getKiller() == null) {
            return;
        }

        int originalXP = event.getDroppedExp();
        if (originalXP > 0) {
            int doubledXP = originalXP * 2;
            event.setDroppedExp(doubledXP);
            
            // Notify player occasionally or via action bar if desired
            // (Keeping it silent to avoid chat spam, but logic is here)
        }
    }

    /**
     * Checks if the current day (based on server timezone) is Saturday or Sunday.
     */
    private boolean isWeekend() {
        DayOfWeek day = LocalDate.now(ZoneId.systemDefault()).getDayOfWeek();
        return (day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY);
    }
}
