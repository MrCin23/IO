

import { useEffect, useState } from "react";
import Announcement from "../types/announcement";
import getDefaultRequest from "../lookup/backend-lookup";




/**
 * Hook odpowiedzialny za pobranie listy ogłoszeń przypisanych do określonego użytkownika.
 *
 * @param userId id użytkownika dla którego pobrane zostaną ogłoszenia.
 * @returns zwraca listę zawierający listę ogłoszeń, funkcję pozwalającą na ustawienie wartości ogłoszeń, oraz zmienną określającą czy ogłoszenia są ładowane.
 */
const useAnnouncments = (userId: number) => {
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
    const fetchAnnouncements = ()=>{
        const uri=`http://localhost:8080/announcements/user/${userId}`
        setIsLoading(true)
        fetch(uri, getDefaultRequest("GET"),)
            .then((response)=>{
                if(response.ok){
                    response.json().then((body)=>{
                        setAnnouncements(body)
                        setIsLoading(false)
                    })
                }
            })
    }


    useEffect(()=>{
        fetchAnnouncements()
        const interval = 20 * 1000
        const timerId = setInterval(() => {
            fetchAnnouncements()
          }, interval);

          return () =>{
            clearInterval(timerId)
          }
    },[])



    return [announcement,setAnnouncements,isLoading] as const
}

export default useAnnouncments;