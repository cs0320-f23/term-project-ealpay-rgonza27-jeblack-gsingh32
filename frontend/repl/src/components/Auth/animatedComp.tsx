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
  //   const location = useLocation();
  return (
    <AnimatePresence>
      <Routes>
        <Route
          path="/"
          element={
            <AuthRoute>
              <HomePage />
            </AuthRoute>
          }
        />
        <Route path="/login" element={<LogInPage />} />
        <Route path="/register" element={<RegisterPage />} />
      </Routes>
    </AnimatePresence>
  );
}
export default AnimatedRouts;
