package de.jaylawl.superseatboi.util;

import org.bukkit.Material;

import java.util.EnumSet;
import java.util.LinkedList;

public final class ConfigurableSettings {

    public static final int DEFAULT_PLAYER_INTERACTION_COOLDOWN = 5;
    public static final WorldFilterMode DEFAULT_WORLD_FILTER_MODE = WorldFilterMode.BLACKLIST;
    public static final boolean DEFAULT_REQUIRE_CONTROL_BLOCK = true;
    public static final String[] DEFAULT_SEAT_BLOCK_MATERIAL_NAMES = new String[]{"tag:STAIRS", "tag:SLABS"};
    public static final String[] DEFAULT_CONTROL_BLOCK_MATERIAL_NAMES = new String[]{"REDSTONE_BLOCK"};
    public static final boolean DEFAULT_ALLOW_WATERLOGGED_SEATS = false;
    public static final boolean DEFAULT_ALLOW_SEATING_WHILE_FALLING = false;
    public static final boolean DEFAULT_ALLOW_SEATING_WHILE_SNEAKING = false;
    public static final boolean DEFAULT_ALLOW_SEATING_WHILE_FLYING = false;
    public static final boolean DEFAULT_ALLOW_SEATING_WHILE_GLIDING = false;
    public static final boolean DEFAULT_ALLOW_SEATING_IN_CREATIVE_MODE = true;
    public static final boolean DEFAULT_ALLOW_SEAT_SWAPPING = false;

    public int playerInteractionCooldown;
    public WorldFilterMode worldFilterMode;
    public LinkedList<String> blacklistedWorlds;
    public LinkedList<String> whitelistedWorlds;
    public EnumSet<Material> seatBlockMaterials = EnumSet.noneOf(Material.class);
    public EnumSet<Material> controlBlockMaterials = EnumSet.noneOf(Material.class);
    public boolean requireControlBlock;
    public boolean allowWaterloggedSeats;
    public boolean allowSeatingWhileFalling;
    public boolean allowSeatingWhileSneaking;
    public boolean allowSeatingWhileFlying;
    public boolean allowSeatingWhileGliding;
    public boolean allowSeatingInCreativeMode;
    public boolean allowSeatSwapping;

    public ConfigurableSettings() {
        applyDefaultValues();
    }

    //

    public void applyDefaultValues() {
        this.playerInteractionCooldown = DEFAULT_PLAYER_INTERACTION_COOLDOWN;
        this.worldFilterMode = DEFAULT_WORLD_FILTER_MODE;
        this.blacklistedWorlds = new LinkedList<>();
        this.whitelistedWorlds = new LinkedList<>();
        this.seatBlockMaterials = EnumSet.noneOf(Material.class);
        this.controlBlockMaterials = EnumSet.noneOf(Material.class);
        this.requireControlBlock = DEFAULT_REQUIRE_CONTROL_BLOCK;
        this.allowWaterloggedSeats = DEFAULT_ALLOW_WATERLOGGED_SEATS;
        this.allowSeatingWhileFalling = DEFAULT_ALLOW_SEATING_WHILE_FALLING;
        this.allowSeatingWhileSneaking = DEFAULT_ALLOW_SEATING_WHILE_SNEAKING;
        this.allowSeatingWhileFlying = DEFAULT_ALLOW_SEATING_WHILE_FLYING;
        this.allowSeatingWhileGliding = DEFAULT_ALLOW_SEATING_WHILE_GLIDING;
        this.allowSeatingInCreativeMode = DEFAULT_ALLOW_SEATING_IN_CREATIVE_MODE;
        this.allowSeatSwapping = DEFAULT_ALLOW_SEAT_SWAPPING;
    }

}
