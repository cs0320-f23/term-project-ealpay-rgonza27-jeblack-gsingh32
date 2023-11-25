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
//Put this on a separate file
const firebaseConfig = {
  apiKey: "AIzaSyDxt7oAN1TTXBK4P03xaywk3fW59Yt0VpE",
  authDomain: "meikdatabase.firebaseapp.com",
  projectId: "meikdatabase",
  storageBucket: "meikdatabase.appspot.com",
  messagingSenderId: "586307420979",
  appId: "1:586307420979:web:e83246de7e624e04bbb446",
  measurementId: "G-8JDMYG4ZG2",
};
//

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
