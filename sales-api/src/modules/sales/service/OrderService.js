import OrderRepository from "../repository/OrderRepository";
import { sendMessageToProductStockUpdateQueue } from '../../product/rabbitmq/productStockUpdateSender.js';
import * as utility from '../../../utility';    
import { PENDING, ACCEPTED, REJECTED } from '../status/OrderStatus.js'; 
import OrderException from '../exception/OrderException.js';

class OrderService {
    async createOrder(req) {
        try {
            let orderData = req.body;
            this.validateOrderData(orderData)
            const { authUser } = req;
            let order = {
                status: PENDING,
                user: authUser,
                createdAt: new Date(),
                updateAt: new Date(),
                products: orderData
            };

            let createdOrder = await OrderRepository.save(order);
            sendMessageToProductStockUpdateQueue(createdOrder.products)
            return {
                status: utility.SUCCESS, accessToken 
            };
        } catch (err) {
            return {
                    status: err.status 
                    ? err.status 
                    : utility.INTERVAL_SERVER_ERROR,
                message: err.message
            };
        }
    }

    validateOrderData(data) {
        if(!data || !data.products)
            throw new OrderException(utility.BAD_REQUEST, "The product must be informed")
    }
}

export default new OrderService();