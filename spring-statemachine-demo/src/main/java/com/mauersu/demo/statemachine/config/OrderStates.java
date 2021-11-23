package com.mauersu.demo.statemachine.config;

import java.util.Arrays;
import java.util.Objects;

public enum OrderStates {
	UNPAID(0, "待支付"), // 待支付
	WAITING_FOR_RECEIVE(1, "待收货"), // 待收货
	DONE(2, "结束"), // 结束
	;

	private int code;
	private String desc;

	OrderStates(int code, String desc) {
		this.code = code;
		this.desc = desc;
	}

	public static OrderStates codeOf(Integer status) {
		return Arrays.asList(OrderStates.values()).stream().filter(orderStates ->
								(orderStates.getCode()==OrderStates.UNPAID.getCode() && Objects.isNull(status))
										|| orderStates.getCode() == status)
				.findFirst().orElseThrow(IllegalArgumentException::new);
	}


	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}
}
