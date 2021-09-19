# Genetics Buddy

![Project Screenshot 1](https://raw.githubusercontent.com/owensizemore/Genetics-Buddy-App/master/screenshot2.PNG)![Project Screenshot 2](https://raw.githubusercontent.com/owensizemore/Genetics-Buddy-App/master/screenshot1.PNG)

### Project Submitted to Peace Out Hacks (9/19/2021) and The Envirothon (9/19/2021)

## Inspiration
We were inspired to create this project because one of our team members is currently an undergraduate genetics researcher at their university. They expressed to the team that one issue they face in their research is the fact that a great deal of genetic data, particular the names and details of vectors and their corresponding restriction sites, is scattered across the internet and hard to gather. To ease this pain point and provide an easier way for scientists and researchers to gain meaningful conclusions from commonly-used genetic data, we have created the Genetics Buddy app.

## What it does
The Genetics Buddy app allows users to select a vector with a dropdown menu and compare two of its corresponding restriction sites in separate dropdown menus in order to learn more about the sites and find the best buffer for an interaction between them. Once options have been selected, the app will populate with various pieces of data about the restriction site's cut site sequence, diluent compatibility, heat inactivation, incubuation temperature, buffer activity, and more.

## How we built it
Genetics Buddy was built using the Java programming language in Android Studio. We also implemented Google Firebase's Firestore database suite to store the genetic data for access by the app.

## Challenges we ran into
One challenge we ran into on the development side was trying to find the most efficient way to pass data through the app after querying the database. The database queries were accomplished using anonymous class calls, making any variables stored within hard to access.

## Accomplishments that we're proud of
We are proud of the fact that our app successfully blended the rich functionality of an Android app with the sophisticated database technology of Firebase Firestore. Our app contains a great deal of genetic data for several vectors and dozens of restriction sites, providing a real utility for scientists and researchers that need access to this information.

## What we learned
Our team learned a great deal more about the Java programming language, Android Studio, and the capabilities of Firebase Firestore with respect to storing and accessing data.

## What's next for Genetics Buddy
In the future, we would love to implement a richer UI alongside the ability for users to create an account and save restriction site combinations to their account for quick re-access. We discussed users having their own "Journal" page where they could view combinations and add important notes.
