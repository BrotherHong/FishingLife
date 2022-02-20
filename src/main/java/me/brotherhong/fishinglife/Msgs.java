package me.brotherhong.fishinglife;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public class Msgs {

    private static final FileConfiguration lang = FishingLife.getPlugin().getLangConfig().getConfig();

    public static final String PREFIX = ChatColor.translateAlternateColorCodes('&', lang.getString("prefix"));

    public static final String UNKNOWN_COMMAND = PREFIX + trans(lang.getString("unknown-command"));
    public static final String SELECTION_TOOL = PREFIX + trans(lang.getString("selection-tool"));
    public static final String NO_SELECT = PREFIX + trans(lang.getString("no-select"));
    public static final String AREA_CONFLICT = PREFIX + trans(lang.getString("area-conflict"));
    public static final String SAME_NAME = PREFIX + trans(lang.getString("same-name"));
    public static final String AREA_NOT_FOUND = PREFIX + trans(lang.getString("not-found"));
    public static final String NO_ITEM_IN_HAND = PREFIX + trans(lang.getString("no-item"));
    public static final String NO_DROPS = PREFIX + trans(lang.getString("no-drops"));
    public static final String SAME_DROPS = PREFIX + trans(lang.getString("same-drops"));
    public static final String LIST_AREA = PREFIX + trans(lang.getString("list-area"));
    public static final String ONLY_INTEGER = PREFIX + trans(lang.getString("only-integer"));
    public static final String ONLY_FLOAT = PREFIX + trans(lang.getString("only-float"));
    public static final String NO_PERMISSION = PREFIX + trans(lang.getString("no-permission"));
    public static final String STACK_LIMIT = PREFIX + trans(lang.getString("stack-limit"));

    public static final String CANCEL_MODIFY = PREFIX + trans(lang.getString("cancel-modify"));
    public static final String CANCEL_DELETE = PREFIX + trans(lang.getString("cancel-delete"));
    public static final String SUCCESS_DELETE = PREFIX + trans(lang.getString("successful-delete"));
    public static final String SUCCESS_ADD = PREFIX + trans(lang.getString("successful-add"));
    public static final String SUCCESS_CREATE = PREFIX + trans(lang.getString("successful-create"));
    public static final String FISH_CAUGHT = PREFIX + trans(lang.getString("fish-caught"));
    public static final String ONE_DIRECTION = PREFIX + trans(lang.getString("one-direction"));
    public static final String SUCCESS_EXPAND = PREFIX + trans(lang.getString("successful-expand"));
    public static final String SUCCESS_MODIFY = PREFIX + trans(lang.getString("successful-modify"));

    public static final String NEW_CHANCE_REQUEST = PREFIX + trans(lang.getString("new-chance-request"));
    public static final String NEW_AMOUNT_REQUEST = PREFIX + trans(lang.getString("new-amount-request"));
    public static final String CHANCE_DISPLAY = trans(lang.getString("chance-display"));
    public static final String CHANGE_CHANCE_HINT = trans(lang.getString("change-chance-hint"));
    public static final String CHANGE_AMOUNT_HINT = trans(lang.getString("change-amount-hint"));
    public static final String DELETE_HINT = trans(lang.getString("delete-hint"));
    public static final String FIRST_PAGE = PREFIX + trans(lang.getString("first-page"));
    public static final String LAST_PAGE = PREFIX + trans(lang.getString("last-page"));

    private static String trans(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

}
