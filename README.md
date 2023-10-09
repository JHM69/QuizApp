# Quiz Q

Quiz Q is a user-friendly mobile app that offers an engaging and challenging quiz experience across various topics. It allows users to answer questions within a time limit and saves their progress for later. The app also offers a history feature to review past games, tracks scores, and provides hours of entertainment and education for students, teachers, and trivia enthusiasts. Quiz Q utilizes ROOM Database, LiveData, ViewModel, Retrofit, and other libraries to deliver a seamless and enjoyable quiz experience for Android users.

1. Questions are randomly selected from [The Trivia Api](https://the-trivia-api.com/docs/#getQuestions)
2. Used ROOM Database for Storing Questions and User result offline
3. Used Live Data and ViewModel
 
# Structure
```
main
   ├───java
   │   └───com
   │       └───jhm69
   │           └───quizapp_hometask
   │               ├───activity
   │               ├───adapter
   │               ├───dao
   │               ├───db
   │               ├───model
   │               ├───repository
   │               ├───retrofit
   │               ├───utils
   │               ├───view
   │               └───viewmodel
   └───res
       ├───anim
       ├───drawable
       ├───font
       ├───layout
       ├───mipmap-anydpi-v26
       ├───mipmap-hdpi
       ├───mipmap-mdpi
       ├───mipmap-xhdpi
       ├───mipmap-xxhdpi
       ├───mipmap-xxxhdpi
       ├───raw
       ├───values
       └───xml
```
# Dependencies
```
    implementation fileTree(dir: "libs", include: ["*.jar"])
    implementation 'androidx.appcompat:appcompat:1.3.0'
    implementation 'com.google.android.material:material:1.8.0-alpha02'
    implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
    testImplementation 'junit:junit:4.13.2'
    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'


    implementation 'androidx.legacy:legacy-support-v4:1.0.0'
    implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.3.0'
    implementation 'androidx.preference:preference:1.1.1'
    testImplementation 'junit:junit:4.13.2'
    implementation 'commons-io:commons-io:2.5'

    androidTestImplementation 'androidx.test.ext:junit:1.1.3'
    androidTestImplementation 'androidx.test.espresso:espresso-core:3.4.0'
    implementation "android.arch.persistence.room:runtime:1.1.1"
    annotationProcessor "android.arch.persistence.room:compiler:1.1.1"
    implementation 'com.google.code.gson:gson:2.8.6'
    implementation 'com.squareup.retrofit2:retrofit:2.3.0'
    implementation 'com.squareup.retrofit2:converter-gson:2.3.0'
    implementation "android.arch.lifecycle:livedata:1.1.1"
    implementation 'com.github.marlonlom:timeago:3.0.2'

    implementation "com.airbnb.android:lottie:3.4.0"
    implementation "android.arch.lifecycle:extensions:1.1.1"
    implementation "android.arch.lifecycle:viewmodel:1.1.1"
```
# ScreenShots

[<img src="/sc/1.jpg" align="center" width="200" hspace="10" vspace="10">](/sc/1.jpg)
[<img src="/sc/2.jpg" align="center" width="200" hspace="10" vspace="10">](/sc/2.jpg)
[<img src="/sc/3.jpg" align="center" width="200" hspace="10" vspace="10">](/sc/3.jpg)
[<img src="/sc/4.jpg" align="center" width="200" hspace="10" vspace="10">](/sc/4.jpg)
[<img src="/sc/5.jpg" align="center" width="200" hspace="10" vspace="10">](/sc/5.jpg)
[<img src="/sc/6.jpg" align="center" width="200" hspace="10" vspace="10">](/sc/6.jpg)

## Contact Me [Jahangir Hossain](https://facebook.com/jhm69)



