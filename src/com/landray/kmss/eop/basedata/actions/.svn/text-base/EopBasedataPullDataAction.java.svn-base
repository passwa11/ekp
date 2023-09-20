package com.landray.kmss.eop.basedata.actions;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.landray.kmss.common.actions.ExtendAction;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.eop.basedata.service.IEopBasedataPullAndPushService;
import com.landray.kmss.eop.basedata.util.EopBasedataUtil;
import com.landray.kmss.util.KmssMessage;
import com.landray.kmss.util.KmssMessages;
import com.landray.kmss.util.KmssReturnPage;
import com.landray.kmss.web.action.ActionForm;
import com.landray.kmss.web.action.ActionForward;
import com.landray.kmss.web.action.ActionMapping;;


/**
*@author yucf
*@date  2021-1-15
*@Description                       从业务模块拉取（导入）基础数据 
**/

public class EopBasedataPullDataAction extends ExtendAction {
	
	private static final Logger logger = LoggerFactory.getLogger(EopBasedataPullDataAction.class);
	
	/**
	 * 从业务模块拉取数据
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward pullData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String function = request.getParameter("f");
		Assert.hasText(function, "未指定导入的数据类型！");
		
		logger.info("待导入的基础数据功能：{}", function);
		
		KmssMessages messages = new KmssMessages();
		
		List<IEopBasedataPullAndPushService> pullAndPushServiceList = EopBasedataUtil.getPullAndPushService(function);
		if(CollectionUtils.isNotEmpty(pullAndPushServiceList)) {
			for(IEopBasedataPullAndPushService pullAndPushService  : pullAndPushServiceList) {
				pullAndPushService.syncData2Eop();
				pullAndPushService.asyncData2BizMod(null, null);
			}
		}

		messages.addMsg(new KmssMessage("eop-basedata:pull.data.success"));

		KmssReturnPage.getInstance(request).addMessages(messages).addButton(
				KmssReturnPage.BUTTON_RETURN).save(request);
		if (messages.hasError()) {
			return mapping.findForward("failure");
		} else {
			return mapping.findForward("success");
		}
	}
	

	@Override
	protected IBaseService getServiceImp(HttpServletRequest request) {
		return null;
	}
}