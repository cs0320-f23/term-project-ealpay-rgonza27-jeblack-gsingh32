import React, { PropsWithChildren, useEffect, useState } from "react";
import { getAuth, onAuthStateChanged } from "firebase/auth";
import { useNavigate } from "react-router-dom";

export interface IAuthRouteProps extends PropsWithChildren {}

const NonMeikRoute: React.FunctionComponent<IAuthRouteProps> = (props) => {
  const { children } = props;
  const auth = getAuth();
  const navigate = useNavigate();

  useEffect(() => {
    const AuthCheck = onAuthStateChanged(auth, (user) => {
      if (user?.email == "ADD THE WHOLE MEIK LIST HERE") {
        navigate("/UserProfile");
      }
    });

    return () => AuthCheck();
  }, [auth]);

  return <>{children}</>;
};

export default NonMeikRoute;
