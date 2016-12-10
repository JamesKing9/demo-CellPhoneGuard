package com.cheng.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * 包信息的工具类
 */
public class PackageInfoUtils {
	public static String getPackageVersion(Context context) {
		// 获取包信息 参1 包名 参2标签
		try {
			PackageInfo packinfo = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0);
			String version = packinfo.versionName;
			return version;
		} catch (NameNotFoundException e) { 
			e.printStackTrace();
			return "解析版本号失败";
		}
	}
}
