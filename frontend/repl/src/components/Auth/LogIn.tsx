import React, { useState } from "react";
import {
  getAuth,
  GoogleAuthProvider,
  signInWithEmailAndPassword,
  signInWithPopup,
} from "firebase/auth";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import FlipCard from "../animations/flipcardRouts";
import "../../styles/LoginStyle.css";

export interface ILoginPageProps {}

const LoginPage: React.FunctionComponent<ILoginPageProps> = (props) => {
  const auth = getAuth();
  const navigate = useNavigate();
  const [authing, setAuthing] = useState(false);
  const [{ email, password }, setCredentials] = useState({
    email: "",
    password: "",
  });

  const signInWithGoogle = async () => {
    setAuthing(true);

    try {
      const response = await signInWithPopup(auth, new GoogleAuthProvider());
      const userEmail = response.user.email || "";

      // Check if the email ends with the allowed domain
      if (userEmail.endsWith("@brown.edu")) {
        console.log(response.user.uid);
        navigate("/");
      } else {
        // User is not allowed, sign them out and show a message
        await auth.signOut();
        console.log("User not allowed. Signed out.");
      }
    } catch (error) {
      console.log(error);
      setAuthing(false);
    }
  };

  const logIn = async () => {
    setAuthing(true);

    signInWithEmailAndPassword(auth, email, password)
      .then((response) => {
        console.log(response.user.uid);
        navigate("/");
      })
      .catch((error) => {
        console.log(error);
        setAuthing(false);
      });
  };

  return (
    <FlipCard>
      <motion.div key="login-page" className="login-container">
        <h1>Login Page</h1>
        <form>
          <div className="input-field-wrapper">
            <label htmlFor="email">Email</label>
            <input
              className="input-field"
              placeholder="Email"
              value={email}
              onChange={(event) =>
                setCredentials({ email: event.target.value, password })
              }
            />
          </div>
          <div className="input-field-wrapper">
            <label htmlFor="password">Password</label>
            <input
              className="input-field"
              placeholder="Password"
              type="password"
              value={password}
              onChange={(event) =>
                setCredentials({ email, password: event.target.value })
              }
            />
          </div>
        </form>
        <button
          className="login-button"
          onClick={() => logIn()}
          disabled={authing}
        >
          Log In
        </button>
        <button
          className="google-login-button"
          onClick={() => signInWithGoogle()}
          disabled={authing}
        >
          Sign in with Google
        </button>
        <button
          className="register-button"
          onClick={() => navigate("/register")}
          disabled={authing}
        >
          Don't have an account? Register
        </button>
      </motion.div>
    </FlipCard>
  );
};

export default LoginPage;
