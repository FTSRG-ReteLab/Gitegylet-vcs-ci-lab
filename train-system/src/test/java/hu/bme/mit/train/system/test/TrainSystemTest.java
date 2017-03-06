package hu.bme.mit.train.system.test;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import hu.bme.mit.train.interfaces.TrainController;
import hu.bme.mit.train.interfaces.TrainSensor;
import hu.bme.mit.train.interfaces.TrainUser;
import hu.bme.mit.train.system.TrainSystem;
import hu.bme.mit.train.tachograph.TrainTachograph;

import java.util.Date;

public class TrainSystemTest {

	TrainController controller;
	TrainSensor sensor;
	TrainUser user;
	TrainTachograph taco = new TrainTachograph();
	
	@Before
	public void before() {
		TrainSystem system = new TrainSystem();
		controller = system.getController();
		sensor = system.getSensor();
		user = system.getUser();

		sensor.overrideSpeedLimit(50);
	}
	
	@Test
	public void test1() {
		sensor.overrideSpeedLimit(10);

		Assert.assertEquals(0, controller.getReferenceSpeed());
		
		user.overrideJoystickPosition(5);

		controller.followSpeed();
		Assert.assertEquals(5, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
		controller.followSpeed();
		Assert.assertEquals(10, controller.getReferenceSpeed());
	}

	@Test
	public void test2() {
		user.overrideJoystickPosition(4);
		controller.followSpeed();
		user.overrideJoystickPosition(-5);
		controller.followSpeed();
		Assert.assertEquals(0, controller.getReferenceSpeed());
	}

	@Test
	public void test3() {
		controller.setSpeedLimit(30);
		user.overrideJoystickPosition(35);
		controller.followSpeed();
		Assert.assertEquals(30, controller.getReferenceSpeed());
	}
	
	@Test
	public void test4() {
		user.overrideJoystickPosition(3);
		controller.followSpeed();
		taco.recordValues(new Date(), user.getJoystickPosition(), controller.getReferenceSpeed());
		user.overrideJoystickPosition(6);
		controller.followSpeed();
		taco.recordValues(new Date(), user.getJoystickPosition(), controller.getReferenceSpeed());
		Assert.assertEquals(2, taco.getNumberOfElements());
	}
	
}
