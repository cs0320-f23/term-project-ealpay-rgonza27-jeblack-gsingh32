import React, { Dispatch, SetStateAction } from "react";
import { REPLFunction } from "./REPLFunction";
import { HistoryItem } from "./HistoryItem";
import { loadMock } from "../Mock/MockCommand";
import { search } from "../Mock/MockCommand";
import { viewMock } from "../Mock/MockCommand";
import { broadbandMock } from "../Mock/MockCommand";

export interface InputProps {
  history: HistoryItem[];
  setHistory: Dispatch<SetStateAction<HistoryItem[]>>;
  commandString: string;
  scrollHistoryToBottom: () => void;
  isBrief: boolean;
  setIsBrief: Dispatch<SetStateAction<boolean>>;
}

const commandMap = new Map<string, REPLFunction>();
/**
 * Registers a custom REPL function with a specified name.
 *
 * @param name - The name to associate with the REPL function.
 * @param func - The REPL function to register.
 */
export function register(name: string, func: REPLFunction): void {
  commandMap.set(name, func);
}
/**
 * Removes a registered REPL function associated with the specified name.
 *
 * @param name - The name of the REPL function to remove.
 */
export function remove(name: string): void {
  commandMap.delete(name);
}

/**
 * HandlerClass is responsible for handling user input commands in a REPL (Read-Eval-Print Loop) environment.
 * It can parse, execute, and provide command history in both brief and verbose modes.
 */
export class HandlerClass {
  /**
   * A boolean flag that represents whether the application is in brief mode (true) or verbose mode (false).
   */
  brief: Boolean = true;

  mock: Boolean = false;
  /**
   * A string that holds information about the parsed data from CSV files, or "No files have been parsed" by default.
   */
  parseData: string = "No files have been parsed";
  mockDict = new Map<string, REPLFunction>();

  /**
   * Creates an instance of the HandlerClass.
   */
  constructor() {
    this.mockDict.set("load_file", loadMock);
    this.mockDict.set("search", search);
    this.mockDict.set("view", viewMock);
    this.mockDict.set("broadband", broadbandMock);
    this.setupKeyListener();
  }

  /**
   * Handles the user's input command and manages the application's behavior based on the command.
   * @param history - An array of strings or string arrays representing the command history.
   * @param setHistory - A function to set the command history state.
   * @param commandString - The user's input command string.
   * @param scrollHistoryToBottom - A function to scroll the history to the bottom.
   */
  async handleInput({
    history,
    setHistory,
    commandString,
    scrollHistoryToBottom,
    isBrief,
    setIsBrief,
  }: InputProps) {
    var arrayCommand = commandString.match(/("[^"]+"|[^"\s]+)/g) || [""];
    //var arrayCommand = commandString.split(" ");
    console.log(arrayCommand);

    var commandName = arrayCommand[0];
    if (commandName === "mode") {
      console.log("a");
      if (isBrief) {
        setIsBrief(false);
        setHistory([
          ...history,
          new HistoryItem(commandString, "Mode switched to verbose"),
        ]);
      } else {
        setIsBrief(true);
        setHistory([
          ...history,
          new HistoryItem(commandString, "Mode switched to brief"),
        ]);
      }
    } else {
      if (this.mock) {
        var commandMock = this.mockDict.get(commandName);
        if (commandMock != undefined) {
          console.log("here");
          await commandMock(arrayCommand.slice(1)).then((data) => {
            console.log(data);
            setHistory([...history, new HistoryItem(commandString, data)]);
          });
        } else {
          setHistory([
            ...history,
            new HistoryItem(commandString, "ERROR: Command not valid"),
          ]);
        }
        scrollHistoryToBottom();
        return;
      }
      var command = commandMap.get(commandName);
      if (command != undefined) {
        if (
          commandName ===
          "PLEASE_DELETE_MY_PARSED_DATA_I_KNOW_I_WILL_HAVE_TO_LOAD_IT_AGAIN"
        ) {
          await command(arrayCommand.slice(1)).then((data) => {
            console.log(data);
          });
          return;
        }
        await command(arrayCommand.slice(1)).then((data) => {
          console.log(data);
          setHistory([...history, new HistoryItem(commandString, data)]);
        });
      } else {
        setHistory([
          ...history,
          new HistoryItem(commandString, "ERROR: Command not valid"),
        ]);
      }
    }
    scrollHistoryToBottom();
  }

  getMode(): Boolean {
    return this.brief;
  }

  setupKeyListener() {
    document.addEventListener("keydown", this.handleKeyDown.bind(this));
  }

  /**
   * Handle keydown events to detect the Control + Option + . key combination.
   * If the combination is detected, toggle the `brief` property.
   * @param event - The keydown event.
   */
  handleKeyDown(event: KeyboardEvent) {
    if (event.ctrlKey && event.altKey && event.key === ".") {
      this.toggleMockMode();
    }
  }

  /**
   * Toggle the `brief` property from true to false or vice versa.
   */
  toggleMockMode() {
    this.mock = !this.mock;
    console.log(String(this.mock));
  }
}
