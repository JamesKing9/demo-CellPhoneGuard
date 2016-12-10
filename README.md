# demo-CellPhoneGuard
仿市面上主流的手机卫士



# 业务实现

### 1.增加代码的可读性

```
1.学习其他开源代码
2.自己多思考，不断重构
```

### 2.代码的组织方式

- 业务逻辑模块组织代码  
  小米阅读
  1. 阅读器 reader com.xiaomi.reader
  2. 分享 share com.xiaomi.share
  3. 便签 note com.xiaomi.note
- 根据代码的类型组织包结构
  1. 界面 com.cheng.activities
  2. 服务 com.cheng.services
  3. 业务逻辑 com.cheng.engine
  4. 数据库 com.cheng.db
  5. 数据库增删改查 com.cheng.db.dao
  6. 工具类 com.cheng.utils
  7. 自定义view  com.cheng.ui

### 3.splash界面的作用

```
1. 展现产品的logo,提升产品的知名度.
2. 初始化应用程序的数据.
3. 连接服务器,查找可更新的版本,自动更新
4. 用户操作指南
5. 新版本特性提醒
```

### 4.布局文件的命名规则

```
SplashActivity--->activity_spalsh.xml
XxxActivity---> activity_xxx.xml
```

### 5.获取应用程序版本号

```
//用PackageManager拿到PackageInfo，PackageInfo中的versionName
PackageInfo packinfo = context.getPackageManager().getPackageInfo(
				context.getPackageName(), 0);
String version = packinfo.versionName;
```

### 6.源代码版本控制

- 安装VisualSVN Server——SVN服务器，一直下一步即可  
- 导入仓库到服务器  
  1.在Repositories处右键，选择Import Existing Repository  
  2.选择Copy repository from another location，下一步  
  3.点击Browse，选择仓库路径，"代码/代码仓库/Repository/cellphoneGuard"，点击下一步  
  4.点击Import  
  5.点击Finish，导入完成  
- 安装TortoiseSVN——SVN客户端，一直下一步即可  
  1.在想要检出代码的地方右键，选择SVN Checkout  
  2.URL of repository处填https://127.0.0.1/svn/mobilesafe/，地址也可以从SVN服务器的mobilesafe处右键选择Copy URL to clipboard拷贝  
  3.Checkout directory出填写检出代码要放的位置，然后点击OK
  4.完成代码的检出
- 将代码更新到指定版本  
  1.cellphoneGuard文件夹出右键，选择Update to version  
  2.点击show log  
  3.点击左下角的show all  
  4.选择要更新的版本，点击OK
  5.版本更新完成

### 7.应用自动更新的逻辑图

![](http://i.imgur.com/EU78wXt.png)

### 8.获取服务器版本号

```java
//获取服务器地址
String path = getResources().getString(R.string.url);
URL url = new URL(path);

//创建网络连接
HttpURLConnection conn = (HttpURLConnection) url.openConnection();
conn.setRequestMethod("GET");
conn.setConnectTimeout(5000);

//发出请求，获得返回码
int code = conn.getResponseCode();
if(code ==200){

	//获取服务器返回的流并进行解析
	InputStream is = conn.getInputStream();
	String result = StreamTools.readStream(is);
	
	//转化为json并解析出版本号
	JSONObject json = new JSONObject(result);
	String serverVersion = json.getString("version");
	Log.i(TAG,"服务器版本:"+serverVersion);
}
```

### 9.将流转化为字符串

```java
public static String readStream(InputStream is) throws IOException{

		//ByteArrayOutputStream类是在创建它的实例时，程序内部创建一个byte型数组的缓冲区，缓冲区会随着数据的不断写入而自动增长。可使用 toByteArray()和 toString()获取数据
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = -1;
		while((len = is.read(buffer))!=-1){
			baos.write(buffer, 0, len);
		}
		is.close();
		return baos.toString();
}
```

### 10.弹出对话框

告诉用户>服务器端有新版本存在，请确认是否更新应用版本。

```
1.使用AlertDialog.Builder
2.设置标题、信息、点击事件等
3.调用show方法显示出来，调用dismiss方法消失
```

### 11.下载apk

如果用户暂不升级，逻辑上应该进入程序的主界面 HomeActivity;

```
1.使用开源框架xUtils
2.使用HttpUtils的download方法，填入三个参数：服务器下载地址，手机中的存储位置、回调事件
3.回调事件中有三个常用的方法：onSuccess下载成功、onFailure下载失败、onLoading更新下载进度

xUtils补充
http://my.oschina.net/u/1171837/blog/147544 作者博客
```

### 12.安装apk

```java
1.调用系统的安装apk的界面，传入对应的参数
2.具体实现方式
Intent intent = new Intent();
intent.setAction("android.intent.action.VIEW");
intent.addCategory("android.intent.category.DEFAULT");
intent.setDataAndType(
		Uri.fromFile(fileinfo.result),
		"application/vnd.android.package-archive");
startActivity(intent);
```

### 13.应用程序的覆盖安装要满足的条件

```
1. 两个版本签名一致
2. 两个版本包名一致
```

### 14.跑马灯效果的TextView

```
1. 系统的TextView不能获取焦点，使用自定义控件
2. 继承TextView控件，重写isFocused方法，直接返回true，让其获取焦点
3. 设置android:ellipsize="marquee"
```

### 15.旋转的黑马logo

```java
1. 使用系统提供的属性动画
2. 具体实现方式
ObjectAnimator oa = ObjectAnimator.ofFloat(iv_home_logo, "rotationY",
			45, 90, 135, 180, 225, 270, 315);
oa.setDuration(3000);
oa.setRepeatCount(ObjectAnimator.INFINITE);
oa.setRepeatMode(ObjectAnimator.RESTART);
oa.start();
```

