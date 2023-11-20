# REPL README

Team Members: rdbrooks and rgonza27

Total Time: 20 hours

Github repository: https://github.com/cs0320-f23/repl-rdbrooks-rgonza27

Responsibilites: almost all of the code was pair-programming, we divided up responsibilities for testing (rgonza27 did unit testing and rdbrooks did integration testing) but checked and added to each other's work

Classmates we discussed with: @22ridley, @helium-balloon

\*If you would like to learn about the backend (Server sprint) specfically, navigate to the appropriate folder and read this specific README. This is the README overall for REPL, which is the integration of both the frontend and backend.

#### Design Choices:

We made various design choiecs to satisfy the user stories and practice good design:

- Our code is organized into a frontend and backend directory. Within the frontend directory, there is a repl folder that contains the frontend code. The src folder has the various react components and styles (two seperate directories), and the tests folder has both the mock and integration tests. Within the backend directory, the data directory contains the data used in testing the program, and the src directory has both the main directory and test directory. The main directory has code for the server as well as the csv parser/searcher which are stored seperately. The test directory has tests for the backend, including using real and mocked ACS data for broadband.
- To register a command, one must export the register function from Handler (it is already exported to map). By default, we register the commands outlined below, but there is also a remove function that can be called if these are not desired. Our commands are stored in a hashmap in handler. All commands have seperate functions that implement the REPLFunction interface, demonstrating the strategy pattern. The REPLFunction interface takes in a string of arguments and returns a promise of either a string or array of strings. The user's command gets broken up with a regex expression that splits based on space, except for when words are enclosed in double quotation marks, and passed as the argument to the REPL function. An array of strings is returned in the instance of an error so the user can see what went wrong, broadband so all necessary information is shown and view to output the csv as a table. The Handler component stores the hashmap, takes care of registering/removing functions and takes the outputs from these functions and adds them to the history.
- Our mocked data is stored within a Mock directory inside of the frontend components directory. The program enters mock mode when a specific sequence of keys is entered (which playwright does when running the tests). The program will check if it is in mock mode and if so use functions from the mocked map, which return the mocked data. This allows us to check the frontend works on its own, thus isolating issues before examining the integration of both the front and backend.
- In terms of defensive programming, our program only passes in essential props to components and const is used when appropriate. The backend makes defensive copies to ensure that data is not modified accidentally, and this gets passed to the frontend. Since the frontend is merely displaying the information recieved from making a request to the backend, there is no need to make a copy. We check for errors in terms of invalid commands or arguments. Otherwise, errors from the backend and their messages are also displayed to the user in the form of a table so they can see entered parameters.

#### Errors/Bugs:

We have found no bugs in our program.

#### Tests:

We have both integration and unit tests for this program. The unit tests use mocked data for the backend and thus check that the frontend can function independently. These are in the Mock.spec.ts file. The integration tests actually call the backend with various inputs to ensure that the frontend/backend can run together. These are in the Integration.spec.ts. To run these tests, open the repl directory inside of the frontend directy, run `npx install playwright` and then `npx playwright test`. There are descriptions of what the tests check before each one, but overall we checked that the commands work seperately and together and that errors are displayed appropriately.

We also have many additional tests for the backend that can be run in Intellj ,and there are more detailed instructions in the README within the backend folder.

#### How to Build and Run Program:

The backend and frontend of the program have to be run seperately. We recommend opening the backend in IntellJ and the frontend in React.

- To run the backend, open the pom.xml file within the backend directory. Navigate to the main method in the Server class and run it. A message should show in the terminal that the server is running.
- To run the frontend, enter the repl directory within the frontend directory. Use `npm install` to install the necessary node modules then enter `npm start` in the terminal. A message show show in the terminal that the local host is running and navigate to the given url.

As instructed at the top of the program, the possible commands are:

- `load_file` `<csv-file-path> <is-header>`
  - The default is false if you do not enter a value for is-header
- `view`
- `search` `<column> <value>`
  - You can search by column index or column name
  - If you do not want to search by a specific column, you may just enter the value you want to serach
- `broadband` `<state> <county>`
- `mode`
  - The default mode is brief, which returns the output of your command
  - Verbose mode returns both your input and output

NOTE: If you want to search any term that includes a space, please enclose it in " ".

The possible shortcuts are:

- control i: select instructions
- control m: switch color modes
- control b: select command box
  - enter to submit
- control s: seelct submit button
  - enter to submit
- control h: select history
- up and down arrows to scroll on page
- left and right arrows to navigate through previous commands

Our program is also compatible with a screen reader :)
