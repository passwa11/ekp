package com.landray.kmss.fssc.config.service.spring;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.convertor.ConvertorContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.fssc.common.util.ExcelFileGenerator;
import com.landray.kmss.fssc.config.forms.FsscConfigScoreForm;
import com.landray.kmss.fssc.config.model.FsscConfigScore;
import com.landray.kmss.fssc.config.model.FsscConfigScoreDetail;
import com.landray.kmss.fssc.config.service.IFsscConfigScoreDetailService;
import com.landray.kmss.fssc.config.service.IFsscConfigScoreService;
import com.landray.kmss.fssc.config.util.ExportExcelBean;
import com.landray.kmss.fssc.config.util.ExportExcelUtil;
import com.landray.kmss.fssc.config.util.FsscConfigUtil;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.sys.notify.interfaces.ISysNotifyMainCoreService;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.sys.organization.service.ISysOrgPersonService;
import com.landray.kmss.sys.transport.service.spring.ImportUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import com.sunbor.web.tag.Page;

import net.sf.json.JSONObject;

/**
  * 点赞积分配置 服务实现
  */
public class FsscConfigScoreServiceImp extends ExtendDataServiceImp implements IFsscConfigScoreService {

    private ISysNotifyMainCoreService sysNotifyMainCoreService;

    public IBaseModel convertBizFormToModel(IExtendForm form, IBaseModel model, ConvertorContext context) throws Exception {
        model = super.convertBizFormToModel(form, model, context);
        if (model instanceof FsscConfigScore) {
            FsscConfigScore fsscConfigScore = (FsscConfigScore) model;
        }
        return model;
    }

    public IBaseModel initBizModelSetting(RequestContext requestContext) throws Exception {
        FsscConfigScore fsscConfigScore = new FsscConfigScore();
        fsscConfigScore.setDocCreateTime(new Date());
        fsscConfigScore.setDocCreator(UserUtil.getUser());
        FsscConfigUtil.initModelFromRequest(fsscConfigScore, requestContext);
        return fsscConfigScore;
    }

    public void initCoreServiceFormSetting(IExtendForm form, IBaseModel model, RequestContext requestContext) throws Exception {
        FsscConfigScore fsscConfigScore = (FsscConfigScore) model;
    }

    public ISysNotifyMainCoreService getSysNotifyMainCoreService() {
        if (sysNotifyMainCoreService == null) {
            sysNotifyMainCoreService = (ISysNotifyMainCoreService) SpringBeanUtil.getBean("sysNotifyMainCoreService");
        }
        return sysNotifyMainCoreService;
    }
    
    
    
    @Override
	public List<String> addInfoByImport(FsscConfigScoreForm form, HttpServletRequest request) throws Exception {
		if(form == null || form.getFormFile() == null
				|| form.getFormFile().getInputStream() == null)
			throw new Exception("文件解析失败！");
		
		// 读取文件
		Workbook workbook = WorkbookFactory.create(form.getFormFile().getInputStream());
		List<String> list = new ArrayList<String>();
		// 读取excel文件内容
		Sheet sheet = workbook.getSheetAt(0);
		for (int j = 1; j < sheet.getPhysicalNumberOfRows(); j++) {
			try{
				getExpert(sheet.getRow(j),request);
			}catch(Exception e){
				e.printStackTrace();
				list.add("第【" + j + "】行数据解析失败:"+ e.getMessage());
			}
		}
		
		return list;
		
	}
    
    private ISysOrgPersonService sysOrgPersonService;
    
    public ISysOrgPersonService getSysOrgPersonService() {
    	if(sysOrgPersonService==null){
    		sysOrgPersonService=(ISysOrgPersonService) SpringBeanUtil.getBean("sysOrgPersonService");
    	}
		return sysOrgPersonService;
	}

    
    /**
	 * 获取导入的专家信息存进数据库中																				
	 * @param row
	 * @return
	 * @throws Exception
	 */
	private void getExpert(Row row,HttpServletRequest request) throws Exception{
		FsscConfigScoreForm main = new FsscConfigScoreForm();
		HQLInfo hqlInfo1=new HQLInfo();
		StringBuffer whereBlock = null;
		whereBlock = new StringBuffer("1 = 1");
    	for (Cell cell : row) {
    		int columnIndex = cell.getColumnIndex();
//    		if(columnIndex!=7)
//    			cell.setCellType(1);
    		String cellValue = ImportUtil.getCellValue(cell);
    		if(StringUtil.isNull(cellValue)){
    			throw new Exception("字段不能为空");
    		}
    		switch (columnIndex) {
			case 0://人员
				String fdLoginName=cellValue;
				HQLInfo hqlInfo=new HQLInfo();
				hqlInfo.setWhereBlock("fdLoginName =:fdLoginName");
				hqlInfo.setParameter("fdLoginName", fdLoginName);
				whereBlock.append(" and fdPerson.fdId =:fdLoginName");
				List<SysOrgPerson> list=getSysOrgPersonService().findList(hqlInfo);
				hqlInfo1.setParameter("fdLoginName", list.get(0).getFdId());
				main.setFdPersonId(list.get(0).getFdId());
				break;
			case 1://月份
				main.setFdMonth(cellValue);
				whereBlock.append(" and fdMonth =:fdMonth");
				hqlInfo1.setParameter("fdMonth", cellValue);
				break;
			case 2://月份
				main.setFdYear(cellValue);
				whereBlock.append(" and fdYear =:fdYear");
				hqlInfo1.setParameter("fdYear", cellValue);
				break;
			case 3://初始积分
				main.setFdScoreInit(cellValue);
				break;
			}
		}
    	hqlInfo1.setWhereBlock(whereBlock.toString());
    	List<FsscConfigScore> list = findList(hqlInfo1);
    	if(list.size()>0){
    		FsscConfigScore main1 = list.get(0);
    		main1.setFdScoreInit(Integer.parseInt(main.getFdScoreInit()));
    		update(main1);
    	}else{
	    	main.setFdId(IDGenerator.generateID());
	    	add(main, new RequestContext());
    	}
	}
	private  IFsscConfigScoreDetailService fsscConfigScoreDetailService;

	public IFsscConfigScoreDetailService getFsscConfigScoreDetailService() {
		if(fsscConfigScoreDetailService==null){
			fsscConfigScoreDetailService=(IFsscConfigScoreDetailService) SpringBeanUtil.getBean("fsscConfigScoreDetailService");
		}
		return fsscConfigScoreDetailService;
		}
	@Override
	public Page getDataPage(HttpServletRequest request, JSONObject params) throws Exception {
		Integer pageno=(Integer)params.get("pageno");
		pageno=pageno==0?1:pageno;
		Integer rowsize=(Integer)params.get("rowsize");
		HQLInfo dataInfo=new HQLInfo();
		dataInfo.setPageNo(pageno);
		dataInfo.setRowSize(rowsize);
		initHqlInfo(dataInfo, request);
//		List<FsscConfigScoreDetail> dataList=getFsscConfigScoreDetailService().findList(dataInfo);
		Page page=getFsscConfigScoreDetailService().findPage(dataInfo);
		Map<String, Integer> rowspanMap=new HashMap<String, Integer>();
		Map<String, Integer> scoreMap=new HashMap<String, Integer>();

		page.setList(formatData(page.getList(),rowspanMap,scoreMap));
        request.setAttribute("rowspanMap", rowspanMap);
        request.setAttribute("scoreMap", scoreMap);

		return page;
	}
	
	private HQLInfo initHqlInfo(HQLInfo dataInfo,HttpServletRequest request){
		String where =" 1=1 ";
		String fdAddScorePersonId=request.getParameter("fdAddScorePersonId");//被点赞人
		if(StringUtil.isNotNull(fdAddScorePersonId)){
			where+=" and fsscConfigScoreDetail.fdAddScorePerson.fdId=:fdAddScorePersonId";
			dataInfo.setParameter("fdAddScorePersonId", fdAddScorePersonId);
		}
		String docCreateTimeStart=request.getParameter("docCreateTimeStart");//点赞时间
		if(StringUtil.isNotNull(docCreateTimeStart)){
			where+=" and fsscConfigScoreDetail.docCreateTime>=:docCreateTimeStart";
			dataInfo.setParameter("docCreateTimeStart", DateUtil.convertStringToDate(docCreateTimeStart));
		}
		String docCreateTimeEnd=request.getParameter("docCreateTimeEnd");//点赞时间
		if(StringUtil.isNotNull(docCreateTimeStart)){
			where+=" and fsscConfigScoreDetail.docCreateTime<=:docCreateTimeEnd";
			dataInfo.setParameter("docCreateTimeEnd", DateUtil.convertStringToDate(docCreateTimeEnd+" 23:59:59"));
		}
		where+=" and fsscConfigScoreDetail.flag=1";
		dataInfo.setWhereBlock(where);
		dataInfo.setOrderBy(" fsscConfigScoreDetail.fdAddScorePerson,fsscConfigScoreDetail.docCreateTime");
		return dataInfo;
	}
	private List formatData(List<FsscConfigScoreDetail> list,Map<String, Integer> rowspanMap,Map<String, Integer> scoreMap){
		// 初始化格式化时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        int rowspan=1;
        int score=0;
		List result=new ArrayList<>();
		for (FsscConfigScoreDetail detail : list) {
			Object[] obj=new Object[6];
			obj[1]=detail.getDocCreator().getFdName();//建单人
			obj[2]=simpleDateFormat.format(detail.getDocCreateTime());//申请日期
			obj[3]=detail.getFdDesc();//详细说明
			obj[4]=detail.getFdScoreUse();//分数
			obj[5]=detail.getFdAddScorePerson().getFdName();//被点赞人
			String key="1"+obj[5].toString();
			if(rowspanMap.get(key)==null){
				rowspan=1;
				score=detail.getFdScoreUse();
			}else{
				rowspan++;
				score+=detail.getFdScoreUse();
			}
			rowspanMap.put(key, rowspan);
			scoreMap.put(key, score);
			obj[0]=rowspan;//第几条数据
			result.add(obj);
		}
		return result;
		
	}
	@Override
	public HSSFWorkbook getExportDataList(HttpServletRequest request,HttpServletResponse response) throws Exception {
		 String exportFileName = "测试数据";
         ExportExcelUtil.updateNameUnicode(request, response, exportFileName);
         OutputStream out = response.getOutputStream();
		//建单人	申请日期	详细说明	分数	被点赞人	总分
		String[] modeArr={"序号","建单人","申请日期","详细说明","分数","被点赞人","总分"};
		String[] keyArr={"row","person","date","desc","score","addScorePerson","totalScore"};
		String[] hbkeyArr={"被点赞人","总分"};

		HQLInfo dataInfo=new HQLInfo();
		initHqlInfo(dataInfo, request);
		List<FsscConfigScoreDetail> list=getFsscConfigScoreDetailService().findList(dataInfo);
		Map<String, Integer> rowspanMap=new HashMap<String, Integer>();
		Map<String, Integer> scoreMap=new HashMap<String, Integer>();
		List<Object[]> objList=formatData(list,rowspanMap,scoreMap);
		List<Map<String, Object>> dataList=new ArrayList<>();
		int row=0;
		for (Object[] obj: objList) {
			Map<String, Object> map=new HashMap<>();
			map.put(keyArr[0], ++row);
			for (int i = 1; i < obj.length; i++) {
				map.put(keyArr[i], obj[i]);
			}
			map.put(keyArr[6], scoreMap.get("1"+obj[5]));

			dataList.add(map);
		}
		
		
		ExportExcelBean excelBean=new ExportExcelBean();
		List<String> titleCn = Arrays.asList(modeArr);
        List<String> valueKey =  Arrays.asList(keyArr);
        excelBean.setHeaders(titleCn);
        excelBean.setTitle("测试导出信息1");
        excelBean.setSheetName("测试sheet");
        excelBean.setKeys(valueKey);
        excelBean.setVerticalMergerColumnHeaders(Arrays.asList(hbkeyArr));
        // 设置sheet的密码，仅作用于当前sheet页
//		excelBean.setProtectSheet("123456");
		excelBean.setDataList(dataList);
		ExportExcelUtil.exportExcel(excelBean, out);
        return null;
	
	}
}
