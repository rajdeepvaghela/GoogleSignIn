# GoogleSignIn

For Google SignIn to work, you will have to create a project in https://console.cloud.google.com.

Steps:
1. Enter project name and keep Location as 'No Organisation'.
2. After project is created, goto Side Menu -> APIs and Services -> OAuth consent screen 
   Here fill all the required fields and press 'Save And Continue'. No need to enter anything in Scopes and Optional Info. Press 'Back to Dashboard' on Summary page.
3. Press 'Publish App' under Publishing Status.
4. Now open Credentials from Side Menu.
5. Press Create Credentials -> OAuth client ID
   Select Application Type as Android
   Enter package name from AndroidManifest.xml file. eg package="com.practice.googlesignin", here 'com.practice.googlesignin' is package name.
   To get SHA-1 open peoject in Android Studio -> Gradle (on top-right corner) -> GoogleSignIn -> Tasks -> android -> signingReport. Now check the console, you will find MD5, SHA1 and SHA-256. 
   Use SHA1 from that and the press CREATE. 
6. Now run the app and Google SignIn should work.
