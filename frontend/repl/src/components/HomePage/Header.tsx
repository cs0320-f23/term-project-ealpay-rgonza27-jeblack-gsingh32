import React from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/Header.css";
import { scrollToSection } from "./Home";

interface HeaderProps {
  onLinkClickContact: () => void;
  onLinkClickAbout: () => void;
  onLinkClickJoin: () => void;
}

const Header: React.FunctionComponent<HeaderProps> = ({
  onLinkClickContact,
  onLinkClickAbout,
  onLinkClickJoin,
}) => {
  const navigate = useNavigate();
  return (
    <div className="header">
      <div className="header-inner">
        <div className="logo" onClick={() => navigate("/")}>
          MeetYourMeiks
        </div>
        <nav className="nav">
          <li onClick={() => navigate("/Meiks")}>
            <a>Meiks!</a>
          </li>
          <li onClick={() => navigate("/Profile")}>
            <a>Profile</a>
          </li>
          <li
            onClick={() => {
              onLinkClickAbout();
            }}
          >
            <a>About</a>
          </li>
          <li
            onClick={() => {
              onLinkClickJoin();
            }}
          >
            <a>Why join us?</a>
          </li>
        </nav>
        <div
          className="contact"
          onClick={() => {
            onLinkClickContact();
            console.log("hey");
          }}
        >
          <a>Contact us!</a>
        </div>
        <div className="nav-menu">
          <span></span>
          <span></span>
        </div>
      </div>
    </div>
  );
};

export default Header;
