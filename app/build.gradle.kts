plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin)
    alias(libs.plugins.jetbrains.compose)
    alias(libs.plugins.google.ksp)
    alias(libs.plugins.google.dagger.hilt)
    alias(libs.plugins.google.firebase.crashlytics)
    alias(libs.plugins.jetbrains.serialization)
}

android {
    namespace = "com.panostob.mycourses"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.panostob.mycourses"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        debug {
            isDebuggable = true
            isMinifyEnabled = false
        }
        release {
            isDebuggable = false
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
    }

    signingConfigs {
        getByName("debug") {
            storeFile = file("/home/ADDC/ptoumpas/.config/.android/debug.keystore")
            keyAlias = "AndroidDebugKey"
        }
        create("release") {
            storeFile = file("keystore/mycourses.jks")
            keyAlias = "myCoursesKey"
            storePassword = "mycoursepasswordassignmentproject"
            keyPassword = "mycoursepasswordassignmentproject"
        }
    }

    flavorDimensions += listOf("env")
    productFlavors {
        create("dev") {
            dimension = "env"
            applicationIdSuffix = ".dev"
            signingConfig = signingConfigs.getByName("debug")
            resValue("string", "app_name", "My Courses DEV")
            buildConfigField("String", "API_MY_COURSES_IMAGES_URL", "\"https://picsum.photos/\"")
        }

        create("prod") {
            dimension = "env"
            signingConfig = signingConfigs.getByName("release")
            resValue("string", "app_name", "My Courses")
            buildConfigField("String", "API_MY_COURSES_IMAGES_URL", "\"\"")
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    buildFeatures {
        buildConfig = true
    }

    applicationVariants.all {
        outputs.all {
            val output = (this as com.android.build.gradle.internal.api.BaseVariantOutputImpl)
            output.outputFileName = "${flavorName}_${versionName}_MY_COURSES_${buildType.name}.apk"
        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    
    // Activity Compose
    implementation(libs.androidx.activityCompose)

    // Lifecycle
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.androidx.lifecycle.viewmodel.compose)

    // Compose
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.tooling)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.animation)
    implementation(libs.androidx.compose.foundation)
    implementation(libs.androidx.compose.material3)
    implementation(libs.androidx.compose.material3.windowsizeclass)
    implementation(libs.androidx.compose.runtime)
    implementation(libs.androidx.compose.ui.util)

    // Material3 Adaptive
    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)

    // Navigation Compose
    implementation(libs.androidx.navigation.compose)

    // Timber
    implementation(libs.timber)

    // Moshi
    implementation(libs.squareup.moshi)
    ksp(libs.squareup.moshi.codegen)

    // okHttp
    implementation(libs.squareup.okhttp3)
    implementation(libs.squareup.okhttp3.logging)

    // Retrofit
    implementation(libs.squareup.retrofit)
    implementation(libs.squareup.retrofit.moshi)

    // Coil
    implementation(libs.coil)
    implementation(libs.coil.network)
    implementation(libs.coil.test)

    // Datastore
    implementation(libs.androidx.datastore.preferences)

    // Serialization Json
    implementation(libs.jetbrains.kotlinx.serialization.json)

    // Firebase
    implementation(platform(libs.google.firebase.bom))
    implementation(libs.google.firebase.analytics)
    implementation(libs.google.firebase.crashlytics)

    // Room
    implementation(libs.androidx.room.runtime)
    annotationProcessor(libs.androidx.room.compiler)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    implementation(libs.androidx.room.paging)

    // Hilt
    implementation(libs.google.hilt)
    ksp(libs.google.hilt.compiler)

    // Hilt Compose
    implementation(libs.androidx.hilt.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    debugImplementation(libs.androidx.ui.tooling)
}