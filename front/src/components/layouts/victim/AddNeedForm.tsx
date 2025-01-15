import React, { useState } from 'react';
import { TextField, Button, MenuItem, Select, InputLabel, FormControl, FormControlLabel, Radio, RadioGroup, SelectChangeEvent } from '@mui/material';
import axios from 'axios';
import Cookies from 'js-cookie';
import MapView from '../../mapComponent/MapView';

const itemCategories = ['CLOTHING', 'HOUSEHOLD', 'FOOD', 'TOYS', 'BOOKS'];

export default function AddNeedForm() {
  const [needType, setNeedType] = useState('material-needs');
  const [formData, setFormData] = useState({
    description: '',
    expirationDate: '',
    itemCategory: '',
    maxVolunteers: '',
    collectionGoal: ''
  });
  const [title, setTitle] = useState("");
  const [description, setDescription] = useState("");
  const [coordinates, setCoordinates] = useState<{ lat: number; lng: number } | null>(null);

  const handleChange = (e: React.ChangeEvent<HTMLInputElement | { name?: string; value: unknown }> | SelectChangeEvent<string>) => {
    const { name, value } = e.target as HTMLInputElement | { name?: string; value: unknown };
    setFormData({
      ...formData,
      [name as string]: value
    });
  };

  const handleNeedTypeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    setNeedType(e.target.value);
  };
  

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
  
    const token = Cookies.get('jwt');
    if (!token) {
      alert('You must be logged in to submit a need');
      return;
    }
  
    try {
      if (!title || !description || !coordinates) {
        alert('Wypełnij wszystkie pola!');
        return;
      }
  
      console.log('Coordinates:', coordinates);
      const mapResponse = await axios.post(
        'http://localhost:8080/api/map',
        {
          coordinates,
          title,
          description,
          type: 'VICTIM',
          active: true
        },
        {
          headers: { Authorization: `Bearer ${token}` }
        }
      );

      const pointId = mapResponse.data.pointID;
  
      if (mapResponse.status === 200 || mapResponse.status === 201) {
        console.log('New map point ID:', pointId);
        alert(`Punkt zapisany! ID: ${pointId}`);
      }
  
      const userResponse = await axios.get('http://localhost:8080/api/auth/me', {
        headers: { Authorization: `Bearer ${token}` }
      });
      const userId = userResponse.data.id;
  
      const requestData = {
        userId: userId,
        mapPointId: pointId,
        description: formData.description,
        expirationDate: formData.expirationDate
      };
  
      if (needType === 'material-needs') {
        Object.assign(requestData, { itemCategory: formData.itemCategory });
      } else if (needType === 'manual-needs') {
        Object.assign(requestData, { maxVolunteers: parseInt(formData.maxVolunteers) });
      } else if (needType === 'financial-needs') {
        Object.assign(requestData, { collectionGoal: parseFloat(formData.collectionGoal) });
      }
  
      console.log('Request data:', requestData);
      const endpoint = `http://localhost:8080/api/${needType}`;
      const response = await axios.post(endpoint, requestData, {
        headers: { Authorization: `Bearer ${token}` }
      });
  
      if (response.status === 200 || response.status === 201) {
        alert('Need submitted successfully!');
        setFormData({
          description: '',
          expirationDate: '',
          itemCategory: '',
          maxVolunteers: '',
          collectionGoal: ''
        });
        setCoordinates(null);
        setTitle('');
        setDescription('');
      }
    } catch (error) {
      console.error('Error submitting need:', error);
      const err = error as any;
      if (err.response && err.response.data && err.response.data.errors) {
        const errorMessage = err.response.data.errors.map((obj: any) => obj.defaultMessage).join(', ');
        alert(`Failed to submit need: ${errorMessage}`);
      } else {
        alert('Failed to submit need. Please try again.');
      }
    }
  };

  
  return (
    <form onSubmit={handleSubmit} className="space-y-4 bg-gray-100 p-4 rounded-xl border-5 text-black min-w-[540px] max-w-lg mx-auto shadow-sm border">

      <div className=" mt-1 h-[500px] w-[500px] mb-4 mx-auto rounded-xl overflow-hidden border border-gray-300 shadow-md">
      <MapView
        pointType="VICTIM"
        canAddPoints={true}
        externalForm={true}
        canShowPoints={false}
        setCoordinates={setCoordinates}
      />
      </div>

      <TextField
      name="coordinatetitle"
      label="Coordinate title"
      fullWidth
      margin="normal"
      value={title}
      onChange={(e) => setTitle(e.target.value)}
      className="bg-transparent"
      required
      />
      <TextField
      name="coordinatedescription"
      label="Coordinate description"
      fullWidth
      margin="normal"
      value={description}
      onChange={(e) => setDescription(e.target.value)}
      className="bg-transparent"
      required
      />

      <FormControl component="fieldset" className="w-full">
      <RadioGroup
        aria-label="need-type"
        name="needType"
        value={needType}
        onChange={handleNeedTypeChange}
        className="flex flex-col"
      >
        <FormControlLabel value="material-needs" control={<Radio />} label="Material Needs" />
        <FormControlLabel value="manual-needs" control={<Radio />} label="Manual Needs" />
        <FormControlLabel value="financial-needs" control={<Radio />} label="Financial Needs" />
      </RadioGroup>
      </FormControl>

      <TextField
      name="description"
      label="Description"
      fullWidth
      margin="normal"
      value={formData.description}
      onChange={handleChange}
      className="bg-transparent"
      required
      inputProps={{ minLength: 5 }}
      />
      <TextField
      name="expirationDate"
      label="Expiration Date"
      type="date"
      InputLabelProps={{ shrink: true }}
      fullWidth
      margin="normal"
      value={formData.expirationDate}
      onChange={handleChange}
      className="bg-transparent"
      required
      />

      {needType === 'material-needs' && (
      <FormControl fullWidth margin="normal" className="text-left">
        <InputLabel id="item-category-label" className='bg-gray-100'>Item Category</InputLabel>
        <Select
        labelId="item-category-label"
        name="itemCategory"
        value={formData.itemCategory}
        onChange={handleChange}
        className="bg-transparent"
        >
        {itemCategories.map((category) => (
          <MenuItem key={category} value={category}>
          {category}
          </MenuItem>
        ))}
        </Select>
      </FormControl>
      )}

      {needType === 'manual-needs' && (
      <TextField
        name="maxVolunteers"
        label="Max Volunteers"
        type="number"
        fullWidth
        margin="normal"
        value={formData.maxVolunteers}
        onChange={handleChange}
        className="bg-transparent"
      />
      )}

      {needType === 'financial-needs' && (
      <TextField
        name="collectionGoal"
        label="Collection Goal"
        type="number"
        fullWidth
        margin="normal"
        value={formData.collectionGoal}
        onChange={handleChange}
        className="bg-transparent"
      />
      )}

      <Button type="submit" variant="contained" color="primary" fullWidth>
      Submit
      </Button>
    </form>
  );
}