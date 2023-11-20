import { useEffect } from "react";
import { HistoryItem } from "./HistoryItem";

export interface HistoryProps {
  history: HistoryItem[];
  isBrief: boolean;
}

/**
 * Creates an HTML table element from a 2D array of strings, that symbolize the rows of the responses.
 *
 * @param output - The 2D array of strings to be displayed in the table.
 * @returns An HTML table element containing the provided data.
 */
function createTable(output: string[][]) {
  return (
    <table>
      <tbody>
        {output.map((row, index) => (
          <tr className="row">
            {row.map((item) => (
              <td>{item}</td>
            ))}
          </tr>
        ))}
      </tbody>
    </table>
  );
}

/**
 * Loops through the history to return each element
 * as either a paragraph if it's a string, or an HTML table
 * if it's a list of list of strings
 *
 * @param props - HistoryProps that contains the history list
 * @returns - element of history properly formatted
 */
export function History(props: HistoryProps) {
  useEffect(() => {
    const handleKey = (event: KeyboardEvent) => {
      if (event.key === "h" && (event.metaKey || event.ctrlKey)) {
        const targetElement = document.getElementById("history-container");
        console.log(targetElement);
        if (targetElement) {
          targetElement.tabIndex = 0;
          targetElement.focus();
        }
      }
    };

    window.addEventListener("keydown", handleKey);

    return () => {
      window.removeEventListener("keydown", handleKey);
    };
  }, []);

  return (
    <div className="history" id="history-container" aria-label="history box">
      {props.history.map((historyItem, index) => {
        const isLast = index === props.history.length - 1;
        if (typeof historyItem.output === "string") {
          if (props.isBrief) {
            return (
              <p aria-live={isLast ? "assertive" : "off"}>
                {historyItem.output}
              </p>
            );
          } else {
            return (
              <p aria-live={isLast ? "assertive" : "off"}>
                Command: {historyItem.command} <br></br> Output:
                {historyItem.output}
              </p>
            );
          }
        } else {
          if (props.isBrief) {
            return (
              <p aria-live={isLast ? "assertive" : "off"}>
                {" "}
                {createTable(historyItem.output)}
              </p>
            );
          } else {
            return (
              <p aria-live={isLast ? "assertive" : "off"}>
                Command: {historyItem.command} <br></br> Output:{" "}
                {createTable(historyItem.output)}
              </p>
            );
          }
        }
      })}
    </div>
  );
}
