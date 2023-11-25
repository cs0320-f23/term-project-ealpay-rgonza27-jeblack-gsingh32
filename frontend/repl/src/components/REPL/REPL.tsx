import { useState, useRef } from "react";
import { History } from "./History";
import { REPLInput } from "./REPLInput";
import { HistoryItem } from "./HistoryItem";

/**
 * Shows the history and then the command line
 * @returns JSX for the REPL component
 */
export default function REPL() {
  const [history, setHistory] = useState<HistoryItem[]>([]);
  const [queryHistory, setQueryHistory] = useState<string[]>([]);
  const historySpaceRef = useRef<HTMLDivElement | null>(null);
  const [isBrief, setIsBrief] = useState<boolean>(true);

  return (
    <div className="repl">
      {/* <hr></hr> */}
      <REPLInput
        history={history}
        setHistory={setHistory}
        queryHistory={queryHistory}
        setQueryHistory={setQueryHistory}
        scrollHistoryToBottom={() => {
          setTimeout(() => {
            if (historySpaceRef.current) {
              historySpaceRef.current.scrollTop =
                historySpaceRef.current.scrollHeight;
            }
          }, 0);
        }}
        isBrief={isBrief}
        setIsBrief={setIsBrief}
      />
      <div className="historySpace" ref={historySpaceRef}>
        <History history={history} isBrief={isBrief} />
      </div>
    </div>
  );
}
