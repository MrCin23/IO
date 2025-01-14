import React, { useState } from 'react';
import { TextField, Button, MenuItem, Select, InputLabel, FormControl, FormControlLabel, Radio, RadioGroup, SelectChangeEvent } from '@mui/material';
import axios from 'axios';
import Cookies from 'js-cookie';

const itemCategories = ['CLOTHING', 'HOUSEHOLD', 'FOOD', 'TOYS', 'BOOKS'];

export default function AddNeedForm() {
  const [needType, setNeedType] = useState('material-needs');
  const [formData, setFormData] = useState({
    mapPointId: '',
    description: '',
    expirationDate: '',
    itemCategory: '',
    maxVolunteers: '',
    collectionGoal: ''
  });

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
      // Get user ID first
      const userResponse = await axios.get('http://localhost:8080/api/auth/me', {
        headers: { Authorization: `Bearer ${token}` }
      });
      const userId = userResponse.data.id;

      // Prepare the request data based on need type
      const requestData = {
        userId: userId,
        mapPointId: formData.mapPointId,
        description: formData.description,
        expirationDate: formData.expirationDate,
      };

      console.log('Request data:', requestData);

      // Add specific fields based on need type
      if (needType === 'material-needs') {
        Object.assign(requestData, { itemCategory: formData.itemCategory });
      } else if (needType === 'manual-needs') {
        Object.assign(requestData, { maxVolunteers: parseInt(formData.maxVolunteers) });
      } else if (needType === 'financial-needs') {
        Object.assign(requestData, { collectionGoal: parseFloat(formData.collectionGoal) });
      }


      console.log('Request data:', requestData);

      // Determine the endpoint
      const endpoint = `http://localhost:8080/api/${needType}`;

      // Make the API call
      const response = await axios.post(endpoint, requestData, {
        headers: { Authorization: `Bearer ${token}` }
      });

      console.log('Response:', response);

      if (response.status === 200 || response.status === 201) {
        alert('Need submitted successfully!');
        // Reset form
        setFormData({
          mapPointId: '',
          description: '',
          expirationDate: '',
          itemCategory: '',
          maxVolunteers: '',
          collectionGoal: ''
        });
      }
    } catch (error) {
      console.error('Error submitting need:', error);
      const err = error as any;
      if (err.response && err.response.data && err.response.data.errors) {
        const errorMessage = err.response.data.errors.map((err: any) => err.defaultMessage).join(', ');
        alert(`Failed to submit need: ${errorMessage}`);
      } else {
        alert('Failed to submit need. Please try again.');
      }
    }
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4 bg-gray-100 p-4 rounded-xl border-5 text-black max-w-lg mx-auto shadow-sm border">
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
        name="mapPointId"
        label="Map Point ID"
        fullWidth
        margin="normal"
        value={formData.mapPointId}
        onChange={handleChange}
        className="bg-transparent"
      />
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