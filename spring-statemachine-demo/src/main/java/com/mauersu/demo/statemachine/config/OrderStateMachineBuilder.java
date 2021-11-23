package com.mauersu.demo.statemachine.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.messaging.Message;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.stereotype.Component;

import java.util.EnumSet;

@Component
@Slf4j
public class OrderStateMachineBuilder {

	private final static String MACHINEID = "orderMachine";

	 /**
	  * 构建状态机
	  *
	 * @param beanFactory
	 * @return
	 * @throws Exception
	 */
	public StateMachine<OrderStates, OrderEvents> build(BeanFactory beanFactory) throws Exception {
		 StateMachineBuilder.Builder<OrderStates, OrderEvents> builder = StateMachineBuilder.builder();

		 log.info("构建订单状态机");

		 builder.configureConfiguration()
		 		.withConfiguration()
		 		.machineId(MACHINEID)
		 		.beanFactory(beanFactory)
				 .listener(customListener());;

		 builder.configureStates()
		 			.withStates()
		 			.initial(OrderStates.UNPAID)
		 			.states(EnumSet.allOf(OrderStates.class));

		 builder.configureTransitions()
					 .withExternal()
						.source(OrderStates.UNPAID).target(OrderStates.WAITING_FOR_RECEIVE)
						.event(OrderEvents.PAY)
						.and()
					.withExternal()
						.source(OrderStates.WAITING_FOR_RECEIVE).target(OrderStates.DONE)
						.event(OrderEvents.RECEIVE)
				 		.and()
					 .withExternal()
						 .source(OrderStates.DONE).target(OrderStates.UNPAID)
						 .event(OrderEvents.BACK);

		 return builder.build();
	 }

	private StateMachineListenerAdapter<OrderStates, OrderEvents> customListener() {
		return new StateMachineListenerAdapter<OrderStates, OrderEvents>() {

			@Override
			public void eventNotAccepted(Message event) {
				//LOG which event was not accepted etc.
				log.error("eventNotAccepted");
				//throw new RuntimeException("eventNotAccepted");
			}
		};
	}

}
