package com.landray.kmss.sys.attachment.servlet;

import com.landray.kmss.sys.attachment.model.SysAttMain;
import com.landray.kmss.sys.attachment.service.ISysAttMainCoreInnerService;
import com.landray.kmss.sys.attachment.util.SysAttConstant;
import com.landray.kmss.sys.attachment.util.SysAttCryptUtil;
import com.landray.kmss.sys.filestore.constant.SysAttUploadConstant;
import com.landray.kmss.sys.filestore.model.SysAttFile;
import com.landray.kmss.sys.filestore.model.SysAttFileSlice;
import com.landray.kmss.sys.filestore.service.ISysAttUploadService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.web.upload.KmssMultipartResolver;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import sun.misc.BASE64Decoder;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 附件上传服务
 */
public class UploaderServlet extends HttpServlet {

	private Logger logger = org.slf4j.LoggerFactory.getLogger(UploaderServlet.class);

	private static final long serialVersionUID = 1046814328443091609L;

	private ISysAttUploadService sysAttUploadService = null;

	private KmssMultipartResolver multipartResolver = null;

	private long expireTime = SysAttUploadConstant.SYS_ATT_CONFIG_EXPIRETIME;

	private long sizeThreshold = SysAttUploadConstant.SYS_ATT_CONFIG_SLICE_SIZE;

	private long smallAttLimit = SysAttUploadConstant.SYS_ATT_CONFIG_FILE_SIZE;

	private String fileLimitType = "1";

	private String disabledFileType = SysAttConstant.DISABLED_FILE_TYPE;

	public UploaderServlet() {
		super();
	}

	@Override
	public void init() throws ServletException {
		if (sysAttUploadService == null) {
			sysAttUploadService = (ISysAttUploadService) SpringBeanUtil
					.getBean("sysAttUploadService");
		}
		if (multipartResolver == null) {
			multipartResolver = (KmssMultipartResolver) SpringBeanUtil
					.getBean("multipartResolver");
		}
		String expire = ResourceUtil.getKmssConfigString("sys.att.expire");
		if (StringUtil.isNotNull(expire)) {
			expireTime = Long.valueOf(expire) * 1000L;
		}
		String sliceSize = ResourceUtil
				.getKmssConfigString("sys.att.slice.size");
		if (StringUtil.isNotNull(sliceSize)) {
			sizeThreshold = Long.valueOf(sliceSize) * 1024L * 1024L;
		}
		String fileSize = ResourceUtil
				.getKmssConfigString("sys.att.smallMaxSize");
		if (StringUtil.isNotNull(fileSize)) {
			smallAttLimit = Long.valueOf(fileSize) * 1024L * 1024L;
		} else {
			smallAttLimit = 1000000 * 1024L * 1024L;
		}
		String fileLimit = ResourceUtil
				.getKmssConfigString("sys.att.fileLimitType");	
		if (StringUtil.isNotNull(fileLimit)) {
			fileLimitType = fileLimit;
		}
		String disabledfile = ResourceUtil
				.getKmssConfigString("sys.att.disabledFileType");
		if (StringUtil.isNotNull(fileLimit) && disabledfile!=null) {
			disabledFileType = disabledfile.toLowerCase();
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
		String reqType = request.getParameter("gettype");
		Map<String, String> rtnMap = null;
		if ("checkmd5".equalsIgnoreCase(reqType)) {
			// 大附件校验，及信息传递
			rtnMap = checkMd5(request);
		} else if ("uploadslice".equalsIgnoreCase(reqType)) {
			// 大附件切片上传
			rtnMap = upload(request, "slice");
		} else if ("upload".equalsIgnoreCase(reqType)) {
			// 小附件上传及校验
			if (StringUtil.isNotNull(request.getParameter("fdAttMainId"))) {
				rtnMap = updateUploadAtt(request, "file");
			} else {
				rtnMap = upload(request, "file");
			}
		} else if ("uploadStream".equalsIgnoreCase(reqType)) {
			// 附件流上传
			rtnMap = uploadStream(request, response);
		} else {
			rtnMap = new HashMap<String, String>();
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "非法的请求");
		}
		printResult(request, response, rtnMap);
	}

	/**
	 * 根据密钥，解析文件信息，返回需要上传的切片信息或者返回已上传完毕的文件信息
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> checkMd5(HttpServletRequest request) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		String userkey = request.getParameter("userkey");
		if (StringUtil.isNotNull(userkey)) {
			try {
				String fileInfo = SysAttCryptUtil.decrypt(userkey);
				String fileMd5 = StringUtil.getParameter(fileInfo, "md5");
				String fileSize = StringUtil.getParameter(fileInfo, "filesize");
				String timestamp = StringUtil.getParameter(fileInfo, "time");
				long curTimestamp = sysAttUploadService.getCurTimestamp()
						.getTime();
				if (StringUtil.isNull(fileMd5)) {
					rtnMap.put("status", "-1");
					rtnMap.put("msg", "文件MD5信息为空!");
					logger.debug("检验出错文件MD5信息为空");
					return rtnMap;
				}
				if (Long.valueOf(timestamp) < curTimestamp - expireTime) {
					rtnMap.put("status", "-1");
					rtnMap.put("msg", "文件上传超过有效期。");
					logger.debug("checkMd5_文件上传超过有效期。当前时间是：" + curTimestamp
							+ ",超时时间是：" + timestamp);
				} else {
					long fileSizel = Long.valueOf(fileSize);
					SysAttFile file = sysAttUploadService.getFileByMd5(fileMd5,
							fileSizel);
					SysAttFileSlice fileSlice = null;
					long uploadSize = 0L;
					if (file == null) {
						fileSlice = sysAttUploadService.addFileInfo(fileMd5,
								fileSizel);
					} else {
						if (file.getFdStatus() == SysAttUploadConstant.SYS_ATT_FILE_STATUS_UPLOADED) {
							rtnMap.put("status", "1"); // 文件已上传
							rtnMap.put("filekey", file.getFdId());
							uploadSize = fileSizel;
						} else {
							fileSlice = sysAttUploadService
									.getNextFileSlice(file.getFdId());
							uploadSize = sysAttUploadService
									.getUploadedCount(file.getFdId());
						}
					}
					if (fileSlice != null) {
						long expire = sysAttUploadService.getCurTimestamp()
								.getTime();
						String sliceid = "&sliceid=" + fileSlice.getFdId()
								+ "&time=" + expire;
						rtnMap.put("status", "0");// 可以上传切片
						rtnMap.put("sliceid", SysAttCryptUtil.encrypt(sliceid));
						rtnMap.put("startpoint", String.valueOf(fileSlice
								.getFdStartPoint()));
						rtnMap.put("endpoint", String.valueOf(fileSlice
								.getFdEndPoint()));
						rtnMap.put("finishedsize", String.valueOf(uploadSize));
					} else {// 合并文件
						if (file != null
								&& file.getFdStatus() != SysAttUploadConstant.SYS_ATT_FILE_STATUS_UPLOADED) {
							boolean isSuccess = sysAttUploadService
									.combFileSlice(file);
							if (isSuccess == true) {
								rtnMap.put("status", "1");
								rtnMap.put("filekey", file.getFdId());

							} else {
								rtnMap.put("status", "-1");
								rtnMap.put("msg", "文件合并出错。");
								logger.debug("checkMd5_文件合并出错。");
							}
						}
					}
				}
			} catch (Exception e) {
				rtnMap.put("status", "-1");
				rtnMap.put("msg", "文件切片或读取过程中出错，获取的密钥为：" + userkey + ",错误信息为："
						+ e);
				logger.error("checkMd5_文件切片或读取过程中出错，获取的密钥为：" + userkey
						+ ",错误信息", e);
			}
		} else {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "文件上传密钥信息为空！");
			logger.error("checkMd5_文件上传密钥信息为空！");
		}
		return rtnMap;
	}

	/**
	 * 切片上传，返回其他切片信息或者已上传的文件信息
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	private void uploadSlice(Map fields, Map<String, String> rtnMap,
			long curTimestamp) throws Exception {
		String sliceInfo = (String) fields.get("sliceid");
		if (StringUtil.isNotNull(sliceInfo)) {
			sliceInfo = SysAttCryptUtil.decrypt(sliceInfo);
			String timestamp = StringUtil.getParameter(sliceInfo, "time");
			String sliceId = StringUtil.getParameter(sliceInfo, "sliceid");
			if (Long.valueOf(timestamp) < curTimestamp - expireTime) {
				rtnMap.put("status", "-1");
				rtnMap.put("msg", "切片上传超过有效期。");
				logger.debug("uploadSlice_切片上传超过有效期,当前时间是：" + curTimestamp
						+ ",超时时间是：" + timestamp);
			} else {
				FileItem data = (FileItem) fields.get("data");
				SysAttFileSlice fileSlice = sysAttUploadService
						.updateFileSlice(sliceId, data.getInputStream());
				if (fileSlice != null) {
					SysAttFile attFile = fileSlice.getFdFile();
					SysAttFileSlice nextFileSlice = sysAttUploadService
							.getNextFileSlice(attFile.getFdId());
					if (nextFileSlice == null) {
						// 合并文件
						boolean isSuccess = sysAttUploadService
								.combFileSlice(attFile);
						if (isSuccess == true) {
							rtnMap.put("status", "1");
							rtnMap.put("filekey", attFile.getFdId());

						} else {
							rtnMap.put("status", "-1");
							rtnMap.put("msg", "文件合并出错。");
							logger.debug("uploadSlice_文件合并出错。");
						}
					} else {
						long uploadSize = sysAttUploadService
								.getUploadedCount(attFile.getFdId());
						long expire = sysAttUploadService.getCurTimestamp()
								.getTime();
						String sliceid = "&sliceid=" + nextFileSlice.getFdId()
								+ "&time=" + expire;
						rtnMap.put("status", "0");
						rtnMap.put("sliceid", SysAttCryptUtil.encrypt(sliceid));
						rtnMap.put("startpoint", String.valueOf(nextFileSlice
								.getFdStartPoint()));
						rtnMap.put("endpoint", String.valueOf(nextFileSlice
								.getFdEndPoint()));
						rtnMap.put("finishedsize", String.valueOf(uploadSize));
					}
				} else {
					rtnMap.put("status", "-1");
					rtnMap.put("msg", "服务器中无此切片信息，切片id：" + sliceId);
					logger.debug("uploadSlice_服务器中无此切片信息，切片id：" + sliceId);
				}
			}
		} else {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "切片上传时密钥为空！");
			logger.debug("uploadSlice_切片上传时密钥为空！");
		}

	}

	/**
	 * 小附件上传逻辑
	 * 
	 * @param fields
	 * @param rtnMap
	 * @throws Exception
	 */
	private void uploadFile(Map fields, Map<String, String> rtnMap,
			long curTimestamp, boolean isCheckedTimeOut) throws Exception {
		String fileInfo = (String) fields.get("userkey");
		if (StringUtil.isNotNull(fileInfo)) {
			String fileMd5 = (String) fields.get("fileMd5");
			String fileSize = (String) fields.get("fileSize");
			if (isCheckedTimeOut) {
				fileInfo = SysAttCryptUtil.decrypt(fileInfo);
				fileMd5 = StringUtil.getParameter(fileInfo, "md5");
				fileSize = StringUtil.getParameter(fileInfo, "filesize");
				String timestamp = StringUtil.getParameter(fileInfo, "time");
				if (Long.valueOf(timestamp) < curTimestamp - expireTime) {
					rtnMap.put("status", "-1");
					rtnMap.put("msg", "附件上传超过有效期。");
					logger.debug("uploadFile_附件上传超过有效期.当前时间是：" + curTimestamp
							+ ",超时时间是：" + timestamp);
					return;
				}
			}
			String _fileName = (String) fields.get("name");
			DiskFileItem data = (DiskFileItem) fields.get("data");
			if (StringUtil.isNotNull(_fileName)) {
				String _fileType = null;
				if (_fileName.indexOf(".") > -1) {
					_fileType = _fileName.substring(_fileName.lastIndexOf("."));
				}

//				if (SysAttBase.ATTACHMENT_LOCATION_SERVER
//						.equals(SysFileLocationUtil.getKey(null))) {
//					InputStream in = data.getInputStream();
//					if (in != null) {
//						_fileType = FileTypeUtil.getFileType(in);
//						IOUtils.closeQuietly(in);
//					}
//				}

				if (StringUtil.isNotNull(_fileType)) {
					_fileType = _fileType.toLowerCase().trim();
					String[] files = disabledFileType.split("[;；]");
					if("1".equals(fileLimitType)){
						Boolean isPass = true;
						for(String f : files){
							if(_fileType.equals(f)){
								isPass = false;
								break;
							}
						}
						if(!isPass){
							rtnMap.put("status", "-1");
							rtnMap.put("msg", "基于安全考虑，不允许上传该附件！");
							return;
						}
					}else if("2".equals(fileLimitType)){
						Boolean isPass = false;
						for(String f : files){
							if(_fileType.equals(f)){
								isPass = true;
								break;
							}
						}
						if(!isPass){
							rtnMap.put("status", "-1");
							rtnMap.put("msg", "基于安全考虑，不允许上传该附件！");
							return;
						}
					}
				}else{
					//无后缀文件不允许上传。
					rtnMap.put("status", "-1");
					rtnMap.put("msg", "基于安全考虑，不允许上传该附件！");
					return;
				}
			}

			Long fileSizeArgu = 0L;
			if (StringUtil.isNotNull(fileSize)) {
				fileSizeArgu = Long.valueOf(fileSize);
			}
			String fdKey = sysAttUploadService.addFile(fileMd5, fileSizeArgu,
					data.getInputStream(), false, data.getStoreLocation()
							.getPath(),_fileName);
			rtnMap.put("status", "1");
			rtnMap.put("filekey", fdKey);
			return;
		} else {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "附件上传密钥为空！");
			logger.debug("uploadFile_附件上传密钥为空！");
			return;
		}
	}

	private Map<String, String> uploadStream(HttpServletRequest request,
			HttpServletResponse response) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		String data = request.getParameter("data");
		if (StringUtil.isNull(data)) {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "附件上传信息为空。");
			logger.error("upload_附件上传信息为空。");
			return rtnMap;
		}
		data = data.split(",")[1];
		byte[] buffer;
		try {
			buffer = new BASE64Decoder().decodeBuffer(data);
			String fdKey = sysAttUploadService.addStreamFile(buffer,request.getParameter("fileName"));
			rtnMap.put("status", "1");
			rtnMap.put("filekey", fdKey);
			return rtnMap;

		} catch (Exception e) {
			logger.error("uploadStream附件流上传错误！", e);
		}
		return rtnMap;
	}

	private List<SysAttMain> getAttMains(HttpServletRequest request) throws Exception {
		String fdId = request.getParameter("fdAttMainId");
		List<SysAttMain> sysAttMains = null;
		if (fdId != null && fdId.trim().length() > 0) {
			String[] l_fdId = fdId.split(";");
			sysAttMains = ((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService"))
					.findModelsByIds(l_fdId);
		}
		return sysAttMains;
	}

	private void updateUploadFile(HttpServletRequest request, Map fields, Map<String, String> rtnMap) throws Exception {
		String fdFileId = request.getParameter("fdAttMainId");
		String fileInfo = (String) fields.get("userkey");
		if (StringUtil.isNotNull(fileInfo)) {
			fileInfo = SysAttCryptUtil.decrypt(fileInfo);
			String fdAttMainId = StringUtil.getParameter(fileInfo, "fdAttMainId");
			if (StringUtil.isNotNull(fdAttMainId)) {
				List<SysAttMain> l = getAttMains(request);
				if (l.size() > 0) {
					DiskFileItem data = (DiskFileItem) fields.get("data");
					SysAttMain sysAttMain = l.get(0);
					if (fdAttMainId.equals(sysAttMain.getFdId())) {
						InputStream in = data.getInputStream();
						sysAttMain.setInputStream(in);
						sysAttMain.setFdSize(new Double(in.available()));
						((ISysAttMainCoreInnerService) SpringBeanUtil.getBean("sysAttMainService")).update(sysAttMain);
					} else {
						rtnMap.put("status", "-1");
						rtnMap.put("msg", "附件更新上传密钥与附件Id不一致！");
						rtnMap.put("filekey", fdFileId);
						logger.debug("uploadFile_附件更新上传密钥与附件Id不一致！");
						return;
					}
				}
				rtnMap.put("status", "1");
				rtnMap.put("filekey", fdFileId);
				return;
			} else {
				rtnMap.put("status", "-1");
				rtnMap.put("filekey", fdFileId);
				rtnMap.put("msg", "附件更新上传密钥为空！");
				logger.debug("uploadFile_附件更新上传密钥为空！");
				return;
			}
		} else {
			rtnMap.put("status", "-1");
			rtnMap.put("filekey", fdFileId);
			rtnMap.put("msg", "附件更新上传密钥为空！");
			logger.debug("uploadFile_附件更新上传密钥为空！");
			return;
		}
	}

	private Map<String, String> updateUploadAtt(HttpServletRequest request, String methodKey) throws IOException {

		Map<String, String> rtnMap = new HashMap<String, String>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "附件上传信息为空。");
			logger.debug("upload_附件上传信息为空。");
		}

		DiskFileItemFactory factory = multipartResolver.getFileItemFactory();
		//DiskFileItemFactory factory = new DiskFileItemFactory();
		//factory.setSizeThreshold((int) sizeThreshold);
		//升级了common-upload组件到1.4之后，内存阀值如果超过0就不会落盘，所以
		//getStoreLocation就会null，所以这里改成0
		//factory.setSizeThreshold(0);
		ServletFileUpload upload = new ServletFileUpload(factory);
		if ("file".equalsIgnoreCase(methodKey)) {
			upload.setFileSizeMax(smallAttLimit);
		} else {
			upload.setFileSizeMax(sizeThreshold);
		}
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> fileItems;
		try {
			Map fields = new HashMap();
			String fileInfo = request.getHeader("userkey");// 优先header获取
			if (StringUtil.isNull(fileInfo)) {
				fileInfo = request.getParameter("userkey");
			}
			if (StringUtil.isNotNull(fileInfo)) {
				fields.put("userkey", fileInfo);
			}
			fileItems = upload.parseRequest(request);
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					fields.put(fileItem.getFieldName(), fileItem.getString());
				} else {
					fields.put("data", fileItem);
				}
			}
			updateUploadFile(request, fields, rtnMap);
		} catch (Exception e) {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "附件上传过程出错，错误信息为：" + e);
			logger.error("upload_附件上传过程出错，错误信息", e);
		}
		return rtnMap;
	}

	/**
	 * 附件上传的处理
	 * 
	 * @param request
	 * @return
	 */
	private Map<String, String> upload(HttpServletRequest request,
			String methodKey) {
		Map<String, String> rtnMap = new HashMap<String, String>();
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (!isMultipart) {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "附件上传信息为空。");
			logger.debug("upload_附件上传信息为空。");
		}
		DiskFileItemFactory factory = multipartResolver.getFileItemFactory();
//		DiskFileItemFactory factory = new DiskFileItemFactory();
		//升级了common-upload组件到1.4之后，内存阀值如果超过0就不会落盘，所以
		//getStoreLocation就会null，所以这里改成0
//		factory.setSizeThreshold(0);
		ServletFileUpload upload = new ServletFileUpload(factory);
		if ("file".equalsIgnoreCase(methodKey)) {
			upload.setFileSizeMax(smallAttLimit);
		} else {
			upload.setFileSizeMax(sizeThreshold);
		}
		upload.setHeaderEncoding("UTF-8");
		List<FileItem> fileItems;
		try {
			boolean isCheckedTimeOut = true;
			// 先获取时间戳，避免后面数据传输所花时间
			long curTimestamp = sysAttUploadService.getCurTimestamp().getTime();
			String fileInfo = request.getHeader("userkey");//优先header获取
			if (StringUtil.isNull(fileInfo)) {
				fileInfo = request.getParameter("userkey");
			}
			Map fields = new HashMap();
			if (StringUtil.isNotNull(fileInfo)) {
				fileInfo = SysAttCryptUtil.decrypt(fileInfo);
				String fileMd5 = StringUtil.getParameter(fileInfo, "md5");
				String fileSize = StringUtil.getParameter(fileInfo, "filesize");
				String timestamp = StringUtil.getParameter(fileInfo, "time");
				if (Long.valueOf(timestamp) < curTimestamp - expireTime) {
					rtnMap.put("status", "-1");
					rtnMap.put("msg", "附件上传超过有效期。");
					logger.debug("uploadFile_附件上传超过有效期.当前时间是：" + curTimestamp
							+ ",超时时间是：" + timestamp);
					return rtnMap;
				}
				fields.put("userkey", fileInfo);
				fields.put("fileMd5", fileMd5);
				fields.put("fileSize", fileSize);
				isCheckedTimeOut = false;
			}
			fileItems = upload.parseRequest(request);
			for (FileItem fileItem : fileItems) {
				if (fileItem.isFormField()) {
					fields.put(fileItem.getFieldName(), fileItem.getString());
				} else {
					fields.put("data", fileItem);
				}
			}
			if ("file".equalsIgnoreCase(methodKey)) {
				uploadFile(fields, rtnMap, curTimestamp, isCheckedTimeOut);
			} else {
				uploadSlice(fields, rtnMap, curTimestamp);
			}
		} catch (Exception e) {
			rtnMap.put("status", "-1");
			rtnMap.put("msg", "附件上传过程出错，错误信息为：" + e);
			logger.error("upload_附件上传过程出错，错误信息", e);
		}
		return rtnMap;
	}

	/**
	 * 打印返回信息
	 * 
	 * @param response
	 * @param rtnMap
	 * @throws ServletException
	 * @throws IOException
	 */
	private void printResult(HttpServletRequest request,
			HttpServletResponse response, Map<String, String> rtnMap)
			throws ServletException, IOException {
		if (rtnMap != null && !rtnMap.isEmpty()) {
			String format = request.getParameter("format");
			if (StringUtil.isNull(format)) {
				format = "xml";
			}
			if ("xml".equals(format)) {
				response.setContentType("text/xml; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter out = response.getWriter();
				if (rtnMap != null && !rtnMap.isEmpty()) {
					out.println("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
					out.println("<return>");
					for (Object key : rtnMap.keySet()) {
						out.println("<"
								+ (String) key
								+ ">"
								+ StringUtil.XMLEscape(rtnMap.get(key)
										.toString()) + "</" + key + ">");
					}
					out.println("</return>");
				}
			} else {
				response.setContentType("text/plain; charset=UTF-8");
				response.setHeader("Cache-Control", "no-cache");
				PrintWriter out = response.getWriter();
				out.print(JSONObject.fromObject(rtnMap).toString());
			}
		}
	}
}
