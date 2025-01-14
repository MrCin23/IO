import React, {useState } from "react";
import Notifications from "./notifications";
import Announcements from "./announcements";
import { useAccount } from "../../contexts/uwierzytelnianie/AccountContext";
import useAnnouncments from "./hooks/use-announcement";
import useNotifications from "./hooks/use-notifications";
import Notification from "../../models/powiadomienia/notification";
import AlertPopup from "./alert-popup";



interface AlertOverlayProps {
    children: React.ReactNode;
  }

const AlertOverlay = ({children}:AlertOverlayProps) => {
    const {account} = useAccount()
    const [announcements] = useAnnouncments(account?.id)
    const [notifications] = useNotifications(account?.id)
    const [alertNotification,setAlertNotification] = useState<Notification | null>(null)

    return (
        <React.Fragment>
            {account && <div className="fixed top-4 right-4 z-50 flex gap-x-4">
                <Notifications
                    notificationList={notifications}
                    onNewNotification={(alert:Notification)=>{setAlertNotification(alert)}}
                />
                <Announcements
                    canCreate={["PRZEDSTAWICIEL_WŁADZ","ORGANIZACJA_POMOCOWA"].includes(account.role.roleName)}
                    announcementsList={announcements}
                />
            </div>}
            <div>{children}</div>
            <AlertPopup notification={alertNotification} />
        </React.Fragment>
    );
}

export default AlertOverlay;