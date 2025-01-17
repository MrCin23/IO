import React from "react";


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
    if (!isOpen) return null;

    return (
        <div className="fixed inset-0 z-50 flex items-center justify-center bg-black bg-opacity-50">
            <div className="bg-white rounded-lg shadow-lg w-96">
                <div className="p-4 border-b">
                    <h2 className="text-xl font-semibold">{title || "Modal Title"}</h2>
                    <button
                        className="absolute top-2 right-2 text-gray-400 hover:text-gray-600"
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
                        Close
                    </button>
                </div>
            </div>
        </div>
    );
};

export default Modal;
