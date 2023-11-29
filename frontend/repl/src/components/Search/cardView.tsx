import { HorizontalScroll } from "../Helpers/ScrollComponents";
import "../../styles/cardView.css";

export interface ICardProps {
  name: String;
  location: String;
  year: String;
  concentration: String;
  email: String;
}

function cardView(props: ICardProps) {
  return (
    <div className="card">
      <div className="upper-half">
        <img src="" alt="User" />
      </div>
      <div className="lower-half">
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
            <span>activity a </span>
            <span>activity b</span>
            <span>activity c</span>
            <span>activity a </span>
            <span>activity b</span>
            <span>activity c</span>
          </div>
        </HorizontalScroll>
      </div>
    </div>
  );
}

export default cardView;
