package com.landray.kmss.code.upgrade;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import com.landray.kmss.code.upgrade.v16.QuickUpgradeBuilder;

public class Runner {

	public static void main(String[] args) {

		// 显示帮助信息
		boolean isExistHelpArg = false;
		for (int i = 0; i < args.length; i++) {
			String value = args[i];
			if ("-h".equals(value)) {
				isExistHelpArg = true;
			}
		}

		if (1 == args.length && isExistHelpArg) {
			Options options = new Options();
			options.addOption("h", "help", false, "输出帮助信息");
			options.addOption("v", "version", true, "默认为升级v16,升级到指定版本,支持:v15,v16版本升级,值：15,16");
			HelpFormatter formatter = new HelpFormatter();
			formatter.printHelp("command-line", options);
			System.exit(0);
		}

		// 从输入参数中去除版本,默认是升级v16
		String version = "16";
		boolean isVersion = false;
		List<String> exeArgsList = new ArrayList<String>();
		for (int i = 0; i < args.length; i++) {
			String value = args[i];
			if ("-v".equals(value)) {
				version = args[i + 1];
				isVersion = true;
			}else {
				if(!isVersion) {
					exeArgsList.add(value);
				}
				isVersion = false;
			}
		}
		
		String[] exeArgs = new String[exeArgsList.size()];
		for (int i = 0; i < exeArgsList.size(); i++) {
			exeArgs[i] = exeArgsList.get(i);
		}

		
		switch (version) {
		case "15":
			/** v14 升级到 v15 */
			com.landray.kmss.code.upgrade.v15.Runner.main(exeArgs);
			break;
		case "16":
			/** v15 升级到 v16 */
			QuickUpgradeBuilder.main(exeArgs);
			break;
		default:
			break;
		}

	}

}
