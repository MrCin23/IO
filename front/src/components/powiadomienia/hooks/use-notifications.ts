

import { useEffect, useState } from "react";
import Notification from "../../../models/powiadomienia/notification";
import getDefaultRequest from "../../../lib/powiadomienia/backend-lookup";


/**
 * Hook odpowiedzialny za pobranie listy powiadomień przypisanych do określonego użytkownika.
 *
 * @param userId id użytkownika dla którego pobrane zostaną powiadomienia.
 * @returns zwraca listę zawierający listę powiadomień, funkcję pozwalającą na ustawienie wartości powiadomień oraz zmienną
 *  określającą czy powiadomienia zostały załodowane.
 */
const useNotifications = (userId: string) => {
    const [notifications, setNotifications] = useState<Notification[]>([]);
    const [isLoading,setIsLoading] = useState<boolean>(false) 
    
    
    /**
     * Pobiera powiadomienia dla bieżącego użytkownika z serwera.
     *
     * Metoda konstruuje URI z identyfikatorem użytkownika, wysyła żądanie GET do serwera
     * i aktualizuje stan powiadomień przy użyciu pobranych danych. Obsługuje również stan ładowania.
     *
     * @function
     * @returns {void} Funkcja nie zwraca wartości; aktualizuje stan komponentu.
     *
    */
    const fetchNotifications = ()=>{
        const uri=`http://localhost:8080/notifications/user/${userId}`
        setIsLoading(true)
        fetch(uri, getDefaultRequest("GET"))
            .then((response)=>{
                if(response.ok){
                    response.json().then((body)=>{
                        setNotifications(body)
                        setIsLoading(false)
                    })
                }
            })
    }


    useEffect(()=>{
        fetchNotifications()
        const interval = 10 * 1000
        const timerId = setInterval(() => {
            fetchNotifications()
          }, interval);

          return () =>{
            clearInterval(timerId)
          }
    },[userId])



    return [notifications,setNotifications,isLoading] as const
}

export default useNotifications;