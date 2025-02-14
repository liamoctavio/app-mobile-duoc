plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.google.gms.google.services)
}

android {

    namespace = "com.example.sum1_b"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.sum1_b"
        minSdk = 30
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    testOptions {
        unitTests {
            isReturnDefaultValues = true
        }
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
        compose = true
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
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth.ktx)
//    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.junit.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.7")
    // ViewModel para Compose
    // Navigation Compose
    implementation("androidx.navigation:navigation-compose:2.8.5") // Verifica la versión más reciente

    // Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3") // Verifica la versión más reciente

    // Activity Compose
    implementation("androidx.activity:activity-compose:1.9.3") // Verifica la versión más reciente

    // Material Icons Extended (Opcional)
    implementation("androidx.compose.material:material-icons-extended:1.7.6") // Verifica la versión más reciente

    implementation("androidx.compose.foundation:foundation:1.4.3")
    // Asegúrate de que todas las dependencias de Compose tengan la misma versión


    // Dependencias de Compose
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    // Este es el importante para Icons (material-icons-extended)
//    implementation("androidx.compose.material:material-icons-extended")
//
//    implementation("com.google.firebase:firebase-bom:32.0.0")
//    implementation("com.google.firebase:firebase-database-ktx")

    implementation("org.osmdroid:osmdroid-android:6.1.16") // Última versión estable de osmdroid
    implementation("com.google.android.gms:play-services-location:21.0.1")
//    implementation ("com.github.maplibre:maplibre-gl-native:main-SNAPSHOT") // Usa la versión más reciente

//    implementation("com.mapbox.maps:android:11.9.2")

    testImplementation("androidx.test:core:1.5.0")


}