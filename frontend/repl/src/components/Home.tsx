import React, { useEffect, useState } from "react";
import { getAuth, onAuthStateChanged } from "firebase/auth";
import { useNavigate } from "react-router-dom";

export interface IHomeProps {}

const HomePage: React.FunctionComponent<IHomeProps> = (props) => {
  return (
    <div>
      <p>Home Page</p>
    </div>
  );
};

export default HomePage;
