import HorizontalScroll from "../Helpers/ScrollComponents";
import "../../styles/cardView.css";
function cardView() {
  return (
    <div className="card">
      <div className="upper-half">
        <img src="" alt="User" />
      </div>
      <div className="lower-half">
        <div className="text-group">
          <span className="Name">Example Name</span>
          <span className="Location">Example Town, RI</span>
        </div>
        <div className="text-group">
          <span className="Concentration">Applied Example</span>
          <span className="Year">'26</span>
        </div>
        <div className="text-group">
          <span className="Email">example_example@brown.edu</span>
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
