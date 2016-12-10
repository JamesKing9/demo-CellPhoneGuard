package com.cheng.activities;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.cheng.R;
/**
 * 应用的主界面
 */
public class HomeActivity extends Activity implements OnClickListener,
		OnItemClickListener {
	private ImageView iv_home_logo;
	private GridView gv_item;
	private ImageButton ibSet;

	private final static String[] names = new String[] { "手机防盗", "骚扰拦截",
			"软件管家", "进程管理", "流量统计", "手机杀毒", "缓存清理", "常用工具" };

	private final static int[] icons = new int[] { R.drawable.sjfd,
			R.drawable.srlj, R.drawable.rjgj, R.drawable.jcgl, R.drawable.lltj,
			R.drawable.sjsd, R.drawable.hcql, R.drawable.cygj };

	private final static String[] desc = new String[] { "远程定位手机", "全面拦截骚扰",
			"管理您的软件", "管理运行进程", "流量一目了然", "病毒无处藏身", "系统快如火箭", "工具大全" };

	// private static final String TAG = "HomeActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		// Logger.d("HomeActivity");

		// 初始化控件
		iv_home_logo = (ImageView) findViewById(R.id.iv_home_logo);
		gv_item = (GridView) findViewById(R.id.gv_home_item);

		// ibSet = (ImageButton) findViewById(R.id.ib_home_set);
		// // 设置点击事件
		// ibSet.setOnClickListener(this);

		// iv_home_logo.setRotationY(rotationY)
		// logo 的转动的属性动画
		// 参1：是需要产生动画的view 对爱； 参2： 动画改变的属性(这里是 Y 轴的旋转属性); 后面的参数：定义的是 动画的旋转的范围
		ObjectAnimator oa = ObjectAnimator.ofFloat(iv_home_logo, "rotationY",
				45, 90, 135, 180, 225, 270, 315); // 沿着 rotationY 轴旋转一圈
		oa.setDuration(3000);// 时间：3s转一圈
		oa.setRepeatCount(ObjectAnimator.INFINITE);// 设置次数 这里是无限次
		oa.setRepeatMode(ObjectAnimator.RESTART);// 重复模式 这里时从头开始
		oa.start();// 开启动画；（注意：属性动画是从 3.0（API 11）开始支持的，所以要在 Mainfest中将
					// minSdkVersion 改为 11，才能使用这种动画）

		// 初始化格子布局的view
		gv_item.setAdapter(new HomeAdapter());

		// 设置单条点击事件
		gv_item.setOnItemClickListener(this);
	}

	private class HomeAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			return names.length;
		}

		@Override
		public Object getItem(int position) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			View view = View.inflate(getApplicationContext(),
					R.layout.item_home, null);
			// 取出控件
			ImageView iv = (ImageView) view.findViewById(R.id.iv_homeitem_icon);
			TextView tv_item_title = (TextView) view
					.findViewById(R.id.tv_homeitem_title);
			TextView tv_item_desc = (TextView) view
					.findViewById(R.id.tv_homeitem_desc);
			// 控件赋值
			iv.setImageResource(icons[position]);
			tv_item_title.setText(names[position]);
			tv_item_desc.setText(desc[position]);

			// 数据设置好后将 view 对象 return
			return view;
		}

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ib_home_set:
			// 跳转设置页面
//			startActivity(new Intent(this, SettingActivity.class));
			break;
		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			// 手机防盗
//			showFdDialog();
			break;
		case 1:
			// 骚扰拦截
//			startActivity(new Intent(this, BlackListActivity.class));
			break;
		case 2:
			// 软件管理
//			startActivity(new Intent(this, AppManagerActivity.class));
			break;
		case 3:
			// 进程管理
//			startActivity(new Intent(this, ProcessManagerActivity.class));
			break;
		case 4:
			// 流量统计
//			startActivity(new Intent(this, TrafficActivity.class));
			break;
		case 5:
			// 手机杀毒
//			startActivity(new Intent(this, AntiVirusActivity.class));
			break;
		case 6:
			// 缓存清理
//			startActivity(new Intent(this, CacheCleanActivity.class));
			break;
		case 7:
			// 常用工具
//			startActivity(new Intent(this, CommonToolsActivity.class));
			break;

		default:
			break;
		}
	}

	/**
	 * 弹出对话框
	 */
//	private void showFdDialog() {
//		// 取出设置的密码
////		String psw = PreferenceUtils.getString(getApplicationContext(),
////				Constants.SJFD_PSW);
//
//		if (!TextUtils.isEmpty(psw)) {
//			// 不为空 去验证密码
//			ShowEnterPswDialog();
//		} else {
//			// 初始化密码
//			ShowInitPswDialog();
//		}
//	}

	/**
	 * 密码验证对话框
	 */
//	private void ShowEnterPswDialog() {
//		AlertDialog.Builder builder = new AlertDialog.Builder(this);
//		// 生成自定义的布局view 上下午必须用this
////		View dialogView = View.inflate(this, R.layout.dialog_enterpsw, null);
//		// 给dialog设置自定义的布局
////		builder.setView(dialogView);
////
////		Button btnConfirm = (Button) dialogView
////				.findViewById(R.id.btn_dep_confirm);
////		Button btnCancel = (Button) dialogView
////				.findViewById(R.id.btn_dep_cancel);
////		final EditText etPsw = (EditText) dialogView
////				.findViewById(R.id.et_dep_psw);
//
//		final AlertDialog dialog = builder.create();
//		// 设置点击事件
//		btnConfirm.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 确定点击事件
//				// 取出密码
//				String psw = etPsw.getText().toString().trim();
//				if (TextUtils.isEmpty(psw)) {
//					ToastUtils.show(getApplicationContext(), "密码不能为空");
//					return;
//				}
//				// 取出保存的密码
//				String pswSave = PreferenceUtils.getString(
//						getApplicationContext(), Constants.SJFD_PSW);
//
//				// 验证密码
//				if (TextUtils.equals(pswSave, psw)) {
//					// 判断是否全部设置完成
//					boolean isSetOver = PreferenceUtils.getBoolean(
//							getApplicationContext(), Constants.SJFD_SET_OVER,
//							false);
//					if (isSetOver) {
//						// 进入手机防盗主页面
//						enterSjfd();
//					} else {
//						// 进入手机防盗的向导页面
//						enterSjfdSet1();
//					}
//					// 弹出框消失
//					dialog.dismiss();
//				} else {
//					ToastUtils.show(getApplicationContext(), "密码错误");
//				}
//			}
//		});
//		btnCancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				// 取消点击事件
//				dialog.dismiss();
//			}
//		});
//		dialog.show();
//	}

	/**
	 * 进入手机防盗主页面
	 *//*
	protected void enterSjfd() {
//		startActivity(new Intent(this, SjfdActivity.class));
	}*/

	/**
	 * 手机防盗向导1页面
	 */
	/*protected void enterSjfdSet1() {
//		startActivity(new Intent(this, SjfdSet1Activity.class));
	}*/

	/**
	 * 初始化密码对话框
	 */
	/*private void ShowInitPswDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		// 生成自定义的布局view 上下午必须用this
		View dialogView = View.inflate(this, R.layout.dialog_initpsw, null);
		// 给dialog设置自定义的布局
		builder.setView(dialogView);

		Button btnConfirm = (Button) dialogView
				.findViewById(R.id.btn_dip_confirm);
		Button btnCancel = (Button) dialogView
				.findViewById(R.id.btn_dip_cancel);
		final EditText etPsw = (EditText) dialogView
				.findViewById(R.id.et_dip_psw);
		final EditText etPswRe = (EditText) dialogView
				.findViewById(R.id.et_dip_pswre);

		final AlertDialog dialog = builder.create();
		// 设置点击事件
		btnConfirm.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 确定点击事件
				// 取出密码
				String psw = etPsw.getText().toString().trim();
				String pswRe = etPswRe.getText().toString().trim();
				if (TextUtils.isEmpty(psw)) {
					ToastUtils.show(getApplicationContext(), "密码不能为空");
					return;
				}
				if (TextUtils.isEmpty(pswRe)) {
					ToastUtils.show(getApplicationContext(), "确认密码不能为空");
					return;
				}
				if (!TextUtils.equals(psw, pswRe)) {
					ToastUtils.show(getApplicationContext(), "两次密码不一致");
					return;
				}

				// 保存密码
				PreferenceUtils.putString(getApplicationContext(),
						Constants.SJFD_PSW, psw);
				// 进入向导一界面
				enterSjfdSet1();
				// 弹出框消失
				dialog.dismiss();
			}
		});
		btnCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 取消点击事件
				dialog.dismiss();
			}
		});

		dialog.show();
	}*/
}
