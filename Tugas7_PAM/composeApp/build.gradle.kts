import org.gradle.kotlin.dsl.invoke
import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeHotReload)
    alias(libs.plugins.sqldelight)
}

kotlin {
    androidTarget {
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    jvm()
    
    sourceSets {
        androidMain.dependencies {
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.activity.compose)
            // DataStore / Settings
            implementation("com.russhwolf:multiplatform-settings:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-coroutines:1.1.1")
            // SQLDelight Core & Coroutines
            implementation("app.cash.sqldelight:runtime:2.0.1")
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")
            implementation("app.cash.sqldelight:android-driver:2.0.1")

            // Tambahkan Koin Android [cite: 159, 160, 161, 162]
            implementation("io.insert-koin:koin-android:3.5.3")
            implementation("io.insert-koin:koin-androidx-compose:3.5.3")

        }
        commonMain.dependencies {
            implementation(libs.compose.runtime)
            implementation(libs.compose.foundation)
            implementation(libs.compose.material3)
            implementation(libs.compose.ui)
            implementation(libs.compose.components.resources)
            implementation(libs.compose.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodelCompose)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.navigation.compose)
            implementation(compose.materialIconsExtended)

            // Koin Core & Compose [cite: 155, 156, 157, 158]
            implementation("io.insert-koin:koin-core:3.5.3")
            implementation("io.insert-koin:koin-compose:1.1.2")

            // 2. Ktor & AI (Network & JSON)
            implementation("io.ktor:ktor-client-core:2.3.9")
            implementation("io.ktor:ktor-client-content-negotiation:2.3.9")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.9")
            implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.3")

            // 1. Multiplatform Settings (Untuk DataStore/Preferences)
            implementation("com.russhwolf:multiplatform-settings:1.1.1")
            implementation("com.russhwolf:multiplatform-settings-coroutines:1.1.1")

            // 2. SQLDelight Core & Coroutines (Untuk Database Utama)
            implementation("app.cash.sqldelight:runtime:2.0.1")
            implementation("app.cash.sqldelight:coroutines-extensions:2.0.1")

            // 3. Kotlinx Datetime (Untuk pencatatan waktu)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.5.0")
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.3") // Untuk testing coroutine/suspend
            implementation("app.cash.turbine:turbine:1.0.0") // Untuk testing Flow
            implementation("io.mockk:mockk:1.13.9") // Untuk Mocking
            implementation("io.insert-koin:koin-test:3.5.3") // Untuk Koin Test
        }

        jvmMain.dependencies {
            implementation(compose.desktop.currentOs)
            implementation(libs.kotlinx.coroutinesSwing)
        }

        val androidInstrumentedTest by getting {
            dependencies {
                implementation("androidx.compose.ui:ui-test-junit4:1.6.7")
                implementation(kotlin("test"))
                implementation("io.mockk:mockk-android:1.13.9") // Versi khusus Android biar nggak crash
                implementation("androidx.test.ext:junit:1.1.5")
            }
        }
    }
}

android {
    namespace = "org.example.project"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "org.example.project"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        val properties = Properties()
        val localPropertiesFile = project.rootProject.file("local.properties")
        if (localPropertiesFile.exists()) {
            properties.load(localPropertiesFile.inputStream())
        }
        val apiKey = properties.getProperty("GEMINI_API_KEY") ?: ""
        buildConfigField("String", "GEMINI_API_KEY", "\"$apiKey\"")


        buildFeatures {
            buildConfig = true
        }
    }

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"

            // --- TAMBAHKAN DUA BARIS INI ---
            excludes += "META-INF/LICENSE.md"
            excludes += "META-INF/LICENSE-notice.md"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(libs.compose.uiTooling)
    debugImplementation("androidx.compose.ui:ui-test-manifest")
}

compose.desktop {
    application {
        mainClass = "org.example.project.MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "org.example.project"
            packageVersion = "1.0.0"

            sqldelight {
                databases {
                    create("NotesDatabase") {
                        packageName.set("com.example.notes.db")
                    }
                }
            }
        }
    }
}

