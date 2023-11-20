## Project details:

#### Project name:
Server

#### Team members and contributions (include cs logins):
ealpay: caching, general design, retrieving data from API, Javadocs, Readme

rgonza27: APIUtilities(serialization + deserialization), testing, formatting API data

collaborative: handlers, protective programming, style editing
(Additionally we went over the entire code together for overall comprehension,
most work were done side by side)


#### Include the total estimated time it took to complete project:

15h

#### A link to your repo:
https://github.com/cs0320-f23/server-RobertoGonzalesMatos-efealpay01.git

#### Design choices -- high level design of your program:

Our Server project uses the following main classes: APIOutput, APIUtilities,
HandlerClasses(viewCSV, loadCSV, searchCSV, broadBand, Mock), Server, CacheData, and one interface
ICache.

Server is the top-level class for this project and utilizes Spark to create and maintain a
server with all the endpoints defined.APIOutput handles the logic for actually making the requests
to the API(s) in question. APIUtilities handles all the serialization and deserialization that
happen in the handlers. Handler classes each have a certain function in the Server method.
Each of them handle a certain endpoint that could be called on by the end user. They all handle
their respective tasks within their classes' handle methods.

In order to get information from one class to the other we decided to use a static container object
which will serve as a bridge between csvLoad and CsvView/searchCsv. The object starts with empty 
fields and they are loaded when the CSV is processed. If there were to be no CSV to retrieve 
information from it will have empty lists. We use detail to check whether a CSV has been loaded or 
not when reaching the viewCSV/searchCSV endpoints.

ICache is implemented by the CacheData
class as well as the APIOutput class so that the CacheData class can override the output method
initially defined within APIOutput and utilize it in the load method from the Guava Cache Library.
The CacheData class is instantiated in the broadBand handler to store the data that the endpoint
retrieves. The ways to empty the cache can be defined by the developer through its parameters.

Additionally, all CSV related handlers share the same instance of "container" (an object type that is
used to hold CSV data) so that they can all refer to the same CSV file.

All potential errors that could come up from the handlers have been dealt in a way to give the user
enough information on what is causing the issue.


#### Runtime/ space optimizations you made (if applicable):
Optimizations for search that follow to this proj: O(1) constant time to access a column and linear
time O(N) to find the item in the column where N is the number of rows.

#### Errors/Bugs:
None!

#### Explanations for checkstyle errors (hopefully none):
none!

#### Tests:

All tests discussed here are in order they're displayed in the actual testing file.

APIOutput Tests:

* A generic test for APIOutput's output method with valid State and County pair
* Testing .output with invalid state name
* Testing .output with invalid county name
* Simple test for getStates method in APIOutput
* Testing getCounties with valid state input
* Testing getCounties with empty state input

BroadBandHandler Tests:

* Testing mockHandler with a valid input
* Testing mockHandler with missing query parameters 
* Testing mockHandler with empty query parameters
* Testing broadbandHandler with a valid inputs
* Testing broadbandHandler with missing query parameters
* Testing broadbandHandler with empty query parameters

LoadCSVHandler Tests:

* Testing loadCSVHandler with a valid input
* Testing loadCSVHandler with a missing "path" parameter
* Testing loadCSVHandler with Invalid "hasHeader" parameter

SearchCSVHandler Tests:

* Testing SearchCSVHandler with valid inputs
* Testing SearchCSVHandler with missing parameters
* Testing SearchCSVHandler with no parsed data (CSV not loaded)

APIUtilities Tests:

* Testing general case for FromJSON
* Testing general case for FromJSONMap
* Testing general case for FromJSONMapCounties
* Testing conversion from parsed object(container) to String
* Testing conversion from parsed object(list) to String
* Testing the formatting for our displayed JSONs
* Testing the JSON method that gives simple status messages

Parse Tests:

* Test Parse with other creator classes, test the errors that it could throw like having an invalid
file name.

Search Tests:

* Test with a column value with and without header
* Test with no column value with and without header
* Test with a column value with and without header and with Caps on the input
* Test with no column value with and without header and with Caps on the input
* Test with a column value with Caps on the column
* Tests with a number column value with and without header
* Entering a search for row 0 when the header is eliminated
* Entering a Column that doesn't exist with no header
* Entering a Column that doesn't exist with a header
* Entering a Column that doesn't exist with a header with negative entry
* Entering a Column that doesn't exist with a header with entry bigger than list length

ViewCSVHandler Tests:

* Testing ViewCSVHandler with valid input


#### How to…
#### Run the tests you wrote/were provided:
    You click the green button at the top of the test class and it runs all tests in the class
    If you want to run the tests for exceptions then you click the arrow in that method
    same goes for all other ones.

#### Build and run your program:
    You run the server and put in the necessary endpoints after the port as directed. There's a
    different functionality for each endpoint and they all have their respective parameters that 
    need to be put in by the user. 


