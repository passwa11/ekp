package com.landray.kmss.third.im.kk.provider;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;

import com.landray.kmss.third.im.kk.model.KkNotifyLog;
import com.landray.kmss.third.im.kk.service.IKkNotifyLogService;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

/**
 * 向kk发送待办线程,解决kk返回值等待时长导致阻塞问题
 * @author zhangtian
 *
 */
public class KkTaskRunner implements Runnable {
	
//	传输数据 xml
	private String postContent;
//	服务器地址
	private String url;
//	数据类型
	private String contentType;
//	数据编码
	private String encoding;

	private IKkNotifyLogService kkLogService;
	
	private KkNotifyLog kkNotifyLog;
	
	private String subject ;
	private String notifyId;

	/**
	 * 定制使用
	 * @param postContent
	 * @param url
	 * @param port
	 * @param protocol
	 * @param contentType
	 * @param encoding
	 */
	KkTaskRunner(String postContent, String subject, String notifyId, String url, String contentType, String encoding) {
		this.postContent = postContent;
		this.url = url;
		this.contentType = contentType;
		this.encoding = encoding;
		kkLogService=(IKkNotifyLogService)SpringBeanUtil.getBean("kkNotifyLogService");
		kkNotifyLog=new KkNotifyLog();
	}

	/**
	 * 默认配置 读取notifyConfigs.properties 取得配置项
	 * @param postContent
	 * @throws Exception 
	 */
	/*	KkTaskRunner(String postContent,String subject,String notifyId) throws Exception {
			this.postContent = postContent;
			this.url = NotifyConfigUtil.getMergeNotifyConfig(KkConfigConstants.KK_SERVERURL);//
			this.url=url.replace("!{port}", NotifyConfigUtil.getMergeNotifyConfig(KkConfigConstants.KK_SERVERPORT)).replace("!{ip}",  NotifyConfigUtil.getMergeNotifyConfig("third.im.kk.serverIp"));
			this.contentType = NotifyConfigUtil.getMergeNotifyConfig(KkConfigConstants.KK_CONTENTTYPE);
			this.encoding =NotifyConfigUtil.getMergeNotifyConfig(KkConfigConstants.KK_ENCODING);
	    this.subject=subject;
	    this.notifyId=notifyId;
			//		this.port=Integer.parseInt(NotifyConfigUtil.getNotifyConfig("serverPort"));
	//		this.protocol=NotifyConfigUtil.getNotifyConfig("serverProtocol");
			kkLogService=(IKkNotifyLogService)SpringBeanUtil.getBean("kkNotifyLogService");
			kkNotifyLog=new KkNotifyLog();
			kkNotifyLog.setFdSubject(subject);
		}*/

	/**
	 * 参数检查
	 * @return
	 */
	public boolean checkParams(){
		if(StringUtil.isNull(postContent)) {
            return false;
        }
		if(StringUtil.isNull(url)) {
            return false;
        }
		if(StringUtil.isNull(contentType)) {
            return false;
        }
		if(StringUtil.isNull(encoding)) {
            return false;
        }
//		if(StringUtil.isNull(protocol)) return false;
//		if(StringUtil.isNull(encoding)) return false;
//		if(port==null) return false;
		return true;
	}
	
	@Override
    public void run() {
		if (!checkParams()) {
            return;
        }
		StringBuffer buf=new StringBuffer();
		buf.append("[subject:"+this.subject+"]\n");
		buf.append("[notifyFdID:"+this.notifyId+"]\n");
		HttpClient httpClient =new HttpClient();//new DefaultHttpClient(new ThreadSafeClientConnManager());
		//		httpClient.getHostConfiguration().setHost(url, port, protocol);
		//设置连接超时的现象
		httpClient.setConnectionTimeout(10*1000);
		httpClient.setTimeout(20*1000);
		
		PostMethod post = new PostMethod(url);
		RequestEntity entity;
		try {
			entity = new StringRequestEntity(postContent, contentType, encoding);
			post.setRequestEntity(entity);
			int result = httpClient.executeMethod(post);
			kkNotifyLog.setKkNotifyData(postContent);
			kkNotifyLog.setSendTime(new Date());
			if(result==200){
				String resString=post.getResponseBodyAsString();
				kkNotifyLog.setKkRtnMsg(resString);
			}
			else{
				String resString=post.getResponseBodyAsString();
				kkNotifyLog.setKkRtnMsg(resString);
			}
		} catch (UnsupportedEncodingException e) {
			//			e.printStackTrace();
			buf.append("[Exception:"+e.getMessage()+"]\n");
			//			kkNotifyLog.setFdParams("[Exception: "+e.getMessage()+"]");
		} catch (HttpException e) {
			//			e.printStackTrace();
			buf.append("[Exception:"+e.getMessage()+"]\n");
			//			kkNotifyLog.setFdParams("[Exception: "+e.getMessage()+"]");
		} catch (IOException e) {
			//			e.printStackTrace();
			e.printStackTrace();
			buf.append("[Exception:"+e.getMessage()+"]\n");
			//			kkNotifyLog.setFdParams("[Exception: "+e.getMessage()+"]");
		} finally {
			post.releaseConnection();
			kkNotifyLog.setFdParams(buf.toString());
			kkNotifyLog.setRtnTime(new Date());
			saveLog(kkNotifyLog);
		}
	}
	
	public void saveLog(KkNotifyLog kkNotifyLog){
		synchronized (kkLogService) {
			try {
				kkLogService.add(kkNotifyLog);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
