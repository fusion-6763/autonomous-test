/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team6763.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SerialPort;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.Encoder;

import java.util.ArrayList;
import java.util.Arrays;

import org.usfirst.frc.team6763.robot.Instruction.State;

import com.kauailabs.navx.frc.AHRS;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private static final String kDefaultAuto = "Default";
	private static final String kCustomAuto = "My Auto";
	private String m_autoSelected;
	private SendableChooser<String> m_chooser = new SendableChooser<>();
	
	DifferentialDrive myRobot = new DifferentialDrive(new Spark(0), new Spark(1));
	
	Encoder leftEncoder = new Encoder(0, 1);
	Encoder rightEncoder = new Encoder(2, 3);
	
	AHRS navx = new AHRS(SerialPort.Port.kUSB);
	
	ArrayList<Instruction> autoMode;
	
	int instructionIndex = 0;
	
	double defaultSpeed = 0.8;
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		m_chooser.addDefault("Default Auto", kDefaultAuto);
		m_chooser.addObject("My Auto", kCustomAuto);
		SmartDashboard.putData("Auto choices", m_chooser);
		
		populateSmartDashboard();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
		m_autoSelected = m_chooser.getSelected();
		// autoSelected = SmartDashboard.getString("Auto Selector",
		// defaultAuto);
		System.out.println("Auto selected: " + m_autoSelected);
		
		//populateSmartDashboard();
		
		double distance1 = SmartDashboard.getNumber("Distance 1", 0);
		double targetAngle1 = SmartDashboard.getNumber("Target Angle 1", 0);
		double turnAngle1 = SmartDashboard.getNumber("Turn Angle 1", 0);
		
		double distance2 = SmartDashboard.getNumber("Distance 1", 0);
		double targetAngle2 = SmartDashboard.getNumber("Target Angle 1", 0);
		double turnAngle2 = SmartDashboard.getNumber("Turn Angle 1", 0);
		
		double distance3 = SmartDashboard.getNumber("Distance 1", 0);
		double targetAngle3 = SmartDashboard.getNumber("Target Angle 1", 0);
		double turnAngle3 = SmartDashboard.getNumber("Turn Angle 1", 0);
		
		double distance4 = SmartDashboard.getNumber("Distance 1", 0);
		double targetAngle4 = SmartDashboard.getNumber("Target Angle 1", 0);
		double turnAngle4 = SmartDashboard.getNumber("Turn Angle 1", 0);
		
		double distance5 = SmartDashboard.getNumber("Distance 1", 0);
		double targetAngle5 = SmartDashboard.getNumber("Target Angle 1", 0);
		double turnAngle5 = SmartDashboard.getNumber("Turn Angle 1", 0);
		
		double distance6 = SmartDashboard.getNumber("Distance 1", 0);
		double targetAngle6 = SmartDashboard.getNumber("Target Angle 1", 0);
		double turnAngle6 = SmartDashboard.getNumber("Turn Angle 1", 0);
		
		autoMode = new ArrayList<Instruction>(Arrays.asList(
				new Instruction((distance1 > 0) ? State.DRIVE_FORWARD : State.DRIVE_BACKWARD, distance1, targetAngle1),
				new Instruction((turnAngle1 > 0) ? State.SPIN_RIGHT : State.SPIN_LEFT, 0, turnAngle1),
				
				new Instruction((distance2 > 0) ? State.DRIVE_FORWARD : State.DRIVE_BACKWARD, distance2, targetAngle2),
				new Instruction((turnAngle2 > 0) ? State.SPIN_RIGHT : State.SPIN_LEFT, 0, turnAngle2),
				
				new Instruction((distance3 > 0) ? State.DRIVE_FORWARD : State.DRIVE_BACKWARD, distance3, targetAngle3),
				new Instruction((turnAngle3 > 0) ? State.SPIN_RIGHT : State.SPIN_LEFT, 0, turnAngle3),
				
				new Instruction((distance4 > 0) ? State.DRIVE_FORWARD : State.DRIVE_BACKWARD, distance4, targetAngle4),
				new Instruction((turnAngle4 > 0) ? State.SPIN_RIGHT : State.SPIN_LEFT, 0, turnAngle4),
				
				new Instruction((distance5 > 0) ? State.DRIVE_FORWARD : State.DRIVE_BACKWARD, distance5, targetAngle5),
				new Instruction((turnAngle5 > 0) ? State.SPIN_RIGHT : State.SPIN_LEFT, 0, turnAngle5),
				
				new Instruction((distance6 > 0) ? State.DRIVE_FORWARD : State.DRIVE_BACKWARD, distance6, targetAngle6),
				new Instruction((turnAngle6 > 0) ? State.SPIN_RIGHT : State.SPIN_LEFT, 0, turnAngle6),
				
				new Instruction(State.STOP, 0, 0)));
		
		leftEncoder.reset();
		rightEncoder.reset();
		navx.reset();
		
		instructionIndex = 0;
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		Instruction instruction = new Instruction(State.STOP ,0, 0);
		if(instructionIndex < autoMode.size()) {
			instruction = autoMode.get(instructionIndex);
		}
		switch(instruction.getState()) {
			case DRIVE_FORWARD:
				if(leftEncoder.get() < instruction.getLimit()) {
					accurateDrive(navx.getYaw(), defaultSpeed, instruction.getTargetAngle(), 2);
				}
				else {
					leftEncoder.reset();
					rightEncoder.reset();
					instructionIndex++;
				}
				break;
			case DRIVE_BACKWARD:
				if(leftEncoder.get() > instruction.getLimit()) {
					accurateDrive(navx.getYaw(), defaultSpeed, instruction.getTargetAngle(), 2);
				}
				else {
					leftEncoder.reset();
					rightEncoder.reset();
					instructionIndex++;
				}
			case SPIN_LEFT:
				if(navx.getYaw() > instruction.getTargetAngle()) {
					myRobot.tankDrive(-defaultSpeed, defaultSpeed);
				}
				else {
					leftEncoder.reset();
					rightEncoder.reset();
					instructionIndex++;
				}
				break;
			case SPIN_RIGHT:
				if(navx.getYaw() < instruction.getTargetAngle()) {
					myRobot.tankDrive(defaultSpeed, -defaultSpeed);
				}
				else {
					leftEncoder.reset();
					rightEncoder.reset();
					instructionIndex++;
				}
			case STOP: //Intentional fall-through
			default:
				myRobot.tankDrive(0.0, 0.0);
				break;
		}
		
	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
	
	public void populateSmartDashboard() {
		SmartDashboard.putNumber("Distance 1", 0);
		SmartDashboard.putNumber("Target Angle 1", 0);
		SmartDashboard.putNumber("Turn Angle 1", 0);
		
		SmartDashboard.putNumber("Distance 2", 0);
		SmartDashboard.putNumber("Target Angle 2", 0);
		SmartDashboard.putNumber("Turn Angle 2", 0);
		
		SmartDashboard.putNumber("Distance 3", 0);
		SmartDashboard.putNumber("Target Angle 3", 0);
		SmartDashboard.putNumber("Turn Angle 3", 0);
		
		SmartDashboard.putNumber("Distance 4", 0);
		SmartDashboard.putNumber("Target Angle 4", 0);
		SmartDashboard.putNumber("Turn Angle 4", 0);
		
		SmartDashboard.putNumber("Distance 5", 0);
		SmartDashboard.putNumber("Target Angle 5", 0);
		SmartDashboard.putNumber("Turn Angle 5", 0);
		
		SmartDashboard.putNumber("Distance 6", 0);
		SmartDashboard.putNumber("Target Angle 6", 0);
		SmartDashboard.putNumber("Turn Angle 6", 0);
		
		
	}
	
	public void accurateDrive(final float gyroValue, final double speed, final double targetAngle, final int tolerance) {
		System.out.println("speed: "+speed);
		if(gyroValue < targetAngle - tolerance) {
			System.out.println("Too far left");
			myRobot.tankDrive(speed, speed / 4);
		}
		else if(gyroValue > targetAngle + tolerance) {
			System.out.println("Too far right");
			myRobot.tankDrive(speed / 4, speed);
		}
		else {
			System.out.println("Good");
			myRobot.tankDrive(speed, speed);
		}
	}
}
