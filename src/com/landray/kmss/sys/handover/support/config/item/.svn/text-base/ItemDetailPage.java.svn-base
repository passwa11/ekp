package com.landray.kmss.sys.handover.support.config.item;

import java.util.ArrayList;
import java.util.List;

import com.sunbor.web.tag.Page;

/**
 * 事项交接明细列表
 * 
 * @author 潘永辉 2018年12月14日
 *
 */
public class ItemDetailPage {
	// 以下为分页信息，可以直接取Page里的数据
	private int pageno;
	private int rowsize;
	private int totalrows;
	private Page page;
	// 查询到的记录
	private List<ItemDetail> list = new ArrayList<ItemDetail>();

	public ItemDetailPage() {
	}

	public ItemDetailPage(Page page) {
		this.page = page;
		this.pageno = page.getPageno();
		this.rowsize = page.getRowsize();
		this.totalrows = page.getTotalrows();
	}

	public Page getPage() {
		return page;
	}

	public void setPage(Page page) {
		this.page = page;
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

	public int getTotalrows() {
		return totalrows;
	}

	public void setTotalrows(int totalrows) {
		this.totalrows = totalrows;
	}

	public List<ItemDetail> getList() {
		return list;
	}

	public void setList(List<ItemDetail> list) {
		this.list = list;
	}

	public ItemDetail addDetail(ItemDetail detail) {
		this.list.add(detail);
		return detail;
	}

	public ItemDetail addDetail(String fdId, String subject, String url) {
		ItemDetail detail = new ItemDetail();
		detail.setFdId(fdId);
		detail.setSubject(subject);
		detail.setUrl(url);
		return addDetail(detail);
	}

	public class ItemDetail {
		private String fdId;
		private String subject;
		private String url;
		private List<Item> item = new ArrayList<Item>();

		public String getFdId() {
			return fdId;
		}

		public void setFdId(String fdId) {
			this.fdId = fdId;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public List<Item> getItem() {
			return item;
		}

		public void setItem(List<Item> item) {
			this.item = item;
		}

		public void addItem(String name, String text) {
			item.add(new Item(name, text));
		}
	}

	public class Item {
		private String name;
		private String text;

		public Item(String name, String text) {
			this.name = name;
			this.text = text;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

	}
}
