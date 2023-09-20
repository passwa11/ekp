package com.landray.kmss.elec.device.vo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import com.landray.kmss.sys.authentication.util.StringUtil;
import org.apache.http.impl.client.HttpClients;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLSession;

public class DefaulttElecChannelFileVO extends AbstractElecChannelFileVO {

	private String ekpFileId;

	@Override
	public String getEkpFileId() {
		return ekpFileId;
	}

	@Override
	public IElecChannelFileVO setEkpFileId(String ekpFileId) {
		this.ekpFileId = ekpFileId;
		return this;
	}

	private String fileLocation;

	@Override
	public String getFileLocation() {
		return this.fileLocation;
	}

	@Override
	public IElecChannelFileVO setFileLocation(String fileLocation) {
		this.fileLocation = fileLocation;
		return this;
	}

	private String filePath;
	
	@Override
	public String getFilePath() {
		return this.filePath;
	}

	@Override
	public IElecChannelFileVO setFilePath(String filePath) {
		this.filePath = filePath;
		return this;
	}
	private String fileName;
	
	@Override
	public String getFileName() {
		return this.fileName;
	}

	@Override
	public IElecChannelFileVO setFileName(String fileName) {
		this.fileName = fileName;
		return this;
	}
	private String downLoadUrl;
	
	@Override
	public String getDownLoadUrl() {
		return this.downLoadUrl;
	}

	@Override
	public IElecChannelFileVO setDownLoadUrl(String url) {
		this.downLoadUrl = url;
		return this;
	}
	
	private Base64 base64;
	
	@Override
	public Base64 getBase64() {
		return this.base64;
	}

	@Override
	public IElecChannelFileVO setBase64(Base64 base64) {
		this.base64 = base64;
		return this;
	}
	
	private byte[] bytes;
	
	@Override
	public byte[] getBytes() {
		return this.bytes;
	}

	@Override
	public IElecChannelFileVO setBytes(byte[] bytes) {
		this.bytes = bytes;
		return this;
	}

	@Override
	public InputStream getFileStream() throws Exception {
		InputStream is = null;
		if(StringUtil.isNotNull(downLoadUrl)) {
			is = getFileStream(downLoadUrl);
			return is;
		}
		if(StringUtil.isNotNull(filePath)) {
			is = new FileInputStream(new File(filePath));
		}
		return is;
	}
	
	private Map<String, Object> fdAdditionalInfo;
	
	@Override
	public Map<String, Object> getFdAdditionalInfo() {
		if(fdAdditionalInfo == null) {
			fdAdditionalInfo = new HashMap<>();
		}
		return this.fdAdditionalInfo;
	}
	
	private String fdThirdNum;
	
	@Override
	public String getFdThirdNum() {
		return this.fdThirdNum;
	}

	@Override
	public IElecChannelFileVO setFdThirdNum(String fdThirdNum) {
		this.fdThirdNum = fdThirdNum;
		return this;
	}

	private InputStream getFileStream(String url) throws Exception {
		String downloadUrl = StringEscapeUtils.unescapeHtml4(url);
		CloseableHttpClient httpClient = HttpClients.custom()
				.setSSLHostnameVerifier(new HostnameVerifier() {
					@Override
					public boolean verify(String hostName, SSLSession sslSession) {
						return true; // 证书校验通过
					}
				})
//	        .setSslcontext(SSLContexts.custom().useProtocol("TLSv1.2").build())
				.build();
		HttpResponse response = httpClient.execute(new HttpGet(downloadUrl));
		HttpEntity entity = response.getEntity();
		InputStream is = entity.getContent();
		return is;
	}

	@Override
	public String toString() {
		return "DefaulttElecChannelFileVO{" +
				"fileLocation='" + fileLocation + '\'' +
				", filePath='" + filePath + '\'' +
				", fileName='" + fileName + '\'' +
				", downLoadUrl='" + downLoadUrl + '\'' +
				", base64=" + base64 +
				", bytes=" + Arrays.toString(bytes) +
				", fdAdditionalInfo=" + fdAdditionalInfo +
				", fdThirdNum='" + fdThirdNum + '\'' +
				'}';
	}
}
