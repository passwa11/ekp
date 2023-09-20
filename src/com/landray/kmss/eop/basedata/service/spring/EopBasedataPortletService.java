package com.landray.kmss.eop.basedata.service.spring;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.hibernate.exception.SQLGrammarException;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.common.model.IDocStatusModel;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.constant.SysAuthConstant;
import com.landray.kmss.sys.config.design.SysCfgFlowDef;
import com.landray.kmss.sys.config.design.SysConfigs;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.lbpm.engine.builder.NodeDefinition;
import com.landray.kmss.sys.lbpm.engine.manager.operation.OperationInfo;
import com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess;
import com.landray.kmss.sys.lbpm.engine.service.NodeInstanceInfo;
import com.landray.kmss.sys.lbpm.engine.service.ProcessExecuteService;
import com.landray.kmss.sys.lbpm.engine.service.ProcessInstanceInfo;
import com.landray.kmss.sys.lbpm.engine.service.WorkitemInstanceInfo;
import com.landray.kmss.sys.lbpmperson.forms.LbpmProcessPersonForm;
import com.landray.kmss.sys.lbpmservice.interfaces.LbpmUtil;
import com.landray.kmss.sys.lbpmservice.node.support.AbstractManualNode;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmSettingDefault;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmProcessService;
import com.landray.kmss.sys.lbpmservice.support.service.spring.InternalLbpmProcessOperationInfo;
import com.landray.kmss.sys.lbpmservice.support.util.LbpmTemplateUtil;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.sys.log.util.oper.UserOperContentHelper;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.workflow.interfaces.SysFlowUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class EopBasedataPortletService extends ExtendDataServiceImp  {
	
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(EopBasedataPortletService.class);
	
	private ILbpmProcessService lbpmProcessService;

	protected IBaseService getServiceImp(HttpServletRequest request) {
		if (lbpmProcessService == null) {
			lbpmProcessService = (ILbpmProcessService) SpringBeanUtil.getBean("lbpmProcessService");
		}
		return lbpmProcessService;
	}
	
	/** 流程运行服务 */
	private ProcessExecuteService processExecuteService;
	
	public ProcessExecuteService getProcessExecuteService() {
		if (processExecuteService == null) {
			processExecuteService = (ProcessExecuteService) SpringBeanUtil.getBean("lbpmProcessExecuteService");
		}
		return processExecuteService;
	}
	
	private final InternalLbpmProcessOperationInfo operationInfo = new InternalLbpmProcessOperationInfo();

	public JSONObject listApproval(HttpServletRequest request) throws Exception{

		CriteriaValue cv = new CriteriaValue(request);
		HQLInfo hqlInfo = buildPageHQLInfo(request);

		hqlInfo.setWhereBlock("lbpmProcess.fdStatus !='10' and lbpmProcess.docDeleteFlag=0 ");

		String[] fdModelNames = cv.polls("fdModelName");
		String postType=request.getParameter("postType");
		if(fdModelNames==null&&("shenhe".equals(postType)||"zhangwu".equals(postType))){
			fdModelNames=new String[]{"com.landray.kmss.fssc.loan.model.FsscLoanMain", 
			              "com.landray.kmss.fssc.expense.model.FsscExpenseMain", 
			              "com.landray.kmss.fssc.payment.model.FsscPaymentSuspendMain", 
			              "com.landray.kmss.fssc.payment.model.FsscPaymentRefund", 
			              "com.landray.kmss.fssc.expense.model.FsscExpenseBalance", 
			              "com.landray.kmss.fssc.loan.model.FsscLoanRepayment",  
			              "com.landray.kmss.fssc.loan.model.FsscLoanTransfer", 
			              "com.landray.kmss.fssc.expense.model.FsscExpenseShareMain", 
			              "com.landray.kmss.fssc.payment.model.FsscPaymentMain"};
		}else if(fdModelNames==null&&"chuna".equals(postType)){
			fdModelNames=new String[]{"com.landray.kmss.fssc.loan.model.FsscLoanMain", 
		              "com.landray.kmss.fssc.expense.model.FsscExpenseMain", 
		              "com.landray.kmss.fssc.payment.model.FsscPaymentSuspendMain", 
		              "com.landray.kmss.fssc.payment.model.FsscPaymentRefund", 
		              "com.landray.kmss.fssc.payment.model.FsscPaymentMain"};
		}
		buildModuleHql(fdModelNames, hqlInfo, request);
		this.buildCurrentHandlerHql(request, hqlInfo);
		if (fdModelNames != null && fdModelNames.length == 1) {
			this.buildDocSubjectHql(request, hqlInfo, fdModelNames[0]);
		}
		// 申请人
		buildCreatorHql(request, hqlInfo);
		CriteriaUtil.buildHql(cv, hqlInfo, LbpmProcess.class);

		String isFast = request.getParameter("q.fastop");
		if (StringUtil.isNull(isFast)) {
			isFast = request.getParameter("fastop");
		}

		// 设置流程 待审
		LbpmUtil.buildLimitBlockForMyApproval("lbpmProcess", hqlInfo, null,isFast);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,SysAuthConstant.AllCheck.NO);

		builderSortHql(hqlInfo);
		
		if("chuna".equals(postType)||"zhangwu".equals(postType)){
			//出纳岗，获取待付款的单据
			buildPageByStatus(hqlInfo,request,fdModelNames);
		}
		Page page = null;
		String getTotalOnly = request.getParameter("getTotalOnly");
		if (getTotalOnly != null && "true".equals(getTotalOnly)) {// 查询总数
			hqlInfo.setSelectBlock("count(*)");
			List list = this.getServiceImp(request)
					.findList(hqlInfo);
			page = Page.getEmptyPage();
			page.setTotalrows(Integer.valueOf(list.get(0).toString()));
			request.setAttribute("queryPage", page);
		} else {
			page = getServiceImp(request).findPage(hqlInfo);
			if(UserOperHelper.allowLogOper("listApproval", "com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess")){
				String evenType = ResourceUtil.getString("sys-lbpmperson:lbpmperson.approvaling");
				UserOperHelper.setEventType(evenType);
			}
			// 获取明细
			getDetailInfo(page, request, true);
		}
		int expense=0,loan=0,payment=0;
		List list = this.getServiceImp(request).findList(hqlInfo);
		for(Object obj:list){
			if(PropertyUtils.isReadable(obj, "fdModelName")){
				String modelName=(String) PropertyUtils.getSimpleProperty(obj, "fdModelName");
				if(modelName.startsWith("com.landray.kmss.fssc.expense.model")||modelName.startsWith("com.landray.kmss.fs.expense.model")){
					expense++;
				}else if(modelName.startsWith("com.landray.kmss.fssc.loan.model")||modelName.startsWith("com.landray.kmss.fs.loan.model")){
					loan++;
				}else if(modelName.startsWith("com.landray.kmss.fssc.payment.model")||modelName.startsWith("com.landray.kmss.fs.payment.model")){
					payment++;
				}
			}
		}
		JSONObject rtnData=buildListData(page,request);
		JSONObject approveObj=new JSONObject();
		approveObj.put("expense", expense);
		approveObj.put("loan", loan);
		approveObj.put("payment", payment);
		rtnData.put("approve", approveObj);
		return rtnData;
	}
	
	/**
	 * 
	* Description:  根据模块拼接待付款或者待制证、待记账数据
	* @author xiexingxing
	 * @param fdModelNames 
	* @date 2020年3月20日
	 */
	
	public void buildPageByStatus(HQLInfo hqlInfo,HttpServletRequest request, String[] fdModelNames) throws Exception{
		String postType=request.getParameter("postType");
		StringBuilder where=new StringBuilder();
		if(fdModelNames.length>0){
			for(int i=0,len=fdModelNames.length;i<len;i++){
				if(i>0){
					where.append(" or ");
				}
				where.append(" lbpmProcess.fdModelId in ( ");
				if("chuna".equals(postType)){
					//出纳岗，获取待付款的单据
					where.append(" select t.fdId from  "+ fdModelNames[i]+" t where t.fdPaymentStatus=:status");
				}else if("zhangwu".equals(postType)){
					where.append("(select t.fdId from  "+ fdModelNames[i]+" t where (t.fdVoucherStatus=:fdVoucherStatus or t.fdBookkeepingStatus=:fdBookkeepingStatus))");
				}
				where.append(")");
			}
			if("chuna".equals(postType)){
				hqlInfo.setParameter("status", "10");  //待付款
			}else if("zhangwu".equals(postType)){
				hqlInfo.setParameter("fdVoucherStatus", "10");  //待制证
				hqlInfo.setParameter("fdBookkeepingStatus", "10"); //待记账
			}
			String whereBlock=StringUtil.linkString(hqlInfo.getWhereBlock(), " and ", "("+where.toString()+")");
			hqlInfo.setWhereBlock(whereBlock);
		}
	}
	
	private HQLInfo buildPageHQLInfo(HttpServletRequest request) {
		String s_pageno = request.getParameter("pageno");
		String s_rowsize = request.getParameter("rowsize");
		String orderby = request.getParameter("orderby");
		String ordertype = request.getParameter("ordertype");
		boolean isReserve = false;
		if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
			isReserve = true;
		}
		int pageno = 0;
		int rowsize = SysConfigParameters.getRowSize();
		if (s_pageno != null && s_pageno.length() > 0) {
			pageno = Integer.parseInt(s_pageno);
		}
		if (s_rowsize != null && s_rowsize.length() > 0) {
			rowsize = Integer.parseInt(s_rowsize);
		}
		if (isReserve) {
			orderby += " desc";
		}
		HQLInfo hqlInfo = new HQLInfo();
		hqlInfo.setOrderBy(orderby);
		hqlInfo.setPageNo(pageno);
		hqlInfo.setRowSize(rowsize);

		return hqlInfo;
	}
	
	/**
	* Description:  根据模块名筛选需要展现的模块数据
	* @author xiexingxing
	* @date 2020年3月20日
	 */
	private void buildModuleHql(String[] fdModelNames, HQLInfo hqlInfo,
			HttpServletRequest request) {
		String modelBlock = "";
		String whereBlock = StringUtil.isNull(hqlInfo.getWhereBlock()) ? " 1=1 "
				: hqlInfo.getWhereBlock();
		if (fdModelNames == null) {
			// 列表显示费控模块
			request.setAttribute("showModule", true);
			request.setAttribute("moduleMap", getModuleMap());
		} else {
			if (fdModelNames.length >= 2) {
				request.setAttribute("showModule", true);
				request.setAttribute("moduleMap", getModuleMap());
			}
			for (int i = 0; i < fdModelNames.length; i++) {
				modelBlock += ",'" + fdModelNames[i] + "'";
			}
			modelBlock = modelBlock.substring(1);
			modelBlock = "(" + modelBlock + ")";
			hqlInfo.setWhereBlock(whereBlock
					+ " and lbpmProcess.fdModelName in" + modelBlock);
		}
	}
	/**
	 * 根据当前处理人组建hql
	 * 
	 * @param cv
	 * @param hqlInfo
	 * @param request
	 * @throws Exception
	 */
	private void buildCurrentHandlerHql(HttpServletRequest requestInfo,
			HQLInfo hqlInfo) throws Exception {
		String fdCurrentHandler = requestInfo
				.getParameter("q.fdCurrentHandler");
		if (StringUtil.isNotNull(fdCurrentHandler)) {
			StringBuffer buff = new StringBuffer();
			if (StringUtil.isNotNull(hqlInfo.getJoinBlock())) {
				buff.append(hqlInfo.getJoinBlock());
			}
			buff.append(",LbpmExpecterLog lbpmExpecterLog");
			hqlInfo.setJoinBlock(buff.toString());
			String whereBlock = StringUtil
					.linkString(
							hqlInfo.getWhereBlock(),
							" and ",
							"lbpmExpecterLog.fdProcessId=lbpmProcess.fdId and lbpmExpecterLog.fdIsActive=:isActive and lbpmExpecterLog.fdHandler.fdId in(:currentHandlerIds)");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("isActive", Boolean.TRUE);
			hqlInfo.setParameter("currentHandlerIds",
					getOrgAndPost(fdCurrentHandler));
		}
	}
	
	/**
	 * 
	* Description:  根据创建者拼接筛选条件
	* @author xiexingxing
	* @date 2020年3月20日
	 */
	private void buildCreatorHql(HttpServletRequest requestInfo,
			HQLInfo hqlInfo) {
		String docCreator = requestInfo.getParameter("q.docCreator");
		if (StringUtil.isNotNull(docCreator)) {
			String whereBlock = hqlInfo.getWhereBlock();
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"lbpmProcess.fdCreator.fdId=:fdCreatorId");
			hqlInfo.setWhereBlock(whereBlock);
			hqlInfo.setParameter("fdCreatorId", docCreator);
		}
	}
	
	/**
	 * 
	* Description:  主题查找
	* @author xiexingxing
	* @date 2020年3月20日
	 */
	private void buildDocSubjectHql(HttpServletRequest requestInfo,
			HQLInfo hqlInfo, String fdModelName) {
		String whereBlock = hqlInfo.getWhereBlock();
		String fdSubject = requestInfo.getParameter("q.docSubject");
		String docStatus = requestInfo.getParameter("q.docStatus");

		if (StringUtil.isNotNull(fdModelName)
				&& (StringUtil.isNotNull(fdSubject)
						|| StringUtil.isNotNull(docStatus))) {
			int i = fdModelName.lastIndexOf('.');
			if (i > -1) {
				fdModelName = fdModelName.substring(i + 1);
			}
			String tableName = fdModelName.substring(0, 1).toLowerCase()
					+ fdModelName.substring(1);

			if (StringUtil.isNotNull(fdSubject)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						tableName
								+ ".docSubject like :fdSubject");
				hqlInfo.setParameter("fdSubject", "%" + fdSubject + "%");
			}
			if (StringUtil.isNotNull(docStatus)) {
				whereBlock = StringUtil.linkString(whereBlock, " and ",
						tableName
								+ ".docStatus = :docStatus");
				hqlInfo.setParameter("docStatus", docStatus);
			}
			StringBuffer buff = new StringBuffer();
			if (StringUtil.isNotNull(hqlInfo.getJoinBlock())) {
				buff.append(hqlInfo.getJoinBlock());
			}
			buff.append("," + fdModelName + " " + tableName);
			hqlInfo.setJoinBlock(buff.toString());
			whereBlock = StringUtil.linkString(whereBlock, " and ",
					"lbpmProcess.fdId=" + tableName + ".fdId");
			hqlInfo.setWhereBlock(whereBlock);
		}
	}
	
	/**
	 * 
	* Description:  排序
	* @author xiexingxing
	* @date 2020年3月20日
	 */
	private void builderSortHql(HQLInfo hqlInfo) {
		String joinBlock = hqlInfo.getJoinBlock();
		// 解决多表联合查询无法排序处理
		String orderBy = hqlInfo.getOrderBy();
		if ((joinBlock != null && joinBlock.contains(","))
				&& StringUtil.isNotNull(orderBy)) {
			if (orderBy.contains(".")) {
				hqlInfo.setOrderBy(orderBy);
			} else {
				hqlInfo.setOrderBy("lbpmProcess." + orderBy);
			}
		}
	}
	
	/**
	 * 
	* Description:获取其他展现信息  
	* @author xiexingxing
	* @date 2020年3月20日
	 */
	private void getDetailInfo(Page page, HttpServletRequest request,
			boolean isFast)
			throws Exception {
		List<LbpmProcess> lbpmProcesses = page.getList();
		List<LbpmProcess> filterLbpmProcesses = new ArrayList<LbpmProcess>();
		Map<String, String> subjectMap = new HashMap<String, String>();
		Map<String, String> urltMap = new HashMap<String, String>();
		Map<String, String> numberMap = new HashMap<String, String>();
		Map<String, String> docStatusMap = new HashMap<String, String>();
		Map<String, String> docTemplateMap = new HashMap<String, String>();
		Map<String, String> moneyMap = new HashMap<String, String>();
		Map<String, String> docCreateTimeMap = new HashMap<String, String>();

		for (LbpmProcess lbpmProcess : lbpmProcesses) {
			String uuid = IDGenerator.generateID();
			LbpmProcessPersonForm temp = new LbpmProcessPersonForm();
			if (isFast) {
				PropertyUtils.copyProperties(temp, lbpmProcess);
				temp.setUuid(uuid);
				loadFastApproveInfo(temp);
				filterLbpmProcesses.add(temp);
			} else {
				filterLbpmProcesses.add(lbpmProcess);
			}
			// 加载主model相关信息
			try {
				IBaseModel model = getServiceImp(request).getBaseDao().findByPrimaryKey(lbpmProcess.getFdId(),lbpmProcess.getFdModelName(),true);
				if (model != null) {

					urltMap.put(lbpmProcess.getFdId(),ModelUtil.getModelUrl(model));
					String docSubject = LbpmTemplateUtil.getDocSubject(model);
					subjectMap.put(lbpmProcess.getFdId(),StringUtil.XMLEscape(docSubject));

					if (model instanceof IDocStatusModel) {
						docStatusMap.put(lbpmProcess.getFdId(),((IDocStatusModel) model).getDocStatus());
					}
					if (PropertyUtils.isReadable(model, "fdNumber")) {
						String fdNumber = (String) PropertyUtils.getSimpleProperty(model, "fdNumber");
						numberMap.put(lbpmProcess.getFdId(), fdNumber);
					} else if (PropertyUtils.isReadable(model, "docNumber")) {
						String docNumber = (String) PropertyUtils.getSimpleProperty(model, "docNumber");
						numberMap.put(lbpmProcess.getFdId(), docNumber);
					} else if (PropertyUtils.isReadable(model,"fdReceiveNum")) {
						String docNumber = (String) PropertyUtils.getSimpleProperty(model, "fdReceiveNum");
						numberMap.put(lbpmProcess.getFdId(), docNumber);
					} else if (PropertyUtils.isReadable(model, "fdDocNum")) {
						String docNumber = (String) PropertyUtils.getSimpleProperty(model, "fdDocNum");
						numberMap.put(lbpmProcess.getFdId(), docNumber);
					} else if (PropertyUtils.isReadable(model, "fdNoRule")) {
						String docNumber = (String) PropertyUtils.getSimpleProperty(model, "fdNoRule");
						numberMap.put(lbpmProcess.getFdId(), docNumber);
					}else if(PropertyUtils.isReadable(model, "fdContractNo")) {
						String fdContractNo = (String) PropertyUtils.getSimpleProperty(model, "fdContractNo");
						numberMap.put(lbpmProcess.getFdId(), fdContractNo);
					}
					
					if (PropertyUtils.isReadable(model, "docTemplate")) {
						Object template=PropertyUtils.getProperty(model, "docTemplate");
						if(template!=null){
							docTemplateMap.put(lbpmProcess.getFdId(), (String) PropertyUtils.getSimpleProperty(template, "fdName"));
						}
					}
					
					if (PropertyUtils.isReadable(model, "fdMoney")) {
						moneyMap.put(lbpmProcess.getFdId(), String.valueOf(PropertyUtils.getSimpleProperty(model, "fdMoney")));
					}else if (PropertyUtils.isReadable(model, "fdLoanMoney")) {//借款
						moneyMap.put(lbpmProcess.getFdId(), String.valueOf(PropertyUtils.getSimpleProperty(model, "fdLoanMoney")));
					}else if (PropertyUtils.isReadable(model, "fdLocalAmount")) {//付款
						moneyMap.put(lbpmProcess.getFdId(), String.valueOf(PropertyUtils.getSimpleProperty(model, "fdLocalAmount")));
					}else if (PropertyUtils.isReadable(model, "fdTotalApprovedMoney")) {//报销
						moneyMap.put(lbpmProcess.getFdId(), String.valueOf(PropertyUtils.getSimpleProperty(model, "fdTotalApprovedMoney")));
					}
					if (PropertyUtils.isReadable(model, "docCreateTime")) {
						docCreateTimeMap.put(lbpmProcess.getFdId(), DateUtil.convertDateToString((Date)PropertyUtils.getSimpleProperty(model, "docCreateTime"), DateUtil.PATTERN_DATETIME));
					}
					
					String modelName = "com.landray.kmss.sys.lbpm.engine.persistence.model.LbpmProcess";
					if(UserOperHelper.allowLogOper("listDraft",modelName)
							||UserOperHelper.allowLogOper("listTrack",modelName)
							||UserOperHelper.allowLogOper("listAbandon",modelName)
							||UserOperHelper.allowLogOper("listCreator",modelName)
							||UserOperHelper.allowLogOper("listApproval",modelName)
							||UserOperHelper.allowLogOper("listApproved",modelName)){
						UserOperContentHelper.putFind(lbpmProcess.getFdId(), docSubject,modelName);
					}
				}
				else{
					subjectMap.put(lbpmProcess.getFdId(), "【文档不存在】");
					logger.debug(
							lbpmProcess.getFdId() + "所在模块:"
									+ lbpmProcess.getFdModelName()
									+ "已经被移除,忽略......");
				}

			} catch (Exception e) {
				logger.debug(
						lbpmProcess.getFdId() + "所在模块:"
								+ lbpmProcess.getFdModelName()
								+ "已经被移除,忽略......",
						e);
				subjectMap.put(lbpmProcess.getFdId(), "【文档不存在】");
			}
		}
		page.setList(filterLbpmProcesses);
		request.setAttribute("urltMap", urltMap);
		request.setAttribute("numberMap", numberMap);
		request.setAttribute("subjectMap", subjectMap);
		request.setAttribute("docStatusMap", docStatusMap);
		request.setAttribute("docTemplateMap", docTemplateMap);
		request.setAttribute("moneyMap", moneyMap);
		request.setAttribute("docCreateTimeMap", docCreateTimeMap);

		request.setAttribute("queryPage", page);
	}
	
	/**
	 * 
	* Description:  获取模块信息，这里只获取费控借款、付款、报销数据
	* @author xiexingxing
	* @date 2020年3月20日
	 */
	private Map<String, String> getModuleMap() {
		Map<String, String> moduleMap = new HashMap<String, String>();
		// #60550 流程发起页面下拉框选择模块，我起草的列表视图筛选器有所属模块，根据上线情况由管理员去设定可选择的模块范围
		try {
			LbpmSettingDefault lbpmSettingDefault = new LbpmSettingDefault();
			// 1、得到数据
			String moduleScopeIdsStr = lbpmSettingDefault.getModuleScopeIds();
			String moduleScopeNamesStr = lbpmSettingDefault.getModuleScopeNames();
			// 2、判空，如果为空则表明管理员没有选择任何模块，返回全部所有的模块
			if (StringUtil.isNotNull(moduleScopeIdsStr) && StringUtil.isNotNull(moduleScopeNamesStr)) {
				// 3、不为空，格式化数据
				String[] spModuleScopeIds = moduleScopeIdsStr.split(";");
				String[] spModuleScopeNames = moduleScopeNamesStr.split(";");
				// 3.1、判断二者数据量是否相等
				if (spModuleScopeIds.length != 0 && spModuleScopeNames.length != 0
						&& spModuleScopeIds.length == spModuleScopeNames.length) {
					for (int i = 0; i < spModuleScopeIds.length; i++) {
						moduleMap.put(spModuleScopeIds[i], spModuleScopeNames[i]);
					}
				}
			} else {
				// 4、为空则重新获取数据
				List<SysCfgFlowDef> sysCfgFlowDefs = SysConfigs.getInstance().getAllFlowDef();
				for (SysCfgFlowDef sysCfgFlowDef : sysCfgFlowDefs) { // 模块信息
					if ((moduleMap.get(sysCfgFlowDef.getModelName()) == null)
							&& (StringUtil.isNotNull(sysCfgFlowDef.getModuleMessageKey()))) {
						if(StringUtil.isNotNull(sysCfgFlowDef.getModelName())&&
								(sysCfgFlowDef.getModelName().startsWith("com.landray.kmss.fssc.expense.model")
										||sysCfgFlowDef.getModelName().startsWith("com.landray.kmss.fssc.loan.model")
										||sysCfgFlowDef.getModelName().startsWith("com.landray.kmss.fssc.payment.model"))){
							String moduleName = ResourceUtil.getString(sysCfgFlowDef.getModuleMessageKey());
							SysDictModel dict = SysDataDict.getInstance().getModel(sysCfgFlowDef.getModelName());
							if (dict != null && StringUtil.isNotNull(dict.getUrl()) && dict.getUrl().indexOf(".do") > 0) {
								String url = dict.getUrl();
								url = url.substring(0, url.indexOf(".do")) + ".do"; // 值获取有相关权限的分类模板
								if (!UserUtil.checkAuthentication(url, null)) {
									continue;
								}
							}
							if (StringUtil.isNotNull(moduleName)) {
								moduleMap.put(sysCfgFlowDef.getModelName(), moduleName);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 5、返回数据
		return moduleMap;
	}
	
	private List getOrgAndPost(String orgId) throws Exception {
		ISysOrgElementService sysOrgElementService = (ISysOrgElementService) SpringBeanUtil
				.getBean("sysOrgElementService");
		List<String> postList = new ArrayList<String>();
		SysOrgElement org = (SysOrgElement) sysOrgElementService
				.findByPrimaryKey(orgId);
		List<SysOrgElement> posts = org.getFdPosts();
		for (SysOrgElement post : posts) {
			if (!postList.contains(orgId)) {
				postList.add(post.getFdId());
			}
		}
		postList.add(orgId);
		return postList;
	}
	
	private ProcessInstanceInfo loadFastApproveInfo(LbpmProcessPersonForm lbpmProcessPersonForm) {
		ProcessInstanceInfo info = getProcessExecuteService().load(lbpmProcessPersonForm.getFdId());
		List<NodeInstanceInfo> nodeinfos = info.getCurrentNodeInfos();
		for (NodeInstanceInfo nodeinfo : nodeinfos) {
			List<WorkitemInstanceInfo> workitemInstanceInfos = nodeinfo
					.getHandlerWrokitemInfos();
			for (WorkitemInstanceInfo workitemInstanceInfo : workitemInstanceInfos) {
				workitemInstanceInfo.getWorkitem();
				// 获取系统当前操作，判断是否有对应的快速审批的操作
				List<OperationInfo> workitemOperations = operationInfo
						.getWorkitemOperationInfos(workitemInstanceInfo);

				// 从节点定义中判断节点是否有快速审批的标识
				NodeDefinition nodeDef = nodeinfo.getNodeDefinitionInfo()
						.getDefinition();
				// 非人工节点不处理
				if (!(nodeDef instanceof AbstractManualNode)) {
					break;
				}
				AbstractManualNode manualNode = (AbstractManualNode) nodeDef;
				// 通过类型需要通过OperType区分
				for (OperationInfo operationInfo : workitemOperations) {
					if ("handler_pass".equals(operationInfo.getType())
							|| "handler_sign".equals(operationInfo.getType())) {
						if (manualNode.isCanFastReview()) {
							lbpmProcessPersonForm.setIsFastApprove("true");
						}
					}
					if ("handler_refuse".equals(operationInfo.getType())
							|| "handler_superRefuse"
									.equals(operationInfo.getType())) {
						if (manualNode.isCanFastReject()) {
							lbpmProcessPersonForm.setIsFastReject("true");
						}
					}
				}
			}
		}
		return info;
	}
	/**
	 * 
	* Description:构建返回前台json数据  
	* @author xiexingxing
	* @date 2020年3月20日
	 */
	public JSONObject buildListData(Page page, HttpServletRequest request) throws Exception{
		Map<String, String> subjectMap = (Map<String, String>) request.getAttribute("subjectMap");
		Map<String, String> urltMap = (Map<String, String>) request.getAttribute("urltMap");
		Map<String, String> docStatusMap = (Map<String, String>) request.getAttribute("docStatusMap");
		Map<String, String> docTemplateMap = (Map<String, String>) request.getAttribute("docTemplateMap");
		Map<String, String> moneyMap = (Map<String, String>) request.getAttribute("moneyMap");
		Map<String, String> docCreateTimeMap = (Map<String, String>) request.getAttribute("docCreateTimeMap");
		Map<String, String> moduleMap = (Map<String, String>) request.getAttribute("moduleMap");
		JSONObject rtnData=new JSONObject();
		JSONArray datasArr=new JSONArray();
		List list=page.getList();
		for(Object obj:list){
			JSONArray dataArr=new JSONArray();
			JSONObject feildValue=new JSONObject();
			String fdId=(String) PropertyUtils.getProperty(obj, "fdId");
			feildValue.put("fdId", fdId);
			feildValue.put("docSubject", subjectMap.containsKey(fdId)?subjectMap.get(fdId):"");
			feildValue.put("type", docTemplateMap.containsKey(fdId)?docTemplateMap.get(fdId):"");
			feildValue.put("money", moneyMap.containsKey(fdId)?moneyMap.get(fdId):"");
			feildValue.put("docPublishTime", docCreateTimeMap.containsKey(fdId)?docCreateTimeMap.get(fdId):"");
			Object org=PropertyUtils.getProperty(obj, "fdCreator");
			if(org!=null){
				SysOrgElement creator=(SysOrgElement) org;
				feildValue.put("fdName", creator.getFdName());
				SysOrgElement dept=creator.getFdParent();
				feildValue.put("department", dept!=null?dept.getFdName():"");
			}
			String docStatus=docStatusMap.containsKey(fdId)?docStatusMap.get(fdId):"";
			feildValue.put("state", EnumerationTypeUtil.getColumnEnumsLabel("common_status",docStatus));
			feildValue.put("url", urltMap.containsKey(fdId)?urltMap.get(fdId):"");
			if(PropertyUtils.isReadable(obj, "fdModelName")){
				String modelName=(String) PropertyUtils.getSimpleProperty(obj, "fdModelName");
				feildValue.put("module", moduleMap.containsKey(modelName)?moduleMap.get(modelName):"");
			}
			dataArr.add(feildValue);
			datasArr.add(dataArr);
		}
		rtnData.put("datas", datasArr);
		JSONObject pageJson=new JSONObject();
		pageJson.put("currentPage", page.getPageno());
		pageJson.put("pageSize", page.getRowsize());
		pageJson.put("totalSize", page.getTotalrows());
		rtnData.put("page", pageJson);
		rtnData.put("approved", getListApproved(request));
		return rtnData;
	}
	
	/**
	 * 
	* Description:获取已审数据  
	* @author xiexingxing
	* @date 2020年3月20日
	 */
	public JSONObject getListApproved(HttpServletRequest request) throws Exception {
		JSONObject approvedObj=new JSONObject();
		CriteriaValue cv = new CriteriaValue(request);
		HQLInfo hqlInfo = buildPageHQLInfo(request);
		hqlInfo.setWhereBlock(
				"lbpmProcess.fdStatus !='10' and lbpmProcess.docDeleteFlag=0 ");
		String[] fdModelNames = cv.polls("fdModelName");
		buildModuleHql(fdModelNames, hqlInfo, request);
		this.buildCurrentHandlerHql(request, hqlInfo);
		if (fdModelNames != null && fdModelNames.length == 1) {
			this.buildDocSubjectHql(request, hqlInfo, fdModelNames[0]);
		}
		// 申请人
		buildCreatorHql(request, hqlInfo);

		CriteriaUtil.buildHql(cv, hqlInfo, LbpmProcess.class);

		// 设置流程 已审
		SysFlowUtil
				.buildLimitBlockForMyApproved("lbpmProcess", hqlInfo);
		hqlInfo.setCheckParam(SysAuthConstant.CheckType.AllCheck,
				SysAuthConstant.AllCheck.NO);

		builderSortHql(hqlInfo);
		int expense=0,loan=0,payment=0;
		List list = this.getServiceImp(request).findList(hqlInfo);
		for(Object obj:list){
			if(PropertyUtils.isReadable(obj, "fdModelName")){
				String modelName=(String) PropertyUtils.getSimpleProperty(obj, "fdModelName");
				if(modelName.startsWith("com.landray.kmss.fssc.expense.model")||modelName.startsWith("com.landray.kmss.fs.expense.model")){
					expense++;
				}else if(modelName.startsWith("com.landray.kmss.fssc.loan.model")||modelName.startsWith("com.landray.kmss.fs.loan.model")){
					loan++;
				}else if(modelName.startsWith("com.landray.kmss.fssc.payment.model")||modelName.startsWith("com.landray.kmss.fs.payment.model")){
					payment++;
				}
			}
		}
		approvedObj.put("expense", expense);
		approvedObj.put("loan", loan);
		approvedObj.put("payment", payment);
		return approvedObj;
	}
	
	/**
	 * 根据单号查询对应的数据连接
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public JSONObject getDocData(HttpServletRequest request)  throws Exception{
		JSONObject rtnObj=new JSONObject();
		String params=request.getParameter("params");
		if(StringUtil.isNotNull(params)) {
			JSONObject paramJson=JSONObject.fromObject(params);
			String fdNumber=paramJson.optString("fdNumber", "");
			String sql="select fd_id from fssc_search_by_tiaoma where doc_number ='"+fdNumber.trim()+"'";
			List<String> result=new ArrayList<String>();
			try {
				result=getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery(sql).list();
			} catch (Exception e) {
				logger.error("fssc_search_by_tiaoma视图不存在，即将创建");
				logger.info("开始创建fssc_search_by_tiaoma视图");
				//未找到视图，创建视图
				// 加载模块
				// 1、读取design.xml，获取模块信息
				String driveName = ResourceUtil.getKmssConfigString("hibernate.connection.driverClass");
				Map<String,Object> modelMap = SysConfigs.getInstance().getFlowDefByMain();
				StringBuilder viewSql=new StringBuilder("create view fssc_search_by_tiaoma as  ");
				if("com.ibm.db2.jcc.DB2Driver".equals(driveName)) {//DB2数据库
					viewSql.append("(");
				}
				String tempSql="";
				for(Entry entry : modelMap.entrySet()){
				    String fdModelName = (String) entry.getKey();
				    if(StringUtil.isNotNull(fdModelName)&&fdModelName.startsWith("com.landray.kmss.fssc")) {
				    	SysDataDict dataDict = SysDataDict.getInstance();
				    	String tableName = dataDict.getModel(fdModelName).getTable();
				    	if(dataDict.getModel(fdModelName).getPropertyMap().containsKey("docNumber")) {
				    		tempSql=StringUtil.linkString(tempSql, " UNION ALL ", " select "+tableName+".fd_id fd_id, "+tableName+".doc_number doc_number from  "+tableName);
				    	}else {
				    		tempSql=StringUtil.linkString(tempSql, " UNION ALL ", " select "+tableName+".fd_id fd_id, "+tableName+".fd_number doc_number from  "+tableName);
				    	}
				    }
				   
				}
				viewSql.append(tempSql);
				if("com.ibm.db2.jcc.DB2Driver".equals(driveName)) {//DB2数据库
					viewSql.append(")");
				}
				getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery(viewSql.toString()).executeUpdate();
				getServiceImp(request).getBaseDao().getHibernateSession().flush();
				logger.info("创建fssc_search_by_tiaoma视图结束");
				result=getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery(sql).list();
			}
			if(!ArrayUtil.isEmpty(result)) {
				//查询流程表
				List<String> modelNameList=getServiceImp(request).getBaseDao().getHibernateSession().createNativeQuery("select fd_model_name from lbpm_process where fd_model_id=:fdModelId")				.setParameter("fdModelId", result.get(0)).list();
				if(!ArrayUtil.isEmpty(modelNameList)) {
					String fdModelName=modelNameList.get(0);
					SysDictModel dict=SysDataDict.getInstance().getModel(fdModelName);
					String fdUrl=dict.getUrl();
					if(StringUtil.isNotNull(fdUrl)) {
						rtnObj.put("fdUrl", fdUrl.replace("${fdId}", result.get(0)));
					}
					rtnObj.put("result", "success");
				}else {
					rtnObj.put("result", "failure");
				}
			}else {
				rtnObj.put("result", "failure");
			}
		}
		return rtnObj;
	}
}
