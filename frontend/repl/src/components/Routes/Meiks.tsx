import React, { useState, useEffect } from "react";
import cardView from "../Search/cardView";
import Header from "../HomePage/Header";
import "../../styles/Meiks.css";
import { motion } from "framer-motion";
import { VerticalScroll } from "../Helpers/ScrollComponents";
import { AllMeiks } from "./MeikHandler";
import Meik from "./MeikObject";

interface IMeikProps {}

const Meiks: React.FunctionComponent<IMeikProps> = (props) => {
  const [tags, setTags] = useState<string[]>([]);
  const [allMeiksData, setAllMeiksData] = useState<Meik[]>([]);
  const [meikObjects, setMeikObjects] = useState<Meik[]>([]);
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
    if (allMeiksData.length === 0) {
      AllMeiks().then((data) => {
        const meikObjects: Meik[] = data.map((item) =>
          typeof item === "string" ? JSON.parse(item) : item
        );
        setAllMeiksData(meikObjects);
        setMeikObjects(meikObjects);
      });
    } else {
      // Filter the meikObjects based on tags
      const filteredMeikObjects = allMeiksData.filter((meik) =>
        tags.every(
          (tag) =>
            meik.name.includes(tag) ||
            meik.concentration.includes(tag) ||
            meik.year.includes(tag) ||
            meik.location.includes(tag) ||
            meik.email.includes(tag) ||
            meik.tags.some((item) => item.includes(tag))
        )
      );
      setMeikObjects(filteredMeikObjects);
    }

    return () => {
      input?.removeEventListener("keyup", handleKey);
    };
  }, [tags, inputValue]); // Ensure all relevant dependencies are listed

  return (
    <div>
      <Header
        onLinkClickContact={function (): void {
          throw new Error("Function not implemented.");
        }}
        onLinkClickAbout={function (): void {
          throw new Error("Function not implemented.");
        }}
        onLinkClickJoin={function (): void {
          throw new Error("Function not implemented.");
        }}
      />
      <VerticalScroll>
        {" "}
        <motion.div
          className="MainBody"
          initial={{ opacity: 0, y: -400 }}
          animate={{ opacity: 1, y: 0 }}
          exit={{ opacity: 0, y: 400 }}
          transition={{ duration: 0.2 }}
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
            {meikObjects.map((meikObject, index) => (
              <div
                className="Rows"
                key={index}
                style={{ display: "inline-block" }}
              >
                {cardView(meikObject, null)}
              </div>
            ))}
          </div>
        </motion.div>
      </VerticalScroll>
    </div>
  );
};

export default Meiks;
