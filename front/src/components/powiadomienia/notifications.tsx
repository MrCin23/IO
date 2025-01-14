import React, { useState } from 'react';
import Modal from './modal';
import useNotifications from './hooks/use-notifications';
import { NotificationType } from '../../models/powiadomienia/notification';
import getDefaultRequest from '../../lib/powiadomienia/backend-lookup';
import Letter from './icons/Letter';
import { useAccount } from '../../contexts/uwierzytelnianie/AccountContext';

/**
 * Komponent wyświetlający listę powiadomień z możliwością ich ukrywania.
 *
 * @returns {JSX.Element} Interfejs powiadomień.
 */
const Notifications: React.FC = () => {
    const {account} = useAccount()
    if(!account){
        return null
    }
    const [isModalOpen, setModalOpen] = useState<boolean>(false)
    const [notifications, setNotifications] = useNotifications(account.id)

    /**
     * Ukrywa powiadomienie na podstawie jego identyfikatora.
     * Jeśli było to ostatnie powiadomienie, zamyka modal.
     *
     * @param {number} id - Identyfikator powiadomienia do ukrycia.
     */ 
    const handleHideNotification = (id: number) => {
        if (notifications.length === 1) {
            setModalOpen(false)
        }
        setNotifications(
            notifications.filter((value) => {
                return value.id !== id
            })
        )
        const url = `http://localhost:8080/notifications/${id}/hide`
        fetch(url, getDefaultRequest("POST"))
    }


    const typeStyles = {
        [NotificationType.INFORMATION]: "bg-blue-100 text-blue-800",
        [NotificationType.ERROR]: "bg-red-100 text-red-800",
        [NotificationType.WARNING]: "bg-yellow-100 text-yellow-800",
    };

    return (

        <React.Fragment>
            <div>
                <button type="button" onClick={(_) => { setModalOpen(true) }}
                    className="relative inline-flex items-center p-3 text-sm font-medium text-center
             text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none
              focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    <Letter />
                    <span className="sr-only">Notifications</span>
                    <div className="absolute inline-flex items-center justify-center w-6 h-6 text-xs
                     font-bold text-white bg-red-500 border-2 border-white rounded-full -top-2 -end-2 dark:border-gray-900">{notifications.length}</div>
                </button>
            </div>
            <Modal
                title='Notifications'
                isOpen={isModalOpen}
                onClose={() => { setModalOpen(false) }}>
                <ul className="space-y-2">
                    {notifications.map((notification) => (
                        <li
                            key={notification.id}
                            className={`flex items-center p-3 rounded-lg ${typeStyles[notification.type]}`}
                        >
                            <span className="ml-2">{notification.message}</span>
                            <button className="ml-2 bg-red-500 text-white rounded-full w-6 h-6 flex items-center justify-center hover:bg-red-600 focus:outline-none"
                                onClick={(_) => { handleHideNotification(notification.id) }}>
                                <span className="text-xl mb-1">&times;</span>
                            </button>
                        </li>
                    ))}
                </ul>
            </Modal>
        </React.Fragment>
    );
};

export default Notifications;
