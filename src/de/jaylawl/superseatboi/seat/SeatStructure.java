package de.jaylawl.superseatboi.seat;

import org.bukkit.Material;

import java.util.*;

public class SeatStructure {

    public static EnumSet<Material> SEAT_MATERIALS;
    public static EnumSet<Material> SEAT_VALIDATION_MATERIALS;

    public static void init() {
        Collection<Material> materials = new ArrayList<>();
        for (Material material : Material.values()) {
            if (material.toString().contains("STAIR")) {
                materials.add(material);
            }
        }
        SEAT_MATERIALS = !materials.isEmpty() ? EnumSet.copyOf(materials) : EnumSet.noneOf(Material.class);

        SEAT_VALIDATION_MATERIALS = EnumSet.copyOf(Collections.singletonList(Material.REDSTONE_BLOCK));
    }

}
