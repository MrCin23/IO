import React, { useEffect, useState } from 'react';
import Modal from './modal';
import Announcement, { AnnouncementType } from '../../models/powiadomienia/announcement';
import Speaker from './icons/Speaker';
import AnnouncementForm from './announcement-form';
import { useTranslation } from 'react-i18next';
import api from '../../api/Axios.tsx';




interface AnnouncementProps {
    announcementsList: Announcement[],
    canCreate: boolean
}

/**
 * Komponent wyświetlający listę ogłoszeń z możliwością ich ukrywania oraz dodawania nowych ogłoszeń.
 *
 * @returns {JSX.Element} Interfejs ogłoszeń.
 */
const Announcements: React.FC<AnnouncementProps> = ({ announcementsList, canCreate }) => {
    const [isModalOpen, setModalOpen] = useState<boolean>(false)
    const [announcements, setAnnouncements] = useState<Announcement[]>(announcementsList)
    const [isForm, setForm] = useState<boolean>(false)
    const { t } = useTranslation()


    /**
    * Obsługuje ukrywanie ogłoszenia przez jego usunięcie z listy oraz wysłanie żądania do API.
    *
    * @param {number} id - ID ogłoszenia, które ma zostać ukryte.
    */
    const handleHideAnnouncement = async (id: number) => {
        setAnnouncements(
            announcements.filter((value) => {
                return value.id !== id
            })
        )
        api.post(`/announcements/${id}/hide`)
    }

    const handleAddAnnouncement = (announcement: Announcement) => {

        setAnnouncements((prevState) => ([
            ...prevState, announcement
        ]))
        setForm(false)
    }


    const typeStyles = {
        [AnnouncementType.INFORMATION]: "bg-blue-100 text-blue-800",
        [AnnouncementType.WARNING]: "bg-red-100 text-red-800",
    };


    useEffect(() => {
        setAnnouncements(announcementsList)
    }, [announcementsList])


    return (

        <React.Fragment>
            <div>
                <button type="button" onClick={(_) => { setModalOpen(true); setForm(false) }} className="relative inline-flex items-center p-3 text-sm font-medium
             text-center text-white bg-blue-700 rounded-lg hover:bg-blue-800 focus:ring-4 focus:outline-none
              focus:ring-blue-300 dark:bg-blue-600 dark:hover:bg-blue-700 dark:focus:ring-blue-800">
                    <Speaker />
                    <span className="sr-only">{t('announcements.dialog_title')}</span>
                    <div className="absolute  inline-flex items-center justify-center w-6 h-6 text-xs font-bold
                 text-white bg-red-500 border-2 border-white rounded-full -top-2 -end-2">{announcements.length}</div>
                </button>
            </div>
            <Modal
                title={t('announcements.dialog_title')}
                isOpen={isModalOpen}
                onClose={() => { setModalOpen(false); setForm(false) }}>
                {(isForm && canCreate) ? (<AnnouncementForm onComplete={handleAddAnnouncement} />) :
                    <div>
                        {canCreate && <div className='flex flex-row-reverse w-full mb-2'>
                            <button className="bg-blue-500 text-white rounded-full w-8 h-8 hover:bg-blue-600 focus:outline-none 
                    text-xl flex items-center justify-center mr-2"
                                onClick={(_) => { setForm(true) }}>
                                <span className='mb-1'>+</span>
                            </button>
                        </div>}
                        <ul className="space-y-2">
                            {announcements.map((announcement) => (

                                <li
                                    key={announcement.id}
                                    className={`flex flex-col p-3 rounded-lg ${typeStyles[announcement.type]}`}
                                >
                                    <div className='flex'>
                                        <span className="font-semibold text-lg">{announcement.title}</span>
                                        <button className="ml-2 bg-red-500 text-white rounded-full w-5 h-5 flex items-center justify-center hover:bg-red-600 focus:outline-none"
                                            onClick={(_) => { handleHideAnnouncement(announcement.id) }}>
                                            <span className="text-xl mb-1">&times;</span>
                                        </button>
                                    </div>
                                    <span className="font-medium mt-1">{announcement.message}</span>
                                </li>
                            ))}
                        </ul>
                    </div>
                }

            </Modal>
        </React.Fragment>
    );
};

export default Announcements;
