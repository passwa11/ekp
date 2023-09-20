package com.landray.kmss.sys.ui.actions;

import java.io.OutputStream;
import java.util.Enumeration;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.landray.kmss.common.actions.BaseAction;
import com.landray.kmss.sys.log.util.UserOperHelper;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

public class SysUiQrcode extends BaseAction {

	public ActionForward getQrcode(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String contents = request.getParameter("contents");
		if (StringUtil.isNull(contents) || "undefined".equals(contents)) {
			return null;
		}
		//遍历这个参数的原因是原url带着contents为完整路径，但是在这里getParameter("contents")会因为切割&字符缺失参数，下面利用循环补全参数
		Enumeration paramNames = request.getParameterNames(); 
		while (paramNames.hasMoreElements()) { 
		   String paramName = (String) paramNames.nextElement(); 
		 
		   String[] paramValues = request.getParameterValues(paramName); 
		   if (paramValues.length == 1) { 
			   	String paramValue = paramValues[0]; 
			   	if (StringUtil.isNotNull(paramValue)&&!("contents".equalsIgnoreCase(paramName))&&!("width".equalsIgnoreCase(paramName))
			    		&&!("height".equalsIgnoreCase(paramName))&&!("method".equalsIgnoreCase(paramName))) {
				     contents = contents + "&"+ paramName + "=" + paramValue ;
			    } 
		   } 
		 }  
		Hashtable<EncodeHintType, Object> hints = new Hashtable<EncodeHintType, Object>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
        hints.put(EncodeHintType.MARGIN, 0);   //设置白边为0
		String width = request.getParameter("width");
		int w = StringUtil.isNull(width) ? 260 : Integer.parseInt(width);
		String height = request.getParameter("height");
		int h = StringUtil.isNull(height) ? 260 : Integer.parseInt(height);
		BitMatrix bitMatrix = new MultiFormatWriter().encode(contents, BarcodeFormat.QR_CODE, w, h,hints);//hints为生成条形码时的一些配置,此项可选  
		bitMatrix = deleteWhite(bitMatrix);
		if (UserOperHelper.allowLogOper("Base_UrlParam", null)) {
			UserOperHelper.setModelNameAndModelDesc(null,
					ResourceUtil.getString("sys-admin:home.nav.sysAdmin")
							+ "(" + ResourceUtil
									.getString("sys-ui:ui.qrcode.getQrcode")
							+ ")");
		}
		OutputStream stream = response.getOutputStream();
		MatrixToImageWriter.writeToStream(bitMatrix, "png", stream);
		return null;
	}
	
	private static BitMatrix deleteWhite(BitMatrix matrix) {
        int[] rec = matrix.getEnclosingRectangle();
        int resWidth = rec[2] + 1;
        int resHeight = rec[3] + 1;
 
        BitMatrix resMatrix = new BitMatrix(resWidth, resHeight);
        resMatrix.clear();
        for (int i = 0; i < resWidth; i++) {
            for (int j = 0; j < resHeight; j++) {
                if (matrix.get(i + rec[0], j + rec[1])) {
                    resMatrix.set(i, j);
                }
            }
        }
        return resMatrix;
    }
}
