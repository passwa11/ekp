package com.landray.kmss.km.signature.handover;

import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.km.signature.model.KmSignatureMain;
import com.landray.kmss.km.signature.service.IKmSignatureMainService;
import com.landray.kmss.sys.handover.interfaces.config.HandoverExecuteContext;
import com.landray.kmss.sys.handover.support.config.item.AbstractItemHandler;
import com.landray.kmss.sys.handover.support.config.item.ItemDetailPage;
import com.landray.kmss.sys.handover.support.config.item.ItemDetailPage.ItemDetail;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.EnumerationTypeUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.SpringBeanUtil;
import com.sunbor.web.tag.Page;

/**
 * 工作交接处理类
 * 
 * @author 潘永辉 2019年3月1日
 *
 */
public class KmSignatureMainItemHandler extends AbstractItemHandler {

	private IKmSignatureMainService kmSignatureMainService;

	public IKmSignatureMainService getKmSignatureMainService() {
		if (kmSignatureMainService == null) {
			kmSignatureMainService = (IKmSignatureMainService) SpringBeanUtil.getBean("kmSignatureMainService");
		}
		return kmSignatureMainService;
	}

	public KmSignatureMainItemHandler(String fdAttribute) {
		super(fdAttribute);
	}

	@Override
	protected boolean isIgnore(HandoverExecuteContext context, IBaseModel model) {
		// 如果交接的是授权用户，接收者只能是人员
		// 交接的属性
		String property = context.getItem();
		// 接收人
		SysOrgElement toOrg = context.getTo();

		if ("fdUsers".equals(property)) {
			// 授权用户（个人签章：单值，单位签章：多值）
			if (EXEC_MODE_APPEND.equals(context.getExecMode())
					&& ((KmSignatureMain) model).getFdDocType().equals(Long.valueOf(1))) {
				return true;
			}
			// 如果有接收人，但是接收人不是人员，此次交接忽略
			if (toOrg != null && !toOrg.getFdOrgType().equals(SysOrgConstant.ORG_TYPE_PERSON)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public String getSubject(IBaseModel model) throws Exception {
		return ((KmSignatureMain) model).getFdMarkName();
	}
	
	@Override
	public ItemDetailPage detail(HQLInfo hqlInfo, String fromOrgId,
			String toOrgId, String moduleName, String item, RequestContext requestContext) throws Exception {
		// 构建查询语句
		hqlInfo.setModelName(moduleName);
		String moduleTable = ModelUtil.getModelTableName(moduleName);
		hqlInfo.setWhereBlock(String.format("%s.%s.fdId = :userId",moduleTable,item));
		hqlInfo.setParameter("userId", fromOrgId);
		// 构建前端筛选器需要拼装的通用过滤HQL(例如：通过文档标题进行搜索)
		super.buildDetailSearchFilterWhereBlock(hqlInfo, moduleName, requestContext);
		// 查询明细列表
		Page page = getKmSignatureMainService().findPage(hqlInfo);
		// 构建一个事项交接的专属列表
		ItemDetailPage detailPage = new ItemDetailPage(page);
		// 构建需要在明细列表中显示的属性
		for (Object obj : page.getList()) {
			KmSignatureMain doc = (KmSignatureMain) obj;
			// 必须：增加明细数据（明细数据基本的属性有：ID，标题，url）
			ItemDetail itemDetail = detailPage.addDetail(doc.getFdId(),
					doc.getFdMarkName(),
					"/km/signature/km_signature_main/kmSignatureMain.do?method=view&fdId=" + doc.getFdId());

			// 可选：以下增加的信息只会在明细列表中显示，不参与后续的交接逻辑
			// 签章类型
			itemDetail.addItem(
					ResourceUtil.getString("km-signature:signature.marktype"),
					EnumerationTypeUtil.getColumnEnumsLabel("km_signature_main_fdDocType",
							String.valueOf(doc.getFdDocType())));
			// 是否有效
			itemDetail.addItem(ResourceUtil.getString("km-signature:signature.docInForce"),
					EnumerationTypeUtil.getColumnEnumsLabel("common_yesno", String.valueOf(doc.getFdIsAvailable())));
			// 创建时间
			itemDetail.addItem(ResourceUtil.getString("model.fdCreateTime"),
					DateUtil.convertDateToString(doc.getDocCreateTime(),
							DateUtil.PATTERN_DATE));
		}
		return detailPage;
	}

}
