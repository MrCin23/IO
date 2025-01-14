import React, { ReactNode } from "react";
import Notifications from "./notifications";
import Announcements from "./announcements";
import { useAccount } from "../../contexts/uwierzytelnianie/AccountContext";



interface AlertOverlayProps {
    children: React.ReactNode;
  }

const AlertOverlay = ({children}:AlertOverlayProps) => {
    const {account} = useAccount()
    return (
        <React.Fragment>
            {account && <div className="fixed top-4 right-4 z-50 flex gap-x-4">
                <Notifications/>
                <Announcements/>
            </div>}
            <div>{children}</div>
        </React.Fragment>
    );
}

export default AlertOverlay;