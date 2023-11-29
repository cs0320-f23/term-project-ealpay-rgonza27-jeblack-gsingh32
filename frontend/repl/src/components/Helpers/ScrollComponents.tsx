import React from "react";
import "../../styles/scrollComponents.css";

interface HorizontalScrollProps {
  children: React.ReactNode;
}

export const HorizontalScroll: React.FC<HorizontalScrollProps> = ({
  children,
}) => {
  return (
    <div className="horizontal-scroll-container">
      <div className="horizontal-scroll-content">{children}</div>
    </div>
  );
};

interface VerticalScrollProps {
  children: React.ReactNode;
}

export const VerticalScroll: React.FC<VerticalScrollProps> = ({ children }) => {
  return (
    <div className="vertical-scroll-container">
      <div className="vertical-scroll-content">{children}</div>
    </div>
  );
};
