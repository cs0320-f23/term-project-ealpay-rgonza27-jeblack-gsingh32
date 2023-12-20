import React from "react";
import "../../styles/JoinUs.css";

function JoinUsPage() {
  return (
    <div className="join-us-main-div">
      <div className="join-header-div">
        <h1 className="join-header-div-h1">Why Join Us?</h1>
      </div>
      <div className="container">
        <div className="row">
          <div className="team-item">
            <img src="../../images/join3.jpg" className="image"></img>
            <h3 className="team-item-h3">Personalized Academic Guidance</h3>
            <div data-testid="join-test" className="team-info">
              <p>
                The Meiklejohn Program provides personalized academic guidance
                through peer advising. Each Meiklejohn Peer Advisor is paired
                with a faculty advising partner and works closely with a small
                group of first-year students throughout the academic year. The
                idea is for first year to receive guidance both in and out of
                the classroom.{" "}
              </p>
            </div>
          </div>
          <div className="team-item">
            <img src="../../images/join2.jpg" className="image"></img>
            <h3 className="team-item-h3">
              Comprehensive Support Beyond Academics
            </h3>
            <div className="team-info">
              <p>
                Meiklejohn Peer Advisors offer holistic support beyond
                academics. They assist students in various aspects of university
                life, including advice on housing, co-curricular and research
                opportunities, campus navigation, and social issues. By sharing
                their own experiences, Meiklejohns help students integrate into
                the university community and make the most of their college
                experience.
              </p>
            </div>
          </div>
          <div className="team-item">
            <img src="../../images/join1.jpg" className="image"></img>
            <h3 className="team-item-h3">
              Building a Strong Community and Network
            </h3>
            <div className="team-info">
              <p>
                Joining the Meiklejohn Program provides an opportunity to become
                part of a vibrant and supportive community. Meiklejohn Peer
                Advisors foster an informal and open dialogue with first-year
                students, creating a sense of camaraderie. Through this program,
                students not only receive valuable advice but also have the
                chance to build lasting connections with peers, faculty, and
                other members of the university community.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default JoinUsPage;
