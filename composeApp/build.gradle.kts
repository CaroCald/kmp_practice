import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    kotlin("plugin.serialization") version  "2.1.10"

}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }
    
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
        }
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        outputModuleName.set("composeApp")
        browser {
            webpackTask {
                sourceMaps = true // Or whatever the current property is, might be in `devtool`
            }
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }

        }
        binaries.executable()
    }
    
    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)

            //coin
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.1.0"))
            implementation(libs.koin.core)
            implementation(libs.koin.android)

            implementation(libs.ktor.client.android)

        }
        commonMain.dependencies {
            implementation(compose.runtime)
            api(compose.foundation)
            api(compose.animation)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)

            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            api(libs.precompose)
            api(libs.precompose.viewmodel)
            implementation(compose.components.resources)
            api(compose.materialIconsExtended)
            implementation(libs.ktor.client.core)
            //implementation(libs.kotlinx.coroutines.core)


            implementation(libs.ktor.serialization)
            implementation(libs.ktor.content.negotation)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)

            //coin
            implementation(project.dependencies.platform("io.insert-koin:koin-bom:4.1.0"))
            implementation(libs.insert.koin.koin.core)
            implementation(libs.koin.compose)
            api(libs.precompose.koin)
            implementation(libs.ktor.client.logging)



        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}

android {
    namespace = "com.example.kmppractice"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.example.kmppractice"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
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
    debugImplementation(compose.uiTooling)
}


compose.resources {
    publicResClass = true // Generates Res class accessible from common code
    packageOfResClass = "com.example.kmppractice.generated.resources" // Optional: customize package
    // By default, resources are picked from 'src/commonMain/composeResources'
    // You can customize this if needed, but the default is usually what you want:
    // resourcesPath.set(project.layout.projectDirectory.dir("src/commonMain/my-custom-resources"))
}
