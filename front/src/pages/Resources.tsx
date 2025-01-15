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
          <h1 className="text-bold text-5xl text-center my-8">List of Resources</h1>
          <ResourcesTable resources={resources}/>
      </div>
  );
};
