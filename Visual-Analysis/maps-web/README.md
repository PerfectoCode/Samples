##Maps-Web Code Sample

This sample utilizes Perfecto's Visual Analysis to test the Google Maps web app on a desktop, AND demonstartes another scenario for cross platform testing.

In the test, a WEB SESSION ON A DESKTOP is started, launching the Google maps web application. Source & destination addresses are specified, and driving diractions are requested, selecting the first route, for which the duration, distance, and route name are extarcted.

Once signed into a Google account associated with a MOBILE DEVICE, these directions are then sent to the Goggle Maps' native application on that mobile.

Both text and image checkpoints are used, image buttons are clicked, and field values are set, all using Visual Analysis.

**TODO:** <br/>
- Set your lab, user, and password.
- Upload the three .png files from the "test images" directory into the repository, and change path in test script to match the loaction you used.
- Set a Google account credentials and associate them with a mobile device. 
- Set that mobile device ID in the mobileSessionMaps.java file.
- Perfecto Utils are required for this test and can be found at https://github.com/PerfectoCode/Templates/tree/master/Utils.
