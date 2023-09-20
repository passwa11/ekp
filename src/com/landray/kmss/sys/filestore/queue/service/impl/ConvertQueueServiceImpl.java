package com.landray.kmss.sys.filestore.queue.service.impl;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.filestore.model.SysFileConvertQueue;
import com.landray.kmss.sys.filestore.queue.service.ConvertQueueService;
import com.landray.kmss.sys.filestore.queue.dto.QueryParameter;
import com.landray.kmss.sys.filestore.service.ISysFileConvertDataService;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 数据库调用接口实现
 */
public class ConvertQueueServiceImpl extends BaseServiceImp implements ConvertQueueService {
    /**
     * data 服务
     */
    private ISysFileConvertDataService dataService = null;
    private ISysFileConvertDataService getDataService() {
        if (dataService == null) {
            dataService = (ISysFileConvertDataService) SpringBeanUtil.getBean("sysFileConvertDataService");
        }
        return dataService;
    }
    /**
     * 获取数量
     * @return
     * @throws Exception
     */
    @Override
    public Integer getCount(QueryParameter queryParameter) throws Exception {
        Page page = getPage(queryParameter);
        return page == null ? 0: page.getList().size();
    }
    /**
     * 获取信息
     * @return
     * @throws Exception
     */
    @Override
    public List<SysFileConvertQueue> getUnassignedTasks(QueryParameter queryParameter) throws Exception {
        Page page = getPage(queryParameter);
        return page == null ? new ArrayList<>() : page.getList();
    }

    /**
     * 查询
     * @return
     * @throws Exception
     */
    public Page getPage(QueryParameter queryParameter) throws Exception{
        String[] converterKeys = queryParameter.getConverterKeys();
        String convertType = queryParameter.getConvertType();
        int status = queryParameter.getStatus();
        int pageNo = queryParameter.getPageNo();
        int rowSize = queryParameter.getRowSize();
        Integer convertNumber = queryParameter.getConvertNumber();

        /**
         * 转换类型
         */
        String converterKey = "";
        for(String key : converterKeys) {
            converterKey += "\'" + key + "\',";
        }
        converterKey = converterKey.substring(0, converterKey.lastIndexOf(","));

        /**
         * 转换次数
         */
        HQLInfo hqlInfo = new HQLInfo();
        String whereBlock = " fdConvertStatus =:fdConvertStatus and fdConverterType =:fdConverterType " +
                " and fdConverterKey in (" +converterKey +" ) ";

        if(convertNumber != null && convertNumber >= 0) {
            whereBlock += " and fdConvertNumber <=:convertNumber  ";
            hqlInfo.setParameter("convertNumber", convertNumber);
        }



        hqlInfo.setWhereBlock(whereBlock);
        hqlInfo.setParameter("fdConvertStatus", status);
        hqlInfo.setParameter("fdConverterType", convertType);
        hqlInfo.setOrderBy(" fdStatusTime desc");
        hqlInfo.setPageNo(pageNo);
        hqlInfo.setRowSize(rowSize);
        hqlInfo.setCheckParam(SysAuthConstant.CheckType.AuthCheck, SysAuthConstant.AuthCheck.SYS_NONE);
        Page page = findPage(hqlInfo);

        return page;
    }
}
