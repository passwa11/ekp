package com.landray.kmss.sys.ui.cache;

import com.landray.kmss.sys.cache.filter.CommonWebContentCache;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.util.RequestUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * 简单JSP缓存，适用于一些通过jsp来输出代码片段的（大多数都是为了构造多语言的代码片段）
 *
 * @author 陈进科
 */
public class SimpleJspCache extends CommonWebContentCache {

    @Override
    public String getDisplayName() {
        return "sys-ui:cache.simplejspcache";
    }

    @Override
    public String getDescription() {
        return "sys-ui:cache.simplejspcache.desc";
    }

    @Override
    protected CacheContext doGetCacheContextForCurRequest(HttpServletRequest request) {
        /*
         * 这里尝试获取当前会话使用的语言信息，用于构造缓存key
         */
        String locale = ResourceUtil.getLocaleStringByUser(request);
        String uri = RequestUtils.getRequestURI(request);

        //前端初始多语言的缓存
        if ("/sys/ui/js/lang/lang.jsp".equals(uri)) {
            String cacheKey = uri + ":" + this.getMD5StringWithParameters(request, false) + ":" + locale;
            return new CacheContext(cacheKey, getDefaultCacheExpire(request),
                    "text/javascript;charset=UTF-8", this);
        }
        //分页的片段代码
        if ("/sys/ui/extend/listview/paging.jsp".equals(uri)) {
            String cacheKey = uri + ":" + this.getMD5StringWithParameters(request, false);
            return new CacheContext(cacheKey, getDefaultCacheExpire(request),
                    "text/html;charset=UTF-8", this);
        }

        if ("/sys/common/dataxml.jsp".equals(uri)) {
            //只处理前端通过s_bean=XMLGetResourceService来获取多语言信息的接口
            String s_bean = request.getParameter("s_bean");
            if ("XMLGetResourceService".equalsIgnoreCase(s_bean)) {
                String cacheKey = uri + ":" + this.getMD5StringWithParameters(request, false) + ":" + locale;
                if (StringUtil.isNotNull(cacheKey)) {
                    return new CacheContext(cacheKey, getDefaultCacheExpire(request),
                            "text/xml;charset=UTF-8", this);
                }
            }
        }
        //ck控件需要的多语言
        if ("/resource/ckeditor/ckresize_lang.jsp".equals(uri)) {
            String cacheKey = "ckresize_lang.jsp:" + locale;
            return new CacheContext(cacheKey, getDefaultCacheExpire(request),
                    "text/html;charset=UTF-8", this);
        }
        //校验器所需要的资源信息
        if ("/resource/js/validation.jsp".equals(uri)
                || "/resource/js/validator.jsp".equals(uri)) {
            String cacheKey = uri + ":" + locale;
            return new CacheContext(cacheKey, getDefaultCacheExpire(request),
                    "application/x-javascript;charset=UTF-8", this);
        }
        if("/sys/ui/extend/listview/paging_simple.jsp".equals(uri)){
            String cacheKey = uri + ":" + locale;
            return new CacheContext(cacheKey, getDefaultCacheExpire(request),
                    "application/x-javascript;charset=UTF-8", this);

        }
        return null;
    }

    @Override
    protected String[] initSupportedUrlPrefix() {
        return new String[]{
                "/resource/js/***",
                "/resource/js/validation.jsp",
                "/resource/js/validator.jsp",
                "/resource/ckeditor/ckresize_lang.jsp",
                "/sys/ui/js/lang/lang.jsp",
                "/sys/ui/extend/listview/paging.jsp",
                "/sys/ui/extend/listview/paging_simple.jsp",
                "/sys/common/dataxml.jsp"
        };
    }
}
