package com.gaohongsen;

/*
 * 此类封装了一个表示是否成功的布尔值与一个Object对象，便于客户端与服务端之间的通讯
 *
 * @author 高洪森
 * @param hasSuccess 表示是否成功的布尔值
 * @param item 传输的对象
 */
public class Reply implements java.io.Serializable {
    private final boolean hasSucceed;
    private final Object item;

    public Reply(boolean hasSuccess, Object item) {
        this.hasSucceed = hasSuccess;
        this.item = item;
    }

    public boolean hasSucceed() {
        return hasSucceed;
    }

    public Object getItem() {
        return item;
    }
}
