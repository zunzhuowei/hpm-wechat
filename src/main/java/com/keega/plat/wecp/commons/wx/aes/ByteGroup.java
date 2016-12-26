package com.keega.plat.wecp.commons.wx.aes;

import java.util.ArrayList;

/**
 * Created by zun.wei on 2016/12/22.
 * To change this template use File|Default Setting
 * |Editor|File and Code Templates|Includes|File Header
 */
public class ByteGroup {

    ArrayList<Byte> byteContainer = new ArrayList<Byte>();

    public byte[] toBytes() {
        byte[] bytes = new byte[byteContainer.size()];
        for (int i = 0; i < byteContainer.size(); i++) {
            bytes[i] = byteContainer.get(i);
        }
        return bytes;
    }

    public ByteGroup addBytes(byte[] bytes) {
        for (byte b : bytes) {
            byteContainer.add(b);
        }
        return this;
    }

    public int size() {
        return byteContainer.size();
    }
}
