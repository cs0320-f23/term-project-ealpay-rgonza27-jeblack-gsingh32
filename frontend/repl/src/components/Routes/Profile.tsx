import React, { useState } from "react";
import { motion } from "framer-motion";
import Header from "../HomePage/Header";
import "../../styles/Profile.css"; // Import your profile-specific styles
import { concentrations } from "../Helpers/concentrations";
import { VerticalScroll } from "../Helpers/ScrollComponents";

interface IProfileProps {}

const Profile: React.FunctionComponent<IProfileProps> = (props) => {
  const [username, setUsername] = useState("Name");
  const [concentration, setConcentration] = useState("Visual Arts");
  const [concentration2, setConcentration2] = useState("");
  const [concentration3, setConcentration3] = useState("");
  const [concentrationNum, setConNum] = useState(1);
  const [location, setLocation] = useState("example,RI");
  const [year, setYear] = useState("'26");

  function addCon() {
    if (concentrationNum === 2) {
      return (
        <div>
          <label htmlFor="Concentration2">Concentration 2:</label>
          <select
            id="Concentration2"
            value={concentration2.replace(" & ", "")}
            onChange={(e) => {
              if (e.target.value != "--") {
                setConcentration2(" & " + e.target.value);
              } else {
                setConcentration2("");
              }
            }}
          >
            {Object.values(concentrations).map((conc) => (
              <option key={conc} value={conc}>
                {conc}
              </option>
            ))}
          </select>
        </div>
      );
    } else if (concentrationNum === 3) {
      return (
        <>
          <div>
            <label htmlFor="Concentration2">Concentration 2:</label>
            <select
              id="Concentration2"
              value={concentration2.replace(" & ", "")}
              onChange={(e) => {
                if (e.target.value != "--") {
                  setConcentration2(" & " + e.target.value);
                } else {
                  setConcentration2("");
                }
              }}
            >
              {Object.values(concentrations).map((conc) => (
                <option key={conc} value={conc}>
                  {conc}
                </option>
              ))}
            </select>
          </div>
          <div>
            <label htmlFor="Concentration3">Concentration 3:</label>
            <select
              id="Concentration3"
              value={concentration3.replace(" & ", "")}
              onChange={(e) => {
                if (e.target.value != "--") {
                  setConcentration3(" & " + e.target.value);
                } else {
                  setConcentration3("");
                }
              }}
            >
              {Object.values(concentrations).map((conc) => (
                <option key={conc} value={conc}>
                  {conc}
                </option>
              ))}
            </select>
          </div>
        </>
      );
    }
    return null;
  }

  return (
    <div>
      <Header
        onLinkClick={function (): void {
          throw new Error("Function not implemented.");
        }}
      />
      <VerticalScroll>
        <motion.div
          initial={{ opacity: 0, y: -400 }}
          animate={{ opacity: 1, y: 0 }}
          exit={{ opacity: 0, y: 400 }}
          transition={{ duration: 0.2 }}
          className="profile-container"
        >
          <div className="profile-content">
            <span className="Title">Edit Your Profile!</span>
            {/* <div>{cardView(profileData)}</div> */}
            <label htmlFor="username">Name:</label>
            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <label htmlFor="location">Location:</label>
            <input
              type="text"
              id="location"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
            />
            <label htmlFor="Concentration">Concentration:</label>
            <select
              id="Concentration"
              value={concentration}
              onChange={(e) => {
                setConcentration(e.target.value as concentrations);
              }}
            >
              {Object.values(concentrations).map((conc) => (
                <option key={conc} value={conc}>
                  {conc}
                </option>
              ))}
            </select>
            {addCon()}
            <button
              onClick={() => {
                if (concentrationNum < 3) {
                  setConNum(concentrationNum + 1);
                }
              }}
            >
              Add Concentration
            </button>
            <label htmlFor="year">Year:</label>
            <select
              id="year"
              value={year}
              onChange={(e) => setYear(e.target.value)}
            >
              <option value="'27">'27</option>
              <option value="'26">'26</option>
              <option value="'25">'25</option>
              <option value="'24">'24</option>
            </select>
            <button>Save Changes</button>
          </div>
        </motion.div>
      </VerticalScroll>
    </div>
  );
};

export default Profile;
