package com.landray.kmss.fssc.expense.service.spring;

import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.eop.basedata.model.EopBasedataCompany;
import com.landray.kmss.fssc.expense.service.IFsscExpenseTempService;
import com.landray.kmss.util.SpringBeanUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.hibernate.query.Query;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.dao.HQLParameter;
import com.landray.kmss.common.service.IXMLDataBean;
import com.landray.kmss.eop.basedata.imp.EopBasedataImportUtil;
import com.landray.kmss.eop.basedata.model.EopBasedataTaxRate;
import com.landray.kmss.fssc.common.util.FsscCommonUtil;
import com.landray.kmss.fssc.common.util.FsscNumberUtil;
import com.landray.kmss.fssc.expense.model.FsscExpenseDetail;
import com.landray.kmss.fssc.expense.model.FsscExpenseTemp;
import com.landray.kmss.fssc.expense.model.FsscExpenseTempDetail;
import com.landray.kmss.fssc.expense.service.IFsscExpenseDetailService;
import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.metadata.interfaces.ExtendDataServiceImp;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class FsscExpenseDetailServiceImp extends ExtendDataServiceImp implements IFsscExpenseDetailService,IXMLDataBean {

	private IFsscExpenseTempService fsscExpenseTempService;

	public IFsscExpenseTempService getFsscExpenseTempService() {
		if(fsscExpenseTempService==null){
			fsscExpenseTempService=(IFsscExpenseTempService) SpringBeanUtil.getBean("fsscExpenseTempService");
		}
		return fsscExpenseTempService;
	}

	@Override
	public void exportDetail(HQLInfo hqlInfo, HttpServletResponse response) throws Exception {
		String hql = "from "+hqlInfo.getFromBlock()+" where "+hqlInfo.getWhereBlock();
		Query query = getBaseDao().getHibernateSession().createQuery(hql);
		for(HQLParameter param:hqlInfo.getParameterList()){
			query.setParameter(param.getName(), param.getValue());
		}
		List<FsscExpenseDetail> list = query.list();
		String filename = ResourceUtil.getString("py.BaoXiaoTaiZhang","fssc-expense");
		filename = new String(filename.getBytes("GBK"), "ISO-8859-1") + ".xls";
		OutputStream os = response.getOutputStream();
		response.reset();
		response.setContentType("application/vnd.ms-excel; charset=UTF-8");
		response.addHeader("Content-Disposition", "attachment;filename="
				+ filename);
		Workbook workBook = new HSSFWorkbook();
		String sheetName = new String(filename.getBytes("ISO8859-1"), "GBK");
		String[] cols = null;
		Sheet sheet = null;
		if(FsscCommonUtil.checkHasModule("/fssc/proapp/")) {
			cols = ResourceUtil.getString("py.BaoXiaoTaiZhang.columns.new","fssc-expense").split(";");
			sheet = workBook.createSheet(sheetName);
			for (int i = 0; i <= cols.length; i++) {
				sheet.setColumnWidth((short) i, (short) 4000);
			}
		}else {
			cols = ResourceUtil.getString("py.BaoXiaoTaiZhang.columns","fssc-expense").split(";");
			sheet = workBook.createSheet(sheetName);
			for (int i = 0; i <= cols.length; i++) {
				sheet.setColumnWidth((short) i, (short) 4000);
			}
		}
		CellStyle style = EopBasedataImportUtil.getTitleStyle(workBook);
		Row row = sheet.createRow(0);
		Cell cell = row.createCell(0);
		cell.setCellValue(ResourceUtil.getString("page.serial"));
		cell.setCellStyle(style);
		for (int i = 0; i < cols.length; i++) {
			cell = row.createCell(i+1);
			cell.setCellValue(cols[i]);
			cell.setCellStyle(style);
		}
		int k=0;
		CellStyle content = EopBasedataImportUtil.getNormalStyle(workBook);
		for(FsscExpenseDetail detail:list){
			row = sheet.createRow(++k);
			
			cell = row.createCell(0);
			cell.setCellValue(k);
			cell.setCellStyle(content);
			
			cell = row.createCell(1);
			cell.setCellValue(detail.getDocMain().getDocSubject());
			cell.setCellStyle(content);
			
			cell = row.createCell(2);
			cell.setCellValue(detail.getDocMain().getDocNumber());
			cell.setCellStyle(content);
			
			cell = row.createCell(3);
			cell.setCellValue(detail.getFdCompany()==null?detail.getDocMain().getFdCompany().getFdName():detail.getFdCompany().getFdName());
			cell.setCellStyle(content);
			
			cell = row.createCell(4);
			cell.setCellValue(detail.getDocMain().getDocTemplate().getFdName());
			cell.setCellStyle(content);
			
			cell = row.createCell(5);
			cell.setCellValue(detail.getFdProject()!=null?detail.getFdProject().getFdName():"");
			cell.setCellStyle(content);
			
			if(FsscCommonUtil.checkHasModule("/fssc/proapp/")) {
				cell = row.createCell(6);
				cell.setCellValue(detail.getDocMain().getFdProappName());
				cell.setCellStyle(content);
				
				cell = row.createCell(7);
				cell.setCellValue(detail.getFdExpenseItem().getFdName());
				cell.setCellStyle(content);
				
				cell = row.createCell(8);
				cell.setCellValue(detail.getFdApprovedStandardMoney()==null?detail.getFdStandardMoney():detail.getFdApprovedStandardMoney());
				cell.setCellStyle(content);
				
				cell = row.createCell(9);
				cell.setCellValue(EnumerationTypeUtil.getColumnEnumsLabel("common_status", detail.getDocMain().getDocStatus()));
				cell.setCellStyle(content);
			}else {
				cell = row.createCell(6);
				cell.setCellValue(detail.getFdExpenseItem().getFdName());
				cell.setCellStyle(content);
				
				cell = row.createCell(7);
				cell.setCellValue(detail.getFdApprovedStandardMoney()==null?detail.getFdStandardMoney():detail.getFdApprovedStandardMoney());
				cell.setCellStyle(content);
				
				cell = row.createCell(8);
				cell.setCellValue(EnumerationTypeUtil.getColumnEnumsLabel("common_status", detail.getDocMain().getDocStatus()));
				cell.setCellStyle(content);
			}
		}
		workBook.write(os);
		os.flush();
		os.close();
	}

	@Override
	public List getDataList(RequestContext requestInfo) throws Exception {
		List<Map<String,Object>> rtn = new ArrayList<Map<String,Object>>( );
		String fdNoteId = requestInfo.getParameter("fdNoteId");
		String fdMainId = requestInfo.getParameter("fdMainId");
		String fdCompanyId = requestInfo.getParameter("fdCompanyId");
		if(StringUtil.isNotNull(fdNoteId)){
			StringBuilder hql = new StringBuilder();
			hql.append("select invoie.fdInvoiceNumber,invoie.fdInvoiceCode,invoie.fdBillingDate,invoie.fdInvoiceType,");
			hql.append("invoie.fdJshj,invoie.fdTotalAmount,invoie.fdTotalTax,note.fdExpenseItemId,note.fdExpenseItemName,invoie.fdJym,invoie.fdPurchaserName,invoie.fdPurchaserTaxNo ");
			hql.append("from com.landray.kmss.fssc.ledger.model.FsscLedgerInvoice invoie,");
			hql.append("com.landray.kmss.fssc.mobile.model.FsscMobileNote note ");
			hql.append("where note.fdId=invoie.fdNoteId and invoie.fdNoteId=:fdNoteId");
			List<Object[]> list = getBaseDao().getHibernateSession().createQuery(hql.toString()).setParameter("fdNoteId", fdNoteId).list();
			if(ArrayUtil.isEmpty(list)){
				return rtn;
			}
			FsscExpenseTemp temp = new FsscExpenseTemp();
			temp.setFdMainId(fdMainId);
			String fdExpenseTempDetailIds = "";
			List<FsscExpenseTempDetail> detailList = new ArrayList<FsscExpenseTempDetail>();
			JSONArray invoices = new JSONArray();
			Double fdTotalInvoiceMoney=0.0;
			for(Object[] obj:list){
				String fdJshj = (String) obj[4];
				fdJshj = fdJshj==null?"0.0":fdJshj;
				Double fdInvoiceMoney = Double.valueOf(fdJshj);
				Double fdTaxMoney = (Double) obj[6];
				fdTaxMoney = fdTaxMoney==null?0d:fdTaxMoney;
				Double fdNoTaxMoney = (Double) obj[5];
				fdNoTaxMoney = fdNoTaxMoney==null?0d:fdNoTaxMoney;
				JSONObject invoice = new JSONObject();
				invoice.put("fdInvoiceCode",(String) obj[1]);
				invoice.put("fdInvoiceNumber",(String) obj[0]);
				invoice.put("fdInvoiceType",(String) obj[3]);
				Date fdInvoiceDate = (Date) obj[2];
				if(fdInvoiceDate!=null){
					invoice.put("fdInvoiceDate",DateUtil.convertDateToString(fdInvoiceDate, DateUtil.PATTERN_DATE));
				}
				invoice.put("fdInvoiceMoney",fdInvoiceMoney);
				invoice.put("fdTaxMoney",fdTaxMoney);
				invoice.put("fdNoTaxMoney",fdNoTaxMoney);
				invoice.put("fdCheckCode",obj[9]!=null?String.valueOf(obj[9]):"");
				invoice.put("fdPurchName",obj[10]!=null?String.valueOf(obj[10]):"");
				invoice.put("fdTaxNumber",obj[11]!=null?String.valueOf(obj[11]):"");
				if(StringUtil.isNotNull(fdCompanyId)){
					invoice.put("fdIsCurrent",getFsscExpenseTempService().getIsCurrent(fdCompanyId,String.valueOf(obj[11]),String.valueOf(obj[10]),String.valueOf(obj[3])));
				}
				invoices.add(invoice);
				fdTotalInvoiceMoney=FsscNumberUtil.getAddition(fdInvoiceMoney, fdTotalInvoiceMoney);
				FsscExpenseTempDetail detail = new FsscExpenseTempDetail();
				detail.setDocMain(temp);
				detail.setFdCompanyId(fdCompanyId);
				detail.setFdExpenseTypeId((String) obj[7]);
				detail.setFdExpenseTypeName((String) obj[8]);
				detail.setFdCheckCode(obj[9]!=null?String.valueOf(obj[9]):"");
				detail.setFdPurchName(obj[10]!=null?String.valueOf(obj[10]):"");
				detail.setFdTaxNumber(obj[11]!=null?String.valueOf(obj[11]):"");
				detail.setFdInvoiceCode((String) obj[1]);
				detail.setFdInvoiceDate((Date) obj[2]);
				detail.setFdInvoiceNumber((String) obj[0]);
				detail.setFdInvoiceMoney(fdInvoiceMoney);
				detail.setFdInvoiceType((String) obj[3]);
				detail.setFdTaxMoney(fdTaxMoney);
				detail.setFdNoTaxMoney(fdNoTaxMoney);
				Double fdTaxRate = FsscNumberUtil.getMultiplication(FsscNumberUtil.getDivide(fdTaxMoney, fdNoTaxMoney), 100.0);
				detail.setFdTax(String.valueOf(fdTaxRate));
				List<String> rlist = getBaseDao().getHibernateSession().createQuery("select fdId from "+EopBasedataTaxRate.class.getName()+" where fdRate=:fdRate")
						.setParameter("fdRate", fdTaxRate).list();
				if(!ArrayUtil.isEmpty(rlist)){
					detail.setFdTaxId(rlist.get(0));
				}
				if(StringUtil.isNotNull(fdExpenseTempDetailIds)){
					fdExpenseTempDetailIds+=";";
				}
				fdExpenseTempDetailIds+=detail.getFdId();
				detailList.add(detail);
			}
			temp.setFdInvoiceListTemp(detailList);
			getBaseDao().getHibernateSession().save(temp);
			List<SysAttMain> addList = new ArrayList<SysAttMain>();
			//查询关联附件
			List<SysAttMain> attList = getBaseDao().getHibernateSession().createQuery("from "+SysAttMain.class.getName()+" where fdModelId = :fdModelId")
					.setParameter("fdModelId", fdNoteId).list();
			for(SysAttMain att:attList){
				SysAttMain attNew = new SysAttMain();
				attNew.setDocCreateTime(new Date());
				attNew.setFdAttType(att.getFdAttType());
				attNew.setFdContentType(att.getFdContentType());
				attNew.setFdCreatorId(att.getFdCreatorId());
				attNew.setFdData(att.getFdData());
				attNew.setFdDataId(att.getFdDataId());
				attNew.setFdFileId(att.getFdFileId());
				attNew.setFdFileName(att.getFdFileName());
				attNew.setFdFilePath(att.getFdFilePath());
				attNew.setFdKey("attInvoice");
				attNew.setFdModelId(temp.getFdId());
				attNew.setFdModelName(FsscExpenseTemp.class.getName());
				attNew.setFdSize(att.getFdSize());
				attNew.setInputStream(att.getInputStream());
				addList.add(attNew);
			}
			if(FsscCommonUtil.checkHasModule("/fssc/ocr/")) {
				hql = new StringBuilder();
				hql.append("select distinct att from com.landray.kmss.sys.attachment.model.SysAttMain att,");
				hql.append("com.landray.kmss.fssc.ocr.model.FsscOcrInvoice inv ");
				hql.append("where att.fdModelId=inv.fdInfoId and inv.fdNoteId=:fdNoteId ");
				attList = getBaseDao().getHibernateSession().createQuery(hql.toString())
						.setParameter("fdNoteId", fdNoteId).list();
				for(SysAttMain att:attList){
					SysAttMain attNew = new SysAttMain();
					attNew.setDocCreateTime(new Date());
					attNew.setFdAttType(att.getFdAttType());
					attNew.setFdContentType(att.getFdContentType());
					attNew.setFdCreatorId(att.getFdCreatorId());
					attNew.setFdData(att.getFdData());
					attNew.setFdDataId(att.getFdDataId());
					attNew.setFdFileId(att.getFdFileId());
					attNew.setFdFileName(att.getFdFileName());
					attNew.setFdFilePath(att.getFdFilePath());
					attNew.setFdKey("attInvoice");
					attNew.setFdModelId(temp.getFdId());
					attNew.setFdModelName(FsscExpenseTemp.class.getName());
					attNew.setFdSize(att.getFdSize());
					attNew.setInputStream(att.getInputStream());
					addList.add(attNew);
				}
			}
			if(FsscCommonUtil.checkHasModule("/fssc/iqubic/")) {
				hql = new StringBuilder();
				hql.append("select distinct att from com.landray.kmss.sys.attachment.model.SysAttMain att,");
				hql.append("com.landray.kmss.fssc.iqubic.model.FsscIqubicInvoice inv ");
				hql.append("where att.fdModelId=inv.fdMainId and inv.fdNoteId=:fdNoteId ");
				attList = getBaseDao().getHibernateSession().createQuery(hql.toString())
						.setParameter("fdNoteId", fdNoteId).list();
				for(SysAttMain att:attList){
					SysAttMain attNew = new SysAttMain();
					attNew.setDocCreateTime(new Date());
					attNew.setFdAttType(att.getFdAttType());
					attNew.setFdContentType(att.getFdContentType());
					attNew.setFdCreatorId(att.getFdCreatorId());
					attNew.setFdData(att.getFdData());
					attNew.setFdDataId(att.getFdDataId());
					attNew.setFdFileId(att.getFdFileId());
					attNew.setFdFileName(att.getFdFileName());
					attNew.setFdFilePath(att.getFdFilePath());
					attNew.setFdKey("attInvoice");
					attNew.setFdModelId(temp.getFdId());
					attNew.setFdModelName(FsscExpenseTemp.class.getName());
					attNew.setFdSize(att.getFdSize());
					attNew.setInputStream(att.getInputStream());
					addList.add(attNew);
				}
			}
			if(!ArrayUtil.isEmpty(addList)){
				getBaseDao().saveOrUpdateAll(addList);
			}
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("fdTempId", temp.getFdId());
			map.put("fdDetailId", fdExpenseTempDetailIds);
			map.put("invoices", invoices.toString());
			if(fdTotalInvoiceMoney>0) {
				map.put("fdInvoiceMoney", new DecimalFormat("0.00").format(FsscNumberUtil.doubleToUp(fdTotalInvoiceMoney)));
			}
			rtn.add(map);
		}
		return rtn;
	}
}
