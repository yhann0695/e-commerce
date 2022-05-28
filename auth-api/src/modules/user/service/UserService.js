import UserRepository from '../repository/UserRepository';
import * as utility from '../../../utility';

class UserService {

    async findByEmail(req) {
        try {
            const { email } = req.params;
            this.validateRequestEmail(email); 
            let user = UsuarioRepository.findByEmail(email);
            if(!user) {

            }
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
            throw new Error("User email was not informed.");
        }
    }
}

export default new UserService();