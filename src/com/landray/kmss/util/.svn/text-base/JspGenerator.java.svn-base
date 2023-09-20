package com.landray.kmss.util;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.htmlparser.lexer.Lexer;
import org.htmlparser.nodes.TagNode;
import org.springframework.web.util.HtmlUtils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.xform.base.service.parse.ParseElement;
import com.landray.kmss.sys.xform.base.service.parse.ParseIterator;
import com.landray.kmss.sys.xform.base.service.parse.ParseUtils;
import com.landray.kmss.web.taglib.xform.TagUtils;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import sun.misc.BASE64Encoder;

public class JspGenerator {

	private static Log logger = LogFactory.getLog(JspGenerator.class);

	/**
	 * 主逻辑
	 * 
	 * @param formFile
	 * @throws Exception
	 */
	public String execGenerate(HttpServletRequest request, File formFile) throws Exception {
		if (formFile == null || !formFile.isFile() || formFile.length() < 1) {
            return null;
        }
		if (logger.isDebugEnabled()) {
            logger.debug("开始生成此表单的归档JSP模板：" + formFile.getName());
        }
		InputStream is = null;
		String fileContent = "";
		try {
			is = new FileInputStream(formFile);
			StringBuilder jsp = execute(request, is);
			fileContent = createFile(jsp.toString(), formFile);
		} catch (Exception e) {
    		logger.error(e.getMessage(), e);
    		throw new Exception(e);
		} finally {
			if (is != null) {
				IOUtils.closeQuietly(is);
			}
		}
		return fileContent;
	}

	/**
	 * 主文档JSP 转 归档JSP
	 * 
	 * @param InputStream
	 * @return StringBuilder
	 * @throws Exception
	 */
	public StringBuilder execute(HttpServletRequest request, InputStream is) throws Exception {
		String jspContent = IOUtils.toString(is,"UTF-8");
		StringBuilder jsp = new StringBuilder(jspContent);

		// 附件import
		exAttchment(jsp);
		// 权限区域控件
		exRight(jsp);
		// XForm多标签控件
		exMutiTab(jsp);
		// 关联文档控件
		exRelevance(request, jsp);
		// 地图控件
		exMap(request, jsp);
		// 二维码控件
		exQrCode(request, jsp);
		// 审批意见控件
		exShowAuditNote(jsp);
		// 选择框控件
		exRelationChoose(request, jsp);
		// 审批操作控件
		exAuditNote(jsp);
		// 新操作控件
		exNewAuditNote(jsp);
		// XXX 表格宽度调整
		return jsp;
	}

	/**
	 * 审批操作控件处理
	 * 
	 * @param jsp
	 */
	private void exAuditNote(StringBuilder jsp) {
		List<Replace> list = new ArrayList<>();

		Pattern p = Pattern.compile("<div (?:(?!</div>)[\\s\\S])*<xform:auditNote(?:(?!</div>)[\\s\\S])*</div>");
		Matcher m = p.matcher(jsp.toString());

		// 直接删除整个div
		while (m.find()) {
			list.add(new Replace(m.start(), m.end(), ""));
		}

		Collections.sort(list);
		Collections.reverse(list);
		for (Replace r : list) {
			jsp = jsp.replace(r.start, r.end, r.newChar);
		}
	}

	/**
	 * 审批操作控件处理
	 * 
	 * @param jsp
	 */
	private void exNewAuditNote(StringBuilder jsp) {
		List<Replace> list = new ArrayList<>();
		List<Replace> listNew = new ArrayList<>();
		Pattern p = Pattern.compile("<div(?:(?!</div>)[\\s\\S])*<xform:newAuditNote(?:(?!</div>)[\\s\\S])*</div>");
		Matcher m = p.matcher(jsp.toString());
		// 直接删除整个div
		while (m.find()) {
			list.add(new Replace(m.start(), m.end(), ""));
		}
		Collections.sort(list);
		Collections.reverse(list);
		for (Replace r : list) {
			jsp = jsp.replace(r.start, r.end, r.newChar);
		}
		// #83586 公文发文正常发布后，选择沉淀，在跳转到沉淀页面后，内容出现乱码。
		Pattern p1 = Pattern.compile(
				"<modeling:mright (?:(?!</modeling:mright>)[\\s\\S])*<xform:newAuditNote(?:(?!</modeling:mright>)[\\s\\S])*</modeling:mright>");
		Matcher m1 = p1.matcher(jsp.toString());
		while (m1.find()) {
			listNew.add(new Replace(m1.start(), m1.end(), ""));
		}
		Collections.sort(listNew);
		Collections.reverse(listNew);
		for (Replace r : listNew) {
			jsp = jsp.replace(r.start, r.end, r.newChar);
		}
	}

	/**
	 * 选择框控件处理
	 * 
	 * @param request
	 * @param jsp
	 */
	private void exRelationChoose(HttpServletRequest request, StringBuilder jsp) {
		List<Replace> list = new ArrayList<>();

		Pattern p = Pattern.compile(
				"<xformflag (?:(?!</xformflag>)[\\s\\S])* flagtype='xform_relation_choose' _xform_type='relationChoose'>");
		Matcher m = p.matcher(jsp.toString());

		while (m.find()) {
			String group = m.group();

			String fieldProperty = getAttrValue(group, "property='", "'");
			if (fieldProperty == null) {
                continue;
            }

			fieldProperty = getFromDetail(jsp, m, fieldProperty);

			fieldProperty = fieldProperty.replace(")", "_text)");
			Object fieldValue = TagUtils.getFieldValue(request, fieldProperty);
			if (fieldValue == null) {
                continue;
            }

			list.add(new Replace(m.end(), m.end(), fieldValue.toString()));
		}

		Collections.sort(list);
		Collections.reverse(list);
		for (Replace r : list) {
			jsp = jsp.replace(r.start, r.end, r.newChar);
		}
	}

	/**
	 * 根据在明细表中的行数获取值
	 * 
	 * @param jsp
	 * @param m
	 * @param fieldProperty
	 * @return
	 */
	private String getFromDetail(StringBuilder jsp, Matcher m, String fieldProperty) {
		if (fieldProperty.contains("${vstatus.index}")) {
			int row = 0;
			int checkStart = Math.min(100, m.start());
			String lastTdTab = jsp.substring(checkStart, m.start());
			if (StringUtil.isNotNull(lastTdTab)) {
				lastTdTab = lastTdTab.substring(lastTdTab.lastIndexOf("<td"));
				if (StringUtil.isNotNull(lastTdTab)) {
					String rowStr = getAttrValue(lastTdTab, "row=\"", "\"");
					if (rowStr != null) {
						row = Integer.parseInt(rowStr);
						row--;
					}
				}
			}
			fieldProperty = fieldProperty.replace("${vstatus.index}", String.valueOf(row));
		}
		return fieldProperty;
	}

	/**
	 * 审批意见
	 * 
	 * @param jsp
	 */
	private void exShowAuditNote(StringBuilder jsp) {
		replace(jsp, "<xform:showAuditNote id=", "<xform:showAuditNote archive=\"true\" id=");
		replace(jsp, "<xform:showAuditNote  id=", "<xform:showAuditNote archive=\"true\" id=");
	}

	/**
	 * 二维码控件
	 * 
	 * @param request
	 * @param jsp
	 * @throws Exception
	 */
	private void exQrCode(HttpServletRequest request, StringBuilder jsp) throws Exception {
		List<Replace> list = new ArrayList<>();

		Pattern p = Pattern.compile("<div (?:(?!</div>)[\\s\\S])* fd_type=\"qrCode\" (?:(?!</div>)[\\s\\S])*></div>");
		Matcher m = p.matcher(jsp.toString());

		String content;
		int width = 140;
		int height = 140;
		while (m.find()) {
			String group = m.group();

			String widthVal = getAttrValue(group, "_width=", " ");
			String heightVal = getAttrValue(group, "_height=", " ");
			String attrVal = getAttrValue(group, "content=\"", "\"");

			try {
				if (widthVal != null) {
                    width = Integer.parseInt(widthVal);
                }

				if (heightVal != null) {
                    height = Integer.parseInt(heightVal);
                }

			} catch (Exception e) {
				logger.error("获取二维码宽度或高度出错", e);
			}

			// 文档链接
			if (attrVal == null) {
				String viewUrl = "";
				String fdId = request.getParameter("fdId");
				Object obj = request.getAttribute("_mainModelName");
				if (obj != null) {
					// 从数据字典获取文档链接Url
					String modelName = obj.toString();
					// 全路径
					String _contextPath = request.getScheme() + "://" + request.getServerName() + ":"
							+ request.getServerPort() + request.getContextPath();
					viewUrl = getUrlByModelName(_contextPath, viewUrl, fdId, modelName);
				}
				logger.debug("文档链接Url=" + viewUrl);
				content = HtmlUtils.htmlUnescape(viewUrl);
			} else {
				// 自定义链接
				logger.debug("自定义链接Url=" + attrVal);
				content = HtmlUtils.htmlUnescape(attrVal);
			}

			byte[] byteArray = createQrCode(content, width, height);
			if (byteArray == null) {
                return;
            }

			String encode = new BASE64Encoder().encode(byteArray);
			String src = "data:image/png;base64," + encode;
			String qrCode = "<img height=\"" + height + "\" width=\"" + width + "\" src=\"" + src + "\"/>";
			list.add(new Replace(m.start(), m.start(), qrCode));
		}

		Collections.sort(list);
		Collections.reverse(list);
		for (Replace r : list) {
			jsp = jsp.replace(r.start, r.end, r.newChar);
		}
	}

	private String getUrlByModelName(String contextPath, String viewUrl, String fdId, String modelName) {
		SysDictModel dict = SysDataDict.getInstance().getModel(modelName);
		if (dict != null) {
			viewUrl = dict.getUrl();
			viewUrl = viewUrl.replace("${fdId}", fdId);

			if (!viewUrl.startsWith("/") && !viewUrl.startsWith("http")) {
                contextPath += "/";
            }

			viewUrl = contextPath + viewUrl;
		}
		return viewUrl;
	}

	private byte[] createQrCode(String content, int width, int height) throws Exception {
		if (content == null) {
			logger.error("生成二维码出错Url不能为空");
			return null;
		}

		// 定义二维码的参数
		HashMap<EncodeHintType, Object> hints = new HashMap<>();
		hints.put(EncodeHintType.CHARACTER_SET, "utf-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.M);
		hints.put(EncodeHintType.MARGIN, 0);

		// 生成二维码
		try {
			BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, width, height, hints);
			OutputStream os = new ByteArrayOutputStream();
			MatrixToImageWriter.writeToStream(bitMatrix, "png", os);
			byte[] byteArray = ((ByteArrayOutputStream) os).toByteArray();
			return byteArray;
		} catch (Exception e) {
			logger.error("生成二维码出错", e);
			throw e;
		}
	}

	/**
	 * 地图控件
	 * 
	 * @param request
	 * @param jsp
	 */
	private void exMap(HttpServletRequest request, StringBuilder jsp) {
		List<Replace> list = new ArrayList<>();

		Pattern p = Pattern.compile("<xformflag (?:(?!</xformflag>)[\\s\\S])* flagtype='map' _xform_type='map'>");
		Matcher m = p.matcher(jsp.toString());

		while (m.find()) {
			String group = m.group();

			String fieldProperty = getAttrValue(group, "property='", "'");
			if (fieldProperty == null) {
                continue;
            }

			fieldProperty = getFromDetail(jsp, m, fieldProperty);

			Object fieldValue = TagUtils.getFieldValue(request, fieldProperty);
			if (fieldValue == null) {
                continue;
            }

			list.add(new Replace(m.end(), m.end(), fieldValue.toString()));
		}

		Collections.sort(list);
		Collections.reverse(list);
		for (Replace r : list) {
			jsp = jsp.replace(r.start, r.end, r.newChar);
		}
	}

	/**
	 * 关联控件
	 * 
	 * @param request
	 * @param jsp
	 */
	private void exRelevance(HttpServletRequest request, StringBuilder jsp) {
		List<Replace> list = new ArrayList<>();

		Pattern p = Pattern
				.compile("<xformflag (?:(?!</xformflag>)[\\s\\S])* flagtype='relevance' _xform_type='relevance'>");
		Matcher m = p.matcher(jsp.toString());

		while (m.find()) {
			String group = m.group();

			String fieldProperty = getAttrValue(group, "property='", "'");
			if (fieldProperty == null || "".equals(fieldProperty)) {
                continue;
            }

			fieldProperty = getFromDetail(jsp, m, fieldProperty);

			Object fieldValue = TagUtils.getFieldValue(request, fieldProperty);
			if (fieldValue == null || "".equals(fieldValue.toString())) {
                continue;
            }

			JSONArray jArr = JSONArray.fromObject(fieldValue);
			if (jArr == null || jArr.size() <= 0) {
                continue;
            }

			JSONObject jObj = JSONObject.fromObject(jArr.get(0));
			if (jObj == null || !jObj.containsKey("subject")) {
                continue;
            }

			list.add(new Replace(m.end(), m.end(), jObj.get("subject").toString()));
		}

		Collections.sort(list);
		Collections.reverse(list);
		for (Replace r : list) {
			jsp = jsp.replace(r.start, r.end, r.newChar);
		}

	}

	/**
	 * 获取参数值
	 * 
	 * @param group
	 * @param beginStr
	 * @param endStr
	 * @return
	 */
	private String getAttrValue(String group, String beginStr, String endStr) {
		if (!group.contains(beginStr)) {
            return null;
        }

		int iBegin = group.indexOf(beginStr) + beginStr.length();
		int iEnd = iBegin + group.substring(iBegin).indexOf(endStr);
		String attrVal = group.substring(iBegin, iEnd);
		return attrVal;
	}

	/**
	 * 附件控件
	 * 
	 * @param jsp
	 */
	private static void exAttchment(StringBuilder jsp) {
		replace(jsp, "/sys/attachment/sys_att_main/sysAttMain_", "/resource/html_locate/sysAttMain_");
	}

	/**
	 * 权限区域控件
	 * 
	 * @param jsp
	 */
	private static void exRight(StringBuilder jsp) {
		// 直接替换sys_right为普通标签
		replace(jsp, "<xform:right", "<span");
		replace(jsp, "</xform:right>", "</span>");
	}

	/**
	 * 多标签控件
	 * 
	 * @param jsp
	 */
	private static void exMutiTab(StringBuilder jsp) {
		try {
			StringBuilder newJsp = new StringBuilder();

			Lexer lexer = ParseUtils.newLexer(jsp.toString());
			ParseIterator iterator = new ParseIterator(lexer);
			ParseElement elem;
			TagNode tNode;
			boolean deleteRow0 = false;
			while (iterator.hasNext()) {
				elem = iterator.nextNode();
				if (elem.getNode() instanceof TagNode) {
					tNode = (TagNode) elem.getNode();
					// 表格样式
					if (isType("mutiTab", tNode)) {
						tNode.setAttribute("class", "tb_normal");
						deleteRow0 = true;
					}
					// 隐藏标签头
					else if (deleteRow0 && "TR".equals(tNode.getTagName())) {
						tNode.setAttribute("style", "\"display:none\"");
						deleteRow0 = false;
					}
					// 显示所有表格
					else if ("TR".equals(tNode.getTagName())
							&& StringUtil.isNotNull(tNode.getAttribute("lks_labelname"))) {
						tNode.removeAttribute("style");
						String tmpTitleTr = "<tr class='tr_normal_title'><td align='left'>"
								+ tNode.getAttribute("lks_labelname") + "</td></tr>";
						newJsp.append(tmpTitleTr);
					}
				}
				newJsp.append(elem.getNode().toHtml());
			}
			jsp.replace(0, jsp.length(), newJsp.toString());

		} catch (Exception e) {
			logger.error("转换多标签控件错误", e);
		}
	}

	private static final boolean isType(String name, TagNode tagNode) {
		return name.equals(tagNode.getAttribute("fd_type"));
	}

	private static void replace(StringBuilder jsp, String oldStr, String newStr) {
		if (jsp == null || jsp.length() == 0 || oldStr == null || newStr == null) {
            return;
        }
		int index = jsp.indexOf(oldStr);
		int loopindex;
		while (index != -1) {
			jsp.replace(index, index + oldStr.length(), newStr);
			loopindex = jsp.substring(index + newStr.length()).indexOf(oldStr);
			if (loopindex != -1) {
				index = index + newStr.length() + loopindex;
			} else {
				index = -1;
				break;
			}
		}
	}

	/**
	 * 生成JSP文件
	 * 
	 * @param jspContent
	 * @param formFile
	 * @return filePath
	 */
	public static String createFile(String jspContent, File formFile) {
		if (formFile == null || !formFile.isFile() || formFile.length() < 1) {
            return null;
        }
		String formPath = formFile.getAbsolutePath();
		formPath = formPath.substring(0, formPath.lastIndexOf("."));
		String archPath = formPath + "_archive.jsp";
		InputStream is = null;
		OutputStream os = null;
		try {
			File archfile = new File(archPath);
			if (!archfile.exists()) {
				archfile.createNewFile();
			}
			// 覆盖
			is = IOUtils.toInputStream(jspContent,"UTF-8");
			os = new FileOutputStream(archfile);
			IOUtils.copy(is, os);
			return archPath;
		} catch (Exception e) {
			logger.error("生成归档JSP文件出错:" + archPath, e);
		} finally {
			if (is != null) {
				IOUtils.closeQuietly(is);
			}
			if (os != null) {
				IOUtils.closeQuietly(os);
			}
		}
		return formPath;
	}

	private class Replace implements Comparable<Replace> {
		int start;
		int end;
		String newChar;

		public Replace(int start, int end, String newChar) {
			this.start = start;
			this.end = end;
			this.newChar = newChar;
		}

		@Override
		public int compareTo(Replace o) {
			return (this.end < o.end) ? -1 : ((this.end == o.end) ? 0 : 1);
		}
	}
}
