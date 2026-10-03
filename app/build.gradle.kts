plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.splashimagenapp"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "com.example.splashimagenapp"
        minSdk = 29
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"
    }

    buildTypes {
        release {
            optimization {
                enable = false
            }
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.constraintlayout)

    // Biblioteca del SplashScreen moderno (compatible desde API 21)
    implementation(libs.core.splashscreen)

    testImplementation(libs.junit)
}
