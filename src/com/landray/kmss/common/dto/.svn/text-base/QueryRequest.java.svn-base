package com.landray.kmss.common.dto;

import java.util.HashMap;
import java.util.Map;

/**
 * 列表数据请求体的结构
 *
 * @Author 严明镜
 * @create 2020/10/21 18:00
 */
public class QueryRequest {
	/**
	 * 查询条件，必须以q.开头
	 * {
	 * "q.mydoc": "approved",
	 * "q.j_path": "/approved",
	 * "q.fdImportance": 1,
	 * "q.docPublishTime": [
	 * "2020-10-08",
	 * "2020-10-15"
	 * ],
	 * "q.fdDepartment": [
	 * "174d340814522e8d27ffd90429099639",
	 * "1723622742fee11985cf46f4d17b068d",
	 * "1721c5dc2d9e287b102077f45f592d75"
	 * ],
	 * "q.docStatus": 30,
	 * "q.fdIsTop": 1,
	 * }
	 */
	private Map<String, Object> conditions = new HashMap<>();
	private Orders sorts = new Orders();
	/**
	 * 当前页
	 */
	private int pageno;
	/**
	 * 分页大小
	 */
	private int rowsize;

	public Map<String, Object> getConditions() {
		return conditions;
	}

	public void setConditions(Map<String, Object> conditions) {
		this.conditions = conditions;
	}

	public Orders getSorts() {
		return sorts;
	}

	public void setSorts(Orders sorts) {
		this.sorts = sorts;
	}

	public int getPageno() {
		return pageno;
	}

	public void setPageno(int pageno) {
		this.pageno = pageno;
	}

	public int getRowsize() {
		return rowsize;
	}

	public void setRowsize(int rowsize) {
		this.rowsize = rowsize;
	}

	public static class Orders {
		/**
		 * 排序字段
		 * "orderby": "fdIsTop;fdTopTime;docAlterTime"
		 */
		private String orderby;
		/**
		 * 排序方式
		 * "ordertype": "up"
		 */
		private String ordertype;

		public String getOrderby() {
			return orderby;
		}

		public void setOrderby(String orderby) {
			this.orderby = orderby;
		}

		public String getOrdertype() {
			return ordertype;
		}

		public void setOrdertype(String ordertype) {
			this.ordertype = ordertype;
		}
	}
}
