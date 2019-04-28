#
# 使用方式

## 添加依赖:

```
    allprojects {
        repositories {
            maven { url 'https://jitpack.io' }
        }
    }
```

```
    dependencies {
        implementation 'com.github.adonggit:CasePackage:1.1'
    }
```

#### 1 检查Manifest权限

```java
    <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE"/>
    <uses-permission android:name="android.permission.VIBRATE"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" /> <!-- 获取网络状态 -->
    <uses-permission android:name="android.permission.INTERNET" /> <!-- 网络通信-->
    <uses-permission android:name="android.permission.READ_PHONE_STATE" /> <!-- 获取设备信息 -->
    <uses-permission android:name="android.permission.ACCESS_WIFI_STATE" /> <!-- 获取MAC地址-->
    <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" /> <!-- 读写sdcard，storage等等 -->
    <uses-permission android:name="android.permission.RECORD_AUDIO" /> <!-- 允许程序录制音频 -->
    <uses-permission android:name="android.permission.GET_TASKS"/>
```

#### 2 创建一个跳转activity名字随便取

```
    public class MainActivity extends TempActivity { // 一定要继承TempActivity

        @Override
        public Class<?> getTargetNativeClazz() {
        return Main2Activity.class; //原生界面的入口activity
    }


    @Override
    public int getAppId() {
        return 11111; //自定义的APPID
    }

    @Override
    public String getUrl() {
        return "http://www.baidu.com"; // 配置URL 注意: 一定要携带http或https
    }
    }
```

#### 3 将APP入口activity换成刚刚创建的activity

```
    <activity android:name=".MainActivity">
        <intent-filter>
        <action android:name="android.intent.action.MAIN"/>

        <category android:name="android.intent.category.LAUNCHER"/>
        </intent-filter>
    </activity>
```

#### 4 配置升级\(可选择\)

1. 在项目build.grade中添加代码

```
    allprojects {
        repositories {
            jcenter()
            maven { url "https://raw.githubusercontent.com/Pgyer/mvn_repo_pgyer/master" }
        }
    }
```

1. 在Manifest中添加

```
    <meta-data
        android:name="PGYER_APPID"
        android:value="4b6e8877dfcc2462bedb37dcf66b6d87" >
    </meta-data>
```

3 修改APP

```
* 第一种方式: 给application加name
    <application
    android:name=".PgyApplication"
    </application>
* 第二种方式:
    将PgyCrashManager.register(this);添加到自己的APP中
* 第三种方式:
    将自己的APP继承PackageApp
```

#### 5 配置极光推送\(可选\)

1 在 module 的 gradle 中添加依赖和AndroidManifest的替换变量。

```
    android {
        defaultConfig {
        applicationId "com.xxx.xxx" //JPush上注册的包名.

        ndk {
            //选择要添加的对应cpu类型的.so库。
            abiFilters 'armeabi', 'armeabi-v7a', 'arm64-v8a'
            // 还可以添加 'x86', 'x86_64', 'mips', 'mips64'
        }

        manifestPlaceholders = [
            JPUSH_PKGNAME : applicationId,
            JPUSH_APPKEY : "你的appkey", //JPush上注册的包名对应的appkey.
            JPUSH_CHANNEL : "developer-default", //暂时填写默认值即可.
        ]
        }
    }

    dependencies {
        compile 'cn.jiguang.sdk:jpush:3.1.1' // 此处以JPush 3.1.1 版本为例。
        compile 'cn.jiguang.sdk:jcore:1.1.9' // 此处以JCore 1.1.9 版本为例。
    }
```

2 在 Project 根目录的gradle.properties文件中添加：

```
    android.useDeprecatedNdk=true
```


