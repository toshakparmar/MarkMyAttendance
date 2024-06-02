MarkMyAttendance Project SetUp..

1. Clone this Repo in Local Area & Download it directly..
2. Open Android.MainFest File, then add these two Permissions on it.
     <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
     <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
3. Open Grade Folder and open built.gradle (Module) file, and then paste these dependencies...
     dependencies {
        implementation fileTree(dir: 'libs', include: ['*.jar'])
        implementation 'androidx.appcompat:appcompat:1.6.1'
        implementation 'com.github.barteksc:android-pdf-viewer:2.8.2'
        implementation 'com.google.android.material:material:1.11.0'
        implementation 'androidx.legacy:legacy-support-v4:1.0.0'
        implementation 'androidx.cardview:cardview:1.0.0'
        implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
        implementation 'it.xabaras.android:recyclerview-swipedecorator:1.4'
        implementation 'com.karumi:dexter:6.2.3'
        implementation 'androidx.core:core:1.13.1'
        implementation "androidx.fragment:fragment:fragment_version"
        testImplementation 'junit:junit:4.13.2'
        androidTestImplementation 'androidx.test.ext:junit:1.1.5'
        androidTestImplementation 'androidx.test.espresso:espresso-core:3.5.1'
    }
4. Click Sync Now Button..(Gradle Build)
     Whenever Gradle Finish Click Run Button Project Run...

5. Thats it Thank You..

@Copyright - @CodeSmachers(Toshak Parmnar) MarkMyAttendance
