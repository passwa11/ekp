package com.landray.kmss.sys.organization.service.spring;

import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.sys.datainit.service.ISysDatainitProcessService;
import com.landray.kmss.sys.datainit.service.ISysDatainitSurroundInterceptor;
import com.landray.kmss.sys.datainit.service.spring.ProcessRuntime;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;
import org.slf4j.Logger;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SysOrgMatrixDataInit implements ISysDatainitSurroundInterceptor {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(ISysDatainitSurroundInterceptor.class);

    private IBaseDao baseDao;

    public void setBaseDao(IBaseDao baseDao) {
        this.baseDao = baseDao;
    }

    private ISysDatainitProcessService sysDatainitProcessService;

    public ISysDatainitProcessService getSysDatainitProcessService() {
        if (sysDatainitProcessService == null) {
            sysDatainitProcessService = (ISysDatainitProcessService) SpringBeanUtil.getBean("sysDatainitProcessService");
        }
        return sysDatainitProcessService;
    }

    private ISysOrgMatrixService sysOrgMatrixService;

    public ISysOrgMatrixService getSysOrgMatrixService() {
        if (sysOrgMatrixService == null) {
            sysOrgMatrixService = (ISysOrgMatrixService) SpringBeanUtil.getBean("sysOrgMatrixService");
        }
        return sysOrgMatrixService;
    }

    private ISysOrgElementService sysOrgElementService;

    public ISysOrgElementService getISysOrgElementService() {
        if (sysOrgElementService == null) {
            sysOrgElementService = (ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
        }
        return sysOrgElementService;
    }

    //导出
    @Override
    public void beforeStoreModelData(IBaseModel model, Map<String, Object> data) throws Exception {
        if(model instanceof SysOrgMatrix){
            SysOrgMatrix sysOrgMatrix = (SysOrgMatrix) getSysOrgMatrixService().findByPrimaryKey(model.getFdId());
            if(sysOrgMatrix != null){
                //矩阵分类
                SysOrgMatrixCate sysOrgMatrixCate = sysOrgMatrix.getFdCategory();
                if(sysOrgMatrixCate != null){
                    getSysDatainitProcessService().exportToFile(sysOrgMatrixCate);
                }
                //矩阵字段
                List<SysOrgMatrixRelation> relationList = sysOrgMatrix.getFdRelations();
                if(!CollectionUtils.isEmpty(relationList)){
                    for(SysOrgMatrixRelation sysOrgMatrixRelation : relationList){
                        getSysDatainitProcessService().exportToFile(sysOrgMatrixRelation);
                    }
                }
                //矩阵数据分组
                List<SysOrgMatrixDataCate> dataCateList = sysOrgMatrix.getFdDataCates();
                if(!CollectionUtils.isEmpty(dataCateList)){
                    for(SysOrgMatrixDataCate sysOrgMatrixDataCate : dataCateList){
                        getSysDatainitProcessService().exportToFile(sysOrgMatrixDataCate);
                    }
                }
                //矩阵版本
                List<SysOrgMatrixVersion> versionList = sysOrgMatrix.getFdVersions();
                if(!CollectionUtils.isEmpty(versionList)){
                    for(SysOrgMatrixVersion sysOrgMatrixVersion : versionList){
                        getSysDatainitProcessService().exportToFile(sysOrgMatrixVersion);
                    }
                }
                //矩阵数据
                Map<Integer,String> columnMap = new HashMap<>();
                List<Object[]> matrixList = new ArrayList<>();
                for (int i = 0; i < versionList.size(); i++) {
                    String version = versionList.get(i).getFdName();
                    int pageNo = 1;
                    int rowSize = 100;
                    Page page = getSysOrgMatrixService().findMatrixPageToExport(sysOrgMatrix.getFdId(),version,pageNo,rowSize,null,columnMap);
                    matrixList.addAll(page.getList());
                    while (page.isHasNextPage()) {
                        pageNo++;
                        page = getSysOrgMatrixService().findMatrixPageToExport(sysOrgMatrix.getFdId(),version,pageNo,rowSize,null,columnMap);
                        if(CollectionUtils.isEmpty(page.getList())){
                            break;
                        }else{
                            matrixList.addAll(page.getList());
                        }
                    }
                }
                List<String> resultList = new ArrayList<>();
                StringBuilder stringBuilder = null;
                for (int i = 0; i < matrixList.size(); i++) {
                    stringBuilder = new StringBuilder();
                    Object[] objects = matrixList.get(i);
                    for(Object o : objects){
                        stringBuilder.append(o).append("|");
                    }
                    if(StringUtil.isNotNull(stringBuilder.toString())){
                        resultList.add(stringBuilder.toString().substring(0,stringBuilder.length()-1));
                    }else{
                        resultList.add("");
                    }
                }
                data.put("matrixList",resultList);
                data.put("columnMap",columnMap);
            }
        }
    }

    //导入
    @Override
    public void beforeRestoreModelData(IBaseModel model, Map<String, Object> data, Map<String, IBaseModel> cache, ProcessRuntime processRuntime) throws Exception {
        List<String> matrixList = (List<String>) data.get("matrixList");
        Map<Integer,String> columnMap = (Map<Integer,String>) data.get("columnMap");
        if(!CollectionUtils.isEmpty(matrixList) && !CollectionUtils.isEmpty(columnMap)){
            SysOrgPerson user = UserUtil.getUser();
            user.getCustomPropMap().put("matrixList",matrixList);
            user.getCustomPropMap().put("columnMap",columnMap);
            baseDao.getHibernateTemplate().update(user);
        }
    }
}
