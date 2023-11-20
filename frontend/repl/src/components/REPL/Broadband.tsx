/**
 * This function retrieves broadband data for a given state and county.
 *
 * @param args - An array of strings where the first element is the state and the second element is the county.
 * @returns A Promise that resolves to either a string indicating an error or a 2D array containing the broadband data.
 */
export function broadband(args: Array<string>): Promise<string | string[][]> {
  if (args.length < 2) {
    return new Promise((resolve, reject) => {
      resolve("ERROR: enter state and county for broadband");
    });
  }
  var state = reformatString(args[0]);
  var county = reformatString(args[1]);

  return fetch(
    "http://localhost:3232/broadBand?State=" + state + "&county=" + county
  )
    .then((response) => response.json())
    .then((data) => {
      if (data["return Type"] === "Failure") {
        return [
          ["return type", "reason"],
          ["Failure", data["reason"]],
        ];
      }

      // Extract key-value pairs from the data and reformat as a 2D array
      const formattedData = Object.entries(data).map(([key, value]) => [
        key.toString(),
        String(value),
      ]);

      return formattedData;
    })
    .catch((e) => {
      return "ERROR: " + e;
    });
}

/**
 * This function removes quotation marks from a string if they are present at the beginning and end.
 *
 * @param word - The input string that may have leading and trailing quotation marks.
 * @returns The input string with quotation marks removed, if present.
 */
function reformatString(word: string): string {
  if (word[0] === '"' && word[word.length - 1] === '"') {
    word = word.substring(1, word.length - 1);
  }
  return word;
}
