import React, { useState } from 'react';
import { TextField, Button, MenuItem, Select, InputLabel, FormControl, FormControlLabel, Radio, RadioGroup, SelectChangeEvent } from '@mui/material';
import axios from 'axios';
import { useTranslation } from 'react-i18next';
import Cookies from 'js-cookie';
import MapView from '../../mapComponent/MapView';

const itemCategories = ['CLOTHING', 'HOUSEHOLD', 'FOOD', 'TOYS', 'BOOKS'];

export default function AddNeedForm() {
  const { t } = useTranslation();

  const [needType, setNeedType] = useState('material-needs');
  const [formData, setFormData] = useState({
    description: '',
    expirationDate: '',
    itemCategory: '',
    maxVolunteers: '',
    collectionGoal: ''
  });
  
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
      alert(t('logginError'));
      return;
    }
  
    try {
      if (!coordinates) {
        alert(t('mapError'));
        return;
      }

      const userResponse = await axios.get('http://localhost:8080/api/auth/me', {
        headers: { Authorization: `Bearer ${token}` }
      });
      const userId = userResponse.data.id;

      const requestData = {
        userId: userId,
        description: formData.description,
        expirationDate: formData.expirationDate
      };

      if (requestData.expirationDate < new Date().toISOString().split('T')[0]) {
        alert(t('expirationDateError'));
        return;
      }

      let title = '';
      let description = '';
  
      if (needType === 'material-needs') {
        Object.assign(requestData, { itemCategory: formData.itemCategory });
        console.log(requestData)
        title = "Material need";
        description = formData.description + ' - ' + formData.itemCategory + ' - expires: ' + formData.expirationDate;
      } else if (needType === 'manual-needs') {
        Object.assign(requestData, { maxVolunteers: parseInt(formData.maxVolunteers) });
        title = "Manual need";
        description = formData.description + ' - volunteers: ' + formData.maxVolunteers + ' - expires: ' + formData.expirationDate;
      } else if (needType === 'financial-needs') {
        Object.assign(requestData, { collectionGoal: parseFloat(formData.collectionGoal) });
        title = "Financial need";
        description = formData.description + ' - goal: ' + formData.collectionGoal + ' - expires: ' + formData.expirationDate;
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

      Object.assign(requestData, { mapPointId: pointId });
  
      console.log('Request data:', requestData);
      const endpoint = `http://localhost:8080/api/${needType}`;
      const response = await axios.post(endpoint, requestData, {
        headers: { Authorization: `Bearer ${token}` }
      });
  
      if (response.status === 200 || response.status === 201) {
        alert('Potrzeba zapisana!');
        setFormData({
          description: '',
          expirationDate: '',
          itemCategory: '',
          maxVolunteers: '',
          collectionGoal: ''
        });
        setCoordinates(null);
        //setTitle('');
        //setDescription('');
      }
    } catch (error) {
      console.error('Failed to submit need:', error);
      const err = error as any;
      if (err.response && err.response.data && err.response.data.errors) {
        const errorMessage = err.response.data.errors.map((obj: any) => obj.defaultMessage).join(', ');
        alert(`Błąd: ${errorMessage}`);
      } else {
        alert('Błąd podczas zapisywania potrzeby');
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

      <FormControl component="fieldset" className="w-full">
      <RadioGroup
        aria-label="need-type"
        name="needType"
        value={needType}
        onChange={handleNeedTypeChange}
        className="flex flex-col"
      >
        <FormControlLabel value="material-needs" control={<Radio />} label={t("materialNeed")} />
        <FormControlLabel value="manual-needs" control={<Radio />} label={t("manualNeed")} />
        <FormControlLabel value="financial-needs" control={<Radio />} label={t("financialNeed")} />
      </RadioGroup>
      </FormControl>

      <TextField
      name="description"
      label={t('formDescription')}
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
      label={t('formExpirationDate')}
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
        <InputLabel id="item-category-label" className='bg-gray-100'>{t('formItemCategory')}</InputLabel>
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
        label={t('formMaxVolunteers')}
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
        label={t('formCollectionGoal')}
        type="number"
        fullWidth
        margin="normal"
        value={formData.collectionGoal}
        onChange={handleChange}
        className="bg-transparent"
      />
      )}

      <Button type="submit" variant="contained" color="primary" fullWidth>
      {t('formSubmitButton')}
      </Button>
    </form>
  );
}