package de.jaylawl.superseatboi.command;

import de.jaylawl.superseatboi.SuperSeatBoi;
import de.jaylawl.superseatboi.util.ConfigurableSettings;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class CommandMaster implements TabCompleter, CommandExecutor {

    public CommandMaster() {
    }

    //

    @Override
    public @Nullable List<String> onTabComplete(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        List<String> completions = new ArrayList<>();
        int argumentNumber = TabHelper.getArgNumber(arguments);

        switch (argumentNumber) {
            case 1 -> {
                completions.add("print");
                completions.add("reload");
            }
            case 2 -> {
                if (arguments[0].equalsIgnoreCase("print")) {
                    completions.add("materials");
                }
            }
            case 3 -> {
                if (arguments[0].equalsIgnoreCase("print")) {
                    if (arguments[1].equalsIgnoreCase("materials")) {
                        completions.add("controlblocks");
                        completions.add("seatblocks");
                    }
                }
            }
        }

        return TabHelper.sortedCompletions(arguments[argumentNumber - 1], completions);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        switch (arguments.length > 0 ? arguments[0].toLowerCase() : "") {

            case "print" -> {

                if (arguments.length < 2) {
                    commandSender.sendMessage(ChatColor.RED + "Unknown/missing argument(s)");

                } else {
                    String whatToPrint = arguments[1].toLowerCase();
                    switch (whatToPrint) {

                        case "materials" -> {
                            if (arguments.length < 3) {
                                commandSender.sendMessage(ChatColor.RED + "Unknown/missing argument(s)");

                            } else {
                                String materialsType = arguments[2].toLowerCase();
                                ConfigurableSettings configurableSettings = SuperSeatBoi.getInstance().getConfigurableSettings();
                                switch (materialsType) {

                                    case "controlblocks" -> {
                                        commandSender.sendMessage(configurableSettings.controlBlockMaterials.size() + " control block material(s) loaded: " + configurableSettings.controlBlockMaterials);
                                    }

                                    case "seatblocks" -> {
                                        commandSender.sendMessage(configurableSettings.seatBlockMaterials.size() + " seat block material(s) loaded: " + configurableSettings.seatBlockMaterials);
                                    }

                                    default -> {
                                        commandSender.sendMessage(ChatColor.RED + "Unknown/missing argument(s)");
                                    }

                                }

                            }
                        }
                    }
                }
            }

            case "reload" -> {
                if (!SuperSeatBoi.getInstance().reload(commandSender)) {
                    commandSender.sendMessage(Component
                            .text("Plugin is already reloading. You will be notified upon completion...")
                            .color(NamedTextColor.YELLOW)
                    );
                }
            }

            default -> {
                commandSender.sendMessage(Component
                        .text("Unknown/missing argument(s)")
                        .color(NamedTextColor.RED)
                );
            }

        }

        return true;
    }

}
