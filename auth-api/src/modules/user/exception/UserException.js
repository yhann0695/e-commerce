class UserException extends Error {
    constructor(status, message) {
        super(message);
        this.status = status;
        this.message = message;
        this.name = this.constructor.name;
        Erro.captureStackTrace(this.this.constructor);
    }
}

export default UserException;