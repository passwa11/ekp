package com.landray.kmss.fssc.voucher.service.spring;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.landray.kmss.common.dao.HQLInfo;
import org.apache.commons.beanutils.PropertyUtils;
import org.hibernate.query.NativeQuery;
import org.slf4j.Logger;

import com.landray.kmss.common.exception.KmssRuntimeException;
import com.landray.kmss.eop.basedata.constant.EopBasedataConstant;
import com.landray.kmss.eop.basedata.model.EopBasedataAccounts;
import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.fssc.common.interfaces.IFsscCommonVoucherService;
import com.landray.kmss.fssc.voucher.constant.FsscVoucherConstant;
import com.landray.kmss.fssc.voucher.model.FsscVoucherDetail;
import com.landray.kmss.fssc.voucher.model.FsscVoucherMain;
import com.landray.kmss.fssc.voucher.service.IFsscVoucherMainService;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictCommonProperty;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import javax.servlet.http.HttpServletRequest;

public class FsscCommonVoucherServiceImp implements IFsscCommonVoucherService {

    private static Logger logger = org.slf4j.LoggerFactory.getLogger(FsscCommonVoucherServiceImp.class);

    private IFsscVoucherMainService fsscVoucherMainService;

    /**
     * 添加凭证
     * @param map{
     *		            fdVoucherCreateType:凭证类型 （通用凭证：commonVoucher 费用凭证：expenseVocher 付款凭证：paymentVoucher）
     *		            docSubject:申请单标题
     *		            fdModelId:fdModelId
     *		            fdModelName:fdModelName
     *		            fdModelNumber:fdModelNumber
     *		            fdBaseVoucherType:FsscBaseVoucherType凭证类型
     *		            fdVoucherDate:Date凭证日期
     *		            fdAccountingYear:会计年度
     *    	            fdPeriod:期间
     *    	            fdBaseCurrency:FsscBaseCurrency凭证货币
     *                  fdCompany:FsscBaseCompany公司
     *                  fdNumber:Integer单据数
     *                  fdVoucherText:凭证抬头文本
     *		       		mapList: {
     *		       		    Map<String, Object>{
     *		            		fdType:借贷类型 1借 2贷
     *		            		fdBaseAccounts:FsscBaseAccounts会计科目
     *		                    fdMoney:Double 金额
     *		                    fdVoucherText:摘要文本
     *		                    fdBaseCostCenter:FsscBaseCostCenter 成本中心
     *		                    fdBaseErpPerson:FsscBaseErpPerson 个人
     *		                    fdBaseCashFlow:FsscBaseCashFlow 现金流量项目
     *		                    fdBaseProject:FsscBaseProject 项目
     *		                    fdBaseCustomer:FsscBaseSupplier 客户
     *		                    fdBaseSupplier:FsscBaseSupplier 供应商
     *		                    fdBaseWbs:FsscBaseWbs WEB号
     *		                    fdBaseInnerOrder:FsscBaseInnerOrder 内部订单
     *		                    fdBasePayBank:FsscBasePayBank 银行
     *		       		    }
     *		       		}
     * 		}
     * @throws Exception
     */
    @Override
    public Map<String, String> addOrUpdate(Map<String, Object> map) throws Exception{
        Map<String, String> rtnMap = new HashMap<String, String>();
        try {
            //设置传参必填字段
            String[] validateProperty1 = {"fdVoucherCreateType","fdModelId","fdModelName"};
            for(String property : validateProperty1){
                Object val = map.containsKey(property)?map.get(property):null;
                if(val == null){
                    KmssMessage msg = new KmssMessage("fssc-voucher:message.fsscVoucherMain.setParameterError");
                    throw new KmssRuntimeException(msg);
                }
            }
            String fdVoucherCreateType = map.get("fdVoucherCreateType")+"";
            String fdModelId = map.get("fdModelId")+"";
            String fdModelName = map.get("fdModelName")+"";
            List<FsscVoucherMain> mainList = fsscVoucherMainService.findByDeleteWhere(fdVoucherCreateType, fdModelId, fdModelName, null);
            FsscVoucherMain main = null;
            if(!ArrayUtil.isEmpty(mainList)){
                main = mainList.get(0);
            }else{
                main = new FsscVoucherMain();
            }
            //不是待记账的凭证不允许更改
            if(StringUtil.isNotNull(main.getFdBookkeepingStatus()) && !FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_10.equals(main.getFdBookkeepingStatus())){
                // 添加日志
                logger.info("FsscVoucherMain [ fdVoucherCreateType："+fdVoucherCreateType+",fdModelId："+fdModelId+",fdModelName："+fdModelName+"] 已记账，不进行更改!");
                rtnMap.put("result", "success");
                return rtnMap;
            }
            return addOrUpdate(map, main);
        } catch (Exception e) {
            e.printStackTrace();
            rtnMap.put("result", "failure");
            rtnMap.put("message", e.toString());
        }
        return rtnMap;
    }



    /**
     * 添加凭证
     * @param map{
     *		            fdVoucherCreateType:凭证类型 （通用凭证：commonVoucher 费用凭证：expenseVocher 付款凭证：paymentVoucher）
     *		            docSubject:申请单标题
     *		            fdModelId:fdModelId
     *		            fdModelName:fdModelName
     *		            fdModelNumber:fdModelNumber
     *		            fdBaseVoucherType:FsscBaseVoucherType凭证类型
     *		            fdVoucherDate:Date凭证日期
     *		            fdAccountingYear:会计年度
     *    	            fdPeriod:期间
     *    	            fdBaseCurrency:FsscBaseCurrency凭证货币
     *                  fdCompany:FsscBaseCompany公司
     *                  fdNumber:Integer单据数
     *                  fdVoucherText:凭证抬头文本
     *		       		mapList: {
     *		       		    Map<String, Object>{
     *		            		fdType:借贷类型 1借 2贷
     *		            		fdBaseAccounts:FsscBaseAccounts会计科目
     *		                    fdMoney:Double 金额
     *		                    fdVoucherText:摘要文本
     *		                    fdBaseCostCenter:FsscBaseCostCenter 成本中心
     *		                    fdBaseErpPerson:FsscBaseErpPerson 个人
     *		                    fdBaseCashFlow:FsscBaseCashFlow 现金流量项目
     *		                    fdBaseProject:FsscBaseProject 项目
     *		                    fdBaseCustomer:FsscBaseSupplier 客户
     *		                    fdBaseSupplier:FsscBaseSupplier 供应商
     *		                    fdBaseWbs:FsscBaseWbs WEB号
     *		                    fdBaseInnerOrder:FsscBaseInnerOrder 内部订单
     *		                    fdBasePayBank:FsscBasePayBank 银行
     *		       		    }
     *		       		}
     * 		}
     * @throws Exception
     */
    public Map<String, String> addOrUpdate(Map<String, Object> map, FsscVoucherMain main) throws Exception{
        Map<String, String> rtnMap = new HashMap<String, String>();
        try {
            //设置传参必填字段
            String[] validateProperty1 = {"fdVoucherCreateType","docSubject","fdModelId","fdModelName","fdModelNumber","fdAccountingYear","fdPeriod","fdBaseCurrency","fdCompany","fdVoucherText","mapList"};
            for(String property : validateProperty1){
                if("mapList".equals(property)){
                    if(ArrayUtil.isEmpty((List<Map<String, Object>>)map.get("mapList"))){
                        KmssMessage msg = new KmssMessage("fssc-voucher:message.fsscVoucherMain.setParameterError");
                        throw new KmssRuntimeException(msg);
                    }
                }else{
                    Object val = map.containsKey(property)?map.get(property):null;
                    if(val == null){
                        KmssMessage msg = new KmssMessage("fssc-voucher:message.fsscVoucherMain.setParameterError");
                        throw new KmssRuntimeException(msg);
                    }
                }
            }
            Iterator it = map.keySet().iterator();
            while(it.hasNext()){
                String key = (String) it.next();  //获取map的key
                if(!"mapList".equals(key)){
                    Object value = map.get(key);  //得到value的值
                    PropertyUtils.setProperty(main, key, value);
                }
            }

            List<Map<String, Object>> mapList = (List<Map<String, Object>>)map.get("mapList");
            List<FsscVoucherDetail> voucherDetails = new ArrayList<FsscVoucherDetail>();
            FsscVoucherDetail detail = null;
            for(int i=0;i<mapList.size();i++){
                Map<String, Object> mapDetail = mapList.get(i);
                //设置传参必填字段
                String[] validateProperty = {"fdType","fdBaseAccounts","fdMoney","fdVoucherText"};
                //,"fdBaseCostCenter","fdBaseErpPerson","fdBaseCashFlow","fdBaseProject","fdBaseCustomer","fdBaseSupplier","fdBaseWbs","fdBaseInnerOrder"
                for(String property : validateProperty){
                    Object val= mapDetail.containsKey(property)?mapDetail.get(property):null;
                    if(val==null){
                        KmssMessage msg = new KmssMessage("fssc-voucher:message.fsscVoucherMain.setParameterError");
                        throw new KmssRuntimeException(msg);
                    }
                }

                detail = new FsscVoucherDetail();
                //迭代器迭代 map集合所有的keys
                Iterator itDetail = mapDetail.keySet().iterator();
                while(itDetail.hasNext()){
                    String key = (String) itDetail.next();  //获取map的key
                    Object value = mapDetail.get(key);  //得到value的值
                    PropertyUtils.setProperty(detail, key, value);
                }
                clearNotCostItemValue(detail);//清除没有勾选的辅助核算项值
                voucherDetails.add(detail);
            }
            if(main.getFdDetail() != null){
                main.getFdDetail().clear();
                main.getFdDetail().addAll(voucherDetails);
            }else{
                main.setFdDetail(voucherDetails);
            }
            fsscVoucherMainService.update(main);


            String fdVoucherCreateType = main.getFdVoucherCreateType();
            String fdBookkeepingStatus = FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_10;//默认待记账
            if(FsscVoucherConstant.FSSC_VOUCHER_DELETE_TYPE_COMMON_VOUCHER.equals(fdVoucherCreateType)){
                fdBookkeepingStatus = FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_10;//待记账
            }else if(FsscVoucherConstant.FSSC_VOUCHER_DELETE_TYPE_EXPENSE_VOUCHER.equals(fdVoucherCreateType)){
                fdBookkeepingStatus = FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_10;//待记账
            }else if(FsscVoucherConstant.FSSC_VOUCHER_DELETE_TYPE_PAYMENT_VOUCHER.equals(fdVoucherCreateType)){
                fdBookkeepingStatus = FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_30;//已记账
            }
            //写入成功，修改单据制证状态为已制证和记账状态
            //固定制证状态字段为fdVoucherStatus，记账状态字段为fdBookkeepingStatus，并且数据字典里面也要有
            SysDictModel dict = SysDataDict.getInstance().getModel(main.getFdModelName());
            if(dict != null){
                Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
                if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdVoucherStatus") && dictMap.containsKey("fdBookkeepingStatus")){
                    SysDictCommonProperty fdVoucherStatusProperty = dictMap.get("fdVoucherStatus");
                    SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
                    StringBuffer sql = new StringBuffer();
                    sql.append(" update ").append(dict.getTable());
                    sql.append(" set ").append(fdVoucherStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_30 +"',");
                    sql.append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+ fdBookkeepingStatus +"'");
                    sql.append(" where fd_id = '").append(main.getFdModelId()).append("' ");
                    NativeQuery query=fsscVoucherMainService.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                    query.addSynchronizedQuerySpace(dict.getTable());
                    query.executeUpdate();

                }
            }

            // 添加日志
            logger.info("save FsscVoucherMain : add FsscVoucherMain success !");
            rtnMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            rtnMap.put("result", "failure");
            rtnMap.put("message", e.toString());
        }
        return rtnMap;
    }

    /**
     * 清除没有勾选的辅助核算项值
     * @param detail
     */
    public void clearNotCostItemValue(FsscVoucherDetail detail){
        EopBasedataAccounts accounts = detail.getFdBaseAccounts();
        if(accounts == null){
            return;
        }
        String properyStr = accounts.getFdCostItem();
        if(properyStr == null){
            properyStr = "";
        }
        properyStr += ";";

        //1 成本中心      2 项目      3 客户
        //4 现金流量项目      5 人员      6 WBS号
        //7 内部订单 		8 银行
        if(properyStr.indexOf("1;") == -1){//成本中心
            detail.setFdBaseCostCenter(null);
        }
        if(properyStr.indexOf("2;") == -1){//项目
            detail.setFdBaseProject(null);
        }
        if(properyStr.indexOf("3;") == -1){//客户
            detail.setFdBaseCustomer(null);
        }
        if(properyStr.indexOf("4;") == -1){//现金流量项目
            detail.setFdBaseCashFlow(null);
        }
        if(properyStr.indexOf("5;") == -1){//人员
            detail.setFdBaseErpPerson(null);
        }
        if(properyStr.indexOf("6;") == -1){//WBS号
            detail.setFdBaseWbs(null);
        }
        if(properyStr.indexOf("7;") == -1){//内部订单
            detail.setFdBaseInnerOrder(null);
        }
        if(properyStr.indexOf("8;") == -1){//银行
            detail.setFdBasePayBank(null);
        }
        if(properyStr.indexOf("9;") == -1){//供应商
            detail.setFdBaseSupplier(null);
        }
        if(properyStr.indexOf("10;") == -1){//合同编号
            detail.setFdContractCode("");
        }
        if(properyStr.indexOf("11;") == -1){//部门
            detail.setFdDept(null);
        }
    }


    /**
     * 获取URL
     * @param map{
     *		     fdVoucherCreateType:凭证类型 （通用凭证：commonVoucher 费用凭证：expenseVocher 付款凭证：paymentVoucher）
     *           fdModelId:fdModelId
     *           fdModelName:fdModelName
     *           method=方法名（例：edit）
     * }
     *
     * @return Map<String, String>
     *     		result:success 成功，failure，失败
     *     		url:路径（/fssc/cashier/...）
     *     		message：失败信息
     * @throws Exception
     */
    @Override
    public Map<String, String> getUrl(Map<String, Object> map) throws Exception{
        Map<String, String> rtnMap = new HashMap<String, String>();
        try {
            //设置传参必填字段
            String[] validateProperty1 = {"fdVoucherCreateType","fdModelId","fdModelName","method"};
            for(String property : validateProperty1){
                Object val = map.containsKey(property)?map.get(property):null;
                if(val == null){
                    KmssMessage msg = new KmssMessage("fssc-voucher:message.fsscVoucherMain.setParameterError");
                    throw new KmssRuntimeException(msg);
                }
            }
            String fdVoucherCreateType = map.get("fdVoucherCreateType")+"";
            String fdModelId = map.get("fdModelId")+"";
            String fdModelName = map.get("fdModelName")+"";
            String method = map.get("method")+"";
            List<FsscVoucherMain> mainList = fsscVoucherMainService.findByDeleteWhere(fdVoucherCreateType, fdModelId, fdModelName, null);
            if(ArrayUtil.isEmpty(mainList)){//没有找到凭证
                rtnMap.put("result", "failure");
                rtnMap.put("message", ResourceUtil.getString("fsscVoucherMain.is.null", "fssc-voucher"));
                return rtnMap;
            }
            rtnMap.put("url", SysDataDict.getInstance().getModel(FsscVoucherMain.class.getName()).getUrl().replace("method=view", "method="+method).replace("${fdId}", mainList.get(0).getFdId()));
            rtnMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            rtnMap.put("result", "failure");
            rtnMap.put("message", e.toString());
        }
        return rtnMap;
    }

    /**
     * 记账
     * @param map{
     *           fdModelId:fdModelId
     *           fdModelName:fdModelName
     * }
     *
     * @return Map<String, String>
     *     		result:success 成功，failure，失败
     *     		message：失败信息
     * @throws Exception
     */
    @Override
    public Map<String, String> updateBookkeeping(Map<String, Object> map) throws Exception{
        Map<String, String> rtnMap = new HashMap<String, String>();
        try {
            //设置传参必填字段
            String[] validateProperty1 = {"fdModelId","fdModelName"};
            for(String property : validateProperty1){
                Object val = map.containsKey(property)?map.get(property):null;
                if(val == null){
                    KmssMessage msg = new KmssMessage("fssc-voucher:message.fsscVoucherMain.setParameterError");
                    throw new KmssRuntimeException(msg);
                }
            }
            String fdModelId = map.get("fdModelId")+"";
            String fdModelName = map.get("fdModelName")+"";
            List<FsscVoucherMain> fsscVoucherMainList = fsscVoucherMainService.findNoBookkeeping(fdModelId, fdModelName, FsscVoucherConstant.FSSC_VOUCHER_FD_PUSH_TYPE_1, null);
            StringBuffer message = new StringBuffer();
            if(ArrayUtil.isEmpty(fsscVoucherMainList)){
                //找不到对应的凭证，请检查！
                throw new Exception(ResourceUtil.getString("bookkeeping.voucher.null.error", "fssc-voucher"));
            }else if(!(fsscVoucherMainList.size() == 1 || fsscVoucherMainList.size() == 2)){
                //对应的凭证的数量异常：查询到%number%个凭证，请检查！
                throw new Exception(ResourceUtil.getString("bookkeeping.voucher.number.error", "fssc-voucher").replace("%number%", fsscVoucherMainList.size()+""));
            }else{
                EopBasedataCompany eopBasedataCompany = fsscVoucherMainList.get(0).getFdCompany();
                String fdJoinSystem = eopBasedataCompany.getFdJoinSystem();//对接财务系统
                if(EopBasedataConstant.FSSC_BASE_U8.equals(fdJoinSystem)){//存在u8模块
                    for (FsscVoucherMain fsscVoucherMain : fsscVoucherMainList) {
                        try {
                            fsscVoucherMainService.updateBookkeepingU8(fsscVoucherMain);
                        } catch (Exception e) {
                            e.printStackTrace();
                            message.append(e.getMessage() + "<br/>");
                            //如果是 U8已存在相同费控编号的凭证 写入不成功！ 就不修改
                            if(!ResourceUtil.getString("bookkeeping.voucher.u8.exist.error", "fssc-voucher").equals(e.getMessage())) {
                                //保存失败原因
                                fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
                                fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
                                fsscVoucherMainService.update(fsscVoucherMain);

                                //记账失败，写入记账状态和记账失败原因
                                //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
                                SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
                                if (dict != null) {
                                    Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
                                    if (StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")) {
                                        SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
                                        SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
                                        StringBuffer sql = new StringBuffer();
                                        sql.append(" update ").append(dict.getTable());
                                        sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '" + FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 + "',");
                                        sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '" + e.getMessage() + "'");
                                        sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                        NativeQuery query=fsscVoucherMainService.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                        query.addSynchronizedQuerySpace(dict.getTable());
                                        query.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }else if(EopBasedataConstant.FSSC_BASE_EAS.equals(fdJoinSystem)){//存在eas模块
                    for(FsscVoucherMain fsscVoucherMain : fsscVoucherMainList){
                        try{
                            fsscVoucherMainService.updateBookkeepingEas(fsscVoucherMain);
                        } catch (Exception e) {
                            e.printStackTrace();
                            message.append(e.getMessage() + "<br/>");
                            //保存失败原因
                            fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
                            fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
                            fsscVoucherMainService.update(fsscVoucherMain);

                            //记账失败，写入记账状态和记账失败原因
                            //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
                            SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
                            if(dict != null){
                                Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
                                if(StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")){
                                    SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
                                    SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
                                    StringBuffer sql = new StringBuffer();
                                    sql.append(" update ").append(dict.getTable());
                                    sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '"+FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 +"',");
                                    sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '"+ e.getMessage() +"'");
                                    sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                    NativeQuery query=fsscVoucherMainService.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                    query.addSynchronizedQuerySpace(dict.getTable());
                                    query.executeUpdate();
                                }
                            }
                        }
                    }
                }else if(EopBasedataConstant.FSSC_BASE_K3.equals(fdJoinSystem)){//存在k3模块
                    for (FsscVoucherMain fsscVoucherMain : fsscVoucherMainList) {
                        try {
                            fsscVoucherMainService.updateBookkeepingK3(fsscVoucherMain);
                        } catch (Exception e) {
                            e.printStackTrace();
                            message.append(e.getMessage() + "<br/>");
                            //如果是 K3已存在相同费控编号的凭证 写入不成功！ 就不修改
                            if(!ResourceUtil.getString("bookkeeping.voucher.K3.exist.error", "fssc-voucher").equals(e.getMessage())) {
                                //保存失败原因
                                fsscVoucherMain.setFdBookkeepingMessage(e.getMessage());
                                fsscVoucherMain.setFdBookkeepingStatus(FsscVoucherConstant.FSSC_VOUCHER_FD_BOOKKEEPING_STATUS_11);
                                fsscVoucherMainService.update(fsscVoucherMain);

                                //记账失败，写入记账状态和记账失败原因
                                //固定记账状态字段为fdBookkeepingStatus，记账失败原因字段为fdBookkeepingMessage，并且数据字典里面也要有
                                SysDictModel dict = SysDataDict.getInstance().getModel(fsscVoucherMain.getFdModelName());
                                if (dict != null) {
                                    Map<String, SysDictCommonProperty> dictMap = dict.getPropertyMap();
                                    if (StringUtil.isNotNull(dict.getTable()) && dictMap != null && dictMap.containsKey("fdBookkeepingMessage") && dictMap.containsKey("fdBookkeepingStatus")) {
                                        SysDictCommonProperty fdBookkeepingStatusProperty = dictMap.get("fdBookkeepingStatus");
                                        SysDictCommonProperty fdBookkeepingMessageProperty = dictMap.get("fdBookkeepingMessage");
                                        StringBuffer sql = new StringBuffer();
                                        sql.append(" update ").append(dict.getTable());
                                        sql.append(" set ").append(fdBookkeepingStatusProperty.getColumn()).append(" = '" + FsscVoucherConstant.FSSC_VOUCHER_FD_VOUCHER_STATUS_11 + "',");
                                        sql.append(fdBookkeepingMessageProperty.getColumn()).append(" = '" + e.getMessage() + "'");
                                        sql.append(" where fd_id = '").append(fsscVoucherMain.getFdModelId()).append("' ");
                                        NativeQuery query=fsscVoucherMainService.getBaseDao().getHibernateSession().createNativeQuery(sql.toString());
                                        query.addSynchronizedQuerySpace(dict.getTable());
                                        query.executeUpdate();
                                    }
                                }
                            }
                        }
                    }
                }else{
                    rtnMap.put("result", "failure");
                    rtnMap.put("message", ResourceUtil.getString("bookkeeping.voucher.type.error", "fssc-voucher"));
                }
            }
            if(StringUtil.isNull(message.toString())){
                rtnMap.put("result", "success");
            }else{
                rtnMap.put("result", "failure");
                rtnMap.put("message", message.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
            rtnMap.put("result", "failure");
            rtnMap.put("message", e.toString());
        }
        return rtnMap;
    }

    /**
     *	获取凭证信息
     * @param map{
     *           fdModelId:
     *           fdModelName:
     * }
     *
     * @return Map<String, Object>
     *          result:success 成功，failure，失败，
     *          voucherList  List<Map<String, Object>>{
     *          		voucherDetailList List<Map<String, Object>>
     *          }
     *          message：失败信息
     * @throws Exception
     */
    @Override
    public Map<String, Object> getVoucherInfo(Map<String, String> map) throws Exception{
        Map<String, Object> rtnMap = new HashMap<String, Object>();
        try {
            //设置传参必填字段
            String[] validateProperty = {"fdModelId","fdModelName"};
            for(String property : validateProperty){
                Object val = map.containsKey(property)?map.get(property):null;
                if(val == null){
                    KmssMessage msg = new KmssMessage("fssc-cashier:message.fsscCashierPayment.setParameterError");
                    throw new KmssRuntimeException(msg);
                }
            }
            String fdModelId = map.get("fdModelId")+"";
            String fdModelName = map.get("fdModelName")+"";

            List<FsscVoucherMain> mainList = fsscVoucherMainService.getFsscVoucherMain(fdModelId, fdModelName);
            List<Map<String, Object>> voucherList = new ArrayList<>();
            Map<String, Object> voucherInfoMap = null;
            List<Map<String, Object>> voucherDetailList = null;
            Map<String, Object> tempMap = null;
            for(FsscVoucherMain main : mainList){
                voucherInfoMap = new HashMap<>();
                voucherDetailList = new ArrayList<Map<String, Object>>();

                voucherInfoMap.put("fdId", main.getFdId());
                voucherInfoMap.put("docFinanceNumber", main.getDocFinanceNumber());
                voucherInfoMap.put("docNumber", main.getDocNumber());
                voucherInfoMap.put("fdModelNumber", main.getFdModelNumber());
                if(StringUtil.isNotNull(main.getFdModelId()) && StringUtil.isNotNull(main.getFdModelName())){
                    SysDictModel dict = SysDataDict.getInstance().getModel(main.getFdModelName());
                    if(dict != null){
                        voucherInfoMap.put("fdModelUrl", dict.getUrl().replace("${fdId}", main.getFdModelId()));
                    }
                }
                voucherInfoMap.put("fdBaseVoucherTypeName", main.getFdBaseVoucherType()==null?"":main.getFdBaseVoucherType().getFdName());
                voucherInfoMap.put("fdVoucherDate", DateUtil.convertDateToString(main.getFdVoucherDate(), "yyyy-MM-dd"));
                voucherInfoMap.put("fdBookkeepingDate", DateUtil.convertDateToString(main.getFdBookkeepingDate(), "yyyy-MM-dd"));
                voucherInfoMap.put("fdAccountingYear", main.getFdAccountingYear());
                voucherInfoMap.put("fdPeriod", main.getFdPeriod());
                voucherInfoMap.put("fdBaseCurrencyName", main.getFdBaseCurrency()==null?"":main.getFdBaseCurrency().getFdName());
                voucherInfoMap.put("fdCompanyName", main.getFdCompany()==null?"":main.getFdCompany().getFdName());
                voucherInfoMap.put("fdCompanyCode", main.getFdCompany()==null?"":main.getFdCompany().getFdCode());
                voucherInfoMap.put("fdNumber", main.getFdNumber());
                voucherInfoMap.put("fdVoucherText", main.getFdVoucherText());
                voucherInfoMap.put("fdBookkeepingStatus", main.getFdBookkeepingStatus());
                voucherInfoMap.put("fdBookkeepingMessage", main.getFdBookkeepingMessage());
                voucherInfoMap.put("fdBookkeepingPersonName", main.getFdBookkeepingPerson()==null?"":main.getFdBookkeepingPerson().getFdName());
                voucherInfoMap.put("docCreatorName", main.getDocCreator()==null?"":main.getDocCreator().getFdName());
                for(FsscVoucherDetail detail : main.getFdDetail()){
                    tempMap = new HashMap<String, Object>();
                    tempMap.put("docMainId", main.getFdId());
                    tempMap.put("docMainCompanyName", main.getFdCompany()==null?"":main.getFdCompany().getFdName());
                    tempMap.put("docMainNumber", main.getDocNumber());
                    tempMap.put("docMainFinanceNumber", main.getDocFinanceNumber());
                    tempMap.put("docMainCurrencyName", main.getFdBaseCurrency()==null?"":main.getFdBaseCurrency().getFdName());

                    tempMap.put("fdType", detail.getFdType());
                    tempMap.put("fdAccounts", detail.getFdBaseAccounts());
                    tempMap.put("fdAccountsCode", detail.getFdBaseAccounts()==null?"":detail.getFdBaseAccounts().getFdCode());
                    tempMap.put("fdAccountsName", detail.getFdBaseAccounts()==null?"":detail.getFdBaseAccounts().getFdName());
                    tempMap.put("fdCostCenterName", detail.getFdBaseCostCenter()==null?"":detail.getFdBaseCostCenter().getFdName());
                    tempMap.put("fdBaseErpPersonName", detail.getFdBaseErpPerson()==null?"":detail.getFdBaseErpPerson().getFdName());
                    tempMap.put("fdBaseCashFlowName", detail.getFdBaseCashFlow()==null?"":detail.getFdBaseCashFlow().getFdName());
                    tempMap.put("fdBaseCustomerName", detail.getFdBaseCustomer()==null?"":detail.getFdBaseCustomer().getFdName());
                    tempMap.put("fdBaseSupplierName", detail.getFdBaseSupplier()==null?"":detail.getFdBaseSupplier().getFdName());
                    tempMap.put("fdBaseWbsName", detail.getFdBaseWbs()==null?"":detail.getFdBaseWbs().getFdName());
                    tempMap.put("fdBaseInnerOrderName", detail.getFdBaseInnerOrder()==null?"":detail.getFdBaseInnerOrder().getFdName());
                    tempMap.put("fdBaseProjectName", detail.getFdBaseProject()==null?"":detail.getFdBaseProject().getFdName());
                    tempMap.put("fdBasePayBank", detail.getFdBasePayBank());
                    tempMap.put("fdBasePayBankName", detail.getFdBasePayBank()==null?"":(detail.getFdBasePayBank().getFdAccountName()+"("+detail.getFdBasePayBank().getFdBankAccount()+")"));
                    tempMap.put("fdMoney", detail.getFdMoney()+"");
                    tempMap.put("fdVoucherText", detail.getFdVoucherText());
                    voucherDetailList.add(tempMap);
                }
                voucherInfoMap.put("voucherDetailList", voucherDetailList);
                voucherList.add(voucherInfoMap);
            }
            rtnMap.put("voucherList", voucherList);
            rtnMap.put("result", "success");
        } catch (Exception e) {
            e.printStackTrace();
            rtnMap.put("result", "failure");
            rtnMap.put("message", e.toString());
        }

        return rtnMap;
    }


    public void setFsscVoucherMainService(IFsscVoucherMainService fsscVoucherMainService) {
        this.fsscVoucherMainService = fsscVoucherMainService;
    }


    /**
     * 归档的打印页面增加归档需要的数据
     * @param request
     * @param fdId
     */	@Override
    public void addFileDocList(HttpServletRequest request, String fdId){
        try{
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock(" fsscVoucherMain.fdModelId=:fdId ");
            hqlInfo.setParameter("fdId",fdId);
            hqlInfo.setOrderBy(" docCreateTime desc");
            List<FsscVoucherMain>  fsscVoucherMainList=fsscVoucherMainService.findList(hqlInfo);
            request.setAttribute("fsscVoucherMainList",fsscVoucherMainList);

        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean checkHasVoucher(String fdId){
        try{
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock(" fsscVoucherMain.fdModelId=:fdId ");
            hqlInfo.setParameter("fdId",fdId);
            hqlInfo.setOrderBy(" docCreateTime desc");
            List<FsscVoucherMain>  fsscVoucherMainList=fsscVoucherMainService.findList(hqlInfo);
            return fsscVoucherMainList!=null&&fsscVoucherMainList.size()>0;

        }catch(Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
