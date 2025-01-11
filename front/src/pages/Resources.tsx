import ResourcesTable from '@/components/ResourcesTable';
import { Resource } from '@/types';
import { useState, useEffect } from 'react';

const Resources = () => {
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

export default Resources;