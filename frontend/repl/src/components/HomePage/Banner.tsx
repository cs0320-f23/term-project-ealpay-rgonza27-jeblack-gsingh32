import React, { useState, useEffect } from "react";
import "../../styles/Banner.css";
import { motion, Variants } from "framer-motion";

interface AnimatedLettersProps {
  title: string;
}

const banner: Variants = {
  animate: {
    transition: {
      delayChildren: 0.1,
      staggerChildren: 0.1,
    },
  },
};

const AnimatedLetters: React.FC<AnimatedLettersProps> = ({ title }) => (
  <span className="row-title">
    {[...title].map((letter, index) => (
      <motion.span
        key={index}
        className="row-letter"
        style={{ display: "inline-block" }}
        initial={{ opacity: 0, y: 400 }} // Initial state with opacity and a small negative y translation
        animate={{
          opacity: 1,
          y: 0, // Ending state with opacity 1 and y translation 0
          transition: { duration: 1, delay: index * 0.1 },
        }}
      >
        {letter}
      </motion.span>
    ))}
  </span>
);

interface BannerRowTopProps {
  title: string;
}

const BannerRowTop: React.FC<BannerRowTopProps> = ({ title }) => (
  <div className={"banner-row"}>
    <div className="row-col">
      <AnimatedLetters title={title} />
    </div>
    <motion.div
      initial={{ opacity: 0, y: 80 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{
        ease: "easeInOut",
        duration: 1,
        delay: 0.4,
      }}
      className="row-col"
    >
      <span className="row-message">
        Brown's PeerAdvisoring Program to guide and aid first-year students to
        transition into our community
      </span>
    </motion.div>
  </div>
);

interface BannerRowBottomProps {
  title: string;
}

const BannerRowBottom: React.FC<BannerRowBottomProps> = ({ title }) => (
  <div className={"banner-row center"}>
    <motion.div
      className="scroll"
      initial={{ opacity: 0, y: 80 }}
      animate={{ opacity: 1, y: 0 }}
      transition={{
        ease: "easeInOut",
        duration: 1,
        delay: 0.4,
      }}
    >
      <span>scroll</span>
      <span>down!</span>
    </motion.div>
    <AnimatedLetters title={title} />
  </div>
);

interface BannerRowCenterProps {
  title: string;
  playMarquee: boolean;
}

const BannerRowCenter: React.FC<BannerRowCenterProps> = ({
  title,
  playMarquee,
}) => (
  <div className={`banner-row marquee  ${playMarquee && "animate"}`}>
    <div className="marquee__inner">
      <AnimatedLetters title={title} />
      <AnimatedLetters title={title} />
      <AnimatedLetters title={title} />
      <AnimatedLetters title={title} />
    </div>
  </div>
);

const Banner: React.FC = () => {
  const [playMarquee, setPlayMarquee] = useState(false);
  useEffect(() => {
    setTimeout(() => {
      setPlayMarquee(true);
    }, 2000);
  }, []);
  return (
    <motion.div className="banner" variants={banner}>
      <BannerRowTop title={"The"} />
      <BannerRowCenter title={"Meiklejohn"} playMarquee={playMarquee} />
      <BannerRowBottom title={"Program"} />
    </motion.div>
  );
};

export default Banner;
