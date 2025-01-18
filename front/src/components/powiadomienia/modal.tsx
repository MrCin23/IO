import React from "react";
import { useTranslation } from "react-i18next";


/**
 * Interfejs definiujący właściwości komponentu Modal.
 */
interface ModalProps {
    /**
     * Określa, czy modal jest otwarty.
     */
    isOpen: boolean;

     /**
     * Funkcja wywoływana podczas zamknięcia modalu.
     */
    onClose: () => void;

    /**
     * Tytuł modalu..
     */
    title?: string;

     /**
     * Zawartość wyświetlana wewnątrz modalu.
     */
    children: React.ReactNode;
}


/**
 * Komponent `Modal` renderuje modal z zawartością zdefiniowaną poprzez jego właściwości.
 *
 * @param {ModalProps} props - Właściwości komponentu.
 * @returns {JSX.Element | null} Renderowany modal lub `null`, jeśli jest zamknięty.
 */
const Modal: React.FC<ModalProps> = ({ isOpen, onClose, title, children }) => {
    const {t} = useTranslation()
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 flex items-center z-[10000] justify-center bg-black bg-opacity-50">
            <div className=" [z-index:5000000] bg-white rounded-lg shadow-lg w-96">
                <div className="p-4 border-b ">
                    <h2 className="text-xl text-black font-semibold">{title || "Modal Title"}</h2>
                    <button
                        className="absolute top-2 right-2 text-gray-400 hover:text-gray-600 z-100"
                        onClick={onClose}
                    >
                        &times;
                    </button>
                </div>
                <div className="p-4">{children}</div>
                <div className="flex justify-end p-4 border-t">
                    <button
                        className="px-4 py-2 text-white bg-blue-500 rounded hover:bg-blue-600"
                        onClick={onClose}
                    >
                        {t("general.cancel")}
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Modal;
