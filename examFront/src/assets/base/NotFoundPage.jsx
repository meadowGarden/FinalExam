import { Link } from "react-router-dom";
import "./NotFoundPage.css";

const NotFoundPage = () => {
  return (
    <>
      <div className="containerNotFound">
        <div>
          <div className="backgroundForPage">
            <h1>page was not found</h1>
          </div>
          <p>
            the page you want to reach does not exists, or was moved somewhere
            else
          </p>
          <Link to="/">return to homepage</Link>
        </div>
        <div>
          <img
            className="picEmpty"
            src="https://images.unsplash.com/photo-1514908182826-bef1b9513694?q=80&w=2070&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            alt="f"
          />
        </div>
      </div>
    </>
  );
};

export default NotFoundPage;
