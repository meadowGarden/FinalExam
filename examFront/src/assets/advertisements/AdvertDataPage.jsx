import { useState } from "react";
import "../base/BaseStyle.css";
import AdvertDataCard from "./AdvertDataCard";
import AdvertListElement from "./AdvertListElement";

const AdvertDataPage = () => {
  const [ads, setAds] = useState([]);

  const adsToDisplay = ads.map((ad) => {
    return <AdvertListElement key={ad.id} data={ad} />;
  });

  return (
    <>
      <div className="pageContainer">
        <div>
          <AdvertDataCard setAds={setAds} />
        </div>
        <div>{adsToDisplay}</div>
      </div>
    </>
  );
};

export default AdvertDataPage;
