import jwt from 'jsonwebtoken';
import { promisify } from 'util';

import AuthException from './AuthException.js'

import * as utility from '../../utility.js';

const emptySpace = ' ';

export default async(req, res, next) => {
    try {
        const { authorization } = req.headers;

        validateAuthorization(authorization);
        let accessToken = authorization;
        accessToken = validateAccessToken(accessToken, authorization);
        const decoded = await promisify(jwt.verify)(accessToken, utility.API_SECRET);
        req.authUser = decoded.authUser;
        return next();
    } catch (err) {
        const status = err.status ? err.status : utility.INTERVAL_SERVER_ERROR
        return res.status(status).json({ status, message: err.status });
    }
    
} 

function validateAuthorization(authorization) {
    if (!authorization) {
        throw new AuthException(utility.UNAUTHORIZED, 'Access token was not informed.');
    }
}

function validateAccessToken(accessToken, authorization) {
    if (accessToken.includes(emptySpace)) {
        accessToken = accessToken.split(emptySpace)[1];
    } else {
        accessToken = authorization;
    }
    return accessToken;
}
