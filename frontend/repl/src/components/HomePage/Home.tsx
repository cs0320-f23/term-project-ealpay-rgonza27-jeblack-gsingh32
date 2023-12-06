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

export interface IHomeProps {}
export const scrollToSection = (
  elementRef: React.RefObject<HTMLDivElement>
) => {
  window.scrollTo({
    top: elementRef.current?.offsetTop || 0,
    behavior: "smooth",
  });
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
  const handleLinkClick = () => {
    console.log("scrolling to contact");
    scrollToSection(contact);
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
          <Header onLinkClick={handleLinkClick} />
          <motion.div
            className="transition-body"
            animate={{ opacity: 1, y: 0 }}
            exit={{ opacity: 0, y: 400 }}
            transition={{ duration: 0.2 }}
          >
            <VerticalScroll>
              <Banner />
              {!loading && (
                <motion.div className="transition-image final">
                  <motion.img
                    src={"/images/image-2.jpg"}
                    layoutId="main-image-1"
                    transition={{ duration: 0.5 }}
                  />
                </motion.div>
              )}
              <div className="about-div">
                <h3 className="AboutPage">About</h3>
                <li onClick={() => scrollToSection(contact)}>gotoContact</li>
              </div>
              <div className="join-div">
                <h3 className="WhyJoinUsPage">Why join us?</h3>
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
            </VerticalScroll>
          </motion.div>
        </div>
      )}
    </>
  );
};

export default HomePage;
