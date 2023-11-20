import REPL from "./REPL/REPL";

import "../styles/App.css";
import { load } from "./REPL/LoadCSV";
import { remove } from "./REPL/LoadCSV";
import { view } from "./REPL/ViewCSV";
import { search } from "./REPL/SearchCSV";
import { broadband } from "./REPL/Broadband";
import { register } from "./REPL/Handler";
import { useEffect } from "react";

/**
 * Top-level application component. Shows the program description and then
 * loads the command-line REPL component
 * @returns JSX for the App component
 */

function App() {
  register("load_file", load);
  register("view", view);
  register("broadband", broadband);
  register("search", search);
  register(
    "PLEASE_DELETE_MY_PARSED_DATA_I_KNOW_I_WILL_HAVE_TO_LOAD_IT_AGAIN",
    remove
  );

  useEffect(() => {
    const handleKey = (event: KeyboardEvent) => {
      if (event.key === "i" && (event.metaKey || event.ctrlKey)) {
        const targetElement = document.getElementById("instructions");
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
    <div className="App">
      <div className="App-header" data-testid="test:header-text">
        <div id="Title"> REPL </div>
        <div id="instructions">
          Possible commands: load_file (type "load_file &lt;csv-file-path&gt;
          &lt;is-header;"), view (type "view"), search (type "search
          &lt;column&gt; &lt;value&gt;") and broadband (type "broadband
          &lt;state&gt; &lt;country&gt;"). The default mode is brief, which
          returns the output of your command, and verbose mode returns both your
          input and output (type "mode" to switch). Keyboard shortcuts: left and
          right arrows to navigate through previous commands, control i to
          select instructions, control m to switch color modes, control b to
          select command box, control s to select submit button, enter to
          submit, control h to select history, up and down to scroll on page.
        </div>
      </div>
      <REPL />
    </div>
  );
}

export default App;
