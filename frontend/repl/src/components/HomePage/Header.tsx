import React from "react";
import "../../styles/Header.css";
const Header = () => {
  return (
    <div className="header">
      <div className="header-inner">
        <div className="logo">MeetYourMeiks</div>
        <nav className="nav">
          <li>
            <a href="/Meiks">Meiks!</a>
          </li>
          <li>
            <a href="/Profile">Profile</a>
          </li>
          <li>
            <a href="#about">About</a>
          </li>
          <li>
            <a href="/why">Why join us?</a>
          </li>
        </nav>
        <div className="contact">
          <a href="/contact">Contact us!</a>
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
