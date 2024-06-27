import { Route, Routes } from "react-router-dom";
import "./App.css";
import MainPage from "./assets/base/MainPage";
import NotFoundPage from "./assets/base/NotFoundPage";
import "bootstrap/dist/css/bootstrap.min.css";
import NavBar from "./assets/base/NavBar";
import CategoriesPage from "./assets/categories/CategoriesPage";
import AdvertDataPage from "./assets/advertisements/AdvertDataPage";

function App() {
  return (
    <>
      <NavBar />
      <Routes>
        <Route path="/" element={<MainPage />} />
        <Route path="/advertisements" element={<AdvertDataPage />} />
        <Route path="/categories" element={<CategoriesPage />} />
        <Route path="/*" element={<NotFoundPage />} />
      </Routes>
    </>
  );
}

export default App;
