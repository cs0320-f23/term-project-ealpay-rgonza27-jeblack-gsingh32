import React, { useState, useEffect } from "react";
import cardView from "../Search/cardView";
import Header from "../HomePage/Header";
import "../../styles/Meiks.css";
import { motion } from "framer-motion";
import { VerticalScroll } from "../Helpers/ScrollComponents";
import { AllMeiks, updateSearch } from "./MeikHandler";
import Meik from "./MeikObject";
import { stringToImage } from "../Helpers/ImageConvertor";
import { getAuth, onAuthStateChanged } from "firebase/auth";

interface IMeikProps {}

const Meiks: React.FunctionComponent<IMeikProps> = (props) => {
  const [tags, setTags] = useState<string[]>([]);
  const [allMeiksData, setAllMeiksData] = useState<Meik[]>([]);
  const [meikObjects, setMeikObjects] = useState<Meik[]>([]);
  const [inputValue, setInputValue] = useState<string>("");
  const [images, setImages] = useState<HTMLImageElement[]>([]);
  const [imagesBack, setImagesBack] = useState<HTMLImageElement[]>([]);
  const [uid, setUid] = useState("");

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(getAuth(), (user) => {
      if (user) {
        setUid(user.uid);
      }
    });

    return () => unsubscribe();
  });
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
        console.log(data);
        const imageElements: HTMLImageElement[] = data.map((item) =>
          stringToImage(
            typeof item === "string" ? JSON.parse(item)["image"] : item["image"]
          )
        );

        setImages(imageElements);
        setImagesBack(imageElements);
        const meikObjects: Meik[] = data.map((item) =>
          typeof item === "string" ? JSON.parse(item) : item
        );
        setAllMeiksData(meikObjects);
        setMeikObjects(meikObjects);
      });
    } else {
      setImagesBack(images);
      const filteredIndices: number[] = [];
      // Filter the meikObjects based on tags
      const filteredMeikObjects = allMeiksData.filter((meik, index) => {
        const isFiltered = tags.every(
          (tag) =>
            meik.name.toLowerCase().includes(tag.toLowerCase()) ||
            meik.concentration.toLowerCase().includes(tag.toLowerCase()) ||
            meik.year.toLowerCase().includes(tag.toLowerCase()) ||
            meik.location.includes(tag.toLowerCase()) ||
            meik.email.toLowerCase().includes(tag.toLowerCase()) ||
            meik.tags.some((item) =>
              item.toLowerCase().includes(tag.toLowerCase())
            )
        );

        if (!isFiltered) {
          filteredIndices.push(index);
        }

        return isFiltered;
      });

      // Remove items from imagesBack based on filteredIndices
      const updatedImagesBack = imagesBack.filter(
        (_, index) => !filteredIndices.includes(index)
      );
      setImagesBack(updatedImagesBack);
      if (tags.length == 0) {
        setImagesBack(images);
      } else {
        if (inputValue.length == 0) {
          updateSearch(uid, String(tags));
        }
      }
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
          <div className="MeikBody">
            {meikObjects.map((meikObject, index) => (
              <div
                className="Rows"
                key={index}
                style={{ display: "inline-block" }}
              >
                {cardView(meikObject, imagesBack[index])}
              </div>
            ))}
          </div>
        </motion.div>
      </VerticalScroll>
    </div>
  );
};

export default Meiks;
