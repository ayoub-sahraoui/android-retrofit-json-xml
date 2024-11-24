plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "ma.ensa.testretrofit2"
    compileSdk = 34

    defaultConfig {
        applicationId = "ma.ensa.testretrofit2"
        minSdk = 28
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    buildFeatures {
        viewBinding = true
    }

    // Add packaging options for XML library
    packaging {
        resources {
            excludes += listOf(
                "META-INF/DEPENDENCIES",
                "META-INF/LICENSE",
                "META-INF/LICENSE.txt",
                "META-INF/license.txt",
                "META-INF/NOTICE",
                "META-INF/NOTICE.txt",
                "META-INF/notice.txt",
                "META-INF/ASL2.0",
                "META-INF/LICENSE.md",
                "META-INF/LICENSE-notice.md"
            )
        }
    }
}

dependencies {
    // AndroidX Core Libraries
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)

    // Navigation Component
    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)

    // Retrofit and Network
    implementation(libs.retrofit)
    implementation(libs.okhttp)
    implementation(libs.retrofit.converter.gson)

    // SOAP Support
    implementation("com.squareup.retrofit2:converter-simplexml:2.9.0") {
        exclude(group = "xpp3")
        exclude(group = "stax")
        exclude(group = "stax-api")
    }
    implementation("org.simpleframework:simple-xml:2.7.1") {
        exclude(group = "stax")
        exclude(group = "stax-api")
        exclude(group = "xpp3")
    }
    implementation("com.squareup.retrofit2:converter-scalars:2.9.0")

    // Logging Interceptor
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")

    // LiveData and ViewModel
    implementation("androidx.lifecycle:lifecycle-livedata:2.6.2")
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")

    // Testing
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}

configurations.all {
    resolutionStrategy {
        force("org.simpleframework:simple-xml:2.7.1")
    }
}