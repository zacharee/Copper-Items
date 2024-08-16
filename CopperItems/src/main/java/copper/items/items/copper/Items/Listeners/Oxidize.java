package copper.items.items.copper.Items.Listeners;

import copper.items.items.copper.Items.CopperItems;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ArmorMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.trim.ArmorTrim;
import org.bukkit.inventory.meta.trim.TrimMaterial;
import org.bukkit.inventory.meta.trim.TrimPattern;

public class Oxidize implements Listener {
    CopperItems plugin;

    public Oxidize(CopperItems plugin) {
        this.plugin = plugin;
    }

    FileConfiguration lang = CopperItems.getCurrentLang();

    @EventHandler
    public void onItemDamage(PlayerItemDamageEvent e) {
        ItemStack item = e.getItem();
        ItemMeta im = item.getItemMeta();
        if (im == null) return;
        String locName = im.getItemName();
        if (locName == null) return;

        if (locName.startsWith("copper_")) {

            int badDurability = item.getDurability();
            int maxDurability = item.getType().getMaxDurability();
            int durability = maxDurability - badDurability - 1;
            int durabilitySmol = maxDurability / 5;

            e.getPlayer().sendMessage(String.valueOf(durability));

            int customModelData = item.getItemMeta().getCustomModelData();
            String name = item.getItemMeta().getDisplayName();
            String newName = name;
            String ex = lang.getString("misc.exposed");
            String we = lang.getString("misc.weathered");
            String ox = lang.getString("misc.oxidized");
            String da = lang.getString("misc.damaged");

            if (durability >= durabilitySmol * 4) {
                customModelData = 1;
                e.getPlayer().sendMessage("1");
            } else if (durability >= durabilitySmol * 3) {
                if (!name.contains(ex)) {
                    newName = ex + name;
                    customModelData = 2;

                    e.getPlayer().sendMessage("2");

                    if (im instanceof ArmorMeta am){
                        am.setTrim(new ArmorTrim(getCustomTrimMaterial("copper_armor:custom_copper_exposed"), getCustomTrimPattern("copper_armor:custom_copper_exposed")));
                        e.getPlayer().sendMessage("test");
                    }
                }

            } else if (durability >= durabilitySmol * 2) {
                if (!name.contains(we)) {
                    newName = we + name.replace(ex, "");
                    customModelData = 3;
                    if (im instanceof ArmorMeta am){
                        am.setTrim(new ArmorTrim(getCustomTrimMaterial("copper_armor:custom_copper_weathered"), getCustomTrimPattern("copper_armor:custom_copper_weathered")));
                    }
                }

            } else if (durability >= durabilitySmol) {
                if (!name.contains(ox)) {
                    newName = ox + name.replace(we, "");
                    customModelData = 4;
                    if (im instanceof ArmorMeta am){
                        am.setTrim(new ArmorTrim(getCustomTrimMaterial("copper_armor:custom_copper_oxidized"), getCustomTrimPattern("copper_armor:custom_copper_oxidized")));
                    }
                }

            } else {
                if (!name.contains(da)) {
                    newName = da + name.replace(ox, "");
                    customModelData = 5;
                    if (im instanceof ArmorMeta am){
                        am.setTrim(new ArmorTrim(getCustomTrimMaterial("copper_armor:custom_copper_damaged"), getCustomTrimPattern("copper_armor:custom_copper_damaged")));
                    }
                }
            }

            im.setDisplayName(ChatColor.WHITE + newName);
            im.setCustomModelData(customModelData);
            item.setItemMeta(im);
        }
    }
    private TrimPattern getCustomTrimPattern(String patternNameSpace) {
        NamespacedKey key = NamespacedKey.fromString(patternNameSpace);
        return Bukkit.getRegistry(TrimPattern.class).get(key);
    }
    private TrimMaterial getCustomTrimMaterial(String patternNameSpace) {
        NamespacedKey key = NamespacedKey.fromString(patternNameSpace);
        return Bukkit.getRegistry(TrimMaterial.class).get(key);
    }
}
