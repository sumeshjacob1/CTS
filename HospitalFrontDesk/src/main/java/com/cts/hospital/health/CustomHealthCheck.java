package com.cts.hospital.health;

import org.springframework.boot.actuate.health.AbstractHealthIndicator;
import org.springframework.boot.actuate.health.Health;
import org.springframework.stereotype.Component;

/**
 * 
 * @author Sumesh Jacob (327723)
 *
 */
@Component
public class CustomHealthCheck extends AbstractHealthIndicator {
	@Override
	protected void doHealthCheck(Health.Builder bldr) throws Exception {
		boolean running = true;
		if (running) {
			bldr.up();
		} else {
			bldr.down();
		}
	}
}