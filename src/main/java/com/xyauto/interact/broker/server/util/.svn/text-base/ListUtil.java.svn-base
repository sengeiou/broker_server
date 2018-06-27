package com.xyauto.interact.broker.server.util;

import java.util.List;

/**
 *
 * @author joe
 */
public class ListUtil {

    public static <T> List<T> clearZero(List<T> list) {
        if (list.isEmpty() == false) {
            if (list.get(0) instanceof Integer && Integer.valueOf(list.get(0).toString()) == 0) {
                list.remove(0);
                return list;
            }
            if (list.get(0) instanceof Short && Short.valueOf(list.get(0).toString()) == 0) {
                list.remove(0);
                return list;
            }
            if (list.get(0) instanceof Long && Long.valueOf(list.get(0).toString()) == 0) {
                list.remove(0);
                return list;
            }
        }
        return list;
    }

}
