# FrontEnd Documentation

## FrontEnd Elements General Overview

#### Auth

•⁠ ⁠purpose: The components in this file are for the log in screen and its relative animations, it defines the frontend navigaton points in Register.tsx

•⁠ ⁠subclasses:
•⁠ ⁠animatedComp
•⁠ ⁠AuthRoute
•⁠ ⁠LogIn
•⁠ ⁠NoneMeikRoute
•⁠ ⁠Register

#### Helpers

•⁠ ⁠purpose: The components in this file contain helper functions and constants utilized in other parts of the frontend such as a list of concentrations, image converters and scroll components.

•⁠ ⁠subclasses:
•⁠ ⁠concentrations
•⁠ ⁠ImageConvertor
•⁠ ⁠ScrollComponents
•⁠ ⁠tags

#### HomePage

•⁠ ⁠purpose: The components in this file contain all the individual parts of the main page including banner, header and body with the logic they need to function

•⁠ ⁠subclasses:
•⁠ ⁠AboutUs
•⁠ ⁠Banner
•⁠ ⁠contactForm
•⁠ ⁠Header
•⁠ ⁠Home
•⁠ ⁠Image
•⁠ ⁠JoinUs
•⁠ ⁠Loader
•⁠ ⁠ScrollToTop

#### Routes

•⁠ ⁠purpose: The components in this file contains all the pages that have their unique endpoint or navigation point that can be reached from the frontend. It also includes user objects for the frontend. It also includes the logic for backend to frontend conversion of data in the MeikHandler class

•⁠ ⁠subclasses:
•⁠ ⁠FirstYearObject
•⁠ ⁠MeikObject
•⁠ ⁠MeikHandler
•⁠ ⁠Meiks
•⁠ ⁠Profile
•⁠ ⁠UserProfile

#### Search

•⁠ ⁠purpose: The components in this file contains all the cards (elements that hold a user info in an arranged manner to be displayed)

•⁠ ⁠subclasses:
•⁠ ⁠cardView
•⁠ ⁠cardViewFirstYear

#### Styles

•⁠ ⁠purpose: This file includes all the css for all the files mentioned above, named accordingly.

#### Testing

•⁠ ⁠purpose: Our main tests for this project are under App.spec.ts within the testing folder. It is important to mention these tests can be run via playwright and all pass individually. Some problems arise when all the tests are run together which needs to be solved in the future. More intricate tests also need to be added in the future, the current list of tests are not all comprehensive (due to the time constrainsts we has).

## Project Goal:

To provide the Meik leadership program a website that has all the meiks as users that the first years can look through. This aims to help Meiks to convey their interests more clearly to first years so that more meaningful relationships can be formed. Our website also provides a recommendation button for first years to be connected with Meiks.
