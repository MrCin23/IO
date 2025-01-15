import { Typography, Card, CardContent } from '@mui/material';

export const VolunteerInfoPage = () => {
    return (
        <div>
            <Typography variant="h4" gutterBottom>
                Volunteer Information
            </Typography>
            <Card>
                <CardContent>
                    <Typography variant="h6">Name: Yellow  Fridge</Typography>
                    <Typography variant="body1">Role: Procrastination expert</Typography>
                    <Typography variant="body1">Contact: yellow.fridge@example.com</Typography>
                    <Typography variant="body1">Password: dupa123</Typography>
                </CardContent>
            </Card>
        </div>
    );
};
