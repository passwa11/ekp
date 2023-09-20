package com.landray.kmss.sys.filestore.scheduler.third.dianju.builder;

import com.landray.kmss.sys.filestore.scheduler.third.dianju.util.HmacSignSha;
import net.sf.json.JSONObject;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.TimeZone;

/**
 * 请求头构造
 */
public class ConvertRequestHeaderBuilder {
    private static final Logger logger = LoggerFactory.getLogger(ConvertRequestHeaderBuilder.class);
    private Map<String, String> callConvertHeader = new HashMap<>();

    /**
     * 设置请求类型 Content-Type
     * @return
     */
    public ConvertRequestHeaderBuilder configContentType() {
        callConvertHeader.put("Content-Type", "application/json");
        return this;
    }

    /**
     * 设置请求Content-Md5
     * @param appId
     * @param appKey
     * @param parameter
     * @return
     */
    public ConvertRequestHeaderBuilder configContentMd5(String appId, String appKey, Map parameter) {
        callConvertHeader.put("Content-Md5", getContentMd5(appId, appKey, parameter));
        return this;
    }

    /**
     * 设置请求头时间
     * @return
     */
    public ConvertRequestHeaderBuilder configDate() {

       // callConvertHeader.put("Date", getDate());

        return this;
    }

    /**
     * 设置授权信息
     * @param appId
     * @param appKey
     * @param parameter
     * @return
     */
    public ConvertRequestHeaderBuilder configAuthorization(String appId, String appKey, Map parameter) {
        String date = getDate();
        String contentType = "application/json";
        String content =  getContentMd5(appId, appKey, parameter) + "\n" + contentType + "\n" + date;
        String authorizationSha1 = HmacSignSha.getHmacSignSha1(content, appKey);
        callConvertHeader.put("Date", date);
        callConvertHeader.put("Authorization", String.format("DianJu:%s:%s", appId, authorizationSha1));

        return this;
    }

    public Map<String, String> config() {
        if(logger.isDebugEnabled()) {
            logger.debug("构造请求参数信息是{}", callConvertHeader.toString());
        }
        return callConvertHeader;
    }
    /**
     * 请求参数进行MD5加密
     *
     * @param appId
     * @param appKey
     * @param parameter
     * @return
     */
    public String getContentMd5(String appId, String appKey, Map parameter) {
        JSONObject jsonObject = JSONObject.fromObject(parameter);
        byte[] body = jsonObject.toString().getBytes();

        String contentMd5;
        if (parameter != null && body != null && body.length > 0) {
            contentMd5 = DigestUtils.md5Hex(body);
        } else {
            contentMd5 = DigestUtils.md5Hex("".getBytes());
        }

        return contentMd5;
    }


    public String getDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        String date = dateFormat.format(new Date());

        return date;
    }
}
