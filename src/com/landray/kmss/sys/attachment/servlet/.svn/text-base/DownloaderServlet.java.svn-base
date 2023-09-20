package com.landray.kmss.sys.attachment.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.util.SpringBeanUtil;

/**
 * 附件下载服务
 */
public class DownloaderServlet extends HttpServlet {
	
	private static final long serialVersionUID = -4793898061935607384L;

	private ISysAttUploadService sysAttUploadService = null;


	public DownloaderServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		if (sysAttUploadService == null) {
            sysAttUploadService = (ISysAttUploadService) SpringBeanUtil
                    .getBean("sysAttUploadService");
        }
		super.init();
	}

	@Override
	protected void doGet(HttpServletRequest request,
						 HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	@Override
	protected void doPost(HttpServletRequest request,
						  HttpServletResponse response) throws ServletException, IOException {
		
	}

}
