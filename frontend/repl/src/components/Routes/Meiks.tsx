import React, { useState, useEffect } from "react";
import cardView from "../Search/cardView";
import Header from "../HomePage/Header";
import "../../styles/Meiks.css";
import { motion } from "framer-motion";

interface IMeikProps {}

const Meiks: React.FunctionComponent<IMeikProps> = (props) => {
  const [tags, setTags] = useState<string[]>([]);
  const [inputValue, setInputValue] = useState<string>("");

  const addTag = (e: KeyboardEvent) => {
    if (e.key === "Enter" && inputValue.trim() !== "") {
      const tag = inputValue.trim();
      if (tag.length > 1 && !tags.includes(tag)) {
        setTags([...tags, tag]);
        setInputValue("");
      }
    }
  };

  const removeTag = (tagToRemove: string) => {
    setTags(tags.filter((tag) => tag !== tagToRemove));
  };

  useEffect(() => {
    const ul = document.querySelector("ul");
    const input = ul?.querySelector("input");

    const handleKey = (ev: KeyboardEvent) => {
      addTag(ev);
    };

    input?.addEventListener("keyup", handleKey);

    return () => {
      input?.removeEventListener("keyup", handleKey);
    };
  }, [tags, inputValue]); // Ensure all relevant dependencies are listed

  return (
    <div>
      <Header />
      <motion.div
        className="MainBody"
        initial={{ opacity: 0, y: -400 }}
        animate={{ opacity: 1, y: 0 }}
        exit={{ opacity: 0, y: 400 }}
        transition={{ duration: 0.3 }}
      >
        <span className="Title">Find the perfect Meik for you!</span>
        <ul className="TagBox">
          {tags.map((tag) => (
            <li key={tag}>
              {tag}
              <span className="X" onClick={() => removeTag(tag)}>
                ×
              </span>
            </li>
          ))}
          <input
            className="SearchBarInput"
            type="text"
            value={inputValue}
            onChange={(e) => setInputValue(e.target.value)}
          />
        </ul>
        <div>
          {cardView({
            name: "ExampleName",
            concentration: "Applied Example",
            year: "'26",
            email: "example@brown.edu",
            location: "example, RI",
          })}
        </div>
      </motion.div>
    </div>
  );
};

export default Meiks;
