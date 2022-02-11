package de.jaylawl.superseatboi.util;

import java.util.LinkedList;

public final class ConfigurableSettings {

    private static final boolean DEFAULT_REQUIRE_CONTROL_BLOCK = true;
    private static final boolean DEFAULT_ALLOW_WATERLOGGED_SEATING = false;
    private static final boolean DEFAULT_ALLOW_SEATING_WHILE_FALLING = false;
    private static final boolean DEFAULT_ALLOW_SEATING_WHILE_SNEAKING = false;
    private static final boolean DEFAULT_ALLOW_SEATING_IF_FLYING = false;
    private static final boolean DEFAULT_ALLOW_SEATING_IF_GLIDING = false;
    private static final boolean DEFAULT_ALLOW_SEATING_IF_IN_CREATIVE_MODE = true;
    private static final WorldFilterMode DEFAULT_WORLD_FILTER_MODE = WorldFilterMode.BLACKLIST;

    public boolean requireControlBlock;
    public boolean allowWaterloggedSeats;
    public boolean allowSeatingIfFalling;
    public boolean allowSeatingIfSneaking;
    public boolean allowSeatingIfFlying;
    public boolean allowSeatingIfGliding;
    public boolean allowSeatingIfCreativeMode;
    public WorldFilterMode worldFilterMode;
    public LinkedList<String> blacklistedWorlds = new LinkedList<>();
    public LinkedList<String> whitelistedWorlds = new LinkedList<>();

    public ConfigurableSettings() {
        applyDefaultValues();
    }

    public void applyDefaultValues() {
        this.requireControlBlock = DEFAULT_REQUIRE_CONTROL_BLOCK;
        this.allowWaterloggedSeats = DEFAULT_ALLOW_WATERLOGGED_SEATING;
        this.allowSeatingIfFalling = DEFAULT_ALLOW_SEATING_WHILE_FALLING;
        this.allowSeatingIfSneaking = DEFAULT_ALLOW_SEATING_WHILE_SNEAKING;
        this.allowSeatingIfFlying = DEFAULT_ALLOW_SEATING_IF_FLYING;
        this.allowSeatingIfGliding = DEFAULT_ALLOW_SEATING_IF_GLIDING;
        this.allowSeatingIfCreativeMode = DEFAULT_ALLOW_SEATING_IF_IN_CREATIVE_MODE;
        this.worldFilterMode = DEFAULT_WORLD_FILTER_MODE;
    }

}
