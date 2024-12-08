plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("kotlin-parcelize")
    alias(libs.plugins.google.android.libraries.mapsplatform.secrets.gradle.plugin)
}

android {
    namespace = "com.example.appmovilespedidosyacliente"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.appmovilespedidosyacliente"
        minSdk = 29
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
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    implementation (libs.glide)
    implementation(libs.play.services.maps)
    annotationProcessor(libs.compiler)
    //viewmodels
    implementation(libs.androidx.lifecycle.livedata.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.ktx)
    //retrofit
    implementation (libs.retrofit)
    implementation (libs.retrofit2.converter.gson)

    //fragment + viewmodel
    implementation (libs.androidx.fragment.ktx)
    implementation(libs.androidx.core.ktx)
    //librería de google play services location
    implementation(libs.play.services.location)

    implementation(libs.gson)

    implementation(libs.material)

    implementation(libs.play.services.maps)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}