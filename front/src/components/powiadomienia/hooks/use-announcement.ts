

import { useEffect, useState } from "react";
import Announcement from "../../../models/powiadomienia/announcement";
import Cookies from "js-cookie";
import axios from "../../../api/Axios.tsx";




/**
 * Hook odpowiedzialny za pobranie listy ogłoszeń przypisanych do określonego użytkownika.
 *
 * @param userId id użytkownika dla którego pobrane zostaną ogłoszenia.
 * @returns zwraca listę zawierający listę ogłoszeń, funkcję pozwalającą na ustawienie wartości ogłoszeń, oraz zmienną określającą czy ogłoszenia są ładowane.
 */
const useAnnouncments = (userId?: string|null) => {
    const [announcement, setAnnouncements] = useState<Announcement[]>([]);
    const [isLoading,setIsLoading] = useState<boolean>(false) 
    
    

    /**
     * Pobiera ogłoszenia dla bieżącego użytkownika z serwera.
     *
     * Metoda konstruuje URI z identyfikatorem użytkownika, wysyła żądanie GET do serwera
     * i aktualizuje stan ogłoszeń przy użyciu pobranych danych. Obsługuje również stan ładowania.
     *
     * @function
     * @returns {void} Funkcja nie zwraca wartości; aktualizuje stan komponentu.
     *
    */
    const fetchAnnouncements= async () => {
            setIsLoading(true)
            const token = Cookies.get('jwt');
            if (token) {
                try {
                    const response = await axios.get(`http://localhost:8080/announcements/user`, {
                        headers: { Authorization: `Bearer ${token}` },
                    });
                    setAnnouncements(response.data)
                    setIsLoading(false)
                } catch (error) {
                    console.error('Failed to fetch announcements:', error);
                }
            }
        };



    useEffect(()=>{
        if(!userId){
            return
        }
        fetchAnnouncements()
        const interval = 20 * 1000
        const timerId = setInterval(() => {
            fetchAnnouncements()
          }, interval);

          return () =>{
            clearInterval(timerId)
          }
    },[userId])



    return [announcement,isLoading] as const
}

export default useAnnouncments;