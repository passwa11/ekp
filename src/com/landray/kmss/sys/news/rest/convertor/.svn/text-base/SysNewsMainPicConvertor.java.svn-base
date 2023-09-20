package com.landray.kmss.sys.news.rest.convertor;

import com.landray.kmss.common.rest.convertor.PropertyConvertorContext;
import com.landray.kmss.common.rest.convertor.PropertyConvertorSupport;
import com.landray.kmss.sys.news.model.SysNewsMain;
import com.landray.kmss.sys.news.rest.controller.SysNewsMainIndexController;
import com.landray.kmss.sys.news.service.ISysNewsMainService;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @ClassName: SysNewsMainPicConvertor
 * @Author: admin
 * @Description: ${description}
 * @Date: 2020-12-15 18:35
 * @Version: 1.0
 */
public class SysNewsMainPicConvertor extends PropertyConvertorSupport {

    private final Log log = LogFactory.getLog(SysNewsMainPicConvertor.class);

    @Override
    public Object convert(Object value, PropertyConvertorContext context) {
        if (context != null) {
            SysNewsMain sysNews = (SysNewsMain)value;
            String newsId = sysNews.getFdId();
            ISysNewsMainService sysNewsMainService = (ISysNewsMainService) SpringBeanUtil.getBean("sysNewsMainService");
            try {
                String attachmentLink = sysNewsMainService.getAttachmentLink(newsId, "Attachment");
                return attachmentLink;
            } catch (Exception e) {
                log.error("无法获取标题图片url,newsId: " + newsId, e);
            }
        }
        return null;
    }
}
