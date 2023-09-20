package com.landray.kmss.sys.organization.handover;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.IBaseDao;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.handover.interfaces.config.*;
import com.landray.kmss.sys.handover.model.SysHandoverConfigLogDetail;
import com.landray.kmss.sys.organization.model.*;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixRelationService;
import com.landray.kmss.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.BooleanUtils;
import org.slf4j.Logger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 矩阵组织工作交接
 * @author 苏琦
 * @date 2021-11-24
 */
public class SysOrgMatrixHandler implements IHandoverHandler {
    private static final Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgMatrixHandler.class);

    private ISysOrgMatrixRelationService sysOrgMatrixRelationService;

    public ISysOrgMatrixRelationService getSysOrgMatrixRelationService() {
        if (sysOrgMatrixRelationService == null) {
            sysOrgMatrixRelationService = (ISysOrgMatrixRelationService) SpringBeanUtil.getBean("sysOrgMatrixRelationService");
        }
        return sysOrgMatrixRelationService;
    }

    //矩阵数据类型-人员
    final static String MATRIX_PERSON = "person";
    //矩阵数据类型-岗位
    final static String MATRIX_POST = "post";
    //矩阵数据类型-人+岗位
    final static String MATRIX_PERSON_POST = "person_post";

    @Override
    public void search(HandoverSearchContext context) throws Exception {
        SysOrgElement handoverOrg = context.getHandoverOrg();
        //判断是交接人是人员或者岗位 部门不处理
        boolean isPerson = handoverOrg.getFdOrgType().equals(
                SysOrgConstant.ORG_TYPE_PERSON);
        boolean isPost = handoverOrg.getFdOrgType().equals(
                SysOrgConstant.ORG_TYPE_POST);
        int total = 0;
        Map<String,HandoverItem> itemsMap = new HashMap<>();
        if(isPerson || isPost) {
            //查询交接人的相关的矩阵数据
            List<SysOrgMatrixRelation> matrixRelationList  = buildHandoverHql(handoverOrg);
            if(!ArrayUtil.isEmpty(matrixRelationList)){
                for (SysOrgMatrixRelation matrixRelationObj : matrixRelationList) {
                    if (matrixRelationObj.getFdMatrix() != null) {
                        String matrixId = matrixRelationObj.getFdMatrix().getFdId(); //矩阵id
                        String matrixName = matrixRelationObj.getFdMatrix().getFdName(); //矩阵名字
                        String relationId = matrixRelationObj.getFdId();
                        //匹配交接人
                        boolean isTrue = searchHandoverOrg(handoverOrg, matrixRelationObj);
                        if (isTrue) {
                            //添加item
                            addHandoverItem(itemsMap, matrixId, matrixName);
                            //记录
                            String desc = buildDesc(matrixRelationObj, handoverOrg);
                            //构建url
                            context.addHandoverRecord(getIdWithPrefix(context, matrixId, relationId), getUrl(matrixRelationObj.getFdMatrix()), new String[]{desc});
                            total++;
                        }
                    }
                }
            }
        }
        HandoverSearchResult result= context.getHandoverSearchResult();
        //构造itemsJsonArray
        JSONArray itemArray = builJsonArray(result,itemsMap);
        //构造json
        JSONObject jsonObject =new JSONObject();
        jsonObject.accumulate("item", itemArray);
        jsonObject.put("module", context.getModule());
        jsonObject.put("total", total);
        jsonObject.put("moduleMessageKey", result.getModuleMessageKey());
        context.setSysMatrixJson(jsonObject);
    }


    /**
     * 构造查询交接人的数据
     * @param handoverOrg
     * @return
     * @throws Exception
     */
    private List<SysOrgMatrixRelation> buildHandoverHql(SysOrgElement handoverOrg) throws Exception {
        boolean isPerson = handoverOrg.getFdOrgType().equals(
                SysOrgConstant.ORG_TYPE_PERSON);
        boolean isPost = handoverOrg.getFdOrgType().equals(
                SysOrgConstant.ORG_TYPE_POST);
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setFromBlock("com.landray.kmss.sys.organization.model.SysOrgMatrixRelation sysOrgMatrixRelation");
        hqlInfo.setWhereBlock(" sysOrgMatrixRelation.fdType in(:typeArray)");
        ArrayList<String> typeList = new ArrayList<String>();
        typeList.add(MATRIX_PERSON_POST); //人+岗
        if (isPerson) {
            typeList.add(MATRIX_PERSON);//人员
            hqlInfo.setParameter("typeArray", typeList);
        }
        if (isPost) {
            typeList.add(MATRIX_POST);//岗位
            hqlInfo.setParameter("typeArray", typeList);
        }
        List<SysOrgMatrixRelation> matrixRelationList  = getSysOrgMatrixRelationService().findList(hqlInfo);
        return matrixRelationList;
    }
    private JSONArray builJsonArray(HandoverSearchResult result, Map<String,HandoverItem> itemsMap) {
        List<HandoverRecord> recordsList =  result.getHandoverRecords();
        List<HandoverSearchResult> resultList = new ArrayList();
        JSONArray jsonArray = new JSONArray();
        for(Map.Entry<String, HandoverItem> entry : itemsMap.entrySet()){
            HandoverItem item = entry.getValue();
            HandoverSearchResult handoverSearchResult =new HandoverSearchResult(result.getModule(),item.getItemMessageKey());
            handoverSearchResult.setItemMessageKey(item.getItemMessageKey());
            resultList.add(handoverSearchResult);
            String fdId = item.getItem(); //fdId
            long total = 0L;
            for(HandoverRecord record:recordsList){
                String recordId = record.getId();
                if(recordId.indexOf(fdId) > -1){
                    handoverSearchResult.addHandoverRecord(record.getId(),record.getUrl(),record.getDatas());
                    total++;
                }
                handoverSearchResult.setTotal(total); //设置总数
            }
            jsonArray.add(JSONObject.fromObject(handoverSearchResult));
        }
        return jsonArray;
    }

    /**
     * 构造描述
     * @param matrixRelationObj
     * @param handoverOrg
     * @return
     */

    private String buildDesc(SysOrgMatrixRelation matrixRelationObj, SysOrgElement handoverOrg) {
        String desc = "";
        Boolean isResult = matrixRelationObj.getFdIsResult();
        if(BooleanUtils.isTrue(isResult)){
            desc += ResourceUtil.getString("sysOrgMatrix.simulator.result", "sys-organization")+":"+matrixRelationObj.getFdName()+"("+handoverOrg.getFdName() +")";
        }else{
            desc += ResourceUtil.getString("sysOrgMatrix.simulator.conditional", "sys-organization")+":"+matrixRelationObj.getFdName()+"("+handoverOrg.getFdName() +")";
        }
        return desc;
    }

    /**
     * 获取有前缀的id
     *
     * @param context
     * @param matrixId 矩阵主表ID
     * @param relationId 矩阵关系表ID
     * @return
     */
    private String getIdWithPrefix(HandoverSearchContext context, String matrixId,String relationId) {
        return context.getModule() + ID_SPLIT + matrixId+ID_SPLIT+relationId;
    }

    /**
     * 匹配交接人ID相同的矩阵数据
     * @param handoverOrg
     * @param matrixRelationObj
     */
    private boolean searchHandoverOrg(SysOrgElement handoverOrg, SysOrgMatrixRelation matrixRelationObj) {
        boolean flag= false;
        String fdFieldName = matrixRelationObj.getFdFieldName(); //字段名
        SysOrgMatrix sysOrgMatrix = matrixRelationObj.getFdMatrix(); //所属矩阵
        String fdSubTable = sysOrgMatrix.getFdSubTable(); //矩阵数据表
        //构建查询条件
        String sql=" select "+fdFieldName+" from "+ fdSubTable;
        List list = getBaseDao().getHibernateSession().createNativeQuery(sql).list();
        if(!ArrayUtil.isEmpty(list)){
            for(int i=0;i<list.size();i++) {
                String fieldId = (String) list.get(i);
                if (StringUtil.isNotNull(fieldId) && fieldId.contains(handoverOrg.getFdId())) {//交接人的ID
                    flag = true;
                }
            }
        }
        return flag;
    }

    @Override
    public void execute(HandoverExecuteContext context) throws Exception {
        SysOrgElement fromOrg = context.getFrom(); //交接人
        SysOrgElement toOrg = context.getTo(); //接受人
        List<String> ids = context.getSelectedRecordIds(); //工作交接记录集
        for (int i = 0; i < ids.size(); i++) {
            String id = ids.get(i).trim();
            String fdId = ids.get(i).trim();
            fdId = fdId.split(ID_SPLIT)[1];
            SysOrgMatrixRelation sysOrgMatrixRelation = (SysOrgMatrixRelation) getSysOrgMatrixRelationService().findByPrimaryKey(fdId);
            SysOrgMatrix sysOrgMatrix = sysOrgMatrixRelation.getFdMatrix();
            String fdType = sysOrgMatrixRelation.getFdType();
            String toOrgId="";
            if(toOrg !=null){ //如果接收人不为空
                toOrgId = toOrg.getFdId();
                Integer toOrgType = toOrg.getFdOrgType();
                if(fdType.equals(MATRIX_PERSON)){ //要求类型是人员
                    if(!toOrgType.equals(SysOrgConstant.ORG_TYPE_PERSON)){
                        String msg = ResourceUtil.getString("sysOrgMatrix.handler.error", "sys-organization");
                        context.info(context.getModule() + ID_SPLIT +id, msg);
                        continue;
                    }
                }else if(fdType.equals(MATRIX_POST)){//要求类型是岗位
                    if(!toOrgType.equals(SysOrgConstant.ORG_TYPE_POST)){
                        String msg = ResourceUtil.getString("sysOrgMatrix.handler.error", "sys-organization");
                        context.info(context.getModule() + ID_SPLIT +id, msg);
                        continue;
                    }
                }else if(fdType.equals(MATRIX_PERSON_POST)){ //要求类型人+岗位
                    if(!toOrgType.equals(fromOrg.getFdOrgType())){ //交接人和接收人类型不相同不交接
                        String msg = ResourceUtil.getString("sysOrgMatrix.handler.error", "sys-organization");
                        context.info(context.getModule() + ID_SPLIT +id, msg);
                        continue;
                    }
                }
            }
            String tableName = sysOrgMatrix.getFdSubTable();  //表名
            String fdFieldName = sysOrgMatrixRelation.getFdFieldName(); //字段名
            String sql1=" select "+fdFieldName+" from "+ tableName;
            List<String> list = getBaseDao().getHibernateSession().createNativeQuery(sql1).list();
            if(!ArrayUtil.isEmpty(list)){
                for (String field :list){
                    if(StringUtil.isNotNull(field) && field.contains(fromOrg.getFdId())){
                        String newField = field.replaceAll(fromOrg.getFdId(),toOrgId);
                        String[] fieldArr = newField.split(";");
                        if(fieldArr.length>1) {
                            newField = getDistinct(fieldArr); //去重
                        }
                        if (newField.endsWith(";")) {
                            newField =newField.substring(0, newField.length() - 1);
                        }
                        String sql2=" update " + tableName + " set " + fdFieldName + " =? " + " where "+ fdFieldName + " =? ";
                        getBaseDao().getHibernateSession().createNativeQuery(sql2)
                                .setParameter(0, newField).setParameter(1,field).executeUpdate();
                    }
                }
            }
            //记录交接日志
            String desc = getDesc(sysOrgMatrixRelation,toOrg);
            context.log(fdId, getModelName(sysOrgMatrix),desc,getUrl(sysOrgMatrixRelation.getFdMatrix()),"", SysHandoverConfigLogDetail.STATE_SUCC);
        }
    }

    /**
     * 构造记录日志的描述
     * @param matrixRelationObj
     * @param toOrg
     * @return
     */
    private String getDesc(SysOrgMatrixRelation matrixRelationObj, SysOrgElement toOrg) {
        Boolean isResult = matrixRelationObj.getFdIsResult();
        SysOrgMatrix sysOrgMatrix = matrixRelationObj.getFdMatrix();
        String desc = "";
        String fdName="";
        if(toOrg!= null) {//如果交接人不为空 显示交接人
            fdName = toOrg.getFdName();
        }
        if (BooleanUtils.isTrue(isResult)) {
            desc += sysOrgMatrix.getFdName()+CONN_SYM+ ResourceUtil.getString("sysOrgMatrix.simulator.result", "sys-organization") + ":" + matrixRelationObj.getFdName() + "(" + fdName+ ")";
        } else {
            desc += sysOrgMatrix.getFdName()+CONN_SYM+ ResourceUtil.getString("sysOrgMatrix.simulator.conditional", "sys-organization") + ":" + matrixRelationObj.getFdName() + "(" + fdName + ")";
        }
        return desc;
    }


    /**
     * 获取modelName
     *
     * @param model
     * @return
     */
    private String getModelName(IBaseModel model) {
        return ModelUtil.getModelClassName(model);
    }

    public static String getDistinct(String [] arrStr) {
        String newField="";
        for (int i=0; i<arrStr.length; i++) {
            if(!newField.contains(arrStr[i])) {
                newField += arrStr[i]+";";
            }
        }
        return newField;
    }

    /**
     * 添加待加的item
     * @param itemsMap
     * @param fdId
     * @param fdName
     * @throws Exception
     */
    public void addHandoverItem(Map<String,HandoverItem> itemsMap,String fdId, String fdName)
            throws Exception {
        HandoverItem item = new HandoverItem(fdId, fdName, null);
        itemsMap.put(fdId,item);
    }

    /**
     * 获取url
     * @param model
     * @return
     */
    private String getUrl(IBaseModel model) {
        SysDictModel dictModel = getSysDictModel(model);
        String url = dictModel.getUrl();
        if (StringUtil.isNotNull(url)) {
            url = url.replace("${fdId}", model.getFdId());
        }
        return url;
    }

    private SysDictModel getSysDictModel(IBaseModel model) {
        return SysDataDict.getInstance().getModel(
                ModelUtil.getModelClassName(model));
    }

    private IBaseDao baseDao = null;

    public IBaseDao getBaseDao() {
        if (baseDao == null) {
            baseDao = (IBaseDao) SpringBeanUtil.getBean("KmssBaseDao");
        }
        return baseDao;
    }
}
