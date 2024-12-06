plugins {
    alias(libs.plugins.android.application)
    id("com.google.gms.google-services")
}

android {
    namespace = "com.example.baocaogiuaky"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.baocaogiuaky"
        minSdk = 24
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

    buildFeatures {
        viewBinding = true
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}

dependencies {
    implementation("androidx.viewpager2:viewpager2:1.0.0")
    implementation("com.caverock:androidsvg:1.4")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("com.google.android.material:material:1.9.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation ("com.tbuonomo:dotsindicator:4.3")
    implementation ("androidx.viewpager2:viewpager2:1.0.0")
    implementation ("com.google.android.material:material:1.4.0")
    implementation ("com.opencsv:opencsv:5.5.2")
    implementation ("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation ("com.google.android.gms:play-services-auth:20.0.1")
    implementation(libs.progressbutton)
    implementation ("com.github.hellosagar:ProgressButton:0.35")
    implementation ("com.facebook.android:facebook-android-sdk:17.0.1")
    implementation(platform("com.google.firebase:firebase-bom:33.5.1"))
    implementation ("com.google.firebase:firebase-auth:21.0.8")
    implementation("com.google.android.gms:play-services-basement:18.4.0")
    implementation ("com.google.android.gms:play-services-auth:20.3.0")
    implementation("com.google.firebase:firebase-analytics")
    // Add Firebase dependencies
    implementation ("com.google.firebase:firebase-database:20.0.4")
    implementation ("com.firebaseui:firebase-ui-database:7.2.0")
    implementation ("com.github.bumptech.glide:glide:4.15.1")
    annotationProcessor ("com.github.bumptech.glide:compiler:4.15.1")
    implementation ("com.squareup.picasso:picasso:2.71828")


}
