package com.cheng.activities;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.res.Resources.NotFoundException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.cheng.R;
import com.cheng.utils.PackageInfoUtils;
import com.cheng.utils.StreamTools;

/**
 * 欢迎页面
 */
public class SplashActivity extends Activity {

	private TextView tv_splash_version;
	private static final String TAG = "SplashActivity";
	public static final int SHOW_UPDATE_DIALOG = 1;
	public static final int ERTOR = 2;
	// 定义一个消息处理器
	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_UPDATE_DIALOG: // 如果得到的消息类型是SHOW_UPDATE_DIALOG,则显示应用更新对话框
				// 将 desc 从消息对象中获取出来
				String desc = (String) msg.obj;
				// 再显示到对话框中
				showUpdateDialog(desc);
				break;

			case ERTOR:
				Toast.makeText(SplashActivity.this, "错误码" + msg.obj,
						Toast.LENGTH_SHORT).show();
				break;
			}
		};
	};

	// private SharedPreferences mSp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);
		// 获取版本名称
		String version = PackageInfoUtils.getPackageVersion(this);
		tv_splash_version = (TextView) findViewById(R.id.tv_splash_version);
		tv_splash_version.setText("版本:" + version);
		// 开启子线程获取服务器的版本信息
		new Thread(new CheckVersionTask()).start();
	}

	/**
	 * 显示应用更新对话框
	 * 
	 * @param desc
	 *            描述
	 */
	protected void showUpdateDialog(String desc) {
		// 弹对话框的套路
		// 1 获取对话框构造器
		AlertDialog.Builder builder = new Builder(this);
		builder.setTitle("升级提醒");

		/*
		 * 这样设置 Message 比较死板;最好是把更新提醒做成服务器端的配置消息, 以后有新的版本,就可以在服务器端设置一些更新的情景描述
		 * ,比如圣诞节版本,情人节版本;* 在updateinfo.json中在添加一个“description”属性
		 */
		// builder.setMessage("发现新的版本，请升级");

		builder.setMessage(desc);
		builder.setPositiveButton("立刻升级", null);
		builder.setNegativeButton("暂不升级", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent intent = new Intent(SplashActivity.this,
						HomeActivity.class);
				startActivity(intent);
				// 因为欢迎界面只需要进入一次，所以进入主界面后，我们就把 欢迎界面finish（）掉，也就是从任务栈中移除掉
				finish(); // 表示关闭自己
			}
		});
		builder.show();
	}

	/**
	 * 获取服务器配置的最新版本号
	 */

	private class CheckVersionTask implements Runnable {

		@Override
		public void run() {
			// 1 获取服务器端资源的路径，把它定义到配置文件中，在 values 目录中定义一个 config.xml 文件
			String path = getResources().getString(R.string.url);
			// 得到路径后，要想访问这个路径就要通过 http的请求
			// 因此先封装一个 URL 对象
			try {
				URL url = new URL(path);
				HttpURLConnection conn = (HttpURLConnection) url
						.openConnection();
				// 这次请求访问的是服务器端的一个资源文件，我们使用的请求方式是个 GET
				conn.setRequestMethod("GET");
				conn.setConnectTimeout(5000);// 如果连接5s服务器未响应，就认为链接超时
				int code = conn.getResponseCode();
				if (code == 200) {
					InputStream is = conn.getInputStream();
					String result = StreamTools.readStream(is);
					JSONObject json = new JSONObject(result);
					String serverVersion = json.getString("version");
					String desc = json.getString("description");
					Log.i(TAG, "服务器版本：" + serverVersion);
					String localVersion = PackageInfoUtils
							.getPackageVersion(SplashActivity.this);
					if (localVersion.equals(serverVersion)) {
						Log.i(TAG, "版本号一致,无需升级,进入程序主界面");
					} else {
						Log.i(TAG, "版本号不一致,提示用户升级");
						// 提示用户升级的一般做法是在界面上弹出一个对话框,由用户决定是否升级
						// 弹出对话框属于更新 UI 的操作,而现在的逻辑是在子线程中,子线程是不可以更新
						// UI的,更新UI要在主线程去实现,因此使用消息机制来处理这种情况,
						// 在上面定义了一个消息处理器,在这里发出一个消息
						Message msg = Message.obtain();
						msg.what = SHOW_UPDATE_DIALOG; // 定义消息类型
						// 把获得的描述信息放到 obj中
						msg.obj = desc;
						// 发送消息
						handler.sendMessage(msg);
					}

				} else {
					Message msg = Message.obtain();
					msg.what = ERTOR; // 定义消息类型
					msg.obj = "code:410"; // 代表服务器的应答出现了问题
					handler.sendMessage(msg);
				}

			} catch (NotFoundException e) { // 出现这个异常说明是网络地址错误，找不到该路径
				Message msg = Message.obtain();
				msg.what = ERTOR; // 定义消息类型
				msg.obj = "code:404"; // 这些错误码是得有一个开发文档说明的，以后程序员就可以对照错误码处理异常了；对于用户而言，他只需要把错误码报给客服就可以得到问题的解决了
				handler.sendMessage(msg);
				e.printStackTrace();
			} catch (MalformedURLException e) {
				Message msg = Message.obtain();
				msg.what = ERTOR; // 定义消息类型
				// 发送消息
				msg.obj = "code:405";
				handler.sendMessage(msg);
				e.printStackTrace();
			} catch (IOException e) {
				Message msg = Message.obtain();
				msg.what = ERTOR; // 定义消息类型
				msg.obj = "code:408"; // 表示服务器不存在，可能是服务器当掉了
				handler.sendMessage(msg);
				e.printStackTrace();
			} catch (JSONException e) {
				Message msg = Message.obtain();
				msg.what = ERTOR; // 定义消息类型
				// 发送消息
				msg.obj = "code:409";
				handler.sendMessage(msg);
				e.printStackTrace();
			}
		}

	}

}
