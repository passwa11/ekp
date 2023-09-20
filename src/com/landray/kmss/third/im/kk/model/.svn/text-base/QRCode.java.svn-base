package com.landray.kmss.third.im.kk.model;
import java.util.Date;

import com.landray.kmss.common.model.BaseModel;


/**
 * @创建人: xionghaolin
 * @创建时间: 2020/9/25 13:46
 * @描述:
 */
public class QRCode{
    //二维码登录时的唯一标识
    private String uuid;
    
   //二维码base64编码
    private String base64Qrcode;

    //二维码失效时间
    private long expireTime;



    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getBase64Qrcode() {
        return base64Qrcode;
    }

    public void setBase64Qrcode(String base64Qrcode) {
        this.base64Qrcode = base64Qrcode;
    }
    
    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("QRCode{");
        sb.append("uuid='").append(uuid).append('\'');
        sb.append(", base64Qrcode='").append(base64Qrcode).append('\'');
        sb.append(", expireTime='").append(expireTime).append('\'');
        sb.append('}');
        return sb.toString();
    }


}


