import React, { PropsWithChildren, useEffect, useState } from "react";
import { getAuth, onAuthStateChanged } from "firebase/auth";
import { useNavigate } from "react-router-dom";

export interface IAuthReoutProps extends PropsWithChildren {}

const AuthRoute: React.FunctionComponent<IAuthReoutProps> = (props) => {
  const { children } = props;
  const auth = getAuth();
  const navigate = useNavigate();
  const [loading, setLoading] = useState(false);

  useEffect(() => {
    AuthCheck();
  }, [auth]);
  const AuthCheck = onAuthStateChanged(auth, (user) => {
    if (user) {
      setLoading(false);
    } else {
      console.log("User doesn't exist");
      navigate("/login");
    }
  });

  if (loading) return <p>Loading ...</p>;
  return <>{children}</>;
};

export default AuthRoute;
