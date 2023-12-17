import React, { useEffect, useState } from "react";
import { useRef } from "react";
import { getAuth, signOut } from "firebase/auth";
import { motion, AnimatePresence } from "framer-motion";
import Banner from "./Banner";
import Loader from "./Loader";
import Header from "./Header";
import { VerticalScroll } from "../Helpers/ScrollComponents";
import "../../styles/AddedPages.css";
import Form from "./contactForm";
import AboutUsPage from "./AboutUs";
import JoinUsPage from "./JoinUs";
import ScrollToTop from "./scrollToTop";

export interface IHomeProps {}
export const scrollToSection = (
  elementRef: React.RefObject<HTMLDivElement>,
  containerRef: React.RefObject<HTMLDivElement>
) => {
  if (elementRef.current && containerRef.current) {
    console.log("Scrolling to:", containerRef.current);
    containerRef.current.scrollTo({
      top: elementRef.current.offsetTop - 125,
      behavior: "smooth",
    });
  }
};

interface FormData {
  name: string;
  email: string;
  message: string;
}

function handleSubmit(data: FormData): void {
  console.log(data);
}

const HomePage: React.FunctionComponent<IHomeProps> = (props) => {
  const [loading, setLoading] = useState(true);
  const auth = getAuth();
  const about = useRef<HTMLDivElement>(null);
  const join = useRef<HTMLDivElement>(null);
  const contact = useRef<HTMLDivElement>(null);
  const scrollAAARef = useRef<HTMLDivElement>(null);

  const handleLinkClickContacts = () => {
    console.log("scrolling to contact");
    scrollToSection(contact, scrollAAARef);
  };
  const handleLinkClickAbout = () => {
    console.log("scrolling to contact");
    scrollToSection(about, scrollAAARef);
  };
  const handleLinkClickJoin = () => {
    console.log("scrolling to contact");
    scrollToSection(join, scrollAAARef);
  };

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
          <Header
            onLinkClickContact={handleLinkClickContacts}
            onLinkClickAbout={handleLinkClickAbout}
            onLinkClickJoin={handleLinkClickJoin}
          />
          <motion.div
            className="transition-body"
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: 400 }}
            transition={{ duration: 0.2 }}
          >
            <VerticalScroll>
              <div className="scrollAAA" ref={scrollAAARef}>
                <Banner />
                <ScrollToTop elementRef={scrollAAARef} />
                {!loading && (
                  <motion.div className="transition-image final">
                    <motion.img
                      src={"/images/image-2.jpg"}
                      layoutId="main-image-1"
                      transition={{ duration: 0.5 }}
                    />
                  </motion.div>
                )}
                <div ref={about} className="about-div">
                  <AboutUsPage />
                </div>
                <div ref={join} className="join-div">
                  <JoinUsPage />
                </div>
                <div ref={contact} className="contact-div">
                  <h3 className="ContactUsPage">Contact us!</h3>
                  <Form onSubmit={handleSubmit} />
                </div>

                <button
                  className="SignOut"
                  onClick={() => {
                    signOut(auth);
                  }}
                >
                  Sign Out
                </button>
              </div>
            </VerticalScroll>
          </motion.div>
        </div>
      )}
    </>
  );
};

export default HomePage;
