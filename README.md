# Beta Release Info:

This version of GetTogether has several main functionalities. Users can create events with a title, times, locations, descriptions. The events will then be displayed in a list accessible to any other users of the app. Users can view events from this list, and all of the aforementioned information about any event will be visible. The login feature is functional, and only requires that users not create usernames that have already been taken. Users can log out from the home screen, and they can also access a help button that redirects to a relevant PDF in this repository.

# Source Code
1. This repository contains all of our source code.

2. To find the .java files go to app/src/main then click on first folder title "java/com/example/manrajsingh960/gettogether" 

3. To find the .xml files used for the user interface go app/src/main/res/layout

4. To find the text color schemes, button color schemes, and images used for the user interface go to app/src/main/res/drawable

# How To Modify Source Code
1. Install Android Studio using this link: 
https://developer.android.com/studio/index.html

2. Create a folder titled "Apps" in either the desktop or documents directory. This is where you will keep all of your android studio projects

3. Download the GetTogether[10-30-17__2-30-pm].zip file (or latest version) at this link:
https://drive.google.com/drive/folders/0B9BkxOGr4o9ScHdiZ1dMemYxMVk?usp=sharing

4. Unzip the file and move the GetTogether[10-30-17__2-30-pm] folder into the "Apps" folder from step 1.

5. Open Andriod Studio and click on "Open an Existing Andriod Studio Project" option from the start up menu.

6. Find the GetTogether[10-30-17__2-30-pm] file and open it.

7. Now you have access to the project in Android Studio.

8. If you want to save the changes you made: compress the GetTogether[10-30-17__2-30-pm] folder in "Apps" folder into a .zip file.

9. Rename the .zip file to the corresponding date and time you are saving the changes.

10. Go to https://drive.google.com/drive/folders/0B9BkxOGr4o9ScHdiZ1dMemYxMVk?usp=sharing and upload the .zip file to Google Drive 

# How To Report Bugs
You can reports bugs to either:
1. The Issues tab on this repository.

2. Send an email to csc131gettogether_1172781000000017243@bugs.zohoprojects.com

# How To Access Database

1. Go to https://www.000webhost.com
2. Email: manrajsingh960@gmail.com 
Password: gettogether
3. Click on Manage Database link
4. Go down the page where it shows the manage drop down menu next to the database name and id
5. In the drop down menu select PhpMyAdmin
6. In the panel all the way to the left it should have the database id3565421_get_together_data (Click on the + sign to the left)
7. Then click on user 
8. Then to your right it should show the users in our app database.
9. Right now there should be a user with username "test" and the password is random letters (this is because the password is encrytped).
10. Go to the app go to create account and create a account to see if u show up. 
11. Report any bugs. 

# Design Patterns/Principles

We have a couple different design and architectural philosophies woven into our app and code. For our overall architecture, our app most closely resembles the MVC (Model, View, Controller) architectural philosophy. When using GetTogether, a user will provide information to the “model,” or the app interface. The app takes that data and uses it to manipulate what pins will be displayed on the Google Maps within the app. Then, the user will see through the UI that the map has been updated, and the event that they created is showing up in the correct location on the map.

As for the life cycle of the development of GetTogether, we most closely followed the staged delivery model. This means that our work was done in stages up until a certain point, at which we transitioned to getting the remaining work done in sections instead of stages. More specifically, we determined our requirements and design for the entire app in order. After we completed the design for all components of our app, we began working on one piece at a time to implement, test, and then maintain. 

First, we focused on setting up our UI and buttons in order to meet the requirements of the Zero Feature Release, and give ourselves a good base to continue working on the app. Then, we implemented the Google Maps component of the app. The map is arguably the most important part of the app, so we needed to incorporate it early on to ensure that it worked as needed. Then, we worked on the login feature, along with the database needed to store all the usernames and passwords of users. 

When designing our UI, we made sure to put an emphasis on simplicity and using as few pages as possible. There are only about 4-5 different screens used in our app, and there isn’t a need for much more than that. The page with the Google Maps displays most of the info needed for usage of the app in an easily accessible and readable format. The UI components we used were buttons and text fields. There are small amounts of information that users need to input themselves, and the range of things that could be inputted is large. Therefore, text boxes were the best choices. Buttons made for an easy-to-use way of navigating the app.


# Design Changes and Rationale

Removed Features:

-User Profile

-Search bar for Search Event

-Filter Event Categories

-Event attendees count

Rationale: 

Most of the features cut were due to time restraints and the capabilities of the team. The user profile seemed superfluous, and was a reasonable feature to cut in the interest of time. The user profile could have provided app users with an additional way to connect with peers and ease the process of setting up events, but ultimately, it is an unnecessary component in respect to the functionality of the app as a whole. 

The search bar was one of the more key features of our app, but it had to be cut in the interest of time. It also would have been a more difficult feature to code, and the app can still function well without it. It may be added back in before final release if time allows.

In our original design, we planned to have a feature that would allow users to filter events by category while they were searching for events. For example, there would be categories like “Sports,” “Art,” “Studying,” etc. We decided that this feature was not necessary unless the app gained popularity and had more users. If there are a small amount of events in a given area to begin with, there is not much use in being able to filter out the events by category. This feature may be implemented in the future if needed.

We originally planned for events to keep a count of users who planned to attend the event. Whenever a user would “join” an event, the count of people for said event would increase by one. We ended up deciding that this feature was largely unnecessary, and provided little use to users who both created and were looking for events.

# HomePage
http://athena.ecs.csus.edu/~gittgthr/

# User Documentation
https://github.com/dcortez2/gittgthr
