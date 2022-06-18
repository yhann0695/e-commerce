const env = process.env;

export const SUCCESS = 200;
export const BAD_REQUEST = 400;
export const UNAUTHORIZED = 401;
export const FORBIDDEN = 403;
export const INTERVAL_SERVER_ERROR = 500;

export const API_SECRET = env.API_SECRET 
    ? env.API_SECRET 
    : 'YXV0aC1hcGktc2VjcmV0LWRldi0xMjM0NTY=';