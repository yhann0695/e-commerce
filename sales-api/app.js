import express from 'express';

import { connect } from "./src/config/db/mongoDbConfig.js";
import { createInicialData } from './src/config/db/initialData.js';
import checkToken from './src/config/auth/checkToken.js';
 
const app = express();
const env = process.env;
const PORT = env.PORT || 8082;

connect();
createInicialData();

app.use(checkToken);

app.get('/api/status', async (req, res) => {
    return res.status(200).json({
        service: 'Sales-API',
        status: 'ok',
        httpStatus: 200
    });
})

app.listen(PORT, () => {
    console.log(`Server started successfuly at port port ${PORT}`);
});