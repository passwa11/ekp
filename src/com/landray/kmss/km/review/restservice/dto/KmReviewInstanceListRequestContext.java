package com.landray.kmss.km.review.restservice.dto;

public class KmReviewInstanceListRequestContext extends PaddingRequestContext {
	private String fdTemplateId;        //模板ID
	private TimeRequest docCreateTime;    //创建时间（区间）
	private String docCreator;            //创建人
	private String fdCurrentHandler;    //当前处理人
	private String fdAlreadyHandler;    // 已处理人

	public String getFdTemplateId() {
		return fdTemplateId;
	}

	public void setFdTemplateId(String fdTemplateId) {
		this.fdTemplateId = fdTemplateId;
	}

	public TimeRequest getDocCreateTime() {
		return docCreateTime;
	}

	public void setDocCreateTime(TimeRequest docCreateTime) {
		this.docCreateTime = docCreateTime;
	}

	public String getDocCreator() {
		return docCreator;
	}

	public void setDocCreator(String docCreator) {
		this.docCreator = docCreator;
	}

	public String getFdCurrentHandler() {
		return fdCurrentHandler;
	}

	public String getFdAlreadyHandler() {
		return fdAlreadyHandler;
	}

	public void setFdAlreadyHandler(String fdAlreadyHandler) {
		this.fdAlreadyHandler = fdAlreadyHandler;
	}

	public void setFdCurrentHandler(String fdCurrentHandler) {
		this.fdCurrentHandler = fdCurrentHandler;
	}

	public static class TimeRequest {
		private long start;
		private long end;

		public long getStart() {
			return start;
		}

		public void setStart(long start) {
			this.start = start;
		}

		public long getEnd() {
			return end;
		}

		public void setEnd(long end) {
			this.end = end;
		}
	}
}
