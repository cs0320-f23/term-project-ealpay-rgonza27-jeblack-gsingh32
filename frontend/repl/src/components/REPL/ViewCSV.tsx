/**
 * Sends a request to view data from a server endpoint and formats the response as a 2D array.
 *
 * @param args - An array of strings (unused in this function).
 * @returns A Promise that resolves to a 2D array containing the data to be viewed, or an error message.
 */
export function view(args: Array<string>): Promise<string | string[][]> {
  return fetch("http://localhost:3232/viewCSV")
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
