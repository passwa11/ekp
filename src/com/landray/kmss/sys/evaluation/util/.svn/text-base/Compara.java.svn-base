package com.landray.kmss.sys.evaluation.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Compara {
	private final String icon = "<img src=\"!{path}/sys/evaluation/import/resource/images/icon_add.png\" width=\"10\" height=\"10\" contenteditable=\"false\" e_id=\"!{id}\">";

	private final String reg = "<img[^>]*e_id.*?>";

	// 是否跟之前段落点评冲突
	private Boolean conflict = Boolean.FALSE;

	// 是否取消本次段落点评
	private Boolean cancel = Boolean.FALSE;

	// 合并后内容
	private String content = "";

	private void setContent(String content) {
		this.content = content;
	}

	public Boolean getConflict() {
		return conflict;
	}

	private void setConflict(Boolean conflict) {
		this.conflict = conflict;
	}

	public Boolean getCancel() {
		return cancel;
	}

	private void setCancel(Boolean cancel) {
		this.cancel = cancel;
	}

	public String getContent() {
		return content;
	}

	// 过滤当前内容
	private String formatCur(String content, String pre) {
		if (pre.indexOf("\r\n") >= 0) {
            content = content.replaceAll("\n", "\r\n");
        }
		return content.replaceAll("<br .*?>", "<br>");
	}

	// 过滤上一版本内容
	private String formatPre(String cur, String pre) {
		return pre.replaceAll("<br .*?>", "<br>");
	}

	// 执行
	public void execute(String id, String cur, String pre,
			HttpServletRequest req) {
		this.beingConflict(id, this.formatCur(cur, pre), this.formatPre(cur,
				pre), req);
	}

	// 校验冲突的对象是否为编辑过，是则取消本次段落点评
	private void beingCancel(String cur, String pre) {
		if (!cur.replaceAll(reg, "").trim().equalsIgnoreCase(pre.replaceAll(reg, "").trim())) {
            this.cancel = Boolean.TRUE;
        }
	}

	// 处理冲突
	private void beingConflict(String id, String cur, String pre,
			HttpServletRequest req) {
		// 取消当前点评 
		this.beingCancel(cur, pre);
		if (this.getCancel()) {
            return;
        }
		// 无冲突情况，以当前版本为主
		if (cur.length() > pre.length()) {
			this.setContent(cur);
			return;
		}
		String ic = icon.replace("!{id}", id).replace("!{path}",
				req.getContextPath());
		int index = cur.indexOf(ic);

		// 不存在标志，取消更新
		if (index <= 0) {
			this.setCancel(Boolean.TRUE);
			return;
		}

		this.setConflict(Boolean.TRUE);
		StringBuffer bf = new StringBuffer(pre);
		String curL = cur.substring(0, index);
		int indexIcon = curL.replaceAll(reg, "").length() - 1;
		String indexStr = "<img src=\"/ekp/sys/evaluation/import/resource/images/icon_add.png\"" + 
							" width=\"10\" height=\"10\" contenteditable=\"false\" e_id";
		int k = pre.indexOf(indexStr);
		List<Integer> arr = new ArrayList<Integer>();
		// 计算各个标志在内容的原始位置，置于数组中
		while (k > 0) {
			pre = pre.replaceFirst(reg, "");
			arr.add(k);
			k = pre.indexOf(indexStr);
		}
		int leng = arr.size();
		int ii = 0;
		if (leng > 0 && arr.get(leng - 1) < indexIcon) {
            ii = leng;
        } else {
            for (int i = 0; i < arr.size(); i++) {
                if (arr.get(i) > indexIcon) {
                    ii = i;
                    break;
                }
            }
        }
		int icon_length = ic.length();
		bf.insert((ii * icon_length + indexIcon) + 1, ic);
		this.setContent(bf.toString());
		bf = null;
		arr = null;
	}
}
