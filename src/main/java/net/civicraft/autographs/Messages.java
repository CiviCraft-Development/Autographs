package net.civicraft.autographs;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

public class Messages {
    public static final TextColor PRIMARY = TextColor.color(222, 199, 49);
    public static final TextColor SECONDARY = TextColor.color(242, 232, 165);

    // General purpose
    public final static Component PREFIX = Component.text("[Autographs] ").color(PRIMARY);
    public static final Component NO_PERMISSION = PREFIX.append(Component.text("You do not have permission to perform this command").color(SECONDARY));

    // /autograph
    public static final Component AUTOGRAPH_RELOAD = PREFIX.append(Component.text("The config file has been reloaded!").color(SECONDARY));
    public static final Component AUTOGRAPH_ITEM_RESTRICTED = PREFIX.append(Component.text("This item cannot be signed. Hold a signable item.").color(SECONDARY));
    public static final Component AUTOGRAPH_EXISTING = PREFIX.append(Component.text("You've already signed this item.").color(SECONDARY));
    public static final Component AUTOGRAPH_SUCCESS = PREFIX.append(Component.text("You've successfully signed the item.").color(SECONDARY));
    public static final Component AUTOGRAPH_FAILURE = PREFIX.append(Component.text("Autographing the item failed.").color(SECONDARY));
}
