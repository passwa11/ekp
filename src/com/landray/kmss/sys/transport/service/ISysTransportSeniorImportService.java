package com.landray.kmss.sys.transport.service;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.forms.IExtendForm;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.transport.model.SysTransportImportConfig;
import com.landray.kmss.sys.transport.service.spring.ImportContext;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionMapping;
import com.landray.kmss.web.upload.FormFile;
import org.apache.poi.ss.usermodel.Row;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;

public interface ISysTransportSeniorImportService extends ISysTransportImportService {
    public List getExcelRenderPageList(String key, String maxLimitedNum);
    public Boolean clearRenderCache(String key);
    public void updateImportData(IExtendForm form, RequestContext requestContext) throws Exception;
}
