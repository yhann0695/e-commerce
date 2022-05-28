import bcrypt from 'bcrypt';
import jwt from 'jsonwebtoken';

import UserRepository from '../repository/UserRepository.js';
import * as utility from '../../../utility.js';
import UserException from '../exception/UserException.js';

class UserService {

    async findByEmail(req) {
        try {
            const { email } = req.params;
            this.validateRequestEmail(email); 
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            return { 
                status: utility.SUCCESS,
                user: {
                    id: user.id, 
                    name: user.name,
                    email: user.email
                }
            }
        } catch (err) {
            return { 
                status: err.status ? err.status : utility.INTERVAL_SERVER_ERROR,
                message: err.status
            }
        }
    }

    validateRequestEmail(email) {
        if(!email) {
            throw new UserException(utility.BAD_REQUEST, "User email was not informed.");
        }
    }

    validateUserNotFound(user) {
        if(!user) {
            throw new UserException(utility.BAD_REQUEST, "User was not found.");
        }
    }

    async getAccessToken(req) {
        try {
            const { email, password } = req.body;
            this.validateAccessTokenData(email, password);
            let user = await UserRepository.findByEmail(email);
            this.validateUserNotFound(user);
            await this.validatePassword(password, user.password);
            const authUser = { id: user.id, name: user.name, email: user.email };
            const accessToken = jwt.sign({ authUser }, utility.API_SECRET, { expiresIn: "1d" });
            return { status: utility.SUCCESS, accessToken }
        } catch (err) {
            return { 
                status: err.status ? err.status : utility.INTERVAL_SERVER_ERROR,
                message: err.status
            }
        }

    }

    validateAccessTokenData(email, password) {
        if(!email || !password) {
            throw new UserException(utility.UNAUTHORIZED, "Email and password must be in informed.");
        }
    }

    async validatePassword(password, hashPassword) {
        if(!await bcrypt.compare(password, hashPassword)) {
            throw new UserException(utility.UNAUTHORIZED, "Password doesn't match.");
        }
    }
}

export default new UserService();