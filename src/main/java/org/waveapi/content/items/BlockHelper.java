package org.waveapi.content.items;

import org.waveapi.api.content.items.block.WaveBlock;
import org.waveapi.utils.ClassHelper;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class BlockHelper {
    public static Map<String, ClassHelper.InterfaceImpl> blockPossibleInterfaces;

    public static void populateEntityPossibleInterfaces() {
        blockPossibleInterfaces = new HashMap<>();
    }

    public static <T extends WaveBlock> List<ClassHelper.InterfaceImpl> searchUp(Class<T> block) {
        List<ClassHelper.InterfaceImpl> list = new LinkedList<>();

        if (blockPossibleInterfaces == null) {
            populateEntityPossibleInterfaces();
        }

        for (Type type : block.getGenericInterfaces()) {
            ClassHelper.InterfaceImpl impl = blockPossibleInterfaces.get(type.getTypeName());
            if (impl != null) {
                list.add(impl);
            }
        }

        return list;
    }
}
