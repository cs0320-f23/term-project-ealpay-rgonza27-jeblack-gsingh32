import React from "react";
import "../../styles/AboutUs.css";

function AboutUsPage() {
  return (
    <div className="about-us-main-div">
      <div className="header-div">
        <h1 className="header-div-h1">About Us</h1>
        <p className="header-div-p">Welcome to the Meik World!</p>
      </div>
      <div className="container">
        <section className="about">
          <div className="about-picture">
            <img className="picture" src="../../images/meikDay.jpg"></img>
          </div>
          <div className="about-content">
            <h2 className="about-content-h2">The Meik Program:</h2>
            <p data-testid="about-us-test" className="about-content-p">
              With over 350 peer advisors serving first-year students annually,
              the Meiklejohn Peer Advising Program is Brown's largest student
              organization. Meiklejohn Peer Advisors are paired with a faculty
              advising partner and work with a small group of first-year
              students for the duration of the academic year. Meiklejohns advise
              students on course selection, study habits, housing, co-curricular
              and research opportunities, navigating the Brown campus and
              Providence, social issues, and more. Meiklejohn peer advisors
              offer a student perspective on the University experience while
              engendering an informal, open dialogue that proves indispensable
              to first-year students.
            </p>
            <a href="" className="read-more">
              Read More
            </a>
          </div>
        </section>
      </div>
    </div>
  );
}

export default AboutUsPage;
