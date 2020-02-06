BottomSheetDateDialog:
	First Page
	Date Selection:
		Start Date -> Start Time | End Date -> End Time
		Start Time and End Time are optional
	ImageView with Grid
		Drag -> Select Region : Make sure you are using select mode
		Touch -> Add/Remove region : independent of select mode, for more precise operation
	Submit -> Move to next fragment

HomePageFragment:
	List of date with time and duration.
	High signal bars represent high motion rate, compared to other.
	

Database:
	Data is stored locally when search operation is performed,
	using hashCode value of zone, start time and end time are stored.
	Hashcode helps in identify same region zone, is selected before.

SharedPreference:
	Added to store last search option.

Retrofit:
	Api Calls are made through retrofit

Picker:
	Contains DatePicker and TimePicker dialogs
	Customize to select only valid dates.

room: 
	Files realted to database

Utils:
	General/Specific operation performed are stored here
	Constant - used to store one reference of object and used where required

adapter:
	Various RecyclerView adpater implemented,
	

Vectors:
	rect_(1-4) -> created to represent signal strength
	
drawable:
	created customizable backgrounds

network_config:
	due to http url, network operations were blocked,
	had to add this file to remove restriction

COnfiguration Changes:
	Application handles and restore the state of the applicaiton

MVVM model used via databinding, viewmodel 

RxJava Library used for LiveData

Focus was given onto implementation rather than the design,
functionality like drag and select was add to improve user experience.

Not implemented landscape view for this application because of time restriction

Additional Operations or fucntionality that can be added:
	1. Adding nosql support for avoiding use of hashcode, library found but were not stable.
	2. Sorting Algorithm to optimize UI and UX
	3. Graphs could be added to present data, library available though not implemented here.
	

