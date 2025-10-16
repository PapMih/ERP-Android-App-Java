plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.projecterp"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.projecterp"
        minSdk = 22
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        javaCompileOptions {
            annotationProcessorOptions {
                arguments += mapOf("room.schemaLocation" to "$projectDir/src/main/schemas")
            }
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
}

dependencies {
    val roomVersion = "2.7.0"
    val lifecycleVersion = "2.6.2"
    val appCompatVersion = "1.6.1"
    val constraintLayoutVersion = "2.1.4"
    val materialVersion = "1.9.0"
    val junitVersion = "4.13.2"
    val coreTestingVersion = "2.1.0"
    val espressoVersion = "3.5.1"
    val androidxJunitVersion = "1.1.5"

    implementation(libs.activity)

    // AppCompat & Material
    implementation("androidx.appcompat:appcompat:$appCompatVersion")
    implementation("com.google.android.material:material:$materialVersion")
    implementation("androidx.constraintlayout:constraintlayout:$constraintLayoutVersion")

    // Lifecycle components
    implementation("androidx.lifecycle:lifecycle-viewmodel:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-livedata:$lifecycleVersion")
    implementation("androidx.lifecycle:lifecycle-common-java8:$lifecycleVersion")

    // Room
    implementation("androidx.room:room-runtime:$roomVersion")
    annotationProcessor("androidx.room:room-compiler:$roomVersion")
    implementation("androidx.room:room-rxjava2:$roomVersion")
    implementation("androidx.room:room-rxjava3:$roomVersion")
    implementation("androidx.room:room-guava:$roomVersion")
    implementation("androidx.room:room-paging:$roomVersion")
    testImplementation("androidx.room:room-testing:$roomVersion")

    // Testing
    testImplementation("junit:junit:$junitVersion")
    androidTestImplementation("androidx.test.ext:junit:$androidxJunitVersion")
    androidTestImplementation("androidx.arch.core:core-testing:$coreTestingVersion")
    androidTestImplementation("androidx.test.espresso:espresso-core:$espressoVersion") {
        exclude(group = "com.android.support", module = "support-annotations")
    }
}
