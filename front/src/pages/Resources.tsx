import {ResourcesTable} from '../components/layouts/resources/ResourcesTable.tsx';
import { Resource } from '../types/index';
import { useState, useEffect } from 'react';

export const Resources = () => {
    const [resources, setResources] = useState<Resource[]>([]);

    useEffect(() => {
        const fetchResources = async () => {
            const response = await fetch("/api/resources");
            const result = await response.json();
            setResources(result);
        }
        fetchResources();
    }, []);

  return (
      <div>
          <h1 className="text-bold text-5xl text-center my-8">List of Resources</h1>
          <ResourcesTable resources={resources}/>
      </div>
  );
};
