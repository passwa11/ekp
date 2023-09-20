package com.landray.kmss.sys.attachment.restservice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.web.annotation.RestApi;

@Controller
@RequestMapping(value = "/api/sys-attachment/sysAttachmentRestService", method = RequestMethod.POST)
@RestApi(docUrl = "/sys/attachment/restservice/sysAttachmentRestServiceHelp.jsp", name = "sysAttachmentRestService", resourceKey = "sys-attachment:sysAttachmentRestService.title")
public class SysAttachmentRestServiceImp {

	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(SysAttachmentRestServiceImp.class);

	private ISysAttMainCoreInnerService sysAttMainService;

	public void setSysAttMainService(ISysAttMainCoreInnerService sysAttMainService) {
		this.sysAttMainService = sysAttMainService;
	}

	private ISysAttUploadService sysAttUploadService;

	public void setSysAttUploadService(ISysAttUploadService sysAttUploadService) {
		this.sysAttUploadService = sysAttUploadService;
	}

	/**
	 * 取下载文件的url
	 */
	@SuppressWarnings("unchecked")
	@ResponseBody
	@RequestMapping(value = "/getDonwloadUrl", method = RequestMethod.GET)
	public String getDonwloadUrl(@RequestParam("fdId") String fdId) throws Exception {

		return sysAttMainService.getRestDownloadUrl(fdId);
	}

}
