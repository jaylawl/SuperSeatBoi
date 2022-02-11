package de.jaylawl.superseatboi.command;

import de.jaylawl.superseatboi.SuperSeatBoi;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
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
                completions.add(
                        "reload"
                );
            }
        }

        return TabHelper.sortedCompletions(arguments[argumentNumber - 1], completions);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String label, @NotNull String[] arguments) {

        switch (arguments.length > 0 ? arguments[0].toLowerCase() : "") {

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
