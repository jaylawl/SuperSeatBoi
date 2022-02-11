package de.jaylawl.superseatboi.command;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

// Copied as is from CommonLjb @ 11.02.2022

public class TabHelper {

    private TabHelper() {
    }

    //

    public static int getArgNumber(@NotNull String[] arguments) {
        return evaluateArgumentNumber(arguments);
    }

    public static int evaluateArgumentNumber(@NotNull String[] arguments) {
        int argumentNumber = 0;
        for (String argument : arguments) {
            if (!argument.equals("")) {
                argumentNumber++;
            }
        }
        if (arguments[arguments.length - 1].equals("")) {
            argumentNumber++;
        }
        return argumentNumber;
    }

    public static @NotNull List<String> sortedCompletions(@NotNull String lastArgument, @NotNull List<String> completions) {
        List<String> sortedCompletions = new ArrayList<>();
        StringUtil.copyPartialMatches(lastArgument, completions, sortedCompletions);
        Collections.sort(sortedCompletions);
        return sortedCompletions;
    }

    public static @NotNull Collection<String> getOnlinePlayerNames() {
        Collection<String> onlinePlayerNames = new ArrayList<>();
        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            onlinePlayerNames.add(onlinePlayer.getName());
        }
        return onlinePlayerNames;
    }

}
