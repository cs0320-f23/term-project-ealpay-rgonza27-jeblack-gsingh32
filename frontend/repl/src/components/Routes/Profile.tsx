import React, { useEffect, useState } from "react";
import { motion } from "framer-motion";
import Header from "../HomePage/Header";
import "../../styles/Profile.css"; // Import your profile-specific styles
import { concentrations } from "../Helpers/concentrations";
import { VerticalScroll } from "../Helpers/ScrollComponents";
import cardView from "../Search/cardView";
import { getAuth, onAuthStateChanged } from "firebase/auth";
import { singleMeik } from "./MeikHandler";

interface IProfileProps {}

const Profile: React.FunctionComponent<IProfileProps> = (props) => {
  const [username, setUsername] = useState("Name");
  const [concentration, setConcentration] = useState("Visual Arts");
  const [concentration2, setConcentration2] = useState("");
  const [concentration3, setConcentration3] = useState("");
  const [concentrationNum, setConNum] = useState(1);
  const [location, setLocation] = useState("example,RI");
  const [year, setYear] = useState("'26");

  useEffect(() => {
    const unsubscribe = onAuthStateChanged(getAuth(), (user) => {
      if (user) {
        const uid = user.uid;
        singleMeik(uid).then((data) => {
          setUsername(data.name);
          setLocation(data.location);
          setYear(data.year);
          const constList = data.concentration.split(" & ");
          setConcentration(constList[0]);
          if (constList[1]) {
            setConcentration2(" & " + constList[1]);
          }
          if (constList[2]) {
            setConcentration3(" & " + constList[2]);
          }
        });
      }
    });

    return () => unsubscribe(); // Cleanup the subscription when the component unmounts
  }, []);

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
            <div>
              {cardView({
                name: username,
                concentration: concentration + concentration2 + concentration3,
                email: "",
                year: year,
                location: location,
                id: "",
                imageURL: "",
                tags: [""],
                text: "",
              })}
            </div>

            <input
              type="text"
              id="username"
              value={username}
              onChange={(e) => setUsername(e.target.value)}
            />
            <input
              type="text"
              id="location"
              value={location}
              onChange={(e) => setLocation(e.target.value)}
            />
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
