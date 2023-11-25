import React, { useState } from "react";
import { getAuth, createUserWithEmailAndPassword } from "firebase/auth";
import { useNavigate } from "react-router-dom";
import { motion } from "framer-motion";
import FlipCard from "../animations/flipcardRouts";
import "../../styles/RegisterPage.css"; // Import your external CSS file if needed

export interface IRegisterPageProps {}

const RegisterPage: React.FunctionComponent<IRegisterPageProps> = (props) => {
  const auth = getAuth();
  const navigate = useNavigate();
  const [authing, setAuthing] = useState(false);
  const [{ email, password }, setCredentials] = useState({
    email: "",
    password: "",
  });

  const register = async () => {
    setAuthing(true);

    createUserWithEmailAndPassword(auth, email, password)
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
      <motion.div key="register-page" className="register-container">
        <h1>Register Page</h1>
        <form>
          <label htmlFor="email">Email</label>
          <input
            className="input-field"
            placeholder="Email"
            value={email}
            onChange={(event) =>
              setCredentials({ email: event.target.value, password })
            }
          />
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
        </form>
        <button
          className="register-button"
          onClick={() => register()}
          disabled={authing}
        >
          Register
        </button>
        <button
          className="back-button"
          onClick={() => navigate("/login")}
          disabled={authing}
        >
          Already have an account? Login
        </button>
      </motion.div>
    </FlipCard>
  );
};

export default RegisterPage;
