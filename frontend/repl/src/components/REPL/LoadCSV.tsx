/**
 * Takes in a filepath and returns result message along with the
 * parsed csv of that filepath
 *
 * @param filePath of the csv that is being loaded
 * @returns success or error message along with the parsed csv data
 */
export function load(args: Array<string>): Promise<string | string[][]> {
  if (args.length == 0) {
    return new Promise((resolve, reject) => {
      resolve("ERROR: enter filepath and if header");
    });
  }

  var hasHeader = false; // default: does not have headers
  if (args.length > 1) {
    let headerArg = args[1];
    if (headerArg.toLowerCase() === "true") {
      hasHeader = true;
    } else if (headerArg.toLowerCase() === "false") {
      hasHeader = false;
    } else {
      return new Promise<string>((resolves) =>
        resolves("Please enter a valid header value: either true or false")
      );
    }
  }
  return fetch(
    "http://localhost:3232/loadCSV?Path=" + args[0] + "&hasHeader=" + hasHeader
  )
    .then((response) => response.json())
    .then((data) => {
      if (data["return Type"] === "Failure") {
        return [
          ["return type", "reason"],
          ["Failure", data["reason"]],
        ];
      }

      return data["return Type"];
    });
}

/**
 * Sends a request to remove parsed data from a server endpoint.
 *
 * @param args - An array of strings (unused in this function).
 * @returns A Promise that resolves to a string indicating the result of the removal process.
 */
export function remove(args: Array<string>): Promise<string | string[][]> {
  return fetch("http://localhost:3232/DELETE_PARSED_DATA").then(
    (response) => "a"
  );
}
