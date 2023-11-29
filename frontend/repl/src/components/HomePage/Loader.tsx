import React, { useEffect } from "react";
import Image from "./Image";
import "../../styles/Loader.css";
import { motion, Variants } from "framer-motion";

interface LoaderProps {
  setLoading: React.Dispatch<React.SetStateAction<boolean>>;
}

const container: Variants = {
  show: {
    transition: {
      staggerChildren: 0.2,
    },
  },
};

const item: Variants = {
  hidden: {
    opacity: 0,
    y: 200,
  },
  show: {
    opacity: 1,
    y: 0,
    transition: {
      duration: 0.4,
    },
  },
  exit: {
    opacity: 0,
    y: -200,
    transition: { ease: "easeInOut", duration: 0.2 },
  },
};

const itemMain: Variants = {
  hidden: {
    opacity: 0,
    y: 200,
  },
  show: {
    opacity: 1,
    y: 0,
    transition: {
      duration: 1,
    },
  },
};

const Loader: React.FC<LoaderProps> = ({ setLoading }) => {
  const ranNums = addNumbers();
  const a = ranNums.pop();
  const b = ranNums.pop();
  const c = ranNums.pop();
  const d = ranNums.pop();
  return (
    <div className="loader">
      <motion.div
        className="loader-inner"
        variants={container}
        initial="hidden"
        animate="show"
        exit="exit"
        onAnimationComplete={() => {
          setLoading(false);
        }}
      >
        <motion.div className="transition-image" variants={itemMain}>
          <motion.img
            src={`/images/image-2.jpg`}
            alt="random alt"
            layoutId="main-image-1"
            transition={{ duration: 0.5 }}
          />
        </motion.div>
        <ImageBlock id={"image-" + a} variants={item} cssId="image-1" />
        <ImageBlock id={"image-" + b} variants={item} cssId="image-3" />
        <ImageBlock id={"image-" + c} variants={item} cssId="image-4" />
        <ImageBlock id={"image-" + d} variants={item} cssId="image-5" />
      </motion.div>
    </div>
  );
};

interface ImageBlockProps {
  id: string;
  cssId: string;
  variants: Variants;
}

function addNumbers(): number[] {
  const numb = [1, 3, 4, 5];
  const finalList = [];
  for (let i: number = 0; i < 4; i++) {
    const a = numb.splice(Math.floor(Math.random() * (4 - i)), 1).pop();
    if (a) {
      finalList.push(a);
    }
  }
  return finalList;
}

export const ImageBlock: React.FC<ImageBlockProps> = ({
  id,
  variants,
  cssId,
}) => {
  return (
    <motion.div className={`image-block ${cssId}`} variants={variants}>
      <Image
        src={`/images/${id}.webp`}
        fallback={`/images/${id}.jpg`}
        alt={id}
      />
    </motion.div>
  );
};

export default Loader;
