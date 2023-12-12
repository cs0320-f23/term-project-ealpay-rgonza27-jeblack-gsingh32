import { HorizontalScroll } from "../Helpers/ScrollComponents";
import "../../styles/cardView.css";

interface Meik {
  concentration: string;
  email: string;
  id: string;
  imageURL: string;
  location: string;
  name: string;
  tags: string[];
  text: string;
  year: string;
}
function cardView(props: Meik, image: HTMLImageElement | null) {
  const imageHtml = image ? image.outerHTML : "";
  return (
    <div className="card">
      <div
        className="upper-half"
        dangerouslySetInnerHTML={{ __html: imageHtml }}
      />
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
            {Array.isArray(props.tags) ? (
              props.tags.map((tag, index) => <span key={index}>{tag}</span>)
            ) : (
              <span>{props.tags}</span>
            )}
          </div>
        </HorizontalScroll>
      </div>
    </div>
  );
}

export default cardView;
