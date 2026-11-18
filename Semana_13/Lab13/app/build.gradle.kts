import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

// Lee local.properties (si existe): ahí vive la clave de Maps, fuera del código y de git
val localProps = Properties().apply {
    val archivo = rootProject.file("local.properties")
    if (archivo.exists()) archivo.inputStream().use { load(it) }
}

android {
    namespace = "com.santamaria.campusgps"
    compileSdk {
        version = release(37)
    }

    defaultConfig {
        applicationId = "com.santamaria.campusgps"
        minSdk = 26
        targetSdk = 37
        versionCode = 1
        versionName = "1.0"

        // La clave llega al Manifest como ${MAPS_API_KEY}
        manifestPlaceholders["MAPS_API_KEY"] = localProps.getProperty("MAPS_API_KEY", "")
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
    buildFeatures {
        compose = true
    }
}

// maps-compose 8.4.0 trae kotlin-stdlib 2.4; el compilador del proyecto es 2.2.
// Se omite solo la revisión de versión de metadata (no se usa ninguna API nueva).
kotlin {
    compilerOptions {
        freeCompilerArgs.add("-Xskip-metadata-version-check")
    }
}

dependencies {
    // Ubicación (Fused) y mapa (Maps Compose)
    implementation(libs.play.services.location)
    implementation(libs.maps.compose)
    // await() para usar las Task de Google con corrutinas
    implementation(libs.kotlinx.coroutines.play.services)

    // Lifecycle y Compose
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.material3)
    // Íconos de línea del diseño (Figma)
    implementation(libs.androidx.compose.material.icons.extended)
    implementation(libs.androidx.compose.ui.tooling.preview)
    debugImplementation(libs.androidx.compose.ui.tooling)
}
