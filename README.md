# SuperSeatBoi

Simple seats for your server

The compiled plugin is available here:<br>
https://www.spigotmc.org/resources/superseatboi.77321/

## Commands

* ### /superseatboi
  > (alias: /ssb)
  <br>Used for reloading the plugin's configuration data.
  <br>Requires the permission _"superseatboi.admin"_.

## Permissions

* ### superseatboi.admin
  > Required for using the command _"/superseatboi"_.

## Configuration file "config.yml"

* ### PlayerInteractionCooldown
  > default: 5
  <br>
  <br>Cooldown period to throttle player seating attempts, measured in game ticks.
  <br>Effective in cases where players would spam right clicks, but performance should never be an issue with this simple plugin anyway.
  <br>Setting this value to 0 or below will deactivate it.


* ### **WorldFilterMode**
  > default: BLACKLIST
  <br>
  <br>Whether the world-blacklist or -whitelist should be used when a player attempts to take a seat.
  <br>Valid values: _"BLACKLIST"_, _"WHITELIST"_.

* ### BlacklistedWorlds
  > default: empty list
  <br>
  <br>Players will not be able to take seats in world names listed here.
  <br>Only effective if _"WorldFilterMode"_ is set to _"BLACKLIST"_.


* ### WhitelistedWorlds
  > default: empty list
  <br>
  <br>Players will only be able to take seats in world names listed here.
  <br>Only effective if _"WorldFilterMode"_ is set to _"WHITELIST"_.


* ### RequireControlBlock
  > default: true
  <br>
  <br>Whether the plugin should check if a control block is present under the clicked seat block.


* ### SeatBlockMaterials
  > default: [tag:STAIRS, tag:SLABS]
  <br>
  <br>List of both materials and tags containing materials, which the plugin is to consider as valid seats.
  <br>Material values accepted can be found here: https://papermc.io/javadocs/paper/1.17/org/bukkit/Material.html
  <br>Tag values can be found here: https://papermc.io/javadocs/paper/1.17/org/bukkit/Tag.html
  <br>
  <br>Be sure to format material-tags in the list as _"tag:TAG_NAME"_.
  <br>The material tag _"WOODEN_FENCES"_ must be formatted as _"tag:WOODEN_FENCES"_.

* ### ControlBlockMaterials
  > default: [REDSTONE_BLOCK]
  <br>
  <br>Functions the same as SeatBlockMaterials, but for control blocks.

* ### AllowWaterloggedSeats
  > default: false
  <br>
  <br>Whether to allow players to take a seat on stair blocks that are waterlogged.

* ### AllowSeatingWhileFalling
  > default: false
  <br>
  <br>Whether to allow players to sit while falling.
  <br>Recommended to set false, to prevent players from dodging fall damage.

* ### AllowSeatingWhileSneaking
  > default: false
  <br>
  <br>Whether to allow players to sit while sneaking.
  <br>Recommended to set false, because the key for sneaking is the same that dismounts players from their seats.

* ### AllowSeatingWhileFlying
  > default: false
  <br>
  <br>Whether to allow players to sit while in flight mode.

* ### AllowSeatingWhileGliding
  > default: false
  <br>
  <br>Whether to allow players to sit while gliding with an elytra.

* ### AllowSeatingInCreativeMode
  > default: true
  <br>
  <br>Whether to allow players to sit while in creative mode.

* ### AllowSeatSwapping
  > default: false
  <br>
  <br>Whether to allow players to swap between seats without having to dismount in between.