import { useState } from "react";
import Announcement, { AnnouncementType, createAnnouncement } from "../types/announcement";
import getDefaultRequest from "../lookup/backend-lookup";


type AnnouncementFormProps = {
    onComplete: (announcement: Announcement) => void;
  };

const AnnouncementForm = ({onComplete}:AnnouncementFormProps) => {

    const [announcement, setAnnouncement] = useState<createAnnouncement>({
        title: "",
        message: "",
        type: AnnouncementType.INFORMATION
    });

    const handleChange = (event: any) => {
        const { name, value } = event.target;
        setAnnouncement((prev) => ({
            ...prev,
            [name]: value

        }))
    };

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const uri = "http://localhost:8080/announcements"
        fetch(uri,getDefaultRequest("POST",announcement)).then((response)=>{
            if(response.ok){
                response.json().then((value)=>{
                    onComplete(value)
                })
            }
        })
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
                            <option value={value}>{value}</option>
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