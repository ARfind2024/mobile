plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
    id("com.google.gms.google-services")
}

android {
    namespace = "com.liontail.arfind"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.liontail.arfind"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {
    //Firebase Auth
    implementation ("com.google.firebase:firebase-auth:23.1.0")
    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    //Gson
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")
    //OkHTTP
    implementation("com.squareup.okhttp3:okhttp:4.9.3")
    //location
    implementation ("com.google.android.gms:play-services-location:21.3.0")
    implementation ("com.google.android.gms:play-services-maps:19.0.0")
    //picasso
    implementation ("com.squareup.picasso:picasso:2.71828")
    //firebase
    implementation(platform("com.google.firebase:firebase-bom:33.2.0"))
    //implementation ("com.google.firebase:firebase-auth:23.6.0'")
    implementation ("com.google.firebase:firebase-firestore:25.1.0")
    implementation("com.google.firebase:firebase-analytics")
    //material
    implementation ("com.google.android.material:material:1.11.0")

    implementation ("androidx.fragment:fragment:1.5.5")

    //default
    //noinspection GradleCompatible,GradleCompatible
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    implementation("androidx.navigation:navigation-fragment-ktx:2.7.7")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.7")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}