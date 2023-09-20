package com.landray.kmss.km.review.cache;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.constant.SysDocConstant;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.sys.cache.CacheConfig;
import com.landray.kmss.sys.cache.KmssCache;
import com.landray.kmss.sys.cache.filter.AbstractWebContentCache;
import com.landray.kmss.sys.cache.filter.CommonWebContentCache;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.InitializingBean;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 流程管理页面请求内容缓那些内容基本固定的页面
 */
public class KmReviewContentCache extends CommonWebContentCache {

    private IKmReviewMainService kmReviewMainService;

    public IKmReviewMainService getKmReviewMainService() {
        if(kmReviewMainService==null){
            kmReviewMainService = (IKmReviewMainService) SpringBeanUtil.getBean("kmReviewMainService");
        }
        return kmReviewMainService;
    }

    @Override
    public String getDisplayName() {
        return "km-review:cache.webcontent";
    }

    @Override
    public String getDescription() {
        return "km-review:cache.webcontent.desc";
    }

    @Override
    protected CacheContext doGetCacheContextForCurRequest(HttpServletRequest request) {
        CacheContext cacheContext=null;
        cacheContext = getCacheContextForEveryone(request,0,null);
        if(cacheContext!=null){
            return cacheContext;
        }
        cacheContext = getCacheContextForPublishedDoc(request);
        if(cacheContext!=null){
            return cacheContext;
        }
//        cacheContext = getCacheContextForSpecificTemplate(request);
//        if(cacheContext!=null){
//            return cacheContext;
//        }
        return null;
    }

    @Override
    protected String[] initSupportedUrlPrefix() {
        return new String[]{
                "/km/review/km_review_main/kmReviewMain.do?method=view*"
        };
        //"/km/review/km_review_main/kmReviewMain.do?method=add*"
    }

    /**
     * 对某些特定的模板新建页使用缓存 kmReviewMain.do?method=add&fdTemplateId=xxxx
     * @param request
     * @return
     */
    private CacheContext getCacheContextForSpecificTemplate(HttpServletRequest request){
        String method = request.getParameter("method");
        //这是一个样例
        if("add".equals(method)){
            String fdTemplateId = request.getParameter("fdTemplateId");
            //这个if可以更加灵活，这里只做样式
            if("17d2880ac638cf283d3844248988f2cf".equals(fdTemplateId)){
                return new CacheContext("addWithTemplate:"+fdTemplateId,
                        getDefaultCacheExpire(request),
                        CONTENT_TYPE_HTML, this);
            }
        }
        return null;
    }

    /**
     * 获取状态已经是30的文档页面的缓存标识
     * @param request
     * @return
     */
    private CacheContext getCacheContextForPublishedDoc(HttpServletRequest request){
        String method = request.getParameter("method");
        if("view".equals(method)){
            String fdId = request.getParameter("fdId");
            String docStatusOf = getDocStatus(fdId);
            if(StringUtil.isNotNull(docStatusOf)
                    && SysDocConstant.DOC_STATUS_PUBLISH.equals(docStatusOf)){
                String user = UserUtil.getKMSSUser().getUserId();
                return new CacheContext("ProcessFinished:"+fdId,
                        getDefaultCacheExpire(request),
                        "text/html;charset=UTF-8", this);
            }
        }
        return null;
    }

    private String getDocStatus(String kmReviewMainFdId){
        String docStatus = null;
        try {
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setSelectBlock("docStatus");
            hqlInfo.setWhereBlock("fdId = :fdId");
            hqlInfo.setParameter("fdId",kmReviewMainFdId);
            List values = getKmReviewMainService().findValue(hqlInfo);
            if(!CollectionUtils.isEmpty(values)){
                docStatus = String.valueOf(values.get(0));
            }
        }catch (Exception e){
            if(logger.isDebugEnabled()){
                logger.debug(e.getMessage(),e);
            }
        }
        return docStatus;
    }

}
