import React from "react";
import { useNavigate } from "react-router-dom";
import "../../styles/Header.css";
const Header = () => {
  const navigate = useNavigate();
  return (
    <div className="header">
      <div className="header-inner">
        <div className="logo">MeetYourMeiks</div>
        <nav className="nav">
          <li onClick={() => navigate("/Meiks")}>
            <a>Meiks!</a>
          </li>
          <li onClick={() => navigate("/Profile")}>
            <a>Profile</a>
          </li>
          <li onClick={() => navigate("/")}>
            <a>About</a>
          </li>
          <li onClick={() => navigate("/why")}>
            <a>Why join us?</a>
          </li>
        </nav>
        <div className="contact" onClick={() => navigate("/contact")}>
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
