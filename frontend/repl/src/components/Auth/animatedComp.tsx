import React from "react";
import {
  BrowserRouter as Router,
  Routes,
  Route,
  useLocation,
} from "react-router-dom";
import HomePage from "../HomePage/Home";
import LogInPage from "./LogIn";
import AuthRoute from "./AuthRoute";
import RegisterPage from "./Register";
import { AnimatePresence } from "framer-motion";
import Meiks from "../Routes/Meiks";
import Profile from "../Routes/Profile";

function AnimatedRouts() {
  const location = useLocation();

  return (
    <AnimatePresence mode="wait">
      <Routes location={location} key={location.pathname}>
        <Route
          path="/"
          element={
            <AuthRoute key="home">
              <HomePage key="home" />
            </AuthRoute>
          }
        />
        <Route path="/login" element={<LogInPage key="login" />} />
        <Route path="/register" element={<RegisterPage key="register" />} />
        <Route
          path="/Meiks"
          element={
            <AuthRoute key="meiks">
              <Meiks key="meiks" />
            </AuthRoute>
          }
        />
        <Route
          path="/Profile"
          element={
            <AuthRoute key="profile">
              <Profile key="profile" />
            </AuthRoute>
          }
        />
      </Routes>
    </AnimatePresence>
  );
}
export default AnimatedRouts;
