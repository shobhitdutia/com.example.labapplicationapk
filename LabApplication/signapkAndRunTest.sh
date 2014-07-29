#!/bin/bash
# Sample usage is as follows;
# ./signapk myapp.apk debug.keystore android androiddebugkey
# The last 3 parameters are optional (They allow you to specify # the location of the debug keystore, keystore password and key # alias
# param1, APK file: Calculator_debug.apk
# param2, test apk path
# param2, keystore location: ~/.android/debug.keystore
# param3, key storepass: android
# param4, key alias: androiddebugkey

USER_HOME=$(eval echo ~${SUDO_USER})

#uninstall test apk if installed previously
adb shell pm uninstall -k com.example.android.apis.test


# use my debug key default
APK=$1
TESTAPKPATH="${2:-/home/shobhit/workspace/MyApkTest/bin/MyApkTest-debug.apk}"
KEYSTORE="${3:-$USER_HOME/.android/debug.keystore}"
STOREPASS="${4:-android}"
ALIAS="${5:-androiddebugkey}"


# get the filename
APK_BASENAME=$(basename $APK)
SIGNED_APK="signed_"$APK_BASENAME

#debug
echo param1 $APK
echo param2 $KEYSTORE
echo param3 $STOREPASS
echo param4 $ALIAS

# delete META-INF folder
zip -d $APK META-INF/\*

# sign APK
jarsigner -verbose -keystore $KEYSTORE -storepass $STOREPASS $APK $ALIAS
#verify
jarsigner -verify $APK

#zipalign
zipalign -v 4 $APK $SIGNED_APK 

#get package name of apk
package=`/home/shobhit/Desktop/android-sdk-linux/build-tools/17.0.0/aapt dump badging $APK | grep package | awk '{print $2}' | sed s/name=//g | sed s/\'//g`
echo package : $package

#get activity name of apk
activity=`/home/shobhit/Desktop/android-sdk-linux/build-tools/17.0.0/aapt dump badging $APK | grep activity | awk '{print $2}' | sed s/name=//g | sed s/\'//g`
echo activity : $activity

#replace test script by activity name
sed -i "s/private static final String LAUNCHER_ACTIVITY_CLASSNAME.*/private static final String LAUNCHER_ACTIVITY_CLASSNAME=\"$activity\";/g" /home/shobhit/workspace/MyApkTest/src/com/example/android/apis/test/Test.java

#replace manifest by package name
sed -i "s/android:targetPackage=.*/android:targetPackage=\"$package\"\/>/g" /home/shobhit/workspace/MyApkTest/AndroidManifest.xml

#create ant debug file
android update project -p /home/shobhit/workspace/MyApkTest -s --target android-19

#install signed apk
adb install $SIGNED_APK

#create new test apk (The command runs inside the proj folder)
cd /home/shobhit/workspace/MyApkTest
ant clean debug

#install test apk on emulator and run the test case

adb install $TESTAPKPATH

adb shell am instrument -w com.example.android.apis.test/android.test.InstrumentationTestRunner
