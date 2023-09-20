package com.landray.kmss.km.signature.util;

import java.sql.Blob;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.km.signature.model.KmSignatureDocumentHistory;
import com.landray.kmss.km.signature.model.KmSignatureDocumentSignature;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureDocumentHistoryService;
import com.landray.kmss.km.signature.service.IKmSignatureDocumentSignatureService;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.UserUtil;

public class iWebRevision {
	private long mFileSize;
	private Blob mFileBody;
	private String mFileName;
	private String mFieldName;
	private String mFileType;
	private String mRecordID;
	private String mDateTime;
	private String mOption;
	private String mMarkName;
	private String mPassword;
	private String mMarkList;
	private String mHostName;
	private String mMarkGuid;
	private String mFieldValue;
	private String mUserName;
	private String mFilePath;
	private DBstep.iMsgServer2000 MsgObj;
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	// 保存签章的历史信息
	private boolean SaveHistory() {
		try {
			String fdId = IDGenerator.generateID();
			IKmSignatureDocumentHistoryService documentHistoryService = (IKmSignatureDocumentHistoryService) SpringBeanUtil
					.getBean("documentHistoryService");
			KmSignatureDocumentHistory documentHistory = new KmSignatureDocumentHistory();
			documentHistory.setFdRecordId(mRecordID);
			documentHistory.setFdFieldName(mFieldName);
			documentHistory.setFdMarkName(mMarkName);
			documentHistory.setFdUserName(UserUtil.getUser().getFdName());
			documentHistory.setFdDateTime(new Date());
			documentHistory.setFdHostName(mHostName);
			documentHistory.setFdMarkGuid(mMarkGuid);
			documentHistory.setFdId(fdId);
			documentHistory.setFdHistoryId(1);
			documentHistory.setFdSigId("1");
			documentHistoryService.add(documentHistory);
			return true;
		} catch (Exception e) {
			MsgObj.MsgError("保存签章的历史信息错误!");
			return false;
		}
	}

	// 列出所有历史信息
	private boolean ShowHistory() {
		try {
			mMarkName = "印章名称" + "\r\n";
			mUserName = "签名人" + "\r\n";
			mHostName = "客户端IP" + "\r\n";
			mDateTime = "签章时间" + "\r\n";
			mMarkGuid = "序列号" + "\r\n";
			IKmSignatureDocumentHistoryService documentHistoryService = (IKmSignatureDocumentHistoryService) SpringBeanUtil
					.getBean("documentHistoryService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock(" documentHistory.recordid = :recordid and documentHistory.fieldname = :fieldname ");
			hqlInfo.setParameter("recordid", mRecordID);
			hqlInfo.setParameter("FieldName", mFieldName);
			List<KmSignatureDocumentHistory> list = documentHistoryService
					.findList(hqlInfo);
			for (int i = 0; i < list.size(); i++) {
				KmSignatureDocumentHistory history = list.get(i);
				mMarkName += history.getFdMarkName() + "\r\n";
				mDateTime += new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
						.format(history.getFdDateTime())
						+ "\r\n";
				mUserName += history.getFdUserName() + "\r\n";
				mHostName += history.getFdHostName() + "\r\n";
				mMarkGuid += history.getFdMarkGuid() + "\r\n";
			}
			return true;
		} catch (Exception e) {
			MsgObj.MsgError("列出所有历史信息错误!");
			return false;
		}
	}

	// 取得签名列表
	private boolean SignatureList() {
		try {
			SysOrgPerson orgPerson = UserUtil.getUser();
			StringBuffer wherebuffer = new StringBuffer(" ('' ");
			wherebuffer.append(",'" + orgPerson.getFdId() + "'");
			if (orgPerson.getFdParent() != null) {
				wherebuffer.append(",'" + orgPerson.getFdParent().getFdId()
						+ "'");
			}
			List<SysOrgElement> posts = orgPerson.getFdPosts();
			for (int i = 0; i < posts.size(); i++) {
				wherebuffer.append(",'" + posts.get(i).getFdId() + "'");
			}
			wherebuffer.append(" )");
			String sql = "select s.fd_mark_name from km_signature_main s,km_signature_users su where s.fd_id=su.fd_signature_id and su.fd_org_id in "
					+ wherebuffer.toString() + " ";
			mMarkList = "";
			IKmSignatureMainService signatureService = (IKmSignatureMainService) SpringBeanUtil
					.getBean("kmSignatureMainService");
			List<String> list = signatureService.getBaseDao().getHibernateSession().createNativeQuery(sql).list();
			for (int i = 0; i < list.size(); i++) {
				mMarkList += list.get(i).toString() + "\r\n";
			}
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			MsgObj.MsgError("取得签名列表错误!");
			return false;
		}

	}

	// 调入签章图案
	private boolean SignatureImage(String vMarkName, String vPassWord) {
		try {
			IKmSignatureMainService signatureService = (IKmSignatureMainService) SpringBeanUtil
					.getBean("kmSignatureMainService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock(" kmSignatureMain.fdMarkName = :markname and kmSignatureMain.fdPassword = :password ");
			hqlInfo.setParameter("markname", vMarkName);
			hqlInfo.setParameter("password", vPassWord);
			List<KmSignatureMain> list = signatureService.findList(hqlInfo);
			mFileBody = list.get(0).getFdMarkBody();
			mFileType = list.get(0).getFdMarkType();
			mFileSize = list.get(0).getFdMarkBody().length();
			return true;
		} catch (Exception e) {
			MsgObj.MsgError("调入签章图案错误!");
			return false;
		}
	}

	// 保存签章数据信息
	private boolean SaveSignatureSysWf() {
		return true;
	}

	// 保存签章数据信息
	private boolean SaveSignature() {
		try {
			IKmSignatureDocumentSignatureService documentSignatureService = (IKmSignatureDocumentSignatureService) SpringBeanUtil
					.getBean("kmSignatureDocumentSignatureService");
			KmSignatureDocumentSignature documentSignature = new KmSignatureDocumentSignature();
			documentSignature.setFdRecordId(mRecordID);
			documentSignature.setFdFieldName(mFieldName);
			documentSignature.setFdUserName(mUserName);
			documentSignature.setFdDateTime(new Date());
			documentSignature.setFdHostName(mHostName);
			documentSignature.setFdFieldValue(mFieldValue);
			documentSignature.setFdId(IDGenerator.generateID());
			documentSignature.setFdDocumentSignatureId(1);
			documentSignatureService.add(documentSignature);
			return true;
		} catch (Exception e) {
			MsgObj.MsgError("保存签章数据信息错误!");
			return false;
		}
	}

	// 更新签章数据信息
	private boolean UpdateSignature() {
		try {
			IKmSignatureDocumentSignatureService documentSignatureService = (IKmSignatureDocumentSignatureService) SpringBeanUtil
					.getBean("kmSignatureDocumentSignatureService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock(" documentSignature.recordid = :recordid and documentSignature.fieldname = :fieldname ");
			hqlInfo.setParameter("recordid", mRecordID);
			hqlInfo.setParameter("fieldname", mFieldName);
			KmSignatureDocumentSignature documentSignature = (KmSignatureDocumentSignature) documentSignatureService
					.findList(hqlInfo).get(0);
			documentSignature.setFdUserName(mUserName);
			documentSignature.setFdDateTime(new Date());
			documentSignature.setFdHostName(mHostName);
			documentSignature.setFdFieldValue(mFieldValue);
			documentSignatureService.update(documentSignature);
			return true;
		} catch (Exception e) {
			MsgObj.MsgError("更新签章数据信息误错误");
			return false;
		}
	}

	// 判断签章数据信息是否存在
	private boolean ShowSignatureIS() {
		try {
			IKmSignatureDocumentSignatureService documentSignatureService = (IKmSignatureDocumentSignatureService) SpringBeanUtil
					.getBean("kmSignatureDocumentSignatureService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock(" documentSignature.recordid = :recordid and documentSignature.fieldname = :fieldname ");
			hqlInfo.setParameter("recordid", mRecordID);
			hqlInfo.setParameter("fieldname", mFieldName);
			List list = documentSignatureService.findList(hqlInfo);
			if (list.size() > 0) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			MsgObj.MsgError("判断签章数据信息是否存在错误");
			return false;
		}
	}

	// 调出签章数据信息
	private boolean LoadSignature() {
		try {
			IKmSignatureDocumentSignatureService documentSignatureService = (IKmSignatureDocumentSignatureService) SpringBeanUtil
					.getBean("kmSignatureDocumentSignatureService");
			HQLInfo hqlInfo = new HQLInfo();
			hqlInfo
					.setWhereBlock(" documentSignature.recordid = :recordid and documentSignature.fieldname = :fieldname ");
			hqlInfo.setParameter("recordid", mRecordID);
			hqlInfo.setParameter("fieldname", mFieldName);
			KmSignatureDocumentSignature documentSignature = (KmSignatureDocumentSignature) documentSignatureService
					.findList(hqlInfo).get(0);
			mFieldValue = documentSignature.getFdFieldValue();
			return true;
		} catch (Exception e) {
			MsgObj.MsgError("调出签章数据信息错误");
			return false;
		}
	}

	// 取得客户端发来的数据包
	private byte[] ReadPackage(HttpServletRequest request) {
		byte mStream[] = null;
		int totalRead = 0;
		int readBytes = 0;
		int totalBytes = 0;
		try {
			totalBytes = request.getContentLength();
			mStream = new byte[totalBytes];
			while (totalRead < totalBytes) {
				request.getInputStream();
				readBytes = request.getInputStream().read(mStream, totalRead,
						totalBytes - totalRead);
				totalRead += readBytes;
				continue;
			}
		} catch (Exception e) {
			MsgObj.MsgError("取得客户端发来的数据包错误");
			logger.error(e.toString());
		}
		return (mStream);
	}

	// 发送处理后的数据包
	private void SendPackage(HttpServletResponse response) {
		try {
			ServletOutputStream OutBinarry = response.getOutputStream();
			OutBinarry.write(MsgObj.MsgVariant());
			OutBinarry.flush();
			OutBinarry.close();
		} catch (Exception e) {
			MsgObj.MsgError("发送处理后的数据包错误");
			logger.error(e.toString());
		}
	}

	// 具体处理客户端控件请求的函数
	public void ExecuteRun(HttpServletRequest request,
			HttpServletResponse response) {
		mOption = "";
		mRecordID = "";
		mFileBody = null;
		mFileName = "";
		mFileType = "";
		mFileSize = 0;
		mDateTime = "";
		mMarkName = "";
		mPassword = "";
		mMarkList = "";
		mMarkGuid = "";
		mUserName = "";
		mFieldName = "";
		mHostName = "";
		mFieldValue = "";
		MsgObj = new DBstep.iMsgServer2000();
		mFilePath = request.getSession().getServletContext().getRealPath(""); // 取得服务器路径
		try {
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				MsgObj.MsgVariant(ReadPackage(request));
				if ("DBSTEP".equalsIgnoreCase(MsgObj.GetMsgByName("DBSTEP"))) { // 检测客户端传递的数据包格式
					mOption = MsgObj.GetMsgByName("OPTION"); // 取得操作类型
					logger.info(mOption);
					if ("SIGNATRUELIST".equalsIgnoreCase(mOption)) { // 下面的代码为创建印章列表
						MsgObj.MsgTextClear(); // 清除SetMsgByName设置的值
						if (SignatureList()) { // 调入印章列表
							MsgObj.SetMsgByName("SIGNATRUELIST", mMarkList); // 设置印章列表字符串
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("创建印章列表失败!"); // 设置错误信息
						}
					}

					else if ("SIGNATRUEIMAGE".equalsIgnoreCase(mOption)) { // 下面的代码为调入印章图案
						mMarkName = MsgObj.GetMsgByName("IMAGENAME"); // 取得印章名称
						mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得用户名
						mPassword = MsgObj.GetMsgByName("PASSWORD"); // 取得印章密码
						MsgObj.MsgTextClear(); // 清除SetMsgByName设置的值
						if (SignatureImage(mMarkName, mPassword)) { // 调入印章
							MsgObj.SetMsgByName("IMAGETYPE", mFileType); // 设置图片类型
							MsgObj.MsgFileBody(BlobUtil.blobToBytes(mFileBody)); // 将签章数据信息打包
							MsgObj.SetMsgByName("STATUS", "打开成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("签名或密码错误!"); // 设置错误信息
						}
					}

					else if ("SAVESIGNATURE".equalsIgnoreCase(mOption)) { // 下面的代码为更新印章数据
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						mFieldName = MsgObj.GetMsgByName("FIELDNAME"); // 取得签章字段名称
						mFieldValue = MsgObj.GetMsgByName("FIELDVALUE"); // 取得签章数据内容
						mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得用户名称
						mDateTime = MsgObj.GetMsgByName("DATETIME"); // 取得签章日期时间
						mHostName = request.getRemoteAddr(); // 取得客户端IP
						MsgObj.MsgTextClear(); // 清除SetMsgByName设置的值
						// MsgObj.MsgFileSave(mFilePath+"/"+mRecordID+"_"+mFieldName+".gif");
						// //在服务器保存输出成图片
						if (ShowSignatureIS()) { // 判断是否已经存在签章记录
							if (UpdateSignature()) { // 更新签章数据
								MsgObj.SetMsgByName("STATUS", "更新成功!"); // 设置状态信息
								MsgObj.MsgError(""); // 清除错误信息
							} else {
								MsgObj.MsgError("保存签章信息失败!"); // 设置错误信息
							}
						} else {
							if (SaveSignature()) {
								MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
								MsgObj.MsgError(""); // 清除错误信息
							} else {
								MsgObj.MsgError("保存签章信息失败!"); // 设置错误信息
							}
						}
					}

					else if ("LOADSIGNATURE".equalsIgnoreCase(mOption)) { // 下面的代码为调入签章数据
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						mFieldName = MsgObj.GetMsgByName("FIELDNAME"); // 取得签章字段名称
						mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得用户名称
						MsgObj.MsgTextClear(); // 清除SetMsgByName设置的值
						if (LoadSignature()) { // 调入签章数据信息
							MsgObj.SetMsgByName("FIELDVALUE", mFieldValue); // 设置签章数据
							MsgObj.SetMsgByName("STATUS", "调入签批数据成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("调入签批数据失败!"); // 设置错误信息
						}
					}

					else if ("SAVEHISTORY".equalsIgnoreCase(mOption)) { // 下面的代码为保存印章历史信息
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						mFieldName = MsgObj.GetMsgByName("FIELDNAME"); // 取得签章字段名称
						mMarkName = MsgObj.GetMsgByName("MARKNAME");
						mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得用户名称
						mDateTime = MsgObj.GetMsgByName("DATETIME"); // 取得签章日期时间
						mHostName = request.getRemoteAddr(); // 取得客户端IP
						mMarkGuid = MsgObj.GetMsgByName("MARKGUID"); // 取得序列号
						MsgObj.MsgTextClear(); // 清除SetMsgByName设置的值
						if (SaveHistory()) { // 保存印章历史信息
							MsgObj.SetMsgByName("MARKNAME", mMarkName); // 将签章名称列表打包
							MsgObj.SetMsgByName("USERNAME", mUserName); // 将用户名列表打包
							MsgObj.SetMsgByName("DATETIME", mDateTime); // 将签章日期列表打包
							MsgObj.SetMsgByName("HOSTNAME", mHostName); // 将客户端IP列表打包
							MsgObj.SetMsgByName("MARKGUID", mMarkGuid); // 将序列号列表打包
							MsgObj.SetMsgByName("STATUS", "保存印章日志成功!"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.MsgError("保存印章日志失败!"); // 设置错误信息
						}
					}

					else if ("SHOWHISTORY".equalsIgnoreCase(mOption)) { // 下面的代码为打开签章历史信息
						mRecordID = MsgObj.GetMsgByName("RECORDID"); // 取得文档编号
						mFieldName = MsgObj.GetMsgByName("FIELDNAME"); // 取得签章字段名称
						mUserName = MsgObj.GetMsgByName("USERNAME"); // 取得用户名
						MsgObj.MsgTextClear(); // 清除SetMsgByName设置的值
						if (ShowHistory()) { // 调入印章历史信息
							MsgObj.SetMsgByName("MARKNAME", mMarkName); // 将签章名称列表打包
							MsgObj.SetMsgByName("USERNAME", mUserName); // 将用户名列表打包
							MsgObj.SetMsgByName("DATETIME", mDateTime); // 将签章日期列表打包
							MsgObj.SetMsgByName("HOSTNAME", mHostName); // 将客户端IP列表打包
							MsgObj.SetMsgByName("MARKGUID", mMarkGuid); // 将序列号列表打包
							MsgObj.SetMsgByName("STATUS", "调入印章日志成功"); // 设置状态信息
							MsgObj.MsgError(""); // 清除错误信息
						} else {
							MsgObj.SetMsgByName("STATUS", "调入印章日志失败"); // 设置状态信息
							MsgObj.MsgError("调入印章日志失败"); // 设置错误信息
						}
					}

					else if ("SENDMESSAGE".equalsIgnoreCase(mOption)) {
						String mCommand = MsgObj.GetMsgByName("COMMAND");
						String mInfo = MsgObj.GetMsgByName("TESTINFO");
						MsgObj.MsgTextClear();
						MsgObj.MsgFileClear();
						logger.info(mCommand);
						if ("SELFINFO".equalsIgnoreCase(mCommand)) {
							mInfo = "服务器端收到客户端传来的信息：“" + mInfo + "”\r\n";
							// 组合返回给客户端的信息
							mInfo = mInfo
									+ "服务器端发回当前服务器时间："
									+ new SimpleDateFormat(
											"yyyy-MM-dd HH:MM:ss")
											.format(new Date());
							MsgObj.SetMsgByName("RETURNINFO", mInfo); // 将返回的信息设置到信息包中
						} else {
							MsgObj.MsgError("客户端Web发送数据包命令没有合适的处理函数!["
									+ mCommand + "]");
							MsgObj.MsgTextClear();
							MsgObj.MsgFileClear();
						}
					}
				} else {
					MsgObj.MsgError("客户端发送数据包错误!");
					MsgObj.MsgTextClear();
					MsgObj.MsgFileClear();
				}
			} else {
				MsgObj.MsgError("请使用Post方法");
				MsgObj.MsgTextClear();
				MsgObj.MsgFileClear();
			}
			logger.info("SendPackage");
			SendPackage(response);
		} catch (Exception e) {
			logger.error(e.toString());
		}
	}
}