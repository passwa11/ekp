
package com.landray.kmss.fssc.config.actions;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.hibernate.query.Query;
import org.hibernate.transform.Transformers;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.config.forms.FsscConfigScoreForm;
import com.landray.kmss.fssc.config.model.FsscConfigScore;
import com.landray.kmss.fssc.config.service.IFsscConfigScoreService;
import com.landray.kmss.fssc.config.util.FormDataUtil;
import com.landray.kmss.fssc.config.webserver.spring.SFiveWebserviceServiceImp;
import com.landray.kmss.fssc.config.webserver.spring.SFourWebserviceServiceImp;
import com.landray.kmss.fssc.config.webserver.spring.SFourteenWebserviceServiceImp;
import com.landray.kmss.fssc.config.webserver.spring.SThirteenWebserviceServiceImp;
import com.landray.kmss.km.review.model.KmReviewMain;
import com.landray.kmss.km.review.service.IKmReviewMainService;
import com.landray.kmss.km.review.service.IKmReviewTemplateService;
import com.landray.kmss.km.review.webservice.IKmReviewWebserviceService;
import com.landray.kmss.km.review.webservice.KmReviewParamterForm;
import com.landray.kmss.sys.attend.model.SysAttendCategory;
import com.landray.kmss.sys.attend.service.ISysAttendCategoryService;
import com.landray.kmss.sys.attend.util.AttendUtil;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.sys.config.model.SysConfigParameters;
import com.landray.kmss.sys.metadata.model.ExtendDataModelInfo;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.service.ISysOrgElementService;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.time.model.SysTimeLeaveAmountItem;
import com.landray.kmss.sys.time.model.SysTimeLeaveDetail;
import com.landray.kmss.sys.time.model.SysTimeLeaveRule;
import com.landray.kmss.sys.time.service.ISysTimeLeaveAmountService;
import com.landray.kmss.sys.xform.maindata.model.SysFormMainDataCustomList;
import com.landray.kmss.sys.xform.maindata.service.ISysFormMainDataCustomListService;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.CriteriaUtil;
import com.landray.kmss.util.CriteriaValue;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import net.sf.json.JSONObject;

/**
  * 点赞积分配置 Action
  */
public class FsscConfigScoreAction extends ExtendAction {

    private IFsscConfigScoreService fsscConfigScoreService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscConfigScoreService == null) {
            fsscConfigScoreService = (IFsscConfigScoreService) getBean("fsscConfigScoreService");
        }
        return fsscConfigScoreService;
    }
 private ISysOrgPersonService sysOrgPersonService;
    
    public ISysOrgPersonService getSysOrgPersonService() {
    	if(sysOrgPersonService==null){
    		sysOrgPersonService=(ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
    	}
		return sysOrgPersonService;
	}
 private ISysOrgElementService sysOrgElementService;
    
    public ISysOrgElementService getSysOrgElementService() {
    	if(sysOrgElementService==null){
    		sysOrgElementService=(ISysOrgElementService) SpringBeanUtil.getBean("sysOrgElementService");
    	}
		return sysOrgElementService;
	}
    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
    	CriteriaValue cv = new CriteriaValue(request);

		// 以下筛选属性需要手工定义筛选范围
		String _fdKey = cv.poll("_fdKey");
		if(_fdKey!=null){

			HQLInfo hqlInfo2=new HQLInfo();
			hqlInfo2.setWhereBlock("fdName like :fdLoginName");
			hqlInfo2.setParameter("fdLoginName", "%"+_fdKey+"%");
			List<SysOrgElement> list=getSysOrgElementService().findList(hqlInfo2);
			
		StringBuffer whereBlock = null;
		whereBlock = new StringBuffer("1 = 1");
		if(list.size()!=0){
			whereBlock.append(
					" and fsscConfigScore.fdPerson.fdId in (:fdKey)");
			List<String> ids = new ArrayList<String>();
			for (int i=0;i<list.size();i++) {
				ids.add(list.get(i).getFdId());
			 
			}

			hqlInfo.setParameter("fdKey", ids);
		}else{
		whereBlock.append(
				" and fsscConfigScore.fdYear=:fdKey or fsscConfigScore.fdMonth=:fdKey");
		hqlInfo.setParameter("fdKey", _fdKey);
		}
		hqlInfo.setWhereBlock(whereBlock.toString());
//        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscConfigScore.class);
		CriteriaUtil.buildHql(cv, hqlInfo, FsscConfigScore.class);
		}
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.fssc.config.util.FsscConfigUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.fssc.config.model.FsscConfigScore.class);
        com.landray.kmss.fssc.config.util.FsscConfigUtil.buildHqlInfoModel(hqlInfo, request);
    }

    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscConfigScoreForm fsscConfigScoreForm = (FsscConfigScoreForm) super.createNewForm(mapping, form, request, response);
        ((IFsscConfigScoreService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscConfigScoreForm;
    }
    
    
    
    /**
	 * 专家导入模板下载
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 public ActionForward downloadTemplate(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    		HttpServletResponse response) throws Exception {
	    	TimeCounter.logCurrentTime("Action-downloadTemplate", true, getClass());
			KmssMessages messages = new KmssMessages();
			try {
				// 模板
				String filePath = ConfigLocationsUtil.getWebContentPath()
						+ "/fssc/config/common/import_template.xlsx";
				Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
				final String fileName = "积分导入模板.xlsx";
				response.setHeader("Content-Disposition", "attachment; filename=\"" 
						+ new String(fileName.getBytes("GBK"),"iso8859-1") + "\"");
				response.setHeader("Content-Type", "application/octet-stream");
				OutputStream out = response.getOutputStream();
				workbook.write(out);
				out.close();
				return null;
			} catch (Exception e) {
				messages.addError(e);
				KmssReturnPage.getInstance(request).addMessages(messages).save(request);
				return getActionForward("failure", mapping, form, request, response);
			}finally{
				TimeCounter.logCurrentTime("Action-downloadTemplate", false, getClass());
			}
	    }
	 
	/**
	 * Excel导入功能
	 * 
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	 public ActionForward saveExcel(ActionMapping mapping, ActionForm form, HttpServletRequest request,
	    		HttpServletResponse response) throws Exception {
	    	TimeCounter.logCurrentTime("Action-importInfos", true, getClass());
			KmssMessages messages = new KmssMessages();
			try {
				List<String> list = ((IFsscConfigScoreService) getServiceImp(request))
						.addInfoByImport((FsscConfigScoreForm) form, request);
				
				if(!ArrayUtil.isEmpty(list)){
					request.setAttribute("errorList", list);
					return getActionForward("importError", mapping, form, request, response);
				}
			} catch (Exception e) {
				messages.addError(e);
			}

			TimeCounter.logCurrentTime("Action-importInfos", false, getClass());
			KmssReturnPage.getInstance(request).addMessages(messages)
					.addButton(KmssReturnPage.BUTTON_CLOSE)
					.addButton(KmssReturnPage.BUTTON_RETURN)
					.save(request);
			if (messages.hasError()) {
				return getActionForward("failure", mapping, form, request, response);
			} else {
				return getActionForward("success", mapping, form, request, response);
			}
	    }
	 
	 
	//==============预算统计台账
		
		/**
		 * 查询报表
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */					
		public ActionForward report(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			System.out.println("积分报表查询");
			TimeCounter.logCurrentTime("executeLedger", true, getClass());
	        KmssMessages messages = new KmssMessages();
	        try {
	            String s_pageno = request.getParameter("pageno");
	            String s_rowsize = request.getParameter("rowsize");
	            String orderby = request.getParameter("orderby");
	            String ordertype = request.getParameter("ordertype");
	            String keyWord = request.getParameter("q._keyword");

	            boolean isReserve = false;
	            if (ordertype != null && "down".equalsIgnoreCase(ordertype)) {
	                isReserve = true;
	            }
	            int pageno = 0;
	            int rowsize = SysConfigParameters.getRowSize();
	            if (s_pageno != null && s_pageno.length() > 0 && Integer.parseInt(s_pageno) > 0) {
	                pageno = Integer.parseInt(s_pageno);
	            }
	            if (s_rowsize != null && s_rowsize.length() > 0 && Integer.parseInt(s_rowsize) > 0) {
	                rowsize = Integer.parseInt(s_rowsize);
	            }
	            if (isReserve&&StringUtil.isNotNull(orderby)) {
					orderby += " desc";
				}
	            JSONObject params = new JSONObject();
	            params.put("keyWord", keyWord);
	            if(StringUtil.isNotNull(orderby)){
	            	  params.put("orderby", orderby);
	            }
	            params.put("pageno", pageno);
	            params.put("rowsize", rowsize);
	            request.setAttribute("queryPage", ((IFsscConfigScoreService) getServiceImp(request)).getDataPage(request,params));
	        } catch (Exception e) {
	            messages.addError(e);
	        }
	        TimeCounter.logCurrentTime("executeLedger", false, getClass());
	        if (messages.hasError()) {
	            KmssReturnPage.getInstance(request).addMessages(messages).addButton(KmssReturnPage.BUTTON_CLOSE).save(request);
	            return mapping.findForward("failure");
	        } else {
	            return mapping.findForward("report");
	        }
		}
		/**
		 * 导出预算执行台账
		 * @param mapping
		 * @param form
		 * @param request
		 * @param response
		 * @return
		 * @throws Exception
		 */
		public ActionForward exportReport(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			TimeCounter.logCurrentTime("Action-exportExecuteLedger", true, getClass());
			KmssMessages messages = new KmssMessages();
			ServletOutputStream os = null;
			try {
				Date data = new Date();
				String dataNow = DateUtil.convertDateToString(data, "yyyy-MM-dd");
				HSSFWorkbook workBook = ((IFsscConfigScoreService) getServiceImp(request)).getExportDataList(request,response);
			} catch (Exception e) {
				messages.addError(e);
			}finally {
				if (os != null) {
					os.close();
				}
			}
			TimeCounter.logCurrentTime("Action-exportExecuteLedger", false, getClass());
			if (messages.hasError()) {
				KmssReturnPage.getInstance(request).addMessages(messages)
						.addButton(KmssReturnPage.BUTTON_RETURN).save(request);
				return getActionForward("failure", mapping, form, request, response);
			}
			return null;
		}
		
		
		
		public String getSFourteenData(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			String sql ="";
			JSONObject result=new JSONObject();
			try {
				String startDate=request.getParameter("startDate");
				String endDate=request.getParameter("endDate");
				
				logger.info("请求的数据:开始日期：" + startDate+"-结束日期:"+endDate);
				String fields="detail.fd_zc_code,main.fd_type ";
				 sql = "SELECT "+fields+" FROM ekp_s_fourteen_main main "
						+ " LEFT JOIN km_review_main review on main.fd_id=review.fd_id "
						+ " LEFT JOIN ekp_s_fourteen_detail detail on main.fd_id=detail.fd_parent_id ";
				String where ="WHERE 1=1 and review.doc_status='30' ORDER BY review.doc_create_time ";//只查询已发布的单据and main.fd_date=:dataType and review.doc_status='30' 
				if(StringUtil.isNotNull(startDate)){
					where+=" and main.fd_date>=:startDate";
				}
				if(StringUtil.isNotNull(endDate)){
					where+=" and main.fd_date<=:endDate";
				}
				sql+=where;
				System.out.println("mainSql:"+sql);
				Query query = getServiceImp(null).getBaseDao().getHibernateSession().createNativeQuery(sql);

				if(StringUtil.isNotNull(startDate)){
					query.setParameter("startDate", startDate);
				}
				if(StringUtil.isNotNull(endDate)){
					query.setParameter("endDate", endDate);
				}
				List<Map<String, Object>> list = query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();
				Map<String, Object> codeMap=new HashMap<>();
				for (Map<String, Object> map : list) {
					String fdZcCode=(String) map.get("fd_zc_code");
					String fdType=(String) map.get("fd_type");
					if(StringUtil.isNotNull(fdZcCode)&&StringUtil.isNotNull(fdType)){
						codeMap.put(fdZcCode, fdType);
					}
				}
				List<Map<String, Object>> resultList=new ArrayList<>();
				for(String key:codeMap.keySet()){
				       String value = codeMap.get(key).toString();
				       Map<String, Object> one=new HashMap<>();  
				       one.put("fd_zc_code", key);
				       one.put("fd_type", value);
				       resultList.add(one);
				}
				net.sf.json.JSONArray jsonarray = net.sf.json.JSONArray.fromObject(resultList);
				result.put("code", 200);
				result.put("data", jsonarray.toString());
				
			} catch (Exception e) {
				result.put("code", 300);
				result.put("data", "请联系管理员！sql为:"+sql);
			}
			response.setCharacterEncoding("GBK");
			response.getWriter().write(result.toString());
			return null;
		}
		
		
		public String getData(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
				String result="";
				String startDate=request.getParameter("startDate");
				String endDate=request.getParameter("endDate");
				String dataType=request.getParameter("dataType");//数据类型：1：资产领用；2：资产调拨
				String type=request.getParameter("type");
				if("s04".equals(type)){
					SFourWebserviceServiceImp server =new SFourWebserviceServiceImp();
					result=server.getData(startDate, endDate, dataType);
				}else if("s05".equals(type)){
					SFiveWebserviceServiceImp server =new SFiveWebserviceServiceImp();
					result=server.getData(startDate, endDate, dataType);
				}else if("s13".equals(type)){
					SThirteenWebserviceServiceImp server =new SThirteenWebserviceServiceImp();
					result=server.getData(startDate, endDate, dataType);
				}else if("s14".equals(type)){
					SFourteenWebserviceServiceImp server =new SFourteenWebserviceServiceImp();
					result=server.getData(startDate, endDate);
				}
				response.setCharacterEncoding("GBK");
				response.getWriter().write(result);
			return null;
		}

		private IKmReviewMainService kmReviewMainService;
		public IKmReviewMainService getKmReviewMainService() {
			if (kmReviewMainService == null) {
				kmReviewMainService = (IKmReviewMainService) SpringBeanUtil.getBean("kmReviewMainService");
			}
			return kmReviewMainService;
		}
		
		
		private IKmReviewTemplateService kmReviewTemplateService;
		public IKmReviewTemplateService getKmReviewTemplateService() {
			if (kmReviewTemplateService == null) {
				kmReviewTemplateService = (IKmReviewTemplateService) SpringBeanUtil.getBean("kmReviewTemplateService");
			}
			return kmReviewTemplateService;
		}
/**
 * sf对接
 * @param mapping
 * @param form
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
		public String addModel(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
//			logger.info("请求的数据:开始日期：" + startDate+"-结束日期:"+endDate);
			String result=testAddNewsInRestTemplate();
			response.getWriter().write(result);
			return null;
		}
		
		
		
		
		private IKmReviewWebserviceService kmReviewWebserviceService;
		public IKmReviewWebserviceService getKmReviewWebserviceService() {
			if (kmReviewWebserviceService == null) {
				kmReviewWebserviceService = (IKmReviewWebserviceService) SpringBeanUtil.getBean("kmReviewWebserviceService");
			}
			return kmReviewWebserviceService;
		}
		
		
		
		
		/**
	     * 多层级的VO对象，且包含上传功能的样例
	     * 注意key的书写格式,类似EL表达式的方式，属性关系用'.', 列表和数组关系用[]，Map关系用["xxx"]
	     */
	    public String testAddNewsInRestTemplate() throws Exception{
	        KmReviewParamterForm webParamForm=new KmReviewParamterForm();
	        webParamForm.setDocSubject("ljc的流程");
	        webParamForm.setDocCreator("{\"Id\":\"1829eefc033969261cc245043689e42d\"}");
	        webParamForm.setDocStatus("20");
	        webParamForm.setFdTemplateId("181fb4fbfa2994d7cd25395488aa59f7");
	        String formValues="{\"field_1170962309\":\"2022-09-30\", \"reason\":\"事由\", \"total\":\"200\"}";
	        webParamForm.setFormValues(formValues);
	        
	        return getKmReviewWebserviceService().addReview(webParamForm);
	    }
	    
	    
	    /**
	     * 判断单据是否能提交,
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public String canSubmit(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	String result="当前已存在重叠时间点流程，请确认后提交！";
//	    	String result="规定时间超过单据申请数，请检查!";
	    	response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
	    	//month,week,day,hour
			String type=request.getParameter("type");
			String fdTemplateId=request.getParameter("fdTemplateId");
			String docCreatorId=UserUtil.getUser().getFdId();
			String maxNum=request.getParameter("maxNum");//限制提交数量

			//报备流程id（根据提交人查询）
			String fdBbTemplateId=request.getParameter("fdBbTemplateId");
			if(StringUtil.isNotNull(fdBbTemplateId)){//如果有报备流程id需要判断，当前提交时间是否在报备时间内
				boolean isBb=false;//是否在报备时间内
				HQLInfo bbInfo=new HQLInfo();
				bbInfo.setWhereBlock(" docCreator.fdId=:docCreatorId and fdTemplate.fdId=:fdBbTemplateId and "
						+ " docStatus='30' ");
				bbInfo.setParameter("docCreatorId", docCreatorId);
				bbInfo.setParameter("fdBbTemplateId", fdBbTemplateId);
				List<KmReviewMain> bbList=getKmReviewMainService().findList(bbInfo);
				if(bbList==null||bbList.size()<=0){//如果没报备
					result="请先报备在提交申请！";
					response.getWriter().write(result);
					return null;
				}
				for (KmReviewMain kmReviewMain : bbList) {
					ExtendDataModelInfo extendDataModelInfo = kmReviewMain.getExtendDataModelInfo();
					Map<String, Object> map=extendDataModelInfo.getModelData();
					Date startTime= (Date) FormDataUtil.getValueByKey(map, "fd_start_time");
					Date endTime= (Date) FormDataUtil.getValueByKey(map, "fd_end_time");
//					Date startTime=DateUtil.convertStringToDate(fdStartTime);
//					Date endTime=DateUtil.convertStringToDate(fdEndTime);
					Date now=DateUtil.getDateQueue();
					if(now.getTime()<endTime.getTime()&&now.getTime()>startTime.getTime()){
						//如果在日期内直接返回
						isBb=true;
						break;
					}
				}
				if(!isBb){
					result="请检查是否在报备时间内申请!";
					response.getWriter().write(result);
					return null;
				}
			}
			Date startTime=new Date();
			Date endTime=new Date();
			if("month".equals(type)){
				startTime=DateUtil.getBeginDayOfMonth();
				endTime=DateUtil.getEndDayOfMonth();
				endTime=DateUtil.getDayEndTime(endTime);
			}else if("week".equals(type)){
				startTime=DateUtil.getBeginDayOfWeek();
				endTime=DateUtil.getEndDayOfWeek();
				endTime=DateUtil.getDayEndTime(endTime);
			}else if("day".equals(type)){
				startTime=DateUtil.getDate(0);
				endTime=DateUtil.getDayEndTime(startTime);
			}
			HQLInfo hqlInfo=new HQLInfo();
			hqlInfo.setWhereBlock(" docCreator.fdId=:docCreatorId and fdTemplate.fdId=:fdTemplateId and "
					+ " docStatus in ('20','30') and docCreateTime<=:endTime and docCreateTime>=:startTime ");
			hqlInfo.setParameter("startTime", startTime);
			hqlInfo.setParameter("endTime", endTime);
			hqlInfo.setParameter("docCreatorId", docCreatorId);
			hqlInfo.setParameter("fdTemplateId", fdTemplateId);

			List list=getKmReviewMainService().findList(hqlInfo);
			int num=0;
			if(list!=null&&list.size()>0){
				num=list.size();
			}
			if(Double.valueOf(maxNum)>num){//判断限制提交数量是否大于已提交数量
				result="true";
			}
			
			response.getWriter().write(result);
			return null;
		}
	    
	    /**
	     * 判断单据是否能提交,
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public String canSubmitDates(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	String result="当前已存在重叠时间点流程，请确认后提交！";
//	    	String result="规定时间超过单据申请数，请检查!";
	    	response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String startDate=request.getParameter("startDate");//开始时间
			String endDate=request.getParameter("endDate");
			String fdTemplateId=request.getParameter("fdTemplateId");
			String fdPersonId=request.getParameter("fdPersonId");
			String maxNum=request.getParameter("maxNum");//限制提交数量
			String tableName=request.getParameter("tableName");//表名
			String startFieldName=request.getParameter("startFieldName");//时间开始字段名称
			String endFieldName=request.getParameter("endFieldName");//时间结束字段名称
			String detailTableName=request.getParameter("detailTableName");//明细表名
			String leaveType=request.getParameter("leaveType");//申请类型
			String hourNum=request.getParameter("hourNum");//小时数
			
			String detailSql="";
			   
			if(StringUtil.isNotNull(detailTableName)){
				detailSql=" left  join "+detailTableName+" detail on main.fd_id=detail.fd_parent_id ";
			}
			String sql = " SELECT main.fd_id FROM "+tableName+" main  " 
					+detailSql
					+ " LEFT JOIN km_review_main review on main.fd_id=review.fd_id  ";
			String where =" WHERE 1=1 and review.doc_status in ('20','30') "
					+ " and review.doc_creator_id=:docCreatorId and review.fd_template_id=:fdTemplateId ";//只查询已发布的单据and main.fd_date=:dataType and review.doc_status='30' 
			
			where+="and (("+startFieldName+"<=:startDate and "+endFieldName+">=:startDate) or "
					+ "("+startFieldName+"<=:endDate and "+endFieldName+">=:endDate)) ";
			if(StringUtil.isNull(startDate)){
				startDate="1970-01-01";
			}
			if(StringUtil.isNull(endDate)){
				endDate=DateUtil.convertDateToString(DateUtil.getDateQueue(), "yyyy-MM-dd HH:mm:ss");
			}
			sql+=where;
			System.out.println("mainSql:"+sql);
			Query query = getServiceImp(null).getBaseDao().getHibernateSession().createNativeQuery(sql);
			query.setParameter("docCreatorId", fdPersonId);
			query.setParameter("fdTemplateId", fdTemplateId);
			query.setParameter("startDate", startDate);
			query.setParameter("endDate", endDate);
			List list = query.list();
			int num=0;
			if(list!=null&&list.size()>0){
				num=list.size();
			}
			if(Double.valueOf(maxNum)>num){//判断限制提交数量是否大于已提交数量
				result="true";
				//如果提交数量校验完了，在校验这个
				
			}
			if(hourNum!=null&&!checkLeaveAmount(fdPersonId, leaveType, startDate, endDate,hourNum)){
				result="额度不足";
			}
			response.getWriter().write(result);
			return null;
		}

	    private ISysTimeLeaveAmountService sysTimeLeaveAmountService;

	    public ISysTimeLeaveAmountService getSysTimeLeaveAmountService() {
	        if (sysTimeLeaveAmountService == null) {
	            sysTimeLeaveAmountService = (ISysTimeLeaveAmountService) SpringBeanUtil.getBean("sysTimeLeaveAmountService");
	        }
	        return sysTimeLeaveAmountService;
	    }
	    private ISysAttendCategoryService sysAttendCategoryService;

	    public ISysAttendCategoryService getSysAttendCategoryService() {
	        if (sysAttendCategoryService == null) {
	        	sysAttendCategoryService = (ISysAttendCategoryService) SpringBeanUtil.getBean("sysAttendCategoryService");
	        }
	        return sysAttendCategoryService;
	    }
	    
	    /**
	     * 验证假期有效额度
	     * @param personId     请假人ID
	     * @param leaveType    假期类型
	     * @param leaveDetail 请假详情
	     * @param leaveRule 请假详情
	     * @description: 验证请假额度是否够请假
	     * @return: boolean true说明假期够 , false说明超出假期
	     * @author: 王京
	     * @throws Exception 
	     * @time: 2022/08/21
	     */
	    public boolean checkLeaveAmount(String personId, String leaveType,String startDateStr,String endDateStr,String hourNum) throws Exception {
	    	Date startDate=DateUtil.convertStringToDate(startDateStr, "yyyy-MM-dd HH:mm");
	    	Date endDate=DateUtil.convertStringToDate(endDateStr, "yyyy-MM-dd HH:mm");
	    	SysTimeLeaveDetail leaveDetail=new SysTimeLeaveDetail();
	    	leaveDetail.setFdStartTime(startDate);
	    	leaveDetail.setFdEndTime(endDate);
	    	SysAttendCategory sysAttendCategor=getSysAttendCategoryService().getCategoryInfo(UserUtil.getUser(),startDate,true);
	    	Float hourF=Float.valueOf(hourNum);
	    	Float fdTotalTime =(float) 7.5;//默认7.5小时一天
	    	if(sysAttendCategor!=null){
	    		fdTotalTime=sysAttendCategor.getFdTotalTime();
	    	}
	    	Float fdLeaveTime=hourF/fdTotalTime;
	    	leaveDetail.setFdLeaveTime(fdLeaveTime);//转化为天数
	    	 SysTimeLeaveRule leaveRule = AttendUtil.getLeaveRuleByType(Integer.valueOf(leaveType));
	        Calendar calendar = Calendar.getInstance();
	        calendar.setTime(new Date());
	        Integer nowYear = calendar.get(Calendar.YEAR);
	        //把当前年度和上一年的假期数据查询出来，不存在上上年度的数据
	        SysTimeLeaveAmountItem amountItem = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear, personId, leaveType);
	        if (amountItem == null) {
	            amountItem = getSysTimeLeaveAmountService().getLeaveAmountItem(nowYear - 1, personId, leaveType);
	        }
	        boolean checkLeave = true;
	        //假期可用
	        if (amountItem != null) {
	            //有效期内的上周期的剩余额度
	            Float lastRestDay = amountItem.getValidLastRestDay(leaveDetail,leaveRule);
	            Float restday = amountItem.getValidRestDay(leaveDetail, leaveRule);
	            //上周期和本有效时间范围内的时长大于等于本次扣减的时长
	            if (lastRestDay.floatValue() + restday.floatValue() >= leaveDetail.getFdLeaveTime()) {
	                checkLeave =true;
	            }else{
	                checkLeave =false;
	            }
	        }
	        return checkLeave;
	    }
	    
	    /**
	     * 判断单据是否能提交,外出申请
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public String canSubmitWC(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	String result="当前已存在重叠时间点流程，请确认后提交！";
//	    	String result="规定时间超过单据申请数，请检查!";
	    	response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String date=request.getParameter("date");//日期
			String startTime=request.getParameter("startTime");//开始时间
			String endTime=request.getParameter("endTime");
			String fdTemplateId=request.getParameter("fdTemplateId");
			String docCreatorId=UserUtil.getUser().getFdId();
			String maxNum=request.getParameter("maxNum");//限制提交数量
			String tableName=request.getParameter("tableName");//表名
			String startFieldName=request.getParameter("startFieldName");//时间开始字段名称
			String endFieldName=request.getParameter("endFieldName");//时间结束字段名称
			
			String sql = "SELECT main.fd_id FROM "+tableName+" main  "
					+ "LEFT JOIN km_review_main review on main.fd_id=review.fd_id  ";
			String where ="WHERE 1=1 and review.doc_status in ('20','30') "
					+ "and review.doc_creator_id=:docCreatorId and review.fd_template_id=:fdTemplateId ";//只查询已发布的单据and main.fd_date=:dataType and review.doc_status='30' 
			Date startTime1=DateUtil.convertStringToDate("1970-01-01 "+startTime+":00","yyyy-MM-dd HH:mm:ss");
			Date endTime1=DateUtil.convertStringToDate("1970-01-01 "+endTime+":00","yyyy-MM-dd HH:mm:ss");
			where+=" and DATE_FORMAT(fd_waiChuRiQi, '%Y-%m-%d') = :date "
					+ " and (("+startFieldName+"<:startTime and "+endFieldName+">:startTime) or "
					+ "("+startFieldName+"<:endTime and "+endFieldName+">:endTime)) ";
			if(StringUtil.isNull(startTime)){
				startTime="00:00";
			}
			if(StringUtil.isNull(endTime)){
				endTime=DateUtil.convertDateToString(DateUtil.getDateQueue(), "yyyy-MM-dd HH:mm:ss");
			}
			sql+=where;
			System.out.println("mainSql:"+sql);
			Query query = getServiceImp(null).getBaseDao().getHibernateSession().createNativeQuery(sql);
			query.setParameter("docCreatorId", docCreatorId);
			query.setParameter("fdTemplateId", fdTemplateId);
			query.setParameter("date", date);
			query.setParameter("startTime", startTime1);
			query.setParameter("endTime", endTime1);
			List list = query.list();
			int num=0;
			if(list!=null&&list.size()>0){
				num=list.size();
			}
			if(Double.valueOf(maxNum)>num){//判断限制提交数量是否大于已提交数量
				result="true";
			}
			response.getWriter().write(result);
			return null;
		}
	    
	    
	    private static ISysFormMainDataCustomListService iSysFormMainDataCustomListService;

	    public static ISysFormMainDataCustomListService getISysFormMainDataCustomListService() {
	        if (iSysFormMainDataCustomListService == null) {
	            iSysFormMainDataCustomListService = (ISysFormMainDataCustomListService) SpringBeanUtil.getBean("sysFormMainDataCustomListService");
	        }
	        return iSysFormMainDataCustomListService;
	    }
	    /**
	     * F06获取【费用归属法人机构】
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public String getGsrjg(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
	    	response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			String fdPersonId=request.getParameter("fdPersonId");
			String  key=request.getParameter("key");
			String result="";
			String sql="select fd_affiliated_company from hr_staff_person_info "
					+ "where fd_id='"+fdPersonId+"'";
			List<String> list=this.getServiceImp(request).getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).list();
			if(list!=null&&list.size()>0){
				String fdValue=list.get(0);
				 HQLInfo hqlInfo = new HQLInfo();
			        hqlInfo.setJoinBlock(" inner join SysFormMainDataCustom sysFormMainDataCustom on sysFormMainDataCustom.fdId = sysFormMainDataCustomList.sysFormMainDataCustom.fdId");
			        hqlInfo.setWhereBlock("sysFormMainDataCustom.fdKey =:key and sysFormMainDataCustomList.fdValue=:fdValue");
			        hqlInfo.setParameter("key", key);
			        hqlInfo.setParameter("fdValue", fdValue);
			        List<SysFormMainDataCustomList> lists = getISysFormMainDataCustomListService().findList(hqlInfo);
			        if(lists!=null&&lists.size()>0){
			        	result=lists.get(0).getFdValueText();
			        }
			}
			response.getWriter().write(result);
			return null;
	    }
	    
	    
	    /**
	     * F06获取【费用类型】
	     * @param mapping
	     * @param form
	     * @param request
	     * @param response
	     * @return
	     * @throws Exception
	     */
	    public String getFylx(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
			String sql="SELECT fd_name,fd_code FROM eop_basedata_expense_item ";
			List<Object[]> list=this.getServiceImp(request).getBaseDao().getHibernateSession().createSQLQuery(sql.toString()).list();
			JSONObject json=new JSONObject();
			if(list!=null&&list.size()>0){
				for (Object[] objs : list) {
					if(objs[0]!=null&&objs[1]!=null){
						json.put(objs[1].toString(), objs[0].toString());
					}
				}
			}
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html;charset=UTF-8");
			response.getWriter().write(json.toString());
			return null;
	    }
}
