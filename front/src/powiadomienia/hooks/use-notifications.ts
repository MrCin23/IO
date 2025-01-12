

import { useEffect, useState } from "react";
import Notification from "../types/notification";
import getDefaultRequest from "../lookup/backend-lookup";

const useNotifications = (userId: number) => {
    const [notifications, setNotifications] = useState<Notification[]>([]);
    const [isLoading,setIsLoading] = useState<boolean>(false) 
    
    

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
    },[])



    return [notifications,setNotifications,isLoading] as const
}

export default useNotifications;