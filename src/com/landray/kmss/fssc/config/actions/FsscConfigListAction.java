package com.landray.kmss.fssc.config.actions;

import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.fssc.config.forms.FsscConfigListForm;
import com.landray.kmss.fssc.config.model.FsscConfigList;
import com.landray.kmss.fssc.config.service.IFsscConfigListService;
import com.landray.kmss.sys.config.loader.ConfigLocationsUtil;
import com.landray.kmss.util.ArrayUtil;
import com.landray.kmss.util.HQLHelper;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

/**
  * 物资清单 Action
  */
public class FsscConfigListAction extends ExtendAction {

    private IFsscConfigListService fsscConfigListService;

    public IBaseService getServiceImp(HttpServletRequest request) {
        if (fsscConfigListService == null) {
            fsscConfigListService = (IFsscConfigListService) getBean("fsscConfigListService");
        }
        return fsscConfigListService;
    }

    public void changeFindPageHQLInfo(HttpServletRequest request, HQLInfo hqlInfo) throws Exception {
        HQLHelper.by(request).buildHQLInfo(hqlInfo, FsscConfigList.class);
        hqlInfo.setOrderBy(getFindPageOrderBy(request, hqlInfo.getOrderBy()));
        com.landray.kmss.fssc.config.util.FsscConfigUtil.buildHqlInfoDate(hqlInfo, request, com.landray.kmss.fssc.config.model.FsscConfigList.class);
        com.landray.kmss.fssc.config.util.FsscConfigUtil.buildHqlInfoModel(hqlInfo, request);
    }

    public ActionForm createNewForm(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
        FsscConfigListForm fsscConfigListForm = (FsscConfigListForm) super.createNewForm(mapping, form, request, response);
        ((IFsscConfigListService) getServiceImp(request)).initFormSetting((IExtendForm) form, new RequestContext(request));
        return fsscConfigListForm;
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
				List<String> list = ((IFsscConfigListService) getServiceImp(request))
						.addInfoByImport((FsscConfigListForm) form, request);
				
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
	 
	 /**
		 * 模板下载
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
							+ "/fssc/config/common/import_list.xlsx";
					Workbook workbook = new XSSFWorkbook(new FileInputStream(filePath));
					final String fileName = "物资清单导入模板.xlsx";
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
}
