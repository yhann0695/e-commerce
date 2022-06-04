import Sequelize from 'sequelize';

const sequelize = new Sequelize("auth-db", "admin", "123456", {
    host: "localhost",
    dialect: "postgres",
    quoteIdentifiers: false,
    define: {
        syncOnAssociation: true,
        timestamps: false,
        underscored: true,
        underscoredAll: true,
        freezeTableName: true
    }
});

sequelize.authenticate().then(() => {
    console.info("Connection has been established successfully.");
}).catch(err => {
    console.error("Unable to connect to the database:", err.message);
});

export default sequelize;