plugins {
    id 'com.android.application'
    id 'kotlin-android'
    id 'com.google.gms.google-services'
    id 'kotlin-parcelize'
    id 'androidx.navigation.safeargs.kotlin'
    id 'dagger.hilt.android.plugin'
}


android {
    compileSdk 31

    defaultConfig {
        applicationId "com.application.snackhub"
        minSdk 21
        targetSdk 31
        versionCode 1
        versionName "1.0"

        testInstrumentationRunner "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            minifyEnabled false
            proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
        }
    }
    compileOptions {
        sourceCompatibility JavaVersion.VERSION_1_8
                targetCompatibility JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = '1.8'
    }

    buildFeatures{
        viewBinding true
    }
}

dependencies {

    apply plugin :"kotlin-kapt"
    implementation 'androidx.core:core-ktx:1.7.0'
    implementation 'androidx.appcompat:appcompat:1.4.1'
    implementation 'com.google.android.material:material:1.5.0'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.3'
    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.navigation:navigation-fragment-ktx:2.4.1'
    implementation 'androidx.navigation:navigation-ui-ktx:2.4.1'

    testImplementation 'junit:junit:4.+'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'

    //intuit
    implementation 'com.intuit.sdp:sdp-android:1.0.6'
    implementation 'com.intuit.ssp:ssp-android:1.0.6'


    //loading button
    implementation 'br.com.simplepass:loading-button-android:2.2.0'

    //firebase
    implementation 'com.google.firebase:firebase-firestore:24.0.1'
    implementation 'com.google.firebase:firebase-firestore-ktx:24.0.1'
    implementation 'com.google.firebase:firebase-auth-ktx:21.0.1'
    implementation 'com.google.firebase:firebase-storage'
    implementation 'com.google.firebase:firebase-storage-ktx:20.0.0'
    implementation 'com.google.firebase:firebase-common-ktx:20.0.0'
    implementation 'com.google.firebase:firebase-messaging-ktx:23.0.0'
    implementation 'com.firebaseui:firebase-ui-auth:4.3.2'

    //google play services
    implementation 'com.google.android.gms:play-services-auth:20.1.0'

    //smooth bar
    implementation 'com.github.ibrahimsn98:SmoothBottomBar:1.7.9'

    //Glide
    implementation 'com.github.bumptech.glide:glide:4.13.0'

    //circular image
    implementation 'de.hdodenhof:circleimageview:3.1.0'

    //Navigation and safe args
    def nav_version = "2.4.1"
    implementation "androidx.navigation:navigation-fragment-ktx:$nav_version"
    implementation "androidx.navigation:navigation-ui-ktx:$nav_version"

    //viewpager2 indicatior
    implementation 'io.github.vejei.viewpagerindicator:viewpagerindicator:1.0.0-alpha.1'

    //lifecycle
    implementation "android.arch.lifecycle:extensions:1.1.0"

    //Firebase coroutines
    implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-play-services:1.1.1'

    //stepView
    implementation 'com.shuhart.stepview:stepview:1.5.1'

    //Android Ktx
    implementation "androidx.fragment:fragment-ktx:1.4.1"


    //Dagger hilt
    implementation "com.google.dagger:hilt-android:2.38.1"
    kapt "com.google.dagger:hilt-compiler:2.38.1"


}
