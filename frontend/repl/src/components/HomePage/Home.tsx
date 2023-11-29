import React, { useEffect, useState } from "react";
import { getAuth, signOut } from "firebase/auth";
import { motion, AnimatePresence } from "framer-motion";
import Banner from "./Banner";
import Loader from "./Loader";
import Header from "./Header";
import { VerticalScroll } from "../Helpers/ScrollComponents";

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
    <>
      {loading ? (
        <motion.div key={String(loading)}>
          <Loader setLoading={setLoading} />
        </motion.div>
      ) : (
        <div>
          <Header />
          <motion.div
            className="transition-body"
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: 400 }}
            transition={{ duration: 0.3 }}
          >
            <VerticalScroll>
              <Banner />
              {!loading && (
                <motion.div className="transition-image final">
                  <motion.img
                    src={"/images/image-2.jpg"}
                    layoutId="main-image-1"
                    transition={{ duration: 1 }}
                  />
                </motion.div>
              )}
              <button
                className="SignOut"
                onClick={() => {
                  signOut(auth);
                }}
              >
                Sign Out
              </button>
            </VerticalScroll>
          </motion.div>
        </div>
      )}
    </>
  );
};

export default HomePage;
