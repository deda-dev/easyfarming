package de.deda.easyfarming.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.MoistureChangeEvent;

public class MoistureChangeListener implements Listener {

    @EventHandler
    public void moistureChange(MoistureChangeEvent event) {
        event.setCancelled(true);
    }

}
