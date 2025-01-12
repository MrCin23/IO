

import { useEffect, useState } from "react";
import Announcement from "../types/announcement";
import getDefaultRequest from "../lookup/backend-lookup";

const useAnnouncments = (userId: number) => {
    const [announcement, setAnnouncements] = useState<Announcement[]>([]);
    const [isLoading,setIsLoading] = useState<boolean>(false) 
    
    

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