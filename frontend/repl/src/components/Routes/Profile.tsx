import React, { useState } from "react";
import { motion } from "framer-motion";
import Header from "../HomePage/Header";
import cardView from "../Search/cardView";

interface IProfileProps {}

const Profile: React.FunctionComponent<IProfileProps> = (props) => {
  const [username, setUsername] = useState("Name");
  const [bio, setBio] = useState("Concentration");
  const [location, setLocation] = useState("example,RI");

  return (
    <div>
      <Header />
      <motion.div
        initial={{ opacity: 0, y: -400 }}
        animate={{ opacity: 1, y: 0 }}
        exit={{ opacity: 0, y: 400 }}
        transition={{ duration: 0.3 }}
        className="profile-container"
      >
        <div className="profile-header">
          <h1>{username}</h1>
          <p>{bio}</p>
          <p>{location}</p>
        </div>
        <div className="profile-content">
          <h2>Edit Profile</h2>
          <label htmlFor="username">Username:</label>
          <input
            type="text"
            id="username"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
          <label htmlFor="bio">Concentratino:</label>
          <textarea
            id="bio"
            value={bio}
            onChange={(e) => setBio(e.target.value)}
          />
          <label htmlFor="location">Location:</label>
          <input
            type="text"
            id="location"
            value={location}
            onChange={(e) => setLocation(e.target.value)}
          />
          <button>Save Changes</button>
        </div>
        <div>{cardView()}</div>
      </motion.div>
    </div>
  );
};

export default Profile;
