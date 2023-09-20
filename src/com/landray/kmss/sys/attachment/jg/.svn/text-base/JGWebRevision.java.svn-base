package com.landray.kmss.sys.attachment.jg;

import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.IBaseService;
import com.landray.kmss.sys.attachment.model.SysAttJgDocSignature;
import com.landray.kmss.util.SpringBeanUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

//金格pc端手写签章
public class JGWebRevision {
	private static final Logger logger = org.slf4j.LoggerFactory.getLogger(JGWebRevision.class);
	private static String mainModelName = "com.landray.kmss.sys.attachment.model.SysAttJgDocSignature";
	protected IBaseService baseServiec;
	private DBstep.iMsgServer2000 MsgObj = new DBstep.iMsgServer2000();

	protected IBaseService getBaseService() {
		if (baseServiec == null) {
			baseServiec = (IBaseService) SpringBeanUtil
					.getBean("KmssBaseService");
		}
		return baseServiec;
	}

	// 保存签章数据信息
	private boolean SaveSignature(JSONObject data) {
		boolean mResult = false;
		IBaseService baseService = getBaseService();
		SysAttJgDocSignature sysAttJgDocSignature = new SysAttJgDocSignature();
		sysAttJgDocSignature.setFdRecordID(data.getString("mRecordID"));
		sysAttJgDocSignature.setFdFieldName(data.getString("mFieldName"));
		sysAttJgDocSignature.setFdUserID(data.getString("mUserName"));
		sysAttJgDocSignature.setFdDateTime(new Date());
		sysAttJgDocSignature.setFdHostName(data.getString("mHostName"));
		sysAttJgDocSignature.setFdFieldValue(data.getString("mFieldValue"));
		try {
			baseService.add(sysAttJgDocSignature);
			mResult = true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			mResult = false;
		}
		return (mResult);
	}

	// 更新签章数据信息(服务于流程审批)
	private boolean UpdateSignature(JSONObject data) {
		boolean mResult = false;
		IBaseService baseService = getBaseService();
		try {
			List results = findByRecordIdAndUserId(baseService,
					data.getString("mRecordID"),
					data.getString("mUserName"));
			for (int i = 0; i < results.size(); i++) {
				SysAttJgDocSignature sysAttJgDocSignature = (SysAttJgDocSignature) results
						.get(i);
				updateModel(baseService, sysAttJgDocSignature, data);
			}
			mResult = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			mResult = false;
		}
		return (mResult);
	}

	private List findByRecordIdAndUserId(IBaseService baseService,
			String recordID, String userId) throws Exception {
		List<Object> results = new ArrayList();
		HQLInfo hql = getCommonHql();
		String tableName = hql.getModelTable();
		hql.setWhereBlock(tableName + ".fdRecordID =:fdRecordID AND "
				+ tableName + ".fdUserID =:fdUserID");
		hql.setParameter("fdRecordID", recordID);
		hql.setParameter("fdUserID", userId);
		Object obj = baseService.findFirstOne(hql);
		if(obj!=null) {
            results.add(obj);
        }
		return results;
	}

	/**
	 * 通用hql
	 * 
	 * @return
	 */
	private HQLInfo getCommonHql() {
		HQLInfo hql = new HQLInfo();
		hql.setModelName(mainModelName);
		return hql;
	}

	// 更新签章数据信息(服务于自定义表单)
	private boolean UpdateSignature_Xform(JSONObject data) {
		boolean mResult = false;
		IBaseService baseService = getBaseService();
		try {
			List results = findByRecordIdAndFieldName(baseService,
					data.getString("mRecordID"),
					data.getString("mFieldName"));
			for (int i = 0; i < results.size(); i++) {
				SysAttJgDocSignature sysAttJgDocSignature = (SysAttJgDocSignature) results
						.get(i);
				updateModel(baseService, sysAttJgDocSignature, data);
			}
			mResult = true;
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			mResult = false;
		}
		return (mResult);
	}

	private List findByRecordIdAndFieldName(IBaseService baseService,
			String recordID, String fieldName) throws Exception {
		List<Object> results = new ArrayList();
		HQLInfo hql = getCommonHql();
		String tableName = hql.getModelTable();
		hql.setWhereBlock(tableName + ".fdRecordID =:fdRecordID AND "
				+ tableName + ".fdFieldName =:fdFieldName");
		hql.setParameter("fdRecordID", recordID);
		hql.setParameter("fdFieldName", fieldName);
		Object obj = baseService.findFirstOne(hql);
		if(obj!=null) {
            results.add(obj);
        }
		return results;
	}

	private void updateModel(IBaseService baseService,
			SysAttJgDocSignature sysAttJgDocSignature, JSONObject data)
			throws Exception {
		sysAttJgDocSignature.setFdDateTime(new Date());
		sysAttJgDocSignature.setFdHostName(data.getString("mHostName"));
		sysAttJgDocSignature.setFdFieldValue(data.getString("mFieldValue"));
		baseService.update(sysAttJgDocSignature);
	}

	// 调出签章数据信息(服务于流程审批)
	private String LoadSignature(JSONObject data) {
		String mResult = "";
		IBaseService baseService = getBaseService();
		try {
			List results = findByRecordIdAndUserId(baseService,
					data.getString("mRecordID"),
					data.getString("mUserName"));
			if (results.size() > 0) {
				SysAttJgDocSignature sysAttJgDocSignature = (SysAttJgDocSignature) results
						.get(0);
				mResult = sysAttJgDocSignature.getFdFieldValue();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return mResult;
	}

	// 调出签章数据信息(服务于自定义表单)
	private String LoadSignature_Xform(JSONObject data) {
		String mResult = "";
		IBaseService baseService = getBaseService();
		try {
			List results = findByRecordIdAndFieldName(baseService,
					data.getString("mRecordID"),
					data.getString("mFieldName"));
			if (results.size() > 0) {
				SysAttJgDocSignature sysAttJgDocSignature = (SysAttJgDocSignature) results
						.get(0);
				mResult = sysAttJgDocSignature.getFdFieldValue();
			}
		} catch (Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return mResult;
	}

	// 判断是否存在手写签章
	public boolean webRevisionIsExits(String fdRecordID, String fdUserID)
			throws Exception {
		boolean mResult = false;
		HQLInfo hql = new HQLInfo();
		hql.setModelName(mainModelName);
		String tableName = hql.getModelTable();
		String whereBlock = tableName + ".fdRecordID =:fdRecordID and "
				+ tableName + ".fdUserID =:fdUserID";
		hql.setWhereBlock(whereBlock);
		hql.setParameter("fdRecordID", fdRecordID);
		hql.setParameter("fdUserID", fdUserID);
		IBaseService baseService = getBaseService();
		List values = baseService.findList(hql);
		if (values.size() > 0) {
			mResult = true;
			}
		return mResult;
	}

	// 取得客户端发来的数据包
	private byte[] ReadPackage(HttpServletRequest request) {
		byte[] mStream = null;
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
			logger.error(e.toString());
		}
	}

	// 具体处理客户端控件请求的函数
	public void ExecuteRun(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if ("POST".equalsIgnoreCase(request.getMethod())) {
				MsgObj.MsgVariant(ReadPackage(request));
				if ("DBSTEP".equalsIgnoreCase(MsgObj.GetMsgByName("DBSTEP"))) { // 检测客户端传递的数据包格式
					JSONObject data = new JSONObject();
					data.accumulate("mRecordID",
							MsgObj.GetMsgByName("RECORDID")); // 取得文档编号
					data.accumulate("mFieldName",
							MsgObj.GetMsgByName("FIELDNAME"));// 取得签章字段名称
					data.accumulate("mFieldValue",
							MsgObj.GetMsgByName("FIELDVALUE")); // 取得签章数据内容
					data.accumulate("mUserName",
							MsgObj.GetMsgByName("USERNAME"));// 取得用户名称
					data.accumulate("mDateTime",
							MsgObj.GetMsgByName("DATETIME"));// 取得签章日期时间
					data.accumulate("mHostName", request.getRemoteAddr());// 取得客户端IP
					String mOption = MsgObj.GetMsgByName("OPTION"); // 取得操作类型，即页面调用的方法名
					MsgObj.MsgTextClear(); // 清除SetMsgByName设置的值
					if ("SAVESIGNATURE".equalsIgnoreCase(mOption)) { // 下面的代码为更新印章数据
						// //在服务器保存输出成图片
						if (webRevisionIsExits(data.getString("mRecordID"),
								data.getString("mUserName"))) { // 判断是否已经存在签章记录
							if (data.getString("mFieldName").indexOf("Xform") > -1) {
								if (UpdateSignature_Xform(data)) { // 更新签章数据
									MsgObj.SetMsgByName("STATUS", "更新成功!"); // 设置状态信息
									MsgObj.MsgError(""); // 清除错误信息
								} else {
									MsgObj.MsgError("保存签章信息失败!"); // 设置错误信息
								}
							} else {
								if (UpdateSignature(data)) { // 更新签章数据
									MsgObj.SetMsgByName("STATUS", "更新成功!"); // 设置状态信息
									MsgObj.MsgError(""); // 清除错误信息
								} else {
									MsgObj.MsgError("保存签章信息失败!"); // 设置错误信息
								}
							}
						} else {
							if (SaveSignature(data)) { // 保存签章数据
								MsgObj.SetMsgByName("STATUS", "保存成功!"); // 设置状态信息
								MsgObj.MsgError(""); // 清除错误信息
							} else {
								MsgObj.MsgError("保存签章信息失败!"); // 设置错误信息
							}
						}
					} else if ("LOADSIGNATURE".equalsIgnoreCase(mOption)) { // 下面的代码为调入签章数据
						String fieldValue = "";
						if (data.getString("mFieldName")
								.indexOf("Xform") > -1) {
							fieldValue = LoadSignature_Xform(data);
						} else {
							fieldValue = LoadSignature(data);
						}
						MsgObj.SetMsgByName("FIELDVALUE", fieldValue); // 设置签章数据
						MsgObj.SetMsgByName("STATUS", "调入签批数据成功!"); // 设置状态信息
						MsgObj.MsgError(""); // 清除错误信息
					} else if ("SENDMESSAGE".equalsIgnoreCase(mOption)) {
						String mCommand = MsgObj.GetMsgByName("COMMAND");
						String mInfo = MsgObj.GetMsgByName("TESTINFO");
						logger.info(mInfo);
						MsgObj.MsgTextClear();
						MsgObj.MsgFileClear();
						logger.info(mCommand);
						if ("SELFINFO".equalsIgnoreCase(mCommand)) {
							mInfo = "服务器端收到客户端传来的信息：“" + mInfo + "”\r\n";
							// 组合返回给客户端的信息
							SimpleDateFormat formatter = new SimpleDateFormat(
									"yyyy-MM-dd HH:mm:ss");
							String currentTime = formatter.format(new Date());
							mInfo = mInfo + "服务器端发回当前服务器时间：" + currentTime;
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
			SendPackage(response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
