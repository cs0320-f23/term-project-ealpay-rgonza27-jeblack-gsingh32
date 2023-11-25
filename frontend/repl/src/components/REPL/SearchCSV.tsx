/**
 * Searches for a specific value in a CSV data set based on user input.
 *
 * @param {string} input - The input string provided by the user.
 * @param {string} data - The name of the CSV data set to search in.
 * @returns {string} - The search result or an error message.
 */
export function search(args: Array<string>): Promise<string | string[][]> {
  if (args.length == 0) {
    return new Promise((resolve, reject) => {
      resolve("ERROR: enter search terms");
    });
  }

  var target_work = reformatString(args[0]);

  // if there is only one argument we will treat it as the target word and use the
  // default of searching through all columns
  if (args.length == 1) {
    return fetch("http://localhost:3232/search?column=&target=" + target_work)
      .then((response) => response.json())
      .then((data) => {
        if (data["return Type"] === "Failure") {
          return [
            ["return type", "reason"],
            ["Failure", data["reason"]],
          ];
        }
        const header = data["header"];
        const dataRows = data["data"];
        const result = [header, ...dataRows];

        return result;
      })
      .catch((e) => {
        return "ERROR: " + e;
      });
  }
  //if there are 2 arguments it will be the target word and the identifier
  var id = reformatString(args[1]);

  return fetch(
    "http://localhost:3232/search?column=" + id + "&target=" + target_work
  )
    .then((response) => response.json())
    .then((data) => {
      if (data["return Type"] === "Failure") {
        return [
          ["return type", "reason"],
          ["Failure", data["reason"]],
        ];
      }

      const header = data["header"];
      const dataRows = data["data"];
      const result = [header, ...dataRows]; // Join header and data

      return result;
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
function reformatString(word: String): String {
  if (word[0] === '"' && word[word.length - 1] === '"') {
    word = word.substring(1, word.length - 1);
    return word;
  }
  return word;
}
