import { RefObject, useEffect, useState } from "react";
import React from "react";
import "../../styles/scrollToTop.css";

interface ScrollToTopProps {
  elementRef: RefObject<HTMLDivElement>;
}

const ScrollToTop: React.FC<ScrollToTopProps> = ({ elementRef }) => {
  const [showScrollToTopButton, setShowScrollToTopButton] = useState(false);

  useEffect(() => {
    const handleScroll = () => {
      if (elementRef.current) {
        setShowScrollToTopButton(elementRef.current.scrollTop > 100);
      }
    };
    if (elementRef.current) {
      elementRef.current.addEventListener("scroll", handleScroll);

      return () => {
        window.removeEventListener("scroll", handleScroll);
      };
    }
  }, [elementRef]);

  const scrollTop = () => {
    if (elementRef.current != null) {
      elementRef.current.scrollTo({
        top: 0,
        behavior: "smooth",
      });
    }
  };
  return (
    <div>
      {showScrollToTopButton && (
        <button className="top-button" onClick={scrollTop}>
          Top
        </button>
      )}
    </div>
  );
};

export default ScrollToTop;
