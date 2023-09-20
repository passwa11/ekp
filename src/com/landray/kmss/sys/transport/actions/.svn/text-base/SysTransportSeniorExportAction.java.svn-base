package com.landray.kmss.sys.transport.actions;

import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.transport.service.ISysListExportService;
import com.landray.kmss.sys.transport.service.ISysTransportSeniorExportService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;


public class SysTransportSeniorExportAction extends SysTransportExportAction {
	private ISysTransportSeniorExportService sysTransportSeniorExportService;

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return getService();
	}

	private ISysTransportSeniorExportService getService() {
		if (sysTransportSeniorExportService == null) {
            sysTransportSeniorExportService = (ISysTransportSeniorExportService) getBean("sysTransportSeniorExportService");
        }
		return sysTransportSeniorExportService;
	}

	protected ISysListExportService sysListExportService;

	@Override
	protected ISysListExportService getSysListExportService(HttpServletRequest request) {
		if(sysListExportService == null){
			sysListExportService = (ISysListExportService)getBean("sysListExportService");
		}
		return sysListExportService;
	}
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	public ActionForward exportDataResult(ActionMapping mapping, ActionForm form,
											 HttpServletRequest request, HttpServletResponse response) throws Exception{

		Locale locale = request.getLocale();
		getService().detailsTableExportData(request, response,
				ResourceUtil.getLocaleByUser());
		return null;
	}

	/**
	 * 明细表的下载模板
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@Override
	public ActionForward deTableExportTemplate(ActionMapping mapping,
											   ActionForm form, HttpServletRequest request,
											   HttpServletResponse response) throws Exception {
		Locale locale = request.getLocale();
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		getService().deTableDownloadTemplate(request, response, locale);
		return null;

	}

}
