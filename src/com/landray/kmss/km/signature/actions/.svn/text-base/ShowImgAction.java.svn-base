package com.landray.kmss.km.signature.actions;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Blob;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.util.IOUtils;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.exception.NoRecordException;
import com.landray.kmss.common.exception.UnexpectedRequestException;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.common.test.TimeCounter;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.km.signature.util.BlobUtil;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class ShowImgAction extends ExtendAction {

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}

	@Override
	public ActionForward execute(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		String flag = request.getParameter("flag");
		InputStream inputStream = null;
		OutputStream outputStream = null;
		try {
			if ("sig".equals(flag)) {
				String fdId = request.getParameter("fdId");
				IKmSignatureMainService imp = (IKmSignatureMainService) SpringBeanUtil
						.getBean("kmSignatureMainService");
				KmSignatureMain signature = (KmSignatureMain) imp
						.findByPrimaryKey(fdId);
				Blob blob = signature.getFdMarkBody();// 通过实体获取二进制图片
				inputStream = new ByteArrayInputStream(BlobUtil
						.blobToBytes(blob));
				outputStream = response.getOutputStream();
				response.setContentLength(inputStream.available());
				response.setContentType("image/jpeg");// 设置显示格式
				// new一个byte数组
				int bufsize = 1024;
				byte[] fdMarkBody = new byte[bufsize]; //inputStream.available()			
				int count = 0;
			    while ((count = inputStream.read(fdMarkBody)) != -1) { //将二进制流信息读入数组
			    	outputStream.write(fdMarkBody, 0, count); //通过输出流将数组信息显示到页面
			    }
			    outputStream.flush();
			}
		} catch (Exception e) {
			log.error("显示图片发生异常：", e);
		} finally {
			IOUtils.closeQuietly(inputStream);
			IOUtils.closeQuietly(outputStream);
		}
		return null;
	}

	@Override
	public ActionForward delete(ActionMapping mapping, ActionForm form,
								HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		TimeCounter.logCurrentTime("Action-delete", true, getClass());
		KmssMessages messages = new KmssMessages();
		try {
			if (!"GET".equals(request.getMethod())) {
                throw new UnexpectedRequestException();
            }
			String id = request.getParameter("fdId");
			if (StringUtil.isNull(id)) {
                messages.addError(new NoRecordException());
            } else {
                getServiceImp(request).delete(id);
            }
		} catch (Exception e) {
			messages.addError(e);
		}

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_CLOSE).save(request);
		TimeCounter.logCurrentTime("Action-delete", false, getClass());
		if (messages.hasError()) {
            return getActionForward("failure", mapping, form, request, response);
        } else {
            return getActionForward("success", mapping, form, request, response);
        }
	}

}
