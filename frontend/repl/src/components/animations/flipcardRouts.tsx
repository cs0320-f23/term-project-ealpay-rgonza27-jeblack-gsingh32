import { ReactNode } from "react";
import { motion } from "framer-motion";
interface FlipContainerProps {
  children: ReactNode;
}
const FlipContainer: React.FC<FlipContainerProps> = ({ children }) => {
  return (
    <motion.div
      style={{
        perspective: "1000px",
        width: "100%",
        height: "100%",
      }}
    >
      {children}
    </motion.div>
  );
};

export default FlipContainer;
