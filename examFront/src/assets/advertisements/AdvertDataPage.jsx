import { useState } from "react";
import "../base/BaseStyle.css";
import AdvertDataCard from "./AdvertDataCard";
import AdvertListElement from "./AdvertListElement";
import ButtonBasic from "../elements/ButtonBasic";
import ModalBasic from "../elements/ModalBasic";
import AddAdvertCard from "./AddAdvertCard";
import useUserStore from "../store/userStore";

const AdvertDataPage = () => {
  const { user } = useUserStore((state) => state);

  const [ads, setAds] = useState([]);
  const [addAdvertModalVisibility, setAddAdvertModalVisibility] =
    useState(false);

  const adsToDisplay = ads.map((ad) => {
    return <AdvertListElement key={ad.id} data={ad} />;
  });

  const showModal = () => {
    setAddAdvertModalVisibility(true);
  };

  const closeModal = () => {
    setAddAdvertModalVisibility(false);
  };

  return (
    <>
      <div className="pageContainer">
        <div>
          <AdvertDataCard setAds={setAds} />
        </div>
        <div>{adsToDisplay}</div>

        {user.userRole === "ADMINISTRATOR" && (
          <ButtonBasic clickHandle={showModal} text="create advertisement" />
        )}

        <ModalBasic
          isModalVisible={addAdvertModalVisibility}
          closeModal={closeModal}
          title={"add new advertisement"}
        >
          <AddAdvertCard />
        </ModalBasic>
      </div>
    </>
  );
};

export default AdvertDataPage;
