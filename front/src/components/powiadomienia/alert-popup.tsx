import React, { useState, useEffect } from "react";
import Notification, { NotificationType } from "../../models/powiadomienia/notification";

interface AlertPopupProps {
    notification: Notification | null,

}

const AlertPopup: React.FC<AlertPopupProps> = ({ notification }: AlertPopupProps) => {
    const [showPopup, setShowPopup] = useState(false);

    useEffect(() => {
        if ((!notification)) {
            return
        }
        setShowPopup(true)
        const timer = setTimeout(() => setShowPopup(false), 7000);

        return () => clearTimeout(timer);
    }, [notification]);

    if (!showPopup) return null;


    return (
        <div
            className={`fixed bottom-4 right-4 bg-white border-2
        border-blue-500 text-blue-500 p-4 rounded shadow-lg transition-opacity duration-50 transition-all rounded-lg ${showPopup ? "" : "bottom-0"}
        ${notification?.type === NotificationType.ERROR && "border-red-500"}
        ${notification?.type === NotificationType.WARNING && "border-yellow-500"}

      }`}
        >
            <div className="flex justify-between items-center">
                <p>{notification?.message}</p>
                <button
                    className="text-blue-500 hover:text-blue-700 ml-4"
                    onClick={() => setShowPopup(false)}
                >
                    &times;
                </button>
            </div>
        </div>
    );
};


export default AlertPopup;
