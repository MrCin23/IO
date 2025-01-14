import { useState } from "react";
import Announcement, { AnnouncementType, createAnnouncement } from "../../models/powiadomienia/announcement";
import axios from "axios";
import Cookies from "js-cookie";


type AnnouncementFormProps = {
    onComplete: (announcement: Announcement) => void;
  };


/**
 * Komponent formularza służący do tworzenia nowych ogłoszeń.
 *
 * @function AnnouncementForm
 * @param {AnnouncementFormProps} props - Właściwości komponentu.
 * @returns {JSX.Element} Formularz do tworzenia ogłoszeń.
 */
const AnnouncementForm = ({onComplete}:AnnouncementFormProps) => {

    const [announcement, setAnnouncement] = useState<createAnnouncement>({
        title: "",
        message: "",
        type: AnnouncementType.INFORMATION
    });


   /**
   * Obsługuje zmiany w polach formularza i aktualizuje stan komponentu.
   *
   * @param {React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement | HTMLSelectElement>} event - Zdarzenie zmiany wartości pola.
   */
    const handleChange = (event: any) => {
        const { name, value } = event.target;
        setAnnouncement((prev) => ({
            ...prev,
            [name]: value

        }))
    };

   /**
   * Obsługuje wysyłanie formularza i wysyła dane ogłoszenia do API.
   *
   * @param {React.FormEvent<HTMLFormElement>} event - Zdarzenie wysłania formularza.
   */
    const handleSubmit = async (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();

       const token = Cookies.get('jwt');
       if (token) {
           try {
               const response = await axios.post("http://localhost:8080/announcements",
                   announcement,
                   {
                    headers: { Authorization: `Bearer ${token}` }
                });
               onComplete(response.data)
           } catch (error) {
               console.error('Failed to fetch announcements:', error);
           }
       }
    };


    return (
        <div className="flex justify-center items-center">
            <form
                onSubmit={handleSubmit}
                className="p-8 w-full max-w-lg"
            >
                <label
                    htmlFor="title"
                    className="block text-lg font-semibold mb-2"
                >
                    Title
                </label>
                <input
                    name="title"
                    type="text"
                    value={announcement.title}
                    onChange={handleChange}
                    placeholder="title"
                    className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                />

                <label
                    htmlFor="message"
                    className="block text-lg font-semibold mb-2"
                >
                    Your Message
                </label>
                <textarea
                    name="message"
                    value={announcement.message}
                    onChange={handleChange}
                    rows={4}
                    placeholder="Type your message here..."
                    className="w-full p-3 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-blue-500"
                ></textarea>

                <div className="mb-4">
                    <label htmlFor="type" className="block text-sm font-medium text-gray-700">
                        Notification Type
                    </label>
                    <select
                        id="type"
                        name="type"
                        value={announcement.type}
                        onChange={handleChange}
                        className="mt-1 block w-full p-2 border border-gray-300 rounded-md"
                    >
                        {Object.values(AnnouncementType).map((value)=>(
                            <option key={value} value={value}>{value}</option>
                        ))}
                    
                    </select>
                </div>
                <button
                    type="submit"
                    className="mt-4 w-full bg-blue-500 text-white py-2 rounded-lg hover:bg-blue-600"
                >
                    Submit
                </button>

            </form>
        </div>
    )
}

export default AnnouncementForm