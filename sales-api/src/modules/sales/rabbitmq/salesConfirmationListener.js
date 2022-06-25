import amqp from 'amqplib/callback_api.js';

import { RABBIT_MQ_URL } from '../../../utility.js';
import { SALES_CONFIRMATION_QUEUE } from '../../../config/rabbitmq/queue.js';
import OrderService from '../service/OrderService.js';

export function listenToSalesConfirmationQueue() {
    amqp.connect(RABBIT_MQ_URL , (error, connection) => {
        if (error)
            throw error;

        console.log('Listening to Sales confirmation Queue...');
        connection.createChannel((error, channel) => {
            if (error)
                throw error;

            channel.consume(SALES_CONFIRMATION_QUEUE, (message) => {
                console.info(`Recieve message from queue: ${message.content.toString()}`);
                OrderService.updateOrder(message);
            }, {
                noAck: true,
            });
        });
        
    })
}