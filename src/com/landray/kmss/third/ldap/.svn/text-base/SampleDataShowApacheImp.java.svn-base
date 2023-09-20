package com.landray.kmss.third.ldap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.directory.api.ldap.model.entry.Attribute;
import org.apache.directory.api.ldap.model.entry.Entry;
import org.apache.directory.api.ldap.model.entry.Value;

import com.landray.kmss.third.ldap.apache.ApacheLdapService;
import com.landray.kmss.util.ResourceUtil;
import com.landray.kmss.util.StringUtil;

public class SampleDataShowApacheImp extends SampleDataShowBaseImp {

	public SampleDataShowApacheImp() {
		super();
	}

	public boolean isPropertyMap(ApacheLdapService service, String type,
			String name) throws Exception {
		return service.getLdapContext().getLdapAttribute(type, name) != null;
	}

	private String getGUID(byte[] guid) {
		if (guid == null) {
			return null;
		}
		return StringUtil.toHexString(guid);
	}

	private String getAttName(ApacheLdapService service, String type,
			String name) {
		String attName = service.getLdapContext().getLdapAttribute(type, name);
		return attName;
	}

	public String getStringProperty(Entry entry, String attName)
			throws Exception {
		if (StringUtil.isNull(attName)) {
			return "";
		}
		if ("objectGUID".equalsIgnoreCase(attName)) {
			byte[] guid = (byte[]) getObjectProperty(entry, attName);
			return getGUID(guid);
		}
		return (String) getObjectProperty(entry, attName);
	}

	public Object getObjectProperty(Entry entry, String attName)
			throws Exception {
		if ("dn".equals(attName)) {
            return entry.getDn().getName();
        }

		Attribute attribute = getAttribute(entry, attName);
		if (attribute == null) {
			return null;
		}
		Value<?> value = attribute.get();
		if ("objectGUID".equalsIgnoreCase(attName)) {
			return value.getBytes();
		} else {
			return value.getString();
		}
	}

	private Attribute getAttribute(Entry entry, String attName) {
		for (Attribute attribute : entry.getAttributes()) {
			if (attName.equalsIgnoreCase(attribute.getId())) {
				return attribute;
			}
		}
		return null;
	}

	private void handleDept(ApacheLdapService service, List<Entry> depts)
			throws Exception {

		StringBuffer sb = new StringBuffer();
		if (depts != null) {
			String[] deptAttNames = new String[deptProps.length];
			for (int i = 0; i < deptProps.length; i++) {
				deptAttNames[i] = getAttName(service, "dept", deptProps[i]);
			}
			Map<String, String> langPropMap = new HashMap<String, String>();
			for (String lang : langs.keySet()) {
				String name = "name" + lang;
				langPropMap.put(name, getAttName(service, "dept", name));
			}

			sb.append(
					ResourceUtil.getString("ldap.sample.dept", "third-ldap")
							+ " "
							+ depts.size() + " " + ResourceUtil
									.getString("ldap.sample.item", "third-ldap")
							+ "<br>\n");
			int size = depts.size() > showCount ? showCount : depts.size();
			for (int i = 0; i < size; i++) {
				sb.append(
						ResourceUtil.getString("ldap.sample.order",
								"third-ldap") + " " + (i + 1)
								+ " "
								+ ResourceUtil.getString(
										"ldap.sample.dept.items", "third-ldap")
								+ "：<br>\n");
				sb.append("<table border='0'>\n");
				Entry entry = depts.get(i);
				for (int j = 0; j < deptProps.length; j++) {
					if (isPropertyMap(service, "dept", deptProps[j])) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString("third-ldap:kmss.ldap.type.dept.prop."
										+ deptProps[j]));
						sb.append("</td><td>\n");
						sb.append(getStringProperty(entry, deptAttNames[j]));
						sb.append("</td></tr>\n");
					}
				}
				for (String lang : langs.keySet()) {
					if (isPropertyMap(service, "dept", "name" + lang)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString(
										"third-ldap:kmss.ldap.type.dept.prop.name")
								+ "(" + langs.get(lang) + ")");
						sb.append("</td><td>\n");
						sb.append(getStringProperty(entry,
								langPropMap.get("name" + lang)));
						sb.append("</td></tr>\n");
					}
				}
				sb.append("</table>\n");
			}
		}
		deptInfo = sb.toString();
	}

	private void handlePerson(ApacheLdapService service, List<Entry> persons)
			throws Exception {

		StringBuffer sb = new StringBuffer();
		if (persons != null) {
			String[] personAttNames = new String[personProps.length];
			for (int i = 0; i < personProps.length; i++) {
				personAttNames[i] = getAttName(service, "person",
						personProps[i]);
			}
			Map<String, String> langPropMap = new HashMap<String, String>();
			for (String lang : langs.keySet()) {
				String name = "name" + lang;
				langPropMap.put(name, getAttName(service, "person", name));
			}

			sb.append(ResourceUtil.getString("ldap.sample.person", "third-ldap")
					+ " " + persons.size() + " "
					+ ResourceUtil.getString("ldap.sample.item", "third-ldap")
					+ "<br>\n");
			int size = persons.size() > showCount ? showCount : persons.size();
			for (int i = 0; i < size; i++) {
				sb.append(ResourceUtil.getString("ldap.sample.order",
						"third-ldap") + " " + (i + 1) + " "
						+ ResourceUtil.getString("ldap.sample.person.items",
								"third-ldap")
						+ "：<br>\n");
				Entry entry = persons.get(i);
				System.out.println(isPropertyMap(service, "person", "nameUS"));
				sb.append("<table border='0'>\n");
				for (int j = 0; j < personProps.length; j++) {
					if (isPropertyMap(service, "person", personProps[j])) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString("third-ldap:kmss.ldap.type.person.prop."
										+ personProps[j]));
						sb.append("</td><td>\n");
						sb.append(getStringProperty(entry, personAttNames[j]));
						sb.append("</td></tr>\n");
					}
				}
				for (String lang : langs.keySet()) {
					if (isPropertyMap(service, "person", "name" + lang)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString(
										"third-ldap:kmss.ldap.type.person.prop.name")
								+ "(" + langs.get(lang) + ")");
						sb.append("</td><td>\n");
						sb.append(getStringProperty(entry,
								langPropMap.get("name" + lang)));
						sb.append("</td></tr>\n");
					}
				}
				for (LdapCustomProp prop : customPorps) {
					String fieldName = prop.getFieldName();
					if (isPropertyMap(service, "person", fieldName)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(prop.getFieldDisplayName());
						sb.append("</td><td>\n");
						sb.append(getStringProperty(entry,
								getAttName(service, "person", fieldName)));
						sb.append("</td></tr>\n");
					}
				}
				sb.append("</table>\n");
			}
		}
		personInfo = sb.toString();
	}

	private void handlePost(ApacheLdapService service, List<Entry> posts)
			throws Exception {


		StringBuffer sb = new StringBuffer();
		if (posts != null) {
			String[] postAttNames = new String[postProps.length];
			for (int i = 0; i < postProps.length; i++) {
				postAttNames[i] = getAttName(service, "post", postProps[i]);
			}
			Map<String, String> langPropMap = new HashMap<String, String>();
			for (String lang : langs.keySet()) {
				String name = "name" + lang;
				langPropMap.put(name, getAttName(service, "post", name));
			}

			sb.append(ResourceUtil.getString("ldap.sample.post", "third-ldap")
					+ " " + posts.size() + " "
					+ ResourceUtil.getString("ldap.sample.item", "third-ldap")
					+ "<br>\n");
			int size = posts.size() > showCount ? showCount : posts.size();
			for (int i = 0; i < size; i++) {
				sb.append(
						ResourceUtil.getString("ldap.sample.order",
								"third-ldap") + " " + (i + 1)
								+ " "
								+ ResourceUtil.getString(
										"ldap.sample.post.items", "third-ldap")
								+ "：<br>\n");
				Entry entry = posts.get(i);
				sb.append("<table border='0'>\n");
				for (int j = 0; j < postProps.length; j++) {
					if (isPropertyMap(service, "post", postProps[j])) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString("third-ldap:kmss.ldap.type.post.prop."
										+ postProps[j]));
						sb.append("</td><td>\n");
						sb.append(getStringProperty(entry, postAttNames[j]));
						sb.append("</td></tr>\n");
					}
				}
				for (String lang : langs.keySet()) {
					if (isPropertyMap(service, "post", "name" + lang)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString(
										"third-ldap:kmss.ldap.type.post.prop.name")
								+ "(" + langs.get(lang) + ")");
						sb.append("</td><td>\n");
						sb.append(getStringProperty(entry,
								langPropMap.get("name" + lang)));
						sb.append("</td></tr>\n");
					}
				}
				sb.append("</table>\n");
			}
		}
		postInfo = sb.toString();
	}


	private void handleGroup(ApacheLdapService service, List<Entry> groups)
			throws Exception {

		StringBuffer sb = new StringBuffer();
		if (groups != null) {
			String[] groupAttNames = new String[groupProps.length];
			for (int i = 0; i < groupProps.length; i++) {
				groupAttNames[i] = getAttName(service, "group", groupProps[i]);
			}
			Map<String, String> langPropMap = new HashMap<String, String>();
			for (String lang : langs.keySet()) {
				String name = "name" + lang;
				langPropMap.put(name, getAttName(service, "group", name));
			}

			sb.append(ResourceUtil.getString("ldap.sample.group", "third-ldap")
					+ " " + groups.size() + " "
					+ ResourceUtil.getString("ldap.sample.item", "third-ldap")
					+ "<br>\n");
			int size = groups.size() > showCount ? showCount : groups.size();
			for (int i = 0; i < size; i++) {
				sb.append(
						ResourceUtil.getString("ldap.sample.order",
								"third-ldap") + " " + (i + 1)
								+ " "
								+ ResourceUtil.getString(
										"ldap.sample.group.itmes", "third-ldap")
								+ "：<br>\n");
				Entry entry = groups.get(i);
				sb.append("<table border='0'>\n");
				for (int j = 0; j < groupProps.length; j++) {
					// huangwq 2012-7-23
					if (isPropertyMap(service, "group", groupProps[j])) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString("third-ldap:kmss.ldap.type.group.prop."
										+ groupProps[j]));
						sb.append("</td><td>\n");
						sb.append(getStringProperty(entry, groupAttNames[j]));
						sb.append("</td></tr>\n");
					}
				}
				for (String lang : langs.keySet()) {
					if (isPropertyMap(service, "group", "name" + lang)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString(
										"third-ldap:kmss.ldap.type.group.prop.name")
								+ "(" + langs.get(lang) + ")");
						sb.append("</td><td>\n");
						sb.append(getStringProperty(entry,
								langPropMap.get("name" + lang)));
						sb.append("</td></tr>\n");
					}
				}
				sb.append("</table>\n");
			}
		}
		groupInfo = sb.toString();
	}


	@Override
    public void handle() throws Exception {
		ApacheLdapService service = new ApacheLdapService();
		try {
			service.connect();
			List<Entry> depts = service.getApacheEntries("dept", null);
			handleDept(service, depts);
			List<Entry> persons = service.getApacheEntries("person", null);
			handlePerson(service, persons);
			List<Entry> posts = service.getApacheEntries("post", null);
			handlePost(service, posts);
			List<Entry> groups = service.getApacheEntries("group", null);
			handleGroup(service, groups);
		} catch (Exception ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			service.close();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) throws Exception {
		SampleDataShowApacheImp sd = new SampleDataShowApacheImp();
		sd.handle();
		// TODO 自动生成的方法存根
		System.out.println(sd.getDeptInfo());
		System.out.println(sd.getPersonInfo());
		System.out.println(sd.getGroupInfo());
		System.out.println(sd.getPostInfo());
	}

}
