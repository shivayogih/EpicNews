import java.io.FileInputStream
import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.hilt.android)
    alias(libs.plugins.ksp)
    alias(libs.plugins.kotlin.serialization)

}

val credentialsPropertiesFile = rootProject.file("credentials.properties")
val credentialsProperties = Properties().apply {
    if (credentialsPropertiesFile.exists()) {
        load(FileInputStream(credentialsPropertiesFile))
    }
}


android {
    namespace = "com.shivayogi.epicnews"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.shivayogi.epicnews"
        minSdk = 28
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    }

    buildTypes {
        debug {
            isMinifyEnabled = false
            isDebuggable =true
            buildConfigField("String", "API_KEY", "\"${credentialsProperties["API_KEY"]}\"")
            buildConfigField("String", "BASE_URL1", "\"${credentialsProperties["BASE_URL1"]}\"")
            buildConfigField("String", "BASE_URL2", "\"${credentialsProperties["BASE_URL2"]}\"")

        }
        
        release {
            isMinifyEnabled = false
            isDebuggable =false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            buildConfigField("String", "API_KEY", "\"${credentialsProperties["API_KEY"]}\"")
            buildConfigField("String", "BASE_URL1", "\"${credentialsProperties["BASE_URL1"]}\"")
            buildConfigField("String", "BASE_URL2", "\"${credentialsProperties["BASE_URL2"]}\"")

        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }

}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)


    //Test Libraries
    testImplementation(libs.junit)
    testImplementation(libs.mockk)
    testImplementation(libs.mockito.core)
    testImplementation(libs.mockito.kotlin)
    testImplementation(libs.core.testing)
    testImplementation(libs.coroutines.test)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)


    implementation(libs.kotlinx.serialization.json) // JSON serialization
    implementation(libs.androidx.navigation.compose)

    // Retrofit and OkHttp for network calls
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.retrofit.mock)
    implementation(libs.okhttp)
    implementation(libs.okhttp.logging.interceptor)

    // Room for database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    ksp(libs.room.compiler)  // KSP annotation processor for Room

    // Hilt for dependency injection
    implementation(libs.android.hilt)
    ksp(libs.android.hilt.compiler)  // KSP for Hilt
    implementation(libs.hilt.navigation.compose)

    // WorkManager
    implementation(libs.androidx.work.runtime.ktx)
    implementation(libs.androidx.hilt.work)

    // Paging
    implementation(libs.androidx.paging.runtime.ktx)
    implementation(libs.androidx.paging.compose)

    // Coil for image loading
    implementation(libs.coil)
    implementation(kotlin("test"))


}