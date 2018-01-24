package com.ryane.banner.util;

import android.util.SparseArray;

import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 * @author RyanLee
 * @date 2018/1/24
 */
public final class ListUtils {

    public static <D> boolean isEmpty(List<D> list) {
        return list == null || list.isEmpty();
    }

    public static <D> boolean isEmpty(D[] list) {
        return list == null || list.length == 0;
    }

    public static <D> boolean isEmpty(Set<D> set) {
        return set == null || set.isEmpty();
    }

    public static <D, R> boolean isEmpty(Map<D, R> map) {
        return map == null || map.isEmpty();
    }

    public static <D> boolean isEmpty(SparseArray<D> list) {
        return list == null || list.size() == 0;
    }
}
