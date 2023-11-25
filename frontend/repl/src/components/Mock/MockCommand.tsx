import { filepathDictionary } from "./mockedJson";
import { broadbandDict } from "./mockedJson";

/**
 * Takes in a filepath and returns result message along with the
 * parsed csv of that filepath
 *
 * @param filePath of the csv that is being loaded
 * @returns success or error message along with the parsed csv data
 */

var data: string[][];
var path: string;
export function loadMock(args: string[]) {
  var value = filepathDictionary.get(args[0]);
  if (value !== undefined) {
    data = value;
    path = args[0];
    return new Promise<string>((resolves) => resolves("Success!"));
  }
  console.log("undefiend");
  return new Promise<string>((resolves) => resolves("Couldn't find the file"));
}
/**
 * A mock function that returns data if available, or a message if no data is present.
 *
 * @param args - An array of strings (unused in this function).
 * @returns A Promise that resolves to either a 2D array of data or a message.
 */
export function viewMock(args: string[]) {
  if (data !== undefined) {
    return new Promise<string[][]>((resolves) => resolves(data));
  }
  return new Promise<string>((resolves) => resolves("Please load a file"));
}
import { mainSearchDict } from "../Mock/mockedJson";

/**
 * Searches for a specific value in a CSV data set based on user input.
 *
 * @param {string} input - The input string provided by the user.
 * @param {string} data - The name of the CSV data set to search in.
 * @returns {string} - The search result or an error message.
 */
export function search(args: string[]) {
  var value = "";

  var specificDict = mainSearchDict.get(filepathDictionary.get(path)); // Get the dictionary of this CSV file
  if (specificDict === undefined) {
    return new Promise<string>((resolves) =>
      resolves("Please Load File First")
    );
  }
  var response: string[][];
  var header = "";
  if (args[1] != undefined) {
    header = reformatString(args[0]);
    console.log(header + "," + reformatString(args[1]));
    response = specificDict.get(header + "," + reformatString(args[1])); // Get the specific search entry from this CSV
  } else {
    response = specificDict.get(reformatString(args[0]));
    console.log("This" + response);
    // Get the specific search entry from this CSV
  }

  if (response === undefined) {
    return new Promise<string>((resolves) => resolves("Value was not found"));
  }

  return new Promise<string[][]>((resolves) => resolves(response));
}
/**
 * A mock function that simulates a request for broadband data based on the provided state and county.
 *
 * @param args - An array of strings where the first element is the state and the second element is the county.
 * @returns A Promise that resolves to either a 2D array of broadband data or an error message.
 */
export function broadbandMock(args: string[]) {
  var state = reformatString(args[0]);
  var county = reformatString(args[1]);
  var response = broadbandDict.get(state + county);
  if (response != undefined) {
    var good_response = response;
    return new Promise<string[][]>((resolves) => resolves(good_response));
  }
  return new Promise<string>((resolves) =>
    resolves("Error: please enter a valid State or County")
  );
}
/**
 * Removes leading and trailing double quotation marks from a string if they are present.
 *
 * @param word - The input string that may have leading and trailing quotation marks.
 * @returns The input string with quotation marks removed, if present.
 */
function reformatString(word: string): string {
  if (word[0] === '"' && word[word.length - 1] === '"') {
    word = word.substring(1, word.length - 1);
    return word;
  }
  return word;
}
