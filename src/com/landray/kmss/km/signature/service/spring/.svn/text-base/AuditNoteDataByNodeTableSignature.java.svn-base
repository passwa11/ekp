package com.landray.kmss.km.signature.service.spring;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;

import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.attachment.forms.AttachmentDetailsForm;
import com.landray.kmss.sys.attachment.forms.IAttachmentForm;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmAuditNote;
import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AuditNoteDataShowByNode;
import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AuditNoteDataShowByTable;
import com.landray.kmss.sys.lbpmservice.taglib.auditnote.AuditNoteStyle;
import com.landray.kmss.sys.ui.taglib.template.TargetUrlContentAcquirer;
import com.landray.kmss.sys.xform.util.SysFormDingUtil;
import com.landray.kmss.util.IDGenerator;
import com.landray.kmss.util.SpringBeanUtil;
import com.landray.kmss.util.StringUtil;

public class AuditNoteDataByNodeTableSignature extends AuditNoteDataShowByNode implements AuditNoteDataShowByTable {

	private static Logger logger = LoggerFactory
			.getLogger(AuditNoteDataByNodeTableSignature.class);
	
	private IKmSignatureMainService kmSignatureMainService = null;

	public IKmSignatureMainService getKmSignatureMainService() {
		if (this.kmSignatureMainService == null) {
			this.kmSignatureMainService = (IKmSignatureMainService) SpringBeanUtil
					.getBean("kmSignatureMainService");
		}
		return this.kmSignatureMainService;
	}

	@Override
	public String showHtml(AuditNoteStyle auditNoteStyle) {
		return buildTableStyle(auditNoteStyle,false);
	}

	@Override
	public String builderStyle2Out(LbpmAuditNote lbpmAuditNote,
			AuditNoteStyle auditNoteStyle, String attachment) {
		String buildString = super.builderStyle2Out(lbpmAuditNote,
				auditNoteStyle, attachment);
		if (buildString.indexOf("${picPath}") >= 0) {
			try {
				String picPathQZ = this.getKmSignatureMainService().getPicPath(
						lbpmAuditNote);
				String imgQZ = "";
				if (StringUtil.isNotNull(picPathQZ)) {
					String[] picPathSplitQZ = picPathQZ.split(";");
					for (int i = 0; i < picPathSplitQZ.length; i++) {
						String picPath = picPathSplitQZ[i];
						String fullPicPath = this.getShowAuditNoteTag()
								.getRequest().getContextPath()
								+ picPath;
						imgQZ += "&nbsp;&nbsp;<img height='75' src='"
								+ fullPicPath
								+ "' title='"
								+ lbpmAuditNote.getFdHandler().getFdName()
								+ "' alt='"
								+ lbpmAuditNote.getFdHandler().getFdName()
								+ "'/>";
					}
				}
				if ("".equals(imgQZ)) {
					if (getAuditNoteConstant().isEmpty()) {
						return "";
					}
					if (useDingUI()) {
						buildString = buildString.replace("${picPath}",
								"暂无电子签名");
					} else {
						buildString = buildString.replace("${picPath}",
								lbpmAuditNote.getFdHandler().getFdName());
					}
				} else {
					buildString = buildString.replace("${picPath}", imgQZ);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return buildString;
	}
	
	@Override
    public void show(AuditNoteStyle auditNoteStyle) {
		String str = buildTableStyle(auditNoteStyle,false);
		try {
			this.getShowAuditNoteTag().getPrint().print(str);
		} catch (IOException e) {
			logger.error("审批意见展示标签输出HTML时出现异常", e);
		}
		
	}
	
	@Override
	public String buildTableStyle(AuditNoteStyle auditNoteStyle,boolean isWordPrint){
		String tagWidth = this.getShowAuditNoteTag().getWidth();
		// 修改默认的展示宽度，将原始宽度应用于整个表格 builderStyle2Out中将用到该值
		this.getShowAuditNoteTag().setWidth("100%");
		List<LbpmAuditNote> sysWfAuditNotes = this.getAuditNoteData();
		sortAuditNodes(sysWfAuditNotes);
		if (null == sysWfAuditNotes || sysWfAuditNotes.isEmpty()) {
			return "";
		}

		// 将同一个节点的审批意见合并
		LinkedHashMap<String, String> mapNotes = new LinkedHashMap();
		// 保存节点id和节点名称的映射关系
		Map<String, String> tempNode = new HashMap();

		List<String> listNodeIds = new ArrayList<String>();
		for (int i = 0; i < sysWfAuditNotes.size(); i++) {
			LbpmAuditNote sysWfAuditNote = sysWfAuditNotes.get(i);
			
			String attach="";
			
			//审批意见可以显示附件信息
			if(this.getShowAuditNoteTag().findForm() instanceof IAttachmentForm){
				IAttachmentForm attachmentForm=(IAttachmentForm)this.getShowAuditNoteTag().findForm();
				if(attachmentForm.getAttachmentForms().containsKey(sysWfAuditNote.getFdId())
						&&!((AttachmentDetailsForm)attachmentForm.getAttachmentForms().get(sysWfAuditNote.getFdId())).getAttachments().isEmpty()){
					String targetUrl = "";
					if (this.getShowAuditNoteTag().getMobile()) {
						targetUrl = "/sys/attachment/mobile/import/view.jsp?"
								+ "formName="
								+ this.getShowAuditNoteTag().getFormBeanName()
								+ "&fdKey=" + sysWfAuditNote.getFdId()
								+ "&fdViewType=simple";
					} else {
						targetUrl="/sys/attachment/sys_att_main/sysAttMain_view.jsp?" +
								"formBeanName="+this.getShowAuditNoteTag().getFormBeanName()+"&" +
										"fdKey="+sysWfAuditNote.getFdId()+"&" +
												"fdModelId="+this.getShowAuditNoteTag().getProcessId()+"&" +
														"fdModelName="+this.getShowAuditNoteTag().findForm().getModelClass().getName()+"&fdViewType=byte&fdForceDisabledOpt=edit&fdUID="+IDGenerator.generateID();
					}
					targetUrl = TargetUrlContentAcquirer.coverUrl(targetUrl,
							this.getShowAuditNoteTag().getPageContext());
					try {
						attach= new TargetUrlContentAcquirer(targetUrl, this.getShowAuditNoteTag().getPageContext(),
								"UTF-8").acquireString();
					} catch (JspException e) {
						logger.error("审批意见展示空间解析附件信息时异常", e);
					}
				}
			}
			if (mapNotes.containsKey(sysWfAuditNote.getFdFactNodeId())) {
				String str = mapNotes.get(sysWfAuditNote.getFdFactNodeId());
				str += builderStyle2Out(sysWfAuditNote, auditNoteStyle,attach);
				mapNotes.put(sysWfAuditNote.getFdFactNodeId(), str);
			} else {
				mapNotes.put(sysWfAuditNote.getFdFactNodeId(),
						builderStyle2Out(sysWfAuditNote, auditNoteStyle,attach));

				tempNode.put(sysWfAuditNote.getFdFactNodeId(), sysWfAuditNote
						.getFdFactNodeName());

				listNodeIds.add(sysWfAuditNote.getFdFactNodeId());
			}
		}

		StringBuffer buffer = new StringBuffer();
		String  tbClass = "tb_normal";
		String tdClass = "";
		if(isWordPrint){
			tbClass =  "tb_normal_word_print";
			tdClass = "tb_normal_td_word_print";
		}
		buffer.append("<table class='" + tbClass + "' style='width:" + tagWidth
				+ "'>");
		
		for (int i = 0; i < listNodeIds.size(); i++) {
			if (StringUtil.isNotNull(mapNotes.get(listNodeIds.get(i)))) {
				if ("true".equals(SysFormDingUtil.getEnableDing())) {
					buffer.append("<tr>");
					buffer.append("    <td style='width:30%;' class='" + tdClass
							+ " lui-ding-audit-node-name'>"
							+ tempNode.get(listNodeIds.get(i))
							+ "</td>");
					buffer.append("</tr>");
				}

				buffer.append("  <tr>");
				if (!"true".equals(SysFormDingUtil.getEnableDing())) {
					buffer.append("    <td style='width:30%' class='" + tdClass
							+ "'>" + tempNode.get(listNodeIds.get(i))
							+ "</td>");
				}
				buffer
						.append("  <td style='width:70%' class='" + tdClass + "'>" + mapNotes.get(listNodeIds.get(i))
								+ "</td>");

				buffer.append("  </tr>");
			}
		}

		buffer.append("	</table>");
		return buffer.toString();
	}
	
	@Override
    public List<LbpmAuditNote> getAuditNoteData() {

		List<LbpmAuditNote> listAuditNotes = null;
		try {
			listAuditNotes = super.getAuditNoteData();

			listAuditNotes = filterByCommonHandlerAndDept(listAuditNotes);

			listAuditNotes = this.filterByNodes(listAuditNotes);

		} catch (Exception e) {
			logger
					.error(
							"审批意见展示标签AuditNoteDataByNodeTableSignature.getAuditNoteData出现异常",
							e);
		}

		return listAuditNotes;

	}
	
	// 覆盖父类的节点过滤的方法。根据节点名排序优先时间排序
	@Override
    protected List<LbpmAuditNote> filterByNodes(
			List<LbpmAuditNote> sysWfAuditNotes) {
		String value = this.getShowAuditNoteTag().getValue();
		if (StringUtil.isNull(value) || sysWfAuditNotes == null
				|| sysWfAuditNotes.isEmpty()) {
			return sysWfAuditNotes;
		}

		String info = this.getShowAuditNoteTag().getInfo();
		JSONObject infoJson = StringUtil.isNotNull(info)
				? JSONObject.fromObject(info)
				: new JSONObject();
		String wfIdStr = infoJson.optString("wfIds");
		String[] wfIds = null;
		if (StringUtil.isNotNull(wfIdStr)) {
			wfIds = wfIdStr.split(";");
		}
		String[] nodeIds = value.split(";");
		//兼容多流程场景
		String wfIdString = "";
		String nodeIdStr = "";
		for (String nodeId : nodeIds) {
			if (nodeId.contains("##")) {
				nodeIdStr += nodeId.split("##")[0] +";";
				wfIdString += nodeId.split("##")[1] + ";";
			}
		}
		if(StringUtil.isNotNull(nodeIdStr)){
			nodeIds = nodeIdStr.split(";");
		}
		if(wfIds == null && StringUtil.isNotNull(wfIdString)){
			wfIds = wfIdString.split(";");
		}

		if (nodeIds.length == 0) {

			// 为空返回所有节点
			return sysWfAuditNotes;
		}

		List<LbpmAuditNote> sysWfAuditNoteTemp = new ArrayList<LbpmAuditNote>();
		// 优先节点的顺序
		for (int i = 0; i < nodeIds.length; i++) {

			for (int j = 0; j < sysWfAuditNotes.size(); j++) {
				LbpmAuditNote sysWfAuditNote = sysWfAuditNotes.get(j);
				// 将指定的节点id的审批记录返回
				if (wfIds != null && wfIds.length > 0) {
					String templateModelId = sysWfAuditNote.getFdProcess()
							.getFdTemplateModelId();
					if (templateModelId.equals(wfIds[i]) && nodeIds[i]
							.equals(getFactNodeId(sysWfAuditNote))) {
						sysWfAuditNoteTemp.add(sysWfAuditNote);
						break;
					}
				} else {
					if (nodeIds[i].equals(getFactNodeId(sysWfAuditNote))) {
						sysWfAuditNoteTemp.add(sysWfAuditNote);
					}
				}
			}
		}
		return sysWfAuditNoteTemp;

	}

}
