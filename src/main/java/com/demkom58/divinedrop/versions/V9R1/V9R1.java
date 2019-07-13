package com.demkom58.divinedrop.versions.V9R1;

import com.demkom58.divinedrop.ConfigurationData;
import com.demkom58.divinedrop.DivineDrop;
import com.demkom58.divinedrop.ItemsHandler;
import com.demkom58.divinedrop.lang.Language;
import com.demkom58.divinedrop.versions.V8R3.V8LangParser;
import com.demkom58.divinedrop.versions.V8R3.V8Listener;
import com.demkom58.divinedrop.versions.V8R3.V8R3;
import com.demkom58.divinedrop.versions.Version;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

public class V9R1 implements Version {
    public static final String VERSION = "1.9";

    private final DivineDrop plugin;
    private final ConfigurationData data;
    private final ItemsHandler logic;

    private V9R1() {
        this.plugin = null;
        this.data = null;
        this.logic = null;
    }

    public V9R1(@NotNull final DivineDrop plugin,
                @NotNull final ConfigurationData data,
                @NotNull final ItemsHandler logic) {
        this.plugin = plugin;
        this.data = data;
        this.logic = logic;
    }

    @Override
    public String getI18NDisplayName(@Nullable ItemStack item) {
        if (item == null)
            return null;

        return getName(item);
    }

    @NotNull
    @Override
    public Map<String, String> parseLang(@NotNull InputStream inputStream) throws IOException {
        return V8LangParser.parseLang(inputStream);
    }

    @NotNull
    @Override
    public String getLangPath(@NotNull final String locale) {
        return String.format(V8R3.PATH, V8R3.langFormat(locale));
    }

    @NotNull
    @Override
    public String id() {
        return VERSION;
    }

    @NotNull
    @Override
    public Listener getListener() {
        return new V8Listener(plugin, data, logic);
    }

    @Override
    public @NotNull String reformatLangCode(@NotNull final String localeCode) {
        return V8R3.langCode(localeCode);
    }

    private String getName(ItemStack bItemStack) {
        net.minecraft.server.v1_9_R1.ItemStack itemStack = org.bukkit.craftbukkit.v1_9_R1.inventory.CraftItemStack.asNMSCopy(bItemStack);
        String s = getLangNameNMS(itemStack);
        if (itemStack.getTag() != null && itemStack.getTag().hasKeyOfType("display", 10)) {
            net.minecraft.server.v1_9_R1.NBTTagCompound nbtTagCompound = itemStack.getTag().getCompound("display");

            if (nbtTagCompound.hasKeyOfType("Name", 8))
                s = nbtTagCompound.getString("Name");
        }
        return s;
    }

    private String getLangNameNMS(net.minecraft.server.v1_9_R1.ItemStack itemStack) {
        return Language.getInstance().getLocName(itemStack.getItem().f_(itemStack) + ".name").trim();
    }

}
