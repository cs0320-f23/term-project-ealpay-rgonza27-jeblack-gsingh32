import { HorizontalScroll } from "../Helpers/ScrollComponents";
import "../../styles/cardViewFirstYear.css";

interface FirstYear {
  concentration: string;
  email: string;
  id: string;
  location: string;
  name: string;
  tags: string[];
  text: string;
  year: string;
}
function cardViewFirstYear(props: FirstYear) {
  return (
    <div className="card">
      <div className="text-group">
        <span className="Name">{props.name}</span>
        <span className="Location">{props.location}</span>
      </div>
      <div className="text-group">
        <span className="Concentration">{props.concentration}</span>
        <span className="Year">{props.year}</span>
      </div>
      <div className="text-group">
        <span className="Email">{props.email}</span>
      </div>
      <HorizontalScroll>
        <div className="scroll-content">
          {Array.isArray(props.tags) ? (
            props.tags.map((tag, index) => <span key={index}>{tag}</span>)
          ) : (
            <span>{props.tags}</span>
          )}
        </div>
      </HorizontalScroll>
    </div>
  );
}

export default cardViewFirstYear;
