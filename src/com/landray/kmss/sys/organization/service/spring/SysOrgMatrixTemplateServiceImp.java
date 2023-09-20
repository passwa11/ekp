package com.landray.kmss.sys.organization.service.spring;

import com.alibaba.fastjson.JSONObject;
import com.landray.kmss.common.actions.RequestContext;
import com.landray.kmss.common.dao.HQLInfo;
import com.landray.kmss.common.service.BaseServiceImp;
import com.landray.kmss.sys.lbpm.engine.builder.AbstractHandlerNode;
import com.landray.kmss.sys.lbpm.engine.builder.NodeDefinition;
import com.landray.kmss.sys.lbpm.engine.builder.ProcessDefinition;
import com.landray.kmss.sys.lbpm.engine.service.ProcessDefinitionInfo;
import com.landray.kmss.sys.lbpmservice.interfaces.Event_AfterTemplateUpdate;
import com.landray.kmss.sys.lbpmservice.interfaces.Event_BeforeTemplateDelete;
import com.landray.kmss.sys.lbpmservice.node.reviewnode.ReviewNode;
import com.landray.kmss.sys.lbpmservice.node.sendnode.SendNode;
import com.landray.kmss.sys.lbpmservice.node.signnode.SignNode;
import com.landray.kmss.sys.lbpmservice.support.model.LbpmTemplate;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmTemplateService;
import com.landray.kmss.sys.lbpmservice.support.service.ILbpmToolsService;
import com.landray.kmss.sys.organization.model.SysOrgMatrixTemplate;
import com.landray.kmss.sys.organization.service.ISysOrgMatrixTemplateService;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SysOrgMatrixTemplateServiceImp extends BaseServiceImp
        implements ISysOrgMatrixTemplateService, ApplicationListener<ApplicationEvent> {
    private Logger logger = org.slf4j.LoggerFactory.getLogger(SysOrgMatrixTemplateServiceImp.class);

    private ILbpmTemplateService lbpmTemplateService;

    // 流程小工具
    private ILbpmToolsService lbpmToolsService;

    public void setLbpmTemplateService(ILbpmTemplateService lbpmTemplateService) {
        this.lbpmTemplateService = lbpmTemplateService;
    }

    public void setLbpmToolsService(ILbpmToolsService lbpmToolsService) {
        this.lbpmToolsService = lbpmToolsService;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof Event_AfterTemplateUpdate) {
            // 新建或更新
            Event_AfterTemplateUpdate tplUpdate = (Event_AfterTemplateUpdate) event;
            LbpmTemplate template = tplUpdate.getTemplate();
            ProcessDefinitionInfo definition = null;
            try {
                definition = lbpmTemplateService.getLastDefinitionByTemplate(template);
            } catch (Exception e) {
                logger.error("查询流程模板失败：", e);
                return;
            }
            if (definition == null) {
                return;
            }

            String templateId = template.getFdModelId();
            String templateName = template.getFdModelName();
            String key = template.getFdKey();
            ProcessDefinition def = definition.getDefinition();
            List<NodeDefinition> nodes = def.getNodes();
            List<SysOrgMatrixTemplate> list = new ArrayList<SysOrgMatrixTemplate>();
            for (NodeDefinition node : nodes) {
                // SignNode, SendNode
                if (node instanceof ReviewNode || node instanceof SignNode || node instanceof SendNode) {
                    // 节点ID
                    String nodeId = node.getId();
                    // 节点名称
                    String nodeName = node.getName();
                    String handlerIds = ((AbstractHandlerNode) node).getHandlerIds();
                    String processId = def.getFdId();
                    // 处理类型：matrix
                    String type = ((AbstractHandlerNode) node).getHandlerSelectType();
                    if ("matrix".equals(type)) {
                        // {"id":"1780fba2c95a157bd8ef3524c7d83f75","idText":"11222","version":"V1","results":"1780fba73a3c41fc949c1214522ac5ab","resultsText":"岗位","option":"1","conditionals":[{"id":"1780fba73a2c4fee173bd6c44848e09d","type":"fdName","value":"$docSubject$","text":"$主题$"}]}
                        JSONObject json = JSONObject.parseObject(handlerIds);
                        String matrixId = json.getString("id");
                        String version = json.getString("version");
                        if (logger.isDebugEnabled()) {
                            logger.debug("该流程节点使用矩阵：templateId=" + templateId + ", modelName=" + templateName
                                    + ", processId=" + processId + ", nodeId=" + nodeId + ", nodeName=" + nodeName + ", matrixId=" + matrixId
                                    + ", version=" + version);
                        }
                        // 创建关系
                        SysOrgMatrixTemplate tpl = new SysOrgMatrixTemplate();
                        tpl.setFdTemplateId(templateId);
                        tpl.setFdTemplateName(templateName);
                        tpl.setFdKey(key);
                        tpl.setFdProcessId(processId);
                        tpl.setFdNodeId(nodeId);
                        tpl.setFdNodeName(nodeName);
                        tpl.setFdMatrixId(matrixId);
                        tpl.setFdMatrixVersion(version);
                        tpl.setFdModifier(UserUtil.getUser());
                        tpl.setFdModifyTime(new Date());
                        list.add(tpl);
                    } else {
                        if (logger.isDebugEnabled()) {
                            logger.debug("该流程节点未使用矩阵：processId=" + processId + ", nodeId=" + nodeId);
                        }
                    }
                }
            }
            try {
                save(list);
            } catch (Exception e) {
                logger.error("保存矩阵关系失败：", e);
            }
        } else if (event instanceof Event_BeforeTemplateDelete) {
            // 删除
            LbpmTemplate template = ((Event_BeforeTemplateDelete) event).getTemplate();
            String modelId = template.getFdModelId();
            if (logger.isDebugEnabled()) {
                String modelName = template.getFdModelName();
                logger.debug("准备删除矩阵关系：modelId=" + modelId + ", modelName=" + modelName);
            }
            try {
                deleteByTemplate(modelId);
            } catch (Exception e) {
                logger.error("删除矩阵关系失败：", e);
            }
        }
    }

    /**
     * 更新模板关系
     *
     * @param list
     * @throws Exception
     */
    private void save(List<SysOrgMatrixTemplate> list) throws Exception {
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysOrgMatrixTemplate tpl : list) {
                deleteByNode(tpl);
                add(tpl);
            }
        }
    }

    /**
     * 根据模板ID删除关系
     *
     * @param templateId
     * @throws Exception
     */
    private void deleteByTemplate(String templateId) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        hqlInfo.setWhereBlock("fdTemplateId = :templateId");
        hqlInfo.setParameter("templateId", templateId);
        List<SysOrgMatrixTemplate> list = findList(hqlInfo);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysOrgMatrixTemplate temp : list) {
                delete(temp);
            }
        }
    }

    /**
     * 根据流程模板ID删除
     *
     * @param tpl
     * @throws Exception
     */
    private void deleteByNode(SysOrgMatrixTemplate tpl) throws Exception {
        HQLInfo hqlInfo = new HQLInfo();
        // 删除模板+节点的数据（一个模板的一个节点只会关联一个矩阵）
        hqlInfo.setWhereBlock("fdNodeId = :nodeId and fdTemplateId = :templateId");
        hqlInfo.setParameter("nodeId", tpl.getFdNodeId());
        hqlInfo.setParameter("templateId", tpl.getFdTemplateId());
        List<SysOrgMatrixTemplate> list = findList(hqlInfo);
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysOrgMatrixTemplate temp : list) {
                delete(temp);
            }
        }
    }

    @Override
    public void updateTemplateVersion(RequestContext request) throws Exception {
        String matrixId = request.getParameter("matrixId");
        String isAll = request.getParameter("isAll");
        String version = request.getParameter("version");
        if ("true".equals(isAll) && StringUtil.isNotNull(matrixId)) {
            // 更新所有模板
            HQLInfo hqlInfo = new HQLInfo();
            hqlInfo.setWhereBlock("fdMatrixId = :matrixId");
            hqlInfo.setParameter("matrixId", matrixId);
            List<SysOrgMatrixTemplate> list = findList(hqlInfo);
            updateTemplateVersion(list, version);
        } else {
            // 更新指定模板
            String ids = request.getParameter("ids");
            if (StringUtil.isNotNull(ids)) {
                List<SysOrgMatrixTemplate> list = findByPrimaryKeys(ids.split(","));
                updateTemplateVersion(list, version);
            }
        }
    }

    private void updateTemplateVersion(List<SysOrgMatrixTemplate> list, String version) throws Exception {
        if (CollectionUtils.isNotEmpty(list)) {
            for (SysOrgMatrixTemplate template : list) {
                String nodeId = template.getFdProcessId() + ";" + template.getFdNodeId();
                // 更新版本号
                lbpmToolsService.updateMatrixVersion(nodeId, version);
                // 更新关系
                template.setFdMatrixVersion(version);
                update(template);
            }
        }
    }

}
