import OrderRepository from "../repository/OrderRepository";
import { sendMessageToProductStockUpdateQueue } from '../../product/rabbitmq/productStockUpdateSender.js';
import * as utility from '../../../utility';    
import { PENDING, ACCEPTED, REJECTED } from '../status/OrderStatus.js'; 
import OrderException from '../exception/OrderException.js';
import ProductClient from "../../product/client/ProductClient.js";

class OrderService {
    async createOrder(req) {
        try {
            let orderData = req.body;
            this.validateOrderData(orderData)
            const { authUser } = req;
            const { authorization } = req.headers; 
            let order = this.createInitialOrderData(orderData, authUser);
            await this.validateProductStock(order, authorization);
            let createdOrder = await OrderRepository.save(order);
            this.sendMessage(createdOrder);
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

    createInitialOrderData(orderData, authUser) {
        return {
            status: PENDING,
            user: authUser,
            createdAt: new Date(),
            updateAt: new Date(),
            products: orderData
        };
    }

    async updateOrder(orderMessage) {
        try {
            const order = JSON.parse(orderMessage);
            if(order.salesId && order.status) {
                let existingOrder = await OrderRepository.findById(order.salesId);
                if (existingOrder && order.status !== existingOrder.status) {
                    existingOrder.status = order.status;
                    existingOrder.updateAt = new Date();
                    await OrderRepository.save(existingOrder);
                }
            } else {
                console.warn('The order message was not complete.')
            }
        } catch (err) {
            console.error('Could not parse order message from queue.');
            console.error(err.message);
        }
    }

    validateOrderData(data) {
        if(!data || !data.products)
            throw new OrderException(utility.BAD_REQUEST, "The product must be informed")
    }

    async validateProductStock(order, token) {
        let stockIsOut = await ProductClient.checkProductStock(order.products, token);
            if (stockIsOut) {
                throw new OrderException(utility.BAD_REQUEST, 'The stock is out for the products.');
            }
    }

    sendMessage(createOrder) {
        const message = {
            salesId: createOrder.id,
            products: createOrder.products 
        };
        sendMessageToProductStockUpdateQueue(message);
    }
}

export default new OrderService();