import { ReactNode } from "react";
import { motion } from "framer-motion";

interface FallDownProps {
  children: ReactNode;
}

const FallDown: React.FC<FallDownProps> = ({ children }) => {
  return (
    <motion.div
      style={{
        perspective: "1000px",
      }}
    >
      <motion.div
        exit={{ y: 200 }}
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

export default FallDown;
