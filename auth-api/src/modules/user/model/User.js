import Sequelize from 'Sequelize';
import sequelize from '../../../config/db/dbConfig.js';

const User = sequelize.define('user', {
    id: {
        type: Sequelize.INTEGER,
        primaryKey: true,
        autoIncrement: true
    },
    name: {
        type: Sequelize.STRING,
        allowNull: false,
        unique: true
    },
    password: {
        type: Sequelize.STRING,
        allowNull: false
    },
    email: {
        type: Sequelize.STRING,
        allowNull: false,
        unique: true
    }
}, {
    // freezeTableName: true,
    // timestamps: false,
    // underscored: true,
    // underscoredAll: true
});

export default User;

