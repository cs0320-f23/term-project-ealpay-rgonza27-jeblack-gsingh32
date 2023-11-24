import React, { useEffect, useState } from "react";
import { getAuth, onAuthStateChanged } from "firebase/auth";
import { useNavigate } from "react-router-dom";

export interface ILogInProps {}

const LogInPage: React.FunctionComponent<ILogInProps> = (props) => {
  return (
    <div>
      <p>LogIn Page</p>
    </div>
  );
};

export default LogInPage;
