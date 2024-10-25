# DigitalAppPoc
This repo contains the source code and binaries for Car Infotainment POC. It includes AAOS application, iOS application, Android application, backend. 

android: Extract content of this folder and open the project in Android Studio. Let the gradle sync complete and download all necessary dependencies. It includes 'automotive', 'mobile' and 'common' modules. 'automotive' module can be built and executed on Android Automotive OS Emulator. It has been tested on Automotive OS API 33 Tiramisu emulator. 'mobile' module can be built and executed on Android OS Emulator. It has been tested on Pixel API 33/34 emulator. 'common' module includes retrofit APIs which are commonly used by both 'automotive' and 'mobile' modules. 

iOS: Mobile application for setting threshold for speed limit. Map view in progress. Verified on iOS 17.5 version.

backend: Java implementation for setting the speed threshold and recording speed violation events. 

docs : Includes use case, solution architecture and project plan.
