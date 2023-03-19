package org.waveapi.content.resources;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class LangManager {

    private static Map<String, Map<String, StringBuilder>> langs = new HashMap<>();

    public static void addTranslation(String mod, String lang, String key, String translation) {
        Map<String, StringBuilder> modMap = langs.computeIfAbsent(mod, k -> new HashMap<>());
        StringBuilder langFile = modMap.computeIfAbsent(lang, k -> new StringBuilder().append("{"));
        langFile.append("\"").append(key).append("\":\"").append(translation).append("\",");
    }

    public static void write() {
        for (Map.Entry<String, Map<String, StringBuilder>> mods : langs.entrySet()) {
            File langFolder = new File(ResourcePackManager.getInstance().getPackDir(), "assets/" + mods.getKey() + "/lang");
            langFolder.mkdirs();

            for (Map.Entry<String, StringBuilder> lang : mods.getValue().entrySet()) {
                File langFile = new File(langFolder, lang.getKey() + ".json");
                StringBuilder builder = lang.getValue();
                builder.setCharAt(builder.length() - 1, '}');
                try {
                    Files.write(langFile.toPath(), lang.getValue().toString().getBytes());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        langs = null;
    }



}
