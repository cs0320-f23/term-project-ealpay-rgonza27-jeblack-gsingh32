import React, { useState, Dispatch, SetStateAction, useEffect } from "react";
import { ControlledInput } from "./ControlledInput";
import {
  TEXT_input_box,
  TEXT_try_button_accessible_name,
  TEXT_try_button_text,
} from "../constants";
import { HandlerClass } from "./Handler";
import { HistoryItem } from "./HistoryItem";

/**
 * Props for history, scrolling and notification
 */
export interface InputProps {
  history: HistoryItem[];
  setHistory: Dispatch<SetStateAction<HistoryItem[]>>;
  queryHistory: string[];
  setQueryHistory: Dispatch<SetStateAction<string[]>>;
  scrollHistoryToBottom: () => void;
  isBrief: boolean;
  setIsBrief: Dispatch<SetStateAction<boolean>>;
}

// Create an instance of HandlerClass
var handl = new HandlerClass();
var dark: boolean = false;

/**
 * Represents the REPL input component, which handles accessibility and creates a Handler.
 *
 * @param {InputProps} props - The component's props including history, setHistory,
 * queryHistory, setQueryHistory and scrollHistoryToBottom.
 * @returns {JSX.Element} - The command line box and submit button.
 */
export function REPLInput({
  queryHistory,
  history,
  setHistory,
  setQueryHistory,
  scrollHistoryToBottom,
  isBrief,
  setIsBrief,
}: InputProps) {
  const [value, setValue] = useState(""); // State for the input value
  const [historyIndex, setHistoryIndex] = useState<number>(-1); // State for history navigation index

  // useEffect to listen for up and down arrow keys and navigate the history
  useEffect(() => {
    const handleKey = (event: KeyboardEvent) => {
      if (event.key === "ArrowLeft") {
        navigateHistory("down");
      } else if (event.key === "ArrowRight") {
        navigateHistory("up");
      }
      if ((event.metaKey || event.ctrlKey) && event.key === "m") {
        darkMode();
      }
      if ((event.metaKey || event.ctrlKey) && event.key === "s") {
        const targetElement = document.getElementById("submit-button");
        if (targetElement) {
          targetElement.tabIndex = 0;
          targetElement.focus();
        }
      }
    };

    window.addEventListener("keydown", handleKey);
    setValue(queryHistory[queryHistory.length - 1 - historyIndex] || "");
    return () => {
      window.removeEventListener("keydown", handleKey);
    };
  }, [historyIndex, queryHistory]);

  // allows dark and light mode
  const darkMode = () => {
    if (dark) {
      dark = !dark;
      document.querySelector("table")?.classList.remove("dark");
      document.querySelector(".App-header")?.classList.remove("dark");
      document.querySelector("legend")?.classList.remove("dark");
      document.querySelector(".th")?.classList.remove("dark");
      document.querySelector(".td")?.classList.remove("dark");
      document.querySelector(".history")?.classList.remove("dark");
      document.querySelector(".historySpace")?.classList.remove("dark");
      document.body.classList.remove("dark");
    } else {
      dark = !dark;
      document.querySelector("table")?.classList.add("dark");
      document.querySelector(".App-header")?.classList.add("dark");
      document.querySelector("legend")?.classList.add("dark");
      document.querySelector(".history")?.classList.add("dark");
      document.body.classList.add("dark");
      document.querySelector(".historySpace")?.classList.add("dark");
    }
  };

  /**
   * Navigates through the command history.
   *
   * @param {"up" | "down"} direction - The direction to navigate (up or down).
   */
  const navigateHistory = (direction: "up" | "down") => {
    if (direction === "up") {
      // When navigating up, decrease the historyIndex
      const newIndex = historyIndex - 1;
      setHistoryIndex(Math.max(newIndex, -1)); // Ensure historyIndex doesn't go below -1
    } else if (direction === "down") {
      // When navigating down, increase the historyIndex
      const newIndex = historyIndex + 1;
      setHistoryIndex(Math.min(newIndex, queryHistory.length - 1)); // Ensure historyIndex doesn't exceed the history length
    }
  };

  /**
   * Handles the form submission.
   *
   * @param {React.FormEvent} event - The form submission event.
   */
  const handleSubmit = (event: React.FormEvent) => {
    setQueryHistory([...queryHistory, value]);
    event.preventDefault(); // Prevent the default form submission
    handl.handleInput({
      history,
      setHistory,
      commandString: value,
      scrollHistoryToBottom,
      isBrief,
      setIsBrief,
    });
    // Reset history index and input value after submitting
    setHistoryIndex(-1);
    setValue("");
  };

  return (
    <div>
      <div>
        <form onSubmit={handleSubmit}>
          {" "}
          <fieldset>
            <legend>Type your command lines here!</legend>
            <ControlledInput
              value={value}
              setValue={setValue}
              ariaLabel={TEXT_input_box}
            />
          </fieldset>
          <div>
            <button
              type="submit"
              id="submit-button"
              aria-label={TEXT_try_button_accessible_name}
              tabIndex={2}
            >
              {TEXT_try_button_text}
            </button>
          </div>
        </form>
      </div>
    </div>
  );
}
