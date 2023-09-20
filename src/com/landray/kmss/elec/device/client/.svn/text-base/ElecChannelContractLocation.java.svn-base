package com.landray.kmss.elec.device.client;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;

/**
*@author yucf
*@date  2020年1月6日
*@Description             文档关键字定位
*/

public class ElecChannelContractLocation implements IElecChannelRequestMessage {
	
	private static final long serialVersionUID = 1L;

	//合同文件
	private List<ContentInfo> contentList;
	
	private List<ElecChannelContractKeywordPosition> keywords;
	
	public class ContentInfo implements Serializable{
		
		private static final long serialVersionUID = 1L;

		//文件名称
		private String fileName;
		
		//文档内容
		private String base64;
		
		//文档顺序
		private int order;

		public ContentInfo(String fileName, String base64, int order) {
			super();
			this.fileName = fileName;
			this.base64 = base64;
			this.order = order;
		}

		public ContentInfo() {
			super();
		}

		public String getFileName() {
			return fileName;
		}

		public void setFileName(String fileName) {
			this.fileName = fileName;
		}

		public String getBase64() {
			return base64;
		}

		public void setBase64(String base64) {
			this.base64 = base64;
		}

		public int getOrder() {
			return order;
		}

		public void setOrder(int order) {
			this.order = order;
		}

		@Override
		public String toString() {
			return "Document [fileName=" + fileName 
					+ ", order=" + order + "]";
		}
	}

	public List<ContentInfo> getContentList() {
		return contentList;
	}

	public void setContentList(List<ContentInfo> contentList) {
		this.contentList = contentList;
	}
	
	public ElecChannelContractLocation addContent(String fileName, String base64, int order){
		
		if(CollectionUtils.isEmpty(this.contentList)){
			this.contentList = new ArrayList<>();
		}
		
		this.contentList.add(new ContentInfo(fileName, base64, order));

		return this;
	}


	public List<ElecChannelContractKeywordPosition> getKeywords() {
		return keywords;
	}


	public void setKeywords(List<ElecChannelContractKeywordPosition> keywords) {
		this.keywords = keywords;
	}
	
	public ElecChannelContractLocation addKeyword(String ... words){
		
		if(CollectionUtils.isEmpty(this.keywords)){
			this.keywords = new ArrayList<>();
		}
		
		if(words != null){
			for(String word : words){
				this.keywords.add(new ElecChannelContractKeywordPosition(word));
			}
		}
		
		return this;
	}
	
	
}
