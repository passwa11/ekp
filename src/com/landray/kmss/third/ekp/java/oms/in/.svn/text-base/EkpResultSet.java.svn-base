package com.landray.kmss.third.ekp.java.oms.in;

import com.landray.kmss.constant.SysOrgConstant;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgDept;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgElement;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgGroup;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgOrg;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgPerson;
import com.landray.kmss.sys.oms.in.interfaces.DefaultOrgPost;
import com.landray.kmss.sys.oms.in.interfaces.HideRange;
import com.landray.kmss.sys.oms.in.interfaces.IOMSElementBaseAttribute;
import com.landray.kmss.sys.oms.in.interfaces.IOMSResultSet;
import com.landray.kmss.sys.oms.in.interfaces.IOrgElement;
import com.landray.kmss.sys.oms.in.interfaces.ViewRange;
import com.landray.kmss.util.DateUtil;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class EkpResultSet implements IOMSResultSet {

	protected JSONArray elmentJsonArray = null;

	protected int index = 0;

	private String[] omsTypes = new String[] { "org", "dept", "group", "post",
			"person" };

	private int[] orgTypes = new int[] { SysOrgConstant.ORG_TYPE_ORG,
			SysOrgConstant.ORG_TYPE_DEPT, SysOrgConstant.ORG_TYPE_GROUP,
			SysOrgConstant.ORG_TYPE_POST, SysOrgConstant.ORG_TYPE_PERSON };

	public EkpResultSet(String stringJsonArray) {
		if (StringUtil.isNotNull(stringJsonArray)) {
            this.elmentJsonArray = (JSONArray) JSONValue.parse(stringJsonArray);
        }
	}

	@Override
    public void open() throws Exception {
	}

	@Override
    public IOMSElementBaseAttribute getElementBaseAttribute() throws Exception {
		EkpElementBaseAttribute baseAtti = new EkpElementBaseAttribute();
		if (elmentJsonArray != null) {
			JSONObject jsonObject = (JSONObject) elmentJsonArray.get(index);
			baseAtti.setElementId((String) jsonObject.get("id"));
			baseAtti.setElementName((String) jsonObject.get("name"));
			baseAtti.setElementType(getElementType((String) jsonObject
					.get("type")));
			baseAtti.setElementUUID((String) jsonObject.get("id"));
			index++;
		}
		return baseAtti;
	}

	@Override
    public IOrgElement getElement() throws Exception {
		if (elmentJsonArray != null) {
			JSONObject jsonObject = (JSONObject) elmentJsonArray.get(index);
			index++;
			return getElement(jsonObject);
		}
		return null;
	}

	@Override
    public boolean next() throws Exception {
		if (elmentJsonArray == null) {
            return false;
        }
		return elmentJsonArray.size() > index;
	}

	@Override
    public void close() throws Exception {
	}

	public int getElementType(String sign) {
		int orgType = 0;
		for (int i = 0; i < omsTypes.length; i++) {
			if (omsTypes[i].equalsIgnoreCase(sign)) {
				orgType = orgTypes[i];
				break;
			}
		}
		return orgType;
	}

	public static void main(String[] args) {
		String s = "{\"id\":\"157fa6b08e765dff8ac2b0347ae979dd\",\"sex\":\"M\",\"name\":\"毛仡葶\",\"parent\":\"157fa6912507800e85994ec44f496bbc\",\"langProps\":{\"fdNameHK\":null,\"fdNameCN\":null,\"fdNameUS\":null,\"fdName\":\"毛仡葶\"},\"hierarchyId\":\"x15774950da35e3205d3e0974e2fbd7e5x157fa6912507800e85994ec44f496bbcx157fa6b08e765dff8ac2b0347ae979ddx\",\"type\":\"person\",\"lunid\":\"157fa6b08e765dff8ac2b0347ae979dd\",\"password\":\"c4ca4238a0b923820dcc509a6f75849b\",\"alterTime\":\"2017-03-08 09:33:17.000\",\"isAvailable\":true,\"loginName\":\"maoyt\"}";
		JSONObject o = (JSONObject) JSONValue.parse(s);
		JSONObject jo = (JSONObject) o.get("langProps");
		Map map = new HashMap();
		for (String key : (Set<String>) jo.keySet()) {
			map.put(key, jo.get(key));
		}
		for (String key : (Set<String>) map.keySet()) {
			if (map.get(key) == null) {
				System.out.println(key);
			}
		}
		System.out.println(map);

		System.out.println(o.get("langProps").getClass());

	}

	private void setRange(JSONObject jsonObject, DefaultOrgOrg elem) {
		Object obj = jsonObject.get("range");
		if (obj != null) {
			ViewRange viewRange = new ViewRange();
			JSONObject rangeObj = (JSONObject)obj;
			Object fdIsOpenLimit = rangeObj.get("fdIsOpenLimit");
			if (fdIsOpenLimit == null) {
				viewRange.setFdIsOpenLimit(false);
			} else {
				viewRange.setFdIsOpenLimit(Boolean.parseBoolean(fdIsOpenLimit.toString()));
			}
			Object fdViewType = rangeObj.get("fdViewType");
			if (fdViewType == null) {
				viewRange.setFdViewType(1);
			} else {
				viewRange.setFdViewType(Integer.parseInt(fdViewType.toString()));
			}
			Object fdViewSubType = rangeObj.get("fdViewSubType");
			if (fdViewSubType != null) {
				viewRange.setFdViewSubType(fdViewSubType.toString());
			}
			Object fdOthersObj = rangeObj.get("fdOthers");
			if (fdOthersObj != null) {
				String[] fdOthers = (String[]) ((JSONArray) fdOthersObj)
						.toArray(new String[0]);
				viewRange.setFdOther(fdOthers);
			}
			elem.setViewRange(viewRange);
		}
	}

	private void setRange(JSONObject jsonObject, DefaultOrgDept elem) {
		Object obj = jsonObject.get("range");
		if (obj != null) {
			ViewRange viewRange = new ViewRange();
			JSONObject rangeObj = (JSONObject)obj;
			Object fdIsOpenLimit = rangeObj.get("fdIsOpenLimit");
			if (fdIsOpenLimit == null) {
				viewRange.setFdIsOpenLimit(false);
			} else {
				viewRange.setFdIsOpenLimit(Boolean.parseBoolean(fdIsOpenLimit.toString()));
			}
			Object fdViewType = rangeObj.get("fdViewType");
			if (fdViewType == null) {
				viewRange.setFdViewType(1);
			} else {
				viewRange.setFdViewType(Integer.parseInt(fdViewType.toString()));
			}
			Object fdViewSubType = rangeObj.get("fdViewSubType");
			if (fdViewSubType != null) {
				viewRange.setFdViewSubType(fdViewSubType.toString());
			}
			Object fdInviteUrl = rangeObj.get("fdInviteUrl");
			if (fdInviteUrl != null) {
				viewRange.setFdInviteUrl(fdInviteUrl.toString());
			}
			Object fdOthersObj = rangeObj.get("fdOthers");
			if (fdOthersObj != null) {
				String[] fdOthers = (String[]) ((JSONArray) fdOthersObj)
						.toArray(new String[0]);
				viewRange.setFdOther(fdOthers);
			}
			elem.setViewRange(viewRange);
		}
	}

	/**
	 * 设置隐藏范围
	 * @description:
	 * @param jsonObject
	 * @param elem
	 * @return: void
	 * @author: wangjf
	 * @time: 2021/9/29 10:46 上午
	 */
	private void setHideRange(JSONObject jsonObject, DefaultOrgElement elem) {
		Object obj = jsonObject.get("hideRange");
		if (obj != null) {
			HideRange hideRange = new HideRange();
			JSONObject rangeObj = (JSONObject)obj;
			Object fdIsOpenLimit = rangeObj.get("fdIsOpenLimit");
			if (fdIsOpenLimit == null) {
				hideRange.setFdIsOpenLimit(false);
			} else {
				hideRange.setFdIsOpenLimit(Boolean.parseBoolean(fdIsOpenLimit.toString()));
			}
			Object fdViewType = rangeObj.get("fdViewType");
			if (fdViewType == null) {
				hideRange.setFdViewType(1);
			} else {
				hideRange.setFdViewType(Integer.parseInt(fdViewType.toString()));
			}

			Object fdOthersObj = rangeObj.get("fdOthers");
			if (fdOthersObj != null) {
				String[] fdOthers = (String[]) ((JSONArray) fdOthersObj)
						.toArray(new String[0]);
				hideRange.setFdOther(fdOthers);
			}
			elem.setHideRange(hideRange);
		}
	}

	private IOrgElement getElement(JSONObject jsonObject) {
		int orgType = getElementType((String) jsonObject.get("type"));
		List required = new ArrayList();
		if (orgTypes[0] == orgType) {
			// org
			DefaultOrgOrg orgElement = new DefaultOrgOrg();
			setElementBase(orgElement, jsonObject, required);
			String fieldName = "parent";
			Object tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setParent((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "thisLeader";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setThisLeader((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "superLeader";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setSuperLeader((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "docCreator";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setCreator((String)tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "admins";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
				String[] admins = (String[]) ((JSONArray) tmpJsonObj)
						.toArray(new String[0]);
				orgElement.setAuthElementAdmins(admins);
			}else{
				//做一个标志位，用于ekp与ekp之间接入时候判断使用 #159527
				orgElement.setAuthElementAdmins(new String[]{""});
			}
			required.add(fieldName);

			fieldName = "orgEmail";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setOrgEmail((String) tmpJsonObj);
            }
			required.add(fieldName);

			// 查看范围
			setRange(jsonObject, orgElement);
			required.add("range");

			// 隐藏范围
			setHideRange(jsonObject, orgElement);
			required.add("hideRange");

			orgElement.setRequiredOms(required);
			return orgElement;

		}
		if (orgTypes[1] == orgType) {
			// dept
			DefaultOrgDept orgElement = new DefaultOrgDept();
			setElementBase(orgElement, jsonObject, required);
			String fieldName = "parent";
			Object tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setParent((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "thisLeader";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setThisLeader((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "superLeader";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setSuperLeader((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "docCreator";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setCreator((String)tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "admins";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
				String[] admins = (String[]) ((JSONArray) tmpJsonObj)
						.toArray(new String[0]);
				orgElement.setAuthElementAdmins(admins);
			}else{
				//做一个标志位，用于ekp与ekp之间接入时候判断使用 #159527
				orgElement.setAuthElementAdmins(new String[]{""});
			}
			required.add(fieldName);

			fieldName = "orgEmail";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setOrgEmail((String) tmpJsonObj);
            }
			required.add(fieldName);

			// 查看范围
			setRange(jsonObject, orgElement);
			required.add("range");
			// 隐藏范围
			setHideRange(jsonObject, orgElement);
			required.add("hideRange");

			orgElement.setRequiredOms(required);
			return orgElement;
		}
		if (orgTypes[2] == orgType) {
			// group
			DefaultOrgGroup orgElement = new DefaultOrgGroup();
			setElementBase(orgElement, jsonObject, required);
			String fieldName = "members";
			Object tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
				String[] members = (String[]) ((JSONArray) tmpJsonObj)
						.toArray(new String[0]);
				orgElement.setMembers(members);
			}
			required.add(fieldName);

			fieldName = "orgEmail";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setOrgEmail((String) tmpJsonObj);
            }
			required.add(fieldName);

			orgElement.setRequiredOms(required);
			return orgElement;
		}
		if (orgTypes[3] == orgType) {
			// post
			DefaultOrgPost orgElement = new DefaultOrgPost();
			setElementBase(orgElement, jsonObject, required);
			String fieldName = "parent";
			Object tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setParent((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "thisLeader";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setThisLeader((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "persons";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
				String[] members = (String[]) ((JSONArray) tmpJsonObj)
						.toArray(new String[0]);
				orgElement.setPersons(members);
			}
			required.add(fieldName);

			orgElement.setRequiredOms(required);
			return orgElement;
		}
		if (orgTypes[4] == orgType) {
			// person
			DefaultOrgPerson orgElement = new DefaultOrgPerson();
			setElementBase(orgElement, jsonObject, required);
			String fieldName = "parent";
			Object tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setParent((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "loginName";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setLoginName((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "password";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setPassword((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "email";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setEmail((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "mobileNo";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setMobileNo((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "attendanceCardNumber";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setAttendanceCardNumber((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "workPhone";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setWorkPhone((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "rtx";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setRtx((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "sex";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
				String sex = (String) tmpJsonObj; // 读取配置 ？
				orgElement.setSex("F".equalsIgnoreCase(sex)
						|| "女".equalsIgnoreCase(sex) ? "F" : "M");
			}
			required.add(fieldName);

			fieldName = "posts";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
				String[] members = (String[]) ((JSONArray) tmpJsonObj)
						.toArray(new String[0]);
				orgElement.setPosts(members);
			}
			required.add(fieldName);

			fieldName = "shortNo";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setShortNo((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "docCreator";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setCreator((String)tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "nickName";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setNickName((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "scard";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setScard((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "wechat";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setWechat((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "isActivated";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setActivated((Boolean) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "canLogin";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setCanLogin((Boolean) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "loginNameLower";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setLoginNameLower((String) tmpJsonObj);
            }
			required.add(fieldName);

			fieldName = "isBusiness";
			tmpJsonObj = jsonObject.get(fieldName);
			if (tmpJsonObj != null) {
                orgElement.setIsBusiness((Boolean)tmpJsonObj);
            }
			required.add(fieldName);

			orgElement.setRequiredOms(required);
			return orgElement;
		}
		return null;
	}

	private void setElementBase(DefaultOrgElement org, JSONObject jsonObject,
								List required) {
		String fieldName = "id";
		Object jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
			org.setId((String) jsonObj);
			org.setImportInfo((String) jsonObj);
			required.add("importInfo");
		}
		required.add(fieldName);

		fieldName = "name";
		jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
            org.setName((String) jsonObj);
        }
		required.add(fieldName);

		fieldName = "keyword";
		jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
            org.setKeyword((String) jsonObj);
        }
		required.add(fieldName);

		fieldName = "no";
		jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
            org.setNo((String) jsonObj);
        }
		required.add(fieldName);

		fieldName = "order";
		jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
            org.setOrder(Integer.valueOf((String) jsonObj));
        }
		required.add(fieldName);

		fieldName = "isAvailable";
		jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
            org.setIsAvailable((Boolean) jsonObj);
        }
		required.add(fieldName);

		fieldName = "memo";
		jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
            org.setMemo((String) jsonObj);
        }
		required.add(fieldName);

		fieldName = "alterTime";
		jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
            org.setAlterTime(timeConvert((String) jsonObj));
        }
		required.add(fieldName);

		fieldName = "langProps";
		if (jsonObject.containsKey(fieldName)) {
			JSONObject o = (JSONObject) jsonObject.get(fieldName);
			if (jsonObj != null) {
				Map map = new HashMap();
				for (String key : (Set<String>) o.keySet()) {
					map.put(key, o.get(key));
				}
				org.setDynamicMap(map);
			}
			required.add(fieldName);
		}

		fieldName = "customProps";
		if (jsonObject.containsKey(fieldName)) {
			JSONObject o = (JSONObject) jsonObject.get(fieldName);
			if (jsonObj != null) {
				Map map = new HashMap();
				for (String key : (Set<String>) o.keySet()) {
					map.put(key, o.get(key));
				}
				org.setCustomMap(map);
			}
			required.add(fieldName);
		}

		fieldName = "isBusiness";
		jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
            org.setIsBusiness((Boolean) jsonObj);
        }

		fieldName = "isExternal";
		jsonObj = jsonObject.get(fieldName);
		if (jsonObj != null) {
            org.setExternal(jsonObj instanceof Boolean ? (Boolean) jsonObj : BooleanUtils.toBoolean(jsonObj.toString()));
        }
	}

	private Date timeConvert(String beginTime) {
		return DateUtil.convertStringToDate(beginTime, ResourceUtil
				.getString("date.format.time.msel"));
	}
}
