package com.mauersu.demo.statemachine.config;
import com.mauersu.demo.statemachine.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.persist.DefaultStateMachinePersister;
import org.springframework.statemachine.persist.StateMachinePersister;

@Configuration
public class PersistConfig {


	@Autowired
    private OrderStateMachinePersist orderStateMachinePersist;

	@Bean(name="orderPersister")
    public StateMachinePersister<OrderStates, OrderEvents, Order> orderPersister() {
		return new DefaultStateMachinePersister<OrderStates, OrderEvents, Order>(orderStateMachinePersist);
	}

}
