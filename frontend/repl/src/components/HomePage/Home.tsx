import React, { useEffect, useState } from "react";
import { getAuth, signOut } from "firebase/auth";
import { motion, AnimatePresence, AnimateSharedLayout } from "framer-motion";
import Banner from "./Banner";
import Loader from "./Loader";
import Header from "./Header";
export interface IHomeProps {}

const HomePage: React.FunctionComponent<IHomeProps> = (props) => {
  const [loading, setLoading] = useState(true);
  const auth = getAuth();
  useEffect(() => {
    const bodyElement = document.querySelector("body");
    if (bodyElement) {
      loading
        ? bodyElement.classList.add("loading")
        : bodyElement.classList.remove("loading");
    }
  }, [loading]);

  return (
    <AnimatePresence>
      {loading ? (
        <motion.div key="loader">
          <Loader setLoading={setLoading} />
        </motion.div>
      ) : (
        <>
          <Header />
          <Banner />
          {!loading && (
            <div className="transition-image final">
              <motion.img
                src={"/images/image-2.jpg"}
                layoutId="main-image-1"
                transition={{ duration: 1.6 }}
              />
              <button onClick={() => signOut(auth)}>Sign Out</button>
              <section id="/about"></section>
            </div>
          )}
        </>
      )}
    </AnimatePresence>
  );
};

export default HomePage;
