package com.landray.kmss.code.hbm;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.landray.kmss.util.StringUtil;

@SuppressWarnings("unchecked")
public class HbmChecker {
	
	private Logger logger = org.slf4j.LoggerFactory.getLogger(this.getClass());
	
	public void scan(File dir) throws Exception {
		File[] files = dir.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			String fileName = file.getName();
			if (fileName.startsWith(".")) {
				continue;
			}
			if (file.isDirectory()) {
				scan(file);
			} else {
				if (fileName.endsWith(".hbm.xml")) {
					checkFile(file);
				}
			}
		}
	}

	private void checkFile(File file) throws Exception {
		HbmMapping mapping = HbmMapping.getInstance(file.getAbsolutePath());
		List<HbmClass> clzes = mapping.getClasses();
		for (HbmClass clz : clzes) {
			checkHbmClass(file, clz);
		}
	}

	private void checkHbmClass(File file, HbmClass clz) {
		if (clz == null) {
			return;
		}
		checkLength(file, clz, clz.getTable(), "表");
		List props = clz.getProperties();
		for (Object prop : props) {
			if (prop instanceof HbmProperty) {
				HbmProperty p = (HbmProperty) prop;
				if (StringUtil.isNotNull(p.getIndex())
						&& StringUtil.isNotNull(p.getLength())) {
					int len = Integer.valueOf(p.getLength());
					if (len > 450) {
						logger.info("文件：" + file.getName() + "中定义的属性"
								+ p.getName() + "长度为：" + len
								+ "，该列启用了索引，长度不能超过450(" + clz.getName() + ")");
					}
				}
			}
			if (prop instanceof BaseProperty) {
				checkLength(file, clz, ((BaseProperty) prop).getColumn(), "列");
			}
			if (prop instanceof HbmBag) {
				HbmBag bag = (HbmBag) prop;
				checkLength(file, clz, bag.getTable(), "表");
				if (bag.getKey() != null) {
					checkLength(file, clz, bag.getKey().getColumn(), "列");
				}
				if (bag.getManyToMany() != null) {
					checkLength(file, clz, bag.getManyToMany().getColumn(), "列");
				}
			}
			if (prop instanceof HbmList) {
				HbmIndex index = ((HbmList) prop).getIndex();
				if (index != null) {
					checkLength(file, clz, index.getColumn(), "列");
				}
			}
		}

		List<HbmSubclass> subclzes = clz.getSubclasses();
		for (HbmSubclass subclz : subclzes) {
			checkHbmClass(file, subclz);
		}
		if (clz instanceof HbmSubclass) {
			checkHbmClass(file, ((HbmSubclass) clz).getJoin());
		}
	}

	private void checkLength(File file, HbmClass clz, String name, String type) {
		if (StringUtil.isNotNull(name) && name.trim().length() > 30) {
			logger.info("文件：" + file.getName() + "中定义的" + type + "名："
					+ name + "超过30个字符！(" + clz.getName() + ")");
		}
	}

	public static void main(String[] args) throws Exception {
		new HbmChecker().scan(new File("src"));
	}
}
