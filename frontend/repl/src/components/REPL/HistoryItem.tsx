/**
 * Represents a history item containing a command and its corresponding output.
 */
export class HistoryItem {
  /**
   * The command executed, represented as a string.
   */
  command: string;
  /**
   * The output of the command, which can be either a string or a 2D array of strings.
   */
  output: string | string[][];
  /**
   * Constructs a new HistoryItem with the provided command and output.
   *
   * @param command - The command executed, represented as a string.
   * @param output - The output of the command, which can be either a string or a 2D array of strings.
   */
  constructor(command: string, output: string | string[][]) {
    this.command = command;
    this.output = output;
  }
}
