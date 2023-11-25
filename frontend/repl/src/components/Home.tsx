import React, { useEffect, useState } from "react";
import { getAuth, signOut } from "firebase/auth";
import { useNavigate } from "react-router-dom";

export interface IHomeProps {}

const HomePage: React.FunctionComponent<IHomeProps> = (props) => {
  const auth = getAuth();
  return (
    <div>
      <p>Home Page</p>
      <button onClick={() => signOut(auth)}>Sign Out</button>
    </div>
  );
};

export default HomePage;
