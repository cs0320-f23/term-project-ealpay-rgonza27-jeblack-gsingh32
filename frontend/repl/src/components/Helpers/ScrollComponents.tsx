import React from "react";
import "../../styles/scrollComponents.css";

interface HorizontalScrollProps {
  children: React.ReactNode;
}

const HorizontalScroll: React.FC<HorizontalScrollProps> = ({ children }) => {
  return (
    <div className="horizontal-scroll-container">
      <div className="horizontal-scroll-content">{children}</div>
    </div>
  );
};

export default HorizontalScroll;
