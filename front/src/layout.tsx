import { Route, Routes } from "react-router";
// import App from "./App";
// import Navbar from "./components/Navbar";
// import { Login, Register, User, Users, Rents } from "./pages";
import Resources from "@/pages/Resources.tsx";
import Warehouses from "@/pages/Warehouses.tsx";
import CreateWarehouse from "@/pages/CreateWarehouse.tsx";
import CreateResource from "@/pages/CreateResource.tsx";

const Layout = () => {

    return (
        <>
            <Routes>
                <Route path="/resources" element={<Resources />} />
                <Route path="/warehouses" element={<Warehouses />} />
                <Route path="/resources/create" element={<CreateResource />} />
                <Route path="/warehouses/create" element={<CreateWarehouse />} />
            </Routes>
        </>
    );
};

export default Layout;