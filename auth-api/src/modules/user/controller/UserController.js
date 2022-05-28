import UserService from '../service/UserService';

class UserController {

    async findByEmail(request, response) {
        let user = await UserService.findByEmail(request);
        return response.status(user.status).json(user);
    }
}

export default new UserController();