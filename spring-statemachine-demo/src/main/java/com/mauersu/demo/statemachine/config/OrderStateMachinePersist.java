package com.mauersu.demo.statemachine.config;
import com.mauersu.demo.statemachine.entity.Order;
import org.springframework.statemachine.StateMachineContext;
import org.springframework.statemachine.StateMachinePersist;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Component;

@Component
public class OrderStateMachinePersist implements StateMachinePersist<OrderStates, OrderEvents, Order> {

	@Override
	public void write(StateMachineContext<OrderStates, OrderEvents> context, Order contextObj) throws Exception {
		//这里不做任何持久化工作
	}

	@Override
	public StateMachineContext<OrderStates, OrderEvents> read(Order contextObj) throws Exception {
		StateMachineContext<OrderStates, OrderEvents> result =
				new DefaultStateMachineContext<OrderStates, OrderEvents>(OrderStates.codeOf(contextObj.getStatus()),
				null, null, null, null, "orderMachine");
		return result;
	}
}
