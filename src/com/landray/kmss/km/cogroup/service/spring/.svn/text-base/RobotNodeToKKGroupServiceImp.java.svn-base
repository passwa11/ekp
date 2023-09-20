package com.landray.kmss.km.cogroup.service.spring;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.slf4j.Logger;

import com.landray.kmss.common.model.IBaseModel;
import com.landray.kmss.km.cogroup.constant.KKContants;
import com.landray.kmss.km.cogroup.util.CogroupUtil;
import com.landray.kmss.km.cogroup.util.HttpRequest;
import com.landray.kmss.km.cogroup.util.PluginUtil;
import com.landray.kmss.km.cogroup.util.RobotUtil;
import com.landray.kmss.sys.config.dict.SysDataDict;
import com.landray.kmss.sys.config.dict.SysDictModel;
import com.landray.kmss.sys.formula.parser.FormulaParser;
import com.landray.kmss.sys.lbpm.engine.manager.task.TaskExecutionContext;
import com.landray.kmss.sys.lbpmservice.node.robotnode.support.AbstractRobotNodeServiceImp;
import com.landray.kmss.sys.organization.model.SysOrgElement;
import com.landray.kmss.sys.organization.model.SysOrgPerson;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ModelUtil;
import com.landray.kmss.util.StringUtil;
import com.landray.kmss.util.UserUtil;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * <P>机器人节点自动生成群聊</P>
 * @author 孙佳
 */
public class RobotNodeToKKGroupServiceImp extends AbstractRobotNodeServiceImp {

	private static Logger logger = org.slf4j.LoggerFactory.getLogger(RobotNodeToKKGroupServiceImp.class);

	@Override
	public void execute(TaskExecutionContext context) {
		JSONObject param = new JSONObject();
		JSONObject desc = new JSONObject();
		try {
			IBaseModel baseModel = context.getMainModel();

			List<String> userList = new ArrayList<String>();//群成员
			String moduleName = null, moduleNameEn = null, groupName = null, creater = null; //模块名称、群名称、创建者

			//获取定义的参数
			String params = getConfigContent(context);
			FormulaParser formulaParser = FormulaParser.getInstance(baseModel);
			formulaParser.addPropertiesFunc(RobotUtil.getFormulaFuncName());
			JSONObject json = JSONObject.fromObject(params);
			JSONArray array = json.getJSONArray("coparams");

			for (int i = 0; i < array.size(); i++) {
				JSONObject obj = (JSONObject) array.get(i);
				String idField = (String) obj.get("idField");
				switch (i) {
				case 0:
					moduleName = String.valueOf(obj.get("nameField"));
					break;
				case 1:
					groupName = String.valueOf(formulaParser.parseValueScript(idField, null));
					break;
				case 2:
					List<String> createrList = getFormulaLoginName(formulaParser, idField);
					if (null != createrList && createrList.size() > 0) {
						creater = createrList.get(0);
					} else {
						creater = UserUtil.getUser().getFdLoginName();
					}
					break;
				case 3:
					userList = getFormulaLoginName(formulaParser, idField);
					break;
				case 4:
					String nameField = String.valueOf(obj.get("nameField"));
					moduleNameEn = StringUtil.isNull(nameField) ? moduleName : nameField;
					break;
				default:
					break;
				}
			}

			String modelName = context.getProcessInstance().getFdModelName();
			String bizType = StringUtil.linkString(context.getNodeDefinition().getId(), "_", "robot");
			SysDictModel sysDictModel = SysDataDict.getInstance().getModel(modelName);
			//String moduleName = ResourceUtil.getString(sysDictModel.getMessageKey()); //模块名称
			String fdId = baseModel.getFdId();
			String url = ModelUtil.getModelUrl(baseModel);

			//获取文档标题
			String fdName = sysDictModel.getDisplayProperty();
			String docValue = ModelUtil.getModelPropertyString(baseModel, fdName, null, null);

			//获取创建人、创建时间
			String descUser = CogroupUtil.getModelDocCreatorProperyValue(baseModel, "docCreator", null);
			String docCreateTime = CogroupUtil.getModelDocCreateTimeProperyValue(baseModel, "docCreateTime",
					Locale.CHINESE);

			long descTime = StringUtil.isNotNull(docCreateTime)
					? DateUtil.convertStringToDate(docCreateTime, DateUtil.PATTERN_DATETIME).getTime() : -1L;

			if (StringUtil.isNull(moduleName)) {
				throw new Exception("模块名称不能为空！");
			}
			if (StringUtil.isNull(url)) {
				throw new Exception("业务URL不能为空！");
			}
			if (StringUtil.isNull(docValue)) {
				throw new Exception("文档标题不能为空！");
			}
			if (null == userList || userList.size() <= 0) {
				throw new Exception("群聊参与者不能为空！");
			}
			String simpleModel = CogroupUtil.getModelSimpleClassName(modelName, fdId);
			param.put("bizType", bizType); //业务类型
			param.put("bizTypeName", moduleName); //业务类型中文名称
			param.put("bizTypeNameEn", moduleNameEn);
			param.put("bizId", StringUtil.linkString(simpleModel, "_", fdId)); //业务ID
			param.put("bizUrl", StringUtil.formatUrl(url)); //业务URL
			param.put("groupName", groupName); //群名称

			desc.put("title", docValue); //卡片模板标题title
			desc.put("user", descUser); //卡片模板用户
			//desc.put("time", String.valueOf(descTime)); //卡片模板时间，毫秒数
			desc.put("time",
					DateUtil.convertDateToString(DateUtil.convertStringToDate(docCreateTime, null), "yyyyMMddHHmmss")); //卡片模板时间，毫秒数
			param.put("desc", desc.toString()); //描述卡片信息JSON
			param.put("creater", creater); //创建者为当前登录用户
			param.put("users", userList.toArray(new String[0])); //参与者loginName


			logger.info("请求参数：" + param);
			//System.out.println(param.toString());
			createBizGroup(param.toString());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			logger.error("", e);
		}
	}

	private void createBizGroup(String param) throws Exception {
		String kk5ServerUrl = PluginUtil.getConfig("com.landray.kmss.third.im.kk")
				.getValuebyKey(KKContants.KK_INNER_DOMAIN);
		if(StringUtil.isNull(kk5ServerUrl)){
			throw new Exception("kk内网地址为空，请先配置kk一体化！");
		}
		HttpRequest.sendPost(kk5ServerUrl + KKContants.KK_CREATE_BIZ_GROUP_API, param, null);
	}

	/**
	 * <p>通过公式解析器获取返回用户登录名</p>
	 * @return
	 * @author 孙佳
	 */
	private List<String> getFormulaLoginName(FormulaParser formulaParser, String nameField) {
		List<String> loginNameList = new ArrayList<String>();
		Object value = formulaParser.parseValueScript(nameField, null);
		if (null != value) {
			if (value instanceof ArrayList) {
				ArrayList list = (ArrayList) value;
				for (int j = 0; j < list.size(); j++) {
					Object obj_temp = list.get(j);
					if (obj_temp instanceof SysOrgElement) {
						SysOrgPerson person = (SysOrgPerson) obj_temp;
						if (person == null || loginNameList.contains(person.getFdLoginName())) {
							continue;
						}
						String loginName = person.getFdLoginName();
						loginNameList.add(loginName);
					}
				}
			} else {
				SysOrgPerson person = (SysOrgPerson) value;
				loginNameList.add(person.getFdLoginName());
			}
		}
		return loginNameList;
	}

	/*public static void main(String[] args) {
		String kk5ServerUrl = "http://192.168.2.247:8000/";
		JSONObject param = new JSONObject();
		JSONObject desc = new JSONObject();
		param.put("bizType", "N5_robot");
		param.put("bizTypeName", "新需求沟通");
		param.put("bizTypeNameEn", "新需求沟通");
		param.put("bizId", "KmReviewMain_162f1bf41905234b61864264156bb4f7");
		param.put("bizUrl", "http://www.baidu.com");
		param.put("groupName", "3333");
		param.put("cardTemplate", 0);
	
		desc.put("title", "3333");
		desc.put("user", "admin");
		desc.put("time", "1524474240000");
	
		param.put("desc", desc.toString());
	
		param.put("creater", "yirf");
		param.put("users", new String[] { "admin" });
	
		HttpRequest.sendPost(kk5ServerUrl + KKContants.KK_CREATE_BIZ_GROUP_API, param.toString());
	}*/

}
