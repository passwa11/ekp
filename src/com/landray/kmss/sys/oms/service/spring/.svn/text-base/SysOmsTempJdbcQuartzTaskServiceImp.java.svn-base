package com.landray.kmss.sys.oms.service.spring;

import java.lang.reflect.Method;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.component.dbop.ds.DSAction;
import com.landray.kmss.component.dbop.ds.DSTemplate;
import com.landray.kmss.component.dbop.ds.DataSet;
import com.landray.kmss.component.dbop.model.CompDbcp;
import com.landray.kmss.component.dbop.service.ICompDbcpService;
import com.landray.kmss.sys.appconfig.service.ISysAppConfigService;
import com.landray.kmss.sys.log.service.ISysLogJobService;
import com.landray.kmss.sys.oms.in.interfaces.OMSBaseSynchroInOtherProvider;
import com.landray.kmss.sys.oms.model.SysOmsTempDept;
import com.landray.kmss.sys.oms.model.SysOmsTempDp;
import com.landray.kmss.sys.oms.model.SysOmsTempPerson;
import com.landray.kmss.sys.oms.model.SysOmsTempPost;
import com.landray.kmss.sys.oms.model.SysOmsTempPp;
import com.landray.kmss.sys.oms.service.ISysOmsTempJdbcQuartzTaskService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxService;
import com.landray.kmss.sys.oms.service.ISysOmsTempTrxSynService;
import com.landray.kmss.sys.oms.temp.OmsTempSynModel;
import com.landray.kmss.sys.oms.temp.OmsTempSynResult;
import com.landray.kmss.sys.oms.temp.SysOmsSynConfig;
import com.landray.kmss.sys.oms.temp.SysOmsTempConstants;
import com.landray.kmss.sys.quartz.interfaces.SysQuartzJobContext;
import com.landray.kmss.sys.quartz.scheduler.ISysQuartzJobExecutor;
import com.landray.kmss.sys.quartz.service.ISysQuartzJobService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import oracle.sql.TIMESTAMP;

/**
 * 类名
 * 
 * @author yuLiang
 * @version 1.0 创建时间：2019年12月20日
 */
public class SysOmsTempJdbcQuartzTaskServiceImp extends OMSBaseSynchroInOtherProvider implements ISysOmsTempJdbcQuartzTaskService{
	private Logger logger = org.slf4j.LoggerFactory.getLogger(SysOmsTempJdbcQuartzTaskServiceImp.class);
	private ISysAppConfigService sysAppConfigService;
	private ISysOmsTempTrxSynService sysOmsTempTrxSynService;
	private SysQuartzJobContext contextStr;
	private String fdKey = "com.landray.kmss.sys.oms.temp.SysOmsJdbcConfig";

	@Override
	public void handlerSynch(SysQuartzJobContext context) throws Exception {
		execute(context);
	}

	@Override
	public void execute(SysQuartzJobContext context) throws Exception {
		this.contextStr = context;
		long temptime = System.currentTimeMillis();
		Map<String, String> params = sysAppConfigService.findByKey(fdKey);
		int synModel = Integer.parseInt(params.get("kmss.oms.temp.syn.model"));
		// 开始同步
		OmsTempSynResult<Object> synResult = begin(params);
		if (synResult.getCode() == SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL) {
			return;
		}
		
		//时间戳
		String synTimestampStr = params.get("kmss.oms.temp.syn.synTimestamp");
		contextStr.logMessage("同步开始时间戳：" + synTimestampStr+"，事务号："+synResult.getTrxId());
		Long synTimestamp = StringUtil.isNull(synTimestampStr)?null:Long.parseLong(synTimestampStr);
		// 同步部门
		addTempDept(synTimestamp, synResult.getTrxId(), params);

		// 同步人员
		addTempPerson(synTimestamp, synResult.getTrxId(), params);

		if (synModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_300.getValue()
				|| synModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()) {
			// 同步岗位
			addTempPost(synTimestamp, synResult.getTrxId(), params);

			// 同步岗位人员关系
			addTempPp(synTimestamp, synResult.getTrxId(), params);
		}

		if (synModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200.getValue()
				|| synModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_400.getValue()) {
			// 同步部门人员关系
			addTempDp(synTimestamp, synResult.getTrxId(), params);
		}
		contextStr.logMessage("写入临时表耗时：" + (System.currentTimeMillis() - temptime) / 1000 + "秒");

		// 结束事务
		temptime = System.currentTimeMillis();
		OmsTempSynResult<Object> result = sysOmsTempTrxSynService.end(synResult.getTrxId());
		contextStr.logMessage("写入组织架构耗时：" + (System.currentTimeMillis() - temptime) / 1000 + "秒");
		contextStr.logMessage(result.getMsg());
		contextStr.logMessage(result.getIllegalDataMsg("\r\n"));
		//保存同步时间戳
		if(result.getCode() != SysOmsTempConstants.SYS_OMS_TEMP_SYN_CODE_FAIL){
			synTimestamp = System.currentTimeMillis();
			params.put("kmss.oms.temp.syn.synTimestamp", synTimestamp+"");
			sysAppConfigService.add(fdKey, params);
			logger.info("同步成功，更新同步时间戳"+synTimestamp);
			contextStr.logMessage("同步结束时间戳"+synTimestamp);
		}
	}

	private OmsTempSynResult<Object> begin(Map<String, String> params) {
		// 模式
		int synModelInt = Integer.parseInt(params.get("kmss.oms.temp.syn.model"));
		boolean fdDeptIsAsc = "1".equals(params.get("kmss.oms.temp.dept_deptisasc"));
		boolean fdPersonIsAsc = "1".equals(params.get("kmss.oms.temp.person_personisasc"));
		boolean fdPersonIsMainDept = "1".equals(params.get("kmss.oms.temp.syn.person_is_main_dept"));
		String dpAlterTime = params.get("kmss.oms.temp.dp_alter_time");
		String ppAlterTime = params.get("kmss.oms.temp.pp_alter_time");
		OmsTempSynResult<Object> synResult = null;
		OmsTempSynModel synModel = OmsTempSynModel.getEnumByValue(synModelInt);
		SysOmsSynConfig synConfig = new SysOmsSynConfig();
		synConfig.setFdDeptIsAsc(fdDeptIsAsc);
		synConfig.setFdPersonIsAsc(fdPersonIsAsc);
		synConfig.setFdPersonPostIsFull(StringUtil.isNull(ppAlterTime));
		synConfig.setFdPersonDeptIsFull(StringUtil.isNull(dpAlterTime));
		if(synModel == OmsTempSynModel.OMS_TEMP_SYN_MODEL_200) {
			synConfig.setFdPersonIsMainDept(fdPersonIsMainDept);
		}
		String synTimestampStr = params.get("kmss.oms.temp.syn.synTimestamp");
		synConfig.setFdFullSynFlag(StringUtil.isNull(synTimestampStr)?1:0);
		synResult = sysOmsTempTrxSynService.begin(synModel,synConfig);

		return synResult;
	}

	private void addTempDept(Long synTimestamp, String fdTrxId, Map<String, String> params) throws Exception {
		List<SysOmsTempDept> tempDeptList = getDeptList(synTimestamp, params);
		if (tempDeptList != null) {
			contextStr.logMessage("接入部门总数："+tempDeptList.size());
			OmsTempSynResult<SysOmsTempDept> result = sysOmsTempTrxSynService.addTempDept(fdTrxId, tempDeptList);
			
			contextStr.logMessage(result.getMsg());
			contextStr.logMessage(result.getIllegalDataMsg("\r\n"));
		} else {
			contextStr.logMessage("接入部门总数：0");
		}
	}

	private List<SysOmsTempDept> getDeptList(final Long synTimestamp, Map<String, String> params) throws Exception {
		String fdSourceName = params.get("kmss.oms.temp.datasource.name");
		// 部门字段名
		String deptTableName = params.get("kmss.oms.temp.dept_table_name");
		final String deptName = params.get("kmss.oms.temp.dept_name");
		final String deptId = params.get("kmss.oms.temp.dept_id");
		final String deptParentId = params.get("kmss.oms.temp.dept_parent_id");
		final String deptAltertime = params.get("kmss.oms.temp.dept_alter_time");
		final String deptStatus = params.get("kmss.oms.temp.dept_status");
		final String deptOrder = params.get("kmss.oms.temp.dept_order");
		final String deptValidStatus = params.get("kmss.oms.temp.dept_valid_status");
		if (StringUtil.isNull(deptTableName)) {
            return null;
        }
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(deptId + ",");
		sql.append(deptParentId + ",");
		sql.append(deptAltertime + ",");
		if (StringUtil.isNotNull(deptOrder)) {
			sql.append(deptOrder + ",");
		}
		if (StringUtil.isNotNull(deptStatus)) {
			sql.append(deptStatus + ",");
		}
		sql.append(deptName + " from " + deptTableName);
		sql.append(" where 1=1");
		if (synTimestamp != null && synTimestamp != 0) {
			Boolean timeType = getTableColumName(fdSourceName, deptTableName, deptAltertime);
			if(timeType){
				sql.append(" and " + deptAltertime + " > ?");
			}else{
				sql.append(" and " + deptAltertime + " > " + synTimestamp);
			}
		} else if (StringUtil.isNotNull(deptStatus) && StringUtil.isNotNull(deptValidStatus)) {
			sql.append(" and " + deptStatus + "=" + deptValidStatus);
		}
		logger.warn("查询部门sql："+sql);
		contextStr.logMessage("查询部门sql："+sql);
		
		@SuppressWarnings("unchecked")
		List<SysOmsTempDept> result = (List<SysOmsTempDept>) DSTemplate.execute(fdSourceName,
				new DSAction<String>(sql.toString()) {
					@Override
					public List<SysOmsTempDept> doAction(DataSet ds, String sql) throws Exception {
						List<SysOmsTempDept> deptList = new ArrayList<SysOmsTempDept>();
						ResultSet rs = null;
						if (synTimestamp != null && sql.indexOf("?")>-1) {
							ds.prepareStatement(sql);
							ds.setTimestamp(1, new Timestamp(synTimestamp));
							rs = ds.executeQuery();
						}else{
							rs = ds.executeQuery(sql);
						}
						SysOmsTempDept sysOmsTempDept = null;
						while (rs.next()) {
							sysOmsTempDept = new SysOmsTempDept();
							sysOmsTempDept.setFdName(rs.getString(deptName));
							sysOmsTempDept.setFdDeptId(rs.getString(deptId));
							sysOmsTempDept.setFdParentid(rs.getString(deptParentId));
							// 有效状态
							if (StringUtil.isNotNull(deptStatus) && StringUtil.isNotNull(deptValidStatus)) {
								sysOmsTempDept
										.setFdIsAvailable(rs.getInt(deptStatus) == Integer.parseInt(deptValidStatus));
							} else {
								sysOmsTempDept.setFdIsAvailable(true);
							}

							// 部门排序号
							if (StringUtil.isNotNull(deptOrder)) {
								Object orderObj = rs.getObject(deptOrder);
								if (orderObj != null) {
									if (orderObj instanceof Number) {
										sysOmsTempDept.setFdOrder(((Number) orderObj).intValue());
									} else if (orderObj instanceof String) {
										sysOmsTempDept.setFdOrder(Integer.parseInt((String) orderObj));
									}
								}
							}
							// 修改日期
							if(StringUtil.isNotNull(deptAltertime)){
								sysOmsTempDept.setFdAlterTime(getTimestampByObj( rs.getObject(deptAltertime)));
							}
			
							deptList.add(sysOmsTempDept);
						}
						return deptList;
					}
				});

		return result;
	}

	private OmsTempSynResult<SysOmsTempPerson> addTempPerson(Long synTimestamp, String fdTrxId,
			Map<String, String> params) throws Exception {
		List<SysOmsTempPerson> tempPersonList = getPersonList(synTimestamp, params);
		OmsTempSynResult<SysOmsTempPerson> result = null;
		if (tempPersonList != null) {
			contextStr.logMessage("接入人员总数："+tempPersonList.size());
			result = sysOmsTempTrxSynService.addTempPerson(fdTrxId, tempPersonList);
			contextStr.logMessage(result.getMsg());
			contextStr.logMessage(result.getIllegalDataMsg("\r\n"));
		} else {
			contextStr.logMessage("接入人员总数：0");
		}
		return result;

	}

	private List<SysOmsTempPerson> getPersonList(final Long synTimestamp, Map<String, String> params) throws Exception {
		String fdSourceName = params.get("kmss.oms.temp.datasource.name");
		// 部门字段名
		final String personTableName = params.get("kmss.oms.temp.person_table_name");
		final String personName = params.get("kmss.oms.temp.person_name");
		final String personId = params.get("kmss.oms.temp.person_id");
		final String personDeptId = params.get("kmss.oms.temp.person_dept_id");
		final String personAltertime = params.get("kmss.oms.temp.person_alter_time");
		final String personStatus = params.get("kmss.oms.temp.person_status");
		final String personMobile = params.get("kmss.oms.temp.person_mobile");
		final String personValidStatus = params.get("kmss.oms.temp.person_valid_status");
		final String personOrder = params.get("kmss.oms.temp.person_order");
		final String personLoginName = params.get("kmss.oms.temp.person_login_name");
		final String personEmail = params.get("kmss.oms.temp.person_email");
		final String personNo = params.get("kmss.oms.temp.person_no");
		final String personWorkPhone = params.get("kmss.oms.temp.person_work_phone");
		final String personDesc = params.get("kmss.oms.temp.person_desc");
		if (StringUtil.isNull(personTableName)) {
            return null;
        }
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(personId + ",");
		if (StringUtil.isNotNull(personDeptId)) {
			sql.append(personDeptId + ",");
		}
		sql.append(personAltertime + ",");
		if (StringUtil.isNotNull(personMobile)) {
			sql.append(personMobile + ",");
		}
		if (StringUtil.isNotNull(personLoginName)) {
			sql.append(personLoginName + ",");
		}
		if (StringUtil.isNotNull(personEmail)) {
			sql.append(personEmail + ",");
		}
		if (StringUtil.isNotNull(personOrder)) {
			sql.append(personOrder + ",");
		}
		if (StringUtil.isNotNull(personStatus)) {
			sql.append(personStatus + ",");
		}
		if (StringUtil.isNotNull(personNo)) {
			sql.append(personNo + ",");
		}
		if (StringUtil.isNotNull(personWorkPhone)) {
			sql.append(personWorkPhone + ",");
		}
		if (StringUtil.isNotNull(personDesc)) {
			sql.append(personDesc + ",");
		}

		// 扩展字段
		Map<String,Object> dbParams = new HashMap<String, Object>();
		Map<String,String> fieldMap = new HashMap<String,String>();
		Map<String, String> expendParams = sysAppConfigService.findByKey("com.landray.kmss.sys.oms.SysOmsJdbcConfig");
		String expendParam = expendParams.get("kmss.oms.temp.person_extendFields");
		if(StringUtil.isNotNull(expendParam)){
			JSONArray ja = JSONArray.fromObject(expendParam);
			for(int i=0;i<ja.size();i++){
				sql.append(ja.getJSONObject(i).getString("expandsele") + ",");
				fieldMap.put(ja.getJSONObject(i).getString("expandsele")+";"+i,ja.getJSONObject(i).getString("expandekp"));
			}
		}	

		sql.append(personName + " from " + personTableName);
		sql.append(" where 1=1");
		if (synTimestamp != null && synTimestamp != 0) {
			Boolean timeType = getTableColumName(fdSourceName, personTableName, personAltertime);
			if(timeType){
				sql.append(" and " + personAltertime + " > ?");
			}else{
				sql.append(" and " + personAltertime + " > " + synTimestamp);
			}
		} else if (StringUtil.isNotNull(personStatus) && StringUtil.isNotNull(personValidStatus)) {
			sql.append(" and " + personStatus + "=" + personValidStatus);
		}
		
		dbParams.put("sql", sql.toString());
		dbParams.put("dbmap", fieldMap);
		logger.warn("查询人员sql："+sql);
		contextStr.logMessage("查询人员sql："+sql);
		@SuppressWarnings("unchecked")
		List<SysOmsTempPerson> result = (List<SysOmsTempPerson>) DSTemplate.execute(fdSourceName,
				new DSAction<Map<String,Object>>(dbParams) {
				@Override
				public List<SysOmsTempPerson> doAction(DataSet ds, Map<String,Object> dbParams) throws Exception {
						List<SysOmsTempPerson> personList = new ArrayList<SysOmsTempPerson>();
						String sql = (String) dbParams.get("sql");
						ResultSet rs = null;
						if (synTimestamp != null && sql.indexOf("?")>-1) {
							ds.prepareStatement(sql);
							ds.setTimestamp(1, new Timestamp(synTimestamp));
							rs = ds.executeQuery();
						}else{
							rs = ds.executeQuery(sql);
						}
						SysOmsTempPerson sysOmsTempPerson = null;
						while (rs.next()) {
							sysOmsTempPerson = new SysOmsTempPerson();
							sysOmsTempPerson.setFdName(rs.getString(personName));
							sysOmsTempPerson.setFdPersonId(rs.getString(personId));
							if (StringUtil.isNotNull(personLoginName)) {
								sysOmsTempPerson.setFdLoginName(rs.getString(personLoginName));
							}
							if (StringUtil.isNotNull(personEmail)) {
								sysOmsTempPerson.setFdEmail(rs.getString(personEmail));
							}
							// 有效状态
							if (StringUtil.isNotNull(personStatus) && StringUtil.isNotNull(personValidStatus)) {
								sysOmsTempPerson.setFdIsAvailable(
										rs.getInt(personStatus) == Integer.parseInt(personValidStatus));
							} else {
								sysOmsTempPerson.setFdIsAvailable(true);
							}
							if (StringUtil.isNotNull(personDeptId)) {
								sysOmsTempPerson.setFdParentid(rs.getString(personDeptId));
							}
							if (StringUtil.isNotNull(personMobile)) {
								sysOmsTempPerson.setFdMobileNo(rs.getString(personMobile));
							}
							
							if (StringUtil.isNotNull(personNo)) {
								sysOmsTempPerson.setFdNo(rs.getString(personNo));
							}
							if (StringUtil.isNotNull(personWorkPhone)) {
								sysOmsTempPerson.setFdWorkPhone(rs.getString(personWorkPhone));
							}
							if (StringUtil.isNotNull(personDesc)) {
								sysOmsTempPerson.setFdDesc(rs.getString(personDesc));
							}
							
							
							// 人员排序号
							if (StringUtil.isNotNull(personOrder)) {
								Object orderObj = rs.getObject(personOrder);
								if (orderObj != null) {
									if (orderObj instanceof Number) {
										sysOmsTempPerson.setFdOrder(((Number) orderObj).intValue());
									} else if (orderObj instanceof String) {
										sysOmsTempPerson.setFdOrder(Integer.parseInt((String) orderObj));
									}
								}
							}
							// 修改日期
							if(StringUtil.isNotNull(personAltertime)){
								sysOmsTempPerson.setFdAlterTime(getTimestampByObj( rs.getObject(personAltertime)));
							}
							
							//扩展字段
							Map<String, String> dbmap = (Map<String, String>) dbParams.get("dbmap");
							JSONObject json = new JSONObject();
							Object object = null;
							List<String> keys = new ArrayList(dbmap.keySet());
							for (String key : keys) {
								object = rs.getObject(key.split("[,;]")[0]);
								if (object != null) {
									if(object instanceof Date){
										json.put(dbmap.get(key), DateUtil.convertDateToString((Date) object, DateUtil.PATTERN_DATE));
									}else if (object instanceof Timestamp) {
										json.put(dbmap.get(key), new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(object));
									}else if (object instanceof TIMESTAMP) {
										Timestamp  timestamp = null;
										timestamp = getOracleTimestamp(object); 
										json.put(dbmap.get(key), (new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")).format(timestamp));
									}else{
										json.put(dbmap.get(key), object);
									}
								}
							}
							sysOmsTempPerson.setFdExtra(json.toString());
							
							personList.add(sysOmsTempPerson);
						}
						return personList;
					}
				});

		return result;
	}

	private void addTempPost(Long synTimestamp, String fdTrxId, Map<String, String> params) throws Exception {
		List<SysOmsTempPost> tempPersonList = getPostList(synTimestamp, params);
		if (tempPersonList != null) {
			contextStr.logMessage("接入岗位总数："+tempPersonList.size());
			OmsTempSynResult<SysOmsTempPost> result = sysOmsTempTrxSynService.addTempPost(fdTrxId, tempPersonList);
			contextStr.logMessage(result.getMsg());
			contextStr.logMessage(result.getIllegalDataMsg("\r\n"));
		} else {
			contextStr.logMessage("接入岗位总数：0");
		}
	}

	private List<SysOmsTempPost> getPostList(final Long synTimestamp, Map<String, String> params) throws Exception {
		String fdSourceName = params.get("kmss.oms.temp.datasource.name");
		// 岗位字段名
		final String postTableName = params.get("kmss.oms.temp.post_table_name");
		final String postName = params.get("kmss.oms.temp.post_name");
		final String postId = params.get("kmss.oms.temp.post_id");
		final String postDeptId = params.get("kmss.oms.temp.post_dept_id");
		final String postAltertime = params.get("kmss.oms.temp.post_alter_time");
		final String postOrder = params.get("kmss.oms.temp.post_order");
		final String postStatus = params.get("kmss.oms.temp.post_status");
		final String postValidStatus = params.get("kmss.oms.temp.post_valid_status");
		if (StringUtil.isNull(postTableName)) {
            return null;
        }

		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(postId + ",");
		if (StringUtil.isNotNull(postAltertime)) {
			sql.append(postAltertime + ",");
		}
		if (StringUtil.isNotNull(postStatus)) {
			sql.append(postStatus + ",");
		}
		if (StringUtil.isNotNull(postDeptId)) {
			sql.append(postDeptId + ",");
		}
		if (StringUtil.isNotNull(postOrder)) {
			sql.append(postOrder + ",");
		}
		sql.append(postName + " from " + postTableName);
		sql.append(" where 1=1");
		if (synTimestamp != null && synTimestamp != 0) {
			Boolean timeType = getTableColumName(fdSourceName, postTableName, postAltertime);
			if(timeType){
				sql.append(" and " + postAltertime + " > ?");
			}else{
				sql.append(" and " + postAltertime + " > " + synTimestamp);
			}
		} else if (StringUtil.isNotNull(postStatus) && StringUtil.isNotNull(postValidStatus)) {
			sql.append(" and " + postStatus + "=" + postValidStatus);
		}
		logger.warn("查询岗位sql："+sql);
		contextStr.logMessage("查询岗位sql："+sql);
		@SuppressWarnings("unchecked")
		List<SysOmsTempPost> result = (List<SysOmsTempPost>) DSTemplate.execute(fdSourceName,
				new DSAction<String>(sql.toString()) {
					@Override
					public List<SysOmsTempPost> doAction(DataSet ds, String sql) throws Exception {
						List<SysOmsTempPost> sysOmsTempPostList = new ArrayList<SysOmsTempPost>();
						ResultSet rs = null;
						if (synTimestamp != null && sql.indexOf("?")>-1) {
							ds.prepareStatement(sql);
							ds.setTimestamp(1, new Timestamp(synTimestamp));
							rs = ds.executeQuery();
						}else{
							rs = ds.executeQuery(sql);
						}
						SysOmsTempPost sysOmsTempPost = null;
						while (rs.next()) {
							sysOmsTempPost = new SysOmsTempPost();
							if(StringUtil.isNotNull(postDeptId)){
								sysOmsTempPost.setFdParentid(rs.getString(postDeptId));
							}
							sysOmsTempPost.setFdPostId(rs.getString(postId));
							sysOmsTempPost.setFdName(rs.getString(postName));
							// 有效状态
							if (StringUtil.isNotNull(postStatus) && StringUtil.isNotNull(postValidStatus)) {
								sysOmsTempPost.setFdIsAvailable(
										rs.getInt(postStatus) == Integer.parseInt(postValidStatus));
							} else {
								sysOmsTempPost.setFdIsAvailable(true);
							}
							
							// 修改日期
							if(StringUtil.isNotNull(postAltertime)){
								sysOmsTempPost.setFdAlterTime(getTimestampByObj( rs.getObject(postAltertime)));
							}
							
							// 部门排序号
							if (StringUtil.isNotNull(postOrder)) {
								Object orderObj = rs.getObject(postOrder);
								if (orderObj != null) {
									if (orderObj instanceof Number) {
										sysOmsTempPost.setFdOrder(((Number) orderObj).intValue());
									} else if (orderObj instanceof String) {
										sysOmsTempPost.setFdOrder(Integer.parseInt((String) orderObj));
									}
								}
							}
							sysOmsTempPostList.add(sysOmsTempPost);
						}
						return sysOmsTempPostList;
					}
				});

		return result;
	}

	private void addTempDp(Long synTimestamp, String fdTrxId, Map<String, String> params) throws Exception {
		List<SysOmsTempDp> tempDpList = getDpList(synTimestamp, params);
		if (tempDpList != null) {
			contextStr.logMessage("接入岗位人员关系总数："+tempDpList.size());
			OmsTempSynResult<SysOmsTempDp> result = sysOmsTempTrxSynService.addTempDeptPerson(fdTrxId, tempDpList);
			contextStr.logMessage(result.getMsg());
			contextStr.logMessage(result.getIllegalDataMsg("\r\n"));
		} else {
			contextStr.logMessage("接入岗位人员关系总数：0");
		}
	}

	private List<SysOmsTempDp> getDpList(final Long synTimestamp, Map<String, String> params) throws Exception {
		String fdSourceName = params.get("kmss.oms.temp.datasource.name");
		// 部门人员关系字段名
		final String dpTableName = params.get("kmss.oms.temp.dp_table_name");
		final String dpDeptId = params.get("kmss.oms.temp.dp_dept_id");
		final String dpPersonId = params.get("kmss.oms.temp.dp_person_id");
		final String dpOrder = params.get("kmss.oms.temp.dp_order");
		final String dpAlterTime = params.get("kmss.oms.temp.dp_alter_time");
		final String dpStatus = params.get("kmss.oms.temp.dp_status");
		final String dpValidStatus = params.get("kmss.oms.temp.dp_valid_status");
		if (StringUtil.isNull(dpTableName)) {
            return null;
        }
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(dpPersonId + ",");
		if (StringUtil.isNotNull(dpOrder)) {
			sql.append(dpOrder + ",");
		}
		if (StringUtil.isNotNull(dpAlterTime)) {
			sql.append(dpAlterTime + ",");
		}
		if (StringUtil.isNotNull(dpStatus)) {
			sql.append(dpStatus + ",");
		}
		sql.append(dpDeptId + " from " + dpTableName);
		sql.append(" where 1=1");
		if(synTimestamp != null && synTimestamp != 0) {
			//增量同步，关系有时间戳，说明关系也是增量的，按照时间戳查找
			if(StringUtil.isNotNull(dpAlterTime)) {
				Boolean timeType = getTableColumName(fdSourceName, dpTableName, dpAlterTime);
				if(timeType){
					sql.append(" and " + dpAlterTime + " > ?");
				}else{
					sql.append(" and " + dpAlterTime + " > " + synTimestamp);
				}
			}else if (StringUtil.isNotNull(dpStatus) && StringUtil.isNotNull(dpValidStatus)) {
				//增量同步，关系没有时间戳，如果有状态，则查找所有有效的关系
				sql.append(" and " + dpStatus + "=" + dpValidStatus);
			}
		} else if (StringUtil.isNotNull(dpStatus) && StringUtil.isNotNull(dpValidStatus)) {
			//全量同步，查找所有有效部门人员关系
			sql.append(" and " + dpStatus + "=" + dpValidStatus);
		}
		logger.warn("查询部门人员关系sql："+sql);
		contextStr.logMessage("查询部门人员关系sql："+sql);
		@SuppressWarnings("unchecked")
		List<SysOmsTempDp> result = (List<SysOmsTempDp>) DSTemplate.execute(fdSourceName,
				new DSAction<String>(sql.toString()) {
					@Override
					public List<SysOmsTempDp> doAction(DataSet ds, String sql) throws Exception {
						List<SysOmsTempDp> sysOmsTempDpList = new ArrayList<SysOmsTempDp>();
						ResultSet rs = null;
						if (synTimestamp != null && sql.indexOf("?")>-1) {
							ds.prepareStatement(sql);
							ds.setTimestamp(1, new Timestamp(synTimestamp));
							rs = ds.executeQuery();
						}else{
							rs = ds.executeQuery(sql);
						}
						SysOmsTempDp sysOmsTempDp = null;
						while (rs.next()) {
							sysOmsTempDp = new SysOmsTempDp();
							sysOmsTempDp.setFdDeptId(rs.getString(dpDeptId));
							sysOmsTempDp.setFdPersonId(rs.getString(dpPersonId));
							// 有效状态
							if (StringUtil.isNotNull(dpStatus) && StringUtil.isNotNull(dpValidStatus)) {
								sysOmsTempDp.setFdIsAvailable(rs.getInt(dpStatus) == Integer.parseInt(dpValidStatus));
							} else {
								sysOmsTempDp.setFdIsAvailable(true);
							}
							// 修改日期
							if(StringUtil.isNotNull(dpAlterTime)){
								sysOmsTempDp.setFdAlterTime(getTimestampByObj( rs.getObject(dpAlterTime)));
							}
							
							// 部门排序号
							if (StringUtil.isNotNull(dpOrder)) {
								Object orderObj = rs.getObject(dpOrder);
								if (orderObj != null) {
									if (orderObj instanceof Number) {
										sysOmsTempDp.setFdOrder(((Number) orderObj).intValue());
									} else if (orderObj instanceof String) {
										sysOmsTempDp.setFdOrder(Integer.parseInt((String) orderObj));
									}
								}
							}
							sysOmsTempDpList.add(sysOmsTempDp);
						}
						return sysOmsTempDpList;
					}
				});

		return result;
	}

	private void addTempPp(Long synTimestamp, String fdTrxId, Map<String, String> params) throws Exception {
		List<SysOmsTempPp> tempPpList = getPpList(synTimestamp, params);
		if (tempPpList != null) {
			contextStr.logMessage("接入岗位人员关系总数："+tempPpList.size());
			OmsTempSynResult<SysOmsTempPp> result = sysOmsTempTrxSynService.addTempPostPerson(fdTrxId, tempPpList);
			contextStr.logMessage(result.getMsg());
			contextStr.logMessage(result.getIllegalDataMsg("\r\n"));
		} else {
			contextStr.logMessage("接入岗位人员关系总数：0");
		}
	}

	private List<SysOmsTempPp> getPpList(final Long synTimestamp, Map<String, String> params) throws Exception {
		String fdSourceName = params.get("kmss.oms.temp.datasource.name");
		// 岗位人员关系字段名
		final String ppTableName = params.get("kmss.oms.temp.pp_table_name");
		final String ppPostId = params.get("kmss.oms.temp.pp_post_id");
		final String ppPersonId = params.get("kmss.oms.temp.pp_person_id");
		final String ppAlterTime = params.get("kmss.oms.temp.pp_alter_time");
		final String ppStatus = params.get("kmss.oms.temp.pp_status");
		final String ppValidStatus = params.get("kmss.oms.temp.pp_valid_status");
		if (StringUtil.isNull(ppTableName)) {
            return null;
        }
		StringBuffer sql = new StringBuffer();
		sql.append("select ");
		sql.append(ppPersonId + ",");
		if (StringUtil.isNotNull(ppAlterTime)) {
			sql.append(ppAlterTime + ",");
		}
		if (StringUtil.isNotNull(ppStatus)) {
			sql.append(ppStatus + ",");
		}
		sql.append(ppPostId + " from " + ppTableName);
		sql.append(" where 1=1");
		if (synTimestamp != null && synTimestamp != 0) {
			if(StringUtil.isNotNull(ppAlterTime)) {
				Boolean timeType = getTableColumName(fdSourceName, ppTableName, ppAlterTime);
				if(timeType){
					sql.append(" and " + ppAlterTime + " > ?");
				}else{
					sql.append(" and " + ppAlterTime + " > " + synTimestamp);
				}
			}else if (StringUtil.isNotNull(ppStatus) && StringUtil.isNotNull(ppValidStatus)) {
				sql.append(" and " + ppStatus + "=" + ppValidStatus);
			}
			
		} else if (StringUtil.isNotNull(ppStatus) && StringUtil.isNotNull(ppValidStatus)) {
			sql.append(" and " + ppStatus + "=" + ppValidStatus);
		}
		logger.warn("查询岗位人员关系sql："+sql);
		contextStr.logMessage("查询岗位人员关系sql："+sql);
		@SuppressWarnings("unchecked")
		List<SysOmsTempPp> result = (List<SysOmsTempPp>) DSTemplate.execute(fdSourceName,
				new DSAction<String>(sql.toString()) {
					@Override
					public List<SysOmsTempPp> doAction(DataSet ds, String sql) throws Exception {
						List<SysOmsTempPp> sysOmsTempDpList = new ArrayList<SysOmsTempPp>();
						ResultSet rs = null;
						if (synTimestamp != null && sql.indexOf("?")>-1) {
							ds.prepareStatement(sql);
							ds.setTimestamp(1, new Timestamp(synTimestamp));
							rs = ds.executeQuery();
						}else{
							rs = ds.executeQuery(sql);
						}
						SysOmsTempPp sysOmsTempPp = null;
						while (rs.next()) {
							sysOmsTempPp = new SysOmsTempPp();
							sysOmsTempPp.setFdPersonId(rs.getString(ppPersonId));
							sysOmsTempPp.setFdPostId(rs.getString(ppPostId));
							// 有效状态
							if (StringUtil.isNotNull(ppStatus) && StringUtil.isNotNull(ppValidStatus)) {
								sysOmsTempPp.setFdIsAvailable(rs.getInt(ppStatus) == Integer.parseInt(ppValidStatus));
							} else {
								sysOmsTempPp.setFdIsAvailable(true);
							}
							// 修改日期
							if(StringUtil.isNotNull(ppAlterTime)){
								sysOmsTempPp.setFdAlterTime(getTimestampByObj( rs.getObject(ppAlterTime)));
							}
							
							sysOmsTempDpList.add(sysOmsTempPp);
						}
						return sysOmsTempDpList;
					}
				});

		return result;
	}

	private Long getTimestampByObj(Object dateObj) {
		
		if (dateObj == null) {
            return null;
        }
		
		if (dateObj instanceof Number) {
			return ((Number) dateObj).longValue();
		} else if (dateObj instanceof Date) {
			return ((Date) dateObj).getTime();
		} else if (dateObj instanceof String) {
			try {
				return Long.parseLong((String)dateObj);
			} catch (Exception e) {
				// logger.error(date,e);
			}
			try {
				return DateUtil.convertStringToDate((String)dateObj, null).getTime();
			} catch (Exception e) {
				// logger.error(date,e);
			}

		}

		return null;
	}

	public void setSysAppConfigService(ISysAppConfigService sysAppConfigService) {
		this.sysAppConfigService = sysAppConfigService;
	}

	

	@Override
	public String[] getKeywordsForDelete() throws Exception {
		return null;
	}

	@Override
	public String getKey() {
		return "";
	}

	public void setSysOmsTempTrxSynService(ISysOmsTempTrxSynService sysOmsTempTrxSynService) {
		this.sysOmsTempTrxSynService = sysOmsTempTrxSynService;
	}

	@Override
	public boolean isSynchroInEnable() throws Exception {
		Map<String, String> params = sysAppConfigService.findByKey(fdKey);
		String key = params.get("kmss.oms.in.db.enabled");
		if ("true".equals(key)) {
			return true;
		} else {
			return false;
		}
	}
	
	protected ICompDbcpService compDbcpService;

	protected ICompDbcpService getCompDbcpService() {
		if (compDbcpService == null) {
            compDbcpService = (ICompDbcpService) SpringBeanUtil.getBean("compDbcpService");
        }
		return compDbcpService;
	}

	private Boolean getTableColumName(String fdSourceName, String tableName, String tbTime) throws Exception {
		CompDbcp dbcp = (CompDbcp) getCompDbcpService().getCompDbcpByName(fdSourceName);
		@SuppressWarnings("unchecked")
		Boolean result = (boolean) DSTemplate.execute(fdSourceName,
				new DSAction<String>(tableName + ";" + dbcp.getFdType() + ";" + tbTime) {
					@Override
					public Boolean doAction(DataSet ds, String type) throws Exception {
						Boolean flag = new Boolean(false);
						String[] schemas = type.split(";");
						String schema = null;
						if ("MS SQL Server".equals(schemas[1])) {
							schema = "dbo";
						} else if ("Oracle".equals(schemas[1])) {
							schema = ds.getConnection().getSchema();
						}
						DatabaseMetaData meta = ds.getConnection().getMetaData();
						ResultSet rs = meta.getColumns(null, schema, schemas[0], null);
						while (rs.next()) {
							String name = rs.getString("COLUMN_NAME");
							String tbType = rs.getString("TYPE_NAME");
							if (StringUtil.isNotNull(name) && StringUtil.isNotNull(tbType)
									&& StringUtil.isNotNull(schemas[2])
									&& schemas[2].toLowerCase().equals(name.toLowerCase())
									&& (tbType.toLowerCase().indexOf("date") > -1
											|| tbType.toLowerCase().indexOf("time") > -1)) {
								flag = true;
							}
						}
						return flag;
					}
				});

		return result;
	}

	
	 protected ISysQuartzJobService sysQuartzJobService;

		protected ISysQuartzJobService getSysQuartzJobServiceImp() {
			if (sysQuartzJobService == null) {
                sysQuartzJobService = (ISysQuartzJobService) SpringBeanUtil.getBean("sysQuartzJobService");
            }
			return sysQuartzJobService;
		}

		protected ISysLogJobService sysLogJobService;

		protected ISysLogJobService getSysLogJobServiceImp() {
			if (sysLogJobService == null) {
                sysLogJobService = (ISysLogJobService) SpringBeanUtil.getBean("sysLogJobService");
            }
			return sysLogJobService;
		}
	    
		@Override
		public void runJob() {
			try {
				HQLInfo hql = new HQLInfo();
				hql.setSelectBlock("fdId");
				hql.setWhereBlock("fdJobService=:fdJobService and fdJobMethod=:fdJobMethod");
				hql.setParameter("fdJobService", "synchroInService");
				hql.setParameter("fdJobMethod", "synchro");
				List list = getSysQuartzJobServiceImp().findValue(hql);
				if (!ArrayUtil.isEmpty(list) && list.get(0) != null) {
					String fdId = list.get(0).toString();
					((ISysQuartzJobExecutor) SpringBeanUtil.getBean("sysQuartzJobExecutor")).execute(fdId);
				}
			} catch (Exception e) {
				logger.error("执行数据库全量接入失败：", e);
			}
		}
		
		
		private Timestamp getOracleTimestamp(Object value) { 
			try { 
			Class clz = value.getClass(); 
			Method m = clz.getMethod("timestampValue", null); 
			                       //m = clz.getMethod("timeValue", null); 时间类型 
			                       //m = clz.getMethod("dateValue", null); 日期类型 
			return (Timestamp) m.invoke(value, null); 

			} catch (Exception e) { 
			return null; 
			} 
			}
}
