import Order from '../../modules/sales/model/Order.js';

export async function createInicialData() {
    await Order.collection.drop();
    await Order.create({
        products: [
            {
                productId: 1001,
                quanitty: 2,
            },
            {
                productId: 1002,
                quanitty: 1,
            },
            {
                productId: 1003,
                quanitty: 1,
            },
        ],
        user: {
            id: '32v1jhv21', 
            name: 'User Test', 
            email: 'usertest@gmail.com'
        }, 
        status: 'APPROVED',
        createdAt: new Date(),
        updatedAt: new Date() 
    });
    await Order.create({
        products: [
            {
                productId: 1001,
                quanitty: 4,
            },
            {
                productId: 1003,
                quanitty: 2,
            },
        ],
        user: {
            id: 'jh432j342', 
            name: 'User Test2', 
            email: 'usertest2@gmail.com'
        }, 
        status: 'REJECTED',
        createdAt: new Date(),
        updatedAt: new Date() 
    });
    let initialData = await Order.find();
    console.info(`Initial data was created: ${JSON.stringify(initialData, undefined, 4)}`);
}

