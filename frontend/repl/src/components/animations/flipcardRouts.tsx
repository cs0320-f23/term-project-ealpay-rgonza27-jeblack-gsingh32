import { ReactNode } from "react";
import { motion } from "framer-motion";

interface FlipCardProps {
  children: ReactNode;
}

const FlipCard: React.FC<FlipCardProps> = ({ children }) => {
  return (
    <motion.div
      style={{
        perspective: "1000px",
      }}
    >
      <motion.div
        initial={{ rotateY: -90 }}
        animate={{ rotateY: 0 }}
        exit={{ rotateY: 90 }}
        transition={{ duration: 0.5 }}
        style={{
          transformStyle: "preserve-3d",
          transformOrigin: "center",
        }}
      >
        {children}
      </motion.div>
    </motion.div>
  );
};

export default FlipCard;
