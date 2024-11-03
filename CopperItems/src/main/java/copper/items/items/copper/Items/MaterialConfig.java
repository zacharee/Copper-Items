package copper.items.items.copper.Items;

import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;

public class MaterialConfig {
    public enum MaterialSuffix {
        SWORD("iron"),
        AXE("iron"),
        PICKAXE("stone"),
        SHOVEL("iron"),
        HOE("stone"),
        HELMET("iron"),
        CHESTPLATE("iron"),
        LEGGINGS("iron"),
        BOOTS("iron");

        public String defaultMaterial;

        MaterialSuffix(String defaultMaterial) {
            this.defaultMaterial = defaultMaterial;
        }
    }

    public static Material materialFromConfig(FileConfiguration config, MaterialSuffix suffix) {
        String configMaterial = config.getString("items." + suffix.name().toLowerCase() + ".material", suffix.defaultMaterial);

        return Material.valueOf(configMaterial.toUpperCase() + "_" + suffix.name());
    }
}
