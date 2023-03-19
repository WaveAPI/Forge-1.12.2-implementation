package org.waveapi.api;

import net.minecraft.launchwrapper.Launch;
import org.waveapi.Main;
import org.waveapi.utils.ByteUtils;
import org.yaml.snakeyaml.Yaml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.HashMap;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

public class WaveLoader {

    public static Map<String, WrappedWaveMod> getMods() {
        return mods;
    }

    public static class WrappedWaveMod {
        public WaveMod mod;
        public File file;
        public boolean changed;
    }

    public static Map<String, WrappedWaveMod> mods = new HashMap<>();

    private static boolean nextChanged = false;
    public static void init() {

        File modFolder = new File("./mods");
        File[] mods = modFolder.listFiles();

        File modified = new File(Main.mainFolder, "modifCache.txt");

        Map<String, Long> lastModified = new HashMap<>();

        if (!(boolean) Launch.blackboard.get("fml.deobfuscatedEnvironment")) {
            try {
                if (modified.isFile()) {
                    FileInputStream in = new FileInputStream(modified);

                    while (in.available() > 0) {
                        lastModified.put(ByteUtils.readString(in), ByteUtils.readLong(in));
                    }
                    in.close();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        for (File mod : mods) {
            try {
                if (!mod.getName().endsWith(".jar")) {
                    continue;
                }
                ZipFile file = new ZipFile(mod);
                ZipEntry waveYml = file.getEntry("wave.yml");
                if (waveYml == null) {
                    continue;
                }

                URL[] urls = new URL[] {mod.toURI().toURL()};
                URLClassLoader classLoader = new URLClassLoader(urls, WaveLoader.class.getClassLoader());


                if (mod.lastModified() > lastModified.getOrDefault(mod.getName(), 0L)) {
                    lastModified.put(mod.getName(), mod.lastModified());
                    Main.LOGGER.info("Found modified mod " + mod.getName());
                    nextChanged = true;
                } else {
                    Main.LOGGER.info("Found mod " + mod.getName());
                }

                Yaml yaml = new Yaml();
                Map<String, Object> params = yaml.load(file.getInputStream(waveYml));

                Object mainObject = params.get("main");
                if (mainObject instanceof String) {
                    String main = (String) params.get("main");
                    Class<?> c = classLoader.loadClass(main);

                    c.getConstructor().newInstance();

                } else {
                    Main.LOGGER.info("Mod " + mod.getName() + " has bad main path");
                }

            } catch (Exception e) {
                throw new RuntimeException("Failed in pre-init of waveAPI mod [" + mod.getName() + "]", e);
            }
        }
        try {
            if (modified.exists()) {
                modified.delete();
            }
            modified.getParentFile().mkdirs();
            modified.createNewFile();
            FileOutputStream out = new FileOutputStream(modified);
            for (Map.Entry<String, Long> entry : lastModified.entrySet()) {
                out.write(ByteUtils.encodeString(entry.getKey()));
                out.write(ByteUtils.encodeLong(entry.getValue()));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void register(WaveMod mod) {
        WrappedWaveMod m = new WrappedWaveMod();

        m.mod = mod;
        m.changed = nextChanged;
        nextChanged = false;

        mods.put(mod.name, m);
    }

}
