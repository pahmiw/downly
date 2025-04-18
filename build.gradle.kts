/*buildscript {
    dependencies {
        classpath(libs.gradle)
        classpath(libs.google.services)
    }
}*/
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.kotlinAndroid) apply false
    alias(libs.plugins.kotlinKapt) apply false
    alias(libs.plugins.daggerHiltAndroid) apply false
//    alias(libs.plugins.googleServices) apply false
}
true // Needed to make the Suppress annotation work for the plugins block