plugins {
    id("com.android.application")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.onlineshoppingapp"
    compileSdk = 34   // ✅ STABLE

    defaultConfig {
        applicationId = "com.example.onlineshoppingapp"
        minSdk = 24
        targetSdk = 34   // ✅ STABLE
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
        dataBinding = true  // ✅ Add if using data binding
    }
}

dependencies {

        // Gemini SDK
        implementation("com.google.ai.client.generativeai:generativeai:0.9.0")

        // Guava (This provides the ListenableFuture and Futures classes)
        implementation("com.google.guava:guava:31.1-android")





    implementation("org.tensorflow:tensorflow-lite:2.14.0")

    implementation("androidx.recyclerview:recyclerview:1.3.2")

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.activity:activity:1.8.2")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.2")
    implementation("androidx.viewpager2:viewpager2:1.1.0")

    // ✅ FIREBASE (BOM)
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-database")

    // ✅ GLIDE
    implementation("com.github.bumptech.glide:glide:4.16.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // ✅ FIXED GSON DEPENDENCY
    implementation("com.google.code.gson:gson:2.10")  // ✔ Correct version

    implementation("com.github.ismaeldivita:chip-navigation-bar:1.4.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}
