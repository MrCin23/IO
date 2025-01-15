import {ResourcesTable} from '../components/layouts/resources/ResourcesTable.tsx';
import { Resource } from '../types/index';
import { useState, useEffect } from 'react';
import api from "../api/Axios.tsx";

export const Resources = () => {
    const [resources, setResources] = useState<Resource[]>([]);

    useEffect(() => {
        const fetchResources = async () => {
            const response = await api.get("/resources")
            setResources(response.data);
        }
        fetchResources();
    }, []);

  return (
      <div className="min-h-screen">
          <ResourcesTable resources={resources}/>
      </div>
  );
};
