import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  useLocation,
} from "react-router-dom";
import HomePage from "../Home";
import LogInPage from "./LogIn";
import AuthRoute from "./AuthRoute";
import RegisterPage from "./Register";
import { AnimatePresence } from "framer-motion";

function AnimatedRouts() {
  const location = useLocation();

  return (
    <AnimatePresence mode="wait">
      <Routes location={location} key={location.pathname}>
        <Route
          path="/"
          element={
            <AuthRoute key="home">
              <HomePage />
            </AuthRoute>
          }
        />
        <Route path="/login" element={<LogInPage key="login" />} />
        <Route path="/register" element={<RegisterPage key="register" />} />
      </Routes>
    </AnimatePresence>
  );
}
export default AnimatedRouts;
