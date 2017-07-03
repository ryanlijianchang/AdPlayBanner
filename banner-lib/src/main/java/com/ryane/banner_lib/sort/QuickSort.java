package com.ryane.banner_lib.sort;

import com.ryane.banner_lib.AdPageInfo;

import java.util.ArrayList;

/**
 * Creator: lijianchang
 * Create Time: 2017/7/3.
 * Email: lijianchang@yy.com
 */

public class QuickSort {
    public static ArrayList<AdPageInfo> quickSort(ArrayList<AdPageInfo> oList, int left, int right) {
        AdPageInfo keyInfo;
        if (oList != null && oList.size() > 0) {
            if (left >= right) {
                return oList;
            }
            int i = left;
            int j = right;
            int key = oList.get(left).getOrder();
            keyInfo = oList.get(left);

            while (i < j) {
                while (i < j && key < oList.get(j).getOrder()) {
                    j--;
                }

                oList.set(i, oList.get(j));

                while (i < j && key >= oList.get(i).getOrder()) {
                    i++;
                }
                oList.set(j, oList.get(i));
            }

            oList.set(i, keyInfo);
            quickSort(oList, left, i - 1);
            quickSort(oList, i + 1, right);
        }
        return oList ;
    }
}
