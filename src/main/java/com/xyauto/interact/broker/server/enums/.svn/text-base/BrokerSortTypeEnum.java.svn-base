package com.xyauto.interact.broker.server.enums;

import java.util.HashMap;
import java.util.Map;

public enum BrokerSortTypeEnum {
    LevelTimeDesc((short) 0),
    LevelDistanceDesc((short) 1);

    private static class Holder {
        static Map<Short, BrokerSortTypeEnum> MAP = new HashMap<>();
    }

    private BrokerSortTypeEnum(short value) {
        Holder.MAP.put(value, this);
    }

    public static BrokerSortTypeEnum find(short val) {
        BrokerSortTypeEnum t = Holder.MAP.get(val);
        if (t == null) {
            throw new IllegalStateException(String.format("Unsupported type %s.", val));
        }
        return t;
    }
}
