plugins { id("com.android.application") }
android {
 namespace="ir.hashemi.smsforwarder"
 compileSdk=35
 defaultConfig { applicationId="ir.hashemi.smsforwarder"; minSdk=26; targetSdk=35; versionCode=1; versionName="1.0" }
 buildTypes { release { isMinifyEnabled=false; isShrinkResources=false } }
 compileOptions { sourceCompatibility=JavaVersion.VERSION_17; targetCompatibility=JavaVersion.VERSION_17 }
}
