// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.android.application) apply false

    // Обновленная версия KSP из твоей подсказки
    id("com.google.devtools.ksp") version "2.3.2" apply false
}