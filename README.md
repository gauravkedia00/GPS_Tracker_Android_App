# GPS_Tracker_Android_App
User Interface:
User Interface is a Basic Layout that contains a Square Custom View and a basic shell of a custom view.
It displays a “start’ button which triggers the GPS Tracker. Once it starts tracking, it displays a “stop” button which immediately stops the tracking process.
It also displays Time and Speed information based on the distance covered.
It also displays a live bar graph with labelled x axis based on distance & adaptable in both x and y axis based on the available information.
It also has a button “show speed” & “show time” to switch between time & speed on y axis.
The bar also displays a line showing average time or speed based on user choice.

Milestones implemented:
Bracket 1: Both layout & shell of a custom view
Bracket 2: Listener for GPS locations for every 15 seconds & an Array List to store these values. A start & stop button to enable & disable GPS tracking
Bracket 3: Averages of Time & Speed are calculated & displayed
Bracket 4: Bar graph is drawn initial showing time for each 100 meters as an individual bar which is adaptable in x axis and height adjusted as per y axis along with average info.
Bracket 5: Bar Graph adaptable is x axis & x axis is labelled for distance
Bracket 6: Lives changes in Bar graph while tracking.
Bracket 7: A line representing overall speed & time is shown based on user choice & user can switch graph on y axis for time if speed is shown and vice versa

Methods:
MainActivity.java
getSystemService(Context.Location_Service) gets Location Manager in Location class.
LocationHelper() – gets user’s current location using series of different listeners.
addObserver(graph)

findViewById() – search the particular view in the layout.
setOnClickListener() – adds a listener to the button.
getText() – to get the text of the button
toString() – to change value to string type
addLocationListener() – a method for defining permissions for accessing location and defines a method requestLocationUpdates() which register for location updates.
Reset() – for fresh set.
removeLocationListener() – defines a method removeUpdates() which removes all location updates.
setMetric() – calls to setMetric class in CustomView.java

CustomView.java
This class is a child class of View class and it also overrides Observer class. It has a constructor containing context and attributeSet as its parameters. 
getTheme().obtainStyledAttributes – calls to the obtainStyledAttributes class of getTheme which return a TypedArray holding values defined by Theme

computeMean() – calculates and returns mean of the values in the List.
Recycle() – returns the allocated memory to the TypedArray.

computeMax() – finds and returns maximum value from the List.

computeStats(): Location method determines location of a user in form of Latitude & Longitude. This method calculates distance using inbuilt distanceTo()  formula and adds it to the distanceGroups list. It also finds the time taken and adds it to timeGroups list.

onDraw() – to draw graph
	getHeight() – to get height of the canvas
	getWidth() – to get width of the canvas
	setColor() – use to give color to canvas
	setStrokeWidth() – like a smoothing
	drawLine() – constructing lines on the canvas of Custom View.
	drawText() – writing text on the canvas
	
	update() – add location points to the collection.

LocationHelper.java
Contains a class LocationHelper which is a child class of Observable class and overrides the LocationListener class.
onLocationChanged() – a method defined for tracking location if it changes. It contains
	notifyObservers() – calls the update method of Observers & notified the change.
