package com.landray.kmss.third.ldap;

import com.landray.kmss.util.ResourceUtil;

public class SampleDataShowDefaultImp extends SampleDataShowBaseImp {

	private void handleDept(LdapEntries depts) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (depts != null) {
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
				LdapEntry entry = depts.get(i);
				for (int j = 0; j < deptProps.length; j++) {
					if (entry.isPropertyMap(deptProps[j])) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString("third-ldap:kmss.ldap.type.dept.prop."
										+ deptProps[j]));
						sb.append("</td><td>\n");
						sb.append(getString(entry
								.getStringProperty(deptProps[j])));
						sb.append("</td></tr>\n");
					}
				}
				for (String lang : langs.keySet()) {
					if (entry.isPropertyMap("name" + lang)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString(
										"third-ldap:kmss.ldap.type.dept.prop.name")
								+ "(" + langs.get(lang) + ")");
						sb.append("</td><td>\n");
						sb.append(getString(entry
								.getStringProperty("name" + lang)));
						sb.append("</td></tr>\n");
					}
				}
				sb.append("</table>\n");
			}
		}
		deptInfo = sb.toString();
	}

	private void handlePerson(LdapEntries persons) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (persons != null) {
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
				LdapEntry entry = persons.get(i);
				System.out.println(entry.isPropertyMap("nameUS"));
				sb.append("<table border='0'>\n");
				for (int j = 0; j < personProps.length; j++) {
					if (entry.isPropertyMap(personProps[j])) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString("third-ldap:kmss.ldap.type.person.prop."
										+ personProps[j]));
						sb.append("</td><td>\n");
						sb.append(getString(entry
								.getStringProperty(personProps[j])));
						sb.append("</td></tr>\n");
					}
				}
				for (String lang : langs.keySet()) {
					if (entry.isPropertyMap("name" + lang)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString(
										"third-ldap:kmss.ldap.type.person.prop.name")
								+ "(" + langs.get(lang) + ")");
						sb.append("</td><td>\n");
						sb.append(getString(entry
								.getStringProperty("name" + lang)));
						sb.append("</td></tr>\n");
					}
				}
				for (LdapCustomProp prop : customPorps) {
					String fieldName = prop.getFieldName();
					if (entry.isPropertyMap(fieldName)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(prop.getFieldDisplayName());
						sb.append("</td><td>\n");
						sb.append(getString(entry
								.getStringProperty(fieldName)));
						sb.append("</td></tr>\n");
					}
				}
				sb.append("</table>\n");
			}
		}
		personInfo = sb.toString();
	}

	private void handlePost(LdapEntries posts) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (posts != null) {
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
				LdapEntry entry = posts.get(i);
				sb.append("<table border='0'>\n");
				for (int j = 0; j < postProps.length; j++) {
					if (entry.isPropertyMap(postProps[j])) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString("third-ldap:kmss.ldap.type.post.prop."
										+ postProps[j]));
						sb.append("</td><td>\n");
						sb.append(getString(entry
								.getStringProperty(postProps[j])));
						sb.append("</td></tr>\n");
					}
				}
				for (String lang : langs.keySet()) {
					if (entry.isPropertyMap("name" + lang)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString(
										"third-ldap:kmss.ldap.type.post.prop.name")
								+ "(" + langs.get(lang) + ")");
						sb.append("</td><td>\n");
						sb.append(getString(entry
								.getStringProperty("name" + lang)));
						sb.append("</td></tr>\n");
					}
				}
				sb.append("</table>\n");
			}
		}
		postInfo = sb.toString();
	}


	private void handleGroup(LdapEntries groups) throws Exception {
		StringBuffer sb = new StringBuffer();
		if (groups != null) {
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
				LdapEntry entry = groups.get(i);
				sb.append("<table border='0'>\n");
				for (int j = 0; j < groupProps.length; j++) {
					// huangwq 2012-7-23
					if (entry.isPropertyMap(groupProps[j])) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString("third-ldap:kmss.ldap.type.group.prop."
										+ groupProps[j]));
						sb.append("</td><td>\n");
						sb.append(entry.getStringProperty(groupProps[j]));
						sb.append("</td></tr>\n");
					}
				}
				for (String lang : langs.keySet()) {
					if (entry.isPropertyMap("name" + lang)) {
						sb.append("<tr><td class='tdb'>\n");
						sb.append(ResourceUtil
								.getString(
										"third-ldap:kmss.ldap.type.group.prop.name")
								+ "(" + langs.get(lang) + ")");
						sb.append("</td><td>\n");
						sb.append(getString(entry
								.getStringProperty("name" + lang)));
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
		LdapService service = new LdapService();
		try {
			service.connect();
			LdapEntries depts = service.getEntries("dept", null);
			handleDept(depts);
			LdapEntries persons = service.getEntries("person", null);
			handlePerson(persons);
			LdapEntries posts = service.getEntries("post", null);
			handlePost(posts);
			LdapEntries groups = service.getEntries("group", null);
			handleGroup(groups);
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
		SampleDataShowDefaultImp sd = new SampleDataShowDefaultImp();
		sd.handle();
		// TODO 自动生成的方法存根
		System.out.println(sd.getDeptInfo());
		System.out.println(sd.getPersonInfo());
		System.out.println(sd.getGroupInfo());
		System.out.println(sd.getPostInfo());
	}

}
