package com.liyang.encodevideo.util;

import com.liyang.encodevideo.data.AddressUrl;

import org.litepal.LitePal;

import java.util.List;

public class DBCRUDUtils {
    /*
    查询所有的地址
     */
    public static List<AddressUrl> queryAddress(){
        return LitePal.findAll(AddressUrl.class);
    }
    /*
    删除多条地址
     */
    public static void deleteAddress(List<AddressUrl> addresses){
        for (AddressUrl url: addresses) {
            LitePal.delete(AddressUrl.class,url.getId());
        }
    }
    /*
    添加多条地址
     */
    public static void addAddress(List<AddressUrl> addresses){
        LitePal.saveAll(addresses);
    }
}
