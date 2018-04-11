package org.usfirst.frc.team6763.robot;

//import org.usfirst.frc.team6763.robot.Instruction.State;

public class Instruction {
	
	private State _state;
	private double _limit;
	private double _targetAngle;
	
	/**
	 * Creates a new Instruction instance.
	 */
	public Instruction(final State state, final double limit, final double targetAngle1) {
		_state = state;
		_limit = limit;
		_targetAngle = targetAngle1;
	}
	
	public State getState() {
		return _state;
	}
	
	public double getLimit() {
		return _limit;
	}
	
	public double getTargetAngle() {
		return _targetAngle;
	}
	
	/**
	 * Robot abilities
	 */
	public enum State {
		STOP,
		DRIVE_FORWARD,
		DRIVE_BACKWARD,
		TURN_RIGHT,
		TURN_LEFT,
		SPIN_RIGHT,
		SPIN_LEFT,
		RAISE_LIFT,
		LOWER_LIFT,
		EJECT_CUBE,
		PULL_CUBE,
		WAIT;
	}
}